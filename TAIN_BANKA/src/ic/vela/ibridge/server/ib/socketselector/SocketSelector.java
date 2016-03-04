package ic.vela.ibridge.server.ib.socketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
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
import java.util.HashMap;
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
public class SocketSelector extends Thread
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";
	private static final int           TRYCONNECT_TIMEOUT  = 10000;   // milliseconds
	
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
	
	private Selector                   selector            = null;
	
	private boolean                    errFlag             = false;
	
	private int                        selectCount         = 0;

	private HashMap<String,SocketHashMapConnect>   hashMapConnect = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public SocketSelector()
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
				String strParamFile = IBridgeParameter.getParam("ib.cfg.prop");
				
				Properties prop = new Properties();
				prop.load(new InputStreamReader(new FileInputStream(strParamFile), CHARSET));
				if (!flag) prop.list(System.out);
				
				if (flag) {
					/////////////////////////////////////////////////////////////
					/*
					 * Properties 파일에서 connect 할 IP, Port 와 명칭을 얻는다.
					 */
					hashMapConnect = new HashMap<String,SocketHashMapConnect> ();
					
					for (int i=1; i < 100; i++) {
						String strIndex = String.format("%02d", i);
						
						String strConnName     = prop.getProperty("system.ib.param.host." + strIndex + ".name");
						String strConnFepId    = prop.getProperty("system.ib.param.host." + strIndex + ".fepid");
						String strConnHostIp   = prop.getProperty("system.ib.param.host." + strIndex + ".host.ip");
						String strConnHostPort = prop.getProperty("system.ib.param.host." + strIndex + ".host.port");
						String strPoll         = prop.getProperty("system.ib.param.host." + strIndex + ".poll");   // TODO DATE.2013.07.12 POLL
						
						if (strConnName != null) {
							/*
							 * TODO DATE.2013.07.12
							 * polling 정보가 없으면 
							 */
							if (strPoll == null) {
								strPoll = "no 0 0";
							}
							
							/*
							 * null 이 아니면 저장한다.
							 */
							SocketHashMapConnect connect = new SocketHashMapConnect(strConnName, strConnFepId, strConnHostIp, strConnHostPort, strPoll);
							
							hashMapConnect.put(strIndex, connect);
						}
					}
					
					if (!flag) {
						
						Set<String> setKeys = hashMapConnect.keySet();
						Iterator<String> iterKeys = setKeys.iterator();
						while (iterKeys.hasNext()) {
							String key = iterKeys.next();
							
							SocketHashMapConnect listen = (SocketHashMapConnect) hashMapConnect.get(key);
							
							if (flag) {
								System.out.println("[" + key + "] [strConnName     =" + listen.getStrConnName()     + "]");
								System.out.println("[" + key + "] [strConnFepId    =" + listen.getStrConnFepId()    + "]");
								System.out.println("[" + key + "] [strConnHostIp   =" + listen.getStrConnHostIp()   + "]");
								System.out.println("[" + key + "] [strConnHostPort =" + listen.getStrConnHostPort() + "]");
								System.out.println("[" + key + "] [iConnHostIp     =" + listen.getiConnHostPort()   + "]");
								System.out.println("[" + key + "] [strPoll         =" + listen.getStrPoll()         + "]"); // TODO : DATE.2013.07.12
								System.out.println("[" + key + "] [striPollSendCnt =" + listen.getiPollSendCnt()    + "]");
								System.out.println("[" + key + "] [striPollKillCnt =" + listen.getiPollKillCnt()    + "]");
							}
						}
						
						if (!flag) System.exit(0);
					}
				}
				
				/*
				 * 사용할 Selector 를 생성한다.
				 */
				selector = Selector.open();
				
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
	private int recv(SocketChannel socketChannel, ByteBuffer byteBuffer, boolean flagBlock) throws Exception
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
	private int send(SocketChannel socketChannel, ByteBuffer byteBuffer) throws Exception
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
	private void tryMultiConnection()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.29
			
			if (!flag) Logger.log("in tryMultiConnection()");
			
			try {
				
				Set<String> setKeys = hashMapConnect.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					SocketHashMapConnect conn = (SocketHashMapConnect) hashMapConnect.get(key);
					
					/*
					 * 접속을 확인하여 접속된 경우이면
					 * 접속을 시도하지 않는다.
					 */
					if (!conn.getFlagConnected()) {
						/*
						 * 미접속상태이기 때문에 접속을 시도한다.
						 */
						try {
							/*
							 * TODO DATE.2013.07.25 : Socket.connect로 변경이 필요함.
							 */
							SocketChannel socketChannel = null;
							if (!flag) {
								/*
								 * Socket.getChannel을 이용하여 생성한다.
								 * connection timeout을 설정하기 위해서 사용함.
								 */
								@SuppressWarnings("resource")
								Socket socket = new Socket();
								socket.connect(new InetSocketAddress(conn.getStrConnHostIp(), conn.getiConnHostPort()), TRYCONNECT_TIMEOUT);
								socketChannel = socket.getChannel();
							} else {
								/*
								 * SocketChannel을 직접 생성한다.
								 */
								socketChannel = SocketChannel.open(new InetSocketAddress(conn.getStrConnHostIp(), conn.getiConnHostPort()));
							}
							
							socketChannel.configureBlocking(false);  // non blocking
							SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
							
							/*
							 * AttachObject를 생성하고 Thread.start() 한다.
							 */
							SocketChannelAttachObject attachObject = new SocketChannelAttachObject(conn);
							attachObject.start();
							
							/*
							 * AttachObject를 연결한다.
							 */
							selectionKey.attach(attachObject);
							
							/*
							 * 접속플래그를 true 로 세팅한다.
							 */
							conn.setFlagConnected(true);

							if (flag) Logger.log("CLIENT : SUCCESS : CONNECT. [%s:%s] : %s"
									, conn.getStrConnName(), conn.getStrConnFepId(), socketChannel);

						} catch (Exception e) {
							//e.printStackTrace();
							System.out.println("ERROR : connection trying over the timeout...");
							
							if (flag) Logger.log("CLIENT : ERROR : NOT CONNECT. [%s:%s:%s]"
									, conn.getStrConnName(), conn.getStrConnHostIp(), conn.getStrConnHostPort());
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	private void keyConnectableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (!flag) Logger.log("\t\t# SocketChannel [" + attachObject.getName() + "].isConnectable()");
			
			if (!flag) {
				if (socketChannel.isConnected()) {
					if (flag) Logger.log("STATUS : socketChannel.isConnected() = TRUE");
				} else {
					if (flag) Logger.log("STATUS : socketChannel.isConnected() = FALSE");
				}
			}
			
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
	private void keyWritableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
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
				int length = send(socketChannel, byteReqStreamBuffer);
				if (length > 0) {
					byteReqStreamBuffer.rewind();
					
					if (!flag) Logger.log("5.SocketSelector-REQ strReqStreamXml [" + strReqStreamXml + "]");
					if (flag) Logger.log(String.format("\nREQ 송신완료 [length=%05d][%s]", length, strReqStreamXml));
					
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
	private void keyReadableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
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
			int length = recv(socketChannel, byteResHeaderBuffer, false);
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
				length = recv(socketChannel, byteResBodyBuffer, true);
				if (length >= 0) {  // length = 0 : 개시전문
					
					byteResBodyBuffer.flip();
					
					/*
					 * 수신전문을 응답전문으로 Decoding 한다.
					 */
					CharBuffer charResBodyBuffer = decoder.decode(byteResBodyBuffer);
					String strResBodyXml = charResBodyBuffer.toString();

					/*
					 * TODO DATE.2013.07.18 : strResBodyXml.trim() -> 원복 : strResBodyXml
					 */
					String strResStreamXml = new String(byteResHeader) + strResBodyXml;
					//String strResStreamXml = new String(byteResHeader) + strResBodyXml.trim();
					
					if (!flag) Logger.log("6.SocketSelector-RES [length=" + (length+160) + "][" + strResStreamXml + "]");
					if (flag) Logger.log(String.format("\nRES 수신완료 [length=%05d][%s]", (length+160), strResStreamXml));
					
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
	private void socketChannelEvent(SelectableChannel selectableChannel, SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * socketChannel 을 얻고 송수신을 처리한다.
			 */
			SocketChannel socketChannel = (SocketChannel) selectableChannel;
			
			/*
			 * 송수신처리 객체를 얻는다.
			 */
			SocketChannelAttachObject attachObject = (SocketChannelAttachObject) key.attachment();

			if (!flag) Logger.log("\t# SocketChannel [" + attachObject.getName() + "]");
			
			try {  // TODO DATE.2013.07.01 내부에서 세션처리
				
				/*
				 * 접속
				 */
				if (key.isConnectable()) {
					/*
					 * key.isConnectable
					 */
					keyConnectableEvent(socketChannel, attachObject);
				}
				
				/*
				 * 송신
				 */
				if (key.isWritable()) {
					/*
					 * key.isWritable
					 */
					keyWritableEvent(socketChannel, attachObject);
				}

				/*
				 * 수신
				 */
				if (key.isReadable()) {
					/*
					 * key.isReadable
					 */
					keyReadableEvent(socketChannel, attachObject);
				}

			} catch (IOException e) {
				//e.printStackTrace();
				// TODO DATE.2013.07.01 내부에서 세션처리
				/*
				 * SocketHashMapConnect 객체를 얻는다.
				 */
				SocketHashMapConnect conn = attachObject.getHashMapConnect();
				
				if (flag) Logger.log("CLIENT : 서버접속종료 [%s:%s]", conn.getStrConnName(), conn.getStrConnFepId());
				
				/*
				 * 세션이 끊기면 관련 attachObject 를 종료한다.
				 */
				attachObject.setErrFlag(true);

				/*
				 * socket channel을 종료한다.
				 */
				socketChannel.finishConnect();
				socketChannel.close();
				
				/*
				 * 접속상태 FALSE 를 세팅하여 다시 접속 시도를 할 수 있도록 한다.
				 */
				conn.setFlagConnected(false);
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
		
		if (flag) {   // DATE.2013.06.29
			
			while (!errFlag) {
				/*
				 * errFlag = false 이므로 프로세싱을 시작한다.
				 */
				for (int i=0;!errFlag; i=(i+1) % 50) {   // 0,1...99 반복
					
					if (i == 0) {
						/*
						 * try to connect to server
						 */
						tryMultiConnection();
					}

					if (!flag) System.exit(0);
					
					try {
						/*
						 * 접속에 대한 이벤트 처리를 한다.
						 */
						int selectSize = selector.select(100);
						if (selectSize <= 0) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {}
							
							continue;
						}

						if (!flag) {
							if (flag) Logger.log("============ %d:%d START ============"
									, selectCount, selectSize);
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
							SelectableChannel selectableChannel = key.channel();
							
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
								socketChannelEvent(selectableChannel, key);
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
						
					} catch (IOException e) {
						/*
						 * 소켓 접속이 끊김
						 * 끊김에 대한 처리를 한다.
						 * TODO DATE.2013.07.01 : socketChannelEvent method 내부에서 처리
						 */
						if (flag) Logger.log(e.getMessage());

						if (flag) {
							// wait for a shot time after the session close
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e1) {}
						}
					} catch (Exception e) {
						/*
						 * 기타 Exception
						 */
						// if other Exception, break the 
						if (flag) Logger.log(e.getMessage());
						
						errFlag = true;
						
						break;
					}
					
					if (flag) {
						/*
						 * wait for a second
						 */
						if (!flag) Logger.log("============ " + selectCount + " END ============");
						selectCount ++;
						
						if (flag) {
							/*
							 *  wait for a short time
							 */
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {}
						}
					}
					
				}   // for (int i=0;; i=(i+1) % 100)
				
			}  // while (!errFlag)
			
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
				new SocketSelector();
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
