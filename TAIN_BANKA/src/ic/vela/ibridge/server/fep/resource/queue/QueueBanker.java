package ic.vela.ibridge.server.fep.resource.queue;

import ic.vela.ibridge.base.queue.IBridgeQueue;

import java.util.HashMap;

/*==================================================================*/
/**
 * HashMapBanker Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    HashMapBanker
 * @author  ����
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
					 * ��ü�� �����Ѵ�.
					 */
					queueBanker = new QueueBanker();
					
					/*
					 * ��ü�� �������� �۾��� �Ѵ�.
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
	 * ��ü�� �������� �۾��� �Ѵ�.
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void execute() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * �ڿ����� ��ü�� �����Ѵ�.
			 */
			this.banker = new HashMap<String, IBridgeQueue>(5, 5);
			
			/*
			 * �ڿ����� ��ü�� �ڿ��� put �Ѵ�.
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
				 * ����� �̷��� ���������� ���߿��� properties ������ �о�
				 * FEPID�� ��� �����Ѵ�.
				 */

				// FEP���� �й踦 ���� ����� QUEUE
				this.banker.put("SEND_HWI01_QUEUE", new IBridgeQueue());  // ��ȭ���� QUEUE
				this.banker.put("SEND_SHI01_QUEUE", new IBridgeQueue());  // ���ѻ��� QUEUE
				this.banker.put("SEND_HKI01_QUEUE", new IBridgeQueue());  // �ﱹ���� QUEUE
				this.banker.put("SEND_HNI01_QUEUE", new IBridgeQueue());  // �ϳ�HSBC���� QUEUE
				this.banker.put("SEND_KFI01_QUEUE", new IBridgeQueue());  // ī�������� QUEUE
				this.banker.put("SEND_ALI01_QUEUE", new IBridgeQueue());  // �˸��������� QUEUE
				this.banker.put("SEND_SSI01_QUEUE", new IBridgeQueue());  // �Ｚ���� QUEUE
				this.banker.put("SEND_MII01_QUEUE", new IBridgeQueue());  // �̷����»��� QUEUE
				this.banker.put("SEND_NHI01_QUEUE", new IBridgeQueue());  // �������� QUEUE
				this.banker.put("SEND_NRI01_QUEUE", new IBridgeQueue());  // ��������� QUEUE
				this.banker.put("SEND_LOCAL_QUEUE", new IBridgeQueue());  // LOCAL QUEUE
			}
			
		}
	}

	/*==================================================================*/
	/**
	 * �ڿ����� ��ü�� return �Ѵ�.
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
			 * �ڿ����� ��ü�� return �Ѵ�.
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
