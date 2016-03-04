package ic.vela.ibridge.server.ap.restoreagent;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.ap.apmanager.ApManager;

/*==================================================================*/
/**
 * RestoreAgent Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    RestoreAgent
 * @author  ����
 * @version 1.0, 2013/06/23
 * @since   jdk1.6.0_45
 * 
 * * 
 *  
 */
/*==================================================================*/
public class RestoreAgent extends Thread 
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
	public RestoreAgent()
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
				this.readQueue  = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_RESTOREAGENT_QUEUE");
				
				// RecvFromIB -> StoreAgent
				this.writeQueue = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOIB_QUEUE");
				
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

		//XmlObject object = null;
		String strResStreamXml = null;
		
		while (true) {
			
			if (flag) {
				/*
				 * SendToFepSendQueue ���� ��û���� ������ ������
				 */
				
				strResStreamXml = (String) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
				if (strResStreamXml == null) {
					continue;
				}
			}
			
			if (flag) {
				try {
					/*
					 *  ��û������ ��´�.
					 */
					
					if (!flag) Logger.log("7.RestoreAgent-RES : [" + strResStreamXml + "]");
					
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
				
				this.writeQueue.put(strResStreamXml);
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
