package ic.vela.ibridge.test.kim.score;

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
 * ScoreTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ScoreTestMain
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
public class ScoreTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        DEFAULT_FILE        = "D:/KANG/WORK/workspace/IB_Vela.2/mdb/tbl_usrans.txt";
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
				ResultSet rs = query.executeQuery("select * from tbl_usrans");
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
				String strSqlDeleteQST = "delete from tbl_usrans";
				statement.executeUpdate(strSqlDeleteQST);
				
				/*
				 * 데이터 파일을 오픈한다.
				 */
				String strDataFile = System.getProperty("system.ib.param.datafile.usrans", DEFAULT_FILE);
				
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
					if (item.length != 6)
						continue;
					
					String strUser     = item[0].replace("'", "`");
					String strGrp      = item[1].replace("'", "`");
					String strClas     = item[2].replace("'", "`");
					String strNo       = item[3].replace("'", "`");
					String strUserScr  = item[4].replace("'", "`");
					String strUserAns  = item[5].replace("'", "`");
					
					if (flag) {
						System.out.println("strUser     = [" + strUser    + "]");
						System.out.println("strGrp      = [" + strGrp     + "]");
						System.out.println("strClas     = [" + strClas    + "]");
						System.out.println("strNo       = [" + strNo      + "]");
						System.out.println("strUserScr  = [" + strUserScr + "]");
						System.out.println("strUserAns  = [" + strUserAns + "]");
						System.out.println("-----------------------------------------");
					}
					
					/*
					 * insert tbl_qst
					 */
					sb = new StringBuffer();
					sb.append(String.format(" INSERT INTO tbl_usrans ( ID, USER, GRP, CLAS, NO, USRSCORE, USRANS ) VALUES "));
					sb.append(String.format(" ( %d, '%s', %s, %s, %s, %s, '%s' )", id, strUser, strGrp, strClas, strNo, strUserScr, strUserAns));
					
					if (statement.executeUpdate(sb.toString()) > 0) {
						System.out.println("tbl_usrans : INSERT OK....");
					} else {
						System.out.println("tbl_usrans : INSERT FAIL....[" + sb.toString() + "]");
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
	 * test04 : 채점
	 */
	/*------------------------------------------------------------------*/
	private static void test04() 
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
				Statement updateStatement = db.createStatement();
				
				/*
				 * select tbl_usrans, tbl_ans
				 */
				StringBuffer sb = null;
				sb = new StringBuffer();
				sb.append(String.format(" SELECT a.[user], a.[grp], a.[clas], a.[no], a.[usrscore], a.[usrans], b.[score], b.[answer]"));
				sb.append(String.format(" FROM tbl_usrans AS a, tbl_ans AS b"));
				sb.append(String.format(" WHERE a.[clas]=b.[clas] And a.[no]=b.[no] And b.[seq]=1"));
				sb.append(String.format(" ORDER BY a.[user], a.[grp], a.[clas], a.[no]"));
				
				ResultSet rs = statement.executeQuery(sb.toString());

				if (!flag) {
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
					
					System.out.println("------------------------------------------");
				}

				if (!flag) {
					int iRowNum = 0;

					while (rs.next()) {
						iRowNum ++;
						System.out.println("Row Number [" + iRowNum + "]");

						String strUser     = rs.getString("USER");
						String strGrp      = rs.getString("GRP");
						String strClas     = rs.getString("CLAS");
						String strNo       = rs.getString("NO");
						String strUsrScore = rs.getString("USRSCORE");
						String strUsrAns   = rs.getString("USRANS");
						String strScore    = rs.getString("SCORE");
						String strAnswer   = rs.getString("ANSWER");
						
						if (flag) {
							System.out.println("\t strUser     = [" + strUser     + "]");
							System.out.println("\t strGrp      = [" + strGrp      + "]");
							System.out.println("\t strClas     = [" + strClas     + "]");
							System.out.println("\t strNo       = [" + strNo       + "]");
							System.out.println("\t strUsrScore = [" + strUsrScore + "]");
							System.out.println("\t strUsrAns   = [" + strUsrAns   + "]");
							System.out.println("\t strScore    = [" + strScore    + "]");
							System.out.println("\t strAnswer   = [" + strAnswer   + "]");
						}
					}
					
					System.out.println("------------------------------------------");
				}
				
				if (flag) {
					int iRowNum = 0;

					while (rs.next()) {
						iRowNum ++;
						System.out.println("Row Number [" + iRowNum + "]");

						String strUser     = rs.getString("USER");
						String strGrp      = rs.getString("GRP");
						String strClas     = rs.getString("CLAS");
						String strNo       = rs.getString("NO");
						//String strUsrScore = rs.getString("USRSCORE");
						String strUsrAns   = rs.getString("USRANS");
						String strScore    = rs.getString("SCORE");
						String strAnswer   = rs.getString("ANSWER");
						
						if (flag) {
							System.out.println("\t strUsrAns   = [" + strUsrAns   + "]");
							System.out.println("\t strScore    = [" + strScore    + "]");
							System.out.println("\t strAnswer   = [" + strAnswer   + "]");
						}
						
						if (strUsrAns.equals(strAnswer)) {
							/*
							 * 맞췄다. 점수를 update 한다.
							 */
							sb = new StringBuffer();
							sb.append(String.format(" UPDATE tbl_usrans AS a"));
							sb.append(String.format(" SET a.[usrscore] = %s", strScore));
							sb.append(String.format(" WHERE a.[user]='%s'", strUser));
							sb.append(String.format(" And a.[grp]=%s", strGrp));
							sb.append(String.format(" And a.[clas]=%s", strClas));
							sb.append(String.format(" And a.[no]=%s", strNo));

							if (updateStatement.executeUpdate(sb.toString()) > 0) {
								System.out.println("tbl_usrans : UPDATE OK....");
							} else {
								System.out.println("tbl_usrans : UPDATE FAIL....");
							}
							
							if (!flag) {
								System.out.println("Query...[" + sb.toString() + "]");
							}
						}
					}
					
					System.out.println("------------------------------------------");
				}
				
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
	 * test05 : 총점
	 */
	/*------------------------------------------------------------------*/
	private static void test05() 
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
				Statement updateStatement = db.createStatement();
				
				/*
				 * 기존의 자료를 삭제한다.
				 */
				String strSqlDeleteQST = "DELETE FROM tbl_totscr WHERE grp = 2";
				statement.executeUpdate(strSqlDeleteQST);
				
				/*
				 * select tbl_usrans, tbl_ans
				 */
				StringBuffer sb = null;
				sb = new StringBuffer();
				sb.append(String.format(" SELECT a.[user], a.[grp], Sum(a.[usrscore]) AS sumscore, Sum(b.[score]) AS totalscore"));
				sb.append(String.format(" FROM tbl_usrans AS a, tbl_ans AS b"));
				sb.append(String.format(" WHERE a.[clas]=b.[clas] And a.[no]=b.[no] And b.[seq]=1"));
				sb.append(String.format(" GROUP BY a.[user], a.[grp]"));
				
				ResultSet rs = statement.executeQuery(sb.toString());

				if (!flag) {
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
					
					System.out.println("------------------------------------------");
				}

				if (flag) {
					int id = 0;

					while (rs.next()) {
						id ++;
						System.out.println("id [" + id + "]");

						String strUser       = rs.getString("USER");
						String strGrp        = rs.getString("GRP");
						String strSumScore   = rs.getString("SUMSCORE");
						String strTotalScore = rs.getString("TOTALSCORE");
						
						if (flag) {
							System.out.println("\t strUser       = [" + strUser       + "]");
							System.out.println("\t strGrp        = [" + strGrp        + "]");
							System.out.println("\t strSumScore   = [" + strSumScore   + "]");
							System.out.println("\t strTotalScore = [" + strTotalScore + "]");
						}
						
						/*
						 * insert tbl_qst
						 */
						sb = new StringBuffer();
						sb.append(String.format(" INSERT INTO tbl_totscr ( ID, USER, GRP, SUMSCORE ) VALUES "));
						sb.append(String.format(" ( %d, '%s', %s, %s )", id, strUser, strGrp, strSumScore));
						
						if (updateStatement.executeUpdate(sb.toString()) > 0) {
							System.out.println("tbl_totscr : INSERT OK....");
						} else {
							System.out.println("tbl_totscr : INSERT FAIL....");
						}
						
						if (!flag) {
							System.out.println("Query...[" + sb.toString() + "]");
						}
					}
					
					System.out.println("------------------------------------------");
				}
				
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
		
		switch (5) {
		case 1:
			if (flag) {
				test01();
			}
			break;
		case 3:
			if (flag) {
				test03();   // File : split -> DB insert
			}
			break;
		case 4:
			if (flag) {
				test04();   // 채점
			}
			break;
		case 5:
			if (flag) {
				test05();   // 총점
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
