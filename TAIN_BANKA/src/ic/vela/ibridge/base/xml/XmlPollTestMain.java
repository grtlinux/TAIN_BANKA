package ic.vela.ibridge.base.xml;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;


/*==================================================================*/
/**
 * XmlPollTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlPollTestMain
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 
 * - polling 전문은 
 * 
 */
/*==================================================================*/
public class XmlPollTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";

	private static final String        POLL_SEND_SEC       = "60";
	private static final String        POLL_LOOP_COUNT     = "100";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private CharsetEncoder             encoder             = null;  // String -> Byte
	private CharsetDecoder             decoder             = null;  // Byte -> String

	private String                     strFepid            = null;
	
	private int                        pollSendSec         = 0;
	private int                        pollLoopCount       = 0;
	
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public XmlPollTestMain()
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * 송수신용 charset 객체를 정의한다.
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				/*
				 * Properties 파일을 읽는다.
				 */
				this.strFepid = System.getProperty("system.ib.param.poll.fepid");

				String strHostIp = System.getProperty("system.ib.param.poll.host.ip");
				String strHostPort = System.getProperty("system.ib.param.poll.host.port");
				int intHostPort = Integer.parseInt(strHostPort);
				
				String strPollSendSec = System.getProperty("system.ib.param.poll.send.sec", POLL_SEND_SEC);
				String strPollLoopCount = System.getProperty("system.ib.param.poll.loop.count", POLL_LOOP_COUNT);
				this.pollSendSec = Integer.parseInt(strPollSendSec);
				this.pollLoopCount = Integer.parseInt(strPollLoopCount);
				
				if (flag) {
					System.out.println("strFepid         = [" + strFepid         + "]");
					System.out.println("strHostIp        = [" + strHostIp        + "]");
					System.out.println("strHostPort      = [" + strHostPort      + "]");
					System.out.println("strPollSendSec   = [" + strPollSendSec   + "]");
					System.out.println("strPollLoopCount = [" + strPollLoopCount + "]");
				}
				
				this.socket = new Socket(strHostIp, intHostPort);
				
				if (flag) System.out.println("POLLING : socket connection to Server....." + socket);

				/*
				 *  make input/output stream
				 */
				this.dis = new DataInputStream(socket.getInputStream());
				this.dos = new DataOutputStream(socket.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * socket receiver
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

		if (socket == null)
			return null;
		
		byte[] buf = new byte[size];

		if (flag) {
			int ret = 0;
			int readed = 0;
			
			socket.setSoTimeout(0);
			while (readed < size) {
				ret = dis.read(buf, readed, size - readed);
				
				if (ret == 0) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {}
				} else if (ret < 0) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {}
					throw new IOException("recv EOF (-1)");
				} else {
					socket.setSoTimeout(0);
				}
				
				readed += ret;
			}
		}
		
		return buf;
	}
	
	/*==================================================================*/
	/**
	 * socket sender
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
		
		if (socket == null)
			return;
		
		if (flag) {
			dos.write(buf, off, len);
			dos.flush();
		}
	}
	
	/*==================================================================*/
	/**
	 * execute
	 */
	/*------------------------------------------------------------------*/
	public void execute()
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * polling 전문을 주기적으로 서버로 보낸다.
				 */
				for (int i=0; i < this.pollLoopCount; i++) {
					/*
					 * 반복적으로 송수신한다.
					 */
					
					if (flag) {
						/*
						 * polling 전문을 생성한다.
						 */
						String strPollReqXml = XmlPoll.get(strFepid, "T");
						
						if (!flag) {
							/*
							 * 요청전문을 송신전문으로 Encoding 한다.
							 */
							CharBuffer charPollResStreamXml = CharBuffer.wrap(strPollReqXml);
							@SuppressWarnings("unused")
							ByteBuffer bytePollReqStreamBuffer = encoder.encode(charPollResStreamXml);
						}

						/*
						 * polling 전문을 송신한다.
						 */
						byte[] bytePollReqXml = strPollReqXml.getBytes(CHARSET);
						
						if (flag) System.out.println(String.format("POLLING REQ Before SEND [%04d][%s]", i, strPollReqXml));
						send(bytePollReqXml, 0, bytePollReqXml.length);
						if (flag) System.out.println(String.format("POLLING REQ After  SEND [%04d][%s]", i, strPollReqXml));
					}
					
					if (flag) {
						/*
						 * polling 전문을 수신한다.
						 */
						if (flag) System.out.println(String.format("POLLING RES Before RECV [%04d]", i));
						byte[] bytePollResXml = recv(160);
						if (bytePollResXml.length == 160) {
							/*
							 * 수신한 전문을 출력한다.
							 */
							String strPollResXml = new String(bytePollResXml, CHARSET);
							if (flag) System.out.println(String.format("POLLING RES After  RECV [%04d][%s]", i, strPollResXml));
						}
						
						if (!flag) {
							/*
							 * 수신전문을 응답전문으로 Decoding 한다.
							 */
							ByteBuffer bytePollResBuffer = null;
							CharBuffer charPollResBuffer = decoder.decode(bytePollResBuffer);
							@SuppressWarnings("unused")
							String strPollResXml = charPollResBuffer.toString();
						}
					}
					
					if (flag) System.out.println("------------------------------------------------------------");
					
					if (flag) {
						/*
						 * 잠시 기다린다.
						 */
						try {
							Thread.sleep(pollSendSec * 1000);
						} catch (InterruptedException e) {}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * Host close
		 */
		if (flag) {
			try {
				/*
				 *  close the session socket and stream in/out
				 */
				if (socket != null) {
					dis.close();
					dos.close();
					socket.close();
					
					socket = null;
				}
			
				if (flag) System.out.println("POLLING : close Socket.....");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * 날짜연산
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			new XmlPollTestMain().execute();
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
