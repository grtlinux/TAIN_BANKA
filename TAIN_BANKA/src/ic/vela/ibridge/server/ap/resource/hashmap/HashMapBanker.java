package ic.vela.ibridge.server.ap.resource.hashmap;

import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;

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
					 * ��ü�� �����Ѵ�.
					 */
					hashMapBanker = new HashMapBanker();
					
					/*
					 * ��ü�� �������� �۾��� �Ѵ�.
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
			//this.banker = new HashMap<String, HashMap<String,XmlObject>>(5, 5);
			this.banker = new HashMap<String, Object>(5, 5);
			
			/*
			 * �ڿ����� ��ü�� �ڿ��� put �Ѵ�.
			 */
			this.banker.put("XMLOBJECT_HASHMAP", new HashMap<String,XmlObject>(5, 5));
			
			this.banker.put("IBRIDGEQUEUE_HASHMAP", new HashMap<String,IBridgeQueue>(5, 5));
			
			// this.banker.put("XMLOBJECT_HASHMAP", new HashMap<String,XmlObject>());
		}
	}

	/*==================================================================*/
	/**
	 * �ڿ����� ��ü�� return �Ѵ�.
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
