package ic.vela.ibridge.base.param;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * 시스템 환경변수 처리 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeParameter
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * * 이 객체는 프로그램 종료가지 존재하며
 *     프로세스에서 필요한 정보를 저장한다.
 * 
 */
/*==================================================================*/
public class IBridgeParameter
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        BASE_KEY            = "system.ib.param.basefolder";
	private static final String        BASE_PATH           = "D:/KANG/WORK/workspace/IB_Vela.2/server/";

	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static IBridgeParameter ibParam = null;

	private HashMap<String,String>     hashMap             = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	private IBridgeParameter()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.22
			
			///////////////////////////////////////////////////////////
			/*
			 * 환경세팅 정보를 hashMap 에 저장한다.
			 * UNIX : env 명령어
			 * WIN : set 명령어
			 */
			this.hashMap = new HashMap<String,String>(System.getenv());

			///////////////////////////////////////////////////////////
			/*
			 * System 의 Properties 를 hashMap에 저장한다.
			 */
			if (flag) {
				Properties prop = System.getProperties();
				
				Set<String> setKeys = prop.stringPropertyNames();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					String value = prop.getProperty(key);
					
					this.hashMap.put(key, value);
				}
			}
			
			///////////////////////////////////////////////////////////
			/*
			 * Base Folder : 프로그램이 시작하기 위한 최초 정보
			 * 
			 *     -Dsystem.ib.param.basefolder=[.../server/]
			 * 
			 */
			if (flag) {
				String strBasePath = this.hashMap.get(BASE_KEY);
				if (strBasePath == null || "".equals(strBasePath)) {
					this.hashMap.put(BASE_KEY, BASE_PATH);
				}
			}
			
			///////////////////////////////////////////////////////////
			/*
			 * 서버의 기본 폴더
			 */
			if (flag) {
				String strBasePath = this.hashMap.get(BASE_KEY);
				
				/*
				 * ib 정보
				 */
				this.hashMap.put("ib.cfg"     , strBasePath + "ib/cfg/");
				this.hashMap.put("ib.cfg.prop", strBasePath + "ib/cfg/ib.properties");
				this.hashMap.put("ib.log"     , strBasePath + "ib/log/");
				this.hashMap.put("ib.log.file", strBasePath + "ib/log/YYYYMMDD.log");
				
				/*
				 * ap 정보
				 */
				this.hashMap.put("ap.cfg"     , strBasePath + "ap/cfg/");
				this.hashMap.put("ap.cfg.prop", strBasePath + "ap/cfg/ap.properties");
				this.hashMap.put("ap.log"     , strBasePath + "ap/log/");
				this.hashMap.put("ap.log.file", strBasePath + "ap/log/YYYYMMDD.log");

				/*
				 * fep 정보
				 */
				this.hashMap.put("fep.cfg"     , strBasePath + "fep/cfg/");
				this.hashMap.put("fep.cfg.prop", strBasePath + "fep/cfg/fep.properties");
				this.hashMap.put("fep.log"     , strBasePath + "fep/log/");
				this.hashMap.put("fep.log.file", strBasePath + "fep/log/YYYYMMDD.log");

				/*
				 * ext 정보
				 */
				this.hashMap.put("ext.cfg"     , strBasePath + "ext/cfg/");
				this.hashMap.put("ext.cfg.prop", strBasePath + "ext/cfg/ext.properties");
				this.hashMap.put("ext.log"     , strBasePath + "ext/log/");
				this.hashMap.put("ext.log.file", strBasePath + "ext/log/YYYYMMDD.log");
			}
			
			/*
			 * 사용폴더의 존재를 확인한다.
			 * 이것은 각 서버에서 처리하기로 한다.
			 * Manager 에서 처리한다.
			 */
			if (flag) {
				
			}
			
			///////////////////////////////////////////////////////////
			/*
			 * hostname
			 */
			if ("x86".equals(this.hashMap.get("os.arch"))) {
				this.hashMap.put("hostname", System.getenv("COMPUTERNAME"));
			} else {
				this.hashMap.put("hostname", System.getenv("HOSTNAME"));
			}
			
			///////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////
			// PRINT
			if (!flag) {
				
				Set<String> setKeys = this.hashMap.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					String value = this.hashMap.get(key);
					
					System.out.println(String.format("[%s=%s]", key, value));
				}
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * to get a instance of the IBridgeParameter
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public synchronized static IBridgeParameter getInstance()
	/*------------------------------------------------------------------*/
	{
		if (ibParam == null) {
			ibParam = new IBridgeParameter();
		}
		
		return ibParam;
	}

	/*==================================================================*/
	/**
	 * 키값에 해당하는 파라미터 값을 얻는다. 
	 * @param key
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public synchronized static String getParam(String key)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strValue = null;
		
		if (flag) {
			if (ibParam == null) {
				getInstance();
			}
			
			strValue = ibParam.hashMap.get(key);
		}
		
		return strValue;
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
				IBridgeParameter.getInstance();
			}
			break;
		case 2:
			if (flag) {
				String strHostName = IBridgeParameter.getParam("hostname");
				System.out.println("hostname = " + strHostName);
			}
			break;
		default:
			if (flag) {
				try {
					Properties prop = System.getProperties();
					prop.list(System.out);
					
					System.out.println("---------------------------");
					
					if ("x86".equals(prop.getProperty("os.arch"))) {
						System.out.println("HOSTNAME=" + System.getenv("COMPUTERNAME"));
					} else {
						System.out.println("HOSTNAME=" + System.getenv("HOSTNAME"));
					}
					
					System.out.println("---------------------------");
					
					HashMap<String,String> hashMap = new HashMap<String,String>(System.getenv());
					
					Set<String> setKeys = hashMap.keySet();
					Iterator<String> iterKeys = setKeys.iterator();
					
					while (iterKeys.hasNext()) {
						String strKey = iterKeys.next();
						String strValue = hashMap.get(strKey);
						
						System.out.println("[" + strKey + "=" + strValue + "]");
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}
}

/*==================================================================*/
/*------------------------------------------------------------------*/
