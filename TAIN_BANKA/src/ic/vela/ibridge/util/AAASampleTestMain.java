package ic.vela.ibridge.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*==================================================================*/
/**
 * 테스트 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    AAASampleTestMain
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 
 * - test01 : 날짜연산, 숫자포맷, replace, String.format
 * - RandomAccessFile 파일처리
 * - readLine method 확인
 * - throw new Exception, throws 처리
 * 
 */
/*==================================================================*/
public class AAASampleTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final long          MILLISEC_DAY        = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/**
	 * 날짜연산
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			// 년월일 연산
			String fm = "20071230";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			try {
				Date dt = sdf.parse(fm);
				long msDate = dt.getTime();
				msDate += (25 * MILLISEC_DAY);
				dt = new Date(msDate);
				//dt = new Date(1 * 24 * 60 * 60 * 1000);
				System.out.println("[" + sdf.format(dt) + "]"); // [20080124]
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			// 숫자포맷
			String num = new DecimalFormat("00000").format(123);
			System.out.println("[" + num + "]");  // [00123]
		}
		
		if (flag) {
			// String replace
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			System.out.println(">" + "123456789_YYYYMMDD_ABCDEFG".replace("YYYYMMDD", strDate));

			String fileName = "D:/work/log/YYYYMMDD/filename.log";
			String outFileName = fileName.replace("YYYYMMDD", strDate);
			
			System.out.println("[" + fileName + "] -> [" + outFileName + "]");
			// [D:/work/log/YYYYMMDD/filename.log] -> [D:/work/log/20130609/filename.log]
		}
		
		if (flag) {
			// String format
			String strHeader = String.format("%-10.10s%05d", "ABCD", 123);
			System.out.println("[" + strHeader + "]");  // [ABCD      00123]
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
		
		if (flag) {
			System.out.println("Default function");
			
			/*
			
			/////
			strFilePath = System.getProperty("sys.param.filepath", FILEPATH);

			/////
			if (!new File(strFileName).exists()) {
				log("(ERROR) ----- file not found (" + strFileName + ") -----");
				return false;
			}
			
			*/
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
