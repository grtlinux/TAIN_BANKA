package ic.vela.ibridge.server.ib.socketselector;

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
public class SocketHashMapConnect
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String                     strConnName         = null;
	private String                     strConnFepId        = null;
	private String                     strConnHostIp       = null;
	private String                     strConnHostPort     = null;
	private String                     strPoll             = null;
	private int                        iPollSendCnt        = 0;
	private int                        iPollKillCnt        = 0;
	
	private int                        iConnHostPort       = 0;
	
	private boolean                    flagConnected       = false;

	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param strConnName
	 * @param strConnFepId
	 * @param strConnHostIp
	 * @param strConnHostPort
	 */
	/*------------------------------------------------------------------*/
	public SocketHashMapConnect(
			String strConnName,
			String strConnFepId,
			String strConnHostIp,
			String strConnHostPort,
			String strPoll
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strConnName = strConnName;
			this.strConnFepId = strConnFepId;
			this.strConnHostIp = strConnHostIp;
			this.strConnHostPort = strConnHostPort;
			
			this.iConnHostPort = Integer.parseInt(strConnHostPort);

			/*
			 * TODO DATE.2013.07.12 POLL 처리
			 * 
			 * SocketChannelAttachObject 에서 sendJob, recvJob에서
			 * String strReqStreamXml = (String) this.readQueue.get(100);
			 * String strResStreamXml = (String) this.recvQueue.get(100);
			 * 위와 같이 되어 있어 * 5 를 한다.
			 */

			String[] strArr = strPoll.trim().split("[ ,]+");
			this.strPoll = strArr[0];
			//this.iPollSendCnt = Integer.parseInt(strArr[1]) * 5;
			//this.iPollKillCnt = Integer.parseInt(strArr[2]) * 5;
			this.iPollSendCnt = Integer.parseInt(strArr[1]);
			this.iPollKillCnt = Integer.parseInt(strArr[2]);
		}
	}
	
	/*==================================================================*/
	/**
	 * getter
	 * 
	 * @return
	 */
	/*------------------------------------------------------------------*/
	
	public String getStrConnName() {
		return strConnName;
	}

	public String getStrConnFepId() {
		return strConnFepId;
	}

	public String getStrConnHostIp() {
		return strConnHostIp;
	}

	public String getStrConnHostPort() {
		return strConnHostPort;
	}

	// TODO DATE.2013.07.12 POLL 처리
	public String getStrPoll() {
		return strPoll;
	}

	public int getiPollSendCnt() {
		return iPollSendCnt;
	}

	public int getiPollKillCnt() {
		return iPollKillCnt;
	}

	public int getiConnHostPort() {
		return iConnHostPort;
	}

	public boolean getFlagConnected() {
		return flagConnected;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param strConnName
	 */
	/*------------------------------------------------------------------*/

	public void setStrConnName(String strConnName) {
		this.strConnName = strConnName;
	}

	public void setStrConnFepId(String strConnFepId) {
		this.strConnFepId = strConnFepId;
	}

	public void setStrConnHostIp(String strConnHostIp) {
		this.strConnHostIp = strConnHostIp;
	}

	public void setStrConnHostPort(String strConnHostPort) {
		this.strConnHostPort = strConnHostPort;
	}

	// TODO DATE.2013.07.12 POLL 처리
	public void setStrPoll(String strPoll) {
		this.strPoll = strPoll;
	}

	public void setiPollSendCnt(int iPollSendCnt) {
		this.iPollSendCnt = iPollSendCnt;
	}

	public void setiPollKillCnt(int iPollKillCnt) {
		this.iPollKillCnt = iPollKillCnt;
	}

	public void setiConnHostPort(int iConnHostPort) {
		this.iConnHostPort = iConnHostPort;
	}

	public void setFlagConnected(boolean flagConnected) {
		this.flagConnected = flagConnected;
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
