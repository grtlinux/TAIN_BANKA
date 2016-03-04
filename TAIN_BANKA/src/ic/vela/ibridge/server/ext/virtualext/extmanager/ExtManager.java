package ic.vela.ibridge.server.ext.virtualext.extmanager;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.server.ext.virtualext.extlogger.ExtLogger;
import ic.vela.ibridge.server.ext.virtualext.resource.ResourceBanker;
import ic.vela.ibridge.server.ext.virtualext.serversocketselector.ServerSocketSelector;


/*==================================================================*/
/**
 * FepManager 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    FepManager
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * * IBridgeManager 객체가 실행되는 동안에는 Logger를 호출하면 안된다.
 *     -> reentrance 현상이 발생함.
 *     
 * * 이 객체는 static 으로 생성되어 끝까지 존재한다.
 *  
 */
/*==================================================================*/
public class ExtManager
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	// private static final long       MILLISEC_DAY = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static ExtManager           extManager           = null;
	
	@SuppressWarnings("unused")
	private IBridgeParameter           ibParam             = null;
	private ResourceBanker             resourceBanker      = null;
	
	/*==================================================================*/
	/**
	 * constructor private
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private ExtManager() throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * doing constructor
			 */
			ibParam = IBridgeParameter.getInstance();
		}
	}
	
	/*==================================================================*/
	/**
	 * make the instance of the Object
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static synchronized ExtManager getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (extManager == null) {
					/*
					 * 객체를 생성한다.
					 */
					extManager = new ExtManager();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
					extManager.execute();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : FepManager.getInstance");
			}
		}
		
		return extManager;
	}
	
	/*==================================================================*/
	/**
	 * execute the manager job
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
			this.resourceBanker = ResourceBanker.getInstance();
			
			/*
			 * 자원을 이용할 일꾼을 생성한다.
			 */
			if (flag) {
				// Logger
				new ExtLogger().start();

				/////////////////////////////////////////////////////
				
				/*
				 *  ServerSocketSelector
				 */
				new ServerSocketSelector().start();
				
			}
		}
	}

	/*==================================================================*/
	/**
	 * get the resource banker
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public ResourceBanker getResourceBanker() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		ResourceBanker banker = null;
		
		if (flag) {
			banker = this.resourceBanker;
		}
		
		return banker;
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
				
				ExtManager.getInstance();
				
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
