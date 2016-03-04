package ic.vela.ibridge.server.webcash.common;

/*==================================================================*/
/**
 * WebCashBatchChkFile 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchChkFile
 * @author  강석
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class WebCashBatchChkFile
/*------------------------------------------------------------------*/
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
	private String strChkFileLine   = null;
	
	private String strJobDateTime   = null;
	private String strSrcPath       = null;
	private String strSrcFileName   = null;
	private String strFileDateTime  = null;
	private String strTgtFileName   = null;
	private String strRecLen        = null;
	private String strRecCount      = null;
	private String strResultMsg     = null;
	
	private String strKey           = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param strChkFileLine
	 */
	/*------------------------------------------------------------------*/
	public WebCashBatchChkFile(String strChkFileLine) throws Exception
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * split the string strChkFileLine
			 */
			String[] item = strChkFileLine.split(";");
			if (item.length != 8) {
				throw new IllegalStateException("IB-ERROR : wrong the check file items, [split.length=" + item.length + "] not 8");
			}
			
			this.strChkFileLine = strChkFileLine;
			
			this.strJobDateTime  = item[0];
			this.strSrcPath      = item[1];
			this.strSrcFileName  = item[2];
			this.strFileDateTime = item[3];
			this.strTgtFileName  = item[4];
			this.strRecLen       = item[5];
			this.strRecCount     = item[6];
			this.strResultMsg    = item[7];
			
			this.strKey = item[2] + item[3];

			if (!flag) {
				System.out.println("[class=WebCashBatchFile]");
				System.out.println("[strChkFileLine  =" + this.strChkFileLine  + "]");
				System.out.println("[strJobDateTime  =" + this.strJobDateTime  + "]");
				System.out.println("[strSrcPath      =" + this.strSrcPath      + "]");
				System.out.println("[strSrcFileName  =" + this.strSrcFileName  + "]");
				System.out.println("[strFileDateTime =" + this.strFileDateTime + "]");
				System.out.println("[strTgtFileName  =" + this.strTgtFileName  + "]");
				System.out.println("[strRecLen       =" + this.strRecLen       + "]");
				System.out.println("[strRecCount     =" + this.strRecCount     + "]");
				System.out.println("[strResultMsg    =" + this.strResultMsg    + "]");
				System.out.println("[strKey          =" + this.strKey          + "]");
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/**
	 * getter
	 * 
	 * @return
	 */
	/*------------------------------------------------------------------*/
	
	public String getStrChkFileLine() {
		return strChkFileLine;
	}

	public String getStrJobDateTime() {
		return strJobDateTime;
	}

	public String getStrSrcPath() {
		return strSrcPath;
	}

	public String getStrSrcFileName() {
		return strSrcFileName;
	}

	public String getStrFileDateTime() {
		return strFileDateTime;
	}

	public String getStrTgtFileName() {
		return strTgtFileName;
	}

	public String getStrRecLen() {
		return strRecLen;
	}

	public String getStrRecCount() {
		return strRecCount;
	}

	public String getStrResultMsg() {
		return strResultMsg;
	}

	public String getStrKey() {
		return strKey;
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
			System.out.println("     [class=WebCashBatchChkFile]");
			System.out.println("[strChkFileLine  =" + this.strChkFileLine  + "]");
			System.out.println("[strJobDateTime  =" + this.strJobDateTime  + "]");
			System.out.println("[strSrcPath      =" + this.strSrcPath      + "]");
			System.out.println("[strSrcFileName  =" + this.strSrcFileName  + "]");
			System.out.println("[strFileDateTime =" + this.strFileDateTime + "]");
			System.out.println("[strTgtFileName  =" + this.strTgtFileName  + "]");
			System.out.println("[strRecLen       =" + this.strRecLen       + "]");
			System.out.println("[strRecCount     =" + this.strRecCount     + "]");
			System.out.println("[strResultMsg    =" + this.strResultMsg    + "]");
			System.out.println("[strKey          =" + this.strKey          + "]");
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
