package ic.vela.ibridge.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/*==================================================================*/
/**
 * 테스트 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    PropertyTestMain
 * @author  강석
 * @version 1.0, 2013/06/10
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class PropertyTestMain
/*==================================================================*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/**
	 * 테스트 프로세스 01
	 */
	/*------------------------------------------------------------------*/
	private void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {  // DATE.2013.06.10
			try {
				//Properties prop = new Properties();
				Properties prop = System.getProperties();
				prop.list(System.out);
				
				System.out.println(">dev.author=" + System.getProperty("dev.author", "imsi를 실행"));
				System.out.println(">dev.version=" + System.getProperty("dev.version", "난 몰라"));
				System.out.println(">CLASSPATH=" + System.getenv("CLASSPATH"));
				System.out.println(">CP=" + System.getenv("CP"));
				
				System.out.println(">user.dir=" + prop.getProperty("user.dir"));
				
				String fileName = null;
				fileName = prop.getProperty("user.dir") + "/imsi.xml";
				prop.storeToXML(new FileOutputStream(fileName), "This is the comment by Kang Seok about property.(XML)");
				prop.loadFromXML(new FileInputStream(fileName));
				
				fileName = prop.getProperty("user.dir") + "/imsi.prop";
				prop.store(new FileOutputStream(fileName), "This is the comment by Kang Seok about property.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {   // DATE.2013.06.10
			try {
				Properties systemProperties = System.getProperties();
				systemProperties.list(System.out);
				
				System.out.println("-------------------------------------------------------");
				
				Properties properties = new Properties();
				properties.load(new InputStreamReader(new FileInputStream("D:/KANG/WORK/server/ap/cfg/imsi.properties"), CHARSET));
				properties.list(System.out);
				
				String hostIp   = properties.getProperty("system.ib.param.host.ip","127.0.0.1");
				String hostPort = properties.getProperty("system.ib.param.host.port","3388");
				String hostType = properties.getProperty("system.ib.param.host.type","Client");
				
				System.out.println("[" + hostIp + ":" + hostPort + ":" + hostType + "]");

				properties.storeToXML(new FileOutputStream("D:/KANG/WORK/server/ap/cfg/imsi.properties.xml")
					, "This is the comment by Kang Seok about property.(XML)");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {    // DATE.2013.06.11
			try {
				
				Properties properties = new Properties();
				properties.loadFromXML(new FileInputStream("D:/KANG/WORK/server/ap/cfg/imsi.properties.xml"));
				properties.list(System.out);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
				new PropertyTestMain().test01();
			}
			break;
		default:
			if (flag) {
				System.out.println("ERROR : wrong switch number.");
			}
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/

