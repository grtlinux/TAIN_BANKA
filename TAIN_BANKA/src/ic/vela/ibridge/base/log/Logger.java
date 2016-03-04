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
 * Logger Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    Logger
 * @author  ����
 * @version 1.0, 2013/06/21
 * @since   jdk1.6.0_45
 * 
 * * Logger ��ü�� �����Ǳ� ���� IBridgeManager �� ����Ǿ� �Ѵ�.
 * 
 * * Logger Ŭ������ ApLogger�� �α׸޽����� �ѱ��.
 * 
 * * ���� : LOG, DEB, ERR
 *   - log : ��������, �������� ���� �����.
 *   - debug : debug�� ���� ������ �����. ��ÿ��� ������� �ʴ´�.
 *   - error : ���μ��� ���࿡ ġ������ ������ �ִ� error�� �����.
 * 
 * * ���� : YYYYMMDD hh:mm:ss.SSS HOSTNAME [���񽺸�:method:Line:STAT] �޽���
 * 
 * * Logger ��ü�� static���� ���ǵǾ� �־� ������ �����Ѵ�.
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
					 * ��ü�� �����Ѵ�.
					 */
					logger = new Logger();
					
					/*
					 * ��ü�� �������� �۾��� �Ѵ�.
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
	 * ��� ���μ����� ó���ϴ� �α׸� �����.
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
				 *  ó���ð� �� ���Ѵ�.
				 */
				String strDateTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS", Locale.KOREA).format(new Date());
				
				/*
				 *  HOSTNAME �� ���Ѵ�.
				 */
				String strHostName = IBridgeParameter.getParam("hostname");

				/*
				 *  ��ġ�� ���Ѵ�.
				 */
				String strPosition = null;

				Exception e = new Exception("KANG SEOK is a good man. (����)");
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
							 * log, debug, error �� �����µ����� looping
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
							 * log, debug, error �� �ȳ����µ����� looping
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
					 * log, debug, error �� ȣ���� ��ġ�� ����� �� �ִ�.
					 * �Ʒ��� ���� ã�� ������ ���
					 * �׳� ó�� stacktrace ������ ó���Ѵ�.
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
				 * �α����Ͽ� ����� log information message �� �����. 
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
				 *  LogMessage �� Queue�� �ִ´�.
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
	 * LOG : ��������, �������� ���� �����. args �� �����.
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
	 * LOG : ��������, �������� ���� �����.
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
	 * DEBUG : debug�� ���� ������ �����. ��ÿ��� ������� �ʴ´�.
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
	 * ERROR : ���μ��� ���࿡ ġ������ ������ �ִ� error�� �����.
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
	 * �α�ó�� ���
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.21

			Logger.log("�̰��� LOG �Դϴ�. ���¸� ����ϰų� �����Ȳ�� ��Ÿ���ϴ�.");
			Logger.debug("�̰��� DEB �Դϴ�. debug�� ���� ������ �����. ��ÿ��� ������� �ʴ´�.");
			Logger.error("�̰��� ERR �Դϴ�. ���μ��� ���࿡ ġ������ ������ �ִ� error�� �����.");
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
			Exception e = new Exception("ERROR Exception() Hello, world...(����)");
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
	 * default test : �ܼ��� ���� ���
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
