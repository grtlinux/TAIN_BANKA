package ic.vela.ibridge.base.log;

import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.server.ap.apmanager.ApManager;
import ic.vela.ibridge.server.ext.virtualext.extmanager.ExtManager;
import ic.vela.ibridge.server.fep.fepmanager.FepManager;
import ic.vela.ibridge.server.ib.ibmanager.IBridgeManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/*==================================================================*/
/**
 * Logger 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    Logger
 * @author  강석
 * @version 1.0, 2013/06/21
 * @since   jdk1.6.0_45
 * 
 * * Logger 객체가 생성되기 전에 IBridgeManager 가 실행되야 한다.
 * 
 * * Logger 클래스는 ApLogger에 로그메시지를 넘긴다.
 * 
 * * 상태 : LOG, DEB, ERR
 *   - log : 상태정보, 진행정보 등을 남긴다.
 *   - debug : debug를 위한 정보를 남긴다. 운영시에는 기록하지 않는다.
 *   - error : 프로세스 진행에 치명적인 영향을 주는 error를 남긴다.
 * 
 * * 형태 : YYYYMMDD hh:mm:ss.SSS HOSTNAME [서비스명:method:Line:STAT] 메시지
 * 
 * * Logger 객체는 static으로 정의되어 있어 끝까지 존재한다.
 * 
 * 
 */
/*==================================================================*/
public class Logger
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	//private static final long       MILLISEC_DAY = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static Logger              logger              = null;
	
	private IBridgeQueue               logQueue            = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private Logger() throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * doing constructor
			 */
			String strServer = IBridgeParameter.getParam("system.ib.param.server");
			if (!flag) System.out.println(">" + strServer);
			
			if ("AP".equalsIgnoreCase(strServer)) {
				logQueue = ApManager.getInstance().getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			} else if ("FEP".equalsIgnoreCase(strServer)) {
				logQueue = FepManager.getInstance().getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			} else if ("EXT".equalsIgnoreCase(strServer)) {
				logQueue = ExtManager.getInstance().getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			} else {
				logQueue = IBridgeManager.getInstance().getResourceBanker().getQueueBanker().getQueue().get("LOGGER_QUEUE");
			}
		}
	}

	/*==================================================================*/
	/**
	 * make the instance of the Object
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static synchronized Logger getInstance() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (logger == null) {
					/*
					 * 객체를 생성한다.
					 */
					logger = new Logger();
					
					/*
					 * 객체의 여러가지 작업을 한다.
					 */
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : Logger.getInstance");
			}
		}
		
		return logger;
	}
	
	/*==================================================================*/
	/**
	 * 모들 프로세스가 처리하는 로그를 남긴다.
	 * @param strStat
	 * @param strLogMessage
	 * @return
	 */
	/*------------------------------------------------------------------*/
	private static synchronized boolean println(String strStat, String strLogMessage)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.21
			try {
				/*
				 *  처리시간 을 구한다.
				 */
				String strDateTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS", Locale.KOREA).format(new Date());
				
				/*
				 *  HOSTNAME 을 구한다.
				 */
				String strHostName = IBridgeParameter.getParam("hostname");

				/*
				 *  위치를 구한다.
				 */
				String strPosition = null;

				Exception e = new Exception("KANG SEOK is a good man. (강석)");
				StackTraceElement[] ste = e.getStackTrace();
				
				if (!flag) {
					System.out.println("Thread.name = [" + Thread.currentThread().getName() + "]");

					for (int i=0; i < ste.length; i++) {
						System.out.println("ste[" + i + "].getClassName()  = [" + ste[i].getClassName()  + "]");
						System.out.println("ste[" + i + "].getFileName()   = [" + ste[i].getFileName()   + "]");
						System.out.println("ste[" + i + "].getLineNumber() = [" + ste[i].getLineNumber() + "]");
						System.out.println("ste[" + i + "].getMethodName() = [" + ste[i].getMethodName() + "]");
						System.out.println("ste[" + i + "].toString()      = [" + ste[i].toString()      + "]");
						System.out.println("--------------------------------------");
					}
				}
				
				int iPos = 1;
				if (flag) {
					boolean flagPos = true;
					
					for (iPos = 0; iPos < ste.length; iPos ++) {
						if (flagPos) {
							/*
							 * log, debug, error 가 나오는데까지 looping
							 */
							if ("log".equals(ste[iPos].getMethodName())
									|| "debug".equals(ste[iPos].getMethodName())
									|| "error".equals(ste[iPos].getMethodName())
									) {
								flagPos = false;
								continue;
							}
						}
						
						if (!flagPos) {
							/*
							 * log, debug, error 가 안나오는데까지 looping
							 */
							if (!"log".equals(ste[iPos].getMethodName())
									&& !"debug".equals(ste[iPos].getMethodName())
									&& !"error".equals(ste[iPos].getMethodName())
									) {
								break;
							}
						}
					}

					/*
					 * log, debug, error 를 호출한 위치를 출력할 수 있다.
					 * 아래는 만일 찾지 못했을 경우
					 * 그냥 처음 stacktrace 값으로 처리한다.
					 */
					if (iPos == ste.length) {
						iPos = 0;
					}
				}
				
				strPosition = String.format("%s(%d)-%s"
						, ste[iPos].getClassName()
						, ste[iPos].getLineNumber()
						, ste[iPos].getMethodName()
						);
				
				/*
				 * 로그파일에 기록할 log information message 를 만든다. 
				 */
				String strLogInfoMsg = String.format("%s %s %s [%s] %s"
						, strDateTime
						, strHostName
						, strStat
						, strPosition
						, strLogMessage
						);

				if (!flag) System.out.println(strLogInfoMsg);
				
				/*
				 *  LogMessage 를 Queue에 넣는다.
				 */
				Logger.getInstance().logQueue.put(strLogInfoMsg);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	/*==================================================================*/
	/**
	 * LOG : 상태정보, 진행정보 등을 남긴다. args 를 사용함.
	 * @param format
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void log(String format, Object... args)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			String strLogMessage = String.format(format, args);
			log(strLogMessage);
		}
	}
	
	/*==================================================================*/
	/**
	 * LOG : 상태정보, 진행정보 등을 남긴다.
	 * @param strLogMessage
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static boolean log(String strLogMessage)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			return println("LOG", strLogMessage);
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * DEBUG : debug를 위한 정보를 남긴다. 운영시에는 기록하지 않는다.
	 * @param strLogMessage
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static boolean debug(String strLogMessage)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			return println("DEB", strLogMessage);
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * ERROR : 프로세스 진행에 치명적인 영향을 주는 error를 남긴다.
	 * @param strLogMessage
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static boolean error(String strLogMessage)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			return println("ERR", strLogMessage);
		}
		
		return true;
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 * 로그처리 방법
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.21

			Logger.log("이것은 LOG 입니다. 상태를 출력하거나 진행상황을 나타냅니다.");
			Logger.debug("이것은 DEB 입니다. debug를 위한 정보를 남긴다. 운영시에는 기록하지 않는다.");
			Logger.error("이것은 ERR 입니다. 프로세스 진행에 치명적인 영향을 주는 error를 남긴다.");
		}
		
	}
	
	/*==================================================================*/
	/**
	 * Exception reading -> StackTraceElement
	 */
	/*------------------------------------------------------------------*/
	private static void test02()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.21
			Exception e = new Exception("ERROR Exception() Hello, world...(강석)");
			StackTraceElement[] ste = e.getStackTrace();
			
			for (int i=0; i < ste.length; i++) {
				System.out.println("ste[" + i + "].getClassName()  = [" + ste[i].getClassName()  + "]");
				System.out.println("ste[" + i + "].getFileName()   = [" + ste[i].getFileName()   + "]");
				System.out.println("ste[" + i + "].getLineNumber() = [" + ste[i].getLineNumber() + "]");
				System.out.println("ste[" + i + "].getMethodName() = [" + ste[i].getMethodName() + "]");
				System.out.println("ste[" + i + "].toString()      = [" + ste[i].toString()      + "]");
				System.out.println("--------------------------------------");
			}
			
			System.out.println("Thread.currentThread().getName() >" + Thread.currentThread().getName());
			System.out.println("e.getLocalizedMessage >" + e.getLocalizedMessage());
			System.out.println("e.getMessage >" + e.getMessage());
			System.out.println("e.toString >" + e.toString());
			
			System.out.println("--------------------------------------");
		}
		
		if (flag) {     // DATE.2013.06.21
			Exception e = new Exception();
			StackTraceElement[] se = e.getStackTrace();
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("(");
			sb.append(Thread.currentThread().getName()).append(":");
			sb.append(se[0].getClassName()).append(":");
			sb.append(se[0].getFileName()).append(":");
			sb.append(se[0].getLineNumber()).append(":");
			sb.append(se[0].getMethodName()).append(":");
			sb.append(")");
			
			System.out.println("[" + sb.toString() + "]");
		}
		
	}
	
	/*==================================================================*/
	/**
	 * default test : 단순한 문자 출력
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.21
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
		case 2:
			if (flag) {
				test02();
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
