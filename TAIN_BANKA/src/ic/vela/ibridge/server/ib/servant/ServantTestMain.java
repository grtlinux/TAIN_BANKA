package ic.vela.ibridge.server.ib.servant;

import ic.vela.ibridge.base.xml.Sample01Xml;
import ic.vela.ibridge.base.xml.XmlObject_20130622;
import ic.vela.ibridge.util.XmlTool;

/*==================================================================*/
/**
 * this is the class for test08 processing
 */
/*------------------------------------------------------------------*/
class SingleDeveloper extends Thread
{
	private String fepId = null;
	
	public SingleDeveloper(String fepId)
	{
		boolean flag = true;
		
		if (flag) {
			this.fepId = fepId;
			if (flag) System.out.println(">" + this.fepId);
		}
	}
	
	public void run()
	{
		boolean flag = true;
		
		if (flag) {  // DATE.2013.06.16 : Async Test
			try {

				IBridgeServant servant01 = new IBridgeServant();
				IBridgeServant servant02 = new IBridgeServant();
				IBridgeServant servant03 = new IBridgeServant();

				// int waitTime = 2;
				int loopCount = 2;
				if (flag) {
					loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "2"));
					if (!flag) System.out.println("> loopCount = " + loopCount);
				}

				String strReqSoapXml = null;
				String strKey = null;

				for (int i=0; i < loopCount; i++) {

					strReqSoapXml = Sample01Xml.getReqXml(this.fepId, "01");
					strKey = servant01.sendXml(strReqSoapXml);
					if (flag) System.out.println("[" + this.fepId + "-01-REQ-"+ i +"][KEY=" + strKey + "] : " + strReqSoapXml);
					
					strReqSoapXml = Sample01Xml.getReqXml(this.fepId, "02");
					strKey = servant02.sendXml(strReqSoapXml);
					if (flag) System.out.println("[" + this.fepId + "-02-REQ-"+ i +"][KEY=" + strKey + "] : " + strReqSoapXml);

					strReqSoapXml = Sample01Xml.getReqXml(this.fepId, "03");
					strKey = servant03.sendXml(strReqSoapXml);
					if (flag) System.out.println("[" + this.fepId + "-03-REQ-"+ i +"][KEY=" + strKey + "] : " + strReqSoapXml);
					
					/*
					 *  wait for some seconds.
					 */
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}

				System.out.println("--------------------------- " + this.fepId + " ----------------------------");
				
				String strResSoapXml = null;

				int exitCnt = 0;
				for (int i=1; i < 100000; i++) {
					
					strResSoapXml = servant01.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println(String.format("[%s-01-RES-%02d] : %s", this.fepId, i, strResSoapXml));
						exitCnt = 0;
					}

					strResSoapXml = servant02.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println(String.format("[%s-02-RES-%02d] : %s", this.fepId, i, strResSoapXml));
						exitCnt = 0;
					}

					strResSoapXml = servant03.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println(String.format("[%s-03-RES-%02d] : %s", this.fepId, i, strResSoapXml));
						exitCnt = 0;
					}

					if (flag) {
						exitCnt++;
						if (exitCnt > 10)
							break;
						
						//System.out.print("#");
					}

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

/*==================================================================*/
/**
 * ServantTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServantTestMain
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 환경변수를 아래와 같이 설정해야 한다.
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 *  
 */
/*==================================================================*/
public class ServantTestMain 
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
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 1. 전문단건 송수신....");
				}
				
				// 송신전문 -> 전문송신
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReqFepidXml("HWI01", "L01");  // 한화생명 OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("SHI01", "L11");  // 신한생명 OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("HNI01", "L63");  // 하나HSBC생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("HKI01", "L04");  // 흥국생명  OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("KFI01", "L78");  // 카디프생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("ALI01", "L02");  // 알리안츠생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("SSI01", "L03");  // 삼성생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("MII01", "L34");  // 미래에셋생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("NHI01", "L34");  // 농협생명
				//strReqSoapXml = Sample01Xml.getReqFepidXml("NRI01", "Y97");  // 노란우산공제
				//strReqSoapXml = Sample01Xml.getReqFepidXml("LOCAL", "L01");  // ERROR
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// 전문수신 -> 수신전문
				String strResSoapXml = servant.recvXml();    // Recv XML

				if (flag) System.out.println("RES: " + strResSoapXml);

				// 잠시기다린다.
				try {
					// Thread.sleep(500000);
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test02
	 */
	/*------------------------------------------------------------------*/
	private static void test02() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 2. 전문 송수신(동기)을 loopcount만큼 반복....");
				}

				// 요청송신전문
				String strReqSoapXml = Sample01Xml.getReqXml();
				
				int waitTime = 2;
				int loopCount = 10;
				{
					try {
						waitTime = Integer.parseInt(System.getProperty("system.ib.param.waittime", "2"));
						loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int i=0; i < loopCount; i++) {
					// 요청송신전문을 출력한다.
					if (flag) System.out.println("REQ: " + strReqSoapXml);
					
					// 요청송신전문을 송신한다.
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// 응답수신전문을 수신한다. 기다리면서
					String strResSoapXml = servant.recvXml();    // Recv XML
					
					// 응답수신전문을 출력한다.
					if (flag) System.out.println("RES: " + strResSoapXml);

					// 잠시기다린다.
					System.out.println("----------------[" + i + "] " + waitTime + "초 잠시 기다립니다. ------------------");
					try {
						Thread.sleep(waitTime * 1000);
					} catch (InterruptedException e) {}
				}

				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*==================================================================*/
	/**
	 * test03
	 */
	/*------------------------------------------------------------------*/
	private static void test03() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.16 : Sync Test
			try {
				/*
				 * sync 송수신처리 방법
				 * 그냥 sendXml 하고 recvXml 로 기다린다.
				 */

				IBridgeServant servant = new IBridgeServant();
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 3. 전문단건 송수신. 일반전문...");
				}
				
				// 요청전문을 생성한다.
				String strReqSoapXml = XmlObject_20130622.setHdrBakDocseq(Sample01Xml.getReqXml());
				if (flag) System.out.println("### REQ : " + strReqSoapXml);

				// 요청전문을 송신한다.
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// 응답전문을 받을 때까지 기다린다.
				String strResSoapXml = servant.recvXml(0);
				if (flag) System.out.println("### RES: " + strResSoapXml);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			if (flag) {
				// wait for some seconds.
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
			}

			// 프로그램 종료
			System.exit(0);
		}
		
	}
	
	/*==================================================================*/
	/**
	 * test04
	 */
	/*------------------------------------------------------------------*/
	private static void test04() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {  // DATE.2013.06.16 : Async Test
			try {
				
				/*
				 * 송수신 처리를 하는 객체를 생성한다.
				 * 이 객체는 송신과 수신을 async 방식으로
				 * 처리한다.
				 */
				IBridgeServant servant = new IBridgeServant();

				System.out.println("Version : " + servant.getVersion());
				
				@SuppressWarnings("unused")
				int waitTime = 2;
				int loopCount = 10;
				{
					try {
						waitTime = Integer.parseInt(System.getProperty("system.ib.param.waittime", "2"));
						loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// 여러건을 한꺼번에 송신한다.
				// send n times
				for (int i=0; i < loopCount; i++) {

					// 요청송신전문을 생성한다. 아래 요청전문은 테스트를 위한 것이고
					// 개발자들은 사용하지 말아주세요.
					String strReqSoapXml = XmlObject_20130622.setHdrBakDocseq(Sample01Xml.getReqXml());

					// 요청전문을 여러번 보낸다.
					servant.sendXml(strReqSoapXml);     // Send XML
					if (flag) System.out.println("REQ["+ i +"] : " + strReqSoapXml);
					
					// wait for some seconds.
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}

				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				
				/*
				 * 여러건을 한꺼번에 수신한다.
				 * recv n times
				 */
				for (int i=0; i < 100000; i++) {
					/*
					 * 응답전문을 받을 준비를 한다.
					 * 
					 * 응답수신전문을 수신한다. 기다리면서
					 * recvXml(FEPID, BLOCKTIME)
					 *         FEPID는 기관별 FEP뱅킹에서 부여한 ID
					 *         BLOCKTIME 은 응답전문을 기다리는 최대시간, 받은 즉시 return 된다.
					 *         만일 BLOCKTIME 이 0 이면 응답전문을 무한정 기다린다. (sync 에 적용)
					 *         async 에 적용하려면 BLOCKTIME 을 특정시간(millisecond) 으로 관리하면
					 *         된다.
					 */
					
					// Recv XML wait for 2 seconds until receiving data
					String strResSoapXml = servant.recvXml(2000);

					if (strResSoapXml != null) {
						// receive the response message from the server, and not null
						if (flag) System.out.println("\nRES[" + i + "]: " + strResSoapXml);
					}
					
					System.out.print("#");
					
					// wait for some seconds.  loop wait time
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}
				
				/*
				 * 위에서 테스트한 결과로 송신과 수신은 async 방식으로 진행되며
				 * 연속송신하는 가운데 수신처리를 삽입할 수 있으며 그 반대의 경우도
				 * 가능하다.
				 */
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * test05
	 */
	/*------------------------------------------------------------------*/
	private static void test05() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {  // DATE.2013.06.16 : Async Test
			try {
				/*
				 * 버전 2에서는 멀티유저와 멀티대외기관 처리를 위해
				 * 설계를 변경하여 만들었습니다.
				 * 
				 * 멀티대외기관은 지금 작업중에 있고,
				 * 멀티유저를 위해서는 개발자PC의 WAS서버에 제공하는
				 * 패키지를 설치하시고 아래와 같이 대상 폴더와 프라퍼티
				 * 파일을 구성하여 준비하시면 개발하신 프로그램을 실행
				 * 하면 됩니다.
				 * 
				 *     server
				 *         ap
				 *             cfg
				 *                 ap.properties
				 *             log
				 *         ext
				 *             cfg
				 *                 ext.properties
				 *             log
				 *         fep
				 *             cfg
				 *                 fep.properties
				 *             log
				 *         ib
				 *             cfg
				 *                 ib.properties
				 *             log
				 * 
				 * 위에 구성하신 정보를 FEP프로그램에서 인식하기 위해서는
				 * 아래와 같은 property 를 WAS 프로그램 실행에 추가하셔야
				 * 합니다.
				 * 
				 *     -Dsystem.ib.param.basefolder=...../server/
				 *   
				 * server 뒤에는 '/'를 반드시 붙여야 합니다. 그리고 로그는
				 * ...../server/ib/log/YYYYMMDD.log 파일로 되어 있습니다.
				 * 
				 */
				
				/*
				 * 송수신 처리를 하는 객체를 생성한다.
				 * 이 객체는 송신과 수신을 async 방식으로
				 * 처리한다.
				 */
				IBridgeServant servant = new IBridgeServant();
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 5. 전문 송수신(비동기).  loopcount 만큼 반복...");
				}

				/*
				 * 패키지의 version을 확인한다.
				 * 멀티처리를 위한 로직을 추가한 version은 2 이다.
				 */
				System.out.println("Version : " + servant.getVersion());
				
				// int waitTime = 2;
				int loopCount = 2;
				if (flag) {
					loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
				}

				/*
				 *  여러건을 한꺼번에 송신한다.
				 *  쉬지않과 loopCount 만큼 송신만 한다.
				 *  비동기 방식 처리 테스트임
				 */
				for (int i=0; i < loopCount; i++) {

					/*
					 *  요청송신전문을 생성한다. 아래 요청전문은 테스트를 위한 것이고
					 *  개발자들은 사용하지 말아주세요.
					 *  개발자는 직접 보험사에서 사용하는 XML 전문을 사용하기 바람.
					 */
					String strReqSoapXml = Sample01Xml.getReqXml();
					
					/*
					 *  요청전문을 보낸다.
					 *  FEPID는 전문에 포함된다.
					 *  전문의 HDR_TRX_ID (9 bytes)에 있으면 된다. 아래 처럼 사용바람.
					 *  
					 *  <HDR_TRX_ID>HWI01</HDR_TRX_ID>
					 *  
					 */
					servant.sendXml(strReqSoapXml);
					if (flag) System.out.println("REQ["+ i +"] : " + strReqSoapXml);
					
					/*
					 *  wait for some seconds.
					 */
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}

				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				
				/*
				 * 여러건을 한꺼번에 수신한다.
				 * recv n times
				 */
				for (int i=1; i < 100000; i++) {
					/*
					 * 응답전문을 받을 준비를 한다.
					 * 
					 * 응답수신전문을 수신한다. 기다리면서
					 * recvXml(BLOCKTIME)
					 *         BLOCKTIME 은 응답전문을 기다리는 최대시간, 받은 즉시 return 된다.
					 *         만일 BLOCKTIME 이 0 이면 응답전문을 무한정 기다린다. (sync 에 적용)
					 *         async 에 적용하려면 BLOCKTIME 을 특정시간(millisecond) 으로 관리하면
					 *         된다.
					 */
					
					/*
					 *  receiveing XML : waiting data for max 2 seconds
					 *  아래 소스는 최대 2초간 기다린다.
					 *  기다리는 사이에 응답전문을 받으면 곧바로 리턴한다.
					 */
					String strResSoapXml = servant.recvXml(2000);

					if (strResSoapXml != null) {
						/*
						 *  receive the response message from the server, and not null
						 *  받은 응답전문을 화면에 출력한다.
						 */
						if (flag) System.out.println("\nRES[" + i + "]: " + strResSoapXml);
					}
					else {
						/*
						 * 비동기임을 확인하기 위한 # 출력임.
						 */
						if (flag) System.out.print("#");
					}
					
					/*
					 *  wait for some seconds.  loop wait time
					 *  받은 후 처리시간을 가정한 loop wait time 이다.
					 *  이 항목은 이해하지 않아도 된다.
					 */
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}
				
				/*
				 * 위에서 테스트한 결과로 송신과 수신은 async 방식으로 진행되며
				 * 연속송신하는 가운데 수신처리를 삽입할 수 있으며 그 반대의 경우도
				 * 가능하다.
				 * 소스에 궁금하신 점이 있으시면 아래로 연락하세요.
				 *                  개발자 : 강석부장 (010-4258-2025)
				 * 감사합니다.
				 */
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * test06
	 */
	/*------------------------------------------------------------------*/
	private static void test06() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 6. 전문 송수신(동기).  loopcount건 만큼 반복....");
				}
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}

				int waitTime = 1;
				int loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
				
				for (int i=0; i < loopCount; i++) {
					String strReqSoapXml = Sample01Xml.getReqXml();

					// 요청송신전문을 출력한다.
					if (flag) System.out.println("REQ: " + strReqSoapXml);
					
					// 요청송신전문을 송신한다.
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// 응답수신전문을 수신한다. 기다리면서
					String strResSoapXml = servant.recvXml(0);    // Recv XML
					
					// 응답수신전문을 출력한다.
					if (flag) System.out.println("RES: " + strResSoapXml);

					// 잠시기다린다.
					System.out.println("----------------[" + i + "] " + waitTime + "초 잠시 기다립니다. ------------------");
					try {
						Thread.sleep(waitTime * 1000);
					} catch (InterruptedException e) {}
				}

				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*==================================================================*/
	/**
	 * test07
	 */
	/*------------------------------------------------------------------*/
	private static void test07() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {  // DATE.2013.06.16 : Async Test
			try {

				IBridgeServant servant01 = new IBridgeServant();
				IBridgeServant servant02 = new IBridgeServant();
				IBridgeServant servant03 = new IBridgeServant();

				if (flag) {
					System.out.println("Version : " + servant01.getVersion());
					System.out.println(" * 7. 멀티IBridgeServant 별 멀티송신 멀티수신...");
				}
				
				// int waitTime = 2;
				int loopCount = 2;
				if (flag) {
					loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "2"));
				}

				String strReqSoapXml = null;

				for (int i=0; i < loopCount; i++) {

					strReqSoapXml = Sample01Xml.getReqXml("01");
					servant01.sendXml(strReqSoapXml);
					if (flag) System.out.println("01-REQ["+ i +"] : " + strReqSoapXml);
					
					strReqSoapXml = Sample01Xml.getReqXml("02");
					servant02.sendXml(strReqSoapXml);
					if (flag) System.out.println("02-REQ["+ i +"] : " + strReqSoapXml);

					strReqSoapXml = Sample01Xml.getReqXml("03");
					servant03.sendXml(strReqSoapXml);
					if (flag) System.out.println("03-REQ["+ i +"] : " + strReqSoapXml);
					
					/*
					 *  wait for some seconds.
					 */
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}

				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				System.out.println("-------------------------------------------------------");
				
				String strResSoapXml = null;

				int exitCnt = 0;
				for (int i=1; i < 100000; i++) {
					
					strResSoapXml = servant01.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println("\n01-RES[" + i + "]: " + strResSoapXml);
						exitCnt = 0;
					}

					strResSoapXml = servant02.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println("\n02-RES[" + i + "]: " + strResSoapXml);
						exitCnt = 0;
					}

					strResSoapXml = servant03.recvXml(100);
					if (strResSoapXml != null) {
						if (flag) System.out.println("\n03-RES[" + i + "]: " + strResSoapXml);
						exitCnt = 0;
					}

					if (flag) {
						exitCnt++;
						if (exitCnt > 10)
							break;
						
						System.out.print("#");
					}

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * test08
	 */
	/*------------------------------------------------------------------*/
	private static void test08()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			if (flag) {
				System.out.println(" * 8. 멀티기관 Thread 비동기 송수신...");
			}

			new SingleDeveloper("LOCAL").start();
			new SingleDeveloper("HWI01").start();
			new SingleDeveloper("HKI01").start();
			
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {}
		}
	}
	
	/*==================================================================*/
	/**
	 * test09 : & 처리
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test09() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFepid = null;
		String strInsid = null;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 9. & 처리....");
				}
				
				if (flag) {
					strFepid = System.getProperty("system.ib.param.fepid", "HWI01");
					strInsid = System.getProperty("system.ib.param.insid", "L01");
				}

				// 송신전문 -> 전문송신
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReq09Xml(strFepid, strInsid);  // 한화생명 OK
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				//servant.sendXml(strReqSoapXml);     // Send XML
				
				// 전문수신 -> 수신전문
				//String strResSoapXml = servant.recvXml();    // Recv XML

				//if (flag) System.out.println("RES: " + strResSoapXml);

				// 잠시기다린다.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test10 : 기관별 개시전문
	 * 
	 * FEPID : INSID
	 * 
	 *     한화생명     HWI01 L01
	 *     신한생명     SHI01 L11
	 *     하나HSBC생명 HNI01 L63
	 *     흥국생명     HKI01 L04
	 *     카디프생명   KFI01 L78
	 *     알리안츠생명 ALI01 L02
	 *     삼성생명     SSI01 L03
	 *     미래에셋생명 MII01 L34
	 *     농협생명     NHI01 L42
	 *     노란우산공제 NRI01 Y97
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test10() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFepid = null;
		String strInsid = null;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 10. 전문단건 송수신....");
				}
				
				if (flag) {
					strFepid = System.getProperty("system.ib.param.fepid", "HWI01");
					strInsid = System.getProperty("system.ib.param.insid", "L01");
				}

				// 송신전문 -> 전문송신
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReqFepidXml(strFepid, strInsid);  // 한화생명 OK
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// 전문수신 -> 수신전문
				String strResSoapXml = servant.recvXml();    // Recv XML

				if (flag) System.out.println("RES: " + strResSoapXml);

				// 잠시기다린다.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test11 : 신한 SHI01 072100 확인용
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test11() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 11. 신한 SHI01 072100 확인용....");
				}
				
				for (int i=0; i < 100; i++) {
					// 송신전문 -> 전문송신
					String strReqSoapXml = null;
					strReqSoapXml = Sample01Xml.getReq11Xml();  // 한화생명 OK
					
					if (flag) System.out.println(String.format("[%03d] REQ [%s]", i, strReqSoapXml));
					
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// 전문수신 -> 수신전문
					String strResSoapXml = servant.recvXml();    // Recv XML

					if (flag) System.out.println(String.format("[%03d] RES [%s]", i, strResSoapXml));

					// 잠시기다린다.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
				
				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test12 : polling 전문 송수신
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test12() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 12. 전문단건 송수신....");
				}
				
				for (int i=0; i < 100; i++) {
					// 송신전문 -> 전문송신
					String strReqSoapXml = null;
					strReqSoapXml = Sample01Xml.getReq11Xml();  // 한화생명 OK
					
					if (flag) System.out.println(String.format("[%03d] REQ [%s]", i, strReqSoapXml));
					
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// 전문수신 -> 수신전문
					String strResSoapXml = servant.recvXml();    // Recv XML

					if (flag) System.out.println(String.format("[%03d] RES [%s]", i, strResSoapXml));

					// 잠시기다린다.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
				
				// 프로그램 완전 종료
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test13 : Socket.connect(.. timeout)을 테스트 한다.
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test13() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				if (flag) {
					System.out.println(" * 13. Socket.connect(.. timeout)을 테스트 한다.....");
				}
				
				new IBridgeServant();
				
				try {
					Thread.sleep(600 * 1000);
				} catch (InterruptedException e) {}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test14
	 * 
	 * org.xml.sax.SAXParseException: The element type "br" must be terminated by the matching end-tag "</br>".
	 * 
	 * &lt; br &gt; -> org.xml.sax.SAXParseException 발생됨
	 * 
	 * 아래 소스는 확인하기 위함임...
	 */
	/*------------------------------------------------------------------*/
	private static void test14() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				IBridgeServant servant = new IBridgeServant();
				
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 14. org.xml.sax.SAXParseException 발생됨...");
				}
				
				// 송신전문 -> 전문송신
				String strReqSoapXml = null;
				String strKey = null;

				strReqSoapXml = Sample01Xml.makeTest14SampleXml();  // 테스트 전문
				if (flag) System.out.println("REQ: " + strReqSoapXml);

				//strKey = XmlTool.getSoapKey(strReqSoapXml);               // error
				//strKey = XmlTool.getSoapKey(strReqSoapXml, "EUC-KR");     // error
				strKey = XmlTool.getSoapKey(strReqSoapXml, "UTF-8");      // OK
				if (flag) System.out.println("strKey: " + strKey);
				
				// 잠시기다린다.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// 프로그램 완전 종료
				System.exit(0);

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
		
		System.out.println("START...");
		
		int switchNo = 1;
		if (flag){
			switchNo = Integer.parseInt(System.getProperty("system.ib.param.switchno", "1"));
			//switchNo = 11;
		}
		//switchNo = 14;
		
		switch (switchNo) {
		case 1 : if (flag) test01(); break;  // 단건 송수신
		case 2 : if (flag) test02(); break;
		case 3 : if (flag) test03(); break;
		case 4 : if (flag) test04(); break;
		case 5 : if (flag) test05(); break;  // 멀티송신 멀티수신
		case 6 : if (flag) test06(); break;
		case 7 : if (flag) test07(); break;  // IBridgeServant 별 멀티송신 멀티수신
		case 8 : if (flag) test08(); break;  // 
		
		case 9 : if (flag) test09(); break;  // & 처리 확인 
		case 10: if (flag) test10(); break;  // 기관별 개시전문
		case 11: if (flag) test11(); break;  // 신한 SHI01 072100 확인용
		case 12: if (flag) test12(); break;  // polling 전문 송수신 -> 미사용
		case 13: if (flag) test13(); break;  // Socket.connect(.. timeout)을 테스트 한다.
		case 14: if (flag) test14(); break;  // org.xml.sax.SAXParseException: The element type "br" must be terminated by the matching end-tag "</br>".
		default: if (flag) default_test00(); break;
		}
		
		System.exit(0);
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
