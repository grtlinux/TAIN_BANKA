package ic.vela.ibridge.base.xml;

import ic.vela.ibridge.base.seq.SeqGender;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/**
* @name   XmlObject
* @author Kang Seok
* @date   2013.05.31
* 
*/
public class XmlObject_20130622 {

	/////////////////////////////////////////////////////////////////
	/*
	 * Private Static Final Variables
	 */
	
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	private Document document = null;

	@SuppressWarnings("unused")
	private Document docHeader = null;
	private Document docBody = null;

	private TransformerFactory transformerFactory = null;
	private Transformer transformer = null;

	private ByteArrayInputStream bais = null;
	private ByteArrayOutputStream baos = null;
	private PrintStream ps = null;
	
	private DOMSource domSource = null;
	private StreamResult streamResult = null;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Private Variables
	 */
	//private CharsetEncoder encoder = null;
	//private CharsetDecoder decoder = null;
	
	private String strSoapXml = null;
	//private String strSoapHeaderXml = null;
	//private String strSoapBodyXml = null;
	
	private String strStreamXml = null;
	//private String strStreamHeaderXml = null;
	//private String strStreamBodyXml = null;
	
	private String strHeaderXml = null;
	private String strBodyXml = null;
	
	private String strXmlCharset = null;
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * 생성자 : socket 을 넘겨 받는다.
	 */
	public XmlObject_20130622() { 
		boolean flag = true;
		
		if (flag) {
			try {
				factory = DocumentBuilderFactory.newInstance();
				builder = factory.newDocumentBuilder();

				transformerFactory = TransformerFactory.newInstance();
				transformer = transformerFactory.newTransformer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	public static String getXmlCharset(String strXml)
	{
		boolean flag = true;
		
		String strCharset = "UTF-8";
		if (flag) {
			if (strXml.indexOf("EUC-KR") >= 0) 
			{
				return "EUC-KR";
			} 
			else if (strXml.indexOf("euc-kr") >= 0) 
			{
				return "EUC-KR";
			}
		}
		
		return strCharset;
	}
	
	
	/////////////////////////////////////////////////////////////////
	/*
	 * transfer Node to Xml
	 */
	private static String transNodeToXml(Document document, boolean flag, String strCharset) {
		StringBuffer sb;
		if (flag)
			sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?>");
		else
			sb = new StringBuffer("");
		
		return transNodeToXml(sb, document.getDocumentElement());
	}
	
	private static String transNodeToXml(Node node, boolean flag, String strCharset) {
		StringBuffer sb;
		if (flag)
			sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?>");
		else
			sb = new StringBuffer("");

		return transNodeToXml(sb, node);
	}
	
	private static String transNodeToXml(StringBuffer sb, Node node) {
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
					sb.append(nodeValue.trim());
				}
				break;
		}
		
		return sb.toString();
	}              

	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * SoapXml -> StreamXml
	 */
	public void transferSoapXmlToStreamXml(String strSoapXml) {
		boolean flag = true;
		
		if (!flag) {    // DATE.2013.06.19 : NOT EXECUTE
			try {
				// store SoapXml
				this.strSoapXml = strSoapXml;
				
				// create document
				this.bais = new ByteArrayInputStream(this.strSoapXml.getBytes("UTF-8"));
				this.docBody = this.builder.parse(this.bais);
				
				// remove header
				Element tagBancassurance = this.docBody.getDocumentElement();
				tagBancassurance.removeChild(this.docBody.getElementsByTagName("HeaderArea").item(0));

				// 
				this.bais = new ByteArrayInputStream(this.strSoapXml.getBytes("UTF-8"));
				this.document = this.builder.parse(this.bais);

				if (flag) {
					// source document
					this.domSource = new DOMSource(tagBancassurance);

					// target document
					this.baos = new ByteArrayOutputStream();
					this.streamResult = new StreamResult(this.baos);

					// do the transformer
					transformer.transform(domSource, streamResult);
					
					// to get the result stream. : Body XML
					byte[] byteBodyXml = this.baos.toByteArray();
					// String strXml = this.baos.toString();

					int iBodySize = this.baos.size();
					
					// transfer header XML to stream XML : Header XML
					baos = new ByteArrayOutputStream();
					ps = new PrintStream(baos);
					
					ps.printf("%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s"
							, document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent()                    // TRX ID          [ 9] S   : 은행사용
							, document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent()                    // SYSTEM ID       [ 3] S   : BAS
							//, Integer.parseInt(document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent())  // 전체전문길이    [ 5] S R : 전체길이 : 00000
							, iBodySize
							, document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent()                    // 자료구분        [ 1] S   : T:Test, R:real
							, document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent()                    // 보험사 ID       [ 3] S R : 보험사코드(공통코드1033참조)
							, document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent()                    // 은행 구분       [ 2] S   : 은행증권구분(공통코드1022참조)
							, document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent()                    // 은행 ID         [ 3] S R : 은행코드(공통코드1024참조)
							, document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent()                    // 전문종별 코드   [ 4] S R : 전문종별코드(전문LIST참조)
							, document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent()                    // 거래구분 코드   [ 6] S   : 거래구분코드(전문LIST참조)
							, document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent()                    // 송수신 FLAG     [ 1] S R : 은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)
							, Integer.parseInt(document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent())  // STATUS          [ 3] S R : 에라-컬럼ID(공통부항목만)
							, document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent()                    // 응답코드        [ 3] S R : 응답코드(공통부에러시만사용)
							, document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent()                    // 전문전송일      [ 8] S R : YYYYMMDD
							, document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent()                    // 전문전송시간    [ 6] S R : HHMMSS
							, document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent()                    // 은행 전문번호   [ 8] S   : 전문일련번호
							, document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent()                    // 보험사 전문번호 [ 8]   R : 전문일련번호
							, document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent()                    // 거래발생일      [ 8] S R : YYYYMMDD
							, document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent()                    // 전체 전문 수    [ 2] S   : 전체전문:01
							, document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent()                    // 현재전문순번    [ 2] S   : 현재전문:01
							, document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent()                    // 처리자/단말번호 [15] S   : 처리자 사번/ 단말번호 (사번10byte,단말번호5byte)
							, document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent()                    // 은행 추가정의   [30] S R : FILLER
							, document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent()                    // 보험사 추가정의 [30] S R : FILLER
							);
					
					byte[] byteHeaderStream = this.baos.toByteArray();
					
					if (flag) {
						System.out.println("HEADER [" + new String(byteHeaderStream) + "]");
						System.out.println("BODY [" + new String(byteBodyXml) + "]");
					}

					this.strStreamXml = "";

					this.baos.close();
				}
				
				if (!flag) {
					
					this.strBodyXml =  transNodeToXml(tagBancassurance, true, "");

					this.strStreamXml = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {    // DATE.2013.06.19 : NOT EXECUTE
			try {
				// store SoapXml -> document
				this.strSoapXml = strSoapXml;
				if (!flag) System.out.println("1.[" + this.strSoapXml + "]");
				
				this.bais = new ByteArrayInputStream(this.strSoapXml.getBytes("UTF-8"));
				this.document = this.builder.parse(this.bais);
				
				String tagHDR_TRX_ID     = document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent();  // TRX ID          [ 9] S   : 은행사용
				String tagHDR_SYS_ID     = document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent();  // SYSTEM ID       [ 3] S   : BAS
				//String tagHDR_DOC_LEN    = document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent();  // 전체전문길이    [ 5] S R : 전체길이 : 00000
				String tagHDR_DAT_GBN    = document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent();  // 자료구분        [ 1] S   : T:Test, R:real
				String tagHDR_INS_ID     = document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent();  // 보험사 ID       [ 3] S R : 보험사코드(공통코드1033참조)
				String tagHDR_BAK_CLS    = document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent();  // 은행 구분       [ 2] S   : 은행증권구분(공통코드1022참조)
				String tagHDR_BAK_ID     = document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent();  // 은행 ID         [ 3] S R : 은행코드(공통코드1024참조)
				String tagHDR_DOC_CODE   = document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent();  // 전문종별 코드   [ 4] S R : 전문종별코드(전문LIST참조)
				String tagHDR_BIZ_CODE   = document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent();  // 거래구분 코드   [ 6] S   : 거래구분코드(전문LIST참조)
				String tagHDR_TRA_FLAG   = document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent();  // 송수신 FLAG     [ 1] S R : 은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)
				String tagHDR_DOC_STATUS = document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent();  // STATUS          [ 3] S R : 에라-컬럼ID(공통부항목만)
				String tagHDR_RET_CODE   = document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent();  // 응답코드        [ 3] S R : 응답코드(공통부에러시만사용)
				String tagHDR_SND_DATE   = document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent();  // 전문전송일      [ 8] S R : YYYYMMDD
				String tagHDR_SND_TIME   = document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent();  // 전문전송시간    [ 6] S R : HHMMSS
				String tagHDR_BAK_DOCSEQ = document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent();  // 은행 전문번호   [ 8] S   : 전문일련번호
				String tagHDR_INS_DOCSEQ = document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent();  // 보험사 전문번호 [ 8]   R : 전문일련번호
				String tagHDR_TXN_DATE   = document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent();  // 거래발생일      [ 8] S R : YYYYMMDD
				String tagHDR_TOT_DOC    = document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent();  // 전체 전문 수    [ 2] S   : 전체전문:01
				String tagHDR_CUR_DOC    = document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent();  // 현재전문순번    [ 2] S   : 현재전문:01
				String tagHDR_AGT_CODE   = document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent();  // 처리자/단말번호 [15] S   : 처리자 사번/ 단말번호 (사번10byte,단말번호5byte)
				String tagHDR_BAK_EXT    = document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent();  // 은행 추가정의   [30] S R : FILLER
				String tagHDR_INS_EXT    = document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent();  // 보험사 추가정의 [30] S R : FILLER

				// to get Root Node
				Node rootNode = this.document.getDocumentElement();
				if (!flag) System.out.println("2.[" + rootNode.getTextContent() + "]");
				
				// to get Header Node
				Node header = this.document.getElementsByTagName("HeaderArea").item(0);
				this.docHeader = header.getOwnerDocument();
				this.strHeaderXml =  transNodeToXml(header, true, "");
				if (!flag) System.out.println("3.[" + header.getTextContent() + "]");
				if (!flag) System.out.println("3.[" + this.strHeaderXml + "]");
				
				// to remove header node from document node : Body Node
				rootNode.removeChild(header);
				this.docBody = rootNode.getOwnerDocument();
				this.strBodyXml =  transNodeToXml(this.docBody, true, "");
				if (!flag) System.out.println("4.[" + rootNode.getTextContent() + "]");
				if (!flag) System.out.println("4.[" + this.strBodyXml + "]");
				
				int iBodySize = this.strBodyXml.getBytes().length;
				
				// transfer header XML to stream XML : Header XML
				baos = new ByteArrayOutputStream();
				ps = new PrintStream(baos);
				
				ps.printf("%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s"
						, tagHDR_TRX_ID
						, tagHDR_SYS_ID
						// , tagHDR_DOC_LEN
						, iBodySize
						, tagHDR_DAT_GBN
						, tagHDR_INS_ID
						, tagHDR_BAK_CLS
						, tagHDR_BAK_ID
						, tagHDR_DOC_CODE
						, tagHDR_BIZ_CODE
						, tagHDR_TRA_FLAG
						//, tagHDR_DOC_STATUS
						, Integer.parseInt(tagHDR_DOC_STATUS)
						, tagHDR_RET_CODE
						, tagHDR_SND_DATE
						, tagHDR_SND_TIME
						, tagHDR_BAK_DOCSEQ
						, tagHDR_INS_DOCSEQ
						, tagHDR_TXN_DATE
						, tagHDR_TOT_DOC
						, tagHDR_CUR_DOC
						, tagHDR_AGT_CODE
						, tagHDR_BAK_EXT   // TODO : DATE.2013.06.15 : 한글이면 Stream 길이가 늘어남..-> 처리가 필요함
						, tagHDR_INS_EXT
						);
				
				byte[] byteHeaderStream = this.baos.toByteArray();
				
				if (!flag) {
					System.out.println("HEADER [" + new String(byteHeaderStream) + "]");
					System.out.println("BODY [" + strBodyXml + "]");
				}

				strStreamXml = new String(byteHeaderStream) + strBodyXml;
				if (!flag) System.out.println("[" + strStreamXml + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				this.strSoapXml = strSoapXml;

				this.strXmlCharset = getXmlCharset(this.strSoapXml);
				
				// input String strSoapXml
				ByteArrayInputStream bais = new ByteArrayInputStream(strSoapXml.getBytes(this.strXmlCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				if (!flag) {
					System.out.println("SoapXml [" + transNodeToXml(document, true, this.strXmlCharset) + "]");
					System.out.println("SoapXml [" + transNodeToXml(document, false, this.strXmlCharset) + "]");
				}
				
				// Header 값을 구한다.
				String txtHDR_TRX_ID     = document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent();   // TRX ID          [ 9] S   : 은행사용
				String txtHDR_SYS_ID     = document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent();   // SYSTEM ID       [ 3] S   : BAS
				@SuppressWarnings("unused")
				String txtHDR_DOC_LEN    = document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent();   // 전체전문길이    [ 5] S R : 전체길이 : 00000
				String txtHDR_DAT_GBN    = document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent();   // 자료구분        [ 1] S   : T:Test, R:real
				String txtHDR_INS_ID     = document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent();   // 보험사 ID       [ 3] S R : 보험사코드(공통코드1033참조)
				String txtHDR_BAK_CLS    = document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent();   // 은행 구분       [ 2] S   : 은행증권구분(공통코드1022참조)
				String txtHDR_BAK_ID     = document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent();   // 은행 ID         [ 3] S R : 은행코드(공통코드1024참조)
				String txtHDR_DOC_CODE   = document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent();   // 전문종별 코드   [ 4] S R : 전문종별코드(전문LIST참조)
				String txtHDR_BIZ_CODE   = document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent();   // 거래구분 코드   [ 6] S   : 거래구분코드(전문LIST참조)
				String txtHDR_TRA_FLAG   = document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent();   // 송수신 FLAG     [ 1] S R : 은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)
				String txtHDR_DOC_STATUS = document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent();   // STATUS          [ 3] S R : 에라-컬럼ID(공통부항목만)
				String txtHDR_RET_CODE   = document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent();   // 응답코드        [ 3] S R : 응답코드(공통부에러시만사용)
				String txtHDR_SND_DATE   = document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent();   // 전문전송일      [ 8] S R : YYYYMMDD
				String txtHDR_SND_TIME   = document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent();   // 전문전송시간    [ 6] S R : HHMMSS
				String txtHDR_BAK_DOCSEQ = document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent();   // 은행 전문번호   [ 8] S   : 전문일련번호
				String txtHDR_INS_DOCSEQ = document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent();   // 보험사 전문번호 [ 8]   R : 전문일련번호
				String txtHDR_TXN_DATE   = document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent();   // 거래발생일      [ 8] S R : YYYYMMDD
				String txtHDR_TOT_DOC    = document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent();   // 전체 전문 수    [ 2] S   : 전체전문:01
				String txtHDR_CUR_DOC    = document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent();   // 현재전문순번    [ 2] S   : 현재전문:01
				String txtHDR_AGT_CODE   = document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent();   // 처리자/단말번호 [15] S   : 처리자 사번/ 단말번호 (사번10byte,단말번호5byte)
				String txtHDR_BAK_EXT    = document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent();   // 은행 추가정의   [30] S R : FILLER
				String txtHDR_INS_EXT    = document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent();   // 보험사 추가정의 [30] S R : FILLER

				// document 에서 header 를 제거한다.
				Element tagBancassurance = document.getDocumentElement();
				tagBancassurance.removeChild(document.getElementsByTagName("HeaderArea").item(0));
				
				// header 를 제거한 XML
				//String strBodyXml = transNodeToXml(tagBancassurance, false);
				Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
				if (tagBusinessArea == null) {
					strBodyXml = "";
				} else {
					strBodyXml = transNodeToXml(tagBancassurance, true, this.strXmlCharset);
				}
				
				if (!flag) {
					System.out.println("SoapXml [" + strBodyXml + "]");
				}
				
				// BodyXml 의 길이를 구한다.
				int iBodyLen = strBodyXml.getBytes().length;
				
				// HeaderStream 을 만든다.
				String strHeaderStream = null;
				String strHeaderFormat = "%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s" +
						"%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s";
				
				strHeaderStream = String.format(strHeaderFormat
						, txtHDR_TRX_ID
						, txtHDR_SYS_ID
						// , txtHDR_DOC_LEN
						, 160 + iBodyLen    // 전체길이 : header + body
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
				
				if (!flag) {
					System.out.println("strHeaderStream [" + strHeaderStream + "]");
					System.out.println("strHeaderStream len [" + strHeaderStream.getBytes().length + "]");
					System.out.println("iBodyLen [" + iBodyLen + "]");
				}
				
				// StreamXml 을 만든다. HeaderStream + BodyXml
				strStreamXml = strHeaderStream + strBodyXml;
				
				if (!flag) {
					System.out.println("strStreamXml [" + strStreamXml + "]");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * StreamXml -> SoapXml
	 */
	public void transferStreamXmlToSoapXml(String strStreamXml) {
		boolean flag = true;
		
		if (!flag) {
			try {
				
				this.strStreamXml = strStreamXml;
				if (!flag) System.out.println("1.[" + this.strStreamXml + "]");
				
				byte[] byteStreamXml = this.strStreamXml.getBytes();
				String header = new String(byteStreamXml, 0, 160);
				String body = new String(byteStreamXml, 160, byteStreamXml.length - 160);
				if (!flag) System.out.println("2.[" + header + "]");
				if (!flag) System.out.println("2.[" + body + "]");
				
				String strHDR_TRX_ID     = new String(byteStreamXml,   0,  9); // [ 9]
				String strHDR_SYS_ID     = new String(byteStreamXml,   9,  3); // [ 3]
				String strHDR_DOC_LEN    = new String(byteStreamXml,  12,  5); // [ 5]
				String strHDR_DAT_GBN    = new String(byteStreamXml,  17,  1); // [ 1]
				String strHDR_INS_ID     = new String(byteStreamXml,  18,  3); // [ 3]
				String strHDR_BAK_CLS    = new String(byteStreamXml,  21,  2); // [ 2]
				String strHDR_BAK_ID     = new String(byteStreamXml,  23,  3); // [ 3]
				String strHDR_DOC_CODE   = new String(byteStreamXml,  26,  4); // [ 4]
				String strHDR_BIZ_CODE   = new String(byteStreamXml,  30,  6); // [ 6]
				String strHDR_TRA_FLAG   = new String(byteStreamXml,  36,  1); // [ 1]
				String strHDR_DOC_STATUS = new String(byteStreamXml,  37,  3); // [ 3]
				String strHDR_RET_CODE   = new String(byteStreamXml,  40,  3); // [ 3]
				String strHDR_SND_DATE   = new String(byteStreamXml,  43,  8); // [ 8]
				String strHDR_SND_TIME   = new String(byteStreamXml,  51,  6); // [ 6]
				String strHDR_BAK_DOCSEQ = new String(byteStreamXml,  57,  8); // [ 8]
				String strHDR_INS_DOCSEQ = new String(byteStreamXml,  65,  8); // [ 8]
				String strHDR_TXN_DATE   = new String(byteStreamXml,  73,  8); // [ 8]
				String strHDR_TOT_DOC    = new String(byteStreamXml,  81,  2); // [ 2]
				String strHDR_CUR_DOC    = new String(byteStreamXml,  83,  2); // [ 2]
				String strHDR_AGT_CODE   = new String(byteStreamXml,  85, 15); // [15]
				String strHDR_BAK_EXT    = new String(byteStreamXml, 100, 30); // [30]
				String strHDR_INS_EXT    = new String(byteStreamXml, 130, 30); // [30]

				document = builder.newDocument();
				
				Element tagHeaderArea = document.createElement("HeaderArea");

				Element tagHDR_TRX_ID     = document.createElement("HDR_TRX_ID"    );
				Element tagHDR_SYS_ID     = document.createElement("HDR_SYS_ID"    );
				Element tagHDR_DOC_LEN    = document.createElement("HDR_DOC_LEN"   );
				Element tagHDR_DAT_GBN    = document.createElement("HDR_DAT_GBN"   );
				Element tagHDR_INS_ID     = document.createElement("HDR_INS_ID"    );
				Element tagHDR_BAK_CLS    = document.createElement("HDR_BAK_CLS"   );
				Element tagHDR_BAK_ID     = document.createElement("HDR_BAK_ID"    );
				Element tagHDR_DOC_CODE   = document.createElement("HDR_DOC_CODE"  );
				Element tagHDR_BIZ_CODE   = document.createElement("HDR_BIZ_CODE"  );
				Element tagHDR_TRA_FLAG   = document.createElement("HDR_TRA_FLAG"  );
				Element tagHDR_DOC_STATUS = document.createElement("HDR_DOC_STATUS");
				Element tagHDR_RET_CODE   = document.createElement("HDR_RET_CODE"  );
				Element tagHDR_SND_DATE   = document.createElement("HDR_SND_DATE"  );
				Element tagHDR_SND_TIME   = document.createElement("HDR_SND_TIME"  );
				Element tagHDR_BAK_DOCSEQ = document.createElement("HDR_BAK_DOCSEQ");
				Element tagHDR_INS_DOCSEQ = document.createElement("HDR_INS_DOCSEQ");
				Element tagHDR_TXN_DATE   = document.createElement("HDR_TXN_DATE"  );
				Element tagHDR_TOT_DOC    = document.createElement("HDR_TOT_DOC"   );
				Element tagHDR_CUR_DOC    = document.createElement("HDR_CUR_DOC"   );
				Element tagHDR_AGT_CODE   = document.createElement("HDR_AGT_CODE"  );
				Element tagHDR_BAK_EXT    = document.createElement("HDR_BAK_EXT"   );
				Element tagHDR_INS_EXT    = document.createElement("HDR_INS_EXT"   );

				Text textHDR_TRX_ID     = document.createTextNode(strHDR_TRX_ID    );
				Text textHDR_SYS_ID     = document.createTextNode(strHDR_SYS_ID    );
				Text textHDR_DOC_LEN    = document.createTextNode(strHDR_DOC_LEN   );
				Text textHDR_DAT_GBN    = document.createTextNode(strHDR_DAT_GBN   );
				Text textHDR_INS_ID     = document.createTextNode(strHDR_INS_ID    );
				Text textHDR_BAK_CLS    = document.createTextNode(strHDR_BAK_CLS   );
				Text textHDR_BAK_ID     = document.createTextNode(strHDR_BAK_ID    );
				Text textHDR_DOC_CODE   = document.createTextNode(strHDR_DOC_CODE  );
				Text textHDR_BIZ_CODE   = document.createTextNode(strHDR_BIZ_CODE  );
				Text textHDR_TRA_FLAG   = document.createTextNode(strHDR_TRA_FLAG  );
				Text textHDR_DOC_STATUS = document.createTextNode(strHDR_DOC_STATUS);
				Text textHDR_RET_CODE   = document.createTextNode(strHDR_RET_CODE  );
				Text textHDR_SND_DATE   = document.createTextNode(strHDR_SND_DATE  );
				Text textHDR_SND_TIME   = document.createTextNode(strHDR_SND_TIME  );
				Text textHDR_BAK_DOCSEQ = document.createTextNode(strHDR_BAK_DOCSEQ);
				Text textHDR_INS_DOCSEQ = document.createTextNode(strHDR_INS_DOCSEQ);
				Text textHDR_TXN_DATE   = document.createTextNode(strHDR_TXN_DATE  );
				Text textHDR_TOT_DOC    = document.createTextNode(strHDR_TOT_DOC   );
				Text textHDR_CUR_DOC    = document.createTextNode(strHDR_CUR_DOC   );
				Text textHDR_AGT_CODE   = document.createTextNode(strHDR_AGT_CODE  );
				Text textHDR_BAK_EXT    = document.createTextNode(strHDR_BAK_EXT   );
				Text textHDR_INS_EXT    = document.createTextNode(strHDR_INS_EXT   );

				tagHDR_TRX_ID    .appendChild(textHDR_TRX_ID    );
				tagHDR_SYS_ID    .appendChild(textHDR_SYS_ID    );
				tagHDR_DOC_LEN   .appendChild(textHDR_DOC_LEN   );
				tagHDR_DAT_GBN   .appendChild(textHDR_DAT_GBN   );
				tagHDR_INS_ID    .appendChild(textHDR_INS_ID    );
				tagHDR_BAK_CLS   .appendChild(textHDR_BAK_CLS   );
				tagHDR_BAK_ID    .appendChild(textHDR_BAK_ID    );
				tagHDR_DOC_CODE  .appendChild(textHDR_DOC_CODE  );
				tagHDR_BIZ_CODE  .appendChild(textHDR_BIZ_CODE  );
				tagHDR_TRA_FLAG  .appendChild(textHDR_TRA_FLAG  );
				tagHDR_DOC_STATUS.appendChild(textHDR_DOC_STATUS);
				tagHDR_RET_CODE  .appendChild(textHDR_RET_CODE  );
				tagHDR_SND_DATE  .appendChild(textHDR_SND_DATE  );
				tagHDR_SND_TIME  .appendChild(textHDR_SND_TIME  );
				tagHDR_BAK_DOCSEQ.appendChild(textHDR_BAK_DOCSEQ);
				tagHDR_INS_DOCSEQ.appendChild(textHDR_INS_DOCSEQ);
				tagHDR_TXN_DATE  .appendChild(textHDR_TXN_DATE  );
				tagHDR_TOT_DOC   .appendChild(textHDR_TOT_DOC   );
				tagHDR_CUR_DOC   .appendChild(textHDR_CUR_DOC   );
				tagHDR_AGT_CODE  .appendChild(textHDR_AGT_CODE  );
				tagHDR_BAK_EXT   .appendChild(textHDR_BAK_EXT   );
				tagHDR_INS_EXT   .appendChild(textHDR_INS_EXT   );

				tagHeaderArea.appendChild(tagHDR_TRX_ID    );
				tagHeaderArea.appendChild(tagHDR_SYS_ID    );
				tagHeaderArea.appendChild(tagHDR_DOC_LEN   );
				tagHeaderArea.appendChild(tagHDR_DAT_GBN   );
				tagHeaderArea.appendChild(tagHDR_INS_ID    );
				tagHeaderArea.appendChild(tagHDR_BAK_CLS   );
				tagHeaderArea.appendChild(tagHDR_BAK_ID    );
				tagHeaderArea.appendChild(tagHDR_DOC_CODE  );
				tagHeaderArea.appendChild(tagHDR_BIZ_CODE  );
				tagHeaderArea.appendChild(tagHDR_TRA_FLAG  );
				tagHeaderArea.appendChild(tagHDR_DOC_STATUS);
				tagHeaderArea.appendChild(tagHDR_RET_CODE  );
				tagHeaderArea.appendChild(tagHDR_SND_DATE  );
				tagHeaderArea.appendChild(tagHDR_SND_TIME  );
				tagHeaderArea.appendChild(tagHDR_BAK_DOCSEQ);
				tagHeaderArea.appendChild(tagHDR_INS_DOCSEQ);
				tagHeaderArea.appendChild(tagHDR_TXN_DATE  );
				tagHeaderArea.appendChild(tagHDR_TOT_DOC   );
				tagHeaderArea.appendChild(tagHDR_CUR_DOC   );
				tagHeaderArea.appendChild(tagHDR_AGT_CODE  );
				tagHeaderArea.appendChild(tagHDR_BAK_EXT   );
				tagHeaderArea.appendChild(tagHDR_INS_EXT   );
				
				//tagBancassurance.appendChild(tagHeaderArea);
				//document.appendChild(tagBancassurance);
				if (!flag) System.out.println("3[" + transNodeToXml(tagHeaderArea, false, "") + "]");
				
				StringBuffer sb;
				
				if (!flag)
					sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				else
					sb = new StringBuffer("");

				// DATE.2013.06.12 : 개시전문은 body 부분이 없다. 그에 대한 처리
				if (body == null || "".equals(body)) {
					sb.append("<Bancassurance>");
					sb.append(transNodeToXml(tagHeaderArea, false, ""));
					sb.append("</Bancassurance>");
				} else {
					// String body = new String(byteStreamXml, 160, byteStreamXml.length - 160);
					bais = new ByteArrayInputStream(body.getBytes("UTF-8"));
					docBody = builder.parse(bais);
					
					Node tagBusinessArea = docBody.getElementsByTagName("BusinessArea").item(0);
					if (!flag) System.out.println("4[" + transNodeToXml(tagBusinessArea, false, "") + "]");

					if (flag)
						sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					else
						sb = new StringBuffer("");

					sb.append("<Bancassurance>");
					sb.append(transNodeToXml(tagHeaderArea, false, ""));
					sb.append(transNodeToXml(tagBusinessArea, false, ""));
					sb.append("</Bancassurance>");
				}
				
				this.strSoapXml = sb.toString();
				
				if (!flag) System.out.println("5[" + strSoapXml + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				this.strStreamXml = strStreamXml;

				this.strXmlCharset = getXmlCharset(this.strStreamXml);
				
				// input String strStreamXml
				byte[] byteStreamXml = strStreamXml.getBytes();
				int iStreamXml = byteStreamXml.length;
				
				// StreamXml 을 header 부분과 body 부분으로 분리한다.
				//String strHeaderStream = new String(byteStreamXml, 0, 160, this.strXmlCharset);
				//String strBodyXml = new String(byteStreamXml, 160, iStreamXml - 160, this.strXmlCharset);
				String strHeaderStream = new String(byteStreamXml, 0, 160);
				String strBodyXml = new String(byteStreamXml, 160, iStreamXml - 160);
				
				if (!flag) {
					System.out.println("strHeaderStream [" + strHeaderStream + "]");
					System.out.println("strBodyXml [" + strBodyXml + "]");
				}
				
				// header 부분을 headerXml 로 만든다.
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
				sbHeaderXml.append("<HeaderArea>");
				sbHeaderXml.append("<HDR_TRX_ID>"    + strHDR_TRX_ID     + "</HDR_TRX_ID>"    );
				sbHeaderXml.append("<HDR_SYS_ID>"    + strHDR_SYS_ID     + "</HDR_SYS_ID>"    );
				sbHeaderXml.append("<HDR_DOC_LEN>"   + strHDR_DOC_LEN    + "</HDR_DOC_LEN>"   );
				sbHeaderXml.append("<HDR_DAT_GBN>"   + strHDR_DAT_GBN    + "</HDR_DAT_GBN>"   );
				sbHeaderXml.append("<HDR_INS_ID>"    + strHDR_INS_ID     + "</HDR_INS_ID>"    );
				sbHeaderXml.append("<HDR_BAK_CLS>"   + strHDR_BAK_CLS    + "</HDR_BAK_CLS>"   );
				sbHeaderXml.append("<HDR_BAK_ID>"    + strHDR_BAK_ID     + "</HDR_BAK_ID>"    );
				sbHeaderXml.append("<HDR_DOC_CODE>"  + strHDR_DOC_CODE   + "</HDR_DOC_CODE>"  );
				sbHeaderXml.append("<HDR_BIZ_CODE>"  + strHDR_BIZ_CODE   + "</HDR_BIZ_CODE>"  );
				sbHeaderXml.append("<HDR_TRA_FLAG>"  + strHDR_TRA_FLAG   + "</HDR_TRA_FLAG>"  );
				sbHeaderXml.append("<HDR_DOC_STATUS>"+ strHDR_DOC_STATUS + "</HDR_DOC_STATUS>");
				sbHeaderXml.append("<HDR_RET_CODE>"  + strHDR_RET_CODE   + "</HDR_RET_CODE>"  );
				sbHeaderXml.append("<HDR_SND_DATE>"  + strHDR_SND_DATE   + "</HDR_SND_DATE>"  );
				sbHeaderXml.append("<HDR_SND_TIME>"  + strHDR_SND_TIME   + "</HDR_SND_TIME>"  );
				sbHeaderXml.append("<HDR_BAK_DOCSEQ>"+ strHDR_BAK_DOCSEQ + "</HDR_BAK_DOCSEQ>");
				sbHeaderXml.append("<HDR_INS_DOCSEQ>"+ strHDR_INS_DOCSEQ + "</HDR_INS_DOCSEQ>");
				sbHeaderXml.append("<HDR_TXN_DATE>"  + strHDR_TXN_DATE   + "</HDR_TXN_DATE>"  );
				sbHeaderXml.append("<HDR_TOT_DOC>"   + strHDR_TOT_DOC    + "</HDR_TOT_DOC>"   );
				sbHeaderXml.append("<HDR_CUR_DOC>"   + strHDR_CUR_DOC    + "</HDR_CUR_DOC>"   );
				sbHeaderXml.append("<HDR_AGT_CODE>"  + strHDR_AGT_CODE   + "</HDR_AGT_CODE>"  );
				sbHeaderXml.append("<HDR_BAK_EXT>"   + strHDR_BAK_EXT    + "</HDR_BAK_EXT>"   );
				sbHeaderXml.append("<HDR_INS_EXT>"   + strHDR_INS_EXT    + "</HDR_INS_EXT>"   );
				sbHeaderXml.append("</HeaderArea>");
				
				String strHeaderXml = sbHeaderXml.toString();
				
				if (!flag) {
					System.out.println("strHeaderXml [" + strHeaderXml + "]");
				}
				
				// body 부분을 bodyXml 로 만든다.
				// DATE.2013.06.12 : body 부분이 없는 개시전문은 BusinessArea 테그가 없다.
				if (strBodyXml == null || "".equals(strBodyXml)) {
					strBodyXml = "";
				} else {
					// body 부분을 bodyXml 로 만든다.
					ByteArrayInputStream bais = new ByteArrayInputStream(strBodyXml.getBytes(this.strXmlCharset));
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.parse(bais);
					bais.close();
					
					// document 에서 body 를 얻는다.
					Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
					strBodyXml = transNodeToXml(tagBusinessArea, false, this.strXmlCharset);
					
					if (!flag) {
						System.out.println("strBodyXml [" + strBodyXml + "]");
					}
				}

				// SoapXml 을 만든다. <Bancassurance> + headerXml + bodyXml + </Bancassurance>
				StringBuffer sbSoapXml = new StringBuffer();
				if (flag) sbSoapXml.append("<?xml version=\"1.0\" encoding=\"" + this.strXmlCharset +"\"?>");
				sbSoapXml.append("<Bancassurance>");
				sbSoapXml.append(strHeaderXml);
				sbSoapXml.append(strBodyXml);
				sbSoapXml.append("</Bancassurance>");

				strSoapXml = sbSoapXml.toString();
				
				if (!flag) {
					System.out.println("strSoapXml [" + strSoapXml + "]");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * HDR_BAK_DOCSEQ 테그에 SeqGender 값 세팅
	 * @param strSourceXml
	 * @return
	 */
	public static String setHdrBakDocseq(String strSourceXml) 
	{
		boolean flag = true;
		
		String strTargetXml = null;
		
		if (flag) {
			try {
				String strXmlCharset = getXmlCharset(strSourceXml);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(strSourceXml.getBytes(strXmlCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				String strKey = String.format("%08d", SeqGender.getInstance().getInt());
				
				Node nodeHDR_BAK_DOCSEQ = document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0);
				nodeHDR_BAK_DOCSEQ.setTextContent(strKey);
				
				strTargetXml = transNodeToXml(document, false, strXmlCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strTargetXml;
	}

	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	public String getStreamXml() {
		return this.strStreamXml;
	}
	
	public String getSoapXml() {
		return this.strSoapXml;
	}

	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	public void test02() {
		boolean flag = true;
		
		if (flag) {
			try {
				String strSoapXml = Sample01Xml.getReqXml();
				
				// input String strSoapXml
				ByteArrayInputStream bais = new ByteArrayInputStream(strSoapXml.getBytes("UTF-8"));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				if (!flag) {
					System.out.println("SoapXml [" + transNodeToXml(document, true, "") + "]");
					System.out.println("SoapXml [" + transNodeToXml(document, false, "") + "]");
				}
				
				// Header 값을 구한다.
				String txtHDR_TRX_ID     = document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent();   // TRX ID          [ 9] S   : 은행사용
				String txtHDR_SYS_ID     = document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent();   // SYSTEM ID       [ 3] S   : BAS
				@SuppressWarnings("unused")
				String txtHDR_DOC_LEN    = document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent();   // 전체전문길이    [ 5] S R : 전체길이 : 00000
				String txtHDR_DAT_GBN    = document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent();   // 자료구분        [ 1] S   : T:Test, R:real
				String txtHDR_INS_ID     = document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent();   // 보험사 ID       [ 3] S R : 보험사코드(공통코드1033참조)
				String txtHDR_BAK_CLS    = document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent();   // 은행 구분       [ 2] S   : 은행증권구분(공통코드1022참조)
				String txtHDR_BAK_ID     = document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent();   // 은행 ID         [ 3] S R : 은행코드(공통코드1024참조)
				String txtHDR_DOC_CODE   = document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent();   // 전문종별 코드   [ 4] S R : 전문종별코드(전문LIST참조)
				String txtHDR_BIZ_CODE   = document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent();   // 거래구분 코드   [ 6] S   : 거래구분코드(전문LIST참조)
				String txtHDR_TRA_FLAG   = document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent();   // 송수신 FLAG     [ 1] S R : 은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)
				String txtHDR_DOC_STATUS = document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent();   // STATUS          [ 3] S R : 에라-컬럼ID(공통부항목만)
				String txtHDR_RET_CODE   = document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent();   // 응답코드        [ 3] S R : 응답코드(공통부에러시만사용)
				String txtHDR_SND_DATE   = document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent();   // 전문전송일      [ 8] S R : YYYYMMDD
				String txtHDR_SND_TIME   = document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent();   // 전문전송시간    [ 6] S R : HHMMSS
				String txtHDR_BAK_DOCSEQ = document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent();   // 은행 전문번호   [ 8] S   : 전문일련번호
				String txtHDR_INS_DOCSEQ = document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent();   // 보험사 전문번호 [ 8]   R : 전문일련번호
				String txtHDR_TXN_DATE   = document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent();   // 거래발생일      [ 8] S R : YYYYMMDD
				String txtHDR_TOT_DOC    = document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent();   // 전체 전문 수    [ 2] S   : 전체전문:01
				String txtHDR_CUR_DOC    = document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent();   // 현재전문순번    [ 2] S   : 현재전문:01
				String txtHDR_AGT_CODE   = document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent();   // 처리자/단말번호 [15] S   : 처리자 사번/ 단말번호 (사번10byte,단말번호5byte)
				String txtHDR_BAK_EXT    = document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent();   // 은행 추가정의   [30] S R : FILLER
				String txtHDR_INS_EXT    = document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent();   // 보험사 추가정의 [30] S R : FILLER

				// document 에서 header 를 제거한다.
				Element tagBancassurance = document.getDocumentElement();
				tagBancassurance.removeChild(document.getElementsByTagName("HeaderArea").item(0));
				
				// header 를 제거한 XML
				//String strBodyXml = transNodeToXml(tagBancassurance, false);
				String strBodyXml = transNodeToXml(tagBancassurance, true, "");
				
				if (!flag) {
					System.out.println("SoapXml [" + strBodyXml + "]");
				}
				
				// BodyXml 의 길이를 구한다.
				int iBodyLen = strBodyXml.getBytes().length;
				
				// HeaderStream 을 만든다.
				String strHeaderStream = null;
				String strHeaderFormat = "%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s" +
						"%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s";
				
				strHeaderStream = String.format(strHeaderFormat
						, txtHDR_TRX_ID
						, txtHDR_SYS_ID
						// , txtHDR_DOC_LEN
						, iBodyLen
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
				
				if (flag) {
					System.out.println("strHeaderStream [" + strHeaderStream + "]");
					System.out.println("strHeaderStream len [" + strHeaderStream.getBytes().length + "]");
					System.out.println("iBodyLen [" + iBodyLen + "]");
				}
				
				// StreamXml 을 만든다. HeaderStream + BodyXml
				String strStreamXml = strHeaderStream + strBodyXml;
				
				if (flag) {
					System.out.println("strStreamXml [" + strStreamXml + "]");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void test03() {
		boolean flag = true;
		
		if (flag) {
			try {
				String strStreamXml = Sample01Xml.getStreamXml();

				// input String strStreamXml
				byte[] byteStreamXml = strStreamXml.getBytes();
				int iStreamXml = byteStreamXml.length;
				
				// StreamXml 을 header 부분과 body 부분으로 분리한다.
				String strHeaderStream = new String(byteStreamXml, 0, 160);
				String strBodyXml = new String(byteStreamXml, 160, iStreamXml - 160);
				
				if (!flag) {
					System.out.println("strHeaderStream [" + strHeaderStream + "]");
					System.out.println("strBodyXml [" + strBodyXml + "]");
				}
				
				// header 부분을 headerXml 로 만든다.
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
				sbHeaderXml.append("<HeaderArea>");
				sbHeaderXml.append("<HDR_TRX_ID>"    + strHDR_TRX_ID     + "</HDR_TRX_ID>"    );
				sbHeaderXml.append("<HDR_SYS_ID>"    + strHDR_SYS_ID     + "</HDR_SYS_ID>"    );
				sbHeaderXml.append("<HDR_DOC_LEN>"   + strHDR_DOC_LEN    + "</HDR_DOC_LEN>"   );
				sbHeaderXml.append("<HDR_DAT_GBN>"   + strHDR_DAT_GBN    + "</HDR_DAT_GBN>"   );
				sbHeaderXml.append("<HDR_INS_ID>"    + strHDR_INS_ID     + "</HDR_INS_ID>"    );
				sbHeaderXml.append("<HDR_BAK_CLS>"   + strHDR_BAK_CLS    + "</HDR_BAK_CLS>"   );
				sbHeaderXml.append("<HDR_BAK_ID>"    + strHDR_BAK_ID     + "</HDR_BAK_ID>"    );
				sbHeaderXml.append("<HDR_DOC_CODE>"  + strHDR_DOC_CODE   + "</HDR_DOC_CODE>"  );
				sbHeaderXml.append("<HDR_BIZ_CODE>"  + strHDR_BIZ_CODE   + "</HDR_BIZ_CODE>"  );
				sbHeaderXml.append("<HDR_TRA_FLAG>"  + strHDR_TRA_FLAG   + "</HDR_TRA_FLAG>"  );
				sbHeaderXml.append("<HDR_DOC_STATUS>"+ strHDR_DOC_STATUS + "</HDR_DOC_STATUS>");
				sbHeaderXml.append("<HDR_RET_CODE>"  + strHDR_RET_CODE   + "</HDR_RET_CODE>"  );
				sbHeaderXml.append("<HDR_SND_DATE>"  + strHDR_SND_DATE   + "</HDR_SND_DATE>"  );
				sbHeaderXml.append("<HDR_SND_TIME>"  + strHDR_SND_TIME   + "</HDR_SND_TIME>"  );
				sbHeaderXml.append("<HDR_BAK_DOCSEQ>"+ strHDR_BAK_DOCSEQ + "</HDR_BAK_DOCSEQ>");
				sbHeaderXml.append("<HDR_INS_DOCSEQ>"+ strHDR_INS_DOCSEQ + "</HDR_INS_DOCSEQ>");
				sbHeaderXml.append("<HDR_TXN_DATE>"  + strHDR_TXN_DATE   + "</HDR_TXN_DATE>"  );
				sbHeaderXml.append("<HDR_TOT_DOC>"   + strHDR_TOT_DOC    + "</HDR_TOT_DOC>"   );
				sbHeaderXml.append("<HDR_CUR_DOC>"   + strHDR_CUR_DOC    + "</HDR_CUR_DOC>"   );
				sbHeaderXml.append("<HDR_AGT_CODE>"  + strHDR_AGT_CODE   + "</HDR_AGT_CODE>"  );
				sbHeaderXml.append("<HDR_BAK_EXT>"   + strHDR_BAK_EXT    + "</HDR_BAK_EXT>"   );
				sbHeaderXml.append("<HDR_INS_EXT>"   + strHDR_INS_EXT    + "</HDR_INS_EXT>"   );
				sbHeaderXml.append("</HeaderArea>");
				
				String strHeaderXml = sbHeaderXml.toString();
				
				if (flag) {
					System.out.println("strHeaderXml [" + strHeaderXml + "]");
				}
				
				// body 부분을 bodyXml 로 만든다.
				ByteArrayInputStream bais = new ByteArrayInputStream(strBodyXml.getBytes("UTF-8"));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// document 에서 body 를 얻는다.
				Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
				strBodyXml = transNodeToXml(tagBusinessArea, false, "");
				
				if (flag) {
					System.out.println("strBodyXml [" + strBodyXml + "]");
				}

				// SoapXml 을 만든다. <Bancassurance> + headerXml + bodyXml + </Bancassurance>
				StringBuffer sbSoapXml = new StringBuffer();
				if (!flag) sbSoapXml.append("<<?xml version=\"1.0\" encoding=\"UTF-8\"?>>");
				sbSoapXml.append("<Bancassurance>");
				sbSoapXml.append(strHeaderXml);
				sbSoapXml.append(strBodyXml);
				sbSoapXml.append("</Bancassurance>");

				String strSoapXml = sbSoapXml.toString();
				
				if (!flag) {
					System.out.println("strSoapXml [" + strSoapXml + "]");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * Entry Point
	 */
	public static void main(String[] args) {
		
		boolean flag = true;
		
		switch (1) {
		case 1:
			if (flag) {
				XmlObject_20130622 obj = new XmlObject_20130622();
				
				System.out.println("-----------------------------------------------");
				obj.transferSoapXmlToStreamXml(Sample01Xml.getReqXml());
				if (flag) System.out.println("REQ [" + obj.getSoapXml() + "]");
				if (flag) System.out.println("-> REQ [" + obj.getStreamXml() + "]");
				
				System.out.println("-----------------------------------------------");
				obj.transferStreamXmlToSoapXml(Sample01Xml.getStreamXml());
				if (flag) System.out.println("RES [" + obj.getStreamXml() + "]");
				if (flag) System.out.println("-> RES [" + obj.getSoapXml() + "]");

				System.out.println("-----------------------------------------------");
			}
			break;
			
		case 2:
			if (flag) {
				new XmlObject_20130622().test02();
			}
			break;
			
		case 3:
			if (flag) {
				new XmlObject_20130622().test03();
			}
			break;
			
		default:
			break;
		}
	}
}
