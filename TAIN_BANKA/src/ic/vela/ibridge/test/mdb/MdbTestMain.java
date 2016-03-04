package ic.vela.ibridge.test.mdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;


/*==================================================================*/
/**
 * MdbTestMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    MdbTestMain
 * @author  ����
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
 * �׸��� ���⼭�� Properties�� ����Ͽ��µ� �̰� mdb�� �ѱ۹��������̴�.
 * mdb�� �ѱ��� 8859_1�� ����ϱ� ������ �ѱ��� �ȱ����� �޾ƿ��� ���ؼ���
 * Connection�� ������ �� charSet�� 8859_1�� ������ �־�� �Ѵ�
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
 * ���� Connection�� ����ؼ� Select ������ ������ �κ��̴�.
 * import���� �ʿ���� �κ��� �����ȴ�. ��������δ� 14��° ���θ� ���� �ȴ�.
 * �����̳� ���ڴ� �������� �ѱ��� ��� ���Ŀ�ؼǿ��� ����� �� Access����
 * �ѱ��� 8859_1(ISO-8859-1)�� ����߱� ������ ���⼭�� �޾ƿ� ��Ʈ���� 14��
 * ����ó�� ���ڵ��� ������ �־�� �޾ƿ� �ѱ��� ������ �ʰ� ǥ�õȴ�.
 * (Ư���ϰԵ� ������ encoding�� euc-kr�̵� utf-8�̵� �����ϰ� ��ó��
 * euc-kr�� ���ڵ��� ���־�� �ѱ��� ������ �ʴ´�.)
 *
 * INSERT�� UPDATE�� �ڹ��ʿ��� mdb�� �ѱ��� �������� �����ϰ� ���ڵ� Ÿ����
 * �����ؼ� �־��־�� �Ѵ�. �翬�� ���ڵ��� �ݴ�� �ؼ�...
 *
 *         String str = new String("�ѱ�".getBytes("euc-kr"), "ISO-8859-1");
 *
 * ��ó�� �ϸ� �ȴ�. ( ISO-8859-1�� 8859_1�� ������ ���ڵ��̴�.)
 *
 * ��� mdb�� ���� �ѱ��� �ְ� ���� ���� ������״ϱ� �� 2������ �޼����
 * ���� �����θ� ���Ұ��̴�.
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

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding ó��
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
				sb.append("     , '�̰��� INSERT�� �����߽��ϴ�.'");
				sb.append(" )                                    ");
				
				Statement query = db.createStatement();
				int ret = query.executeUpdate(sb.toString());
				
				System.out.println("ret = " + ret);

				db.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding ó��
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

		if (flag) {    // SELECT : DATE.2013.06.21 : charSet encoding decoding ó��
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
	 * default test : �ܼ��� ���� ���
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
