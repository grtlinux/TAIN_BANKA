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
 * - ���õ� ������ ����� �� Thread�� �����Ѵ�.
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
				 * ��ü�� �̸��� �����Ѵ�.
				 */
				this.conn = conn;
				
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.ibManager = IBridgeManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				this.readQueue = this.ibManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_REQ_QUEUE");
				
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
		
		if (flag) {     // TO DO DATE.2013.06.23
			/*
			 * SEND_REQ_QUEUE ���� ������ü�� get �Ѵ�.
			 */
			XmlObject xmlObject = (XmlObject) this.readQueue.get(100);

			/*
			 * ���� ���� ��ü�� Ȯ���Ѵ�.
			 */
			if (xmlObject == null) {
				/*
				 * ���� ���� ������ null �̸� false �� �����Ѵ�.
				 */
				retFlag = false;
				
			} else {
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * ��ü���� ��û������ ��� selector�� ������.
				 */
				
				String strReqStreamXml = xmlObject.getReqStreamXml();
				this.sendQueue.put(strReqStreamXml);
				
				/*
				 * �۽��� ������ü�� �����Ѵ�.
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
		
		if (flag) {     // TO DO DATE.2013.06.23
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

				if (XmlPoll.isPoll(strResStreamXml)) {
					/*
					 * TO DO DATE.2013.07.13
					 * ���� ��ü�� null �ƴϰ�
					 * polling ������ �ƴϸ�
					 * XmlObject ��ü�� IBridgeServant �� ������.
					 */
					retFlag = false;
					return retFlag;
				}
				
				/*
				 * ���� ���� ��ü�� null �� �ƴϸ� ó���� �Ѵ�.
				 * ���������� KEY�� ���ϰ� hashMapXml ����
				 * ���������� �ش��ϴ� ��û������ �˻��Ѵ�.
				 */
				XmlObject xmlObject = null;
				String key = null;
				
				{
					key = XmlTool.getStreamKey(strResStreamXml);
					xmlObject = this.hashMapXml.get(key);
					/*
					 * ����Ű�� �ش��ϴ� XmlObject �� �ִ�.
					 * hashMapXml ���� �ش�Ű�� ���� �ڷḦ �����Ѵ�.
					 */
					this.hashMapXml.remove(key);
				}
				
				if (xmlObject == null) {
					/*
					 * ����Ű�� �ش��ϴ� XmlObject �� ����.
					 * ���信 �����ϴ� ��û������ ã�� �� ����.
					 * Ȥ�� �������� �ʾҴ��� Ȯ���Ѵ�.
					 */
					if (flag) Logger.log("STATUS : ����Ű[" + key + "]�� �ش��ϴ� ��û������ �����ϴ�.");
					
					retFlag = false;
					
				} else {
					/*
					 * XmlObject ��ü�� ���������� �����Ѵ�.
					 */
					xmlObject.setResStreamXml(strResStreamXml);
					
					/*
					 * XmlObject ��ü�� IBridgeServant �� ������.
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
								setErrFlag(true);

								intPollStat = POLL_STAT_KILL;
								
								if (!flag) {
									String strDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA).format(new Date(currPollTime));
									System.out.println("currPollTime = [" + strDateTime + "] -> POLL_STAT_KILL");
								}
								
							} else if ((intPollStat == POLL_STAT_NORM) && (currPollTime > longPollSendTime)) {
								/*
								 * polling ������ send �Ѵ�.
								 */
								/*
								 * poll ������ �����ϰ�
								 * String strFepid = this.conn.getStrConnFepId();
								 * this.sendQueue.put(poll����) �� poll ������ �ִ´�.
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
	 * TODO DATE.2013.07.01 : SocketHashMapConnect ��ü�� �����ش�.
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
