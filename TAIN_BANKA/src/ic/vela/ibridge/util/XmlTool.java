package ic.vela.ibridge.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/*==================================================================*/
/**
 * XmlTool Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlTool
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - ���� ��� ����
 * 
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
public class XmlTool 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	@SuppressWarnings("unused")
	private static final long          MILLISEC_DAY        = 24 * 60 * 60 * 1000;
	
	private static final String        TAG_TRX_ID          = "/Bancassurance/HeaderArea/HDR_TRX_ID";
	private static final String        TAG_BAK_DOCSEQ      = "/Bancassurance/HeaderArea/HDR_BAK_DOCSEQ";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	
	/*==================================================================*/
	/**
	 * get a charset from XML Document
	 * @param strXml
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static String getXmlCharset(String strXml)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * default charset
		 */
		String strCharset = "EUC-KR";
		
		if (flag) {
			if (strXml.indexOf("UTF-8") >= 0) 
			{
				/*
				 * searched UTF-8
				 */
				return "UTF-8";
			} 
			else if (strXml.indexOf("utf-8") >= 0) 
			{
				/*
				 * searched utf-8 to UTF-8
				 */
				return "UTF-8";
			}
		}
		
		return strCharset;
	}
	
	
	/*==================================================================*/
	/**
	 * get a charset from XML Document
	 * @param strXml
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public static String getXmlCharset_DATE20130820(String strXml)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * default charset
		 */
		String strCharset = "UTF-8";
		
		if (flag) {
			if (strXml.indexOf("EUC-KR") >= 0) 
			{
				/*
				 * searched EUC-KR
				 */
				return "EUC-KR";
			} 
			else if (strXml.indexOf("euc-kr") >= 0) 
			{
				/*
				 * searched euc-kr to EUC-KR
				 */
				return "EUC-KR";
			}
		}
		
		return strCharset;
	}
	
	
	/*==================================================================*/
	/**
	 * �ش� SoapXML�� KEY�� ���Ѵ�.
	 * 
	 * KEY : FEPID(5) + NOSEQ(8)
	 * NOSEQ(8) : NO(2) + SEQ(6)
	 * 
	 * FEPID �� �Ʒ��� ����.
	 * 
	 * NOSEQ�� NO�� AP�� �����ϴ� ������ IB ��ȣ�� ó���Ѵ�.
	 * SEQ�� �� NO�� �ش��ϴ� ������ �����ϱ� ���� SEQ�̴�
	 * �� SEQ�� �� ���� ���� �Ǵ� �׽�Ʈ�ϴ� PC���� �����Ǿ�� �Ѵ�.
	 * 
	 * @param strSoapXml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getSoapKey(String strSoapXml, String charset) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strKey = null;
		
		if (flag) {
			/*
			 * SAX �� �̿��Ͽ� KEY�� ��´�.
			 */
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();

			InputSource input = null;
			
			input = new InputSource(new ByteArrayInputStream(strSoapXml.getBytes(charset)));
			String strTrxId     = ((NodeList) xPath.evaluate(TAG_TRX_ID,     input, XPathConstants.NODESET)).item(0).getTextContent();
			
			input = new InputSource(new ByteArrayInputStream(strSoapXml.getBytes(charset)));
			String strBakDocSeq = ((NodeList) xPath.evaluate(TAG_BAK_DOCSEQ, input, XPathConstants.NODESET)).item(0).getTextContent();

			strKey = String.format("%-5.5s%-8.8s", strTrxId, strBakDocSeq);
		}
		
		return strKey;
	}
	
	/*==================================================================*/
	/**
	 * �ش� SoapXML�� KEY�� ���Ѵ�.
	 * 
	 * KEY : FEPID(5) + NOSEQ(8)
	 * NOSEQ(8) : NO(2) + SEQ(6)
	 * 
	 * FEPID �� �Ʒ��� ����.
	 * 
	 * NOSEQ�� NO�� AP�� �����ϴ� ������ IB ��ȣ�� ó���Ѵ�.
	 * SEQ�� �� NO�� �ش��ϴ� ������ �����ϱ� ���� SEQ�̴�
	 * �� SEQ�� �� ���� ���� �Ǵ� �׽�Ʈ�ϴ� PC���� �����Ǿ�� �Ѵ�.
	 * 
	 * @param strSoapXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getSoapKey(String strSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		/*
		 * default charset is EUC-KR
		 */
		return getSoapKey(strSoapXml, "EUC-KR");
	}
	
	/*==================================================================*/
	/**
	 * �ش� StreamXML�� KEY�� ���Ѵ�.
	 * 
	 *     String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
	 *     String strHDR_BAK_DOCSEQ = new String(byteStreamXml,  57,  8).trim(); // [ 8]
	 *     
	 * KEY : FEPID(5) + NOSEQ(8)
	 * NOSEQ(8) : NO(2) + SEQ(6)
	 * 
	 * FEPID �� �Ʒ��� ����.
	 * 
	 * NOSEQ�� NO�� AP�� �����ϴ� ������ IB ��ȣ�� ó���Ѵ�.
	 * SEQ�� �� NO�� �ش��ϴ� ������ �����ϱ� ���� SEQ�̴�
	 * �� SEQ�� �� ���� ���� �Ǵ� �׽�Ʈ�ϴ� PC���� �����Ǿ�� �Ѵ�.
	 * 
	 * @param strSoapXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getStreamKey(String strStreamXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strKey = null;
		
		if (flag) {   // DATE.2013.06.23
			
			byte[] byteStreamXml = strStreamXml.getBytes();
			
			String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
			String strHDR_BAK_DOCSEQ = new String(byteStreamXml,  57,  8).trim(); // [ 8]
			
			strKey = String.format("%-5.5s%-8.8s", strHDR_TRX_ID, strHDR_BAK_DOCSEQ);
		}
		
		return strKey;
	}
	
	/*==================================================================*/
	/**
	 * �ش� SoapXML�� FEPID�� ���Ѵ�.
	 * 
	 * @param strSoapXml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getSoapFepid(String strSoapXml, String charset) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFepid = null;
		
		if (flag) {
			/*
			 * SAX �� �̿��Ͽ� KEY�� ��´�.
			 */
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();

			InputSource input = null;
			
			input = new InputSource(new ByteArrayInputStream(strSoapXml.getBytes(charset)));
			String strTrxId     = ((NodeList) xPath.evaluate(TAG_TRX_ID,     input, XPathConstants.NODESET)).item(0).getTextContent();
			
			strFepid = String.format("%-5.5s", strTrxId);
		}
		
		return strFepid;
	}
	
	/*==================================================================*/
	/**
	 * �ش� SoapXML�� FEPID�� ���Ѵ�.
	 * 
	 * @param strSoapXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getSoapFepid(String strSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		/*
		 * default charset is EUC-KR
		 */
		return getSoapKey(strSoapXml, "EUC-KR");
	}
	
	/*==================================================================*/
	/**
	 * �ش� StreamXML�� FEPID�� ���Ѵ�.
	 * 
	 *     String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
	 * 
	 * @param strSoapXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String getStreamFepid(String strStreamXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFepid = null;
		
		if (flag) {   // DATE.2013.06.23
			
			byte[] byteStreamXml = strStreamXml.getBytes();
			
			String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
			
			strFepid = String.format("%-5.5s", strHDR_TRX_ID);
		}
		
		return strFepid;
	}
	
	/*==================================================================*/
	/**
	 * SoapXml -> StreamXml
	 * 
	 * @param strSoapXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transferSoapXmlToStreamXml(String strSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		return transferSoapXmlToStreamXml(strSoapXml, "EUC-KR");
	}

	/*==================================================================*/
	/**
	 * SoapXml -> StreamXml
	 * 
	 * @param strSoapXml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transferSoapXmlToStreamXml(String strSoapXml, String charset) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strStreamXml = null;
		
		if (flag) {
			/*
			 * make a document from strSoapXml
			 */
			ByteArrayInputStream bais = new ByteArrayInputStream(strSoapXml.getBytes(charset));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(bais);
			bais.close();
			
			/*
			 * get the items of the header from the strSoapXml document
			 */
			String txtHDR_TRX_ID     = document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent();   // TRX ID          [ 9] S   : ������
			String txtHDR_SYS_ID     = document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent();   // SYSTEM ID       [ 3] S   : BAS
			@SuppressWarnings("unused")
			String txtHDR_DOC_LEN    = document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent();   // ��ü��������    [ 5] S R : ��ü���� : 00000
			String txtHDR_DAT_GBN    = document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent();   // �ڷᱸ��        [ 1] S   : T:Test, R:real
			String txtHDR_INS_ID     = document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent();   // ����� ID       [ 3] S R : ������ڵ�(�����ڵ�1033����)
			String txtHDR_BAK_CLS    = document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent();   // ���� ����       [ 2] S   : �������Ǳ���(�����ڵ�1022����)
			String txtHDR_BAK_ID     = document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent();   // ���� ID         [ 3] S R : �����ڵ�(�����ڵ�1024����)
			String txtHDR_DOC_CODE   = document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent();   // �������� �ڵ�   [ 4] S R : ���������ڵ�(����LIST����)
			String txtHDR_BIZ_CODE   = document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent();   // �ŷ����� �ڵ�   [ 6] S   : �ŷ������ڵ�(����LIST����)
			String txtHDR_TRA_FLAG   = document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent();   // �ۼ��� FLAG     [ 1] S R : ���� <-> ����(->1, <- 5),����<->����(->2, <-6)
			String txtHDR_DOC_STATUS = document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent();   // STATUS          [ 3] S R : ����-�÷�ID(������׸�)
			String txtHDR_RET_CODE   = document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent();   // �����ڵ�        [ 3] S R : �����ڵ�(����ο����ø����)
			String txtHDR_SND_DATE   = document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent();   // ����������      [ 8] S R : YYYYMMDD
			String txtHDR_SND_TIME   = document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent();   // �������۽ð�    [ 6] S R : HHMMSS
			String txtHDR_BAK_DOCSEQ = document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent();   // ���� ������ȣ   [ 8] S   : �����Ϸù�ȣ
			String txtHDR_INS_DOCSEQ = document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent();   // ����� ������ȣ [ 8]   R : �����Ϸù�ȣ
			String txtHDR_TXN_DATE   = document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent();   // �ŷ��߻���      [ 8] S R : YYYYMMDD
			String txtHDR_TOT_DOC    = document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent();   // ��ü ���� ��    [ 2] S   : ��ü����:01
			String txtHDR_CUR_DOC    = document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent();   // ������������    [ 2] S   : ��������:01
			String txtHDR_AGT_CODE   = document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent();   // ó����/�ܸ���ȣ [15] S   : ó���� ���/ �ܸ���ȣ (���10byte,�ܸ���ȣ5byte)
			String txtHDR_BAK_EXT    = document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent();   // ���� �߰�����   [30] S R : FILLER
			String txtHDR_INS_EXT    = document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent();   // ����� �߰����� [30] S R : FILLER

			/*
			 * remove the header from the strSoapXml document
			 */
			Element tagBancassurance = document.getDocumentElement();
			tagBancassurance.removeChild(document.getElementsByTagName("HeaderArea").item(0));
			
			/*
			 * BusinessArea �±��� ������ ������ strStreamBodyXml�� "" �̴�
			 */
			String strStreamBodyXml = null;
			Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
			if (tagBusinessArea == null) {
				strStreamBodyXml = "";
			} else {
				strStreamBodyXml = transNodeToXml(tagBancassurance, true, charset);
			}
			
			// PRINT : strStreamBodyXml
			if (!flag) System.out.println("strStreamBodyXml = [" + strStreamBodyXml + "]");
			
			/*
			 *  BodyXml �� ���̸� ���Ѵ�.
			 */
			int iStreamBodyLen = strStreamBodyXml.getBytes(charset).length;
			
			/*
			 *  StreamHeader �� �����.
			 */
			String strStreamHeader = null;
			String strStreamHeaderFormat = "%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s"
					+ "%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s";
			
			strStreamHeader = String.format(strStreamHeaderFormat
					, txtHDR_TRX_ID
					, txtHDR_SYS_ID
					// , txtHDR_DOC_LEN
					, 160 + iStreamBodyLen    // ��ü���� : header + body
					, txtHDR_DAT_GBN
					, txtHDR_INS_ID
					, txtHDR_BAK_CLS
					, txtHDR_BAK_ID
					, txtHDR_DOC_CODE
					, txtHDR_BIZ_CODE
					, txtHDR_TRA_FLAG
					//, txtHDR_DOC_STATUS
					, Integer.parseInt(txtHDR_DOC_STATUS)
					, txtHDR_RET_CODE
					, txtHDR_SND_DATE
					, txtHDR_SND_TIME
					, txtHDR_BAK_DOCSEQ
					, txtHDR_INS_DOCSEQ
					, txtHDR_TXN_DATE
					, txtHDR_TOT_DOC
					, txtHDR_CUR_DOC
					, txtHDR_AGT_CODE
					, txtHDR_BAK_EXT
					, txtHDR_INS_EXT
					);
			
			// PRINT : 
			if (!flag) {
				System.out.println("strStreamHeader [" + strStreamHeader.getBytes().length + "] [" + strStreamHeader + "]");
				System.out.println("strStreamBodyXml [" + iStreamBodyLen + "] [" + strStreamBodyXml + "]");
			}
			
			/*
			 *  StreamXml �� �����. HeaderStream + BodyXml
			 */
			strStreamXml = strStreamHeader + strStreamBodyXml;
			
			// PRINT : 
			if (!flag) {
				System.out.println("strStreamXml [" + strStreamXml + "]");
			}
		}
		
		return strStreamXml;
	}
	
	/*==================================================================*/
	/**
	 * StreamXml -> SoapXml
	 * 
	 * @param strStreamXml
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transferStreamXmlToSoapXml(String strStreamXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		return transferStreamXmlToSoapXml(strStreamXml, "EUC-KR");
	}

	/*==================================================================*/
	/**
	 * StreamXml -> SoapXml
	 * TODO DATE.20130819 : ���� xml ������ �׳� ��ȯ�Ѵ�.
	 * 
	 * @param StreamXml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transferStreamXmlToSoapXml(String strStreamXml, String charset) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strSoapXml = null;
		
		if (flag) {
			/*
			 * get the byteStreamXml and iStreamXml
			 */
			byte[] byteStreamXml = strStreamXml.getBytes();
			int iStreamXml = byteStreamXml.length;
			
			/*
			 * get the streamHeader and streamBody from byteStreamXml
			 */
			String strStreamHeader = new String(byteStreamXml, 0, 160);
			String strStreamBodyXml = new String(byteStreamXml, 160, iStreamXml - 160);
			
			// PRINT :
			if (!flag) {
				System.out.println("strStreamHeader [160] [" + strStreamHeader + "]");
				System.out.println("strStreamBodyXml [" + (iStreamXml-160) + "] [" + strStreamBodyXml + "]");
			}
			
			/*
			 * make the header from the streamHeader
			 */
			String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
			String strHDR_SYS_ID     = new String(byteStreamXml,   9,  3).trim(); // [ 3]
			String strHDR_DOC_LEN    = new String(byteStreamXml,  12,  5).trim(); // [ 5]
			String strHDR_DAT_GBN    = new String(byteStreamXml,  17,  1).trim(); // [ 1]
			String strHDR_INS_ID     = new String(byteStreamXml,  18,  3).trim(); // [ 3]
			String strHDR_BAK_CLS    = new String(byteStreamXml,  21,  2).trim(); // [ 2]
			String strHDR_BAK_ID     = new String(byteStreamXml,  23,  3).trim(); // [ 3]
			String strHDR_DOC_CODE   = new String(byteStreamXml,  26,  4).trim(); // [ 4]
			String strHDR_BIZ_CODE   = new String(byteStreamXml,  30,  6).trim(); // [ 6]
			String strHDR_TRA_FLAG   = new String(byteStreamXml,  36,  1).trim(); // [ 1]
			String strHDR_DOC_STATUS = new String(byteStreamXml,  37,  3).trim(); // [ 3]
			String strHDR_RET_CODE   = new String(byteStreamXml,  40,  3).trim(); // [ 3]
			String strHDR_SND_DATE   = new String(byteStreamXml,  43,  8).trim(); // [ 8]
			String strHDR_SND_TIME   = new String(byteStreamXml,  51,  6).trim(); // [ 6]
			String strHDR_BAK_DOCSEQ = new String(byteStreamXml,  57,  8).trim(); // [ 8]
			String strHDR_INS_DOCSEQ = new String(byteStreamXml,  65,  8).trim(); // [ 8]
			String strHDR_TXN_DATE   = new String(byteStreamXml,  73,  8).trim(); // [ 8]
			String strHDR_TOT_DOC    = new String(byteStreamXml,  81,  2).trim(); // [ 2]
			String strHDR_CUR_DOC    = new String(byteStreamXml,  83,  2).trim(); // [ 2]
			String strHDR_AGT_CODE   = new String(byteStreamXml,  85, 15).trim(); // [15]
			String strHDR_BAK_EXT    = new String(byteStreamXml, 100, 30).trim(); // [30]
			String strHDR_INS_EXT    = new String(byteStreamXml, 130, 30).trim(); // [30]
			
			StringBuffer sbHeaderXml = new StringBuffer();

			if (strStreamBodyXml.indexOf("<Bancassurance>") < 0) {
				/*
				 * <Bancassurance> �±� ����.
				 */
				sbHeaderXml.append("<?xml version=\"1.0\" encoding=\"" + charset +"\"?>\n");
				sbHeaderXml.append("<Bancassurance>\n");
				sbHeaderXml.append("<HeaderArea>\n");
				sbHeaderXml.append("<HDR_TRX_ID>"    + strHDR_TRX_ID     + "</HDR_TRX_ID>\n"    );
				sbHeaderXml.append("<HDR_SYS_ID>"    + strHDR_SYS_ID     + "</HDR_SYS_ID>\n"    );
				sbHeaderXml.append("<HDR_DOC_LEN>"   + strHDR_DOC_LEN    + "</HDR_DOC_LEN>\n"   );
				sbHeaderXml.append("<HDR_DAT_GBN>"   + strHDR_DAT_GBN    + "</HDR_DAT_GBN>\n"   );
				sbHeaderXml.append("<HDR_INS_ID>"    + strHDR_INS_ID     + "</HDR_INS_ID>\n"    );
				sbHeaderXml.append("<HDR_BAK_CLS>"   + strHDR_BAK_CLS    + "</HDR_BAK_CLS>\n"   );
				sbHeaderXml.append("<HDR_BAK_ID>"    + strHDR_BAK_ID     + "</HDR_BAK_ID>\n"    );
				sbHeaderXml.append("<HDR_DOC_CODE>"  + strHDR_DOC_CODE   + "</HDR_DOC_CODE>\n"  );
				sbHeaderXml.append("<HDR_BIZ_CODE>"  + strHDR_BIZ_CODE   + "</HDR_BIZ_CODE>\n"  );
				sbHeaderXml.append("<HDR_TRA_FLAG>"  + strHDR_TRA_FLAG   + "</HDR_TRA_FLAG>\n"  );
				sbHeaderXml.append("<HDR_DOC_STATUS>"+ strHDR_DOC_STATUS + "</HDR_DOC_STATUS>\n");
				sbHeaderXml.append("<HDR_RET_CODE>"  + strHDR_RET_CODE   + "</HDR_RET_CODE>\n"  );
				sbHeaderXml.append("<HDR_SND_DATE>"  + strHDR_SND_DATE   + "</HDR_SND_DATE>\n"  );
				sbHeaderXml.append("<HDR_SND_TIME>"  + strHDR_SND_TIME   + "</HDR_SND_TIME>\n"  );
				sbHeaderXml.append("<HDR_BAK_DOCSEQ>"+ strHDR_BAK_DOCSEQ + "</HDR_BAK_DOCSEQ>\n");
				sbHeaderXml.append("<HDR_INS_DOCSEQ>"+ strHDR_INS_DOCSEQ + "</HDR_INS_DOCSEQ>\n");
				sbHeaderXml.append("<HDR_TXN_DATE>"  + strHDR_TXN_DATE   + "</HDR_TXN_DATE>\n"  );
				sbHeaderXml.append("<HDR_TOT_DOC>"   + strHDR_TOT_DOC    + "</HDR_TOT_DOC>\n"   );
				sbHeaderXml.append("<HDR_CUR_DOC>"   + strHDR_CUR_DOC    + "</HDR_CUR_DOC>\n"   );
				sbHeaderXml.append("<HDR_AGT_CODE>"  + strHDR_AGT_CODE   + "</HDR_AGT_CODE>\n"  );
				sbHeaderXml.append("<HDR_BAK_EXT>"   + strHDR_BAK_EXT    + "</HDR_BAK_EXT>\n"   );
				sbHeaderXml.append("<HDR_INS_EXT>"   + strHDR_INS_EXT    + "</HDR_INS_EXT>\n"   );
				sbHeaderXml.append("</HeaderArea>\n");
				sbHeaderXml.append("</Bancassurance>\n");
				
				strSoapXml = sbHeaderXml.toString();
				
			} else {
				/*
				 * <Bancassurance> �±� �ִ�.
				 * replace
				 */
				sbHeaderXml.append("<Bancassurance>\n");
				sbHeaderXml.append("<HeaderArea>\n");
				sbHeaderXml.append("<HDR_TRX_ID>"    + strHDR_TRX_ID     + "</HDR_TRX_ID>\n"    );
				sbHeaderXml.append("<HDR_SYS_ID>"    + strHDR_SYS_ID     + "</HDR_SYS_ID>\n"    );
				sbHeaderXml.append("<HDR_DOC_LEN>"   + strHDR_DOC_LEN    + "</HDR_DOC_LEN>\n"   );
				sbHeaderXml.append("<HDR_DAT_GBN>"   + strHDR_DAT_GBN    + "</HDR_DAT_GBN>\n"   );
				sbHeaderXml.append("<HDR_INS_ID>"    + strHDR_INS_ID     + "</HDR_INS_ID>\n"    );
				sbHeaderXml.append("<HDR_BAK_CLS>"   + strHDR_BAK_CLS    + "</HDR_BAK_CLS>\n"   );
				sbHeaderXml.append("<HDR_BAK_ID>"    + strHDR_BAK_ID     + "</HDR_BAK_ID>\n"    );
				sbHeaderXml.append("<HDR_DOC_CODE>"  + strHDR_DOC_CODE   + "</HDR_DOC_CODE>\n"  );
				sbHeaderXml.append("<HDR_BIZ_CODE>"  + strHDR_BIZ_CODE   + "</HDR_BIZ_CODE>\n"  );
				sbHeaderXml.append("<HDR_TRA_FLAG>"  + strHDR_TRA_FLAG   + "</HDR_TRA_FLAG>\n"  );
				sbHeaderXml.append("<HDR_DOC_STATUS>"+ strHDR_DOC_STATUS + "</HDR_DOC_STATUS>\n");
				sbHeaderXml.append("<HDR_RET_CODE>"  + strHDR_RET_CODE   + "</HDR_RET_CODE>\n"  );
				sbHeaderXml.append("<HDR_SND_DATE>"  + strHDR_SND_DATE   + "</HDR_SND_DATE>\n"  );
				sbHeaderXml.append("<HDR_SND_TIME>"  + strHDR_SND_TIME   + "</HDR_SND_TIME>\n"  );
				sbHeaderXml.append("<HDR_BAK_DOCSEQ>"+ strHDR_BAK_DOCSEQ + "</HDR_BAK_DOCSEQ>\n");
				sbHeaderXml.append("<HDR_INS_DOCSEQ>"+ strHDR_INS_DOCSEQ + "</HDR_INS_DOCSEQ>\n");
				sbHeaderXml.append("<HDR_TXN_DATE>"  + strHDR_TXN_DATE   + "</HDR_TXN_DATE>\n"  );
				sbHeaderXml.append("<HDR_TOT_DOC>"   + strHDR_TOT_DOC    + "</HDR_TOT_DOC>\n"   );
				sbHeaderXml.append("<HDR_CUR_DOC>"   + strHDR_CUR_DOC    + "</HDR_CUR_DOC>\n"   );
				sbHeaderXml.append("<HDR_AGT_CODE>"  + strHDR_AGT_CODE   + "</HDR_AGT_CODE>\n"  );
				sbHeaderXml.append("<HDR_BAK_EXT>"   + strHDR_BAK_EXT    + "</HDR_BAK_EXT>\n"   );
				sbHeaderXml.append("<HDR_INS_EXT>"   + strHDR_INS_EXT    + "</HDR_INS_EXT>\n"   );
				sbHeaderXml.append("</HeaderArea>");
				
				strSoapXml = strStreamBodyXml.replaceFirst("<Bancassurance>", sbHeaderXml.toString());
			}
			
			if (!flag) System.out.println("strSoapXml [" + strSoapXml + "]");
		}
		
		return strSoapXml;
	}
	
	
	/*==================================================================*/
	/**
	 * StreamXml -> SoapXml
	 * 
	 * @param StreamXml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transferStreamXmlToSoapXml_DATE20130819(String strStreamXml, String charset) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strSoapXml = null;
		
		if (flag) {
			/*
			 * get the byteStreamXml and iStreamXml
			 */
			byte[] byteStreamXml = strStreamXml.getBytes();
			int iStreamXml = byteStreamXml.length;
			
			/*
			 * get the streamHeader and streamBody from byteStreamXml
			 */
			String strStreamHeader = new String(byteStreamXml, 0, 160);
			String strStreamBodyXml = new String(byteStreamXml, 160, iStreamXml - 160);
			
			// PRINT :
			if (!flag) {
				System.out.println("strStreamHeader [160] [" + strStreamHeader + "]");
				System.out.println("strStreamBodyXml [" + (iStreamXml-160) + "] [" + strStreamBodyXml + "]");
			}
			
			/*
			 * make the header from the streamHeader
			 */
			String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9).trim(); // [ 9]
			String strHDR_SYS_ID     = new String(byteStreamXml,   9,  3).trim(); // [ 3]
			String strHDR_DOC_LEN    = new String(byteStreamXml,  12,  5).trim(); // [ 5]
			String strHDR_DAT_GBN    = new String(byteStreamXml,  17,  1).trim(); // [ 1]
			String strHDR_INS_ID     = new String(byteStreamXml,  18,  3).trim(); // [ 3]
			String strHDR_BAK_CLS    = new String(byteStreamXml,  21,  2).trim(); // [ 2]
			String strHDR_BAK_ID     = new String(byteStreamXml,  23,  3).trim(); // [ 3]
			String strHDR_DOC_CODE   = new String(byteStreamXml,  26,  4).trim(); // [ 4]
			String strHDR_BIZ_CODE   = new String(byteStreamXml,  30,  6).trim(); // [ 6]
			String strHDR_TRA_FLAG   = new String(byteStreamXml,  36,  1).trim(); // [ 1]
			String strHDR_DOC_STATUS = new String(byteStreamXml,  37,  3).trim(); // [ 3]
			String strHDR_RET_CODE   = new String(byteStreamXml,  40,  3).trim(); // [ 3]
			String strHDR_SND_DATE   = new String(byteStreamXml,  43,  8).trim(); // [ 8]
			String strHDR_SND_TIME   = new String(byteStreamXml,  51,  6).trim(); // [ 6]
			String strHDR_BAK_DOCSEQ = new String(byteStreamXml,  57,  8).trim(); // [ 8]
			String strHDR_INS_DOCSEQ = new String(byteStreamXml,  65,  8).trim(); // [ 8]
			String strHDR_TXN_DATE   = new String(byteStreamXml,  73,  8).trim(); // [ 8]
			String strHDR_TOT_DOC    = new String(byteStreamXml,  81,  2).trim(); // [ 2]
			String strHDR_CUR_DOC    = new String(byteStreamXml,  83,  2).trim(); // [ 2]
			String strHDR_AGT_CODE   = new String(byteStreamXml,  85, 15).trim(); // [15]
			String strHDR_BAK_EXT    = new String(byteStreamXml, 100, 30).trim(); // [30]
			String strHDR_INS_EXT    = new String(byteStreamXml, 130, 30).trim(); // [30]
			
			StringBuffer sbHeaderXml = new StringBuffer();
			sbHeaderXml.append("<HeaderArea>\n");
			sbHeaderXml.append("<HDR_TRX_ID>"    + strHDR_TRX_ID     + "</HDR_TRX_ID>\n"    );
			sbHeaderXml.append("<HDR_SYS_ID>"    + strHDR_SYS_ID     + "</HDR_SYS_ID>\n"    );
			sbHeaderXml.append("<HDR_DOC_LEN>"   + strHDR_DOC_LEN    + "</HDR_DOC_LEN>\n"   );
			sbHeaderXml.append("<HDR_DAT_GBN>"   + strHDR_DAT_GBN    + "</HDR_DAT_GBN>\n"   );
			sbHeaderXml.append("<HDR_INS_ID>"    + strHDR_INS_ID     + "</HDR_INS_ID>\n"    );
			sbHeaderXml.append("<HDR_BAK_CLS>"   + strHDR_BAK_CLS    + "</HDR_BAK_CLS>\n"   );
			sbHeaderXml.append("<HDR_BAK_ID>"    + strHDR_BAK_ID     + "</HDR_BAK_ID>\n"    );
			sbHeaderXml.append("<HDR_DOC_CODE>"  + strHDR_DOC_CODE   + "</HDR_DOC_CODE>\n"  );
			sbHeaderXml.append("<HDR_BIZ_CODE>"  + strHDR_BIZ_CODE   + "</HDR_BIZ_CODE>\n"  );
			sbHeaderXml.append("<HDR_TRA_FLAG>"  + strHDR_TRA_FLAG   + "</HDR_TRA_FLAG>\n"  );
			sbHeaderXml.append("<HDR_DOC_STATUS>"+ strHDR_DOC_STATUS + "</HDR_DOC_STATUS>\n");
			sbHeaderXml.append("<HDR_RET_CODE>"  + strHDR_RET_CODE   + "</HDR_RET_CODE>\n"  );
			sbHeaderXml.append("<HDR_SND_DATE>"  + strHDR_SND_DATE   + "</HDR_SND_DATE>\n"  );
			sbHeaderXml.append("<HDR_SND_TIME>"  + strHDR_SND_TIME   + "</HDR_SND_TIME>\n"  );
			sbHeaderXml.append("<HDR_BAK_DOCSEQ>"+ strHDR_BAK_DOCSEQ + "</HDR_BAK_DOCSEQ>\n");
			sbHeaderXml.append("<HDR_INS_DOCSEQ>"+ strHDR_INS_DOCSEQ + "</HDR_INS_DOCSEQ>\n");
			sbHeaderXml.append("<HDR_TXN_DATE>"  + strHDR_TXN_DATE   + "</HDR_TXN_DATE>\n"  );
			sbHeaderXml.append("<HDR_TOT_DOC>"   + strHDR_TOT_DOC    + "</HDR_TOT_DOC>\n"   );
			sbHeaderXml.append("<HDR_CUR_DOC>"   + strHDR_CUR_DOC    + "</HDR_CUR_DOC>\n"   );
			sbHeaderXml.append("<HDR_AGT_CODE>"  + strHDR_AGT_CODE   + "</HDR_AGT_CODE>\n"  );
			sbHeaderXml.append("<HDR_BAK_EXT>"   + strHDR_BAK_EXT    + "</HDR_BAK_EXT>\n"   );
			sbHeaderXml.append("<HDR_INS_EXT>"   + strHDR_INS_EXT    + "</HDR_INS_EXT>\n"   );
			sbHeaderXml.append("</HeaderArea>\n");
			
			String strSoapHeaderXml = sbHeaderXml.toString();
			
			if (!flag) {
				System.out.println("strSoapHeaderXml [" + strSoapHeaderXml + "]");
			}
			
			/*
			 * make the body xml
			 * DATE.2013.06.12 : body �κ��� ���� ���������� BusinessArea �ױװ� ����.
			 */
			String strSoapBodyXml = null;
			
			if (strStreamBodyXml == null || "".equals(strStreamBodyXml)) {
				strSoapBodyXml = "";
			} else {
				// body �κ��� bodyXml �� �����.
				ByteArrayInputStream bais = new ByteArrayInputStream(strStreamBodyXml.getBytes(charset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// document ���� body �� ��´�.
				Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
				strSoapBodyXml = transNodeToXml(tagBusinessArea, false, charset);
				
				// PRINT :
				if (!flag) {
					System.out.println("strSoapBodyXml [" + strSoapBodyXml + "]");
				}
			}

			/*
			 *  SoapXml �� �����. <Bancassurance> + headerXml + bodyXml + </Bancassurance>
			 */
			StringBuffer sbSoapXml = new StringBuffer();
			if (flag) sbSoapXml.append("<?xml version=\"1.0\" encoding=\"" + charset +"\"?>");
			sbSoapXml.append("<Bancassurance>");
			sbSoapXml.append(strSoapHeaderXml);
			sbSoapXml.append(strSoapBodyXml);
			sbSoapXml.append("</Bancassurance>");

			strSoapXml = sbSoapXml.toString();
			
			if (!flag) {
				System.out.println("strSoapXml [" + strSoapXml + "]");
			}
		}
		
		return strSoapXml;
	}
	
	
	/*==================================================================*/
	/**
	 * Node -> Xml
	 * 
	 * @param document
	 * @param flag
	 * @param strCharset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transNodeToXml(Document document, boolean flag, String strCharset) throws Exception
	/*------------------------------------------------------------------*/
	{
		StringBuffer sb = null;
		
		if (flag)
			sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?>");
		else
			sb = new StringBuffer("");
		
		return transNodeToXml(sb, document.getDocumentElement());
	}
	
	/*==================================================================*/
	/**
	 * Node -> Xml
	 * 
	 * @param node
	 * @param flag
	 * @param strCharset
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public static String transNodeToXml(Node node, boolean flag, String strCharset) throws Exception
	/*------------------------------------------------------------------*/
	{
		StringBuffer sb = null;
		
		if (flag)
			sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?>");
		else
			sb = new StringBuffer("");

		return transNodeToXml(sb, node);
	}
	
	/*==================================================================*/
	/**
	 * Node -> Xml
	 * 
	 * @param sb
	 * @param node
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static String transNodeToXml(StringBuffer sb, Node node) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * Node�� TAG ó���� �Ѵ�.
			 */
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				sb.append("<" + node.getNodeName());
				NamedNodeMap attributes = node.getAttributes();
				if (attributes.getLength() != 0) {
					for (int i = 0 ; i < attributes.getLength() ; i++) {
						transNodeToXml(sb, attributes.item(i));
					}
				}
				sb.append(">");
				NodeList elementNodeList = node.getChildNodes();
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						transNodeToXml(sb, elementNodeList.item(i));
					}
				}
				sb.append("</" + node.getNodeName() + ">");
				break;
			case Node.ATTRIBUTE_NODE:
				sb.append(" " + node.getNodeName());
				sb.append("=\"" + node.getNodeValue().trim() + "\"");
				break;
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
					//
				} else {
					// sb.append(nodeValue);
					/*
					 * DATE.20130629 : �յڰ�������
					 * DATE.20130705 : & ó���� �߰���.
					 */
					sb.append(nodeValue.trim().replaceAll("&", "&amp;"));
				}
				break;
			}
		}
		
		return sb.toString();
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
				String strStreamXml = null;

				strStreamXml = "HNI01    BAS16098TL6302269021007210050010002013081920212900012587        201308190101100000000000000                                                            ";
				System.out.println("[" + XmlTool.transferStreamXmlToSoapXml(strStreamXml) + "]");

				strStreamXml = "HNI01    BAS16098TL6302269021007210050010002013081920212900012587        201308190101100000000000000                                                            "
					+ "<?xml version=\"1.0\" encoding=\"EUC-KR\"?>";
				System.out.println("[" + XmlTool.transferStreamXmlToSoapXml(strStreamXml) + "]");

				strStreamXml = "HNI01    BAS16098TL6302269021007210050010002013081920212900012587        201308190101100000000000000                                                            "
					+ "<Bancassurance>\n"
					+ "<BusinessArea>\n"
				    + "    <PITEM>603073</PITEM>\n"
					+ "</BusinessArea>\n"
					+ "</Bancassurance>\n";
				System.out.println("[" + XmlTool.transferStreamXmlToSoapXml(strStreamXml) + "]");

				strStreamXml = "HNI01    BAS16098TL6302269021007210050010002013081920212900012587        201308190101100000000000000                                                            "
					+ "<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\n" 
					+ "<Bancassurance>\n"
					+ "<BusinessArea>\n"
				    + "    <PITEM>603073</PITEM>\n"
					+ "</BusinessArea>\n"
					+ "</Bancassurance>\n";
				System.out.println("[" + XmlTool.transferStreamXmlToSoapXml(strStreamXml) + "]");
				
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
