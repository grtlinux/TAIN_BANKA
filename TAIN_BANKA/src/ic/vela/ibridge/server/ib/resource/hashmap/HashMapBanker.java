package ic.vela.ibridge.server.ib.resource.hashmap;

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
	private static final String[][]    FEPIDINFO           = {
		{ "HWI01", "한화생명"     },
		{ "SHI01", "신한생명"     },
		{ "HNI01", "하나HSBC생명" },
		{ "HKI01", "흥국생명"     },
		{ "KFI01", "카디프생명"   },
		{ "ALI01", "알리안츠생명" },
		{ "SSI01", "삼성생명"     },
		{ "MII01", "미래에셋생명" },
		{ "NHI01", "농협생명"     },
		{ "NRI01", "노란우산공제" },
		//{ "LOCAL", "시뮬레이터"   },
	};
	
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
			this.banker = new HashMap<String, Object>(5, 5);
			
			////////////////////////////////////////////////////////////
			/*
			 * FEPID 저장할 객체를 생성한다.
			 */
			HashMap<String,String> hashMapFepidInfo = new HashMap<String,String>(5, 5);
			if (flag) {
				/*
				 * FEPID 정보를 기록한다.
				 */
				for (int i=0; i < FEPIDINFO.length; i++) {
					String[] fepid = FEPIDINFO[i];
					
					hashMapFepidInfo.put(fepid[0], fepid[1]);
				}
			}
			
			////////////////////////////////////////////////////////////
			/*
			 * 자원관리 객체에 자원을 put 한다.
			 */
			this.banker.put("FEPIDINFO_HASHMAP", hashMapFepidInfo);
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
