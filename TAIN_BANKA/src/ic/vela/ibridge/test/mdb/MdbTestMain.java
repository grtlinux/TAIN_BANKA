package ic.vela.ibridge.test.mdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;


/*==================================================================*/
/**
 * MdbTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    MdbTestMain
 * @author  강석
 * @version 1.0, 2013/06/21
 * @since   jdk1.6.0_45
 *
 *
 * -
 * Class.forName("org.gjt.mm.mysql.Driver");
 * c = DriverManager.getConnection("jdbc:mysql://localhost/tspc?useUnicode=true&characterEncoding=euckr", "root", "321");
 *
 * import java.sql.Connection;
 * import java.sql.DriverManager;
 * import java.sql.PreparedStatement;
 * import java.sql.ResultSet;
 * import java.sql.SQLException;
 * import java.sql.Statement;
 * import java.util.Properties;
 *
 * public class DBConnection {
 *     private String DB_URL = "Jdbc:Odbc:Testdata";
 *     private String DB_USER = "test";
 *     private String DB_PASSWORD= "test";
 *     Connection conn = null;
 *
 *     public Connection getConnection() {
 *
 *         try {
 *             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 *         } catch (ClassNotFoundException e1) {
 *             e1.printStackTrace();
 *         }
 *
 *         try {
 *             Properties props = new Properties();
 *             props.put("charSet", "8859_1" );
 *             props.put("user", DB_USER);
 *             props.put("password", DB_PASSWORD);
 *             conn = DriverManager.getConnection(DB_URL, props);
 *         } catch (SQLException e) {
 *             e.printStackTrace();
 *         }
 *
 *         return conn;
 *     }
 *
 *
 * 그리고 여기서는 Properties를 사용하였는데 이건 mdb의 한글문제때문이다.
 * mdb는 한글을 8859_1로 사용하기 때문에 한글을 안깨지고 받아오기 위해서는
 * Connection을 연결할 때 charSet을 8859_1로 설정해 주어야 한다
 *
 *
 *         DBConnection dbConn = new DBConnection();
 *         Connection conn = null;
 *         PreparedStatement psmt = null;
 *         ResultSet rs = null;
 *
 *         try {
 *             String sql = " SELECT username FROM member ";
 *             conn = dbConn.getConnection();
 *             psmt = conn.prepareStatement(sql);
 *             rs = psmt.executeQuery();
 *
 *             while (rs.next()) {
 *                 String username = new String(rs.getString("username").getBytes("8859_1"), "euc-kr");
 *             }
 *
 *         } catch(Exception e) {
 *             e.printStackTrace();
 *         } finally {
 *             dbConn.disConnection(rs,psmt, conn);
 *         }
 *
 * 이제 Connection을 사용해서 Select 쿼리를 날리는 부분이다.
 * import등의 필요없는 부분은 빼버렸다. 결과적으로는 14번째 라인만 보면 된다.
 * 영문이나 숫자는 괜찮지만 한글의 경우 디비커넥션에서 사용할 때 Access에서
 * 한글을 8859_1(ISO-8859-1)을 사용했기 때문에 여기서도 받아온 스트링을 14번
 * 라인처럼 엔코딩을 변경해 주어야 받아온 한글이 깨지지 않고 표시된다.
 * (특이하게도 문서의 encoding이 euc-kr이든 utf-8이든 동일하게 위처럼
 * euc-kr로 인코딩을 해주어야 한글이 깨지지 않는다.)
 *
 * INSERT나 UPDATE등 자바쪽에서 mdb로 한글을 넣을때도 동일하게 인코딩 타입을
 * 변경해서 넣어주어야 한다. 당연히 인코딩은 반대로 해서...
 *
 *         String str = new String("한글".getBytes("euc-kr"), "ISO-8859-1");
 *
 * 위처럼 하면 된다. ( ISO-8859-1와 8859_1는 동인한 인코딩이다.)
 *
 * 디비를 mdb를 쓰면 한글을 넣고 빼는 일은 빈번할테니까 위 2가지를 메서드로
 * 따로 만들어두면 편할것이다.
 *
 *
 *
 *
 */
/*==================================================================*/
public class MdbTestMain
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

	/*==================================================================*/
	/**
	 *
	 */
	/*------------------------------------------------------------------*/
	private static void test01()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (!flag) {    // DATE.2013.06.21
			try {
				String strDbName = "jdbc:odbc:BookCatalog";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				props.put("charSet", "8859_1");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from BookList");
				ResultSetMetaData rsMeta = rs.getMetaData();
				int columnCount = rsMeta.getColumnCount();
				int iRowNum = 0;

				while (rs.next()) {
					iRowNum ++;
					System.out.println("Row Number [" + iRowNum + "]");

					for (int i=1; i <= columnCount; i++) {
						String strColumnName = rsMeta.getColumnName(i);
						String strText = rs.getString(i);

						System.out.println("\t[" + i + "] [" + strColumnName + "=" + strText + "]");
					}
				}

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding 처리
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from kang");
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

		if (flag) {    // UPDATE : DATE.2013.06.21
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				StringBuffer sb = new StringBuffer();
				sb.append(" UPDATE            ");
				sb.append("     kang          ");
				sb.append(" SET               ");
				sb.append("     NAME = 'AAAAA'");
				sb.append(" WHERE             ");
				sb.append("     ID = 3        ");
				
				Statement query = db.createStatement();
				int ret = query.executeUpdate(sb.toString());
				
				System.out.println("ret = " + ret);

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // INSERT : DATE.2013.06.21
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				StringBuffer sb = new StringBuffer();
				sb.append(" INSERT INTO kang                     ");
				sb.append(" (                                    ");
				sb.append("     ID                               ");
				sb.append("     , NAME                           ");
				sb.append("     , CONTENT                        ");
				sb.append(" )                                    ");
				sb.append(" VALUES                               ");
				sb.append(" (                                    ");
				sb.append("     9                                ");
				sb.append("     , 'TABLE'                        ");
				sb.append("     , '이것은 INSERT로 생성했습니다.'");
				sb.append(" )                                    ");
				
				Statement query = db.createStatement();
				int ret = query.executeUpdate(sb.toString());
				
				System.out.println("ret = " + ret);

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding 처리
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from kang");
				ResultSetMetaData rsMeta = rs.getMetaData();
				int columnCount = rsMeta.getColumnCount();
				int iRowNum = 0;

				while (rs.next()) {
					iRowNum ++;
					System.out.println("Row Number [" + iRowNum + "]");

					for (int i=1; i <= columnCount; i++) {
						String strColumnName = rsMeta.getColumnName(i);
						String strText = rs.getString(i);

						System.out.println("\t[" + i + "] [" + strColumnName + "=" + strText + "]");
					}
				}

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // DELETE : DATE.2013.06.21
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				StringBuffer sb = new StringBuffer();
				sb.append(" DELETE FROM       ");
				sb.append("     kang          ");
				sb.append(" WHERE             ");
				sb.append("     ID = 9        ");
				
				Statement query = db.createStatement();
				int ret = query.executeUpdate(sb.toString());
				
				System.out.println("ret = " + ret);

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding 처리
			try {
				String strDbName = "jdbc:odbc:Kang01";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Properties props = new Properties();
				//props.put("charSet", "8859_1");
				props.put("charSet", "EUC-KR");
				props.put("user", "");
				props.put("password", "");

				Connection db = DriverManager.getConnection(strDbName, props);

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from kang");
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

		if (!flag) {    // DATE.2013.06.21 : Excel Error TODO later
			try {
				String strDbName = "jdbc:odbc:KANG02";
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection db = DriverManager.getConnection(strDbName, "", "");

				Statement query = db.createStatement();
				ResultSet rs = query.executeQuery("select * from KANGTBL");
				ResultSetMetaData rsMeta = rs.getMetaData();
				int columnCount = rsMeta.getColumnCount();
				int iRowNum = 0;

				while (rs.next()) {
					iRowNum ++;
					System.out.println("Row Number [" + iRowNum + "]");

					for (int i=1; i <= columnCount; i++) {
						String strColumnName = rsMeta.getColumnName(i);
						String strText = rs.getString(i);

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
