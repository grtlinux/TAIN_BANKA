package ic.vela.ibridge.server.ext.virtualext.extlogger;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.ext.virtualext.extmanager.ExtManager;
import ic.vela.ibridge.util.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/*==================================================================*/
/**
 * FepLogger Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    FepLogger
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class ExtLogger extends Thread
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private ExtManager                 extManager          = null;
	private IBridgeQueue               logQueue            = null;

	private String                     strFileYYYYMMDD     = null;
	private String                     strFileName         = null;
	
	private String                     strLogInfoMsg       = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public ExtLogger() throws Exception
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * doing constructor
			 */
			this.extManager = ExtManager.getInstance();
			this.logQueue = this.extManager.getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			
			/*
			 * �α� ���ϸ��� ��´�.
			 */
			this.strFileYYYYMMDD = IBridgeParameter.getParam("ext.log.file");
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private boolean preJob() throws Exception
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (flag) {
			/*
			 * SEND_REQ_QUEUE ���� ������ü�� get �Ѵ�.
			 */
			this.strLogInfoMsg = (String) this.logQueue.get();
			
			/*
			 * ���� ���� ������ null �̸� false �� �����Ѵ�.
			 */
			if (this.strLogInfoMsg == null)
				retFlag = false;
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private boolean processJob() throws Exception
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (flag) {
			/*
			 * �α׸޽����� ���Ϸ� ����Ѵ�.
			 */

			// ���ϸ� ó��
			this.strFileName = this.strFileYYYYMMDD.replace("YYYYMMDD", DateTime.getStrYYYYMMDD());
			if (!flag && !new File(this.strFileName).exists()) {
				/*
				 * �ش� ������ �������� �ʴ´ٸ� ���� ó�� ������ ����Ѵ�.
				 * �ƴϸ� �ش� ������ ������ �����Ѵ�. TODO
				 */
				System.out.println("ERROR : ApLogger :  not found (" + this.strFileName + ")");
			}
			
			if (!flag) System.out.println("ApLogger [file=" + this.strFileName + "]");
			if (!flag) {
				/*
				 * ������ ������ �����Ѵ�.
				 * �Ʒ��� FileOutputStream �� ������ ������ �����Ѵ�. 
				 */
				if (!new File(this.strFileName).exists()) {
					new File(this.strFileName).createNewFile();
				}
			}

			// �α����Ͽ� ����Ѵ�.
			PrintStream ps = new PrintStream(new FileOutputStream(this.strFileName, true), true, CHARSET);
			ps.println(this.strLogInfoMsg);
			ps.close();

			// �α������� ȭ�鿡 ����Ѵ�.
			if (!flag) {
				System.out.println("ApLogger [" + this.strLogInfoMsg + "]");
			}
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private boolean postJob() throws Exception
	{
		boolean flag = true;
		
		boolean retFlag = true;
		
		if (flag) {
			/*
			 * �ƹ��� �۾��� ����.
			 */
		}
		
		return retFlag;
	}
	
	/*==================================================================*/
	/**
	 * Thread running method
	 */
	/*------------------------------------------------------------------*/
	public void run()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				
				while (true) {
					/*
					 * ť���� �ڷḦ get �Ѵ�.
					 */
					if (!preJob())
						continue;
					
					/*
					 * �ڷḦ ó���Ѵ�.
					 */
					if (!processJob())
						continue;
					
					/*
					 * �ڷḦ ť�� put �Ѵ�.
					 */
					if (!postJob())
						continue;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
