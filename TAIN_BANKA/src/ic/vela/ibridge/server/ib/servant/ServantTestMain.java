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
 * ServantTestMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServantTestMain
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * ȯ�溯���� �Ʒ��� ���� �����ؾ� �Ѵ�.
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
					System.out.println(" * 1. �����ܰ� �ۼ���....");
				}
				
				// �۽����� -> �����۽�
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReqFepidXml("HWI01", "L01");  // ��ȭ���� OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("SHI01", "L11");  // ���ѻ��� OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("HNI01", "L63");  // �ϳ�HSBC����
				//strReqSoapXml = Sample01Xml.getReqFepidXml("HKI01", "L04");  // �ﱹ����  OK
				//strReqSoapXml = Sample01Xml.getReqFepidXml("KFI01", "L78");  // ī��������
				//strReqSoapXml = Sample01Xml.getReqFepidXml("ALI01", "L02");  // �˸���������
				//strReqSoapXml = Sample01Xml.getReqFepidXml("SSI01", "L03");  // �Ｚ����
				//strReqSoapXml = Sample01Xml.getReqFepidXml("MII01", "L34");  // �̷����»���
				//strReqSoapXml = Sample01Xml.getReqFepidXml("NHI01", "L34");  // ��������
				//strReqSoapXml = Sample01Xml.getReqFepidXml("NRI01", "Y97");  // ���������
				//strReqSoapXml = Sample01Xml.getReqFepidXml("LOCAL", "L01");  // ERROR
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// �������� -> ��������
				String strResSoapXml = servant.recvXml();    // Recv XML

				if (flag) System.out.println("RES: " + strResSoapXml);

				// ��ñ�ٸ���.
				try {
					// Thread.sleep(500000);
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				// ���α׷� ���� ����
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
					System.out.println(" * 2. ���� �ۼ���(����)�� loopcount��ŭ �ݺ�....");
				}

				// ��û�۽�����
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
					// ��û�۽������� ����Ѵ�.
					if (flag) System.out.println("REQ: " + strReqSoapXml);
					
					// ��û�۽������� �۽��Ѵ�.
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// ������������� �����Ѵ�. ��ٸ��鼭
					String strResSoapXml = servant.recvXml();    // Recv XML
					
					// ������������� ����Ѵ�.
					if (flag) System.out.println("RES: " + strResSoapXml);

					// ��ñ�ٸ���.
					System.out.println("----------------[" + i + "] " + waitTime + "�� ��� ��ٸ��ϴ�. ------------------");
					try {
						Thread.sleep(waitTime * 1000);
					} catch (InterruptedException e) {}
				}

				// ���α׷� ���� ����
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
				 * sync �ۼ���ó�� ���
				 * �׳� sendXml �ϰ� recvXml �� ��ٸ���.
				 */

				IBridgeServant servant = new IBridgeServant();
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 3. �����ܰ� �ۼ���. �Ϲ�����...");
				}
				
				// ��û������ �����Ѵ�.
				String strReqSoapXml = XmlObject_20130622.setHdrBakDocseq(Sample01Xml.getReqXml());
				if (flag) System.out.println("### REQ : " + strReqSoapXml);

				// ��û������ �۽��Ѵ�.
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// ���������� ���� ������ ��ٸ���.
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

			// ���α׷� ����
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
				 * �ۼ��� ó���� �ϴ� ��ü�� �����Ѵ�.
				 * �� ��ü�� �۽Ű� ������ async �������
				 * ó���Ѵ�.
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
				
				// �������� �Ѳ����� �۽��Ѵ�.
				// send n times
				for (int i=0; i < loopCount; i++) {

					// ��û�۽������� �����Ѵ�. �Ʒ� ��û������ �׽�Ʈ�� ���� ���̰�
					// �����ڵ��� ������� �����ּ���.
					String strReqSoapXml = XmlObject_20130622.setHdrBakDocseq(Sample01Xml.getReqXml());

					// ��û������ ������ ������.
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
				 * �������� �Ѳ����� �����Ѵ�.
				 * recv n times
				 */
				for (int i=0; i < 100000; i++) {
					/*
					 * ���������� ���� �غ� �Ѵ�.
					 * 
					 * ������������� �����Ѵ�. ��ٸ��鼭
					 * recvXml(FEPID, BLOCKTIME)
					 *         FEPID�� ����� FEP��ŷ���� �ο��� ID
					 *         BLOCKTIME �� ���������� ��ٸ��� �ִ�ð�, ���� ��� return �ȴ�.
					 *         ���� BLOCKTIME �� 0 �̸� ���������� ������ ��ٸ���. (sync �� ����)
					 *         async �� �����Ϸ��� BLOCKTIME �� Ư���ð�(millisecond) ���� �����ϸ�
					 *         �ȴ�.
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
				 * ������ �׽�Ʈ�� ����� �۽Ű� ������ async ������� ����Ǹ�
				 * ���Ӽ۽��ϴ� ��� ����ó���� ������ �� ������ �� �ݴ��� ��쵵
				 * �����ϴ�.
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
				 * ���� 2������ ��Ƽ������ ��Ƽ��ܱ�� ó���� ����
				 * ���踦 �����Ͽ� ��������ϴ�.
				 * 
				 * ��Ƽ��ܱ���� ���� �۾��߿� �ְ�,
				 * ��Ƽ������ ���ؼ��� ������PC�� WAS������ �����ϴ�
				 * ��Ű���� ��ġ�Ͻð� �Ʒ��� ���� ��� ������ ������Ƽ
				 * ������ �����Ͽ� �غ��Ͻø� �����Ͻ� ���α׷��� ����
				 * �ϸ� �˴ϴ�.
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
				 * ���� �����Ͻ� ������ FEP���α׷����� �ν��ϱ� ���ؼ���
				 * �Ʒ��� ���� property �� WAS ���α׷� ���࿡ �߰��ϼž�
				 * �մϴ�.
				 * 
				 *     -Dsystem.ib.param.basefolder=...../server/
				 *   
				 * server �ڿ��� '/'�� �ݵ�� �ٿ��� �մϴ�. �׸��� �α״�
				 * ...../server/ib/log/YYYYMMDD.log ���Ϸ� �Ǿ� �ֽ��ϴ�.
				 * 
				 */
				
				/*
				 * �ۼ��� ó���� �ϴ� ��ü�� �����Ѵ�.
				 * �� ��ü�� �۽Ű� ������ async �������
				 * ó���Ѵ�.
				 */
				IBridgeServant servant = new IBridgeServant();
				if (flag) {
					System.out.println("VERSION : " + servant.getVersion());
					System.out.println(" * 5. ���� �ۼ���(�񵿱�).  loopcount ��ŭ �ݺ�...");
				}

				/*
				 * ��Ű���� version�� Ȯ���Ѵ�.
				 * ��Ƽó���� ���� ������ �߰��� version�� 2 �̴�.
				 */
				System.out.println("Version : " + servant.getVersion());
				
				// int waitTime = 2;
				int loopCount = 2;
				if (flag) {
					loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
				}

				/*
				 *  �������� �Ѳ����� �۽��Ѵ�.
				 *  �����ʰ� loopCount ��ŭ �۽Ÿ� �Ѵ�.
				 *  �񵿱� ��� ó�� �׽�Ʈ��
				 */
				for (int i=0; i < loopCount; i++) {

					/*
					 *  ��û�۽������� �����Ѵ�. �Ʒ� ��û������ �׽�Ʈ�� ���� ���̰�
					 *  �����ڵ��� ������� �����ּ���.
					 *  �����ڴ� ���� ����翡�� ����ϴ� XML ������ ����ϱ� �ٶ�.
					 */
					String strReqSoapXml = Sample01Xml.getReqXml();
					
					/*
					 *  ��û������ ������.
					 *  FEPID�� ������ ���Եȴ�.
					 *  ������ HDR_TRX_ID (9 bytes)�� ������ �ȴ�. �Ʒ� ó�� ���ٶ�.
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
				 * �������� �Ѳ����� �����Ѵ�.
				 * recv n times
				 */
				for (int i=1; i < 100000; i++) {
					/*
					 * ���������� ���� �غ� �Ѵ�.
					 * 
					 * ������������� �����Ѵ�. ��ٸ��鼭
					 * recvXml(BLOCKTIME)
					 *         BLOCKTIME �� ���������� ��ٸ��� �ִ�ð�, ���� ��� return �ȴ�.
					 *         ���� BLOCKTIME �� 0 �̸� ���������� ������ ��ٸ���. (sync �� ����)
					 *         async �� �����Ϸ��� BLOCKTIME �� Ư���ð�(millisecond) ���� �����ϸ�
					 *         �ȴ�.
					 */
					
					/*
					 *  receiveing XML : waiting data for max 2 seconds
					 *  �Ʒ� �ҽ��� �ִ� 2�ʰ� ��ٸ���.
					 *  ��ٸ��� ���̿� ���������� ������ ��ٷ� �����Ѵ�.
					 */
					String strResSoapXml = servant.recvXml(2000);

					if (strResSoapXml != null) {
						/*
						 *  receive the response message from the server, and not null
						 *  ���� ���������� ȭ�鿡 ����Ѵ�.
						 */
						if (flag) System.out.println("\nRES[" + i + "]: " + strResSoapXml);
					}
					else {
						/*
						 * �񵿱����� Ȯ���ϱ� ���� # �����.
						 */
						if (flag) System.out.print("#");
					}
					
					/*
					 *  wait for some seconds.  loop wait time
					 *  ���� �� ó���ð��� ������ loop wait time �̴�.
					 *  �� �׸��� �������� �ʾƵ� �ȴ�.
					 */
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}
				
				/*
				 * ������ �׽�Ʈ�� ����� �۽Ű� ������ async ������� ����Ǹ�
				 * ���Ӽ۽��ϴ� ��� ����ó���� ������ �� ������ �� �ݴ��� ��쵵
				 * �����ϴ�.
				 * �ҽ��� �ñ��Ͻ� ���� �����ø� �Ʒ��� �����ϼ���.
				 *                  ������ : �������� (010-4258-2025)
				 * �����մϴ�.
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
					System.out.println(" * 6. ���� �ۼ���(����).  loopcount�� ��ŭ �ݺ�....");
				}
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}

				int waitTime = 1;
				int loopCount = Integer.parseInt(System.getProperty("system.ib.param.loopcount", "10"));
				
				for (int i=0; i < loopCount; i++) {
					String strReqSoapXml = Sample01Xml.getReqXml();

					// ��û�۽������� ����Ѵ�.
					if (flag) System.out.println("REQ: " + strReqSoapXml);
					
					// ��û�۽������� �۽��Ѵ�.
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// ������������� �����Ѵ�. ��ٸ��鼭
					String strResSoapXml = servant.recvXml(0);    // Recv XML
					
					// ������������� ����Ѵ�.
					if (flag) System.out.println("RES: " + strResSoapXml);

					// ��ñ�ٸ���.
					System.out.println("----------------[" + i + "] " + waitTime + "�� ��� ��ٸ��ϴ�. ------------------");
					try {
						Thread.sleep(waitTime * 1000);
					} catch (InterruptedException e) {}
				}

				// ���α׷� ���� ����
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
					System.out.println(" * 7. ��ƼIBridgeServant �� ��Ƽ�۽� ��Ƽ����...");
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
				System.out.println(" * 8. ��Ƽ��� Thread �񵿱� �ۼ���...");
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
	 * test09 : & ó��
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
					System.out.println(" * 9. & ó��....");
				}
				
				if (flag) {
					strFepid = System.getProperty("system.ib.param.fepid", "HWI01");
					strInsid = System.getProperty("system.ib.param.insid", "L01");
				}

				// �۽����� -> �����۽�
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReq09Xml(strFepid, strInsid);  // ��ȭ���� OK
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				//servant.sendXml(strReqSoapXml);     // Send XML
				
				// �������� -> ��������
				//String strResSoapXml = servant.recvXml();    // Recv XML

				//if (flag) System.out.println("RES: " + strResSoapXml);

				// ��ñ�ٸ���.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// ���α׷� ���� ����
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test10 : ����� ��������
	 * 
	 * FEPID : INSID
	 * 
	 *     ��ȭ����     HWI01 L01
	 *     ���ѻ���     SHI01 L11
	 *     �ϳ�HSBC���� HNI01 L63
	 *     �ﱹ����     HKI01 L04
	 *     ī��������   KFI01 L78
	 *     �˸��������� ALI01 L02
	 *     �Ｚ����     SSI01 L03
	 *     �̷����»��� MII01 L34
	 *     ��������     NHI01 L42
	 *     ��������� NRI01 Y97
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
					System.out.println(" * 10. �����ܰ� �ۼ���....");
				}
				
				if (flag) {
					strFepid = System.getProperty("system.ib.param.fepid", "HWI01");
					strInsid = System.getProperty("system.ib.param.insid", "L01");
				}

				// �۽����� -> �����۽�
				String strReqSoapXml = null;
				strReqSoapXml = Sample01Xml.getReqFepidXml(strFepid, strInsid);  // ��ȭ���� OK
				
				if (flag) System.out.println("REQ: " + strReqSoapXml);
				
				servant.sendXml(strReqSoapXml);     // Send XML
				
				// �������� -> ��������
				String strResSoapXml = servant.recvXml();    // Recv XML

				if (flag) System.out.println("RES: " + strResSoapXml);

				// ��ñ�ٸ���.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// ���α׷� ���� ����
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test11 : ���� SHI01 072100 Ȯ�ο�
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
					System.out.println(" * 11. ���� SHI01 072100 Ȯ�ο�....");
				}
				
				for (int i=0; i < 100; i++) {
					// �۽����� -> �����۽�
					String strReqSoapXml = null;
					strReqSoapXml = Sample01Xml.getReq11Xml();  // ��ȭ���� OK
					
					if (flag) System.out.println(String.format("[%03d] REQ [%s]", i, strReqSoapXml));
					
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// �������� -> ��������
					String strResSoapXml = servant.recvXml();    // Recv XML

					if (flag) System.out.println(String.format("[%03d] RES [%s]", i, strResSoapXml));

					// ��ñ�ٸ���.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
				
				// ���α׷� ���� ����
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test12 : polling ���� �ۼ���
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
					System.out.println(" * 12. �����ܰ� �ۼ���....");
				}
				
				for (int i=0; i < 100; i++) {
					// �۽����� -> �����۽�
					String strReqSoapXml = null;
					strReqSoapXml = Sample01Xml.getReq11Xml();  // ��ȭ���� OK
					
					if (flag) System.out.println(String.format("[%03d] REQ [%s]", i, strReqSoapXml));
					
					servant.sendXml(strReqSoapXml);     // Send XML
					
					// �������� -> ��������
					String strResSoapXml = servant.recvXml();    // Recv XML

					if (flag) System.out.println(String.format("[%03d] RES [%s]", i, strResSoapXml));

					// ��ñ�ٸ���.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
				
				// ���α׷� ���� ����
				System.exit(0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/*==================================================================*/
	/**
	 * test13 : Socket.connect(.. timeout)�� �׽�Ʈ �Ѵ�.
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
					System.out.println(" * 13. Socket.connect(.. timeout)�� �׽�Ʈ �Ѵ�.....");
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
	 * &lt; br &gt; -> org.xml.sax.SAXParseException �߻���
	 * 
	 * �Ʒ� �ҽ��� Ȯ���ϱ� ������...
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
					System.out.println(" * 14. org.xml.sax.SAXParseException �߻���...");
				}
				
				// �۽����� -> �����۽�
				String strReqSoapXml = null;
				String strKey = null;

				strReqSoapXml = Sample01Xml.makeTest14SampleXml();  // �׽�Ʈ ����
				if (flag) System.out.println("REQ: " + strReqSoapXml);

				//strKey = XmlTool.getSoapKey(strReqSoapXml);               // error
				//strKey = XmlTool.getSoapKey(strReqSoapXml, "EUC-KR");     // error
				strKey = XmlTool.getSoapKey(strReqSoapXml, "UTF-8");      // OK
				if (flag) System.out.println("strKey: " + strKey);
				
				// ��ñ�ٸ���.
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				// ���α׷� ���� ����
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
		case 1 : if (flag) test01(); break;  // �ܰ� �ۼ���
		case 2 : if (flag) test02(); break;
		case 3 : if (flag) test03(); break;
		case 4 : if (flag) test04(); break;
		case 5 : if (flag) test05(); break;  // ��Ƽ�۽� ��Ƽ����
		case 6 : if (flag) test06(); break;
		case 7 : if (flag) test07(); break;  // IBridgeServant �� ��Ƽ�۽� ��Ƽ����
		case 8 : if (flag) test08(); break;  // 
		
		case 9 : if (flag) test09(); break;  // & ó�� Ȯ�� 
		case 10: if (flag) test10(); break;  // ����� ��������
		case 11: if (flag) test11(); break;  // ���� SHI01 072100 Ȯ�ο�
		case 12: if (flag) test12(); break;  // polling ���� �ۼ��� -> �̻��
		case 13: if (flag) test13(); break;  // Socket.connect(.. timeout)�� �׽�Ʈ �Ѵ�.
		case 14: if (flag) test14(); break;  // org.xml.sax.SAXParseException: The element type "br" must be terminated by the matching end-tag "</br>".
		default: if (flag) default_test00(); break;
		}
		
		System.exit(0);
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
