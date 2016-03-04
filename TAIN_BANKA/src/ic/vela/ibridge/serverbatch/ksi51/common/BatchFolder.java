package ic.vela.ibridge.serverbatch.ksi51.common;

/*==================================================================*/
/**
 * BatchFolder Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchFolder
 * @author  ����
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class BatchFolder
/*==================================================================*/
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
	
	private String strKey = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public BatchFolder()
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
	public BatchFolder(
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

	public String getStrKey() {
		return strKey;
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

	public void setStrKey(String strKey) {
		this.strKey = strKey;
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
			System.out.println("     [class=BatchFolder]");
			System.out.println("[strNoFolder  =" + strNoFolder  + "]");
			System.out.println("[strSrcFolder =" + strSrcFolder + "]");
			System.out.println("[strTgtFolder =" + strTgtFolder + "]");
			System.out.println("[strKey       =" + strKey       + "]");
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
