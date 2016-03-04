package ic.vela.ibridge.server.fep.fepmanager;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.server.fep.feplogger.FepLogger;
import ic.vela.ibridge.server.fep.recvfromap.RecvFromAp;
import ic.vela.ibridge.server.fep.recvfromext.RecvFromExt;
import ic.vela.ibridge.server.fep.resource.ResourceBanker;
import ic.vela.ibridge.server.fep.restoreagent.RestoreAgent;
import ic.vela.ibridge.server.fep.sendtoap.SendToAp;
import ic.vela.ibridge.server.fep.sendtoext.SendToExt;
import ic.vela.ibridge.server.fep.serversocketselector.ServerSocketSelector;
import ic.vela.ibridge.server.fep.socketselector.SocketSelector;
import ic.vela.ibridge.server.fep.storeagent.StoreAgent;


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
public class FepManager
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
	private static FepManager           fepManager           = null;
	
	@SuppressWarnings("unused")
	private IBridgeParameter           ibParam             = null;
	private ResourceBanker             resourceBanker      = null;
	
	/*==================================================================*/
	/**
	 * constructor private
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private FepManager() throws Exception 
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
	public static synchronized FepManager getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (fepManager == null) {
					/*
					 * 객체를 생성한다.
					 */
					fepManager = new FepManager();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
					fepManager.execute();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : FepManager.getInstance");
			}
		}
		
		return fepManager;
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
				new FepLogger().start();

				/////////////////////////////////////////////////////
				
				/*
				 *  ServerSocketSelector
				 */
				new ServerSocketSelector().start();
				
				/*
				 * RecvFromAp
				 */
				new RecvFromAp().start();
				
				/*
				 * StoreAgent
				 */
				new StoreAgent().start();
				
				/*
				 * SendToExt
				 */
				new SendToExt().start();
				
				/*
				 *  SocketSelector
				 */
				new SocketSelector().start();
				//new Kang01Selector().start();

				/*
				 * RecvFromExt
				 */
				new RecvFromExt().start();
				
				/*
				 * RestoreAgent
				 */
				new RestoreAgent().start();
				
				/*
				 * SendToAp
				 */
				new SendToAp().start();
				
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
				
				FepManager.getInstance();
				
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
