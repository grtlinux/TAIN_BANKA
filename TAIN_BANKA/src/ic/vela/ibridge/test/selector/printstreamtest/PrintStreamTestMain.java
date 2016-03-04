package ic.vela.ibridge.test.selector.printstreamtest;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintStream;

/*==================================================================*/
/**
 * �׽�Ʈ Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    PrintStreamTestMain
 * @author  ����
 * @version 1.0, 2013/06/13
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class PrintStreamTestMain 
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
	 * test method
	 */
	/*------------------------------------------------------------------*/
	private static void test01()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.13
			try {
				
				String strFileName = "K:/WORK/kang.log";

				String strCharSet = null;
				
				if (!flag) strCharSet = "EUC-KR";
				else      strCharSet = "UTF-8";
				
				PrintStream ps = new PrintStream(strFileName, strCharSet);
				
				ps.println("Hello, world......");
				ps.println("�ȳ��ϼ���. �����Դϴ�.");
				ps.printf("[%-20s][%010d]\n", "�����Դϴ�.", 1234);
				
				byte[] byteMsg = "�̰���  �ѱ��Դϴ�.\n".getBytes();
				ps.write(byteMsg, 0, byteMsg.length);                  // OK
				ps.flush();
				
				ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {   // DATE.2013.06.14
			/**
			 * �׽�Ʈ ���  -> ��� : PrintStream(UTF-8), write(byte)
			 * 
			 * charSet                          EUC-KR      UTF-8
			 * 
			 * eclipse Console  println           X           O
			 *                  write(byte)       O           O
			 * 
			 * DOSâ ����       println           O           X
			 *                  write(byte)       O           O
			 * 
			 * UltraEdit        println           O           X
			 *                  write(byte)       O           O
			 * 
			 * NotePad          println           O           X
			 *                  write(byte)       O           O
			 * 
			 */
			
			try {
				
				String strFileName = "K:/WORK/kang.log";

				LineNumberReader lnr = new LineNumberReader(new FileReader(strFileName));
				
				while (flag) {
					
					int iNoLine = lnr.getLineNumber();
					String strLine = lnr.readLine();
					
					if (strLine == null) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
						
						continue;
					}
					
					System.out.println("[" + iNoLine + "] > " + strLine);
				}
				
				lnr.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * default method
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
		}
	}
	
	/*==================================================================*/
	/**
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args) {
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
