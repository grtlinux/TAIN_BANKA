package ic.vela.ibridge.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @name   ConnectionCheck
 * @author Kang Seok
 * @date   2013.05.28
 *
 */
public class ConnectionCheck {

	/////////////////////////////////////////////////////////////////
	/*
	 * Server Host, Port
	 */
	private static final String   HOST    = "127.0.0.1";
	private static final String   PORT    = "2345";
	
	/*
	 * Ŭ���̾�Ʈ�� ����� ��Ʈ
	 */
	private Socket socket;
	private String host = null;
	private int port = 0;
	private int waitSecs = 10;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * ������ : socket�� �Ѱ� �޴´�.
	 */
	public ConnectionCheck() {
		host = System.getProperty("system.property.host", HOST);
		port = Integer.parseInt(System.getProperty("system.property.port", PORT));
		waitSecs = Integer.parseInt(System.getProperty("system.property.waitsecs", "1"));
	}

	/*
	 * 
	 */
	public void execute() {
		
		boolean flag = true;
		
		try {
			// to get the socket connected to server
			socket = new Socket(host, port);
			
			println("> " + socket + " : �����");

			// make input/output stream
			OutputStream toServer = socket.getOutputStream();
			InputStream fromServer = socket.getInputStream();

			if (!flag) 
			{
				// �޽��� �۽�
				byte[] msg = "Thank you for your acception. �����մϴ�.\r\n".getBytes("EUC-KR");
				toServer.write(msg);
				println("    SEND > " + new String(msg, "EUC-KR"));

				// �޽��� ����
				byte[] buf = new byte[1024];
				int count = fromServer.read(buf);
				println("    RECV (" + count + ") > " + new String(buf, "EUC-KR"));
			}
			
			if (flag) 
			{
				// ��ٸ���.
				try {
					Thread.sleep(waitSecs * 1000);
				} catch (InterruptedException e) {}
			}
			
			toServer.close();
			fromServer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			println("> " + socket + " : ���������");

			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * �α׸� ����Ѵ�. 
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
	public static void main(String[] args) {
		new ConnectionCheck().execute();
	}
}
