package ic.vela.ibridge.common.version;

/*==================================================================*/
/**
 * IBTestMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBTestMain
 * @author  ����
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
	 * - AP�������� AP�� IB�� �и���.
	 * - ��ȭ���� ���� �����ڵ鵵 ���� �� �� �ֵ��� IB ��ü�� �и���.
	 * - IB ��ü�� AP�� TCP/IP ����� ��.
	 * - IBridgeServant�� ��ü�� ������ �����ص� ��� ����
	 * - IBridgeServant�� ��ü�� ����纰�� ������ �� ����.
	 * - IBridgeServant���� �񵿱�/���� ������� ���� �ۼ��� ����
	 * - ��ƼFEP�� �������� ��ܱ���� �����Ͽ� ó�� �� �� ����.
	 * 
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130623.0";

	/*
	 * 
	 * - ��Ƽ IBridgeServant ó��
	 * - ��Ƽ IB ó��
	 * - ��Ƽ AP ó��
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130628.2";

	/*
	 * 
	 * - ����ó��
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130703.3";

	/*
	 * 
	 * - �α�ó������ ���� ���� : ��¥���濡 ���� �α����ϵ� ��¥�����.
	 *      ApLogger ExtLogger FepLogger IBridgeLogger.java ����
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130705.4";

	/*
	 * 
	 * - ��ġ���� ó���� ���� ��� �߰� : �ﱹ����, ���ѻ���
	 * 
	 */
	//private static String              version             = "IB_Vela.2.130708.5";

	/*
	 * DATE.2013.07.13
	 * - Polling ó�� �߰�, yes/no
	 * system.ib.param.host.91.poll           = yes, 20, 30
	 * 20 : polling ������ �۽��Ѵ�.
	 * 30 : polling ���� �۽� �Ŀ� ������ ������ 30 �� �Ŀ� ���α׷��� �����Ѵ�.
	 * 	private static final int           TRYCONNECT_TIMEOUT  = 5000;   // milliseconds
	 */
	//private static String              version             = "IB_Vela.2.130716.6";

	/*
	 * DATE.2013.07.25
	 * ���ǿ����� ���� ���ӽõ��ð��� ������ �� �ֵ��� ��.
	 * - private static final int           TRYCONNECT_TIMEOUT  = 10000;   // milliseconds
	 */
	//private static String              version             = "IB_Vela.2.130725.6";

	/*
	 * DATE.2013.07.31
	 * �����ڵ� BAK_ID �� 021 -> 269 �� ������
	 */
	//private static String              version             = "IB_Vela.2.130731.7";

	/*
	 * DATE.2013.08.19
	 * 1. ib ���� ���� ������ �������� �ʰ� �״�� recvXml�� ����
	 * 2. SocketSelector.tryMultiConnection ���� �߻��Ǵ� ���
	 * �Ʒ� ������ ���ӽõ��� �����Ǵ� Thread, AttachObject���� ó���ϱ�� �Ѵ�.
	 *     1. ������ ��Ʈ�� ������ �������� �ݹ� ������ �õ��Ѵ�.
	 *     2. ������ �Ǿ� ���� ������ ���ӽõ��� ���� 1�� �̻��� �ð��� �Ҹ��Ѵ�.
	 *     3. 2�� �׸����� ��Ÿ �ٸ� ���񽺿� ��ָ� ����Ų��.
	 * 
	 * -> �缳�谡 �ʿ��� ��Ȳ�� �Ǿ����ϴ�. �׷��� ����UP�� �����ϴ�.
	 */
	//private static String              version             = "IB_Vela.2.130819.8";

	/*
	 * DATE.2013.09.08
	 * �ϳ����� ��ġó���� ���� ������.
	 *   ����Ʈ�� ū ��ġ������ 1,022,000���� �ɰ��� ���ϸ� 1-N, 2-N.. �� ���̸鼭
	 *   �۽��Ѵ�. �� doDataTransfer()���� �������� ������ �����ؾ� �Ѵ�.
	 */
	//private static String              version             = "IB_Vela.2.130908.8";

	/*
	 * DATE.2013.09.09
	 * �Լ� : public static String XmlPoll.get(String strFepid, String strServerCode)
	 * Poll ó���� <HDR_DAT_GBN>T/R</HDR_DAT_GBN>
	 */
	//private static String              version             = "IB_Vela.2.130909.8";

	/*
	 * DATE.2013.11.08
	 * �̷����� ���� �׸� 8���� �߰� : search file���� '�̷�����'�� ã���� �ȴ�.
	 */
	//private static String              version             = "IB_Vela.2.131108.9";

	/*
	 * DATE.2013.12.23
	 * �������� ���� �׸� 8���� �߰� : search file���� '��������'�� ã���� �ȴ�.
	 */
	//private static String              version             = "IB_Vela.2.131223.10";

	/*
	 * DATE.2015.11.11
	 * ���������(NRI01, Y97) ���� �׸� 8���� �߰� : search file���� '���������'�� ã���� �ȴ�.
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
				System.out.println(" * IBridge ������ " + getVersion() + " �Դϴ�.");
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
