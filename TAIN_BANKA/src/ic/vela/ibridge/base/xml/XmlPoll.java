package ic.vela.ibridge.base.xml;

import ic.vela.ibridge.base.seq.SeqGender;
import ic.vela.ibridge.util.XmlTool;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


/*==================================================================*/
/**
 * XmlPoll Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlPoll
 * @author  ����
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 
 * - polling ������ 
<?xml version="1.0" encoding="UTF-8"?>
<Bancassurance>
    <Field001 TagName="HDR_TRX_ID    " KorName="TRX ID         " EngName="HDR_TRX_ID    " Type="CHAR" Length=" 9" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="������" />
    <Field002 TagName="HDR_SYS_ID    " KorName="SYSTEM ID      " EngName="HDR_SYS_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="BAS" />
    <Field003 TagName="HDR_DOC_LEN   " KorName="��ü��������   " EngName="HDR_DOC_LEN   " Type="CHAR" Length=" 5" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="��ü����" />
    <Field004 TagName="HDR_DAT_GBN   " KorName="�ڷᱸ��       " EngName="HDR_DAT_GBN   " Type="CHAR" Length=" 1" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="T:Test, R:real" />
    <Field005 TagName="HDR_INS_ID    " KorName="����� ID      " EngName="HDR_INS_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="������ڵ�(�����ڵ�1033����) " />
    <Field006 TagName="HDR_BAK_CLS   " KorName="���� ����      " EngName="HDR_BAK_CLS   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="�������Ǳ���(�����ڵ�1022����)" />
    <Field007 TagName="HDR_BAK_ID    " KorName="���� ID        " EngName="HDR_BAK_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="�����ڵ�(�����ڵ�1024����)" />
    <Field008 TagName="HDR_DOC_CODE  " KorName="�������� �ڵ�  " EngName="HDR_DOC_CODE  " Type="CHAR" Length=" 4" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="���������ڵ�(����LIST����)" />
    <Field009 TagName="HDR_BIZ_CODE  " KorName="�ŷ����� �ڵ�  " EngName="HDR_BIZ_CODE  " Type="CHAR" Length=" 6" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="�ŷ������ڵ�(����LIST����)" />
    <Field010 TagName="HDR_TRA_FLAG  " KorName="�ۼ��� FLAG    " EngName="HDR_TRA_FLAG  " Type="CHAR" Length=" 1" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="���� <-> ����(->1, <- 5),����<->����(->2, <-6)" />
    <Field011 TagName="HDR_DOC_STATUS" KorName="STATUS         " EngName="HDR_DOC_STATUS" Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="����-�÷�ID(������׸�)" />
    <Field012 TagName="HDR_RET_CODE  " KorName="�����ڵ�       " EngName="HDR_RET_CODE  " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="�����ڵ�(����ο����ø����)" />
    <Field013 TagName="HDR_SND_DATE  " KorName="����������     " EngName="HDR_SND_DATE  " Type="CHAR" Length=" 8" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="YYYYMMDD" />
    <Field014 TagName="HDR_SND_TIME  " KorName="�������۽ð�   " EngName="HDR_SND_TIME  " Type="CHAR" Length=" 6" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="HHMMSS" />
    <Field015 TagName="HDR_BAK_DOCSEQ" KorName="���� ������ȣ  " EngName="HDR_BAK_DOCSEQ" Type="CHAR" Length=" 8" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="�����Ϸù�ȣ" />
    <Field016 TagName="HDR_INS_DOCSEQ" KorName="����� ������ȣ" EngName="HDR_INS_DOCSEQ" Type="CHAR" Length=" 8" InSet=" " OutSet="Y" GroupTagName="Bancassurance" Desc="�����Ϸù�ȣ" />
    <Field017 TagName="HDR_TXN_DATE  " KorName="�ŷ��߻���     " EngName="HDR_TXN_DATE  " Type="CHAR" Length=" 8" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="YYYYMMDD" />
    <Field018 TagName="HDR_TOT_DOC   " KorName="��ü ���� ��   " EngName="HDR_TOT_DOC   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="��ü����:01" />
    <Field019 TagName="HDR_CUR_DOC   " KorName="������������   " EngName="HDR_CUR_DOC   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="��������:01" />
    <Field020 TagName="HDR_AGT_CODE  " KorName="ó����/�ܸ���ȣ" EngName="HDR_AGT_CODE  " Type="CHAR" Length="15" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="ó���� ���/ �ܸ���ȣ (���10byte,�ܸ���ȣ5byte)" />
    <Field021 TagName="HDR_BAK_EXT   " KorName="���� �߰�����  " EngName="HDR_BAK_EXT   " Type="CHAR" Length="30" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="FILLER" />
    <Field022 TagName="HDR_INS_EXT   " KorName="����� �߰�����" EngName="HDR_INS_EXT   " Type="CHAR" Length="30" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="FILLER" />
</Bancassurance>
 * 
 */
/*==================================================================*/
public class XmlPoll
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	private static final String[][] strArrFepid = {
		{ "��ȭ����     ", "HWI01", "L01" },
		{ "���ѻ���     ", "SHI01", "L11" },
		{ "�ϳ�HSBC���� ", "HNI01", "L63" },
		{ "�ﱹ����     ", "HKI01", "L04" },
		{ "ī��������   ", "KFI01", "L78" },
		{ "�˸��������� ", "ALI01", "L02" },
		{ "�Ｚ����     ", "SSI01", "L03" },
		{ "�̷����»��� ", "MII01", "L34" },
		{ "��������     ", "NHI01", "L42" },
		{ "��������� ", "NRI01", "Y97" },
		{ "LOCAL-PC     ", "LOCAL", "L00" },
	};
	
	/*==================================================================*/
	/**
	 * polling ������ ��´�.
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String get(String strFepid, String strServerCode) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strInsid = null;
		String strXml = null;

		if (flag) {
			StringBuffer sb = new StringBuffer();
			
			if (flag) {   // DATE.2013.06.12  : ��������
				/*
				 * ��¥�� �ð��� ��´�.
				 */
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
				
				/*
				 * strArrFepid �� insid�� ��´�.
				 */
				for (int i=0; i < strArrFepid.length; i++) {
					if (strArrFepid[i][1].equals(strFepid)) {
						strInsid = strArrFepid[i][2];
						break;
					}
				}
				
				/*
				 * FEPID : ��ܱ�� �ڵ�
				 * 
				 * INSID : ������ڵ�
				 * 
				 */
				sb.append("<Bancassurance>                                            \n");
				sb.append("  <HeaderArea>                                             \n");
				sb.append("    <HDR_TRX_ID>" + strFepid + "</HDR_TRX_ID>              \n");   // TRX ID <- FEPID : �� ��������
				sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // ����
				sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // ���� : header + body
				sb.append("    <HDR_DAT_GBN>" + strServerCode + "</HDR_DAT_GBN>       \n");   // �ڷᱸ�� : TEST / REAL
				sb.append("    <HDR_INS_ID>" + strInsid + "</HDR_INS_ID>              \n");   // ������ڵ� : �� ��������
				sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
				sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
				sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800 :
				sb.append("    <HDR_BIZ_CODE>006000</HDR_BIZ_CODE>                    \n");   // 006000 : ȸ������ �ŷ��ڵ�
				sb.append("    <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
				sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
				sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
				sb.append("    <HDR_SND_DATE>" + strDate + "</HDR_SND_DATE>           \n");   // ����������
				sb.append("    <HDR_SND_TIME>" + strTime + "</HDR_SND_TIME>           \n");   // �������۽ð�
				sb.append("    <HDR_BAK_DOCSEQ>00" + strTime + "</HDR_BAK_DOCSEQ>     \n");   // ���� ������ȣ
				sb.append("    <HDR_INS_DOCSEQ>00000000</HDR_INS_DOCSEQ>              \n");   // ����� ������ȣ
				sb.append("    <HDR_TXN_DATE>" + strDate + "</HDR_TXN_DATE>           \n");   // �ŷ��߻���
				sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
				sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
				sb.append("    <HDR_AGT_CODE>KANG1004</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
				sb.append("    <HDR_BAK_EXT>_POLLING_</HDR_BAK_EXT>                   \n");   // ���� �߰�����
				sb.append("    <HDR_INS_EXT></HDR_INS_EXT>                            \n");   // ����� �߰�����
				sb.append("  </HeaderArea>                                            \n");
				sb.append("</Bancassurance>                                           \n");
			}
			
			if (!flag) {
				/*
				 * parser ó���� �Ѵ�.
				 */
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(CHARSET));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				if (!flag) {
					/*
					 * Ư���׸� ���� �����Ѵ�.
					 */
					String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
					System.out.println("[DOCSEQ=" + strDocSeq + "]");
					document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				}
				
				/*
				 * ������ �����Ѵ�. ������ xml
				 */
				strXml = XmlTool.transNodeToXml(document, true, CHARSET);
			}
			
			if (flag) {
				/*
				 * �������� stream ���ڿ�
				 */
				strXml = XmlTool.transferSoapXmlToStreamXml(sb.toString(), CHARSET);
			}
		}
		
		return strXml;
	}
	
	/*==================================================================*/
	/**
	 * polling ���������� �����.
	 * 
	 * @param strXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String set(String strXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strRetXml = null;
		
		if (flag) {
			strRetXml = strXml.replaceFirst("0800", "0810");
		}
		
		return strRetXml;
	}
	
	/*==================================================================*/
	/**
	 * polling �������� Ȯ���Ѵ�.
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static boolean isPoll(String strXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		boolean bRet = false;
		
		if (flag) {
			/*
			 * 0800 006000
			 * 0810 006000
			 * 0910 006000
			 * 9810 006000
			 */
			String strPollCode = strXml.substring(26, 36);
			
			if ("0800006000".equals(strPollCode) || "0810006000".equals(strPollCode) || "0910006000".equals(strPollCode) || "9810006000".equals(strPollCode)) {
				bRet = true;
			}
		}
		
		return bRet; 
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				String strXml = XmlPoll.get("HWI01", "T");
				boolean bPoll = XmlPoll.isPoll(strXml);
				System.out.println("[" + bPoll + "][" + strXml + "]");
				
				strXml = XmlPoll.set(strXml);
				bPoll = XmlPoll.isPoll(strXml);
				System.out.println("[" + bPoll + "][" + strXml + "]");
				
				
				String strInfo = "  no  123, 456   ";
				String[] strArrItem = strInfo.trim().split("[ ,]+");
				for (int i=0; i < strArrItem.length; i++) {
					System.out.println("(" + i + ") [" + strArrItem[i] + "]");
				}
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test02() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			for (int i=0; i < strArrFepid.length; i++) {
				System.out.printf("[%02d] [%5s] [%3s] %s\n", i, strArrFepid[i][1].trim(), strArrFepid[i][2], strArrFepid[i][0]);
			}
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
		
		switch (2) {
		case 1:
			if (flag) {
				test01();
			}
			break;
		case 2:
			if (flag) {
				test02();
			}
			break;
		default:
			if (flag) {
				System.out.println("default....");
			}
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
