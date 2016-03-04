package ic.vela.ibridge.serverbatch.hwi51.common;

/*==================================================================*/
/**
 * WebCashBatchFile 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchFile
 * @author  강석
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class BatchFile
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
	private String strFepId        = null;
	private String strName         = null;
	private String strTrCode       = null;
	private String strSrcFileName  = null;
	private String strTgtFileName  = null;
	private String strHostIp       = null;
	private String strHostPort     = null;
	private String strBackupPath   = null;
	private String strOrgPath      = null;
	
	private int intHostPort = 0;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public BatchFile()
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
	 * @param strFepId
	 * @param strName
	 * @param strTrCode
	 * @param strSrcFileName
	 * @param strTgtFileName
	 * @param strHostIp
	 * @param strHostPort
	 * @param strBackupPath
	 * @param strOrgPath
	 */
	/*------------------------------------------------------------------*/
	public BatchFile(
			String strFepId,
			String strName,
			String strTrCode,
			String strSrcFileName,
			String strTgtFileName,
			String strHostIp,
			String strHostPort,
			String strBackupPath,
			String strOrgPath
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strFepId = strFepId;
			this.strName = strName;
			this.strTrCode = strTrCode;
			this.strSrcFileName = strSrcFileName;
			this.strTgtFileName = strTgtFileName;
			this.strHostIp = strHostIp;
			this.strHostPort = 	strHostPort;
			this.strBackupPath = strBackupPath;
			this.strOrgPath = strOrgPath;
			this.intHostPort = Integer.parseInt(strHostPort);

			if (!flag) {
				System.out.println("[strFepId        =" + strFepId       + "]");
				System.out.println("[strName         =" + strName        + "]");
				System.out.println("[strTrCode       =" + strTrCode      + "]");
				System.out.println("[strSrcFileName  =" + strSrcFileName + "]");
				System.out.println("[strTgtFileName  =" + strTgtFileName + "]");
				System.out.println("[strHostIp       =" + strHostIp      + "]");
				System.out.println("[strHostPort     =" + strHostPort    + "]");
				System.out.println("[strBackupPath   =" + strBackupPath  + "]");
				System.out.println("[strOrgPath      =" + strOrgPath     + "]");
				System.out.println("[intHostPort     =" + intHostPort    + "]");
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
	
	public String getStrFepId() {
		return this.strFepId;
	}

	public String getStrName() {
		return this.strName;
	}

	public String getStrTrCode() {
		return this.strTrCode;
	}

	public String getStrSrcFileName() {
		return this.strSrcFileName;
	}

	public String getStrTgtFileName() {
		return this.strTgtFileName;
	}

	public String getStrHostIp() {
		return this.strHostIp;
	}

	public String getStrHostPort() {
		return this.strHostPort;
	}

	public String getStrBackupPath() {
		return this.strBackupPath;
	}

	public String getStrOrgPath() {
		return this.strOrgPath;
	}

	public int getIntHostPort() {
		return this.intHostPort;
	}
	
	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strFepId
	 */
	/*------------------------------------------------------------------*/

	public void setStrFepId(String strFepId) {
		this.strFepId = strFepId;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public void setStrTrCode(String strTrCode) {
		this.strTrCode = strTrCode;
	}

	public void setStrSrcFileName(String strSrcFileName) {
		this.strSrcFileName = strSrcFileName;
	}

	public void setStrTgtFileName(String strTgtFileName) {
		this.strTgtFileName = strTgtFileName;
	}

	public void setStrHostIp(String strHostIp) {
		this.strHostIp = strHostIp;
	}

	public void setStrHostPort(String strHostPort) {
		this.strHostPort = strHostPort;
		this.intHostPort = Integer.parseInt(strHostPort);
	}

	public void setStrBackupPath(String strBackupPath) {
		this.strBackupPath = strBackupPath;
	}

	public void setStrOrgPath(String strOrgPath) {
		this.strOrgPath = strOrgPath;
	}

	/*==================================================================*/
	/**
	 * print the information of the object
	 */
	/*------------------------------------------------------------------*/
	public void toPrint()
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println("     [class=WebCashBatchFile]");
			System.out.println("[strFepId        =" + strFepId       + "]");
			System.out.println("[strName         =" + strName        + "]");
			System.out.println("[strTrCode       =" + strTrCode      + "]");
			System.out.println("[strSrcFileName  =" + strSrcFileName + "]");
			System.out.println("[strTgtFileName  =" + strTgtFileName + "]");
			System.out.println("[strHostIp       =" + strHostIp      + "]");
			System.out.println("[strHostPort     =" + strHostPort    + "]");
			System.out.println("[strBackupPath   =" + strBackupPath  + "]");
			System.out.println("[strOrgPath      =" + strOrgPath     + "]");
			System.out.println("[intHostPort     =" + intHostPort    + "]");
			System.out.println();
		}
	}
	
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
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.22
			try {
				
				BatchFile fileInfo = new BatchFile(
						"HDC51",
						"현대카드 승인",
						"FMA26000001",
						"01_YYYYMMDD_9100210861_HD.tXt",
						"R_HDC_FMA26000001_YYYYMMDD_01",
						"127.0.0.1",
						"44110",
						"",
						""
						);
				
				if (flag) {
					System.out.println("system.ib.param.batchfile.01.fepid       = [" + fileInfo.getStrFepId()        + "]");
					System.out.println("system.ib.param.batchfile.01.name        = [" + fileInfo.getStrName()         + "]");
					System.out.println("system.ib.param.batchfile.01.trcode      = [" + fileInfo.getStrTrCode()       + "]");
					System.out.println("system.ib.param.batchfile.01.file.src    = [" + fileInfo.getStrSrcFileName()  + "]");
					System.out.println("system.ib.param.batchfile.01.file.tgt    = [" + fileInfo.getStrTgtFileName()  + "]");
					System.out.println("system.ib.param.batchfile.01.host.ip     = [" + fileInfo.getStrHostIp()       + "]");
					System.out.println("system.ib.param.batchfile.01.host.port   = [" + fileInfo.getStrHostPort()     + "]");
					System.out.println("system.ib.param.batchfile.01.path.backup = [" + fileInfo.getStrBackupPath()   + "]");
					System.out.println("system.ib.param.batchfile.01.path.org    = [" + fileInfo.getStrOrgPath()      + "]");
					System.out.println("intHostPort                              = [" + fileInfo.getIntHostPort()     + "]");
				}
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
