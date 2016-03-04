package ic.vela.ibridge.server.fep.sendtoext;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;
import ic.vela.ibridge.util.XmlTool;

import java.util.HashMap;

/*==================================================================*/
/**
 * SendToFep 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SendToFep
 * @author  강석
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
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				// StoreAgent -> SendToExt
				this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_SENDTOEXT_QUEUE");
				
				// SendToExt -> SocketSelector
				if (flag) {
					/*
					 * hashMapQueue를 구한다.
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
				 * SendToFepSendQueue 에서 요청전문 봉투를 꺼낸다
				 */
				
				strReqStreamXml = (String) this.readQueue.get();  // 1초 멈칫한다.
				if (strReqStreamXml == null) {
					continue;
				}
			}
			
			if (flag) {
				try {
					/*
					 *  요청전문을 얻는다.
					 */
					
					//strReqStreamXml = object.getReqStreamXml();
					if (!flag) Logger.log("4.SendToFep-REQ : [" + strReqStreamXml + "]");
					
					/*
					 * 요청전문에서 FEPID를 얻는다.
					 */
					strFepid = XmlTool.getStreamFepid(strReqStreamXml);
					
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
				 * 송신하고자 하는 queue name 을 얻는다.
				 */
				String strQueueName = String.format("SEND_%s_QUEUE", strFepid);
				this.writeQueue = this.hashMapQueue.get(strQueueName);
				if (this.writeQueue == null) {
					/*
					 * 해당하는 FEPID별 queue 가 없다.
					 * TODO DATE.2013.07.02 : 나중에 에러전문 발생하여 IB에 돌려준다.
					 */
					if (flag) Logger.log("ERROR : wrong queue [%s] [서비스 준비 중] ######################", strQueueName);
					
					continue;
				}
				
				/*
				 *  요청전문 봉투를 SelectorSendQueue 에 넣는다.
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
