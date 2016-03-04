package ic.vela.ibridge.server.ib.iblogger;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.ib.ibmanager.IBridgeManager;
import ic.vela.ibridge.util.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/*==================================================================*/
/**
 * IBridgeLogger 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeLogger
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class IBridgeLogger extends Thread
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
	private IBridgeManager             ibManager           = null;
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
	public IBridgeLogger() throws Exception
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * doing constructor
			 */
			this.ibManager = IBridgeManager.getInstance();
			this.logQueue = this.ibManager.getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			
			/*
			 * 로그 파일명을 얻는다.
			 */
			this.strFileYYYYMMDD = IBridgeParameter.getParam("ib.log.file");
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
			 * SEND_REQ_QUEUE 에서 전문객체을 get 한다.
			 */
			this.strLogInfoMsg = (String) this.logQueue.get();
			
			/*
			 * 만일 전문 내용이 null 이면 false 를 리턴한다.
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
			 * 로그메시지를 파일로 기록한다.
			 */

			// 파일명 처리
			this.strFileName = this.strFileYYYYMMDD.replace("YYYYMMDD", DateTime.getStrYYYYMMDD());
			if (!flag && !new File(this.strFileName).exists()) {
				/*
				 * 해당 파일이 존재하지 않는다면 지금 처럼 에러를 출력한다.
				 * 아니면 해당 폴더와 파일을 생성한다. TODO
				 */
				System.out.println("ERROR : IBridgeLogger :  not found (" + this.strFileName + ")");
			}
			
			if (!flag) System.out.println("IBridgeLogger [file=" + this.strFileName + "]");
			if (!flag) {
				/*
				 * 파일이 없으면 생성한다.
				 * 아래의 FileOutputStream 은 파일이 없으면 생성한다. 
				 */
				if (!new File(this.strFileName).exists()) {
					new File(this.strFileName).createNewFile();
				}
			}

			// 로그파일에 기록한다.
			PrintStream ps = new PrintStream(new FileOutputStream(this.strFileName, true), true, CHARSET);
			ps.println(this.strLogInfoMsg);
			ps.close();

			// 로그파일을 화면에 출력한다.
			if (!flag) {
				System.out.println("IBridgeLogger [" + this.strLogInfoMsg + "]");
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
			 * 아무런 작업이 없다.
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
					 * 큐에서 자료를 get 한다.
					 */
					if (!preJob())
						continue;
					
					/*
					 * 자료를 처리한다.
					 */
					if (!processJob())
						continue;
					
					/*
					 * 자료를 큐에 put 한다.
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
