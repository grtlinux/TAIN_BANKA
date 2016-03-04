package ic.vela.ibridge.server.ext.virtualext.serversocketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.server.ext.virtualext.extmanager.ExtManager;
import ic.vela.ibridge.util.XmlTool;

import java.util.HashMap;

/*==================================================================*/
/**
 * ServerSocketChannelAttachObject Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketChannelAttachObject
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class ServerSocketChannelAttachObject
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private ServerSocketHashMapListen  listen              = null;
	
	private ExtManager                 extManager          = null;
	
	private IBridgeQueue               writeQueue          = null;
	private IBridgeQueue               readQueue           = null;
	
	/*------------------------------------------------------------------*/
	
	private IBridgeQueue               sendQueue           = null;
	private IBridgeQueue               recvQueue           = null;
	
	private IBridgeQueue               sendToApQueue       = null;

	private HashMap<String,XmlObject>  hashMapXml          = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketChannelAttachObject(ServerSocketHashMapListen listen)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // TO DO DATE.2013.06.30 : ServerSocketHashMapListen listen �� ���
			try {
				/*
				 * ��ü�� �̸��� �����Ѵ�.
				 */
				this.listen = listen;
				
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.extManager = ExtManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				this.writeQueue = this.extManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_RECVFROMFEP_QUEUE");
				this.readQueue  = this.extManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SERVERSELECTOR_QUEUE");
				
				/*
				 * SocketSelector �� ���� �ְ���� Queue �� �����Ѵ�.
				 */
				this.sendQueue = new IBridgeQueue();
				this.recvQueue = new IBridgeQueue();

				/*
				 * SendToIB �� �ѱ��.
				 */
				this.sendToApQueue  = this.extManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOIB_QUEUE");

				/*
				 * �ۼ����� ������ ��ü�� �����Ѵ�.
				 */
				this.hashMapXml = new HashMap<String,XmlObject>(5,5);
				
				if (!flag) Logger.log(String.format("STATUS : create ServerSocketChannelAttachObject[%s]", this.listen.getStrListenName()));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * preJob
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean sendJob() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (!flag) {    // DATE.2013.06.23 
			/*
			 * QUEUE ���� ������ü�� get �Ѵ�.
			 */
			String strReqStreamXml = (String) this.sendQueue.get();

			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (strReqStreamXml == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * XmlObject ��ü�� �����Ѵ�.
				 */
				
				XmlObject xmlObject = new XmlObject(this.recvQueue);
				
				if (!flag) Logger.log("1.ServerSocketChannelAttachObject-REQ [" + strReqStreamXml + "]");

				/*
				 * ��û������ �����Ѵ�.
				 */
				xmlObject.setReqStreamXml(strReqStreamXml);
				
				this.writeQueue.put(xmlObject);
				
				/*
				 * �۽��� ������ü�� �����Ѵ�.
				 */
				this.hashMapXml.put(xmlObject.getKey(), xmlObject);
			}
			
		}
		
		if (!flag) {    // TO DO DATE.2013.06.23
			/*
			 * QUEUE ���� ������ü�� get �Ѵ�.
			 */
			String strReqStreamXml = (String) this.sendQueue.get(100);

			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (strReqStreamXml == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * XmlObject ��ü�� �����Ѵ�.
				 */
				
				if (flag) Logger.log("1.ServerSocketChannelAttachObject-REQ [" + strReqStreamXml + "]");

				this.writeQueue.put(strReqStreamXml);
			}
			
		}
		
		if (flag) {    // TODO DATE.2013.06.25
			/*
			 * QUEUE ���� ������ü�� get �Ѵ�.
			 */
			String strReqStreamXml = (String) this.sendQueue.get(100);
			if (!flag) {
				System.out.println("KANG - SOCKET -> ATTACH");
			}

			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (strReqStreamXml == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 */
				if (flag) {
					/*
					 * XmlObject ��ü�� �����Ѵ�.
					 */
					XmlObject xmlObject = new XmlObject(this.recvQueue);
					
					/*
					 * ��û������ �����Ѵ�.
					 */
					xmlObject.setReqStreamXml(strReqStreamXml);
					
					/*
					 * XmlObject ��ü�� RECV_SENDTOIB_QUEUE �� �ִ´�.
					 */
					this.sendToApQueue.put(xmlObject);
				}
				
				if (!flag) Logger.log("1.ServerSocketChannelAttachObject-REQ [" + strReqStreamXml + "]");

				this.writeQueue.put(strReqStreamXml);
				
				if (!flag) {
					String key = XmlTool.getStreamKey(strReqStreamXml);
					System.out.println("[KEY=" + key + "]");
					System.out.println("[" + strReqStreamXml + "]");
				}
			}
			
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/**
	 * process Job
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean processJob() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (!flag) {   // DATE.2013.06.22 : ������� ����.
			/*
			 * ������ü�� ó���Ѵ�.
			 */
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/**
	 * preJob
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private boolean recvJob() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (!flag) {    // DATE.2013.06.23 
			/*
			 * ������ü�� ť�� get �Ѵ�.
			 */
			//XmlObject xmlObject = (XmlObject) this.recvQueue.get();
			Object object = (Object) this.recvQueue.get();
			
			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (object == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * ���������� KEY�� ���ϰ� hashMapXml ����
				 * ���������� �ش��ϴ� ��û������ �˻��Ѵ�.
				 */
				XmlObject xmlObject = (XmlObject) object;
				String key = xmlObject.getKey();
				
				if (this.hashMapXml.get(key) == null) {
					/*
					 * ����Ű�� �ش��ϴ� XmlObject �� ����.
					 * ���信 �����ϴ� ��û������ ã�� �� ����.
					 * Ȥ�� �������� �ʾҴ��� Ȯ���Ѵ�.
					 */
					if (flag) Logger.log("STATUS : ����Ű[" + key + "]�� �ش��ϴ� ��û������ �����ϴ�.");
					
					retFlag = false;
					
				} else {
					/*
					 * ����Ű�� �ش��ϴ� XmlObject �� �ִ�.
					 * hashMapXml ���� �ش�Ű�� ���� �ڷḦ �����Ѵ�.
					 */
					this.hashMapXml.remove(key);
					
					/*
					 * XmlObject ��ü�� IBridgeServant �� ������.
					 */
					String strResStreamXml = xmlObject.getResStreamXml();
					this.recvQueue.put(strResStreamXml);
					
					if (flag) Logger.log("9.ServerSocketChannelAttachObject-RES [" + strResStreamXml + "]");
				}
			}
		}
		
		if (flag) {    // TODO DATE.2013.06.23 
			/*
			 * ������ü�� ť�� get �Ѵ�.
			 */
			String strResStreamXml = (String) this.readQueue.get(100);
			
			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (strResStreamXml == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * ���������� KEY�� ���ϰ� hashMapXml ����
				 * ���������� �ش��ϴ� ��û������ �˻��Ѵ�.
				 */
				this.recvQueue.put(strResStreamXml);
				
				if (flag) Logger.log("9.ServerSocketChannelAttachObject-RES [" + strResStreamXml + "]");
			}
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/**
	 * Thread running method
	 */
	/*------------------------------------------------------------------*/
	public void run()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {    // DATE.2013.06.22
			try {
				
				while (true) {
					/*
					 * ť���� �ڷḦ get �Ѵ�.
					 */
					if (!sendJob())
						continue;
					
					/*
					 * �ڷḦ ó���Ѵ�.
					 */
					if (!processJob())
						continue;
					
					/*
					 * �ڷḦ ť�� put �Ѵ�.
					 */
					if (!recvJob())
						continue;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {     // TO DO DATE.2013.06.23 : �ùķ�����
			try {

				while (true) {
					String strReqStreamXml = (String) this.sendQueue.get(100);
					
					if (strReqStreamXml != null) {
						
						if (flag) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {}
						}
						
						if (flag) Logger.log("5.SocketChannelAttachObject-REQ->RES : [" + strReqStreamXml + "]");
						
						this.recvQueue.put(strReqStreamXml);
					} else {
						// System.out.print("#");
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {    // DATE.2013.06.23
			/*
			 * DATE.2013.06.24
			 * �� Thread�� �ۼ����� ���ÿ� ó���ؾ� �Ѵ�.
			 * �׷��� �񵿱� ������� ó���ؾ� �ϱ� ������
			 * queue�� ó���ð��� ������ �ʿ䰡 �ִ�.
			 * get(100), put(100) ���� �����ؾ� �Ѵ�.
			 */
			try {
				
				while (true) {
					/*
					 * ť���� �ڷḦ get �Ѵ�.
					 */
					sendJob();
					
					/*
					 * �ڷḦ ó���Ѵ�.
					 */
					processJob();
					
					/*
					 * �ڷḦ ť�� put �Ѵ�.
					 */
					recvJob();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/*==================================================================*/
	/**
	 * �۽�ť�� ��´�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public IBridgeQueue getSendQueue()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		IBridgeQueue queue = null;
		
		if (flag) {
			queue = this.sendQueue;
		}
		
		return queue;
	}

	/*==================================================================*/
	/**
	 * ����ť�� ��´�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public IBridgeQueue getRecvQueue()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		IBridgeQueue queue = null;
		
		if (flag) {
			queue = this.recvQueue;
		}
		
		return queue;
	}

	
	/*==================================================================*/
	/**
	 * Listen Port�� ���� ������ �����Ѵ�.
	 * 
	 * @return ServerSocketHashMapListen
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketHashMapListen getHashMapListen()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;   // TODO DATE.2013.06.30 : ServerSocketHashMapListen listen �� ���
		}
		
		return this.listen;
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
			;
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
