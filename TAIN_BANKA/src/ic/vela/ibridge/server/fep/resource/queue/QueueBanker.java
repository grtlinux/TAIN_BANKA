package ic.vela.ibridge.server.fep.resource.queue;

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
			// Logger -> ApLogger
			this.banker.put("LOGGER_QUEUE", new IBridgeQueue());
			

			// ServerSocketSelector -> RecvFromAp
			this.banker.put("SEND_RECVFROMAP_QUEUE", new IBridgeQueue());
			
			// RecvFromAp -> StoreAgent
			this.banker.put("SEND_STOREAGENT_QUEUE", new IBridgeQueue());

			// StoreAgent -> SendToExt
			this.banker.put("SEND_SENDTOEXT_QUEUE", new IBridgeQueue());
			
			// SendToExt -> SocketSelector
			this.banker.put("SEND_SELECTOR_QUEUE", new IBridgeQueue());

			
			// SocketSelector -> RecvFromExt
			this.banker.put("RECV_RECVFROMEXT_QUEUE", new IBridgeQueue());

			// RecvFromExt -> RestoreAgent
			this.banker.put("RECV_RESTOREAGENT_QUEUE", new IBridgeQueue());

			// RestoreAgent -> SendToAp
			this.banker.put("RECV_SENDTOAP_QUEUE", new IBridgeQueue());

			// SendToAp -> ServerSocketSelector
			this.banker.put("RECV_SERVERSELECTOR_QUEUE", new IBridgeQueue());
			
			if (flag) {
				/*
				 * 현재는 이렇게 생성하지만 나중에는 properties 파일을 읽어
				 * FEPID를 얻어 생성한다.
				 */

				// FEP에서 분배를 위해 사용할 QUEUE
				this.banker.put("SEND_HWI01_QUEUE", new IBridgeQueue());  // 한화생명 QUEUE
				this.banker.put("SEND_SHI01_QUEUE", new IBridgeQueue());  // 신한생명 QUEUE
				this.banker.put("SEND_HKI01_QUEUE", new IBridgeQueue());  // 흥국생명 QUEUE
				this.banker.put("SEND_HNI01_QUEUE", new IBridgeQueue());  // 하나HSBC생명 QUEUE
				this.banker.put("SEND_KFI01_QUEUE", new IBridgeQueue());  // 카디프생명 QUEUE
				this.banker.put("SEND_ALI01_QUEUE", new IBridgeQueue());  // 알리안츠생명 QUEUE
				this.banker.put("SEND_SSI01_QUEUE", new IBridgeQueue());  // 삼성생명 QUEUE
				this.banker.put("SEND_MII01_QUEUE", new IBridgeQueue());  // 미래에셋생명 QUEUE
				this.banker.put("SEND_NHI01_QUEUE", new IBridgeQueue());  // 농협생명 QUEUE
				this.banker.put("SEND_NRI01_QUEUE", new IBridgeQueue());  // 노란우산공제 QUEUE
				this.banker.put("SEND_LOCAL_QUEUE", new IBridgeQueue());  // LOCAL QUEUE
			}
			
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
