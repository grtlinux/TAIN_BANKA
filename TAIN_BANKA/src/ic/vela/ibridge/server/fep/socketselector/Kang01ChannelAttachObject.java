package ic.vela.ibridge.server.fep.socketselector;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.base.xml.XmlPoll;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;

import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.Selector;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/*==================================================================*/
/**
 * Kang01ChannelAttachObject 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    Kang01ChannelAttachObject
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 관련된 세션이 끊기면 이 Thread는 종료한다.
 *  
 */
/*==================================================================*/
public class Kang01ChannelAttachObject extends Thread
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
	
	private FepManager                 fepManager          = null;
	
	private IBridgeQueue               readQueue           = null;
	private IBridgeQueue               writeQueue          = null;
	
	private boolean                    errFlag             = false;
	
	/*------------------------------------------------------------------*/
	
	private IBridgeQueue               sendQueue           = null;
	private IBridgeQueue               recvQueue           = null;
	
	@SuppressWarnings("unused")
	private HashMap<String,XmlObject>  hashMapXml          = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public Kang01ChannelAttachObject(SocketHashMapConnect conn)
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
				this.fepManager = FepManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				// SendToFep -> SocketSelector
				if (flag) {
					//this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_SELECTOR_QUEUE");
					String strFepid = this.conn.getStrConnFepId();
					String strQueueName = String.format("SEND_%s_QUEUE", strFepid);
					this.readQueue  = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get(strQueueName);
				}

				// SocketSelector -> RecvFromFep
				this.writeQueue = this.fepManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_RECVFROMEXT_QUEUE");

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
		
		if (flag) {
			/*
			 * SEND_REQ_QUEUE 에서 전문객체을 get 한다.
			 */
			String strReqStreamXml = (String) this.readQueue.get(100);

			/*
			 * 받은 전문 객체를 확인한다.
			 */
			if (strReqStreamXml == null) {
				/*
				 * 만일 전문 내용이 null 이면 false 를 리턴한다.
				 */
				retFlag = false;
				
			} else {
				/*
				 * 만일 전문 객체가 null 이 아니면 처리를 한다.
				 * 객체에서 요청전문을 얻어 selector에 보낸다.
				 */
				
				this.sendQueue.put(strReqStreamXml);
				
				/*
				 * 송신한 전문객체를 저장한다.
				 */
				//this.hashMapXml.put(xmlObject.getKey(), xmlObject);
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
		
		if (flag) {
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
				
				if (!XmlPoll.isPoll(strResStreamXml)) {
					/*
					 * TODO DATE.2013.07.13
					 * 전문 객체가 null 아니고
					 * polling 전문이 아니면
					 * XmlObject 객체를 IBridgeServant 로 보낸다.
					 */
					this.writeQueue.put(strResStreamXml);
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
								//setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
								/*
								 * TODO DATE.2013.07.16 : 내 프로세스를 죽여달라는 메시지를 보낸다. ㅠㅠ
								 */
								this.sendQueue.put("POLL_OUT_KILL_ME");
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling 전문을 send 한다.
								 */
								/*
								 * poll 전문을 생성하고
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll전문) 에 poll 전문을 넣는다.
								 */
								String strPoll = XmlPoll.get(this.conn.getStrConnFepId(), this.conn.getStrConnServerCode());
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
		
		if (!flag) {    // TODO DATE.2013.08.10 Thread connection 처리를 위한 방안...
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
					
					if (conn.getSocket().isConnected() == false) {
						/*
						 * TODO DATE.2013.08.10 : connection 을 시도한다.
						 * -> 로직 전체의 이해가 필요함... 재설계를 해야 함.....ㅠㅠ
						 */
						try {
							/*
							 * 접속을 시도한다.
							 */
							conn.getSocket().connect(new InetSocketAddress(conn.getStrConnHostIp(), conn.getiConnHostPort()));
							
							if (flag) Logger.log("CLIENT : SUCCESS : CONNECT. [%s:%s]"
									, conn.getStrConnName(), conn.getStrConnFepId());

						} catch (SocketTimeoutException e) {
							//e.printStackTrace();
							if (flag) Logger.log("CLIENT : ERROR : NOT CONNECT. SocketTimeoutException [%s:%s:%s]"
									, conn.getStrConnName(), conn.getStrConnHostIp(), conn.getStrConnHostPort());
							try {
								Thread.sleep(10 * 1000);
							} catch (InterruptedException ee) {}

							continue;
						} catch (Exception e) {
							//e.printStackTrace();
							if (flag) Logger.log("CLIENT : ERROR : NOT CONNECT. [%s:%s:%s]"
									, conn.getStrConnName(), conn.getStrConnHostIp(), conn.getStrConnHostPort());
							try {
								Thread.sleep(10 * 1000);
							} catch (InterruptedException ee) {}

							continue;
						}
					}
					
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
								//setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
								/*
								 * TODO DATE.2013.07.16 : 내 프로세스를 죽여달라는 메시지를 보낸다. ㅠㅠ
								 */
								this.sendQueue.put("POLL_OUT_KILL_ME");
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling 전문을 send 한다.
								 */
								/*
								 * poll 전문을 생성하고
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll전문) 에 poll 전문을 넣는다.
								 */
								String strPoll = XmlPoll.get(this.conn.getStrConnFepId(), this.conn.getStrConnServerCode());
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
	 * TO DO DATE.2013.07.01 : errFlag 를 세팅한다. 이 Thread를 종료하려면 errFlag = TRUE.
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
	 * TO DO DATE.2013.07.01 : SocketHashMapConnect 객체를 돌려준다.
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
	
	/*==================================================================*/
	/**
	 * TO DO DATE.2013.08.19 : selector 를 세팅한다.
	 * 
	 * @param errFlag
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public void setSelector(Selector selector)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			//this.selector = selector;
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