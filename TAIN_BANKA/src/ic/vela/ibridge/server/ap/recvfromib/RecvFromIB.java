package ic.vela.ibridge.server.ap.recvfromib;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.server.ap.apmanager.ApManager;

/*==================================================================*/
/**
 * RecvFromIB Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    RecvFromIB
 * @author  ����
 * @version 1.0, 2013/06/23
 * @since   jdk1.6.0_45
 * 
 * * 
 *  
 */
/*==================================================================*/
public class RecvFromIB extends Thread 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	// private static final long       MILLISEC_DAY = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private ApManager                  apManager           = null;
	
	@SuppressWarnings("unused")
	private IBridgeParameter           ibParam             = null;

	private IBridgeQueue               readQueue           = null;
	private IBridgeQueue               writeQueue          = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public RecvFromIB()
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.apManager = ApManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				// ServerSocketSelector -> RecvFromIB
				this.readQueue  = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_RECVFROMIB_QUEUE");
				
				// RecvFromIB -> StoreAgent
				this.writeQueue = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_STOREAGENT_QUEUE");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

		XmlObject object = null;
		String strReqStreamXml = null;
		
		if (!flag) {     // DATE.2013.06.23

			while (true) {
				
				if (flag) {
					/*
					 * SendToFepSendQueue ���� ��û���� ������ ������
					 */
					
					object = (XmlObject) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
					if (object == null) {
						continue;
					}
				}
				
				if (flag) {
					try {
						/*
						 *  ��û������ ��´�.
						 */
						
						strReqStreamXml = object.getReqStreamXml();
						if (!flag) Logger.log("2.RecvFromIB-REQ : [" + strReqStreamXml + "]");
						
						/*
						 * ��û������ �����Ѵ�.
						 */
						//synchronized (this.hashMapXml) {
						//	this.hashMapXml.put(object.getKey(), object);
						//}
						/*
						 * ��û������ �����Ѵ�.
						 */
						//this.hashMapXml.put(object.getKey(), object);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (flag) {
					/*
					 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
					 */
					
					this.writeQueue.put(strReqStreamXml);
				}
			}
		}

		if (flag) {     // TODO DATE.2013.06.23

			while (true) {
				
				if (flag) {
					/*
					 * SendToFepSendQueue ���� ��û���� ������ ������
					 */
					
					strReqStreamXml = (String) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
					if (strReqStreamXml == null) {
						continue;
					}
				}
				
				if (flag) {
					try {
						/*
						 *  ��û������ ��´�.
						 */
						
						if (!flag) Logger.log("2.RecvFromIB-REQ : [" + strReqStreamXml + "]");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (flag) {
					/*
					 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
					 */
					
					this.writeQueue.put(strReqStreamXml);
				}
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
