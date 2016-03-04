package ic.vela.ibridge.server.fep.serversocketselector;

import java.util.HashMap;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.base.xml.XmlPoll;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;
import ic.vela.ibridge.util.XmlTool;

/*==================================================================*/
/**
 * SocketChannelAttachObject Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketChannelAttachObject
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class SocketChannelAttachObject extends Thread
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
	private ServerSocketHashMapAllow   allow               = null;
	
	private FepManager                 fepManager          = null;
	
	private IBridgeQueue               readQueue           = null;
	private IBridgeQueue               writeQueue          = null;
	
	private boolean                    errFlag             = false;

	/*------------------------------------------------------------------*/
	
	private IBridgeQueue               sendQueue           = null;
	private IBridgeQueue               recvQueue           = null;
	
	private IBridgeQueue               sendToApQueue       = null;

	@SuppressWarnings("unused")
	private HashMap<String,XmlObject>  hashMapXml          = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public SocketChannelAttachObject(ServerSocketHashMapListen listen, ServerSocketHashMapAllow allow)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * ��ü�� �̸��� �����Ѵ�.
				 */
				this.listen = listen;
				this.allow = allow;
				
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				this.writeQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_RECVFROMAP_QUEUE");
				this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SERVERSELECTOR_QUEUE");

				/*
				 * SocketSelector �� ���� �ְ���� Queue �� �����Ѵ�.
				 */
				this.sendQueue = new IBridgeQueue();
				this.recvQueue = new IBridgeQueue();

				/*
				 * SendToIB �� �ѱ��.
				 */
				this.sendToApQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOAP_QUEUE");

				/*
				 * �ۼ����� ������ ��ü�� �����Ѵ�.
				 */
				this.hashMapXml = new HashMap<String,XmlObject>(5,5);
				
				if (!flag) Logger.log(String.format("STATUS : create SocketChannelAttachObject[%s]", this.listen.getStrListenName()));

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
		
		if (flag) {    // TODO DATE.2013.06.25 sendJob
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
				
				if (flag) {
					/*
					 * TODO DATE.2013.07.12
					 * ���� poll �����̸� ó������ �ʰ�
					 * 0810 006000 ������ ���� ������.
					 */
					if (XmlPoll.isPoll(strReqStreamXml)) {
						String strResStreamXml = XmlPoll.set(strReqStreamXml);
						
						this.recvQueue.put(strResStreamXml);
						
						return retFlag;
					}
				}
				
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
		
		if (flag) {    // TODO DATE.2013.06.23 recvJob
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
		
		if (flag) {    // DATE.2013.06.23
			/*
			 * DATE.2013.06.24
			 * �� Thread�� �ۼ����� ���ÿ� ó���ؾ� �Ѵ�.
			 * �׷��� �񵿱� ������� ó���ؾ� �ϱ� ������
			 * queue�� ó���ð��� ������ �ʿ䰡 �ִ�.
			 * get(100), put(100) ���� �����ؾ� �Ѵ�.
			 */
			try {
				
				while (!errFlag) {
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
	 * TODO DATE.2013.07.01 : errFlag �� �����Ѵ�. �� Thread�� �����Ϸ��� errFlag = TRUE.
	 * 
	 * @param errFlag
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public boolean setErrFlag(boolean errFlag)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.errFlag = errFlag;
		}
		
		return this.errFlag;
	}
	
	/*==================================================================*/
	/**
	 * DATE.2013.07.01 Listen Port�� ���� ������ �����Ѵ�.
	 * 
	 * @return ServerSocketHashMapListen
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketHashMapListen getHashMapListen()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;   // TO DO DATE.2013.06.30 : ServerSocketHashMapListen listen �� ���
		}
		
		return this.listen;
	}
	
	/*==================================================================*/
	/**
	 * DATE.2013.07.01 Allow �� ���� ������ �����Ѵ�.
	 * 
	 * @return ServerSocketHashMapListen
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketHashMapAllow getHashMapAllow()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
		
		return this.allow;
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
