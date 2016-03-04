package ic.vela.ibridge.test.timeout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;


/*==================================================================*/
/**
 * SocketTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketTestMain
 * @author  강석
 * @version 1.0, 2013/07/25
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class SocketTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	//private static final String   IP             = "127.0.0.1";
	//private static final int      PORT           = 8888; 
	
	private Socket                socket         = null;
	private DataInputStream       dis            = null;
	private DataOutputStream      dos            = null;

	/*==================================================================*/
	/**
	 * socket recv
	 * 
	 * @param size
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private byte[] recv(final int size) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		byte[] buf = null;

		if (flag) {
			int ret = 0;
			int readed = 0;
			buf = new byte[size];
			
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
		}
		
		return buf;
	}
	
	/*==================================================================*/
	/**
	 *  socket send
	 *  
	 * @param buf
	 * @param off
	 * @param len
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void send(final byte[] buf, final int off, final int len) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			dos.write(buf, off, len);
			dos.flush();
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 *  to get the session socket to the server
	 */
	/*------------------------------------------------------------------*/
	private void initClient()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				// to get the socket connected to server
				System.out.println("socket connect-1.....\n");
				socket = new Socket();
				//Proxy.NO_PROXY;
				System.out.println(String.format("getKeepAlive=%s, getTcpNoDelay=%s, getSoTimeout=%d, getSoLinger=%d", socket.getKeepAlive(), socket.getTcpNoDelay(), socket.getSoTimeout(), socket.getSoLinger()));
				//socket.setKeepAlive(true);
				//socket.setTcpNoDelay(true);
				//socket.setSoTimeout(20*1000);
				//socket.setSoLinger(true, 5);
				System.out.println(String.format("getKeepAlive=%s, getTcpNoDelay=%s, getSoTimeout=%d, getSoLinger=%d", socket.getKeepAlive(), socket.getTcpNoDelay(), socket.getSoTimeout(), socket.getSoLinger()));
				//socket.bind(null);
				//socket.connect(new InetSocketAddress("172.30.222.3", PORT), 1000*1000);
				//socket.connect(new InetSocketAddress(IP, PORT), 1000*1000);
				//socket.connect(new InetSocketAddress("172.30.222.3", 2227), 1000*1000);
				//socket.connect(new InetSocketAddress(InetAddress.getByName("172.30.225.53"), 2227), 10*1000);  // OK
				socket.connect(new InetSocketAddress("172.30.225.43", 12345), 5*1000);  // OK
				//socket.connect(new InetSocketAddress(InetAddress.getByName("172.30.222.163"), 2227), 10*1000);  // OK
				//socket.connect(new InetSocketAddress("172.30.222.163", 12345), 5*1000);  //
				System.out.println("socket connect-3.....\n");
				
				System.out.println("socket connect to Server.....\n");

				// make input/output stream
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (SocketTimeoutException e) {
				//System.out.println("ERROR : SocketTimeoutException...");
				e.printStackTrace();
				System.exit(-1);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	/*==================================================================*/
	/**
	 *  send the request xml file to the server
	 */
	/*------------------------------------------------------------------*/
	private void sendReq()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				String strLine = "Hello, world!!!!!";
				byte[] byteLine = strLine.getBytes();
				int intSize = byteLine.length;
				
				if (flag) {
					// send length to server
					byte[] byteSize = new DecimalFormat("0000").format(intSize).getBytes();
					send(byteSize, 0, 4);
					System.out.println("REQ SEND LEN:" + new String(byteSize));
				}
				
				if (flag) {
					// send data to server
					send(byteLine, 0, intSize);
					System.out.println("REQ SEND DATA:\n" + new String(byteLine, 0, intSize));
					System.out.println("---------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 *  recv the response xml file from the server
	 */
	/*------------------------------------------------------------------*/
	private void recvRes()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {

				int intSize = 0;
				
				if (flag) {
					// recv length from server
					byte[] byteSize = recv(4);
					System.out.println("RES RECV LEN:" + new String(byteSize));
					
					// get size from length
					intSize = Integer.parseInt(new String(byteSize));
					System.out.println("RES RECV SIZE:" + intSize);
				}
				
				if (flag) {
					// recv data from server
					byte[] byteLine = recv(intSize);
					System.out.println("RES RECV DATA:\n" + new String(byteLine));
					System.out.println("---------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 *  close
	 */
	/*------------------------------------------------------------------*/
	private void closeClient()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				dis.close();
				dos.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*==================================================================*/
	/**
	 * Client Main Processing
	 */
	/*------------------------------------------------------------------*/
	private void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			// get the session socket to the server
			initClient();
			
			// send the request xml file to the server
			sendReq();
			
			// recv the response xml file from the server
			recvRes();
			
			// close
			closeClient();
		}
	}
	
	/*==================================================================*/
	/**
	 * test01 : 세션연결과 송수신 테스트
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			new SocketTestMain().execute();
		}
	}
	
	/*==================================================================*/
	/**
	 * default test : 단순한 문자 출력
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println("Default function");
		}
	}
	
	/*==================================================================*/
	/**
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		switch (1) {
		case 1:
			if (flag) {
				test01();
			}
			break;
		default:
			if (flag) {
				default_test00();
			}
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
