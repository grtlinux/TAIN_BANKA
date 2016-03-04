package ic.vela.ibridge.common.version;

/*==================================================================*/
/**
 * IBTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBTestMain
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class Version 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	// private static final long       MILLISEC_DAY = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	/*
	 * 
	 * - AP서버에서 AP와 IB로 분리함.
	 * - 방화벽에 막힌 개발자들도 개발 할 수 있도록 IB 객체를 분리함.
	 * - IB 객체는 AP와 TCP/IP 통신을 함.
	 * - IBridgeServant를 객체를 여러개 생성해도 상관 없음
	 * - IBridgeServant를 객체를 보험사별로 관리할 수 있음.
	 * - IBridgeServant에서 비동기/동기 방식으로 전문 송수신 가능
	 * - 멀티FEP로 여러개의 대외기관을 연결하여 처리 할 수 있음.
	 * 
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130623.0";

	/*
	 * 
	 * - 멀티 IBridgeServant 처리
	 * - 멀티 IB 처리
	 * - 멀티 AP 처리
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130628.2";

	/*
	 * 
	 * - 세션처리
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130703.3";

	/*
	 * 
	 * - 로그처리일자 적용 수정 : 날짜변경에 따른 로그파일도 날짜변경됨.
	 *      ApLogger ExtLogger FepLogger IBridgeLogger.java 수정
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130705.4";

	/*
	 * 
	 * - 배치파일 처리를 위한 모듈 추가 : 흥국생명, 신한생명
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130708.5";

	/*
	 * DATE.2013.07.13
	 * - Polling 처리 추가, yes/no
	 * system.ib.param.host.91.poll           = yes, 20, 30
	 * 20 : polling 전문을 송신한다.
	 * 30 : polling 전문 송신 후에 응답이 없으면 30 초 후에 프로그램을 종료한다.
	 * 	private static final int           TRYCONNECT_TIMEOUT  = 5000;   // milliseconds
	 */
	//private static String              version             = "IB_Vela.2.130716.6";

	/*
	 * DATE.2013.07.25
	 * 세션연결을 위한 접속시도시간을 설정할 수 있도록 함.
	 * - private static final int           TRYCONNECT_TIMEOUT  = 10000;   // milliseconds
	 */
	//private static String              version             = "IB_Vela.2.130725.6";

	/*
	 * DATE.2013.07.31
	 * 은행코드 BAK_ID 를 021 -> 269 로 변경함
	 */
	//private static String              version             = "IB_Vela.2.130731.7";

	/*
	 * DATE.2013.08.19
	 * 1. ib 에서 받은 전문을 변형하지 않고 그대로 recvXml로 전달
	 * 2. SocketSelector.tryMultiConnection 에서 발생되는 장애
	 * 아래 이유로 접속시도는 생성되는 Thread, AttachObject에서 처리하기로 한다.
	 *     1. 서버와 포트가 구성이 되있으면 금방 접속을 시도한다.
	 *     2. 구성이 되어 있지 않으면 접속시도를 위해 1분 이상의 시간을 소모한다.
	 *     3. 2번 항목으로 여타 다른 서비스에 장애를 일으킨다.
	 * 
	 * -> 재설계가 필요한 상황이 되었습니다. 그래서 버젼UP은 없습니다.
	 */
	//private static String              version             = "IB_Vela.2.130819.8";

	/*
	 * DATE.2013.09.08
	 * 하나생명 배치처리를 위해 수정함.
	 *   사이트가 큰 배치파일을 1,022,000으로 쪼개서 파일명 1-N, 2-N.. 을 붙이면서
	 *   송신한다. 즉 doDataTransfer()에서 여러개의 파일을 수신해야 한다.
	 */
	//private static String              version             = "IB_Vela.2.130908.8";

	/*
	 * DATE.2013.09.09
	 * 함수 : public static String XmlPoll.get(String strFepid, String strServerCode)
	 * Poll 처리시 <HDR_DAT_GBN>T/R</HDR_DAT_GBN>
	 */
	//private static String              version             = "IB_Vela.2.130909.8";

	/*
	 * DATE.2013.11.08
	 * 미래에셋 관련 항목 8군데 추가 : search file에서 '미래에셋'을 찾으면 된다.
	 */
	//private static String              version             = "IB_Vela.2.131108.9";

	/*
	 * DATE.2013.12.23
	 * 농협생명 관련 항목 8군데 추가 : search file에서 '농협생명'을 찾으면 된다.
	 */
	//private static String              version             = "IB_Vela.2.131223.10";

	/*
	 * DATE.2015.11.11
	 * 노란우산공제(NRI01, Y97) 관련 항목 8군데 추가 : search file에서 '노란우산공제'을 찾으면 된다.
	 */
	private static String              version             = "IB_Vela.2.151111.11";

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	public static String getVersion() throws Exception
	{
		boolean flag = true;
		
		String strVersion = null;
		
		if (flag) {
			strVersion = Version.version;
		}
		
		return strVersion;
	}
	
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
				System.out.println(" * IBridge 버젼은 " + getVersion() + " 입니다.");
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
