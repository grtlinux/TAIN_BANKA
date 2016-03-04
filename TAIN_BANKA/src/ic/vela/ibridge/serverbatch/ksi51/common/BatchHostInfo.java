package ic.vela.ibridge.serverbatch.ksi51.common;

/*==================================================================*/
/**
 * BatchHostInfo 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchHostInfo
 * @author  강석
 * @version 1.0, 2013/07/06
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class BatchHostInfo
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
	private String strNo         = null;
	private String strFepid      = null;
	private String strInsid      = null;
	private String strName       = null;
	private String strHostIp     = null;
	private String strHostPort   = null;
	private String strPathOrg    = null;
	private String strPathBackup = null;
	
	private int    intHostPort   = 0;
	
	private String strKey = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public BatchHostInfo()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}

	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param strNo
	 * @param strFepid
	 * @param strInsid
	 * @param strName
	 * @param strHostIp
	 * @param strHostPort
	 * @param strPathOrg
	 * @param strPathBackup
	 */
	/*------------------------------------------------------------------*/
	public BatchHostInfo(
			String strNo,
			String strFepid,
			String strInsid,
			String strName,
			String strHostIp,
			String strHostPort,
			String strPathOrg,
			String strPathBackup
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strNo = strNo;
			this.strFepid = strFepid;
			this.strInsid = strInsid;
			this.strName = strName;
			this.strHostIp = strHostIp;
			this.strHostPort = strHostPort;
			this.strPathOrg = strPathOrg;
			this.strPathBackup = strPathBackup;
			
			this.intHostPort = Integer.parseInt(this.strHostPort);
			
			if (!flag) {
				System.out.println("[strNo         =" + strNo            + "]");
				System.out.println("[strFepid      =" + strFepid         + "]");
				System.out.println("[strInsid      =" + strInsid         + "]");
				System.out.println("[strName       =" + strName          + "]");
				System.out.println("[strHostIp     =" + strHostIp        + "]");
				System.out.println("[strHostPort   =" + strHostPort      + "]");
				System.out.println("[strPathOrg    =" + strPathOrg       + "]");
				System.out.println("[strPathBackup =" + strPathBackup    + "]");
				System.out.println("[intHostPort   =" + this.intHostPort + "]");
			}
		}
	}


	/*==================================================================*/
	/**
	 * getter
	 * 
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public String getStrNo() {
		return strNo;
	}

	public String getStrFepid() {
		return strFepid;
	}

	public String getStrInsid() {
		return strInsid;
	}

	public String getStrName() {
		return strName;
	}

	public String getStrHostIp() {
		return strHostIp;
	}

	public String getStrHostPort() {
		return strHostPort;
	}

	public String getStrPathOrg() {
		return strPathOrg;
	}

	public String getStrPathBackup() {
		return strPathBackup;
	}

	public int getIntHostPort() {
		return intHostPort;
	}

	public String getStrKey() {
		return strKey;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strNo
	 */
	/*------------------------------------------------------------------*/
	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public void setStrFepid(String strFepid) {
		this.strFepid = strFepid;
	}

	public void setStrInsid(String strInsid) {
		this.strInsid = strInsid;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public void setStrHostIp(String strHostIp) {
		this.strHostIp = strHostIp;
	}

	public void setStrHostPort(String strHostPort) {
		this.strHostPort = strHostPort;
	}

	public void setStrPathOrg(String strPathOrg) {
		this.strPathOrg = strPathOrg;
	}

	public void setStrPathBackup(String strPathBackup) {
		this.strPathBackup = strPathBackup;
	}

	public void setIntHostPort(int intHostPort) {
		this.intHostPort = intHostPort;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}

	/*==================================================================*/
	/**
	 * print the information of the object
	 */
	/*------------------------------------------------------------------*/
	public void toPrint()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println("     [class=BatchHostInfo]");
			System.out.println("[strNo         =" + strNo            + "]");
			System.out.println("[strFepid      =" + strFepid         + "]");
			System.out.println("[strInsid      =" + strInsid         + "]");
			System.out.println("[strName       =" + strName          + "]");
			System.out.println("[strHostIp     =" + strHostIp        + "]");
			System.out.println("[strHostPort   =" + strHostPort      + "]");
			System.out.println("[strPathOrg    =" + strPathOrg       + "]");
			System.out.println("[strPathBackup =" + strPathBackup    + "]");
			System.out.println("[strKey        =" + strKey           + "]");
			System.out.println("[intHostPort   =" + this.intHostPort + "]");
			System.out.println();
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.22
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*==================================================================*/
	/**
	 * default test
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
