package ic.vela.ibridge.base.param;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * �ý��� ȯ�溯�� ó�� Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeParameter
 * @author  ����
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * * �� ��ü�� ���α׷� ���ᰡ�� �����ϸ�
 *     ���μ������� �ʿ��� ������ �����Ѵ�.
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
			 * ȯ�漼�� ������ hashMap �� �����Ѵ�.
			 * UNIX : env ��ɾ�
			 * WIN : set ��ɾ�
			 */
			this.hashMap = new HashMap<String,String>(System.getenv());

			///////////////////////////////////////////////////////////
			/*
			 * System �� Properties �� hashMap�� �����Ѵ�.
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
			 * Base Folder : ���α׷��� �����ϱ� ���� ���� ����
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
			 * ������ �⺻ ����
			 */
			if (flag) {
				String strBasePath = this.hashMap.get(BASE_KEY);
				
				/*
				 * ib ����
				 */
				this.hashMap.put("ib.cfg"     , strBasePath + "ib/cfg/");
				this.hashMap.put("ib.cfg.prop", strBasePath + "ib/cfg/ib.properties");
				this.hashMap.put("ib.log"     , strBasePath + "ib/log/");
				this.hashMap.put("ib.log.file", strBasePath + "ib/log/YYYYMMDD.log");
				
				/*
				 * ap ����
				 */
				this.hashMap.put("ap.cfg"     , strBasePath + "ap/cfg/");
				this.hashMap.put("ap.cfg.prop", strBasePath + "ap/cfg/ap.properties");
				this.hashMap.put("ap.log"     , strBasePath + "ap/log/");
				this.hashMap.put("ap.log.file", strBasePath + "ap/log/YYYYMMDD.log");

				/*
				 * fep ����
				 */
				this.hashMap.put("fep.cfg"     , strBasePath + "fep/cfg/");
				this.hashMap.put("fep.cfg.prop", strBasePath + "fep/cfg/fep.properties");
				this.hashMap.put("fep.log"     , strBasePath + "fep/log/");
				this.hashMap.put("fep.log.file", strBasePath + "fep/log/YYYYMMDD.log");

				/*
				 * ext ����
				 */
				this.hashMap.put("ext.cfg"     , strBasePath + "ext/cfg/");
				this.hashMap.put("ext.cfg.prop", strBasePath + "ext/cfg/ext.properties");
				this.hashMap.put("ext.log"     , strBasePath + "ext/log/");
				this.hashMap.put("ext.log.file", strBasePath + "ext/log/YYYYMMDD.log");
			}
			
			/*
			 * ��������� ���縦 Ȯ���Ѵ�.
			 * �̰��� �� �������� ó���ϱ�� �Ѵ�.
			 * Manager ���� ó���Ѵ�.
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
	 * Ű���� �ش��ϴ� �Ķ���� ���� ��´�. 
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
