package ic.vela.ibridge.serverbatch.ksi51.common;

/*==================================================================*/
/**
 * BatchTrInfo 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchTrInfo
 * @author  강석
 * @version 1.0, 2013/07/06
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class BatchTrInfo
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
	private String strTrNo = null;
	private String strTrName = null;
	private String strTrCode = null;
	
	private String strKey = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public BatchTrInfo()
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
	 * @param strTrNo
	 * @param strTrName
	 * @param strTrCode
	 */
	/*------------------------------------------------------------------*/
	public BatchTrInfo(
			String strTrNo,
			String strTrName,
			String strTrCode
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strTrNo = strTrNo;
			this.strTrName = strTrName;
			this.strTrCode = strTrCode;
			
			if (!flag) {
				System.out.println("[strTrNo  =" + strTrNo + "]");
				System.out.println("[strTrName=" + strTrName + "]");
				System.out.println("[strTrCode=" + strTrCode + "]");
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
	public String getStrTrNo() {
		return strTrNo;
	}

	public String getStrTrName() {
		return strTrName;
	}

	public String getStrTrCode() {
		return strTrCode;
	}

	public String getStrKey() {
		return strKey;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strTrNo
	 */
	/*------------------------------------------------------------------*/
	public void setStrTrNo(String strTrNo) {
		this.strTrNo = strTrNo;
	}

	public void setStrTrName(String strTrName) {
		this.strTrName = strTrName;
	}

	public void setStrTrCode(String strTrCode) {
		this.strTrCode = strTrCode;
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
			System.out.println("     [class=BatchTrInfo]");
			System.out.println("[strTrNo   =" + strTrNo   + "]");
			System.out.println("[strTrName =" + strTrName + "]");
			System.out.println("[strTrCode =" + strTrCode + "]");
			System.out.println("[strKey    =" + strKey    + "]");
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
