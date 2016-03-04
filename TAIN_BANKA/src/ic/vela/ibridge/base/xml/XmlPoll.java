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
 * XmlPoll 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlPoll
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 
 * - polling 전문은 
<?xml version="1.0" encoding="UTF-8"?>
<Bancassurance>
    <Field001 TagName="HDR_TRX_ID    " KorName="TRX ID         " EngName="HDR_TRX_ID    " Type="CHAR" Length=" 9" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="은행사용" />
    <Field002 TagName="HDR_SYS_ID    " KorName="SYSTEM ID      " EngName="HDR_SYS_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="BAS" />
    <Field003 TagName="HDR_DOC_LEN   " KorName="전체전문길이   " EngName="HDR_DOC_LEN   " Type="CHAR" Length=" 5" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="전체길이" />
    <Field004 TagName="HDR_DAT_GBN   " KorName="자료구분       " EngName="HDR_DAT_GBN   " Type="CHAR" Length=" 1" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="T:Test, R:real" />
    <Field005 TagName="HDR_INS_ID    " KorName="보험사 ID      " EngName="HDR_INS_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="보험사코드(공통코드1033참조) " />
    <Field006 TagName="HDR_BAK_CLS   " KorName="은행 구분      " EngName="HDR_BAK_CLS   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="은행증권구분(공통코드1022참조)" />
    <Field007 TagName="HDR_BAK_ID    " KorName="은행 ID        " EngName="HDR_BAK_ID    " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="은행코드(공통코드1024참조)" />
    <Field008 TagName="HDR_DOC_CODE  " KorName="전문종별 코드  " EngName="HDR_DOC_CODE  " Type="CHAR" Length=" 4" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="전문종별코드(전문LIST참조)" />
    <Field009 TagName="HDR_BIZ_CODE  " KorName="거래구분 코드  " EngName="HDR_BIZ_CODE  " Type="CHAR" Length=" 6" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="거래구분코드(전문LIST참조)" />
    <Field010 TagName="HDR_TRA_FLAG  " KorName="송수신 FLAG    " EngName="HDR_TRA_FLAG  " Type="CHAR" Length=" 1" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)" />
    <Field011 TagName="HDR_DOC_STATUS" KorName="STATUS         " EngName="HDR_DOC_STATUS" Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="에라-컬럼ID(공통부항목만)" />
    <Field012 TagName="HDR_RET_CODE  " KorName="응답코드       " EngName="HDR_RET_CODE  " Type="CHAR" Length=" 3" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="응답코드(공통부에러시만사용)" />
    <Field013 TagName="HDR_SND_DATE  " KorName="전문전송일     " EngName="HDR_SND_DATE  " Type="CHAR" Length=" 8" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="YYYYMMDD" />
    <Field014 TagName="HDR_SND_TIME  " KorName="전문전송시간   " EngName="HDR_SND_TIME  " Type="CHAR" Length=" 6" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="HHMMSS" />
    <Field015 TagName="HDR_BAK_DOCSEQ" KorName="은행 전문번호  " EngName="HDR_BAK_DOCSEQ" Type="CHAR" Length=" 8" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="전문일련번호" />
    <Field016 TagName="HDR_INS_DOCSEQ" KorName="보험사 전문번호" EngName="HDR_INS_DOCSEQ" Type="CHAR" Length=" 8" InSet=" " OutSet="Y" GroupTagName="Bancassurance" Desc="전문일련번호" />
    <Field017 TagName="HDR_TXN_DATE  " KorName="거래발생일     " EngName="HDR_TXN_DATE  " Type="CHAR" Length=" 8" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="YYYYMMDD" />
    <Field018 TagName="HDR_TOT_DOC   " KorName="전체 전문 수   " EngName="HDR_TOT_DOC   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="전체전문:01" />
    <Field019 TagName="HDR_CUR_DOC   " KorName="현재전문순번   " EngName="HDR_CUR_DOC   " Type="CHAR" Length=" 2" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="현재전문:01" />
    <Field020 TagName="HDR_AGT_CODE  " KorName="처리자/단말번호" EngName="HDR_AGT_CODE  " Type="CHAR" Length="15" InSet="Y" OutSet=" " GroupTagName="Bancassurance" Desc="처리자 사번/ 단말번호 (사번10byte,단말번호5byte)" />
    <Field021 TagName="HDR_BAK_EXT   " KorName="은행 추가정의  " EngName="HDR_BAK_EXT   " Type="CHAR" Length="30" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="FILLER" />
    <Field022 TagName="HDR_INS_EXT   " KorName="보험사 추가정의" EngName="HDR_INS_EXT   " Type="CHAR" Length="30" InSet="Y" OutSet="Y" GroupTagName="Bancassurance" Desc="FILLER" />
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
		{ "한화생명     ", "HWI01", "L01" },
		{ "신한생명     ", "SHI01", "L11" },
		{ "하나HSBC생명 ", "HNI01", "L63" },
		{ "흥국생명     ", "HKI01", "L04" },
		{ "카디프생명   ", "KFI01", "L78" },
		{ "알리안츠생명 ", "ALI01", "L02" },
		{ "삼성생명     ", "SSI01", "L03" },
		{ "미래에셋생명 ", "MII01", "L34" },
		{ "농협생명     ", "NHI01", "L42" },
		{ "노란우산공제 ", "NRI01", "Y97" },
		{ "LOCAL-PC     ", "LOCAL", "L00" },
	};
	
	/*==================================================================*/
	/**
	 * polling 전문을 얻는다.
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
			
			if (flag) {   // DATE.2013.06.12  : 개시전문
				/*
				 * 날짜와 시간을 얻는다.
				 */
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
				
				/*
				 * strArrFepid 로 insid를 얻는다.
				 */
				for (int i=0; i < strArrFepid.length; i++) {
					if (strArrFepid[i][1].equals(strFepid)) {
						strInsid = strArrFepid[i][2];
						break;
					}
				}
				
				/*
				 * FEPID : 대외기관 코드
				 * 
				 * INSID : 보험사코드
				 * 
				 */
				sb.append("<Bancassurance>                                            \n");
				sb.append("  <HeaderArea>                                             \n");
				sb.append("    <HDR_TRX_ID>" + strFepid + "</HDR_TRX_ID>              \n");   // TRX ID <- FEPID : 위 설명참조
				sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // 고정
				sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // 길이 : header + body
				sb.append("    <HDR_DAT_GBN>" + strServerCode + "</HDR_DAT_GBN>       \n");   // 자료구분 : TEST / REAL
				sb.append("    <HDR_INS_ID>" + strInsid + "</HDR_INS_ID>              \n");   // 보험사코드 : 위 설명참조
				sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // 은행증권구분 : 02 (증권사)
				sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // 은행코드 : 269 (한화증권)
				sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800 :
				sb.append("    <HDR_BIZ_CODE>006000</HDR_BIZ_CODE>                    \n");   // 006000 : 회선점검 거래코드
				sb.append("    <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                         \n");   // 송수신 FLAG
				sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
				sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // 응답코드
				sb.append("    <HDR_SND_DATE>" + strDate + "</HDR_SND_DATE>           \n");   // 전문전송일
				sb.append("    <HDR_SND_TIME>" + strTime + "</HDR_SND_TIME>           \n");   // 전문전송시간
				sb.append("    <HDR_BAK_DOCSEQ>00" + strTime + "</HDR_BAK_DOCSEQ>     \n");   // 은행 전문번호
				sb.append("    <HDR_INS_DOCSEQ>00000000</HDR_INS_DOCSEQ>              \n");   // 보험사 전문번호
				sb.append("    <HDR_TXN_DATE>" + strDate + "</HDR_TXN_DATE>           \n");   // 거래발생일
				sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // 전체 전문 수
				sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // 현재전문순번
				sb.append("    <HDR_AGT_CODE>KANG1004</HDR_AGT_CODE>                  \n");   // 처리자/단말번호
				sb.append("    <HDR_BAK_EXT>_POLLING_</HDR_BAK_EXT>                   \n");   // 은행 추가정의
				sb.append("    <HDR_INS_EXT></HDR_INS_EXT>                            \n");   // 보험사 추가정의
				sb.append("  </HeaderArea>                                            \n");
				sb.append("</Bancassurance>                                           \n");
			}
			
			if (!flag) {
				/*
				 * parser 처리를 한다.
				 */
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(CHARSET));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				if (!flag) {
					/*
					 * 특정항목 값을 세팅한다.
					 */
					String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
					System.out.println("[DOCSEQ=" + strDocSeq + "]");
					document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				}
				
				/*
				 * 전문을 생성한다. 나열된 xml
				 */
				strXml = XmlTool.transNodeToXml(document, true, CHARSET);
			}
			
			if (flag) {
				/*
				 * 전문생성 stream 문자열
				 */
				strXml = XmlTool.transferSoapXmlToStreamXml(sb.toString(), CHARSET);
			}
		}
		
		return strXml;
	}
	
	/*==================================================================*/
	/**
	 * polling 응답전문을 만든다.
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
	 * polling 전문인지 확인한다.
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
