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
 * Kang01ChannelAttachObject Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    Kang01ChannelAttachObject
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - ���õ� ������ ����� �� Thread�� �����Ѵ�.
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
				 * ��ü�� �̸��� �����Ѵ�.
				 */
				this.conn = conn;
				
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.fepManager = FepManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
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
				 * SocketSelector �� ���� �ְ���� Queue �� �����Ѵ�.
				 */
				this.sendQueue = new IBridgeQueue();
				this.recvQueue = new IBridgeQueue();

				/*
				 * �ۼ����� ������ ��ü�� �����Ѵ�.
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
			 * SEND_REQ_QUEUE ���� ������ü�� get �Ѵ�.
			 */
			String strReqStreamXml = (String) this.readQueue.get(100);

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
				 * ��ü���� ��û������ ��� selector�� ������.
				 */
				
				this.sendQueue.put(strReqStreamXml);
				
				/*
				 * �۽��� ������ü�� �����Ѵ�.
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
		
		if (flag) {
			/*
			 * ������ü�� ť�� get �Ѵ�.
			 */
			String strResStreamXml = (String) this.recvQueue.get(100);
			
			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (strResStreamXml == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				
				if (!XmlPoll.isPoll(strResStreamXml)) {
					/*
					 * TODO DATE.2013.07.13
					 * ���� ��ü�� null �ƴϰ�
					 * polling ������ �ƴϸ�
					 * XmlObject ��ü�� IBridgeServant �� ������.
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

		if (flag) {    // TODO DATE.2013.07.11 POLL ó��
			/*
			 * DATE.2013.06.24
			 * �� Thread�� �ۼ����� ���ÿ� ó���ؾ� �Ѵ�.
			 * �׷��� �񵿱� ������� ó���ؾ� �ϱ� ������
			 * queue�� ó���ð��� ������ �ʿ䰡 �ִ�.
			 * get(100), put(100) ���� �����ؾ� �Ѵ�.
			 * DATE.2013.07.11
			 * polling ó���� ����
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
					 * ť���� �ڷḦ get �Ѵ�.
					 */
					if (sendJob()) {
						/*
						 * ������ ���´�.
						 */
					}
					
					/*
					 * �ڷḦ ó���Ѵ�.
					 */
					processJob();
					
					/*
					 * �ڷḦ ť�� put �Ѵ�.
					 */
					bRecvJob = recvJob();
					
					///////////////////////////////////////////////////////
					// TODO DATE.2013.07.12
					
					if ("YES".equalsIgnoreCase(this.conn.getStrPoll())) {

						if (bRecvJob) {
							/*
							 * ������ �޾����� poll count�� 0���� �����Ѵ�.
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
							 * ���� �ð��� ���Ѵ�.
							 */
							currPollTime = new Date().getTime();
							
							/*
							 * polling�� ���� ó��
							 */
							if ((intPollStat == POLL_STAT_SEND) && (currPollTime > longPollKillTime)) {
								/*
								 * ó������� �����Ѵ�.
								 * ������ ���� �����۾��� �Ѵ�.
								 */
								/*
								 * �۾��� �����Ѵ�.
								 * 30�� ���� �ƹ��� ������ ����.
								 * TODO DATE.2013.07.12 ���۾� �ʿ�
								 * selector���� socket channel �� �����Ѵ�.
								 */
								//setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
								/*
								 * TODO DATE.2013.07.16 : �� ���μ����� �׿��޶�� �޽����� ������. �Ф�
								 */
								this.sendQueue.put("POLL_OUT_KILL_ME");
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling ������ send �Ѵ�.
								 */
								/*
								 * poll ������ �����ϰ�
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll����) �� poll ������ �ִ´�.
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
						 * polling ó�� ��
						 */
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*
			 * ���� Thread  socket�� �����Ѵ�.
			 */
		}
		
		if (!flag) {    // TODO DATE.2013.08.10 Thread connection ó���� ���� ���...
			/*
			 * DATE.2013.06.24
			 * �� Thread�� �ۼ����� ���ÿ� ó���ؾ� �Ѵ�.
			 * �׷��� �񵿱� ������� ó���ؾ� �ϱ� ������
			 * queue�� ó���ð��� ������ �ʿ䰡 �ִ�.
			 * get(100), put(100) ���� �����ؾ� �Ѵ�.
			 * DATE.2013.07.11
			 * polling ó���� ����
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
						 * TODO DATE.2013.08.10 : connection �� �õ��Ѵ�.
						 * -> ���� ��ü�� ���ذ� �ʿ���... �缳�踦 �ؾ� ��.....�Ф�
						 */
						try {
							/*
							 * ������ �õ��Ѵ�.
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
					 * ť���� �ڷḦ get �Ѵ�.
					 */
					if (sendJob()) {
						/*
						 * ������ ���´�.
						 */
					}
					
					/*
					 * �ڷḦ ó���Ѵ�.
					 */
					processJob();
					
					/*
					 * �ڷḦ ť�� put �Ѵ�.
					 */
					bRecvJob = recvJob();
					
					///////////////////////////////////////////////////////
					// TODO DATE.2013.07.12
					
					if ("YES".equalsIgnoreCase(this.conn.getStrPoll())) {

						if (bRecvJob) {
							/*
							 * ������ �޾����� poll count�� 0���� �����Ѵ�.
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
							 * ���� �ð��� ���Ѵ�.
							 */
							currPollTime = new Date().getTime();
							
							/*
							 * polling�� ���� ó��
							 */
							if ((intPollStat == POLL_STAT_SEND) && (currPollTime > longPollKillTime)) {
								/*
								 * ó������� �����Ѵ�.
								 * ������ ���� �����۾��� �Ѵ�.
								 */
								/*
								 * �۾��� �����Ѵ�.
								 * 30�� ���� �ƹ��� ������ ����.
								 * TODO DATE.2013.07.12 ���۾� �ʿ�
								 * selector���� socket channel �� �����Ѵ�.
								 */
								//setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
								/*
								 * TODO DATE.2013.07.16 : �� ���μ����� �׿��޶�� �޽����� ������. �Ф�
								 */
								this.sendQueue.put("POLL_OUT_KILL_ME");
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling ������ send �Ѵ�.
								 */
								/*
								 * poll ������ �����ϰ�
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll����) �� poll ������ �ִ´�.
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
						 * polling ó�� ��
						 */
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*
			 * ���� Thread  socket�� �����Ѵ�.
			 */
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
	 * TO DO DATE.2013.07.01 : errFlag �� �����Ѵ�. �� Thread�� �����Ϸ��� errFlag = TRUE.
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
	 * TO DO DATE.2013.07.01 : SocketHashMapConnect ��ü�� �����ش�.
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
	 * TO DO DATE.2013.08.19 : selector �� �����Ѵ�.
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