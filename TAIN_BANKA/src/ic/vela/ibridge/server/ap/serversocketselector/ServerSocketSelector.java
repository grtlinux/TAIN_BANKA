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
 * Server Socket Selector Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketSelector
 * @author  ����
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
				 * Encoding / Decoding �� ó���� ��ü�� ����
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();

				/*
				 * Properties ���ϸ��� ��´�.
				 */
				String strParamFile = IBridgeParameter.getParam("ap.cfg.prop");
				
				Properties prop = new Properties();
				prop.load(new InputStreamReader(new FileInputStream(strParamFile), CHARSET));
				if (!flag) prop.list(System.out);
				
				if (flag) {
					/////////////////////////////////////////////////////////////
					/*
					 * Properties ���Ͽ��� Listen �� Port �� ��Ī�� ��´�.
					 */
					hashMapListen = new HashMap<String,ServerSocketHashMapListen> ();
					
					for (int i=1; i < 100; i++) {
						String strIndex = String.format("%02d", i);
						String strListenName = prop.getProperty("system.ib.param.listen." + strIndex + ".name");
						String strListenPort = prop.getProperty("system.ib.param.listen." + strIndex + ".port");
						
						if (strListenName != null) {
							/*
							 * null �� �ƴϸ� �����Ѵ�.
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
					 * Properties ���Ͽ��� ������ accessable IP �� ��Ī�� ��´�.
					 */
					hashMapAllow = new HashMap<String,ServerSocketHashMapAllow> ();
					
					for (int i=1; i < 100; i++) {
						String strIndex = String.format("%02d", i);
						String strAllowName = prop.getProperty("system.ib.param.allow." + strIndex + ".name");
						String strAllowIp   = prop.getProperty("system.ib.param.allow." + strIndex + ".host.ip");
						
						if (strAllowName != null) {
							/*
							 * null �� �ƴϸ� �����Ѵ�.
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
				 * ����� Selector �� �����Ѵ�.
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
				// ÷ �Ϻ��ڷḦ �о��µ� 1�� ���� �������� ���� ���ϸ�
				// �׳� return �Ѵ�. �� ���ϴ� ������ �ڷ�� �� ��´�.
				// ����ó�� �Ǵ� �α�ó���� �Ѵ�.
				
				iReaded = socketChannel.read(byteBuffer);
				
				if (iReaded < 0) {
					throw new IOException("channel READ EOF...");
				}
				
				if (iReaded == 0) {
					
					if (!flagBlock && iLen == 0) {
						// non blocking ��� ó���� ����
						// ���� �ڷᰡ ������ ������ return �Ѵ�.
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
		
		if (!flag) {  // TO DO : �׽�Ʈ�� ����
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
						// non blocking ��� ó���� ����
						// �� �ڷᰡ ������ ������ return �Ѵ�.
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
					 * ������ Ȯ���Ͽ� ���ӵ� ����̸�
					 * ������ �õ����� �ʴ´�.
					 */
					if (!listen.getFlagListened()) {
						/*
						 * �����ӻ����̱� ������ ������ �õ��Ѵ�.
						 */
						try {
							ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
							serverSocketChannel.configureBlocking(false);  // non blocking
							serverSocketChannel.socket().bind(new InetSocketAddress(listen.getiListenPort()));
							SelectionKey selectionKey = serverSocketChannel.register(selector, SERVER_SELECTION_KEY);
							
							/*
							 * ServerSocketChannelAttachObject�� ���� �Ѵ�.
							 */
							ServerSocketChannelAttachObject attachObject = new ServerSocketChannelAttachObject(listen);
							//attachObject.start();  // Thread�� �������� �ʴ´�.

							/*
							 * AttachObject�� �����Ѵ�.
							 */
							selectionKey.attach(attachObject);
							
							/*
							 * �����÷��׸� true �� �����Ѵ�.
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
			 * ��û������ �����Ѵ�. : IB -> AP
			 */

			/*
			 *  ��û���� HEADER �� ��������� �����.
			 */
			ByteBuffer byteReqHeaderBuffer = ByteBuffer.allocate(160);  // StreamXML �� Header ���� 160
			byteReqHeaderBuffer.clear();

			if (!flag) Logger.log("KANG : FEP ���� �غ� �Ǿ����ϴ�.");
			/*
			 *  HEADER �� �д´�. Non Blocking ���
			 */
			int length = recv(socketChannel, byteReqHeaderBuffer, false);
			if (length > 0) {
				byteReqHeaderBuffer.flip();
				
				byte[] byteReqHeader = byteReqHeaderBuffer.array();
				
				String strHDR_DOC_LEN    = new String(byteReqHeader, 12, 5); // ������ü���� ���ڿ� 00000

				/*
				 * BODY �� ���̸� ���Ѵ�.
				 */
				length = Integer.parseInt(strHDR_DOC_LEN) - 160;  // body = length - header
				if (flag) {
					if (!flag) Logger.log("1.SocketSelector-REQ HEADER [Body length=" + length + "][" + new String(byteReqHeader) + "]");
					if (!flag) System.out.println("1.SocketSelector-REQ HEADER [Body length=" + length + "][" + new String(byteReqHeader) + "]");
				}

				ByteBuffer byteReqBodyBuffer = ByteBuffer.allocate(length);
				
				/*
				 * BODY �� �д´�.  Blocking ���
				 */
				length = recv(socketChannel, byteReqBodyBuffer, true);
				if (length >= 0) {  // length = 0 : ��������
					
					byteReqBodyBuffer.flip();
					
					/*
					 * ���������� ��û�������� Decoding �Ѵ�.
					 */
					CharBuffer charReqBodyBuffer = decoder.decode(byteReqBodyBuffer);
					String strReqBodyXml = charReqBodyBuffer.toString();

					//String strReqStreamXml = new String(byteReqHeader) + strReqBodyXml;
					String strReqStreamXml = new String(byteReqHeader) + strReqBodyXml.trim();
					
					if (!flag) Logger.log("1.SocketSelector-REQ [length=" + (length+160) + "][" + strReqStreamXml + "]");
					
					/*
					 *  ��û������ ť�� �ִ´�.
					 */
					int cnt = attachObject.getSendQueue().put(strReqStreamXml);

					if (flag) {
						String strKey = XmlTool.getStreamKey(strReqStreamXml);
						if (flag) Logger.log("\n================================ [START-REQ:%s] ====================================="
								+ "\nREQ ���ſϷ� [length=%05d][%s]", strKey, (length+160), strReqStreamXml);

						if (!flag) {
							System.out.println("1.SocketSelector-REQ [cnt=" + cnt + "][length=" + (length+160) + "][" + strReqStreamXml + "]");
						}
					}
				}
			}
			else {
				// ��û������ ����.
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
			 * ���������۽� : IB <- AP
			 */

			/*
			 *  ���������� �д´�.
			 */
			String strResStreamXml = (String) attachObject.getRecvQueue().get(100);  // 0.1�� ��ĩ�Ѵ�.
			if (strResStreamXml != null) {
				
				/*
				 * ���������� �۽��������� Encoding �Ѵ�.
				 */
				CharBuffer charResStreamXml = CharBuffer.wrap(strResStreamXml);
				ByteBuffer byteResStreamBuffer = encoder.encode(charResStreamXml);
				
				/*
				 *  ���������� �۽��Ѵ�.
				 */
				int length = send(socketChannel, byteResStreamBuffer);
				if (length > 0) {
					byteResStreamBuffer.rewind();

					if (!flag) Logger.log("9.SocketSelector-RES strResStreamXml [%s]\n"
							+ "=========================================================================================="
							, strResStreamXml);

					if (!flag) Logger.log("RES �۽ſϷ� [length=%05d][%s]"
							+ "\n=========================================================================================="
							, length, strResStreamXml);

					String strKey = XmlTool.getStreamKey(strResStreamXml);
					if (flag) Logger.log("\nRES �۽ſϷ� [length=%05d][%s]"
							+ "\n================================ [END-RES:%s] ====================================="
							, length, strResStreamXml, strKey);
				}
			} else {
				// ���������� ����.
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
				 * ����
				 */
				if (key.isConnectable()) {
					/*
					 * key.isConnectable
					 */
					keyConnectableEvent(socketChannel, attachObject);
				}
				
				/*
				 * ����
				 */
				if (key.isReadable()) {
					/*
					 * key.isReadable
					 */
					keyReadableEvent(socketChannel, attachObject);
				}
				
				/*
				 * �۽�
				 */
				if (key.isWritable()) {
					/*
					 * key.isWritable
					 */
					keyWritableEvent(socketChannel, attachObject);
				}
				
			} catch (IOException e) {
				//e.printStackTrace();
				// TODO DATE.2013.07.01 ���ο��� ����ó��
				/*
				 * SocketHashMapConnect ��ü�� ��´�.
				 */
				ServerSocketHashMapAllow allow = attachObject.getHashMapAllow();
				
				if (flag) Logger.log("SERVER : Ŭ���̾�Ʈ �������� [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());
				
				/*
				 * ������ ����� ���� attachObject �� �����Ѵ�.
				 */
				attachObject.setErrFlag(true);

				/*
				 * socket channel�� �����Ѵ�.
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
					 * TODO DATE.2013.07.01 Ư�� selector�� ���� �ʰ��̸� deny �Ѵ�.
					 */
					if (flag) Logger.log("SERVER : �ִ뼼�ǰ��� �ʰ�. [NO:%d][MAX:%d]", selectorSize, MAX_CONNECTION);
					
					socketChannel.finishConnect();
					socketChannel.close();
					return;
				}
				
				ServerSocketHashMapAllow allow = null;
				
				if (flag) {
					/*
					 * TODO DATE.2013.07.01 ���� ������ IP�� �˻��Ѵ�.
					 */
					Socket socket = socketChannel.socket();
					InetSocketAddress inetSockAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
					String strIpAddr = inetSockAddress.getHostName();
					
					allow = hashMapAllow.get(strIpAddr);
					if (allow == null) {
						/*
						 * �ش� IP�� ���� ������ ���� �����մϴ�.
						 */
						if (flag) Logger.log("SERVER : ���ٱ��Ѿ��� IP. ��������. [IP:%s]", strIpAddr);
						
						socketChannel.finishConnect();
						socketChannel.close();
						return;
					}
				}
				
				socketChannel.configureBlocking(false);   // non blocking
				SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
				
				/*
				 * ServerSocketChannelAttachObject �� ���Ѵ�.
				 */
				ServerSocketChannelAttachObject serverAttachObject = (ServerSocketChannelAttachObject) key.attachment();
				
				/*
				 * SocketChannelAttachObject�� �����ϰ� Thread.start() �Ѵ�.
				 */
				SocketChannelAttachObject attachObject = new SocketChannelAttachObject(serverAttachObject.getHashMapListen(), allow);
				attachObject.start();   // Thread�� �����Ѵ�.
				selectionKey.attach(attachObject);
				
				//if (flag) Logger.log("SERVER : SUCCESS ACCEPTED. %s", socketChannel);
				//if (flag) Logger.log("SERVER : SUCCESS ACCEPTED. [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());
				if (flag) Logger.log("SERVER : Ŭ���̾�Ʈ ���ӽ��� [%s:%s]", allow.getStrAllowName(), allow.getStrAllowIp());

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
					 *  selector �� �غ�ܰ踦 ��ٸ���.
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
					 *  selector �� selectedKey ���� ��´�.
					 */
					Set<SelectionKey> setKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterKeys = setKeys.iterator();
					
					/*
					 *  selectSize ���� ��ŭ �ݺ��Ѵ�.
					 */
					while (iterKeys.hasNext()) {
						
						/*
						 * to get the key
						 *  selected �� ���� key �� ��´�.
						 */
						SelectionKey key = iterKeys.next();
						iterKeys.remove();
						
						/*
						 *  selectable channel
						 *  selectableChannel �� ��´�.
						 */
						SelectableChannel selectableChannel = key.channel();
						
						/*
						 *  ������ ServerSocketChannel ���� Ȯ��
						 */
						if (selectableChannel instanceof ServerSocketChannel) 
						{
							// serverSocketChannelEvent
							serverSocketChannelEvent(selectableChannel, key);
						}
						/*
						 *  Ŭ���̾�Ʈ�� SocketChannel ���� Ȯ��
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
							// ��� Ŭ���̾�Ʈ�� �ۼ����ϰ� ��ٸ���.
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
