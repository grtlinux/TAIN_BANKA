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
 * 날짜처리에 관련된 클래스
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: enSof technology</p>
 *
 * @author  강석
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
	// 20070725 추가
	private static SimpleDateFormat tf3 = null;
	
	private static SimpleDateFormat dtf = null;
	private static SimpleDateFormat dtf2 = null;
	
	// 20050902 추가
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
			// 20070725 추가
			tf3 = new SimpleDateFormat("HHmmss", Locale.KOREA);
			
			dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS", Locale.KOREA);
			dtf2 = new SimpleDateFormat("yyyyMMdd HHmmssSSS", Locale.KOREA);
			
			// 20050902 추가
			dtf3 = new SimpleDateFormat("MM/dd HH:mm:ss", Locale.KOREA);
			dtf4 = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
			dtf5 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*======================================================================*/
	/**
	 * 날짜문자열을 얻는다. 
	 *     true : 2005/08/10 
	 *     false : 20050810  
	 * @param flagsep 구분문자를 삽입 플래그
	 * @return 날짜 문자열
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
	 * 오늘 날짜에 더한 날짜의 문자열을 얻는다. 
	 *     true : 2005/08/10 
	 *     false : 20050810  
	 * @param flagsep 구분문자를 삽입 플래그
	 * @return 날짜 문자열
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
	 * 시간문자열을 얻는다. 
	 *     true : 13:43:52.231 
	 *     false : 134352231
	 * @param flagsep 구분문자를 삽입 플래그
	 * @return 시간문자열
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
	 * 날짜시간문자열을 얻는다. 
	 *     true : 2005/08/10 13:43:52.231
	 *     false : 20050810 134352231
	 * @param flagsep 구분문자를 삽입 플래그
	 * @return 날짜시간문자열
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
	 * 날짜시간문자열을 얻는다. 
	 *     true : 2005/08/10 13:43:52.231
	 *     false : 20050810 134352231
	 * @param flagsep 구분문자를 삽입 플래그
	 * @return 날짜시간문자열
	 */
    /*----------------------------------------------------------------------*/
	public static String getDateTime() 
    /*----------------------------------------------------------------------*/
	{
		return dtf5.format(new Date());
	}
	
	/*======================================================================*/
	/**
	 * Queue정보를 읽을 시간을 정의한다.
	 * @param flagdate 날짜출력 여부 true:월/일 시:분:초 false:시:분:초
	 * @return 시긴정보 문자열
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
	 * 포멧따른 문자열을 얻는다.
	 * @return 문자열
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
	 * 해당날짜가 맞는지 확인한다. 입력형태는 'yyyyMMdd'이다.
	 * @param strDate 날자형 문자열
	 * @return 날짜가 맞으면 true, 아니면 false
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
	 * 인자날짜에 년, 월, 일을 더한 날짜를 구한다.
	 * @param strDate 날짜형 문자열 'yyyyMMdd'
	 * @param year 더할 년
	 * @param month 더할 월
	 * @param day 더할 일
	 * @return 처리된 날짜
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
	 * 날짜의 년을 더한 날짜를 구한다.
	 * @param strDate 날짜형 문자열 'yyyyMMdd'
	 * @param year 더할 년
	 * @return 처리된 날짜
	 */
	/*----------------------------------------------------------------------*/
	public static String addYears(String strDate, int year)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, year, 0, 0);
	}
	
	/*======================================================================*/
	/**
	 * 날짜의 년을 더한 날짜를 구한다.
	 * @param strDate 날짜형 문자열 'yyyyMMdd'
	 * @param month 더할 월
	 * @return 처리된 날짜
	 */
	/*----------------------------------------------------------------------*/
	public static String addMonths(String strDate, int month)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, 0, month, 0);
	}
	
	/*======================================================================*/
	/**
	 * 날짜의 년을 더한 날짜를 구한다.
	 * @param strDate 날짜형 문자열 'yyyyMMdd'
	 * @param days 더할 일
	 * @return 처리된 날짜
	 */
	/*----------------------------------------------------------------------*/
	public static String addDays(String strDate, int days)
    /*----------------------------------------------------------------------*/
	{
		return addDate(strDate, 0, 0, days);
	}
	
	/*======================================================================*/
	/**
	 * 두날짜의 차이를 일자로 구한다. 
	 * @param strDate1 날짜1 YYYYMMDD
	 * @param strDate2 날짜2 YYYYMMDD
	 * @return 차이일자
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
	 * 날짜를 YYYYMMDD 형태로 얻는다.
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
				// 날자 포멧 문자 확인
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
				// 년월일이 맞는자 확인
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
				// 년월일 연산
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
				// java.util.Calender를 이용한 년월일 연산
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
				// 두 날짜의 차이를 구한다.
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
