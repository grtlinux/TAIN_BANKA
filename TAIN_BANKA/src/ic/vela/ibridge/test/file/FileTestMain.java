package ic.vela.ibridge.test.file;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/*==================================================================*/
/**
 * FileTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    FileTestMain
 * @author  강석
 * @version 1.0, 2013/06/20
 * @since   jdk1.6.0_45
 * 
 * 
 * - File, FileFilter 사용
 * 
 */
/*==================================================================*/
public class FileTestMain 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/**
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {
			// 대상폴더 : D:/KANG/WORK/webCash/data
			
			try {
				
				File fileDir = new File("D:/KANG/WORK/webCash/data");
				
				if (fileDir.isDirectory()) {
					System.out.println("MAIN : Directory...[" + fileDir.getName() + "]" + fileDir.toString());
				} else {
					System.out.println("MAIN : File...[" + fileDir.getName() + "]" + fileDir.toString());
				}
				
				File[] files = fileDir.listFiles();
				
				for (int i=0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						System.out.println("SUB : Directory....[" + files[i].getName() + "]" + files[i].toString());
					} else if (files[i].isFile()) {
						System.out.println("SUB : File....[" + files[i].getName() + "]" + files[i].toString());
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!flag) {
			// 대상폴더 : D:/KANG/WORK/webCash/data
			/*
				String strDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(0));
			 */
			try {
				
				File fileDir = new File("D:/KANG/WORK/webCash/data");
				
				if (fileDir.isDirectory()) {
					System.out.println("[" + fileDir.toString() + "]" + "[" + fileDir.getName() + "]" + fileDir.lastModified());
				} else {
					System.out.println("[" + fileDir.toString() + "]" + "[" + fileDir.getName() + "]" + fileDir.lastModified());
				}
				
				File[] files = fileDir.listFiles();
				
				for (int i=0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						System.out.println("SUB : [" + files[i].toString() + "]" + "[" + files[i].getName() + "]" + files[i].lastModified());
					} else if (files[i].isFile()) {
						System.out.println("SUB : [" + files[i].toString() + "]" + "[" + files[i].getName() + "]" + files[i].lastModified());

						String strTodayDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
						String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(files[i].lastModified()));
						System.out.println("      [" + strTodayDate + "] [" + strLastModifiedDate + "]");
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {
			// 대상폴더 : D:/KANG/WORK/webCash/data
			/*
				String strDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(0));
			 */
			try {
				
				File fileDir = new File("D:/KANG/WORK/webCash/data");

				if (flag) {
					if (fileDir.isDirectory()) {
						System.out.println("[" + fileDir.toString() + "]" + "[" + fileDir.getName() + "]" + fileDir.lastModified());
					} else {
						System.out.println("[" + fileDir.toString() + "]" + "[" + fileDir.getName() + "]" + fileDir.lastModified());
					}
				}
				
				File[] files = fileDir.listFiles(
						new FileFilter() {
							public boolean accept(File file) {
								String strCompareDate = new SimpleDateFormat("yyyyMMdd000000", Locale.KOREA).format(new Date());
								String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
								
								strCompareDate = "20130515000000";
								System.out.println("      [" + strCompareDate + "] [" + strLastModifiedDate + "]");

								if (strCompareDate.compareTo(strLastModifiedDate) < 0) {
									return true;
								} else {
									return false;
								}
							}
						}
						);
				
				for (int i=0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						System.out.println("SUB : [" + files[i].toString() + "]" + "[" + files[i].getName() + "]" + files[i].lastModified());
					} else if (files[i].isFile()) {
						System.out.println("SUB : [" + files[i].toString() + "]" + "[" + files[i].getName() + "]" + files[i].lastModified());

						String strTodayDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
						String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(files[i].lastModified()));
						System.out.println("      [" + strTodayDate + "] [" + strLastModifiedDate + "]");
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * test02
	 */
	/*------------------------------------------------------------------*/
	private static void test02() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			String strBatchFileFolder = "D:/KANG/WORK/workspace/IB_Vela.2/server/webcash/ap/data/YYYYMMDD";
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			String strFileFolder = strBatchFileFolder.replace("YYYYMMDD", strDate);
			
			File dir = new File(strFileFolder);
			if (!dir.exists()) {
				/*
				 * 폴더가 존재하지 않으면 폴더를 생성한다.
				 */
				if (dir.mkdirs()) {
					if (flag) System.out.println("SUCCESS : create the folder. [" + strFileFolder + "]");
				} else {
					if (flag) System.out.println("FAIL : couldn't create the folder. [" + strFileFolder + "]");
				}
			} else {
				if (flag) System.out.println("STATUS : already exists...[" + strFileFolder + "]");
			}
			
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
		
		switch (2) {
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
