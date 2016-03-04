package ic.vela.ibridge.server.ext.virtualext.serversocketselector;


/*==================================================================*/
/**
 * ServerSocketHashMapListen 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketHashMapListen
 * @author  강석
 * @version 1.0, 2013/06/29
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class ServerSocketHashMapListen
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	//private static final long          MILLISEC_DAY        = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String                     strListenName       = null;
	private String                     strListenPort       = null;
	
	private int                        iListenPort         = 0;
	
	private boolean                    flagListened        = false;

	/*==================================================================*/
	/**
	 * constructor 
	 * 
	 * @param strListenName
	 * @param strListenPort
	 */
	/*------------------------------------------------------------------*/
	public ServerSocketHashMapListen(
			String strListenName,
			String strListenPort
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strListenName = strListenName;
			this.strListenPort = strListenPort;
			
			this.iListenPort = Integer.parseInt(strListenPort);
		}
	}
	
	/*==================================================================*/
	/**
	 * getter
	 * 
	 * @return
	 */
	/*------------------------------------------------------------------*/
	
	public String getStrListenName() {
		return strListenName;
	}

	public String getStrListenPort() {
		return strListenPort;
	}

	public int getiListenPort() {
		return iListenPort;
	}

	public boolean getFlagListened() {
		return flagListened;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strListenName
	 */
	/*------------------------------------------------------------------*/

	public void setStrListenName(String strListenName) {
		this.strListenName = strListenName;
	}

	public void setStrListenPort(String strListenPort) {
		this.strListenPort = strListenPort;
	}

	public void setiListenPort(int iListenPort) {
		this.iListenPort = iListenPort;
	}

	public void setFlagListened(boolean flagListened) {
		this.flagListened = flagListened;
	}

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/**
	 * 날짜연산
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}
	
	/*==================================================================*/
	/**
	 * default test : 단순한 문자 출력
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
