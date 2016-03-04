package ic.vela.ibridge.server.fep.sendtoext;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;
import ic.vela.ibridge.util.XmlTool;

import java.util.HashMap;

/*==================================================================*/
/**
 * SendToFep Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SendToFep
 * @author  ����
 * @version 1.0, 2013/06/23
 * @since   jdk1.6.0_45
 * 
 * * 
 *  
 */
/*==================================================================*/
public class SendToExt extends Thread 
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
	private FepManager                 fepManager          = null;
	
	@SuppressWarnings("unused")
	private IBridgeParameter           ibParam             = null;

	private IBridgeQueue               readQueue           = null;
	private IBridgeQueue               writeQueue          = null;
	
	private HashMap<String,IBridgeQueue>  hashMapQueue     = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public SendToExt()
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				// StoreAgent -> SendToExt
				this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_SENDTOEXT_QUEUE");
				
				// SendToExt -> SocketSelector
				if (flag) {
					/*
					 * hashMapQueue�� ���Ѵ�.
					 */
					//this.writeQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_SELECTOR_QUEUE");
					this.hashMapQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue();
				}
				
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
		String strReqStreamXml = null;
		
		String strFepid = null;
		
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
					
					//strReqStreamXml = object.getReqStreamXml();
					if (!flag) Logger.log("4.SendToFep-REQ : [" + strReqStreamXml + "]");
					
					/*
					 * ��û�������� FEPID�� ��´�.
					 */
					strFepid = XmlTool.getStreamFepid(strReqStreamXml);
					
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
				 * �۽��ϰ��� �ϴ� queue name �� ��´�.
				 */
				String strQueueName = String.format("SEND_%s_QUEUE", strFepid);
				this.writeQueue = this.hashMapQueue.get(strQueueName);
				if (this.writeQueue == null) {
					/*
					 * �ش��ϴ� FEPID�� queue �� ����.
					 * TODO DATE.2013.07.02 : ���߿� �������� �߻��Ͽ� IB�� �����ش�.
					 */
					if (flag) Logger.log("ERROR : wrong queue [%s] [���� �غ� ��] ######################", strQueueName);
					
					continue;
				}
				
				/*
				 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
				 */
				this.writeQueue.put(strReqStreamXml);
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
