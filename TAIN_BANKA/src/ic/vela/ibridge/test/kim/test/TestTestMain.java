package ic.vela.ibridge.test.kim.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

/*==================================================================*/
/**
 * TestTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    TestTestMain
 * @author  강석
 * @version 1.0, 2013/07/19
 * @since   jdk1.6.0_45
 * 
 * 
 * - standard IO
 * - File : split -> DB
 * - Random + seq
 * - 
 * 
 */
/*==================================================================*/
public class TestTestMain
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        DEFAULT_FILE        = "D:/KANG/WORK/workspace/IB_Vela.2/mdb/tbl_tstgrp.txt";
	private static final String        CHARSET             = "EUC-KR";
	
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
	 * test01 : 
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
				ResultSet rs = query.executeQuery("select * from tbl_tstgrp");
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
	 * test03 : File : split -> DB insert
	 */
	/*------------------------------------------------------------------*/
	private static void test03() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
			try {
				/*
				 * DB를 오픈한다.
				 */
				String strDbName = "jdbc:odbc:KimYoungChun";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);
				Statement statement = db.createStatement();
				
				/*
				 * 테이블의 기존자료를 삭제한다.
				 */
				String strSqlDeleteQST = "delete from tbl_tstgrp";
				statement.executeUpdate(strSqlDeleteQST);
				
				/*
				 * 데이터 파일을 오픈한다.
				 */
				String strDataFile = System.getProperty("system.ib.param.datafile.tstgrp", DEFAULT_FILE);
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(strDataFile), CHARSET));
				String strLine = null;
				
				/*
				 * 데이터 파일의 한라인을 읽으며 insert 처리를 한다.
				 */
				int id = 0;
				StringBuffer sb = null;
				while ((strLine=bufferedReader.readLine()) != null) {
					id ++;
					
					if (!flag) {
						System.out.println("[" + strLine + "]");
					}
					
					strLine = strLine.trim();
					String[] item = strLine.split(";");
					if (item.length != 4)
						continue;
					
					String strGrp      = item[0].replace("'", "`");
					String strClas     = item[1].replace("'", "`");
					String strNo       = item[2].replace("'", "`");
					String strTestDate = item[3].replace("'", "`");
					
					if (flag) {
						System.out.println("strGrp      = [" + strGrp      + "]");
						System.out.println("strClas     = [" + strClas     + "]");
						System.out.println("strNo       = [" + strNo       + "]");
						System.out.println("strTestDate = [" + strTestDate + "]");
						System.out.println("-----------------------------------------");
					}
					
					/*
					 * insert tbl_qst
					 */
					sb = new StringBuffer();
					sb.append(String.format(" INSERT INTO tbl_tstgrp ( ID, GRP, CLAS, NO, TESTDATE ) VALUES "));
					sb.append(String.format(" ( %d, %s, %s, %s, '%s' )", id, strGrp, strClas, strNo, strTestDate));
					
					if (statement.executeUpdate(sb.toString()) > 0) {
						System.out.println("tbl_tstgrp : INSERT OK....");
					} else {
						System.out.println("tbl_tstgrp : INSERT FAIL....[" + sb.toString() + "]");
					}
				}
				
				/*
				 * 데이터 파일을 클로즈한다.
				 */
				bufferedReader.close();
				
				/*
				 * DB를 클로즈한다.
				 */
				db.close();
				
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
		
		switch (3) {
		case 1:
			if (flag) {
				test01();
			}
			break;
		case 3:
			if (flag) {
				test03();
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
