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
 * SocketSelector Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketSelector
 * @author  ����
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
				 * Encoding / Decoding �� ó���� ��ü�� ����
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();

				/*
				 * Properties ���ϸ��� ��´�.
				 */
				String strParamFile = IBridgeParameter.getParam("ib.cfg.prop");
				
				Properties prop = new Properties();
				prop.load(new InputStreamReader(new FileInputStream(strParamFile), CHARSET));
				if (!flag) prop.list(System.out);
				
				if (flag) {
					/////////////////////////////////////////////////////////////
					/*
					 * Properties ���Ͽ��� connect �� IP, Port �� ��Ī�� ��´�.
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
							 * polling ������ ������ 
							 */
							if (strPoll == null) {
								strPoll = "no 0 0";
							}
							
							/*
							 * null �� �ƴϸ� �����Ѵ�.
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
		int iMaxCount = 8;
		
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
					 * ������ Ȯ���Ͽ� ���ӵ� ����̸�
					 * ������ �õ����� �ʴ´�.
					 */
					if (!conn.getFlagConnected()) {
						/*
						 * �����ӻ����̱� ������ ������ �õ��Ѵ�.
						 */
						try {
							/*
							 * TODO DATE.2013.07.25 : Socket.connect�� ������ �ʿ���.
							 */
							SocketChannel socketChannel = null;
							if (!flag) {
								/*
								 * Socket.getChannel�� �̿��Ͽ� �����Ѵ�.
								 * connection timeout�� �����ϱ� ���ؼ� �����.
								 */
								@SuppressWarnings("resource")
								Socket socket = new Socket();
								socket.connect(new InetSocketAddress(conn.getStrConnHostIp(), conn.getiConnHostPort()), TRYCONNECT_TIMEOUT);
								socketChannel = socket.getChannel();
							} else {
								/*
								 * SocketChannel�� ���� �����Ѵ�.
								 */
								socketChannel = SocketChannel.open(new InetSocketAddress(conn.getStrConnHostIp(), conn.getiConnHostPort()));
							}
							
							socketChannel.configureBlocking(false);  // non blocking
							SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
							
							/*
							 * AttachObject�� �����ϰ� Thread.start() �Ѵ�.
							 */
							SocketChannelAttachObject attachObject = new SocketChannelAttachObject(conn);
							attachObject.start();
							
							/*
							 * AttachObject�� �����Ѵ�.
							 */
							selectionKey.attach(attachObject);
							
							/*
							 * �����÷��׸� true �� �����Ѵ�.
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
	 * ����
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
	 * �۽�
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
			 * ��û�����۽� : IB -> AP
			 */

			/*
			 *  ��û������ �д´�.
			 */
			String strReqStreamXml = (String) attachObject.getSendQueue().get(100);  // 0.1�� ��ĩ�Ѵ�.
			if (strReqStreamXml != null) {
				
				if (!flag) Logger.log("5.SocketSelector-REQ strReqStreamXml [" + strReqStreamXml + "]");

				/*
				 * ��û������ �۽��������� Encoding �Ѵ�.
				 */
				CharBuffer charResStreamXml = CharBuffer.wrap(strReqStreamXml);
				ByteBuffer byteReqStreamBuffer = encoder.encode(charResStreamXml);
				
				/*
				 *  ��û������ �۽��Ѵ�.
				 */
				int length = send(socketChannel, byteReqStreamBuffer);
				if (length > 0) {
					byteReqStreamBuffer.rewind();
					
					if (!flag) Logger.log("5.SocketSelector-REQ strReqStreamXml [" + strReqStreamXml + "]");
					if (flag) Logger.log(String.format("\nREQ �۽ſϷ� [length=%05d][%s]", length, strReqStreamXml));
					
				}
			} else {
				// ��û������ ����.
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * ����
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
			 * ������������ : IB <- AP
			 */

			/*
			 *  �������� HEADER �� ��������� �����.
			 */
			ByteBuffer byteResHeaderBuffer = ByteBuffer.allocate(160);  // StreamXML �� Header ���� 160
			byteResHeaderBuffer.clear();

			/*
			 *  HEADER �� �д´�. Non Blocking ���
			 */
			int length = recv(socketChannel, byteResHeaderBuffer, false);
			if (length > 0) {
				byteResHeaderBuffer.flip();
				
				byte[] byteResHeader = byteResHeaderBuffer.array();
				
				String strHDR_DOC_LEN    = new String(byteResHeader, 12, 5); // ������ü���� ���ڿ� 00000

				/*
				 * BODY �� ���̸� ���Ѵ�.
				 */
				length = Integer.parseInt(strHDR_DOC_LEN) - 160;  // body = length - header
				if (!flag) Logger.log("6.SocketSelector-RES HEADER [Body length=" + length + "][" + new String(byteResHeader) + "]");

				ByteBuffer byteResBodyBuffer = ByteBuffer.allocate(length);
				
				/*
				 * BODY �� �д´�.  Blocking ���
				 */
				length = recv(socketChannel, byteResBodyBuffer, true);
				if (length >= 0) {  // length = 0 : ��������
					
					byteResBodyBuffer.flip();
					
					/*
					 * ���������� ������������ Decoding �Ѵ�.
					 */
					CharBuffer charResBodyBuffer = decoder.decode(byteResBodyBuffer);
					String strResBodyXml = charResBodyBuffer.toString();

					/*
					 * TODO DATE.2013.07.18 : strResBodyXml.trim() -> ���� : strResBodyXml
					 */
					String strResStreamXml = new String(byteResHeader) + strResBodyXml;
					//String strResStreamXml = new String(byteResHeader) + strResBodyXml.trim();
					
					if (!flag) Logger.log("6.SocketSelector-RES [length=" + (length+160) + "][" + strResStreamXml + "]");
					if (flag) Logger.log(String.format("\nRES ���ſϷ� [length=%05d][%s]", (length+160), strResStreamXml));
					
					/*
					 *  ���������� ť�� �ִ´�.
					 */
					attachObject.getRecvQueue().put(strResStreamXml);
				}
			}
			else {
				// ���������� ����.
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
			 * socketChannel �� ��� �ۼ����� ó���Ѵ�.
			 */
			SocketChannel socketChannel = (SocketChannel) selectableChannel;
			
			/*
			 * �ۼ���ó�� ��ü�� ��´�.
			 */
			SocketChannelAttachObject attachObject = (SocketChannelAttachObject) key.attachment();

			if (!flag) Logger.log("\t# SocketChannel [" + attachObject.getName() + "]");
			
			try {  // TODO DATE.2013.07.01 ���ο��� ����ó��
				
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
				 * �۽�
				 */
				if (key.isWritable()) {
					/*
					 * key.isWritable
					 */
					keyWritableEvent(socketChannel, attachObject);
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

			} catch (IOException e) {
				//e.printStackTrace();
				// TODO DATE.2013.07.01 ���ο��� ����ó��
				/*
				 * SocketHashMapConnect ��ü�� ��´�.
				 */
				SocketHashMapConnect conn = attachObject.getHashMapConnect();
				
				if (flag) Logger.log("CLIENT : ������������ [%s:%s]", conn.getStrConnName(), conn.getStrConnFepId());
				
				/*
				 * ������ ����� ���� attachObject �� �����Ѵ�.
				 */
				attachObject.setErrFlag(true);

				/*
				 * socket channel�� �����Ѵ�.
				 */
				socketChannel.finishConnect();
				socketChannel.close();
				
				/*
				 * ���ӻ��� FALSE �� �����Ͽ� �ٽ� ���� �õ��� �� �� �ֵ��� �Ѵ�.
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
				 * errFlag = false �̹Ƿ� ���μ����� �����Ѵ�.
				 */
				for (int i=0;!errFlag; i=(i+1) % 50) {   // 0,1...99 �ݺ�
					
					if (i == 0) {
						/*
						 * try to connect to server
						 */
						tryMultiConnection();
					}

					if (!flag) System.exit(0);
					
					try {
						/*
						 * ���ӿ� ���� �̺�Ʈ ó���� �Ѵ�.
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
						 *  selector �� selectedKey ���� ��´�.
						 */
						Set<SelectionKey> setKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterKeys = setKeys.iterator();
						
						// selectSize ���� ��ŭ �ݺ��Ѵ�.
						while (iterKeys.hasNext()) {
							
							/*
							 *  selected �� ���� key �� ��´�.
							 */
							SelectionKey key = iterKeys.next();
							iterKeys.remove();
							
							/*
							 *  selectableChannel �� ��´�.
							 */
							SelectableChannel selectableChannel = key.channel();
							
							/*
							 *  ������ ServerSocketChannel ���� Ȯ��
							 */
							if (selectableChannel instanceof ServerSocketChannel) 
							{
								// serverSocketChannelEvent
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
							else 
							{
								// otherEvent
							}
							
						}   // while (iterKeys.hasNext())
						
					} catch (IOException e) {
						/*
						 * ���� ������ ����
						 * ���迡 ���� ó���� �Ѵ�.
						 * TODO DATE.2013.07.01 : socketChannelEvent method ���ο��� ó��
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
						 * ��Ÿ Exception
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
			
			if (flag) Logger.log("IB : ���α׷��� �����մϴ�.");
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
