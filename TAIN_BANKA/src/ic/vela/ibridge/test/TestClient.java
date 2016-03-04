package ic.vela.ibridge.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @name   TestClient
 * @author Kang Seok
 * @date   2013.05.28
 *
 */
public class TestClient {

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
	private String host = null;
	private int port = 0;
	private String direct = null;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 생성자 : socket을 넘겨 받는다.
	 */
	public TestClient() {
		host = System.getProperty("system.property.host", HOST);
		port = Integer.parseInt(System.getProperty("system.property.port", PORT));
		direct = System.getProperty("system.property.direct", "SendRecv");
	}

	/*
	 * 
	 */
	public void execute() {
		try {
			// to get the socket connected to server
			socket = new Socket(host, port);
			
			println("> " + socket + " : 연결됨");

			// make input/output stream
			OutputStream toServer = socket.getOutputStream();
			InputStream fromServer = socket.getInputStream();

			if ("SendRecv".equals(direct) || "Send".equals(direct))
			{
				// 메시지 송신
				byte[] msg = "Thank you for your acception. 감사합니다.\r\n".getBytes("EUC-KR");
				toServer.write(msg);
				print("    SEND > " + new String(msg, "EUC-KR"));
			}

			if ("SendRecv".equals(direct) || "Recv".equals(direct))
			{
				// 메시지 수신
				byte[] buf = new byte[1024];
				int count = fromServer.read(buf);
				print("    RECV (" + count + ") > " + new String(buf, 0, count, "EUC-KR"));
			}
			
			toServer.close();
			fromServer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			println("> " + socket + " : 연결종료됨");

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
	public static void main(String[] args) {
		new TestClient().execute();
	}
}
