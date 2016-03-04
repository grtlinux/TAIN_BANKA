package ic.vela.ibridge.test.kim;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;


/*==================================================================*/
/**
 * KimTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    KimTestMain
 * @author  강석
 * @version 1.0, 2013/07/19
 * @since   jdk1.6.0_45
 * 
 * 
 * - accdb, mdb : select
 * - standard IO
 * - File : split -> DB
 * - Random + seq
 * 
 */
/*==================================================================*/
public class KimTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * test01 : accdb, mdb : select
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding 처리
			try {
				String strDbName = "jdbc:odbc:KimYoungChun";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from tbl_qst");
				ResultSetMetaData rsMeta = rs.getMetaData();
				int columnCount = rsMeta.getColumnCount();
				int iRowNum = 0;

				while (rs.next()) {
					iRowNum ++;
					System.out.println("Row Number [" + iRowNum + "]");

					for (int i=1; i <= columnCount; i++) {
						String strColumnName = rsMeta.getColumnName(i);
						String strText = rs.getString(i);

						//strText = new String(strText.getBytes("ISO-8859-1"), "EUC-KR");

						System.out.println("\t[" + i + "] [" + strColumnName + "=" + strText + "]");
					}
				}

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * test02 : standard IO
	 * 
	 * -Dfile.encoding=euc-kr
	 */
	/*------------------------------------------------------------------*/
	private static void test02() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strCharset = null;
		strCharset = "UTF-8";
		strCharset = "MS949";
		strCharset = "EUC-KR";
		
		if (!flag) {
			try {
				
				@SuppressWarnings("resource")
				//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileDescriptor.in)));
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileDescriptor.in), strCharset));
				
				while (true) {
					System.out.print("INPUT STRING : ");
					String strLine = reader.readLine();
					
					if (strLine == null || "QUIT".equalsIgnoreCase(strLine)) {
						System.out.println("GOOD BYE....................");
						break;
					}
					
					System.out.println();
					System.out.println("ANSWER : [" + strLine + "]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {   // DATE.2013.07.19
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, strCharset));
				
				while (true) {
					System.out.print("INPUT STRING : ");
					String strLine = reader.readLine();
					
					if (strLine == null || "QUIT".equalsIgnoreCase(strLine)) {
						System.out.println("GOOD BYE....................");
						break;
					}
					
					System.out.println();
					System.out.println("ANSWER : [" + strLine + "]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * test03 : File : split -> DB
	 */
	/*------------------------------------------------------------------*/
	private static void test03() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}
	
	/*==================================================================*/
	/**
	 * test04 : Random + seq
	 */
	/*------------------------------------------------------------------*/
	private static void test04() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				long longSeed = new Date().getTime();
				
				Random random = new Random(longSeed);
				
				for (int i=0; i < 10; i++) {
					int randNo = random.nextInt(100);
					String strKey = String.format("%03d%03d", randNo, i);
					
					System.out.println(String.format("%d) [%s]", i, strKey));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
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
		
		switch (4) {
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
		case 3:
			if (flag) {
				test03();
			}
			break;
		case 4:
			if (flag) {
				test04();
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
