package ic.vela.ibridge.server.fep.restoreagent;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;

/*==================================================================*/
/**
 * RestoreAgent 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    RestoreAgent
 * @author  강석
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
	private FepManager                 fepManager          = null;
	
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
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				// RecvFromExt -> RestoreAgent
				this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_RESTOREAGENT_QUEUE");
				
				// RestoreAgent -> SendToAp
				this.writeQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOAP_QUEUE");
				
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
				 * SendToFepSendQueue 에서 요청전문 봉투를 꺼낸다
				 */
				
				strResStreamXml = (String) this.readQueue.get();  // 1초 멈칫한다.
				if (strResStreamXml == null) {
					continue;
				}
			}
			
			if (flag) {
				try {
					/*
					 *  요청전문을 얻는다.
					 */
					
					if (!flag) Logger.log("7.RestoreAgent-RES : [" + strResStreamXml + "]");
					
					/*
					 * 요청전문을 저장한다.
					 */
					//this.hashMapXml.put(object.getKey(), object);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (flag) {
				/*
				 *  요청전문 봉투를 SelectorSendQueue 에 넣는다.
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
