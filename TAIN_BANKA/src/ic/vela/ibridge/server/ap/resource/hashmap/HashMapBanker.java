package ic.vela.ibridge.server.ap.resource.hashmap;

import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;

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
public class HashMapBanker 
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
	private static HashMapBanker      hashMapBanker        = null;

	//private HashMap<String, HashMap<String,XmlObject>> banker    = null;
	
	private HashMap<String, Object>    banker              = null;

	/*==================================================================*/
	/**
	 * constructor private
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private HashMapBanker() throws Exception 
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
	public static synchronized HashMapBanker getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (hashMapBanker == null) {
					/*
					 * 객체를 생성한다.
					 */
					hashMapBanker = new HashMapBanker();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
					hashMapBanker.execute();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : HashMapBanker.getInstance");
			}
		}
		
		return hashMapBanker;
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
			//this.banker = new HashMap<String, HashMap<String,XmlObject>>(5, 5);
			this.banker = new HashMap<String, Object>(5, 5);
			
			/*
			 * 자원관리 객체에 자원을 put 한다.
			 */
			this.banker.put("XMLOBJECT_HASHMAP", new HashMap<String,XmlObject>(5, 5));
			
			this.banker.put("IBRIDGEQUEUE_HASHMAP", new HashMap<String,IBridgeQueue>(5, 5));
			
			// this.banker.put("XMLOBJECT_HASHMAP", new HashMap<String,XmlObject>());
		}
	}

	/*==================================================================*/
	/**
	 * 자원관리 객체를 return 한다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public HashMap<String, Object> getHashMap() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		HashMap<String, Object> banker = null;
		
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
