package ic.vela.ibridge.base.xml.sendrecv;

import ic.vela.ibridge.base.xml.XmlObject_20130622;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.xml.sax.InputSource;

/*==================================================================*/
/**
 * XmlSendRecvTestMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlSendRecvTestMain
 * @author  ����
 * @version 1.0, 2013/06/19
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class XmlSendRecvTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";

	private static final int         XML_READY              = 0;
	private static final int         XML_SEND_OK            = 1;
	private static final int         XML_RECV_OK            = 2;
	
	//private static final String      HOST_IP                = "127.0.0.1";
	//private static final String      HOST_PORT              = "2345";
	private static final String      HOST_IP                = "210.216.158.157";
	private static final String      HOST_PORT              = "7960";
	@SuppressWarnings("unused")
	private static final int         SERVER_SELECTION_KEY   = SelectionKey.OP_ACCEPT;
	private static final int         SOCKET_SELECTION_KEY   = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String              strHostIp             = null;
	private String              strHostPort           = null;
	
	private Selector            selector              = null;
	private SocketChannel       socketChannel         = null;
	private SelectableChannel   selectableChannel     = null;
	
	private boolean             errFlag               = false;
	
	private int                 step                  = XML_READY;
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public XmlSendRecvTestMain()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {
			try {
				/*
				 * TODO : ���� properties �� ���߿� ó���ϱ�� �ϰ� ���� System.properties �� ó���ϱ�� �Ѵ�.
				 */
				//String strParamFile = "D:/KANG/WORK/workspace/IB_Vela.1/src/ic/vela/ibridge/base/xml/sendrecv/XmlSendRecvTestMain.properties";
				//String strParamFile = "D:/KANG/WORK/workspace/IB_Vela.1/bin/ic/vela/ibridge/base/xml/sendrecv/XmlSendRecvTestMain.properties";
				//String strParamFile = "./bin/ic/vela/ibridge/base/xml/sendrecv/XmlSendRecvTestMain.properties"; // ERROR
				//String strParamFile = "ic.vela.ibridge.base.xml.sendrecv.XmlSendRecvTestMain.properties";  // ERROR
				String strParamFile = "./bin/ic/vela/ibridge/base/xml/sendrecv/XmlSendRecvTestMain.properties";
				
				Properties prop = new Properties();
				prop.load(new InputStreamReader(new FileInputStream(strParamFile), CHARSET));
				if (flag) prop.list(System.out);
				
				this.strHostIp = prop.getProperty("system.ib.param.host.ip", HOST_IP);
				this.strHostPort = prop.getProperty("system.ib.param.host.port", HOST_PORT);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (flag) println("HOST information : [" + this.strHostIp + ":" + this.strHostPort + "]");
		}
		
		if (flag) {
			try {
				this.strHostIp = System.getProperty("system.ib.param.host.ip", HOST_IP);
				this.strHostPort = System.getProperty("system.ib.param.host.port", HOST_PORT);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (flag) println("HOST information : [" + this.strHostIp + ":" + this.strHostPort + "]");
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
			System.out.println("############### send (byteBuffer) info = " + byteBuffer);
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
					if (flag) println("CLIENT��� : try to connect to the server.....[TRYCNT=" + tryCnt + "]");
				}
				
				// wait for the next connection
				try {
					Thread.sleep(10000);   // 10 seconds
				} catch (InterruptedException e) {}
			}
			
			socketChannel.configureBlocking(false);
			SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
			
			selectionKey.attach("KANG SEOK");  // TODO : �����۾� �ʿ�
			
			if (flag) println("CLIENT��� : connect to the server. : " + socketChannel);
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyConnectableEvent(String object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (!flag) println("\t\t# SocketChannel [" + object + "].isConnectable()");
			
			if (socketChannel.isConnectionPending()) {
				socketChannel.finishConnect();
				socketChannel.close();
				
				throw new IOException("\t\t\tCLIENT - Connection : close the session..");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyWritableEvent(String object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.15
			/**
			 * ��û�����۽� : FEP -> EXT
			 * SelectorSendQueue ���� ��û������ �а� EXT�� �����Ѵ�.
			 * 
			 */
			StringBuffer sb = new StringBuffer();
			
			//sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\n");
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			
			sb.append("<Bancassurance><HeaderArea>                \n");
			sb.append("  <HDR_TRX_ID></HDR_TRX_ID>                \n");
			sb.append("  <HDR_SYS_ID>BAS</HDR_SYS_ID>             \n");
			sb.append("  <HDR_DOC_LEN>1128</HDR_DOC_LEN>          \n");
			sb.append("  <HDR_DAT_GBN>T</HDR_DAT_GBN>             \n");
			sb.append("  <HDR_INS_ID>L01</HDR_INS_ID>             \n");
			sb.append("  <HDR_BAK_CLS>02</HDR_BAK_CLS>            \n");
			sb.append("  <HDR_BAK_ID>269</HDR_BAK_ID>             \n");
			sb.append("  <HDR_DOC_CODE>0200</HDR_DOC_CODE>        \n");
			sb.append("  <HDR_BIZ_CODE>073600</HDR_BIZ_CODE>      \n");
			sb.append("  <HDR_TRA_FLAG>1</HDR_TRA_FLAG>           \n");
			sb.append("  <HDR_DOC_STATUS>1</HDR_DOC_STATUS>       \n");
			sb.append("  <HDR_RET_CODE>000</HDR_RET_CODE>         \n");
			sb.append("  <HDR_SND_DATE>20130619</HDR_SND_DATE>    \n");
			sb.append("  <HDR_SND_TIME>110232</HDR_SND_TIME>      \n");
			sb.append("  <HDR_BAK_DOCSEQ>00000279</HDR_BAK_DOCSEQ>\n");
			sb.append("  <HDR_INS_DOCSEQ></HDR_INS_DOCSEQ>        \n");
			sb.append("  <HDR_TXN_DATE>20130619</HDR_TXN_DATE>    \n");
			sb.append("  <HDR_TOT_DOC>01</HDR_TOT_DOC>            \n");
			sb.append("  <HDR_CUR_DOC>01</HDR_CUR_DOC>            \n");
			sb.append("  <HDR_AGT_CODE>GUEST00851</HDR_AGT_CODE>  \n");
			sb.append("  <HDR_BAK_EXT></HDR_BAK_EXT>              \n");
			sb.append("  <HDR_INS_EXT></HDR_INS_EXT>              \n");
			sb.append("</HeaderArea><BusinessArea>                \n");
			sb.append("  <B_CONT_NO>160278090</B_CONT_NO>         \n");
			sb.append("  <SENRET_RESN_NAME/>                      \n");
			sb.append("  <APPL_ARV_DATE/>                         \n");
			sb.append("  <SCAN_PROC_DATE/>                        \n");
			sb.append("  <CONTA_TEL/>                             \n");
			
			//sb.append("  <CONTR_NM>�����</CONTR_NM>              \n");
			sb.append("  <CONTR_NM/>                              \n");
			
			sb.append("  <CONT_STS/>                              \n");
			sb.append("  <CONT_STS_DATE/>                         \n");
			sb.append("  <SP_COD_GB/>                             \n");
			sb.append("  <OPT_INF>                                \n");
			sb.append("    <OPTION_KEY></OPTION_KEY>              \n");
			sb.append("    <OPTION_VAL></OPTION_VAL>              \n");
			sb.append("  </OPT_INF>                               \n");
			sb.append("  <RTCD/>                                  \n");
			sb.append("  <MSG200/>                                \n");
			sb.append("</BusinessArea></Bancassurance>            \n");

			// ��û������ �д´�.
			String strReqSoapXml = sb.toString();
			
			if (strReqSoapXml != null) {
				
				// ��û������ SoapXML -> StreamXML ��ȯ�Ѵ�.
				XmlObject_20130622 xmlObject = new XmlObject_20130622();
				xmlObject.transferSoapXmlToStreamXml(strReqSoapXml);
				String strReqStreamXml = xmlObject.getStreamXml();
				if (!flag) println("5.SocketSelector-REQ StreamXMl [" + strReqStreamXml + "]");
				
				ByteBuffer byteReqStreamBuffer = ByteBuffer.wrap(strReqStreamXml.getBytes());
				if (!flag) println("5.SocketSelector-REQ StreamXMl [byteReqBuffer=" + byteReqStreamBuffer + "]");
				
				// ��û������ EXT�� �۽��Ѵ�.
				int length = send(byteReqStreamBuffer);
				if (length > 0) {
					byteReqStreamBuffer.rewind();
					
					if (flag) println("5.SocketSelector-REQ SoapXML[" + strReqSoapXml + "]");
					if (flag) println("5.SocketSelector-REQ StreamXML [" + strReqStreamXml + "]");
				}
			}
		}
		
		if (flag) {
			step = XML_SEND_OK;
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void keyReadableEvent(String object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/**
			 * ������������ : FEP <- EXT
			 * EXT�κ��� ���������� �����Ͽ� SelectorRecvQueue �� �ѱ��.
			 * 
			 */

			// ���������� �����Ѵ�.
			ByteBuffer byteResHeaderBuffer = ByteBuffer.allocate(160);  // StreamXML �� Header ���� 160
			byteResHeaderBuffer.clear();

			// HEADER �� �д´�
			int length = recv(byteResHeaderBuffer, false);
			if (length > 0) {
				byteResHeaderBuffer.flip();
				
				byte[] byteResHeader = byteResHeaderBuffer.array();
				
				String strHDR_DOC_LEN    = new String(byteResHeader, 12, 5); // ������ü���� ���ڿ� 00000

				// body �� ���̸� ���Ѵ�.
				length = Integer.parseInt(strHDR_DOC_LEN) - 160;  // body = length - header
				if (flag) println("6.SocketSelector-RES Header [length=" + length + "][" + new String(byteResHeader) + "]");  // TODO DATE.2013.06.19 : �ѱ�XML ó���� ��������� ���

				ByteBuffer byteResBodyBuffer = ByteBuffer.allocate(length);
				
				// body �� �д´�.
				length = recv(byteResBodyBuffer, true);
				if (flag) {
					// TODO DATE.2013.06.19 : �ѱ� XML ó���� ���� DEBUG
					println("########## KANG DATE.2013.06.19 : �ѱ� XML ó���� ���� DEBUG");
					println("6.SocketSelector-RES Body [length=" + length + "]");
					
					byte[] byteResBody = byteResBodyBuffer.array();
					println("6.SocketSelector-RES Body [" + new String(byteResBody, 0, length) + "]");
				}
				
				if (length >= 0) {
					
					byteResBodyBuffer.flip();
					
					byte[] byteResBody = byteResBodyBuffer.array();

					String strResStreamXml = new String(byteResHeader) + new String(byteResBody).trim();
					if (!flag) println("##### RES [" + strResStreamXml + "] #####");  // TODO : �׽�Ʈ
					
					// ��û������ StreamXML -> SoapXML ��ȯ�Ѵ�.
					XmlObject_20130622 xmlObject = new XmlObject_20130622();
					xmlObject.transferStreamXmlToSoapXml(strResStreamXml);
					String strResSoapXml = xmlObject.getSoapXml();

					if (!flag) println("6.SocketSelector-RES [length=" + length + "] [" + strResSoapXml + "]");
				}
			}
			else {
				// Ŭ���̾�Ʈ�κ��� �ö�� ��û������ ����.
			}
		}
		
		if (flag) {
			step = XML_RECV_OK;
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
			socketChannel = (SocketChannel) selectableChannel;
			
			String object = (String) key.attachment();

			if (!flag) println("\t# SocketChannel [" + object + "]");
			
			if (step == XML_READY && key.isConnectable()) {
				/*
				 * key.isConnectable
				 */
				keyConnectableEvent(object);
			}
			if (step == XML_READY && key.isWritable()) {
				/*
				 * key.isWritable
				 */
				keyWritableEvent(object);
			}
			if (step == XML_SEND_OK && key.isReadable()) {
				/*
				 * key.isReadable
				 */
				keyReadableEvent(object);
			}
			
			if (flag && step == XML_RECV_OK) {
				throw new IllegalArgumentException("���α׷� ����");
			}
		}
	}

	/*==================================================================*/
	/**
	 * test method
	 */
	/*------------------------------------------------------------------*/
	public void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.13
			
			while (!errFlag) {
				try {
					
					// try to connect to server
					tryConnection();
					
					while (flag) {
						// selector �� �غ�ܰ踦 ��ٸ���.
						int selectSize = selector.select();
						if (selectSize <= 0) {
							break;
						}
						
						// selector �� selectedKey ���� ��´�.
						Set<SelectionKey> setKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterKeys = setKeys.iterator();
						
						// selectSize ���� ��ŭ �ݺ��Ѵ�.
						while (iterKeys.hasNext()) {
							
							// selected �� ���� key �� ��´�.
							SelectionKey key = iterKeys.next();
							iterKeys.remove();
							
							// selectableChannel �� ��´�.
							selectableChannel = key.channel();
							
							// ������ ServerSocketChannel ���� Ȯ��
							if (selectableChannel instanceof ServerSocketChannel) 
							{
								// serverSocketChannelEvent
							}
							// Ŭ���̾�Ʈ�� SocketChannel ���� Ȯ��
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
							// wait for a short time
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {}
						}
						
					}   // while (selector.select() > 0)
					
				} catch (IOException e) {
					// close the session because the server closed
					if (flag) println(e.getMessage());

					if (flag) {
						// wait for a shot time after the session close
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {}
					}
				} catch (Exception e) {
					// if other Exception, break the 
					e.printStackTrace();
					break;
				}
			}
			
			if (flag) println("KANG_TEST: ���α׷��� �����մϴ�.");
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * system output printf  format
	 * @param format
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private void printf(String format, Object... args) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			String logMsg = String.format(format, args);
			
			println(logMsg);
		}
		else {
			System.out.printf(format, args);
		}
	}

	/*==================================================================*/
	/**
	 * log println
	 * @param msg
	 */
	/*------------------------------------------------------------------*/
	private void println(String msg) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			print(msg + "\n");
		}
	}
	
	/*==================================================================*/
	/**
	 * log print
	 * @param msg
	 */
	/*------------------------------------------------------------------*/
	private void print(String msg) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			log(msg);
		}
	}

	/*==================================================================*/
	/**
	 * log to the queue
	 * @param str
	 */
	/*------------------------------------------------------------------*/
	private void log(String str)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			System.out.print(str);
		}
	}

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
		
		if (flag) {
			try {
				StringBuffer sb = new StringBuffer();
				
				sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\n");
				sb.append("<Bancassurance><HeaderArea>                \n");
				sb.append("  <HDR_TRX_ID></HDR_TRX_ID>                \n");
				sb.append("  <HDR_SYS_ID>BAS</HDR_SYS_ID>             \n");
				sb.append("  <HDR_DOC_LEN>1128</HDR_DOC_LEN>          \n");
				sb.append("  <HDR_DAT_GBN>T</HDR_DAT_GBN>             \n");
				sb.append("  <HDR_INS_ID>L01</HDR_INS_ID>             \n");
				sb.append("  <HDR_BAK_CLS>02</HDR_BAK_CLS>            \n");
				sb.append("  <HDR_BAK_ID>269</HDR_BAK_ID>             \n");
				sb.append("  <HDR_DOC_CODE>0200</HDR_DOC_CODE>        \n");
				sb.append("  <HDR_BIZ_CODE>073600</HDR_BIZ_CODE>      \n");
				sb.append("  <HDR_TRA_FLAG>1</HDR_TRA_FLAG>           \n");
				sb.append("  <HDR_DOC_STATUS>1</HDR_DOC_STATUS>       \n");
				sb.append("  <HDR_RET_CODE>000</HDR_RET_CODE>         \n");
				sb.append("  <HDR_SND_DATE>20130619</HDR_SND_DATE>    \n");
				sb.append("  <HDR_SND_TIME>110232</HDR_SND_TIME>      \n");
				sb.append("  <HDR_BAK_DOCSEQ>00000279</HDR_BAK_DOCSEQ>\n");
				sb.append("  <HDR_INS_DOCSEQ></HDR_INS_DOCSEQ>        \n");
				sb.append("  <HDR_TXN_DATE>20130619</HDR_TXN_DATE>    \n");
				sb.append("  <HDR_TOT_DOC>01</HDR_TOT_DOC>            \n");
				sb.append("  <HDR_CUR_DOC>01</HDR_CUR_DOC>            \n");
				sb.append("  <HDR_AGT_CODE>GUEST00851</HDR_AGT_CODE>  \n");
				sb.append("  <HDR_BAK_EXT></HDR_BAK_EXT>              \n");
				sb.append("  <HDR_INS_EXT></HDR_INS_EXT>              \n");
				sb.append("</HeaderArea><BusinessArea>                \n");
				sb.append("  <B_CONT_NO>160278090</B_CONT_NO>         \n");
				sb.append("  <SENRET_RESN_NAME/>                      \n");
				sb.append("  <APPL_ARV_DATE/>                         \n");
				sb.append("  <SCAN_PROC_DATE/>                        \n");
				sb.append("  <CONTA_TEL/>                             \n");
				sb.append("  <CONTR_NM>�����</CONTR_NM>              \n");
				sb.append("  <CONT_STS/>                              \n");
				sb.append("  <CONT_STS_DATE/>                         \n");
				sb.append("  <SP_COD_GB/>                             \n");
				sb.append("  <OPT_INF>                                \n");
				sb.append("    <OPTION_KEY></OPTION_KEY>              \n");
				sb.append("    <OPTION_VAL></OPTION_VAL>              \n");
				sb.append("  </OPT_INF>                               \n");
				sb.append("  <RTCD/>                                  \n");
				sb.append("  <MSG200/>                                \n");
				sb.append("</BusinessArea></Bancassurance>            \n");

				// ��û������ �д´�.
				String strReqSoapXml = sb.toString();
				
				if (!flag) System.out.println("[" + strReqSoapXml + "]");
				
				if (!flag) {
					/*
					 * XML ������ encoding �� Ȯ���Ѵ�.
					 */
					
					InputSource inputSource = new InputSource(new ByteArrayInputStream(strReqSoapXml.getBytes()));
					
					if (flag) {
						inputSource.setEncoding("EUC-KR");
						System.out.println("* InputSource.getEncoding() = " + inputSource.getEncoding());
						System.out.println("* InputSource.getPublicId() = " + inputSource.getPublicId());
						System.out.println("* InputSource.getSystemId() = " + inputSource.getSystemId());
					}
				}
				
				if (flag) {
					String strXmlTag = sb.substring(0, Math.min(100, sb.length()));
					
					System.out.println("EUC-KR index=" + strXmlTag.indexOf("EUC-KR"));
					System.out.println("euc-kr index=" + strXmlTag.indexOf("euc-kr"));
				}
				
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
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			switch (1) {
			case 1:
				new XmlSendRecvTestMain().execute();
				break;
			case 2:
				test01();
				break;
			default:
				break;
			}
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
