package ic.vela.ibridge.test.kangcli;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@SuppressWarnings("unused")
public class KangClient {

	private static final String   CLIENT_PATH    = "D:/KANG/WORK/workspace/KangProj/src/ic/test/kangcli/";
	private static final String   SERVER_PATH    = "D:/KANG/WORK/workspace/KangProj/src/ic/test/kangsvr/";
	
	private static final String[] FILE01         = { "ReqStd.xml", "StdToDiv01.xsl", "ReqDiv01.xml" };
	private static final String[] FILE02         = { "ReqStd.xml", "StdToDiv02.xsl", "ReqDiv02.xml" };
	private static final String[] FILE03         = { "ResDiv01.xml", "Div01ToStd.xsl", "ResStd01.xml" };
	private static final String[] FILE04         = { "ResDiv02.xml", "Div02ToStd.xsl", "ResStd02.xml" };

	private static final String   IP             = "127.0.0.1";
	private static final int      PORT           = 8888; 
	
	private Socket                socket         = null;
	private DataInputStream       dis            = null;
	private DataOutputStream      dos            = null;

	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////

	// contructor
	public KangClient() {
		System.out.println("KangClient started....");
	}
	
	// socket recv
	public byte[] recv(final int size) throws Exception {
		int ret = 0;
		int readed = 0;
		byte[] buf = new byte[size];
		
		socket.setSoTimeout(0);
		while (readed < size) {
			ret = dis.read(buf, readed, size - readed);
			//System.out.println("    size:" + size + "    readed:" + readed + "     ret:" + ret);
			
			if (ret <= 0) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}

				continue;
			} else {
				socket.setSoTimeout(1000);
			}
			
			readed += ret;
		}
		
		return buf;
	}
	
	// socket send
	public void send(final byte[] buf, final int off, final int len) throws Exception {
		dos.write(buf, off, len);
		dos.flush();
	}
	
	// execute
	public void execute_org() {
		try {
			// to get the socket connected to server
			socket = new Socket(IP, PORT);
			
			System.out.println("socket connect to Server.....\n");

			// make input/output stream
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// Send Request
			{
				// send length to server
				byte[] len = "0010".getBytes();
				send(len, 0, 4);
	
				System.out.println("SEND LEN:" + new String(len));
				
				// send data to server
				byte[] data = "0123456789".getBytes();
				send(data, 0, 10);
	
				System.out.println("SEND DATA:" + new String(data));
			}
			
			// Recv Response
			{
				// recv length from server
				byte[] len = recv(4);
				System.out.println("RECV LEN:" + new String(len));
				
				// get size from length
				int size = Integer.parseInt(new String(len));
				System.out.println("RECV SIZE:" + size);
				
				// recv data from server
				byte[] data = recv(size);
				System.out.println("RECV DATA:" + new String(data));
			}
			
			dis.close();
			dos.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// FileInputStream
	public void fileInputStream() {
		try {
			FileInputStream fis = new FileInputStream(CLIENT_PATH + "StandardRecord.xml");
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();
			
			System.out.println("FILE DATA: ret:" + ret + "\n" + new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// FileOutputStream
	public void fileOutputStream() {
		
	}

	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////

	// to get the session socket to the server
	private void initClient() {
		try {
			// to get the socket connected to server
			socket = new Socket(IP, PORT);
			
			System.out.println("socket connect to Server.....\n");

			// make input/output stream
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// print ReqStd.xml
	private void printReqStd() {
		try {
			// read the req file
			String[] FILE = null;
			FILE = FILE01;
			
			FileInputStream fis = new FileInputStream(CLIENT_PATH + FILE[0]);
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();

			if (ret <= 0) {
				throw new IOException("fis.read(data) error (-1)");
			}
			
			byte[] len = new DecimalFormat("0000").format(ret).getBytes();
			
			System.out.print("REQ FILE DATA : ");
			System.out.print("  NAME:" + FILE[0]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data, 0, ret));
			System.out.println("---------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Parser the XML : Std -> Div
	private void parserStd2Div() {
		try {
			String[] FILE = null;
			FILE = FILE01;
			
			Source xmlSource = new StreamSource(CLIENT_PATH + FILE[0]);
			Source xslSource = new StreamSource(CLIENT_PATH + FILE[1]);
			Result xmlResult = new StreamResult(CLIENT_PATH + FILE[2]);
			// Result outResult = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", new Integer(2));
			Transformer transformer = transformerFactory.newTransformer(xslSource);
			
			if (transformer != null) {
				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}
			
			transformer.transform(xmlSource, xmlResult);
			// transformer.transform(xmlSource, outResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// send the request xml file to the server
	private void sendReqXml() {
		try {
			// read the req file
			String[] FILE = null;
			FILE = FILE01;
			
			FileInputStream fis = new FileInputStream(CLIENT_PATH + FILE[2]);
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();

			if (ret <= 0) {
				throw new IOException("fis.read(data) error (-1)");
			}
			
			byte[] len = new DecimalFormat("0000").format(ret).getBytes();
			
			System.out.print("REQ FILE DATA : ");
			System.out.print("  NAME:" + FILE[2]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data, 0, ret));
			System.out.println("---------------------------------------");
			
			// send length to server
			send(len, 0, 4);
			System.out.println("REQ SEND LEN:" + new String(len));
			
			// send data to server
			send(data, 0, ret);
			System.out.println("REQ SEND DATA:\n" + new String(data));
			System.out.println("---------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// recv the response xml file from the server
	private void recvResXml() {
		try {
			// recv length from server
			byte[] len = recv(4);
			System.out.println("RES RECV LEN:" + new String(len));
			
			// get size from length
			int size = Integer.parseInt(new String(len));
			System.out.println("RES RECV SIZE:" + size);
			
			// recv data from server
			byte[] data = recv(size);
			System.out.println("RES RECV DATA:\n" + new String(data));
			System.out.println("---------------------------------------");

			// write a res file
			String[] FILE = null;
			FILE = FILE03;
			
			FileOutputStream fos = new FileOutputStream(CLIENT_PATH + FILE[0]);
			fos.write(data);
			fos.close();

			System.out.print("RES FILE DATA : ");
			System.out.print("  NAME:" + FILE[0]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data));
			System.out.println("---------------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Parser the XML : Div -> Std
	private void parserDiv2Std() {
		try {
			String[] FILE = null;
			FILE = FILE03;
			
			Source xmlSource = new StreamSource(CLIENT_PATH + FILE[0]);
			Source xslSource = new StreamSource(CLIENT_PATH + FILE[1]);
			Result xmlResult = new StreamResult(CLIENT_PATH + FILE[2]);
			// Result outResult = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", new Integer(2));
			Transformer transformer = transformerFactory.newTransformer(xslSource);
			
			if (transformer != null) {
				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}
			
			transformer.transform(xmlSource, xmlResult);
			// transformer.transform(xmlSource, outResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// print ResStd.xml
	private void printResStd() {
		try {
			// read the req file
			String[] FILE = null;
			FILE = FILE03;
			
			FileInputStream fis = new FileInputStream(CLIENT_PATH + FILE[2]);
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();

			if (ret <= 0) {
				throw new IOException("fis.read(data) error (-1)");
			}
			
			byte[] len = new DecimalFormat("0000").format(ret).getBytes();
			
			System.out.print("RES FILE DATA : ");
			System.out.print("  NAME:" + FILE[2]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data, 0, ret));
			System.out.println("---------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// close
	private void closeClient() {
		try {
			dis.close();
			dos.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Client Main Processing
	public void execute() {
		// get the session socket to the server
		initClient();
		
		// print ResStd.xml
		printReqStd();
		
		// parser 1
		parserStd2Div();
		
		// send the request xml file to the server
		sendReqXml();
		
		// recv the response xml file from the server
		recvResXml();
		
		// parser 2
		parserDiv2Std();
		
		// print ResStd.xml
		printResStd();
		
		// close
		closeClient();
	}
	
	// Start Entry function
	public static void main(String[] args) {
		new KangClient().execute();
	}
}
