package ic.vela.ibridge.server.ext.virtualext.resource;

import ic.vela.ibridge.server.fep.resource.hashmap.HashMapBanker;
import ic.vela.ibridge.server.fep.resource.queue.QueueBanker;


/*==================================================================*/
/**
 * ResourceBanker 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ResourceBanker
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class ResourceBanker 
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
	private static ResourceBanker      resourceBanker      = null;

	private HashMapBanker              hashMapBanker       = null;
	private QueueBanker                queueBanker         = null;
	
	/*==================================================================*/
	/**
	 * constructor private
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private ResourceBanker() throws Exception 
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
	public static synchronized ResourceBanker getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (resourceBanker == null) {
					/*
					 * 객체를 생성한다.
					 */
					resourceBanker = new ResourceBanker();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
					resourceBanker.execute();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : ResourceBanker.getInstance");
			}
		}
		
		return resourceBanker;
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
			this.hashMapBanker = HashMapBanker.getInstance();
			
			this.queueBanker = QueueBanker.getInstance();
		}
	}

	/*==================================================================*/
	/**
	 * get the hashMap banker
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public HashMapBanker getHashMapBanker() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		HashMapBanker banker = null;
		
		if (flag) {
			banker = this.hashMapBanker;
		}
		
		return banker;
	}
	
	/*==================================================================*/
	/**
	 * get the queue banker
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public QueueBanker getQueueBanker() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		QueueBanker banker = null;
		
		if (flag) {
			banker = this.queueBanker;
		}
		
		return banker;
	}
	
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
