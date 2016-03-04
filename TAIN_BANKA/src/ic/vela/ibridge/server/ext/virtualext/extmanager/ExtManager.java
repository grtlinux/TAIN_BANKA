package ic.vela.ibridge.server.ext.virtualext.extmanager;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.server.ext.virtualext.extlogger.ExtLogger;
import ic.vela.ibridge.server.ext.virtualext.resource.ResourceBanker;
import ic.vela.ibridge.server.ext.virtualext.serversocketselector.ServerSocketSelector;


/*==================================================================*/
/**
 * FepManager Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    FepManager
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * * IBridgeManager ��ü�� ����Ǵ� ���ȿ��� Logger�� ȣ���ϸ� �ȵȴ�.
 *     -> reentrance ������ �߻���.
 *     
 * * �� ��ü�� static ���� �����Ǿ� ������ �����Ѵ�.
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
					 * ��ü�� �����Ѵ�.
					 */
					extManager = new ExtManager();
					
					/*
					 * ��ü�� �������� �۾��� �Ѵ�.
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
			 * �ڿ����� ��ü�� �����Ѵ�.
			 */
			this.resourceBanker = ResourceBanker.getInstance();
			
			/*
			 * �ڿ��� �̿��� �ϲ��� �����Ѵ�.
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
