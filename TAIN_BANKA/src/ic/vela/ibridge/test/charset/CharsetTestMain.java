package ic.vela.ibridge.test.charset;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;


/*==================================================================*/
/**
 * CharsetTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    CharsetTestMain
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 확인해야할 사항
 * - System.out.print 에서 정상적으로 한글이 출력
 * - PrintStream 에서 정상적으로 한글이 출력
 * - log 파일을 정상적으로 tail -f 가능한지 확인
 * - log 파일을 Tailer.class 로 처리가능한지
 * - String <-> byte[] 처리에 이상이 없는지
 * - String <-> XML 변화에 이상이 없는지
 * 
 */
/*==================================================================*/
public class CharsetTestMain 
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
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFileName = "K:/WORK/kang.log";
		
		PrintStream ps = null;
		
		if (flag) {
			try {
				ps = new PrintStream(new FileOutputStream(strFileName, true));
				//ps = new PrintStream("D:/KANG/WORK/fep/kang.log", "EUC-KR");
				//ps = new PrintStream("D:/KANG/WORK/fep/kang.log", "UTF-8");
				
				ps.println("Hello, world......");
				ps.println("안녕하세요. 강석입니다.");
				ps.printf("[%-20s][%010d]\r\n", "강석입니다.", 1234);
				
				byte[] byteMsg = "이것은  한글입니다.\r\n".getBytes();
				ps.write(byteMsg, 0, byteMsg.length);                  // OK
				ps.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 : Enc(UTF-8) -> Dec(UTF-8)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes("UTF-8");
				
				String strDecode = new String(byteEncode, "UTF-8");
				
				System.out.println("1.[" + strTest + "] Enc(UTF-8) -> Dec(UTF-8) [" + strDecode + "]");
				ps.println("1.[" + strTest + "] Enc(UTF-8) -> Dec(UTF-8) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 : Enc(UTF-8) -> Dec(EUC-KR)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes("UTF-8");
				
				String strDecode = new String(byteEncode, "EUC-KR");
				
				System.out.println("2.[" + strTest + "] Enc(UTF-8) -> Dec(EUC-KR) [" + strDecode + "]");
				ps.println("2.[" + strTest + "] Enc(UTF-8) -> Dec(EUC-KR) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 : Enc(EUC-KR) -> Dec(UTF-8)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes("EUC-KR");
				
				String strDecode = new String(byteEncode, "UTF-8");
				
				System.out.println("3.[" + strTest + "] Enc(EUC-KR) -> Dec(UTF-8) [" + strDecode + "]");
				ps.println("3.[" + strTest + "] Enc(EUC-KR) -> Dec(UTF-8) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 : Enc(EUC-KR) -> Dec(EUC-KR)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes("EUC-KR");
				
				String strDecode = new String(byteEncode, "EUC-KR");
				
				System.out.println("4.[" + strTest + "] Enc(EUC-KR) -> Dec(EUC-KR) [" + strDecode + "]");
				ps.println("4.[" + strTest + "] Enc(EUC-KR) -> Dec(EUC-KR) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 :  -> Dec(UTF-8)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes();
				
				String strDecode = new String(byteEncode, "UTF-8");
				
				System.out.println("5.[" + strTest + "]  -> Dec(UTF-8) [" + strDecode + "]");
				ps.println("5.[" + strTest + "]  -> Dec(UTF-8) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 :  -> Dec(EUC-KR)
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes();
				
				String strDecode = new String(byteEncode, "EUC-KR");
				
				System.out.println("6.[" + strTest + "]  -> Dec(EUC-KR) [" + strDecode + "]");
				ps.println("6.[" + strTest + "]  -> Dec(EUC-KR) [" + strDecode + "]");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (flag) {  // DATE.2013.06.19 : 
			try {
				String strTest = "한글";
			
				byte[] byteEncode = strTest.getBytes();
				
				String strDecode = new String(byteEncode);
				
				System.out.println("7.[" + strTest + "]  [" + strDecode + "]");
				ps.println("7.[" + strTest + "]  [" + strDecode + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			
			if (flag) System.out.println("------- RandomAccessFile -----------");
			
			try {
				RandomAccessFile randFile = new RandomAccessFile(strFileName, "r");

				while (true) {
					String line = randFile.readLine();
					
					if (line == null)
						break;
					
					System.out.println(">" + line);
				}
				
				randFile.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			
			if (flag) System.out.println("--------- LineNumberReader ------------");
			
			try {
				LineNumberReader lnr = new LineNumberReader(new FileReader(strFileName));
				
				while (true) {
					String strLine = lnr.readLine();
					if (strLine == null)
						break;
					
					int iLine = lnr.getLineNumber();
					
					System.out.println("[" + iLine + "] " + strLine);
				}
				
				lnr.close();
			} catch (Exception e) {
				e.printStackTrace();
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
