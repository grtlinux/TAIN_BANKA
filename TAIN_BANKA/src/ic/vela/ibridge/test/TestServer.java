package ic.vela.ibridge.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @name   TestServer
 * @author Kang Seok
 * @date   2013.05.28
 *
 */
public class TestServer extends Thread {

	/////////////////////////////////////////////////////////////////
	/*
	 * Server Host, Port
	 */
	private static final String   HOST    = "127.0.0.1";
	private static final String   PORT    = "2345";
	
	/*
	 * 클라이언트와 연결된 포트
	 */
	private Socket socket;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 생성자 : socket을 넘겨 받는다.
	 */
	public TestServer(Socket socket) {
		this.socket = socket;
	}
	
	/*
	 * Thread 실행자
	 */
	public void run() {
		
		try {
			
			String direct = System.getProperty("system.property.direct", "RecvSend");
			
			println("    > " + socket + " : 연결됨");
			
			InputStream fromClient = socket.getInputStream();
			OutputStream toClient = socket.getOutputStream();
			
			if ("RecvSend".equals(direct) || "SendRecv".equals(direct))
			{
				// 송수신 처리
				byte[] msg = "Welcome to the world of IB. 접속을 환영합니다.\r\n".getBytes("EUC-KR");
				byte[] buf = new byte[1024];
				int count;
				
				while ((count=fromClient.read(buf)) != -1) {
					print("        RECV > " + new String(buf, 0, count, "EUC-KR"));

					toClient.write(msg);
					//toClient.write(buf, 0, count);
					
					print("        SEND > " + new String(msg, "EUC-KR"));
				}
			}
			else if ("Recv".equals(direct)) 
			{
				// 수신만
				byte[] buf = new byte[1024];
				int count;
				
				while ((count=fromClient.read(buf)) != -1) {
					print("        RECV > " + new String(buf, 0, count, "EUC-KR"));
				}
			}
			else if ("Send".equals(direct)) 
			{
				// 송신만
				byte[] msg = "Welcome to the world of IB. 접속을 환영합니다.\r\n".getBytes("EUC-KR");

				for (int i=0; i < 10; i++) {
					toClient.write(msg);
					
					print("        SEND > " + new String(msg, "EUC-KR"));
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
			}
			else
			{
				// 그외는 기다린다.
				for (int i=0; i < 10; i++) {
					println("        WAIT > " + i);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
			}
			
			toClient.close();
			fromClient.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			println("    > " + socket + " : 연결종료됨");
			
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 로그를 출력한다. 
	 */
	public static void print(String str) {
		System.out.print(str);
	}
	
	public static void println(String str) {
		print(str + "\r\n");
	}
	
	public static void print(byte[] bStr) {
		print(new String(bStr));
	}
	
	public static void println(byte[] bStr) {
		print(new String(bStr) + "\r\n");
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
		try {
			
			String host = System.getProperty("system.property.host", HOST);
			int port = Integer.parseInt(System.getProperty("system.property.port", PORT));
			
			ServerSocket serverSocket = new ServerSocket(port);
			println(serverSocket + " : 서버소켓 생성");
			
			while (true) {
				Socket socket = serverSocket.accept();
				TestServer testServer = new TestServer(socket);
				testServer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
