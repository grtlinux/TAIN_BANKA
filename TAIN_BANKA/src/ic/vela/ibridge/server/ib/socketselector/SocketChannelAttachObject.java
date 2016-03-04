package ic.vela.ibridge.server.ib.socketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.base.xml.XmlPoll;
import ic.vela.ibridge.server.ib.ibmanager.IBridgeManager;
import ic.vela.ibridge.util.XmlTool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
 * - 관련된 세션이 끊기면 이 Thread는 종료한다.
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
	//private static final int           POLL_SEND_CNT       = 100;  // 0.2 * 100 = 20 seconds
	//private static final int           POLL_KILL_CNT       = 140;  // 0.2 * 140 = 28 seconds
	// TODO DATE.2013.07.13
	//private static final int           POLL_SEND_SEC       = 60;  // 60 seconds
	//private static final int           POLL_KILL_SEC       = 70;  // 70 seconds
	
	private static final int           POLL_STAT_NORM      = 1;
	private static final int           POLL_STAT_SEND      = 2;
	private static final int           POLL_STAT_KILL      = 9;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	
	private SocketHashMapConnect       conn                = null;
	
	private IBridgeManager             ibManager           = null;
	
	private IBridgeQueue               readQueue           = null;
	
	private boolean                    errFlag             = false;
	
	/*------------------------------------------------------------------*/
	
	private IBridgeQueue               sendQueue           = null;
	private IBridgeQueue               recvQueue           = null;
	
	private HashMap<String,XmlObject>  hashMapXml          = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public SocketChannelAttachObject(SocketHashMapConnect conn)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * 객체의 이름을 설정한다.
				 */
				this.conn = conn;
				
				/*
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.ibManager = IBridgeManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				this.readQueue = this.ibManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_REQ_QUEUE");
				
				/*
				 * SocketSelector 와 전문 주고받을 Queue 를 생성한다.
				 */
				this.sendQueue = new IBridgeQueue();
				this.recvQueue = new IBridgeQueue();

				/*
				 * 송수신을 저장할 객체를 생성한다.
				 */
				this.hashMapXml = new HashMap<String,XmlObject>(5,5);
				
				if (!flag) Logger.log(String.format("STATUS : create SocketChannelAttachObject. [%s]", this.conn.getStrConnName()));

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
		
		if (flag) {     // TO DO DATE.2013.06.23
			/*
			 * SEND_REQ_QUEUE 에서 전문객체을 get 한다.
			 */
			XmlObject xmlObject = (XmlObject) this.readQueue.get(100);

			/*
			 * 받은 전문 객체를 확인한다.
			 */
			if (xmlObject == null) {
				/*
				 * 만일 전문 내용이 null 이면 false 를 리턴한다.
				 */
				retFlag = false;
				
			} else {
				/*
				 * 만일 전문 객체가 null 이 아니면 처리를 한다.
				 * 객체에서 요청전문을 얻어 selector에 보낸다.
				 */
				
				String strReqStreamXml = xmlObject.getReqStreamXml();
				this.sendQueue.put(strReqStreamXml);
				
				/*
				 * 송신한 전문객체를 저장한다.
				 */
				this.hashMapXml.put(xmlObject.getKey(), xmlObject);
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
		
		if (flag) {     // TO DO DATE.2013.06.23
			/*
			 * 전문객체을 큐에 get 한다.
			 */
			String strResStreamXml = (String) this.recvQueue.get(100);
			
			/*
			 * 받은 전문 객체를 확인한다.
			 */
			if (strResStreamXml == null) {
				/*
				 * 만일 전문 내용이 null 이면 false 를 리턴한다.
				 */
				retFlag = false;
				
			} else {

				if (XmlPoll.isPoll(strResStreamXml)) {
					/*
					 * TO DO DATE.2013.07.13
					 * 전문 객체가 null 아니고
					 * polling 전문이 아니면
					 * XmlObject 객체를 IBridgeServant 로 보낸다.
					 */
					retFlag = false;
					return retFlag;
				}
				
				/*
				 * 만일 전문 객체가 null 이 아니면 처리를 한다.
				 * 응답전문의 KEY를 구하고 hashMapXml 에서
				 * 응답전문에 해당하는 요청전문을 검색한다.
				 */
				XmlObject xmlObject = null;
				String key = null;
				
				{
					key = XmlTool.getStreamKey(strResStreamXml);
					xmlObject = this.hashMapXml.get(key);
					/*
					 * 응답키에 해당하는 XmlObject 가 있다.
					 * hashMapXml 에서 해당키에 대한 자료를 삭제한다.
					 */
					this.hashMapXml.remove(key);
				}
				
				if (xmlObject == null) {
					/*
					 * 응답키에 해당하는 XmlObject 가 없다.
					 * 응답에 대응하는 요청전문을 찾을 수 없다.
					 * 혹시 삭제되지 않았는지 확인한다.
					 */
					if (flag) Logger.log("STATUS : 응답키[" + key + "]에 해당하는 요청전문이 없습니다.");
					
					retFlag = false;
					
				} else {
					/*
					 * XmlObject 객체에 응답전문을 세팅한다.
					 */
					xmlObject.setResStreamXml(strResStreamXml);
					
					/*
					 * XmlObject 객체를 IBridgeServant 로 보낸다.
					 */
					xmlObject.getParentQueue().put(xmlObject);
				}
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
		
		if (!flag) {    // DATE.2013.06.23
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

		if (flag) {    // TODO DATE.2013.07.11 POLL 처리
			/*
			 * DATE.2013.06.24
			 * 이 Thread는 송수신을 동시에 처리해야 한다.
			 * 그래서 비동기 방식으로 처리해야 하기 때문에
			 * queue의 처리시간을 조절할 필요가 있다.
			 * get(100), put(100) 으로 설정해야 한다.
			 * DATE.2013.07.11
			 * polling 처리를 위해
			 */
			try {
				
				boolean bRecvJob = false;
				
				long longPollTime = new Date().getTime();
				long longPollSendTime = longPollTime + this.conn.getiPollSendCnt() * 1000;
				long longPollKillTime = longPollTime + this.conn.getiPollKillCnt() * 1000;
				int intPollStat = POLL_STAT_NORM;
				if (!flag) {
					String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(longPollTime));
					System.out.println("longPollTime = [" + strDateTime + "] -> POLL_STAT_NORM");
				}

				long currPollTime = 0;
				
				while (!errFlag) {
					/*
					 * 큐에서 자료를 get 한다.
					 */
					if (sendJob()) {
						/*
						 * 전문을 보냈다.
						 */
					}
					
					/*
					 * 자료를 처리한다.
					 */
					processJob();
					
					/*
					 * 자료를 큐에 put 한다.
					 */
					bRecvJob = recvJob();
					
					///////////////////////////////////////////////////////
					// TODO DATE.2013.07.12
					
					if ("YES".equalsIgnoreCase(this.conn.getStrPoll())) {

						if (bRecvJob) {
							/*
							 * 응답을 받았으면 poll count를 0으로 세팅한다.
							 */
							longPollTime = new Date().getTime();
							longPollSendTime = longPollTime + this.conn.getiPollSendCnt() * 1000;
							longPollKillTime = longPollTime + this.conn.getiPollKillCnt() * 1000;
							intPollStat = POLL_STAT_NORM;
							
							if (!flag) {
								String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(longPollTime));
								System.out.println("longPollTime = [" + strDateTime + "] -> POLL_STAT_NORM");
							}
							
						} else {
							/*
							 * 현재 시간을 구한다.
							 */
							currPollTime = new Date().getTime();
							
							/*
							 * polling에 대한 처리
							 */
							if ((intPollStat == POLL_STAT_SEND) && (currPollTime > longPollKillTime)) {
								/*
								 * 처리모듈을 종료한다.
								 * 세션을 끊고 정리작업을 한다.
								 */
								/*
								 * 작업을 종료한다.
								 * 30초 동안 아무런 반응이 없다.
								 * TODO DATE.2013.07.12 후작업 필요
								 * selector에서 socket channel 을 제거한다.
								 */
								setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling 전문을 send 한다.
								 */
								/*
								 * poll 전문을 생성하고
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll전문) 에 poll 전문을 넣는다.
								 */
								String strPoll = XmlPoll.get(this.conn.getStrConnFepId(),"T");
								this.sendQueue.put(strPoll);
								
								intPollStat = POLL_STAT_SEND;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_SEND");
								}
							}
							
							if (!flag) {
								String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
								System.out.println("currPollTime = [" + strDateTime + "]");
							}
						}
						
						/*
						 * polling 처리 끝
						 */
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*
			 * 관련 Thread  socket를 정리한다.
			 */
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
	 * TODO DATE.2013.07.01 : SocketHashMapConnect 객체를 돌려준다.
	 * 
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public SocketHashMapConnect getHashMapConnect()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
		
		return this.conn;
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
