package ic.vela.ibridge.server.ext.virtualext.resource.queue;

import ic.vela.ibridge.base.queue.IBridgeQueue;

import java.util.HashMap;

/*==================================================================*/
/**
 * HashMapBanker 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    HashMapBanker
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class QueueBanker 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	@SuppressWarnings("unused")
	private static final long          MILLISEC_DAY        = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static QueueBanker      queueBanker            = null;

	private HashMap<String,IBridgeQueue>         banker    = null;
	
	/*==================================================================*/
	/**
	 * constructor private
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private QueueBanker() throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * doing constructor
			 */
		}
	}
	
	/*==================================================================*/
	/**
	 * make the instance of the Object
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static synchronized QueueBanker getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (queueBanker == null) {
					/*
					 * 객체를 생성한다.
					 */
					queueBanker = new QueueBanker();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
					queueBanker.execute();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : QueueBanker.getInstance");
			}
		}
		
		return queueBanker;
	}
	
	/*==================================================================*/
	/**
	 * 객체의 여러가지 작업을 한다.
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void execute() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * 자원관리 객체를 생성한다.
			 */
			this.banker = new HashMap<String, IBridgeQueue>(5, 5);
			
			/*
			 * 자원관리 객체에 자원을 put 한다.
			 * 
			 * LOGGER_QUEUE : Logger -> IBridgeLogger
			 * SEND_REQ_QUEUE : IBridgeServant -> AttachObject
			 * 
			 * 
			 */
			// Logger -> Logger
			this.banker.put("LOGGER_QUEUE", new IBridgeQueue());
			

			// ServerSocketSelector -> RecvFromFep
			this.banker.put("SEND_RECVFROMFEP_QUEUE", new IBridgeQueue());
			
			// RecvFromFep -> ServerSocketSelector
			this.banker.put("RECV_SERVERSELECTOR_QUEUE", new IBridgeQueue());
		}
	}

	/*==================================================================*/
	/**
	 * 자원관리 객체를 return 한다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public HashMap<String,IBridgeQueue> getQueue() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		HashMap<String,IBridgeQueue> banker = null;
		
		if (flag) {
			/*
			 * 자원관리 객체를 return 한다.
			 */
			banker = this.banker;
		}
		
		return banker;
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/

	
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
			System.out.println("Default function");
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
