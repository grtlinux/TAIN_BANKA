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
 * SocketChannelAttachObject 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketChannelAttachObject
 * @author  강석
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
				 * 객체의 이름을 설정한다.
				 */
				this.listen = listen;
				this.allow = allow;
				
				/*
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				this.writeQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_RECVFROMAP_QUEUE");
				this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SERVERSELECTOR_QUEUE");

				/*
				 * SocketSelector 와 전문 주고받을 Queue 를 생성한다.
				 */
				this.sendQueue = new IBridgeQueue();
				this.recvQueue = new IBridgeQueue();

				/*
				 * SendToIB 에 넘긴다.
				 */
				this.sendToApQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOAP_QUEUE");

				/*
				 * 송수신을 저장할 객체를 생성한다.
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
			 * QUEUE 에서 전문객체을 get 한다.
			 */
			String strReqStreamXml = (String) this.sendQueue.get(100);
			if (!flag) {
				System.out.println("KANG - SOCKET -> ATTACH");
			}

			/*
			 * 받은 전문 객체를 확인한다.
			 */
			if (strReqStreamXml == null) {
				/*
				 * 만일 전문 내용이 null 이면 false 를 리턴한다.
				 */
				retFlag = false;
				
			} else {
				
				if (flag) {
					/*
					 * TODO DATE.2013.07.12
					 * 만일 poll 전문이면 처리하지 않고
					 * 0810 006000 세팅후 돌려 보낸다.
					 */
					if (XmlPoll.isPoll(strReqStreamXml)) {
						String strResStreamXml = XmlPoll.set(strReqStreamXml);
						
						this.recvQueue.put(strResStreamXml);
						
						return retFlag;
					}
				}
				
				/*
				 * 만일 전문 객체가 null 이 아니면 처리를 한다.
				 */
				if (flag) {
					/*
					 * XmlObject 객체를 생성한다.
					 */
					XmlObject xmlObject = new XmlObject(this.recvQueue);
					
					/*
					 * 요청전문을 세팅한다.
					 */
					xmlObject.setReqStreamXml(strReqStreamXml);
					
					/*
					 * XmlObject 객체를 RECV_SENDTOIB_QUEUE 에 넣는다.
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
		
		if (!flag) {   // DATE.2013.06.22 : 사용하지 않음.
			/*
			 * 전문객체을 처리한다.
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
			 * 전문객체을 큐에 get 한다.
			 */
			String strResStreamXml = (String) this.readQueue.get(100);
			
			/*
			 * 받은 전문 객체를 확인한다.
			 */
			if (strResStreamXml == null) {
				/*
				 * 만일 전문 내용이 null 이면 false 를 리턴한다.
				 */
				retFlag = false;
				
			} else {
				/*
				 * 만일 전문 객체가 null 이 아니면 처리를 한다.
				 * 응답전문의 KEY를 구하고 hashMapXml 에서
				 * 응답전문에 해당하는 요청전문을 검색한다.
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
			 * 이 Thread는 송수신을 동시에 처리해야 한다.
			 * 그래서 비동기 방식으로 처리해야 하기 때문에
			 * queue의 처리시간을 조절할 필요가 있다.
			 * get(100), put(100) 으로 설정해야 한다.
			 */
			try {
				
				while (!errFlag) {
					/*
					 * 큐에서 자료를 get 한다.
					 */
					sendJob();
					
					/*
					 * 자료를 처리한다.
					 */
					processJob();
					
					/*
					 * 자료를 큐에 put 한다.
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
	 * 송신큐를 얻는다.
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
	 * 수신큐를 얻는다.
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
	 * TODO DATE.2013.07.01 : errFlag 를 세팅한다. 이 Thread를 종료하려면 errFlag = TRUE.
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
	 * DATE.2013.07.01 Listen Port에 대한 정보를 리턴한다.
	 * 
	 * @return ServerSocketHashMapListen
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketHashMapListen getHashMapListen()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;   // TO DO DATE.2013.06.30 : ServerSocketHashMapListen listen 만 사용
		}
		
		return this.listen;
	}
	
	/*==================================================================*/
	/**
	 * DATE.2013.07.01 Allow 에 대한 정보를 리턴한다.
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
