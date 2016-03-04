package ic.vela.ibridge.test.kangsvr;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public class KangServer {

	private static final String   CLIENT_PATH    = "D:/KANG/WORK/workspace/KangProj/src/ic/test/kangcli/";
	private static final String   SERVER_PATH    = "D:/KANG/WORK/workspace/KangProj/src/ic/test/kangsvr/";
	
	private static final String[] FILE01         = { "ReqStd.xml", "StdToDiv01.xsl", "ReqDiv01.xml" };
	private static final String[] FILE02         = { "ReqStd.xml", "StdToDiv02.xsl", "ReqDiv02.xml" };
	private static final String[] FILE03         = { "ResDiv01.xml", "Div01ToStd.xsl", "ResStd01.xml" };
	private static final String[] FILE04         = { "ResDiv02.xml", "Div02ToStd.xsl", "ResStd02.xml" };

	private static final String   IP             = "127.0.0.1";
	private static final int      PORT           = 8888; 
	
	private ServerSocket          serverSocket   = null;
	private Socket                socket         = null;
	private DataInputStream       dis            = null;
	private DataOutputStream      dos            = null;
	
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////

	// constructor
	public KangServer() {
		System.out.println("KangServer started....");
	}
	
	// socket recv
	public byte[] recv(final int size) throws Exception {
		int ret = 0;
		int readed = 0;
		byte[] buf = new byte[size];
		
		socket.setSoTimeout(0);
		while (readed < size) {
			ret = dis.read(buf, readed, size - readed);
			
			if (ret == 0) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}
			} else if (ret < 0) {
				throw new IOException("recv error (-1)");
			} else {
				socket.setSoTimeout(0);
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
			// make serversocket..
			serverSocket = new ServerSocket(PORT);
			serverSocket.setReuseAddress(true);

			while (true) {
				System.out.println("\nListening.....");
				
				// wait for accept... listening
				socket = serverSocket.accept();
				
				System.out.println("Accepted client socket....");
				
				// make input/output stream
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				// Recv Request
				{
					// recv length from client
					byte[] len = recv(4);
					System.out.println("RECV LEN:" + new String(len));
					
					// get size from length
					int size = Integer.parseInt(new String(len));
					System.out.println("RECV SIZE:" + size);
					
					// recv data from client
					byte[] data = recv(size);
					System.out.println("RECV DATA:" + new String(data));
				}

				// Send Response
				{
					// send length to client
					byte[] len = "0020".getBytes();
					send(len, 0, 4);
		
					System.out.println("SEND LEN:" + new String(len));
					
					// send data to client
					byte[] data = "0123456789ABCDEFGHIJ".getBytes();
					send(data, 0, 20);
		
					System.out.println("SEND DATA:" + new String(data));
				}
				
				dis.close();
				dos.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////

	// get the session socket from the client
	private void initServer() {
		try {
			// make serversocket..
			serverSocket = new ServerSocket(PORT);
			serverSocket.setReuseAddress(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// recv the request xml file from the client
	private void recvReqXml() {
		try {
			// recv length from server
			byte[] len = recv(4);
			System.out.println("REQ RECV LEN:" + new String(len));
			
			// get size from length
			int size = Integer.parseInt(new String(len));
			System.out.println("REQ RECV SIZE:" + size);
			
			// recv data from server
			byte[] data = recv(size);
			System.out.println("REQ RECV DATA:\n" + new String(data));
			System.out.println("---------------------------------------");

			// write a res file
			String[] FILE = null;
			FILE = FILE01;
			
			FileOutputStream fos = new FileOutputStream(SERVER_PATH + FILE[2]);
			fos.write(data);
			fos.close();

			System.out.print("REQ FILE DATA : ");
			System.out.print("  NAME:" + FILE[2]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data));
			System.out.println("---------------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// send the response xml file to the client
	private void sendResXml() {
		try {
			// read the req file
			String[] FILE = null;
			FILE = FILE03;
			
			FileInputStream fis = new FileInputStream(SERVER_PATH + FILE[0]);
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();

			if (ret <= 0) {
				throw new IOException("fis.read(data) error (-1)");
			}
			
			byte[] len = new DecimalFormat("0000").format(ret).getBytes();
			
			System.out.print("RES FILE DATA : ");
			System.out.print("  NAME:" + FILE[0]);
			System.out.print("  LEN:" + new String(len));
			System.out.println();
			System.out.println(new String(data, 0, ret));
			System.out.println("---------------------------------------");
			
			// send length to server
			send(len, 0, 4);
			System.out.println("RES SEND LEN:" + new String(len));
			
			// send data to server
			send(data, 0, ret);
			System.out.println("RES SEND DATA:\n" + new String(data, 0, ret));
			System.out.println("---------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// close
	private void closeServer() {
		try {
			dis.close();
			dos.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// execute
	public void execute() {
		// get the session socket from the client
		initServer();
		
		while (true) {
			try {
				System.out.println("\nListening.....");
				
				// wait for accept... listening
				socket = serverSocket.accept();
				
				System.out.println("Accepted client socket....");
				
				// make input/output stream
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				// recv the request xml file from the client
				recvReqXml();
				
				// send the response xml file to the client
				sendResXml();

				// close
				closeServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Start Entry function
	public static void main(String[] args) {
		new KangServer().execute();
	}
}
