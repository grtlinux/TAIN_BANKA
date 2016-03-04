package ic.vela.ibridge.server.webcash.common;

/*==================================================================*/
/**
 * WebCashBatchFolder 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchFolder
 * @author  강석
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class WebCashBatchFolder
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
	private String strNoFolder = null;
	private String strSrcFolder = null;
	private String strTgtFolder = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public WebCashBatchFolder()
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
	 * @param strNoFolder
	 * @param strSrcFolder
	 * @param strTgtFolder
	 */
	/*------------------------------------------------------------------*/
	public WebCashBatchFolder(
			String strNoFolder,
			String strSrcFolder,
			String strTgtFolder
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strNoFolder = strNoFolder;
			this.strSrcFolder = strSrcFolder;
			this.strTgtFolder = strTgtFolder;
			
			if (!flag) {
				System.out.println("[strNoFolder=" + strNoFolder + "]");
				System.out.println("[strSrcFolder=" + strSrcFolder + "]");
				System.out.println("[strTgtFolder=" + strTgtFolder + "]");
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
	
	public String getStrNoFolder() {
		return strNoFolder;
	}

	public String getStrSrcFolder() {
		return strSrcFolder;
	}

	public String getStrTgtFolder() {
		return strTgtFolder;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strNoFolder
	 */
	/*------------------------------------------------------------------*/

	public void setStrNoFolder(String strNoFolder) {
		this.strNoFolder = strNoFolder;
	}

	public void setStrSrcFolder(String strSrcFolder) {
		this.strSrcFolder = strSrcFolder;
	}

	public void setStrTgtFolder(String strTgtFolder) {
		this.strTgtFolder = strTgtFolder;
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
			System.out.println("     [class=WebCashBatchFolder]");
			System.out.println("[strNoFolder=" + strNoFolder + "]");
			System.out.println("[strSrcFolder=" + strSrcFolder + "]");
			System.out.println("[strTgtFolder=" + strTgtFolder + "]");
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
