package ic.vela.ibridge.server.ap.serversocketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.util.XmlTool;

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
 * Server Socket Selector 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketSelector
 * @author  강석
 * @version 1.0, 2013/06/13
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class ServerSocketSelector extends Thread
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	private static final int           MAX_CONNECTION      = 100;
	
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
	
	private int                        selectorSize        = 0;
	private int                        selectCount         = 0;
	
	private HashMap<String,ServerSocketHashMapListen>   hashMapListen = null;
	private HashMap<String,ServerSocketHashMapAllow>    hashMapAllow  = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketSelector()
	/*------------------------------------------------------------------*/
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
				
				if (flag) {
					/////////////////////////////////////////////////////////////
					/*
					 * Properties 파일에서 Listen 할 Port 와 명칭을 얻는다.
					 */
					hashMapListen = new HashMap<String,ServerSocketHashMapListen> ();
					
					for (int i=1; i < 100; i++) {
						String strIndex = String.format("%02d", i);
						String strListenName = prop.getProperty("system.ib.param.listen." + strIndex + ".name");
						String strListenPort = prop.getProperty("system.ib.param.listen." + strIndex + ".port");
						
						if (strListenName != null) {
							/*
							 * null 이 아니면 저장한다.
							 */
							ServerSocketHashMapListen listen = new ServerSocketHashMapListen(strListenName, strListenPort);
							
							hashMapListen.put(strIndex, listen);
						}
					}
					
					if (!flag) {
						System.out.println("< ServerSocketHashMapListen >");
						
						Set<String> setKeys = hashMapListen.keySet();
						Iterator<String> iterKeys = setKeys.iterator();
						while (iterKeys.hasNext()) {
							String key = iterKeys.next();
							
							ServerSocketHashMapListen listen = (ServerSocketHashMapListen) hashMapListen.get(key);
							
							if (flag) {
								System.out.println("[" + key + "] [[strListenName =" + listen.getStrListenName() + "]");
								System.out.println("[" + key + "] [[strListenPort =" + listen.getStrListenPort() + "]");
								System.out.println("[" + key + "] [[iListenPort   =" + listen.getiListenPort()   + "]");
							}
						}
						
						if (!flag) System.exit(0);
					}
				}
				
				if (flag) {
					/////////////////////////////////////////////////////////////
					/*
					 * Properties 파일에서 접속을 accessable IP 와 명칭을 얻는다.
					 */
					hashMapAllow = new HashMap<String,ServerSocketHashMapAllow> ();
					
					for (int i=1; i < 100; i++) {
						String strIndex = String.format("%02d", i);
						String strAllowName = prop.getProperty("system.ib.param.allow." + strIndex + ".name");
						String strAllowIp   = prop.getProperty("system.ib.param.allow." + strIndex + ".host.ip");
						
						if (strAllowName != null) {
							/*
							 * null 이 아니면 저장한다.
							 */
							ServerSocketHashMapAllow listen = new ServerSocketHashMapAllow(strAllowName, strAllowIp);
							
							//hashMapAllow.put(strIndex, listen);  // KEY <- strIndex
							hashMapAllow.put(strAllowIp, listen);  // KEY <- strAllowIp
						}
					}
					
					if (!flag) {
						System.out.println("< ServerSocketHashMapAllow >");
						
						Set<String> setKeys = hashMapAllow.keySet();
						Iterator<String> iterKeys = setKeys.iterator();
						while (iterKeys.hasNext()) {
							String key = iterKeys.next();
							
							ServerSocketHashMapAllow allow = (ServerSocketHashMapAllow) hashMapAllow.get(key);
							
							if (flag) {
								System.out.println("[" + key + "] [strAllowName =" + allow.getStrAllowName() + "]");
								System.out.println("[" + key + "] [strAllowIp   =" + allow.getStrAllowIp()   + "]");
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
		int iMaxCount = 20;
		
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
		int iMaxCount = 20;
		
		if (!flag) {  // TO DO : 테스트를 위함
			System.out.println("############### send (byteBuffer) info = " + byteBuffer);
			System.out.println("############### send (byteBuffer) = " + new String(byteBuffer.array(),0,5));
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
	 * MultiListen
	 */
	/*------------------------------------------------------------------*/
	private void tryMultiListen()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.29
			
			try {
				
				Set<String> setKeys = hashMapListen.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					
					String key = iterKeys.next();
					ServerSocketHashMapListen listen = (ServerSocketHashMapListen) hashMapListen.get(key);
					
					/*
					 * 접속을 확인하여 접속된 경우이면
					 * 접속을 시도하지 않는다.
					 */
					if (!listen.getFlagListened()) {
						/*
						 * 미접속상태이기 때문에 접속을 시도한다.
						 */
						try {
							ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
							serverSocketChannel.configureBlocking(false);  // non blocking
							serverSocketChannel.socket().bind(new InetSocketAddress(listen.getiListenPort()));
							SelectionKey selectionKey = serverSocketChannel.register(selector, SERVER_SELECTION_KEY);
							
							/*
							 * ServerSocketChannelAttachObject를 생성 한다.
							 */
							ServerSocketChannelAttachObject attachObject = new ServerSocketChannelAttachObject(listen);
							//attachObject.start();  // Thread를 실행하지 않는다.

							/*
							 * AttachObject를 연결한다.
							 */
							selectionKey.attach(attachObject);
							
							/*
							 * 접속플래그를 true 로 세팅한다.
							 */
							listen.setFlagListened(true);

							if (flag) Logger.log("SERVER : SUCCESS LISTEN. [%s][PORT:%d]", listen.getStrListenName(), listen.getiListenPort());

						} catch (Exception e) {
							e.printStackTrace();
							if (flag) Logger.log("SERVER : ERROR LISTEN. [%s][PORT:%d]", listen.getStrListenName(), listen.getiListenPort());
						}
						
					}  // if (!listen.getFlagListened())
					
				}  // while (iterKeys.hasNext())
				
			} catch (Exception e) {
				e.printStackTrace();
				
				if (flag) System.exit(0);
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean keyConnectableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (!flag) Logger.log("\t\t# SocketChannel [" + attachObject.getName() + "].isConnectable()");
			
			if (socketChannel.isConnectionPending()) {
				if (!flag) Logger.log("SERVER : SocketChannel.isConnectionPending() [%s]"
						, attachObject.getHashMapListen().getStrListenName());

				socketChannel.finishConnect();
				socketChannel.close();
				
				return false;
			}
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean keyReadableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.20
			
			/*
			 * 요청전문을 수신한다. : IB -> AP
			 */

			/*
			 *  요청전문 HEADER 를 저장버퍼을 만든다.
			 */
			ByteBuffer byteReqHeaderBuffer = ByteBuffer.allocate(160);  // StreamXML 의 Header 길이 160
			byteReqHeaderBuffer.clear();

			if (!flag) Logger.log("KANG : FEP 읽을 준비가 되었습니다.");
			/*
			 *  HEADER 를 읽는다. Non Blocking 모드
			 */
			int length = recv(socketChannel, byteReqHeaderBuffer, false);
			if (length > 0) {
				byteReqHeaderBuffer.flip();
				
				byte[] byteReqHeader = byteReqHeaderBuffer.array();
				
				String strHDR_DOC_LEN    = new String(byteReqHeader, 12, 5); // 전문전체길이 문자열 00000

				/*
				 * BODY 의 길이를 구한다.
				 */
				length = Integer.parseInt(strHDR_DOC_LEN) - 160;  // body = length - header
				if (flag) {
					if (!flag) Logger.log("1.SocketSelector-REQ HEADER [Body length=" + length + "][" + new String(byteReqHeader) + "]");
					if (!flag) System.out.println("1.SocketSelector-REQ HEADER [Body length=" + length + "][" + new String(byteReqHeader) + "]");
				}

				ByteBuffer byteReqBodyBuffer = ByteBuffer.allocate(length);
				
				/*
				 * BODY 를 읽는다.  Blocking 모드
				 */
				length = recv(socketChannel, byteReqBodyBuffer, true);
				if (length >= 0) {  // length = 0 : 개시전문
					
					byteReqBodyBuffer.flip();
					
					/*
					 * 수신전문을 요청전문으로 Decoding 한다.
					 */
					CharBuffer charReqBodyBuffer = decoder.decode(byteReqBodyBuffer);
					String strReqBodyXml = charReqBodyBuffer.toString();

					//String strReqStreamXml = new String(byteReqHeader) + strReqBodyXml;
					String strReqStreamXml = new String(byteReqHeader) + strReqBodyXml.trim();
					
					if (!flag) Logger.log("1.SocketSelector-REQ [length=" + (length+160) + "][" + strReqStreamXml + "]");
					
					/*
					 *  요청전문을 큐에 넣는다.
					 */
					int cnt = attachObject.getSendQueue().put(strReqStreamXml);

					if (flag) {
						String strKey = XmlTool.getStreamKey(strReqStreamXml);
						if (flag) Logger.log("\n================================ [START-REQ:%s] ====================================="
								+ "\nREQ 수신완료 [length=%05d][%s]", strKey, (length+160), strReqStreamXml);

						if (!flag) {
							System.out.println("1.SocketSelector-REQ [cnt=" + cnt + "][length=" + (length+160) + "][" + strReqStreamXml + "]");
						}
					}
				}
			}
			else {
				// 요청전문이 없다.
			}
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean keyWritableEvent(SocketChannel socketChannel, SocketChannelAttachObject attachObject) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.20
			
			/*
			 * 응답전문송신 : IB <- AP
			 */

			/*
			 *  응답전문을 읽는다.
			 */
			String strResStreamXml = (String) attachObject.getRecvQueue().get(100);  // 0.1초 멈칫한다.
			if (strResStreamXml != null) {
				
				/*
				 * 응답전문을 송신전문으로 Encoding 한다.
				 */
				CharBuffer charResStreamXml = CharBuffer.wrap(strResStreamXml);
				ByteBuffer byteResStreamBuffer = encoder.encode(charResStreamXml);
				
				/*
				 *  응답전문을 송신한다.
				 */
				int length = send(socketChannel, byteResStreamBuffer);
				if (length > 0) {
					byteResStreamBuffer.rewind();

					if (!flag) Logger.log("9.SocketSelector-RES strResStreamXml [%s]\n"
							+ "=========================================================================================="
							, strResStreamXml);

					if (!flag) Logger.log("RES 송신완료 [length=%05d][%s]"
							+ "\n=========================================================================================="
							, length, strResStreamXml);

					String strKey = XmlTool.getStreamKey(strResStreamXml);
					if (flag) Logger.log("\nRES 송신완료 [length=%05d][%s]"
							+ "\n================================ [END-RES:%s] ====================================="
							, length, strResStreamXml, strKey);
				}
			} else {
				// 응답전문이 없다.
			}
		}

		return true;
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
			SocketChannel socketChannel = (SocketChannel) selectableChannel;
			
			SocketChannelAttachObject attachObject = (SocketChannelAttachObject) key.attachment();

			if (!flag) Logger.log("\t# SocketChannel [" + attachObject.getName() + "]");
			
			try {
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
				 * 수신
				 */
				if (key.isReadable()) {
					/*
					 * key.isReadable
					 */
					keyReadableEvent(socketChannel, attachObject);
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
				
			} catch (IOException e) {
				//e.printStackTrace();
				// TODO DATE.2013.07.01 내부에서 세션처리
				/*
				 * SocketHashMapConnect 객체를 얻는다.
				 */
				ServerSocketHashMapAllow allow = attachObject.getHashMapAllow();
				
				if (flag) Logger.log("SERVER : 클라이언트 접속종료 [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());
				
				/*
				 * 세션이 끊기면 관련 attachObject 를 종료한다.
				 */
				attachObject.setErrFlag(true);

				/*
				 * socket channel을 종료한다.
				 */
				socketChannel.finishConnect();
				socketChannel.close();
			}
		}
	}

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/**
	 * 
	 * @param key
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void serverSocketChannelEvent(SelectableChannel selectableChannel, SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (!flag) Logger.log("\t# ServerSocketChannel");
			
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
			
			/*
			 * key.isAcceptable
			 */
			if (key.isAcceptable()) {
				if (!flag) Logger.log("\t\t# ServerSocketChannel.isAcceptable()");

				/*
				 * accept and client information
				 */
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel == null) {
					/*
					 * null socket channel
					 */
					if (flag) Logger.log("SERVER : null socketChannel....");
					return;
				}
				
				if (flag && selectorSize > MAX_CONNECTION) {
					/*
					 * TODO DATE.2013.07.01 특정 selector의 갯수 초과이면 deny 한다.
					 */
					if (flag) Logger.log("SERVER : 최대세션갯수 초과. [NO:%d][MAX:%d]", selectorSize, MAX_CONNECTION);
					
					socketChannel.finishConnect();
					socketChannel.close();
					return;
				}
				
				ServerSocketHashMapAllow allow = null;
				
				if (flag) {
					/*
					 * TODO DATE.2013.07.01 접근 가능한 IP를 검색한다.
					 */
					Socket socket = socketChannel.socket();
					InetSocketAddress inetSockAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
					String strIpAddr = inetSockAddress.getHostName();
					
					allow = hashMapAllow.get(strIpAddr);
					if (allow == null) {
						/*
						 * 해당 IP는 접근 권한이 없어 제한합니다.
						 */
						if (flag) Logger.log("SERVER : 접근권한없는 IP. 접근제한. [IP:%s]", strIpAddr);
						
						socketChannel.finishConnect();
						socketChannel.close();
						return;
					}
				}
				
				socketChannel.configureBlocking(false);   // non blocking
				SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
				
				/*
				 * ServerSocketChannelAttachObject 를 구한다.
				 */
				ServerSocketChannelAttachObject serverAttachObject = (ServerSocketChannelAttachObject) key.attachment();
				
				/*
				 * SocketChannelAttachObject를 생성하고 Thread.start() 한다.
				 */
				SocketChannelAttachObject attachObject = new SocketChannelAttachObject(serverAttachObject.getHashMapListen(), allow);
				attachObject.start();   // Thread를 실행한다.
				selectionKey.attach(attachObject);
				
				//if (flag) Logger.log("SERVER : SUCCESS ACCEPTED. %s", socketChannel);
				//if (flag) Logger.log("SERVER : SUCCESS ACCEPTED. [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());
				if (flag) Logger.log("SERVER : 클라이언트 접속시작 [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());

			}
		}
	}
	
	/*==================================================================*/
	/**
	 * Thread run method
	 */
	/*------------------------------------------------------------------*/
	public void run()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.13
			
			try {
				
				/*
				 *  make the server socket channel
				 */
				tryMultiListen();
				
				/*
				 *  do loop for selector processing
				 */
				while (flag) {
					
					/*
					 *  selector 가 준비단계를 기다린다.
					 */
					selectorSize = selector.select();
					if (selectorSize <= 0) {
						break;
					}
					
					if (!flag) {
						if (flag) Logger.log("============ " + selectCount + " START ============");
					}
					
					/*
					 *  to get the iterator selected key
					 *  selector 의 selectedKey 들을 얻는다.
					 */
					Set<SelectionKey> setKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterKeys = setKeys.iterator();
					
					/*
					 *  selectSize 갯수 만큼 반복한다.
					 */
					while (iterKeys.hasNext()) {
						
						/*
						 * to get the key
						 *  selected 된 개별 key 를 얻는다.
						 */
						SelectionKey key = iterKeys.next();
						iterKeys.remove();
						
						/*
						 *  selectable channel
						 *  selectableChannel 을 얻는다.
						 */
						SelectableChannel selectableChannel = key.channel();
						
						/*
						 *  서버의 ServerSocketChannel 인지 확인
						 */
						if (selectableChannel instanceof ServerSocketChannel) 
						{
							// serverSocketChannelEvent
							serverSocketChannelEvent(selectableChannel, key);
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
						else {
							// otherEvent
						}
						
					}   // while (iterKeys.hasNext())
					
					if (flag) {
						if (!flag) Logger.log("============ " + selectCount + " END ============");
						selectCount ++;
						
						if (flag) {
							// wait for a short time
							// 모든 클라이언트에 송수신하고 기다린다.
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {}
						}
					}
					
				}   // while (selector.select() > 0)
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * default method
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
				new ServerSocketSelector();
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
