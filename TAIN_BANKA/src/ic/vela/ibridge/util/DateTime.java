package ic.vela.ibridge.util;

/*--------------------------------------------------------------------------*/
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/*--------------------------------------------------------------------------*/

/*==================================================================*/
/**
 * ��¥ó���� ���õ� Ŭ����
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: enSof technology</p>
 *
 * @author  ����
 * @version 1.0, 2005/07/18
 * @since   JDK4.2
 */
/*==================================================================*/
public class DateTime 
/*------------------------------------------------------------------*/
{
	private static final long       MILLISEC_DAY = 24 * 60 * 60 * 1000;
	
	private static SimpleDateFormat df = null;
	private static SimpleDateFormat df2 = null;
	private static SimpleDateFormat df3 = null;
	private static SimpleDateFormat tf = null;
	private static SimpleDateFormat tf2 = null;
	// 20070725 �߰�
	private static SimpleDateFormat tf3 = null;
	
	private static SimpleDateFormat dtf = null;
	private static SimpleDateFormat dtf2 = null;
	
	// 20050902 �߰�
	private static SimpleDateFormat dtf3 = null;
	private static SimpleDateFormat dtf4 = null;
	private static SimpleDateFormat dtf5 = null;
	

	static {
		try {
			df = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
			df2 = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			df3 = new SimpleDateFormat("yyMMdd", Locale.KOREA);
			tf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.KOREA);
			tf2 = new SimpleDateFormat("HHmmssSSS", Locale.KOREA);
			// 20070725 �߰�
			tf3 = new SimpleDateFormat("HHmmss", Locale.KOREA);
			
			dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS", Locale.KOREA);
			dtf2 = new SimpleDateFormat("yyyyMMdd HHmmssSSS", Locale.KOREA);
			
			// 20050902 �߰�
			dtf3 = new SimpleDateFormat("MM/dd HH:mm:ss", Locale.KOREA);
			dtf4 = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
			dtf5 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*======================================================================*/
	/**
	 * ��¥���ڿ��� ��´�. 
	 *     true : 2005/08/10 
	 *     false : 20050810  
	 * @param flagsep ���й��ڸ� ���� �÷���
	 * @return ��¥ ���ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getDate(int type) 
    /*----------------------------------------------------------------------*/
	{
		switch (type) {
		case 0:
			return df.format(new Date());
		case 1:
			return df2.format(new Date());
		case 2:
			return df3.format(new Date());
		default:
			return "";
		}
	}

	/*======================================================================*/
	/**
	 * ���� ��¥�� ���� ��¥�� ���ڿ��� ��´�. 
	 *     true : 2005/08/10 
	 *     false : 20050810  
	 * @param flagsep ���й��ڸ� ���� �÷���
	 * @return ��¥ ���ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getDateAdd(int num, boolean flagsep) 
    /*----------------------------------------------------------------------*/
	{
		//Date dt = new Date(new Date().getTime() + (long)num * 1000 * 60 * 60 * 24);
		Date dt = new Date();
		long msec = dt.getTime();
		msec += (long)num * 1000 * 60 * 60 * 24;
		dt.setTime(msec);
		
		if (flagsep)
			return df.format(dt);
		else
			return df2.format(dt);
	}

	/*======================================================================*/
	/**
	 * �ð����ڿ��� ��´�. 
	 *     true : 13:43:52.231 
	 *     false : 134352231
	 * @param flagsep ���й��ڸ� ���� �÷���
	 * @return �ð����ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getTime(int type) 
    /*----------------------------------------------------------------------*/
	{
		switch (type) {
		case 0:
			return tf.format(new Date());
		case 1:
			return tf2.format(new Date());
		case 2:
			return tf3.format(new Date());
		default:
			return "";
		}
	}

	/*======================================================================*/
	/**
	 * ��¥�ð����ڿ��� ��´�. 
	 *     true : 2005/08/10 13:43:52.231
	 *     false : 20050810 134352231
	 * @param flagsep ���й��ڸ� ���� �÷���
	 * @return ��¥�ð����ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getDateTime(boolean flagsep) 
    /*----------------------------------------------------------------------*/
	{
		if (flagsep)
			return dtf.format(new Date());
		else
			return dtf2.format(new Date());
	}
	
	/*======================================================================*/
	/**
	 * ��¥�ð����ڿ��� ��´�. 
	 *     true : 2005/08/10 13:43:52.231
	 *     false : 20050810 134352231
	 * @param flagsep ���й��ڸ� ���� �÷���
	 * @return ��¥�ð����ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getDateTime() 
    /*----------------------------------------------------------------------*/
	{
		return dtf5.format(new Date());
	}
	
	/*======================================================================*/
	/**
	 * Queue������ ���� �ð��� �����Ѵ�.
	 * @param flagdate ��¥��� ���� true:��/�� ��:��:�� false:��:��:��
	 * @return �ñ����� ���ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getQueueTime(boolean flagdate)
    /*----------------------------------------------------------------------*/
	{
		if (flagdate)
			return dtf3.format(new Date());
		else
			return dtf4.format(new Date());
	}
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	/*======================================================================*/
	/**
	 * ������� ���ڿ��� ��´�.
	 * @return ���ڿ�
	 */
    /*----------------------------------------------------------------------*/
	public static String getFormatDate(String format) 
    /*----------------------------------------------------------------------*/
	{
		SimpleDateFormat sf = new SimpleDateFormat(format, Locale.KOREA);
		return sf.format(new Date());
	}
	
	/*======================================================================*/
	/**
	 * �ش糯¥�� �´��� Ȯ���Ѵ�. �Է����´� 'yyyyMMdd'�̴�.
	 * @param strDate ������ ���ڿ�
	 * @return ��¥�� ������ true, �ƴϸ� false
	 */
    /*----------------------------------------------------------------------*/
	public static boolean check(String strDate)
    /*----------------------------------------------------------------------*/
	{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			if (sdf.format(sdf.parse(strDate)).equals(strDate))
				return true;
		} catch (Exception e) {}
		
		return false;
	}

	/*======================================================================*/
	/**
	 * ���ڳ�¥�� ��, ��, ���� ���� ��¥�� ���Ѵ�.
	 * @param strDate ��¥�� ���ڿ� 'yyyyMMdd'
	 * @param year ���� ��
	 * @param month ���� ��
	 * @param day ���� ��
	 * @return ó���� ��¥
	 */
	/*----------------------------------------------------------------------*/
	public static String addDate(String strDate, int year, int month, int day)
    /*----------------------------------------------------------------------*/
	{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			sdf.parse(strDate);
			
			Calendar cal = sdf.getCalendar();
			cal.add(Calendar.YEAR, year);
			cal.add(Calendar.MONTH, month);
			cal.add(Calendar.DAY_OF_MONTH, day);
			
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return null;
	}
	
	/*======================================================================*/
	/**
	 * ��¥�� ���� ���� ��¥�� ���Ѵ�.
	 * @param strDate ��¥�� ���ڿ� 'yyyyMMdd'
	 * @param year ���� ��
	 * @return ó���� ��¥
	 */
	/*----------------------------------------------------------------------*/
	public static String addYears(String strDate, int year)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, year, 0, 0);
	}
	
	/*======================================================================*/
	/**
	 * ��¥�� ���� ���� ��¥�� ���Ѵ�.
	 * @param strDate ��¥�� ���ڿ� 'yyyyMMdd'
	 * @param month ���� ��
	 * @return ó���� ��¥
	 */
	/*----------------------------------------------------------------------*/
	public static String addMonths(String strDate, int month)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, 0, month, 0);
	}
	
	/*======================================================================*/
	/**
	 * ��¥�� ���� ���� ��¥�� ���Ѵ�.
	 * @param strDate ��¥�� ���ڿ� 'yyyyMMdd'
	 * @param days ���� ��
	 * @return ó���� ��¥
	 */
	/*----------------------------------------------------------------------*/
	public static String addDays(String strDate, int days)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, 0, 0, days);
	}
	
	/*======================================================================*/
	/**
	 * �γ�¥�� ���̸� ���ڷ� ���Ѵ�. 
	 * @param strDate1 ��¥1 YYYYMMDD
	 * @param strDate2 ��¥2 YYYYMMDD
	 * @return ��������
	 */
    /*----------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public static int diffDates(String strDate1, String strDate2)
    /*----------------------------------------------------------------------*/
	{
		try {
			if (false) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
				Date dt1 = sdf.parse(strDate1);
				Date dt2 = sdf.parse(strDate2);
				long val1 = dt1.getTime();
				long val2 = dt2.getTime();
				
				long diff = Math.abs(val1 - val2);
				
				long diffDays = diff / MILLISEC_DAY;
				
				return (int) diffDays;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
				return (int) (Math.abs(sdf.parse(strDate1).getTime() - sdf.parse(strDate2).getTime()) / MILLISEC_DAY);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return -1;
	}
	
	/*==================================================================*/
	/**
	 * ��¥�� YYYYMMDD ���·� ��´�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static String getStrYYYYMMDD()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strYYYYMMDD = null;
		
		if (flag) {
			strYYYYMMDD = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
		}
		
		return strYYYYMMDD;
	}
	
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	/*======================================================================*/
	public static void main(String[] args)
	{
		switch (7) {
		case 0:
			{
				// ���� ���� ���� Ȯ��
				String[] fm = {
					"yyyy/MM/dd HH:mm:ss.SSS",
					"yyMMdd HHmmss",
					"y/M/d H:m:s",
					"GG yyyyy/MM/dd ww W DDD dd F EEE aa HH kk K h mm ss SSS z Z",
				};

				for (int i=0; i < fm.length; i++) {
					System.out.println("(" + i + ") [" + DateTime.getFormatDate(fm[i]) + "]");
				}
			}
			break;

		case 1:
			{
				// ������� �´��� Ȯ��
				String[] fm = {
					"20071212",
					"20071231",
					"20071131",
					"20070231",
					"20070131",
					"20071331",
					"20071232",
				};
				
				for (int i=0; i < fm.length; i++) {
					System.out.println("(" + i + ") [" + fm[i] + "] -> [" + DateTime.check(fm[i]) + "]");
				}
			}
			break;
			
		case 2:
			{
				// ����� ����
				String fm = "20071230";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
				try {
					Date dt = sdf.parse(fm);
					long msDate = dt.getTime();
					msDate += (25 * MILLISEC_DAY);
					dt = new Date(msDate);
					//dt = new Date(1 * 24 * 60 * 60 * 1000);
					System.out.println("[" + sdf.format(dt) + "]");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
			
		case 3:
			{
				// java.util.Calender�� �̿��� ����� ����
				try {
					System.out.println("[" + DateTime.addDate("20071230", 1, 0, 0) + "]");
					System.out.println("[" + DateTime.addDate("20071230", 0, 2, 5) + "]");
					System.out.println("[" + DateTime.addDate("20071230", 0, 0, 34) + "]");
					System.out.println("[" + DateTime.addDate("20071232", 0, 0, 34) + "]");
					System.out.println();
					System.out.println("[" + DateTime.addYears("20071230", -1) + "]");
					System.out.println("[" + DateTime.addMonths("20071230", 2) + "]");
					System.out.println("[" + DateTime.addDays("20071230", 34) + "]");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
			
		case 4:
			{
				// �� ��¥�� ���̸� ���Ѵ�.
				try {
					System.out.println("[" + DateTime.diffDates("20071230", "20071229") + "]");
					System.out.println("[" + DateTime.diffDates("20071229", "20071230") + "]");
					System.out.println("[" + DateTime.diffDates("20071229", "20081230") + "]");
					System.out.println("[" + DateTime.diffDates("20071229", "20071130") + "]");
					System.out.println("[" + DateTime.diffDates("20091229", "20071230") + "]");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		
		case 5:
			{
				String num = new DecimalFormat("00000").format(123);
				System.out.println(">" + num);
			}
			break;

		case 6:
			{
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				System.out.println(">" + "123456789_YYYYMMDD_ABCDEFG".replace("YYYYMMDD", strDate));
			}
			break;
			
		case 7:
			{
				String strDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(0));
				System.out.println(">" + "123456789_YYYYMMDD_ABCDEFG".replace("YYYYMMDD", strDate));
			}
			break;
		default:
			System.out.println("DEFAULT....");
			break;
		}
	}
}
/*==========================================================================*/
