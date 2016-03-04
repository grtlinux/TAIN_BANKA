package ic.vela.ibridge.server.ap.socketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * SocketSelector 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketSelector
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class SocketSelector_20130629 extends Thread
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	private static final String        HOST_IP             = "127.0.0.1";
	private static final String        HOST_PORT           = "2345";
	
	@SuppressWarnings("unused")
	private static final int SERVER_SELECTION_KEY  = SelectionKey.OP_ACCEPT;
	private static final int SOCKET_SELECTION_KEY  = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private CharsetEncoder             encoder             = null;
	private CharsetDecoder             decoder             = null;
	
	private String                     strHostIp           = null;
	private String                     strHostPort         = null;
	
	private Selector                   selector            = null;
	private SocketChannel              socketChannel       = null;
	private SelectableChannel          selectableChannel   = null;
	
	private boolean                    errFlag            = false;
	
	private int                        selectCount         = 0;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public SocketSelector_20130629()
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * Encoding / Decoding 을 처리할 객체를 생성
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();

				/*
				 * Properties 파일명을 얻는다.
				 */
				String strParamFile = IBridgeParameter.getParam("ap.cfg.prop");
				
				Properties prop = new Properties();
				prop.load(new InputStreamReader(new FileInputStream(strParamFile), CHARSET));
				if (!flag) prop.list(System.out);
				
				/*
				 * Properties 파일에서 Host IP, Port 를 얻는다.
				 */
				this.strHostIp = prop.getProperty("system.ib.param.host.ip", HOST_IP);
				this.strHostPort = prop.getProperty("system.ib.param.host.port", HOST_PORT);
				
				/*
				 * 사용할 Queue를 얻는다.
				 */
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param byteBuffer
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private int recv(ByteBuffer byteBuffer, boolean flagBlock) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		int iLen = 0;
		int iReaded = 0;
		int iCount = 0;
		int iMaxCount = 8;
		
		if (flag) {
			
			while (byteBuffer.hasRemaining() && iCount < iMaxCount) {
				// 첨 일부자료를 읽었는데 1초 동안 나머지를 읽지 못하면
				// 그냥 return 한다. 즉 원하는 길이의 자료는 못 얻는다.
				// 에러처리 또는 로그처리를 한다.
				
				iReaded = socketChannel.read(byteBuffer);
				
				if (iReaded < 0) {
					throw new IOException("channel READ EOF...");
				}
				
				if (iReaded == 0) {
					
					if (!flagBlock && iLen == 0) {
						// non blocking 모드 처리를 위해
						// 읽을 자료가 없으면 무조건 return 한다.
						return 0;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					
					iCount ++;
				}
				else {
					iCount = 0;
				}
				
				iLen += iReaded;
			}
		}
		
		return iLen;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param byteBuffer
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private int send(ByteBuffer byteBuffer) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		int iLen = 0;
		int iWritten = 0;
		int iCount = 0;
		int iMaxCount = 10;
		
		if (!flag) {
			Logger.log("############### send (byteBuffer) info = " + byteBuffer);
			iLen = socketChannel.write(byteBuffer);
		}
		
		if (flag) {
			
			while (byteBuffer.hasRemaining() && iCount < iMaxCount) {
				
				iWritten = socketChannel.write(byteBuffer);
				
				if (iWritten < 0) {
					throw new IOException("channel WRITE EOF...");
				}
				
				if (iWritten == 0) {
					if (iLen == 0) {
						// non blocking 모드 처리를 위해
						// 쓸 자료가 없으면 무조건 return 한다.
						return 0;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					
					iCount ++;
				}
				else {
					iCount = 0;
				}
				
				iLen += iWritten;
			}
		}
		
		return iLen;
	}

	/*==================================================================*/
	/**
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void tryConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			selector = Selector.open();
			
			for (int tryCnt = 1; tryCnt < 5 * 60 * 6; tryCnt ++) {  // 5시간이상 반복
				try {
					socketChannel = SocketChannel.open(new InetSocketAddress(strHostIp, Integer.parseInt(strHostPort)));
					break;
				} catch (Exception e) {
					//e.printStackTrace();
					if (flag) Logger.log("CLIENT : try to connect to the server.....[TRYCNT=" + tryCnt + "]");
				}
				
				// wait for the next connection
				try {
					Thread.sleep(10000);   // 10 seconds
				} catch (InterruptedException e) {}
			}
			
			socketChannel.configureBlocking(false);
			SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
			
			/*
			 * AttachObject를 생성하고 Thread.start() 한다.
			 */
			//SocketChannelAttachObject attachObject = new SocketChannelAttachObject("KANG SEOK");
			SocketChannelAttachObject attachObject = new SocketChannelAttachObject(null);
			attachObject.start();
			
			/*
			 * AttachObject를 연결한다.
			 */
			selectionKey.attach(attachObject);
			
			if (flag) Logger.log("CLIENT : connect to the server. : " + socketChannel);
		}
	}
	
	/*==================================================================*/
	/**
	 * 접속
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyConnectableEvent(SocketChannelAttachObject object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (!flag) Logger.log("\t\t# SocketChannel [" + object.getName() + "].isConnectable()");
			
			if (socketChannel.isConnectionPending()) {
				socketChannel.finishConnect();
				socketChannel.close();
				
				throw new IOException("\t\t\tCLIENT - Connection : close the session..");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 송신
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyWritableEvent(SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.23
			/*
			 * 요청전문송신 : IB -> AP
			 */

			/*
			 *  요청전문을 읽는다.
			 */
			String strReqStreamXml = (String) attachObject.getSendQueue().get(100);  // 0.1초 멈칫한다.
			if (strReqStreamXml != null) {
				
				if (!flag) Logger.log("5.SocketSelector-REQ strReqStreamXml [" + strReqStreamXml + "]");

				/*
				 * 요청전문을 송신전문으로 Encoding 한다.
				 */
				CharBuffer charResStreamXml = CharBuffer.wrap(strReqStreamXml);
				ByteBuffer byteReqStreamBuffer = encoder.encode(charResStreamXml);
				
				/*
				 *  요청전문을 송신한다.
				 */
				int length = send(byteReqStreamBuffer);
				if (length > 0) {
					byteReqStreamBuffer.rewind();
					
					if (flag) Logger.log(String.format("REQ 송신완료 [length=%05d][%s]", length, strReqStreamXml));
				}
			} else {
				// 요청전문이 없다.
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 수신
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyReadableEvent(SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.23
			/*
			 * 응답전문수신 : IB <- AP
			 */

			/*
			 *  응답전문 HEADER 를 저장버퍼을 만든다.
			 */
			ByteBuffer byteResHeaderBuffer = ByteBuffer.allocate(160);  // StreamXML 의 Header 길이 160
			byteResHeaderBuffer.clear();

			/*
			 *  HEADER 를 읽는다. Non Blocking 모드
			 */
			int length = recv(byteResHeaderBuffer, false);
			if (length > 0) {
				byteResHeaderBuffer.flip();
				
				byte[] byteResHeader = byteResHeaderBuffer.array();
				
				String strHDR_DOC_LEN    = new String(byteResHeader, 12, 5); // 전문전체길이 문자열 00000

				/*
				 * BODY 의 길이를 구한다.
				 */
				length = Integer.parseInt(strHDR_DOC_LEN) - 160;  // body = length - header
				if (!flag) Logger.log("6.SocketSelector-RES HEADER [Body length=" + length + "][" + new String(byteResHeader) + "]");

				ByteBuffer byteResBodyBuffer = ByteBuffer.allocate(length);
				
				/*
				 * BODY 를 읽는다.  Blocking 모드
				 */
				length = recv(byteResBodyBuffer, true);
				if (length >= 0) {  // length = 0 : 개시전문
					
					byteResBodyBuffer.flip();
					
					/*
					 * 수신전문을 응답전문으로 Decoding 한다.
					 */
					CharBuffer charResBodyBuffer = decoder.decode(byteResBodyBuffer);
					String strResBodyXml = charResBodyBuffer.toString();

					//String strResStreamXml = new String(byteResHeader) + strResBodyXml;
					String strResStreamXml = new String(byteResHeader) + strResBodyXml.trim();
					
					if (!flag) Logger.log("6.SocketSelector-RES [length=" + (length+160) + "][" + strResStreamXml + "]");
					if (flag) Logger.log(String.format("RES 수신완료 [length=%05d][%s]", (length+160), strResStreamXml));
					
					/*
					 *  응답전문을 큐에 넣는다.
					 */
					attachObject.getRecvQueue().put(strResStreamXml);
				}
			}
			else {
				// 응답전문이 없다.
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param key
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void socketChannelEvent(SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * socketChannel 을 얻고 송수신을 처리한다.
			 */
			socketChannel = (SocketChannel) selectableChannel;
			
			/*
			 * 송수신처리 객체를 얻는다.
			 */
			SocketChannelAttachObject object = (SocketChannelAttachObject) key.attachment();

			if (!flag) Logger.log("\t# SocketChannel [" + object.getName() + "]");
			
			/*
			 * 접속
			 */
			if (key.isConnectable()) {
				/*
				 * key.isConnectable
				 */
				keyConnectableEvent(object);
			}
			
			/*
			 * 송신
			 */
			if (key.isWritable()) {
				/*
				 * key.isWritable
				 */
				keyWritableEvent(object);
			}

			/*
			 * 수신
			 */
			if (key.isReadable()) {
				/*
				 * key.isReadable
				 */
				keyReadableEvent(object);
			}
		}
	}

	/*==================================================================*/
	/**
	 * test method
	 */
	/*------------------------------------------------------------------*/
	public void run()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.13
			
			while (!errFlag) {
				try {
					
					/*
					 *  try to connect to server
					 */
					tryConnection();
					
					while (flag) {
						/*
						 *  selector 가 준비단계를 기다린다.
						 */
						int selectSize = selector.select();
						if (selectSize <= 0) {
							break;
						}
						
						if (!flag) {
							if (flag) Logger.log("============ " + selectCount + " START ============");
							;
						}

						/*
						 *  selector 의 selectedKey 들을 얻는다.
						 */
						Set<SelectionKey> setKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterKeys = setKeys.iterator();
						
						// selectSize 갯수 만큼 반복한다.
						while (iterKeys.hasNext()) {
							
							/*
							 *  selected 된 개별 key 를 얻는다.
							 */
							SelectionKey key = iterKeys.next();
							iterKeys.remove();
							
							/*
							 *  selectableChannel 을 얻는다.
							 */
							selectableChannel = key.channel();
							
							/*
							 *  서버의 ServerSocketChannel 인지 확인
							 */
							if (selectableChannel instanceof ServerSocketChannel) 
							{
								// serverSocketChannelEvent
							}
							/*
							 *  클라이언트의 SocketChannel 인지 확인
							 */
							else if (selectableChannel instanceof SocketChannel) 
							{
								// socketChannelEvent
								socketChannelEvent(key);
								
							}
							else if (selectableChannel instanceof DatagramChannel) 
							{
								// datagramChannelEvent
							}
							else if (selectableChannel instanceof Pipe.SinkChannel) 
							{
								// pipeSinkChannelEvent
							}
							else if (selectableChannel instanceof Pipe.SourceChannel) 
							{
								// pipeSourceChannelEvent
							}
							else 
							{
								// otherEvent
							}
							
						}   // while (iterKeys.hasNext())
						
						if (flag) {
							if (!flag) Logger.log("============ " + selectCount + " END ============");
							selectCount ++;
							
							if (flag) {
								// wait for a short time
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {}
							}
						}
						
					}   // while (selector.select() > 0)
					
				} catch (IOException e) {
					// close the session because the server closed
					if (flag) Logger.log(e.getMessage());

					if (flag) {
						// wait for a shot time after the session close
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {}
					}
				} catch (Exception e) {
					// if other Exception, break the 
					if(flag) Logger.log(e.getMessage());
					break;
				}
			}
			
			if (flag) Logger.log("IB : 프로그램을 종료합니다.");
		}
	}

	/*==================================================================*/
	/*------------------------------------------------------------------*/

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.22
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*==================================================================*/
	/**
	 * default test
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
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
