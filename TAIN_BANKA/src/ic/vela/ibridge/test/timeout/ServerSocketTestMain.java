package ic.vela.ibridge.test.timeout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;


/*==================================================================*/
/**
 * ServerSocketTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketTestMain
 * @author  강석
 * @version 1.0, 2013/07/25
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class ServerSocketTestMain
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

	private static final int      PORT           = 8888; 
	
	private ServerSocket          serverSocket   = null;
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
	public byte[] recv(final int size) throws Exception
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
	public void send(final byte[] buf, final int off, final int len) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			dos.write(buf, off, len);
			dos.flush();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 *  get the session socket from the client
	 */
	/*------------------------------------------------------------------*/
	private void initServer()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				// make serversocket..
				serverSocket = new ServerSocket(PORT);
				serverSocket.setReuseAddress(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 *  recv the request xml file from the client
	 */
	/*------------------------------------------------------------------*/
	private void recvReq()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {

				int intSize = 0;
				
				if (flag) {
					// recv length from server
					byte[] byteSize = recv(4);
					System.out.println("REQ RECV LEN:" + new String(byteSize));
					
					// get size from length
					intSize = Integer.parseInt(new String(byteSize));
					System.out.println("REQ RECV SIZE:" + intSize);
				}
				
				if (flag) {
					// recv data from server
					byte[] byteLine = recv(intSize);
					System.out.println("REQ RECV DATA:\n" + new String(byteLine));
					System.out.println("---------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 *  send the response xml file to the client
	 */
	/*------------------------------------------------------------------*/
	private void sendRes()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				String strLine = "How do you do? Kang Seok.....^^";
				byte[] byteLine = strLine.getBytes();
				int intSize = byteLine.length;
				
				if (flag) {
					// send length to server
					byte[] byteSize = new DecimalFormat("0000").format(intSize).getBytes();
					send(byteSize, 0, 4);
					System.out.println("RES SEND LEN:" + new String(byteSize));
				}
				
				if (flag) {
					// send data to server
					send(byteLine, 0, intSize);
					System.out.println("RES SEND DATA:\n" + new String(byteLine, 0, intSize));
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
	private void closeServer()
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
	 *  execute
	 */
	/*------------------------------------------------------------------*/
	private void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
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
					recvReq();
					
					// send the response xml file to the client
					sendRes();

					// close
					closeServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			new ServerSocketTestMain().execute();
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
