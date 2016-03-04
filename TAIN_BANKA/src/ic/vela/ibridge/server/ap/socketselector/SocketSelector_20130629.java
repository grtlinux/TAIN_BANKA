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
				
				/*
				 * Properties ���Ͽ��� Host IP, Port �� ��´�.
				 */
				this.strHostIp = prop.getProperty("system.ib.param.host.ip", HOST_IP);
				this.strHostPort = prop.getProperty("system.ib.param.host.port", HOST_PORT);
				
				/*
				 * ����� Queue�� ��´�.
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
	private void tryConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			selector = Selector.open();
			
			for (int tryCnt = 1; tryCnt < 5 * 60 * 6; tryCnt ++) {  // 5�ð��̻� �ݺ�
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
			 * AttachObject�� �����ϰ� Thread.start() �Ѵ�.
			 */
			//SocketChannelAttachObject attachObject = new SocketChannelAttachObject("KANG SEOK");
			SocketChannelAttachObject attachObject = new SocketChannelAttachObject(null);
			attachObject.start();
			
			/*
			 * AttachObject�� �����Ѵ�.
			 */
			selectionKey.attach(attachObject);
			
			if (flag) Logger.log("CLIENT : connect to the server. : " + socketChannel);
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
	 * �۽�
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
				int length = send(byteReqStreamBuffer);
				if (length > 0) {
					byteReqStreamBuffer.rewind();
					
					if (flag) Logger.log(String.format("REQ �۽ſϷ� [length=%05d][%s]", length, strReqStreamXml));
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
	private void keyReadableEvent(SocketChannelAttachObject attachObject) throws Exception
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
			int length = recv(byteResHeaderBuffer, false);
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
				length = recv(byteResBodyBuffer, true);
				if (length >= 0) {  // length = 0 : ��������
					
					byteResBodyBuffer.flip();
					
					/*
					 * ���������� ������������ Decoding �Ѵ�.
					 */
					CharBuffer charResBodyBuffer = decoder.decode(byteResBodyBuffer);
					String strResBodyXml = charResBodyBuffer.toString();

					//String strResStreamXml = new String(byteResHeader) + strResBodyXml;
					String strResStreamXml = new String(byteResHeader) + strResBodyXml.trim();
					
					if (!flag) Logger.log("6.SocketSelector-RES [length=" + (length+160) + "][" + strResStreamXml + "]");
					if (flag) Logger.log(String.format("RES ���ſϷ� [length=%05d][%s]", (length+160), strResStreamXml));
					
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
	private void socketChannelEvent(SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * socketChannel �� ��� �ۼ����� ó���Ѵ�.
			 */
			socketChannel = (SocketChannel) selectableChannel;
			
			/*
			 * �ۼ���ó�� ��ü�� ��´�.
			 */
			SocketChannelAttachObject object = (SocketChannelAttachObject) key.attachment();

			if (!flag) Logger.log("\t# SocketChannel [" + object.getName() + "]");
			
			/*
			 * ����
			 */
			if (key.isConnectable()) {
				/*
				 * key.isConnectable
				 */
				keyConnectableEvent(object);
			}
			
			/*
			 * �۽�
			 */
			if (key.isWritable()) {
				/*
				 * key.isWritable
				 */
				keyWritableEvent(object);
			}

			/*
			 * ����
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
						 *  selector �� �غ�ܰ踦 ��ٸ���.
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
							selectableChannel = key.channel();
							
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
