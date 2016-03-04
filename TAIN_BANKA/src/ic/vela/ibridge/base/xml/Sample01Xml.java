package ic.vela.ibridge.base.xml;

import ic.vela.ibridge.base.seq.SeqGender;
import ic.vela.ibridge.util.XmlTool;

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
import org.w3c.dom.Node;

/*
 * 
--------------------------------------------------------------------------------
----- ������� ��� : ��������� ����

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

public class Sample01Xml {

	private static String strReqSampleXml = null;
	private static String strResSampleXml = null;
	private static String strStreamXml = null;
	
	private static String strCharset = "EUC-KR";
	
	/////////////////////////////////////////////////////////////////

	public Sample01Xml() {}
	
	/////////////////////////////////////////////////////////////////

	public static String makeTest14SampleXml() {
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.01<?xml version="1.0" encoding="EUC-KR"?>
			sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>                \n");
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>HWI01</HDR_TRX_ID>                         \n");
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");
			sb.append("    <HDR_BAK_ID>209</HDR_BAK_ID>                           \n");
			sb.append("    <HDR_DOC_CODE>0210</HDR_DOC_CODE>                      \n");
			sb.append("    <HDR_BIZ_CODE>076100</HDR_BIZ_CODE>                    \n");
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");
			sb.append("    <HDR_SND_DATE>20130514</HDR_SND_DATE>                  \n");
			sb.append("    <HDR_SND_TIME>161214</HDR_SND_TIME>                    \n");
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");
			sb.append("    <HDR_TXN_DATE>20130514</HDR_TXN_DATE>                  \n");
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");
			sb.append("  </HeaderArea>                                            \n");
			sb.append("  <BusinessArea>                                           \n");
			sb.append("    <B_CONT_NO>155399876</B_CONT_NO>                       \n");
			sb.append("    <TEXT>�ȳ��ϼ���. &lt;br&gt; �ȳ�&lt;br&gt;   </TEXT>  \n");
			sb.append("  </BusinessArea>                                          \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		return sb.toString();
	}
	
	public static String getReqFepidXml(String strFepid, String strInsid) 
	{
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		String strReqXml = null;
		
		if (flag) {   // DATE.2013.06.12  : ��������
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
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");   // �ڷᱸ�� : TEST
			sb.append("    <HDR_INS_ID>" + strInsid + "</HDR_INS_ID>              \n");   // ������ڵ� : �� ��������
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
			sb.append("    <HDR_SND_DATE>20130624</HDR_SND_DATE>                  \n");   // ����������
			sb.append("    <HDR_SND_TIME>213000</HDR_SND_TIME>                    \n");   // �������۽ð�
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");   // ���� ������ȣ
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");   // ����� ������ȣ
			sb.append("    <HDR_TXN_DATE>20130624</HDR_TXN_DATE>                  \n");   // �ŷ��߻���
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");   // ���� �߰�����
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");   // ����� �߰�����
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				/*
				 * ��û������ �����Ѵ�.
				 */
				strReqXml = XmlTool.transNodeToXml(document, true, strCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqXml;
	}

	/////////////////////////////////////////////////////////////////

	public static String getReq09Xml(String strFepid, String strInsid) 
	{
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		String strReqXml = null;
		
		if (flag) {   // DATE.2013.06.12  : ��������
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
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");   // �ڷᱸ�� : TEST
			sb.append("    <HDR_INS_ID>" + strInsid + "</HDR_INS_ID>              \n");   // ������ڵ� : �� ��������
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
			sb.append("    <HDR_SND_DATE>20130624</HDR_SND_DATE>                  \n");   // ����������
			sb.append("    <HDR_SND_TIME>213000</HDR_SND_TIME>                    \n");   // �������۽ð�
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");   // ���� ������ȣ
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");   // ����� ������ȣ
			sb.append("    <HDR_TXN_DATE>20130624</HDR_TXN_DATE>                  \n");   // �ŷ��߻���
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
			sb.append("    <HDR_BAK_EXT> &amp;    </HDR_BAK_EXT>                  \n");   // ���� �߰�����   <----
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");   // ����� �߰�����
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				/*
				 * ��û������ �����Ѵ�.
				 */
				strReqXml = XmlTool.transNodeToXml(document, true, strCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqXml;
	}

	/////////////////////////////////////////////////////////////////

	public static String getReq11Xml() 
	{
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		String strReqXml = null;
		
		if (flag) {   // DATE.2013.06.12  : ��������
			/*
			 * FEPID : ��ܱ�� �ڵ�
			 * 
			 * INSID : ������ڵ�
			 * 
			 */
			sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>     \n");
			sb.append("<Bancassurance><HeaderArea>                     \n");
			sb.append("  <HDR_TRX_ID>SHI01</HDR_TRX_ID>                \n");
			sb.append("  <HDR_SYS_ID>BAS</HDR_SYS_ID>                  \n");
			sb.append("  <HDR_DOC_LEN>3692</HDR_DOC_LEN>               \n");
			sb.append("  <HDR_DAT_GBN>T</HDR_DAT_GBN>                  \n");
			sb.append("  <HDR_INS_ID>L11</HDR_INS_ID>                  \n");
			sb.append("  <HDR_BAK_CLS>02</HDR_BAK_CLS>                 \n");
			sb.append("  <HDR_BAK_ID>269</HDR_BAK_ID>                  \n");
			sb.append("  <HDR_DOC_CODE>0200</HDR_DOC_CODE>             \n");
			sb.append("  <HDR_BIZ_CODE>072100</HDR_BIZ_CODE>           \n");
			sb.append("  <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                \n");
			sb.append("  <HDR_DOC_STATUS>1</HDR_DOC_STATUS>            \n");
			sb.append("  <HDR_RET_CODE>000</HDR_RET_CODE>              \n");
			sb.append("  <HDR_SND_DATE>20130709</HDR_SND_DATE>         \n");
			sb.append("  <HDR_SND_TIME>141515</HDR_SND_TIME>           \n");
			sb.append("  <HDR_BAK_DOCSEQ>00002429</HDR_BAK_DOCSEQ>     \n");
			sb.append("  <HDR_INS_DOCSEQ></HDR_INS_DOCSEQ>             \n");
			sb.append("  <HDR_TXN_DATE>20130709</HDR_TXN_DATE>         \n");
			sb.append("  <HDR_TOT_DOC>01</HDR_TOT_DOC>                 \n");
			sb.append("  <HDR_CUR_DOC>01</HDR_CUR_DOC>                 \n");
			sb.append("  <HDR_AGT_CODE>2002006920002</HDR_AGT_CODE>    \n");
			sb.append("  <HDR_BAK_EXT></HDR_BAK_EXT>                   \n");
			sb.append("  <HDR_INS_EXT></HDR_INS_EXT>                   \n");
			sb.append("</HeaderArea><BusinessArea>                     \n");
			sb.append("  <B_CONT_NO/>                                  \n");
			sb.append("  <PITEM>1003082</PITEM>                        \n");
			sb.append("  <PITEM_NM></PITEM_NM>                         \n");
			sb.append("  <DIAG_YN></DIAG_YN>                           \n");
			sb.append("  <FUND_DIST_GB/>                               \n");
			sb.append("  <FUND_DIST_CYL/>                              \n");
			sb.append("  <FUND_DIST_DAY/>                              \n");
			sb.append("  <FUND_DIST_NEXTYMD/>                          \n");
			sb.append("  <DCA_GB/>                                     \n");
			sb.append("  <DCA_CYL/>                                    \n");
			sb.append("  <DCA_DAY/>                                    \n");
			sb.append("  <ANTY_CL_TP>1</ANTY_CL_TP>                    \n");
			sb.append("  <MULTI_ANTY_INF>                              \n");
			sb.append("    <MULTI_CL_TP>1</MULTI_CL_TP>                \n");
			sb.append("    <ANTY_CL_RAT>100</ANTY_CL_RAT>              \n");
			sb.append("    <ANST_AGE>50</ANST_AGE>                     \n");
			sb.append("    <ANTY_CL_PRD>10</ANTY_CL_PRD>               \n");
			sb.append("    <ANTY_CL_CYL>12</ANTY_CL_CYL>               \n");
			sb.append("    <MIN_GUR_PRD>10</MIN_GUR_PRD>               \n");
			sb.append("    <GINCR_TP>90</GINCR_TP>                     \n");
			sb.append("    <GINCR_PRD>12</GINCR_PRD>                   \n");
			sb.append("    <GINCR_VALUE>0</GINCR_VALUE>                \n");
			sb.append("  </MULTI_ANTY_INF>                             \n");
			sb.append("  <IMRC_GB>3</IMRC_GB>                          \n");
			sb.append("  <TOT_PRM></TOT_PRM>                           \n");
			sb.append("  <RPRM></RPRM>                                 \n");
			sb.append("  <PM_CYL>01</PM_CYL>                           \n");
			sb.append("  <APRM_INF>                                    \n");
			sb.append("    <APRM_NTS></APRM_NTS>                       \n");
			sb.append("    <APRM_PRD></APRM_PRD>                       \n");
			sb.append("    <APRM></APRM>                               \n");
			sb.append("  </APRM_INF>                                   \n");
			sb.append("  <IPRM_NTS></IPRM_NTS>                         \n");
			sb.append("  <IPRM></IPRM>                                 \n");
			sb.append("  <FREE_CONT_AMT></FREE_CONT_AMT>               \n");
			sb.append("  <FREE_PRM></FREE_PRM>                         \n");
			sb.append("  <PM_INF>                                      \n");
			sb.append("    <PM_DESC></PM_DESC>                         \n");
			sb.append("    <PM_AMOUNT></PM_AMOUNT>                     \n");
			sb.append("  </PM_INF>                                     \n");
			sb.append("  <HUNM_INF>                                    \n");
			sb.append("    <CUST_CONT_RTP>11</CUST_CONT_RTP>           \n");
			sb.append("    <CUST_NM>���ο�</CUST_NM>                   \n");
			sb.append("    <SSN>7501032536810</SSN>                    \n");
			sb.append("    <AGE/>                                      \n");
			sb.append("    <ISD_REL>01</ISD_REL>                       \n");
			sb.append("    <JOB_CD></JOB_CD>                           \n");
			sb.append("    <CAR_KND></CAR_KND>                         \n");
			sb.append("    <RSK_GD/>                                   \n");
			sb.append("    <SMOK_YN></SMOK_YN>                         \n");
			sb.append("    <DALY_SMOK_QNTY></DALY_SMOK_QNTY>           \n");
			sb.append("    <SMOK_TERM></SMOK_TERM>                     \n");
			sb.append("    <HGHT></HGHT>                               \n");
			sb.append("    <WGHT></WGHT>                               \n");
			sb.append("  </HUNM_INF>                                   \n");
			sb.append("  <HUNM_INF>                                    \n");
			sb.append("    <CUST_CONT_RTP>21</CUST_CONT_RTP>           \n");
			sb.append("    <CUST_NM>���ο�</CUST_NM>                   \n");
			sb.append("    <SSN>7501032536810</SSN>                    \n");
			sb.append("    <AGE/>                                      \n");
			sb.append("    <ISD_REL>01</ISD_REL>                       \n");
			sb.append("    <JOB_CD>22120</JOB_CD>                      \n");
			sb.append("    <CAR_KND>1</CAR_KND>                        \n");
			sb.append("    <RSK_GD/>                                   \n");
			sb.append("    <SMOK_YN></SMOK_YN>                         \n");
			sb.append("    <DALY_SMOK_QNTY></DALY_SMOK_QNTY>           \n");
			sb.append("    <SMOK_TERM></SMOK_TERM>                     \n");
			sb.append("    <HGHT></HGHT>                               \n");
			sb.append("    <WGHT></WGHT>                               \n");
			sb.append("  </HUNM_INF>                                   \n");
			sb.append("  <IITEM>1003082</IITEM>                        \n");
			sb.append("  <INM/>                                        \n");
			sb.append("  <CONT_AMT></CONT_AMT>                         \n");
			sb.append("  <PRM>100000</PRM>                             \n");
			sb.append("  <INPRD_TP>20</INPRD_TP>                       \n");
			sb.append("  <INPRD_TP_VAL>50</INPRD_TP_VAL>               \n");
			sb.append("  <PMPRD_TP>01</PMPRD_TP>                       \n");
			sb.append("  <PMPRD_TP_VAL>9</PMPRD_TP_VAL>                \n");
			sb.append("  <COVERAGE_NOTICE_CD/>                         \n");
			sb.append("  <INSU_INF>                                    \n");
			sb.append("    <R_IITEM></R_IITEM>                         \n");
			sb.append("    <R_INM></R_INM>                             \n");
			sb.append("    <R_CONT_AMT></R_CONT_AMT>                   \n");
			sb.append("    <R_PRM></R_PRM>                             \n");
			sb.append("    <R_INPRD_TP></R_INPRD_TP>                   \n");
			sb.append("    <R_INPRD_TP_VAL></R_INPRD_TP_VAL>           \n");
			sb.append("    <R_PMPRD_TP></R_PMPRD_TP>                   \n");
			sb.append("    <R_PMPRD_TP_VAL></R_PMPRD_TP_VAL>           \n");
			sb.append("    <R_BENE_INF>                                \n");
			sb.append("      <R_NAME></R_NAME>                         \n");
			sb.append("      <R_CAUSE_CD></R_CAUSE_CD>                 \n");
			sb.append("      <R_DEPTH></R_DEPTH>                       \n");
			sb.append("      <R_VAL_INF>                               \n");
			sb.append("        <R_VALUE></R_VALUE>                     \n");
			sb.append("      </R_VAL_INF>                              \n");
			sb.append("    </R_BENE_INF>                               \n");
			sb.append("    <R_INSU_OPT_INF>                            \n");
			sb.append("      <R_INSU_OPTION_KEY></R_INSU_OPTION_KEY>   \n");
			sb.append("      <R_INSU_OPTION_VAL></R_INSU_OPTION_VAL>   \n");
			sb.append("    </R_INSU_OPT_INF>                           \n");
			sb.append("    <R_NOTICE_CD></R_NOTICE_CD>                 \n");
			sb.append("    <R_PRM_IDX/>                                \n");
			sb.append("    <R_GEN_INPRD_TP/>                           \n");
			sb.append("    <R_GEN_PMPRD_TP/>                           \n");
			sb.append("    <R_GEN_CONT_AMT/>                           \n");
			sb.append("  </INSU_INF>                                   \n");
			sb.append("  <R_COVERAGE_NOTICE_CD/>                       \n");
			sb.append("  <SURRENDER_NOTICE_CD/>                        \n");
			sb.append("  <ANNU_NOTICE_CD/>                             \n");
			sb.append("  <ACUM_NOTICE_CD/>                             \n");
			sb.append("  <SAVING_NOTICE_CD/>                           \n");
			sb.append("  <PUB_RATE/>                                   \n");
			sb.append("  <PLLN_RATE/>                                  \n");
			sb.append("  <ETC_RATE/>                                   \n");
			sb.append("  <ADM_PLN_NO/>                                 \n");
			sb.append("  <CAPITAL_ATTAIN_MONTH/>                       \n");
			sb.append("  <POLY_NOTICE_CD/>                             \n");
			sb.append("  <CRNCY_GB>KRW</CRNCY_GB>                      \n");
			sb.append("  <RTCD/>                                       \n");
			sb.append("  <MSG2000/>                                    \n");
			sb.append("</BusinessArea></Bancassurance>                 \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				//String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				//System.out.println("[DOCSEQ=" + strDocSeq + "]");
				//document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				/*
				 * ��û������ �����Ѵ�.
				 */
				strReqXml = XmlTool.transNodeToXml(document, true, strCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqXml;
	}

	/////////////////////////////////////////////////////////////////

	private static String makeReqSampleXml() {
		boolean flag = true;
		
		if (!flag && strReqSampleXml != null)
			return strReqSampleXml;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.12  : ��������
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			
			/*
			 * 
			 */
			sb.append("    <HDR_TRX_ID>HWI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : ��ȭ����
			//sb.append("    <HDR_TRX_ID>SHI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : ���ѻ���
			//sb.append("    <HDR_TRX_ID>HNI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : �ϳ�HSBC����
			//sb.append("    <HDR_TRX_ID>HKI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : �ﱹ����
			//sb.append("    <HDR_TRX_ID>KFI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : ī��������
			//sb.append("    <HDR_TRX_ID>ALI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : �˸���������
			//sb.append("    <HDR_TRX_ID>SSI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : �Ｚ����
			//sb.append("    <HDR_TRX_ID>MII01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : �̷����»���
			//sb.append("    <HDR_TRX_ID>NHI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : ��������
			//sb.append("    <HDR_TRX_ID>NRI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID : ���������
			
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // ����
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // ���� : header + body
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");   // �ڷᱸ�� : TEST
			
			/*
			 * 
			 */
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");   // ������ڵ� : LO1 (��ȭ����)
			//sb.append("    <HDR_INS_ID>L11</HDR_INS_ID>                           \n");   // ������ڵ� : L11 (���ѻ���)
			//sb.append("    <HDR_INS_ID>Y97</HDR_INS_ID>                           \n");   // ������ڵ� : Y97 (���������)
			
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
			sb.append("    <HDR_SND_DATE>20130624</HDR_SND_DATE>                  \n");   // ����������
			sb.append("    <HDR_SND_TIME>213000</HDR_SND_TIME>                    \n");   // �������۽ð�
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");   // ���� ������ȣ
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");   // ����� ������ȣ
			sb.append("    <HDR_TXN_DATE>20130624</HDR_TXN_DATE>                  \n");   // �ŷ��߻���
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");   // ���� �߰�����
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");   // ����� �߰�����
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (!flag) {   // DATE.2013.06.01
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>BANCA-1</HDR_TRX_ID>                       \n");
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");
			sb.append("    <HDR_BAK_ID>209</HDR_BAK_ID>                           \n");
			sb.append("    <HDR_DOC_CODE>0210</HDR_DOC_CODE>                      \n");
			sb.append("    <HDR_BIZ_CODE>076100</HDR_BIZ_CODE>                    \n");
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");
			sb.append("    <HDR_SND_DATE>20130514</HDR_SND_DATE>                  \n");
			sb.append("    <HDR_SND_TIME>161214</HDR_SND_TIME>                    \n");
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");
			sb.append("    <HDR_TXN_DATE>20130514</HDR_TXN_DATE>                  \n");
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");
			sb.append("  </HeaderArea>                                            \n");
			sb.append("  <BusinessArea>                                           \n");
			sb.append("    <B_CONT_NO>155399876</B_CONT_NO>                       \n");
			sb.append("  </BusinessArea>                                          \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				//strReqSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
				strReqSampleXml = XmlTool.transNodeToXml(document, true, strCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqSampleXml;
	}
	
	/////////////////////////////////////////////////////////////////

	private static String makeReqSampleXml(String depNo) {
		boolean flag = true;
		
		if (!flag && strReqSampleXml != null)
			return strReqSampleXml;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.12  : ��������
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>HWI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // ����
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // ���� : header + body
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");   // �ڷᱸ�� : TEST
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");   // ������ڵ� : LO1 (���ѻ���)
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
			sb.append("    <HDR_SND_DATE>20130624</HDR_SND_DATE>                  \n");   // ����������
			sb.append("    <HDR_SND_TIME>213000</HDR_SND_TIME>                    \n");   // �������۽ð�
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");   // ���� ������ȣ
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");   // ����� ������ȣ
			sb.append("    <HDR_TXN_DATE>20130624</HDR_TXN_DATE>                  \n");   // �ŷ��߻���
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");   // ���� �߰�����
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");   // ����� �߰�����
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("%2.2s%06d", depNo, SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				//strReqSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
				strReqSampleXml = XmlTool.transNodeToXml(document, true, strCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqSampleXml;
	}
	
	/////////////////////////////////////////////////////////////////

	private static String makeReqSampleXml(String fepId, String depNo) {
		boolean flag = true;
		
		if (!flag && strReqSampleXml != null)
			return strReqSampleXml;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.12  : ��������
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>HWI01</HDR_TRX_ID>                         \n");   // TRX ID <- FEPID
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // ����
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // ���� : header + body
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");   // �ڷᱸ�� : TEST
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");   // ������ڵ� : LO1 (���ѻ���)
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0800</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");   // �ۼ��� FLAG
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");   // STATUS
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");   // �����ڵ�
			sb.append("    <HDR_SND_DATE>20130624</HDR_SND_DATE>                  \n");   // ����������
			sb.append("    <HDR_SND_TIME>213000</HDR_SND_TIME>                    \n");   // �������۽ð�
			sb.append("    <HDR_BAK_DOCSEQ>99000123</HDR_BAK_DOCSEQ>              \n");   // ���� ������ȣ
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");   // ����� ������ȣ
			sb.append("    <HDR_TXN_DATE>20130624</HDR_TXN_DATE>                  \n");   // �ŷ��߻���
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");   // ��ü ���� ��
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");   // ������������
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");   // ó����/�ܸ���ȣ
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");   // ���� �߰�����
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");   // ����� �߰�����
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_TRX_ID ���� ����
				document.getElementsByTagName("HDR_TRX_ID").item(0).setTextContent(fepId);
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("%2.2s%06d", depNo, SeqGender.getInstance().getInt());
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				if (!flag) System.out.print("[KEY=" + fepId + strDocSeq + "]");
				
				//strReqSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
				strReqSampleXml = XmlTool.transNodeToXml(document, true, strCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqSampleXml;
	}
	
	/////////////////////////////////////////////////////////////////

	private static String makeResSampleXml() {
		boolean flag = true;
		
		if (strResSampleXml != null)
			return strResSampleXml;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.01  : �������� ����
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>BANCA-1</HDR_TRX_ID>                       \n");
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");   // ����
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");   // ���� : header + body
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");   // ������ڵ� : LO1 (���ѻ���)
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");   // �������Ǳ��� : 02 (���ǻ�)
			sb.append("    <HDR_BAK_ID>269</HDR_BAK_ID>                           \n");   // �����ڵ� : 269 (��ȭ����)
			sb.append("    <HDR_DOC_CODE>0810</HDR_DOC_CODE>                      \n");   // 0800
			sb.append("    <HDR_BIZ_CODE>001000</HDR_BIZ_CODE>                    \n");   // 001000
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");
			sb.append("    <HDR_SND_DATE>20130514</HDR_SND_DATE>                  \n");
			sb.append("    <HDR_SND_TIME>161214</HDR_SND_TIME>                    \n");
			sb.append("    <HDR_BAK_DOCSEQ>00099999</HDR_BAK_DOCSEQ>              \n");
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");
			sb.append("    <HDR_TXN_DATE>20130514</HDR_TXN_DATE>                  \n");
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");
			sb.append("  </HeaderArea>                                            \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (!flag) {   // DATE.2013.06.12
			sb.append("<Bancassurance>                                            \n");
			sb.append("  <HeaderArea>                                             \n");
			sb.append("    <HDR_TRX_ID>BANCA-2</HDR_TRX_ID>                       \n");
			sb.append("    <HDR_SYS_ID>BAS</HDR_SYS_ID>                           \n");
			sb.append("    <HDR_DOC_LEN>900</HDR_DOC_LEN>                         \n");
			sb.append("    <HDR_DAT_GBN>T</HDR_DAT_GBN>                           \n");
			sb.append("    <HDR_INS_ID>L01</HDR_INS_ID>                           \n");
			sb.append("    <HDR_BAK_CLS>02</HDR_BAK_CLS>                          \n");
			sb.append("    <HDR_BAK_ID>209</HDR_BAK_ID>                           \n");
			sb.append("    <HDR_DOC_CODE>0210</HDR_DOC_CODE>                      \n");
			sb.append("    <HDR_BIZ_CODE>076100</HDR_BIZ_CODE>                    \n");
			sb.append("    <HDR_TRA_FLAG>5</HDR_TRA_FLAG>                         \n");
			sb.append("    <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                     \n");
			sb.append("    <HDR_RET_CODE>000</HDR_RET_CODE>                       \n");
			sb.append("    <HDR_SND_DATE>20130514</HDR_SND_DATE>                  \n");
			sb.append("    <HDR_SND_TIME>161214</HDR_SND_TIME>                    \n");
			sb.append("    <HDR_BAK_DOCSEQ>00051365</HDR_BAK_DOCSEQ>              \n");
			sb.append("    <HDR_INS_DOCSEQ>000</HDR_INS_DOCSEQ>                   \n");
			sb.append("    <HDR_TXN_DATE>20130514</HDR_TXN_DATE>                  \n");
			sb.append("    <HDR_TOT_DOC>01</HDR_TOT_DOC>                          \n");
			sb.append("    <HDR_CUR_DOC>01</HDR_CUR_DOC>                          \n");
			sb.append("    <HDR_AGT_CODE>TYP01002</HDR_AGT_CODE>                  \n");
			sb.append("    <HDR_BAK_EXT>000</HDR_BAK_EXT>                         \n");
			sb.append("    <HDR_INS_EXT>000</HDR_INS_EXT>                         \n");
			sb.append("  </HeaderArea>                                            \n");
			sb.append("  <BusinessArea>                                           \n");
			sb.append("    <B_CONT_NO>155399876</B_CONT_NO>                       \n");
			sb.append("    <PITEM>1563007</PITEM>                                 \n");
			sb.append("    <PITEM_NM>(��)����Ʈ63��</PITEM_NM>                    \n");
			sb.append("    <CONT_YMD>20120127</CONT_YMD>                          \n");
			sb.append("    <EXPD_YMD>20220127</EXPD_YMD>                          \n");
			sb.append("    <DEST_YMD/>                                            \n");
			sb.append("    <TOT_PRM>1000000</TOT_PRM>                             \n");
			sb.append("    <LCYM>201303</LCYM>                                    \n");
			sb.append("    <LPMT>15</LPMT>                                        \n");
			sb.append("    <PLCY_CVY_MTH>1</PLCY_CVY_MTH>                         \n");
			sb.append("    <ETC_PST_RCV>1</ETC_PST_RCV>                           \n");
			sb.append("    <PM_CYL>01</PM_CYL>                                    \n");
			sb.append("    <CONT_STS>30</CONT_STS>                                \n");
			sb.append("    <FREE_CONT_AMT>0</FREE_CONT_AMT>                       \n");
			sb.append("    <FTM_ONC_PRM>0</FTM_ONC_PRM>                           \n");
			sb.append("    <HUNM_INF>                                             \n");
			sb.append("      <CUST_CONT_RTP>11</CUST_CONT_RTP>                    \n");
			sb.append("      <CUST_SORT_CD>1</CUST_SORT_CD>                       \n");
			sb.append("      <CUST_NM>�赿��</CUST_NM>                            \n");
			sb.append("      <SSN>7403151542715</SSN>                             \n");
			sb.append("      <JOB_CD>171501</JOB_CD>                              \n");
			sb.append("      <ISD_REL>01</ISD_REL>                                \n");
			sb.append("      <HP_CNTY/>                                           \n");
			sb.append("      <HP_COM>010</HP_COM>                                 \n");
			sb.append("      <HP_GUK>8534</HP_GUK>                                \n");
			sb.append("      <HP_NO>5748</HP_NO>                                  \n");
			sb.append("      <ADDR_INF>                                           \n");
			sb.append("        <ADDR_GB>10</ADDR_GB>                              \n");
			sb.append("        <PST_NO1>135</PST_NO1>                             \n");
			sb.append("        <PST_NO2>090</PST_NO2>                             \n");
			sb.append("        <PST_ADDR>���� ������ �Ｚ��</PST_ADDR>            \n");
			sb.append("        <ETC_ADDR>�Ե�ĳ�������̾� 106�� 1304ȣ</ETC_ADDR> \n");
			sb.append("        <TEL_CNTY/>                                        \n");
			sb.append("        <TEL_DDD>010</TEL_DDD>                             \n");
			sb.append("        <TEL_GUK>8534</TEL_GUK>                            \n");
			sb.append("        <TEL_NO>5748</TEL_NO>                              \n");
			sb.append("      </ADDR_INF>                                          \n");
			sb.append("      <ADDR_INF>                                           \n");
			sb.append("        <ADDR_GB>20</ADDR_GB>                              \n");
			sb.append("        <PST_NO1>135</PST_NO1>                             \n");
			sb.append("        <PST_NO2>090</PST_NO2>                             \n");
			sb.append("        <PST_ADDR>���� ������ �Ｚ��</PST_ADDR>            \n");
			sb.append("        <ETC_ADDR>�Ե�ĳ�������̾� 106�� 1304ȣ</ETC_ADDR> \n");
			sb.append("        <TEL_CNTY/>                                        \n");
			sb.append("        <TEL_DDD>010</TEL_DDD>                             \n");
			sb.append("        <TEL_GUK>8534</TEL_GUK>                            \n");
			sb.append("        <TEL_NO>5748</TEL_NO>                              \n");
			sb.append("      </ADDR_INF>                                          \n");
			sb.append("    </HUNM_INF>                                            \n");
			sb.append("    <HUNM_INF>                                             \n");
			sb.append("      <CUST_CONT_RTP>21</CUST_CONT_RTP>                    \n");
			sb.append("      <CUST_SORT_CD>1</CUST_SORT_CD>                       \n");
			sb.append("      <CUST_NM>�赿��</CUST_NM>                            \n");
			sb.append("      <SSN>7403151542715</SSN>                             \n");
			sb.append("      <JOB_CD>171501</JOB_CD>                              \n");
			sb.append("      <ISD_REL>01</ISD_REL>                                \n");
			sb.append("      <HP_CNTY/>                                           \n");
			sb.append("      <HP_COM>010</HP_COM>                                 \n");
			sb.append("      <HP_GUK>8534</HP_GUK>                                \n");
			sb.append("      <HP_NO>5748</HP_NO>                                  \n");
			sb.append("      <ADDR_INF>                                           \n");
			sb.append("        <ADDR_GB>10</ADDR_GB>                              \n");
			sb.append("        <PST_NO1>135</PST_NO1>                             \n");
			sb.append("        <PST_NO2>090</PST_NO2>                             \n");
			sb.append("        <PST_ADDR>���� ������ �Ｚ��</PST_ADDR>            \n");
			sb.append("        <ETC_ADDR>�Ե�ĳ�������̾� 106�� 1304ȣ</ETC_ADDR> \n");
			sb.append("        <TEL_CNTY/>                                        \n");
			sb.append("        <TEL_DDD>010</TEL_DDD>                             \n");
			sb.append("        <TEL_GUK>8534</TEL_GUK>                            \n");
			sb.append("        <TEL_NO>5748</TEL_NO>                              \n");
			sb.append("      </ADDR_INF>                                          \n");
			sb.append("      <ADDR_INF>                                           \n");
			sb.append("        <ADDR_GB>20</ADDR_GB>                              \n");
			sb.append("        <PST_NO1>135</PST_NO1>                             \n");
			sb.append("        <PST_NO2>090</PST_NO2>                             \n");
			sb.append("        <PST_ADDR>���� ������ �Ｚ��</PST_ADDR>            \n");
			sb.append("        <ETC_ADDR>�Ե�ĳ�������̾� 106�� 1304ȣ</ETC_ADDR> \n");
			sb.append("        <TEL_CNTY/>                                        \n");
			sb.append("        <TEL_DDD>010</TEL_DDD>                             \n");
			sb.append("        <TEL_GUK>8534</TEL_GUK>                            \n");
			sb.append("        <TEL_NO>5748</TEL_NO>                              \n");
			sb.append("      </ADDR_INF>                                          \n");
			sb.append("    </HUNM_INF>                                            \n");
			sb.append("    <BNFR_INF>                                             \n");
			sb.append("      <CUST_CONT_RTP_BNFR>31</CUST_CONT_RTP_BNFR>          \n");
			sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>             \n");
			sb.append("      <CUST_NM_BNFR>�赿��</CUST_NM_BNFR>                  \n");
			sb.append("      <SSN_BNFR>7403151542715</SSN_BNFR>                   \n");
			sb.append("      <ISD_REL_BNFR>01</ISD_REL_BNFR>                      \n");
			sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                  \n");
			sb.append("    </BNFR_INF>                                            \n");
			sb.append("    <BNFR_INF>                                             \n");
			sb.append("      <CUST_CONT_RTP_BNFR>33</CUST_CONT_RTP_BNFR>          \n");
			sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>             \n");
			sb.append("      <CUST_NM_BNFR>�赿��</CUST_NM_BNFR>                  \n");
			sb.append("      <SSN_BNFR>7403151542715</SSN_BNFR>                   \n");
			sb.append("      <ISD_REL_BNFR>01</ISD_REL_BNFR>                      \n");
			sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                  \n");
			sb.append("    </BNFR_INF>                                            \n");
			sb.append("    <BNFR_INF>                                             \n");
			sb.append("      <CUST_CONT_RTP_BNFR>35</CUST_CONT_RTP_BNFR>          \n");
			sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>             \n");
			sb.append("      <CUST_NM_BNFR>���������</CUST_NM_BNFR>              \n");
			sb.append("      <SSN_BNFR>0000000000000</SSN_BNFR>                   \n");
			sb.append("      <ISD_REL_BNFR>11</ISD_REL_BNFR>                      \n");
			sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                  \n");
			sb.append("    </BNFR_INF>                                            \n");
			sb.append("    <TRSF_HOP_DD>30</TRSF_HOP_DD>                          \n");
			sb.append("    <DEPOR_NM>�赿��</DEPOR_NM>                            \n");
			sb.append("    <DEPOR_SSN>7403151542715</DEPOR_SSN>                   \n");
			sb.append("    <BANK_NM>������������</BANK_NM>                        \n");
			sb.append("    <ACNO>09971000889</ACNO>                               \n");
			sb.append("    <PE_CD>20912087</PE_CD>                                \n");
			sb.append("    <BANK_CD>209</BANK_CD>                                 \n");
			sb.append("    <BRAN_CD>10006</BRAN_CD>                               \n");
			sb.append("    <CONT_INF>                                             \n");
			sb.append("      <IITEM>1563007</IITEM>                               \n");
			sb.append("      <IITEM_GB>1</IITEM_GB>                               \n");
			sb.append("      <INM>����� ����Ʈ63���襱 �⺻��</INM>              \n");
			sb.append("      <CONT_AMT>75357950</CONT_AMT>                        \n");
			sb.append("      <PRM>1000000</PRM>                                   \n");
			sb.append("      <INPRD_TP>01</INPRD_TP>                              \n");
			sb.append("      <INPRD_TP_VAL>10</INPRD_TP_VAL>                      \n");
			sb.append("      <PMPRD_TP>01</PMPRD_TP>                              \n");
			sb.append("      <PMPRD_TP_VAL>5</PMPRD_TP_VAL>                       \n");
			sb.append("      <CONT_OPT_INF>                                       \n");
			sb.append("        <CONT_OPT_KEY/>                                    \n");
			sb.append("        <CONT_OPT_VAL/>                                    \n");
			sb.append("      </CONT_OPT_INF>                                      \n");
			sb.append("    </CONT_INF>                                            \n");
			sb.append("    <FUND_INF>                                             \n");
			sb.append("      <FUND_PRM_GB/>                                       \n");
			sb.append("      <FUND_SH_RAT>0.0</FUND_SH_RAT>                       \n");
			sb.append("      <VARI_FUND_TP/>                                      \n");
			sb.append("      <FUND_NAME/>                                         \n");
			sb.append("    </FUND_INF>                                            \n");
			sb.append("    <FUND_DIST_GB>0</FUND_DIST_GB>                         \n");
			sb.append("    <FUND_DIST_CYL/>                                       \n");
			sb.append("    <FUND_DIST_DAY/>                                       \n");
			sb.append("    <FUND_DIST_NEXTYMD/>                                   \n");
			sb.append("    <DCA_GB>0</DCA_GB>                                     \n");
			sb.append("    <DCA_CYL/>                                             \n");
			sb.append("    <DCA_DAY/>                                             \n");
			sb.append("    <FUND_OPT_INF>                                         \n");
			sb.append("      <FUND_OPTION_KEY/>                                   \n");
			sb.append("      <FUND_OPTION_VAL/>                                   \n");
			sb.append("    </FUND_OPT_INF>                                        \n");
			sb.append("    <ANTY_CL_TP/>                                          \n");
			sb.append("    <MULTI_ANTY_INF>                                       \n");
			sb.append("      <MULTI_CL_TP/>                                       \n");
			sb.append("      <ANTY_CL_RAT/>                                       \n");
			sb.append("      <ANST_AGE>0</ANST_AGE>                               \n");
			sb.append("      <ANTY_CL_PRD>0</ANTY_CL_PRD>                         \n");
			sb.append("      <ANTY_CL_CYL/>                                       \n");
			sb.append("      <MIN_GUR_PRD/>                                       \n");
			sb.append("      <GINCR_TP/>                                          \n");
			sb.append("      <GINCR_PRD>0</GINCR_PRD>                             \n");
			sb.append("      <GINCR_VALUE>0</GINCR_VALUE>                         \n");
			sb.append("    </MULTI_ANTY_INF>                                      \n");
			sb.append("    <BEF_DISC_PRM_TOT>15000000</BEF_DISC_PRM_TOT>          \n");
			sb.append("    <AFT_DISC_PRM_TOT>14850000</AFT_DISC_PRM_TOT>          \n");
			sb.append("    <DISC_PRM_TOT>150000</DISC_PRM_TOT>                    \n");
			sb.append("    <PM_METH>07</PM_METH>                                  \n");
			sb.append("    <CRNCY_GB>KRW</CRNCY_GB>                               \n");
			sb.append("    <RTCD>0000000000</RTCD>                                \n");
			sb.append("    <MSG200>���������� ó���Ǿ����ϴ�.</MSG200>            \n");
			sb.append("  </BusinessArea>                                          \n");
			sb.append("</Bancassurance>                                           \n");
		}
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();

				strResSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strResSampleXml;
	}
	
	/////////////////////////////////////////////////////////////////

	private static String makeStreamXml() {
		boolean flag = true;
		
		if (strStreamXml != null)
			return strStreamXml;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?>                 \n");
		sb.append("<Bancassurance>                                                         \n");
		sb.append("  <BusinessArea>                                                        \n");
		sb.append("    <B_CONT_NO>158354211</B_CONT_NO>                                    \n");
		sb.append("    <PITEM>1718010</PITEM>                                              \n");
		sb.append("    <PITEM_NM>(��)����ƮV����</PITEM_NM>                                \n");
		sb.append("    <CONT_YMD>20120914</CONT_YMD>                                       \n");
		sb.append("    <EXPD_YMD>20150914</EXPD_YMD>                                       \n");
		sb.append("    <DEST_YMD/>                                                         \n");
		sb.append("    <TOT_PRM>10000000</TOT_PRM>                                         \n");
		sb.append("    <LCYM>201301</LCYM>                                                 \n");
		sb.append("    <LPMT>5</LPMT>                                                      \n");
		sb.append("    <PLCY_CVY_MTH>1</PLCY_CVY_MTH>                                      \n");
		sb.append("    <ETC_PST_RCV>2</ETC_PST_RCV>                                        \n");
		sb.append("    <PM_CYL>01</PM_CYL>                                                 \n");
		sb.append("    <CONT_STS>60</CONT_STS>                                             \n");
		sb.append("    <FREE_CONT_AMT>0</FREE_CONT_AMT>                                    \n");
		sb.append("    <FTM_ONC_PRM>0</FTM_ONC_PRM>                                        \n");
		sb.append("    <HUNM_INF>                                                          \n");
		sb.append("      <CUST_CONT_RTP>11</CUST_CONT_RTP>                                 \n");
		sb.append("      <CUST_SORT_CD>1</CUST_SORT_CD>                                    \n");
		sb.append("      <CUST_NM>������</CUST_NM>                                         \n");
		sb.append("      <SSN>4608301675818</SSN>                                          \n");
		sb.append("      <JOB_CD>150112</JOB_CD>                                           \n");
		sb.append("      <ISD_REL>01</ISD_REL>                                             \n");
		sb.append("      <HP_CNTY/>                                                        \n");
		sb.append("      <HP_COM>011</HP_COM>                                              \n");
		sb.append("      <HP_GUK>536</HP_GUK>                                              \n");
		sb.append("      <HP_NO>2443</HP_NO>                                               \n");
		sb.append("      <ADDR_INF>                                                        \n");
		sb.append("        <ADDR_GB>10</ADDR_GB>                                           \n");
		sb.append("        <PST_NO1>704</PST_NO1>                                          \n");
		sb.append("        <PST_NO2>160</PST_NO2>                                          \n");
		sb.append("        <PST_ADDR>�뱸 �޼��� ������</PST_ADDR>                         \n");
		sb.append("        <ETC_ADDR>�����帶ũ 101��2401ȣ</ETC_ADDR>                   \n");
		sb.append("        <TEL_CNTY/>                                                     \n");
		sb.append("        <TEL_DDD>053</TEL_DDD>                                          \n");
		sb.append("        <TEL_GUK>655</TEL_GUK>                                          \n");
		sb.append("        <TEL_NO>0100</TEL_NO>                                           \n");
		sb.append("      </ADDR_INF>                                                       \n");
		sb.append("      <ADDR_INF>                                                        \n");
		sb.append("        <ADDR_GB>20</ADDR_GB>                                           \n");
		sb.append("        <PST_NO1>717</PST_NO1>                                          \n");
		sb.append("        <PST_NO2>832</PST_NO2>                                          \n");
		sb.append("        <PST_ADDR>��� ��ɱ� ����� �Ű���</PST_ADDR>                  \n");
		sb.append("        <ETC_ADDR>820-1����</ETC_ADDR>                                  \n");
		sb.append("        <TEL_CNTY/>                                                     \n");
		sb.append("        <TEL_DDD>054</TEL_DDD>                                          \n");
		sb.append("        <TEL_GUK>956</TEL_GUK>                                          \n");
		sb.append("        <TEL_NO>2230</TEL_NO>                                           \n");
		sb.append("      </ADDR_INF>                                                       \n");
		sb.append("    </HUNM_INF>                                                         \n");
		sb.append("    <HUNM_INF>                                                          \n");
		sb.append("      <CUST_CONT_RTP>21</CUST_CONT_RTP>                                 \n");
		sb.append("      <CUST_SORT_CD>1</CUST_SORT_CD>                                    \n");
		sb.append("      <CUST_NM>������</CUST_NM>                                         \n");
		sb.append("      <SSN>4608301675818</SSN>                                          \n");
		sb.append("      <JOB_CD>150112</JOB_CD>                                           \n");
		sb.append("      <ISD_REL>01</ISD_REL>                                             \n");
		sb.append("      <HP_CNTY/>                                                        \n");
		sb.append("      <HP_COM>011</HP_COM>                                              \n");
		sb.append("      <HP_GUK>536</HP_GUK>                                              \n");
		sb.append("      <HP_NO>2443</HP_NO>                                               \n");
		sb.append("      <ADDR_INF>                                                        \n");
		sb.append("        <ADDR_GB>10</ADDR_GB>                                           \n");
		sb.append("        <PST_NO1>704</PST_NO1>                                          \n");
		sb.append("        <PST_NO2>160</PST_NO2>                                          \n");
		sb.append("        <PST_ADDR>�뱸 �޼��� ������</PST_ADDR>                         \n");
		sb.append("        <ETC_ADDR>�����帶ũ 101��2401ȣ</ETC_ADDR>                   \n");
		sb.append("        <TEL_CNTY/>                                                     \n");
		sb.append("        <TEL_DDD>053</TEL_DDD>                                          \n");
		sb.append("        <TEL_GUK>655</TEL_GUK>                                          \n");
		sb.append("        <TEL_NO>0100</TEL_NO>                                           \n");
		sb.append("      </ADDR_INF>                                                       \n");
		sb.append("      <ADDR_INF>                                                        \n");
		sb.append("        <ADDR_GB>20</ADDR_GB>                                           \n");
		sb.append("        <PST_NO1>717</PST_NO1>                                          \n");
		sb.append("        <PST_NO2>832</PST_NO2>                                          \n");
		sb.append("        <PST_ADDR>��� ��ɱ� ����� �Ű���</PST_ADDR>                  \n");
		sb.append("        <ETC_ADDR>820-1����</ETC_ADDR>                                  \n");
		sb.append("        <TEL_CNTY/>                                                     \n");
		sb.append("        <TEL_DDD>054</TEL_DDD>                                          \n");
		sb.append("        <TEL_GUK>956</TEL_GUK>                                          \n");
		sb.append("        <TEL_NO>2230</TEL_NO>                                           \n");
		sb.append("      </ADDR_INF>                                                       \n");
		sb.append("    </HUNM_INF>                                                         \n");
		sb.append("    <BNFR_INF>                                                          \n");
		sb.append("      <CUST_CONT_RTP_BNFR>31</CUST_CONT_RTP_BNFR>                       \n");
		sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>                          \n");
		sb.append("      <CUST_NM_BNFR>������</CUST_NM_BNFR>                               \n");
		sb.append("      <SSN_BNFR>4608301675818</SSN_BNFR>                                \n");
		sb.append("      <ISD_REL_BNFR>01</ISD_REL_BNFR>                                   \n");
		sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                               \n");
		sb.append("    </BNFR_INF>                                                         \n");
		sb.append("    <BNFR_INF>                                                          \n");
		sb.append("      <CUST_CONT_RTP_BNFR>33</CUST_CONT_RTP_BNFR>                       \n");
		sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>                          \n");
		sb.append("      <CUST_NM_BNFR>������</CUST_NM_BNFR>                               \n");
		sb.append("      <SSN_BNFR>4608301675818</SSN_BNFR>                                \n");
		sb.append("      <ISD_REL_BNFR>01</ISD_REL_BNFR>                                   \n");
		sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                               \n");
		sb.append("    </BNFR_INF>                                                         \n");
		sb.append("    <BNFR_INF>                                                          \n");
		sb.append("      <CUST_CONT_RTP_BNFR>35</CUST_CONT_RTP_BNFR>                       \n");
		sb.append("      <CUST_BNFR_SORT_CD>1</CUST_BNFR_SORT_CD>                          \n");
		sb.append("      <CUST_NM_BNFR>���������</CUST_NM_BNFR>                           \n");
		sb.append("      <SSN_BNFR>0000000000000</SSN_BNFR>                                \n");
		sb.append("      <ISD_REL_BNFR>11</ISD_REL_BNFR>                                   \n");
		sb.append("      <SHR_RTO_BNFR>100.00</SHR_RTO_BNFR>                               \n");
		sb.append("    </BNFR_INF>                                                         \n");
		sb.append("    <TRSF_HOP_DD>15</TRSF_HOP_DD>                                       \n");
		sb.append("    <DEPOR_NM>������(������)</DEPOR_NM>                               \n");
		sb.append("    <DEPOR_SSN>4608301675818</DEPOR_SSN>                                \n");
		sb.append("    <BANK_NM>����</BANK_NM>                                             \n");
		sb.append("    <ACNO>76501078961</ACNO>                                            \n");
		sb.append("    <PE_CD>11301472</PE_CD>                                             \n");
		sb.append("    <BANK_CD>011</BANK_CD>                                              \n");
		sb.append("    <BRAN_CD>000765</BRAN_CD>                                           \n");
		sb.append("    <CONT_INF>                                                          \n");
		sb.append("      <IITEM>1718010</IITEM>                                            \n");
		sb.append("      <IITEM_GB>1</IITEM_GB>                                            \n");
		sb.append("      <INM>����� ����ƮV���ຸ�� ������ȯ�� ������(����������)</INM>   \n");
		sb.append("      <CONT_AMT>240000000</CONT_AMT>                                    \n");
		sb.append("      <PRM>10000000</PRM>                                               \n");
		sb.append("      <INPRD_TP>01</INPRD_TP>                                           \n");
		sb.append("      <INPRD_TP_VAL>3</INPRD_TP_VAL>                                    \n");
		sb.append("      <PMPRD_TP>01</PMPRD_TP>                                           \n");
		sb.append("      <PMPRD_TP_VAL>2</PMPRD_TP_VAL>                                    \n");
		sb.append("      <CONT_OPT_INF>                                                    \n");
		sb.append("        <CONT_OPT_KEY/>                                                 \n");
		sb.append("        <CONT_OPT_VAL/>                                                 \n");
		sb.append("      </CONT_OPT_INF>                                                   \n");
		sb.append("    </CONT_INF>                                                         \n");
		sb.append("    <FUND_INF>                                                          \n");
		sb.append("      <FUND_PRM_GB/>                                                    \n");
		sb.append("      <FUND_SH_RAT>0.0</FUND_SH_RAT>                                    \n");
		sb.append("      <VARI_FUND_TP/>                                                   \n");
		sb.append("      <FUND_NAME/>                                                      \n");
		sb.append("    </FUND_INF>                                                         \n");
		sb.append("    <FUND_DIST_GB>0</FUND_DIST_GB>                                      \n");
		sb.append("    <FUND_DIST_CYL/>                                                    \n");
		sb.append("    <FUND_DIST_DAY/>                                                    \n");
		sb.append("    <FUND_DIST_NEXTYMD/>                                                \n");
		sb.append("    <DCA_GB>0</DCA_GB>                                                  \n");
		sb.append("    <DCA_CYL/>                                                          \n");
		sb.append("    <DCA_DAY/>                                                          \n");
		sb.append("    <FUND_OPT_INF>                                                      \n");
		sb.append("      <FUND_OPTION_KEY/>                                                \n");
		sb.append("      <FUND_OPTION_VAL/>                                                \n");
		sb.append("    </FUND_OPT_INF>                                                     \n");
		sb.append("    <ANTY_CL_TP/>                                                       \n");
		sb.append("    <MULTI_ANTY_INF>                                                    \n");
		sb.append("      <MULTI_CL_TP/>                                                    \n");
		sb.append("      <ANTY_CL_RAT/>                                                    \n");
		sb.append("      <ANST_AGE>0</ANST_AGE>                                            \n");
		sb.append("      <ANTY_CL_PRD>0</ANTY_CL_PRD>                                      \n");
		sb.append("      <ANTY_CL_CYL/>                                                    \n");
		sb.append("      <MIN_GUR_PRD/>                                                    \n");
		sb.append("      <GINCR_TP/>                                                       \n");
		sb.append("      <GINCR_PRD>0</GINCR_PRD>                                          \n");
		sb.append("      <GINCR_VALUE>0</GINCR_VALUE>                                      \n");
		sb.append("    </MULTI_ANTY_INF>                                                   \n");
		sb.append("    <BEF_DISC_PRM_TOT>50000000</BEF_DISC_PRM_TOT>                       \n");
		sb.append("    <AFT_DISC_PRM_TOT>50000000</AFT_DISC_PRM_TOT>                       \n");
		sb.append("    <DISC_PRM_TOT>0</DISC_PRM_TOT>                                      \n");
		sb.append("    <PM_METH>07</PM_METH>                                               \n");
		sb.append("    <CRNCY_GB>KRW</CRNCY_GB>                                            \n");
		sb.append("    <RTCD>0000000000</RTCD>                                             \n");
		sb.append("    <MSG200>���������� ó���Ǿ����ϴ�.</MSG200>                         \n");
		sb.append("  </BusinessArea>                                                       \n");
		sb.append("</Bancassurance>                                                        \n");
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();

				strStreamXml = XmlTool.transNodeToXml(document, true, strCharset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String strStreamHeader = "HWI01    BAS00847RL010111 26900761005   00020130514135116990001230000000020130514010111301472                                                                   ";

		return strStreamHeader + strStreamXml;
	}

	///////////////////////////////////////////////////////////////////////
	
	private static String makeReq072100Xml() {
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.12  : ��������
			sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>    \n");
			sb.append("<Bancassurance><HeaderArea>                    \n");
			sb.append("  <HDR_TRX_ID>HWI01</HDR_TRX_ID>               \n");
			sb.append("  <HDR_SYS_ID>BAS</HDR_SYS_ID>                 \n");
			sb.append("  <HDR_DOC_LEN>3883</HDR_DOC_LEN>              \n");
			sb.append("  <HDR_DAT_GBN>T</HDR_DAT_GBN>                 \n");
			sb.append("  <HDR_INS_ID>L01</HDR_INS_ID>                 \n");
			sb.append("  <HDR_BAK_CLS>02</HDR_BAK_CLS>                \n");
			sb.append("  <HDR_BAK_ID>269</HDR_BAK_ID>                 \n");
			sb.append("  <HDR_DOC_CODE>0200</HDR_DOC_CODE>            \n");
			sb.append("  <HDR_BIZ_CODE>072100</HDR_BIZ_CODE>          \n");
			sb.append("  <HDR_TRA_FLAG>1</HDR_TRA_FLAG>               \n");
			sb.append("  <HDR_DOC_STATUS>1</HDR_DOC_STATUS>           \n");
			sb.append("  <HDR_RET_CODE>000</HDR_RET_CODE>             \n");
			sb.append("  <HDR_SND_DATE>20130701</HDR_SND_DATE>        \n");
			sb.append("  <HDR_SND_TIME>151038</HDR_SND_TIME>          \n");
			sb.append("  <HDR_BAK_DOCSEQ>00001006</HDR_BAK_DOCSEQ>    \n");
			sb.append("  <HDR_INS_DOCSEQ></HDR_INS_DOCSEQ>            \n");
			sb.append("  <HDR_TXN_DATE>20130701</HDR_TXN_DATE>        \n");
			sb.append("  <HDR_TOT_DOC>01</HDR_TOT_DOC>                \n");
			sb.append("  <HDR_CUR_DOC>01</HDR_CUR_DOC>                \n");
			sb.append("  <HDR_AGT_CODE>111111111155555</HDR_AGT_CODE> \n");
			sb.append("  <HDR_BAK_EXT></HDR_BAK_EXT>                  \n");
			sb.append("  <HDR_INS_EXT></HDR_INS_EXT>                  \n");
			sb.append("</HeaderArea>                                  \n");
			sb.append("<BusinessArea>                                 \n");
			sb.append("  <B_CONT_NO/>                                 \n");
			sb.append("  <PITEM>1718035</PITEM>                       \n");
			sb.append("  <PITEM_NM></PITEM_NM>                        \n");
			sb.append("  <DIAG_YN></DIAG_YN>                          \n");
			sb.append("  <FUND_DIST_GB></FUND_DIST_GB>                \n");
			sb.append("  <FUND_DIST_CYL></FUND_DIST_CYL>              \n");
			sb.append("  <FUND_DIST_DAY></FUND_DIST_DAY>              \n");
			sb.append("  <FUND_DIST_NEXTYMD></FUND_DIST_NEXTYMD>      \n");
			sb.append("  <DCA_GB></DCA_GB>                            \n");
			sb.append("  <DCA_CYL></DCA_CYL>                          \n");
			sb.append("  <DCA_DAY></DCA_DAY>                          \n");
			sb.append("  <FUND_OPT_INF>                               \n");
			sb.append("    <FUND_OPTION_KEY></FUND_OPTION_KEY>        \n");
			sb.append("    <FUND_OPTION_VAL></FUND_OPTION_VAL>        \n");
			sb.append("  </FUND_OPT_INF>                              \n");
			sb.append("  <ANTY_CL_TP></ANTY_CL_TP>                    \n");
			sb.append("  <MULTI_ANTY_INF>                             \n");
			sb.append("    <MULTI_CL_TP></MULTI_CL_TP>                \n");
			sb.append("    <ANTY_CL_RAT></ANTY_CL_RAT>                \n");
			sb.append("    <ANST_AGE></ANST_AGE>                      \n");
			sb.append("    <ANTY_CL_PRD></ANTY_CL_PRD>                \n");
			sb.append("    <ANTY_CL_CYL></ANTY_CL_CYL>                \n");
			sb.append("    <MIN_GUR_PRD></MIN_GUR_PRD>                \n");
			sb.append("    <GINCR_TP></GINCR_TP>                      \n");
			sb.append("    <GINCR_PRD></GINCR_PRD>                    \n");
			sb.append("    <GINCR_VALUE></GINCR_VALUE>                \n");
			sb.append("  </MULTI_ANTY_INF>                            \n");
			sb.append("  <IMRC_GB>3</IMRC_GB>                         \n");
			sb.append("  <TOT_PRM></TOT_PRM>                          \n");
			sb.append("  <RPRM></RPRM>                                \n");
			sb.append("  <PM_CYL>9</PM_CYL>                           \n");
			sb.append("  <APRM_INF>                                   \n");
			sb.append("    <APRM_NTS></APRM_NTS>                      \n");
			sb.append("    <APRM_PRD></APRM_PRD>                      \n");
			sb.append("    <APRM></APRM>                              \n");
			sb.append("  </APRM_INF>                                  \n");
			sb.append("  <IPRM_NTS></IPRM_NTS>                        \n");
			sb.append("  <IPRM></IPRM>                                \n");
			sb.append("  <FREE_CONT_AMT></FREE_CONT_AMT>              \n");
			sb.append("  <FREE_PRM></FREE_PRM>                        \n");
			sb.append("  <PM_INF>                                     \n");
			sb.append("    <PM_DESC></PM_DESC>                        \n");
			sb.append("    <PM_AMOUNT></PM_AMOUNT>                    \n");
			sb.append("  </PM_INF>                                    \n");
			sb.append("  <HUNM_INF>                                   \n");
			sb.append("    <CUST_CONT_RTP>11</CUST_CONT_RTP>          \n");
			sb.append("    <CUST_NM>������</CUST_NM>                  \n");
			sb.append("    <SSN>8409302222222</SSN>                   \n");
			sb.append("    <AGE/>                                     \n");
			sb.append("    <ISD_REL>01</ISD_REL>                      \n");
			sb.append("    <JOB_CD></JOB_CD>                          \n");
			sb.append("    <CAR_KND></CAR_KND>                        \n");
			sb.append("    <RSK_GD/>                                  \n");
			sb.append("    <SMOK_YN></SMOK_YN>                        \n");
			sb.append("    <DALY_SMOK_QNTY></DALY_SMOK_QNTY>          \n");
			sb.append("    <SMOK_TERM></SMOK_TERM>                    \n");
			sb.append("    <HGHT></HGHT>                              \n");
			sb.append("    <WGHT></WGHT>                              \n");
			sb.append("  </HUNM_INF>                                  \n");
			sb.append("  <HUNM_INF>                                   \n");
			sb.append("    <CUST_CONT_RTP>21</CUST_CONT_RTP>          \n");
			sb.append("    <CUST_NM>�̼Ҷ�</CUST_NM>                  \n");
			sb.append("    <SSN>7910122222222</SSN>                   \n");
			sb.append("    <AGE/>                                     \n");
			sb.append("    <ISD_REL>08</ISD_REL>                      \n");
			sb.append("    <JOB_CD>11321</JOB_CD>                     \n");
			sb.append("    <CAR_KND>5</CAR_KND>                       \n");
			sb.append("    <RSK_GD/>                                  \n");
			sb.append("    <SMOK_YN></SMOK_YN>                        \n");
			sb.append("    <DALY_SMOK_QNTY></DALY_SMOK_QNTY>          \n");
			sb.append("    <SMOK_TERM></SMOK_TERM>                    \n");
			sb.append("    <HGHT></HGHT>                              \n");
			sb.append("    <WGHT></WGHT>                              \n");
			sb.append("  </HUNM_INF>                                  \n");
			sb.append("  <IITEM>1718035</IITEM>                       \n");
			sb.append("  <INM/>                                       \n");
			sb.append("  <CONT_AMT></CONT_AMT>                        \n");
			sb.append("  <PRM>111111111</PRM>                         \n");
			sb.append("  <INPRD_TP>01</INPRD_TP>                      \n");
			sb.append("  <INPRD_TP_VAL>7</INPRD_TP_VAL>               \n");
			sb.append("  <PMPRD_TP>01</PMPRD_TP>                      \n");
			sb.append("  <PMPRD_TP_VAL>000</PMPRD_TP_VAL>             \n");
			sb.append("  <COVERAGE_NOTICE_CD/>                        \n");
			sb.append("  <INSU_INF>                                   \n");
			sb.append("    <R_IITEM></R_IITEM>                        \n");
			sb.append("    <R_INM></R_INM>                            \n");
			sb.append("    <R_CONT_AMT></R_CONT_AMT>                  \n");
			sb.append("    <R_PRM></R_PRM>                            \n");
			sb.append("    <R_INPRD_TP></R_INPRD_TP>                  \n");
			sb.append("    <R_INPRD_TP_VAL></R_INPRD_TP_VAL>          \n");
			sb.append("    <R_PMPRD_TP></R_PMPRD_TP>                  \n");
			sb.append("    <R_PMPRD_TP_VAL></R_PMPRD_TP_VAL>          \n");
			sb.append("    <R_BENE_INF>                               \n");
			sb.append("      <R_NAME></R_NAME>                        \n");
			sb.append("      <R_CAUSE_CD></R_CAUSE_CD>                \n");
			sb.append("      <R_DEPTH></R_DEPTH>                      \n");
			sb.append("      <R_VAL_INF>                              \n");
			sb.append("        <R_VALUE></R_VALUE>                    \n");
			sb.append("      </R_VAL_INF>                             \n");
			sb.append("    </R_BENE_INF>                              \n");
			sb.append("    <R_INSU_OPT_INF>                           \n");
			sb.append("      <R_INSU_OPTION_KEY></R_INSU_OPTION_KEY>  \n");
			sb.append("      <R_INSU_OPTION_VAL></R_INSU_OPTION_VAL>  \n");
			sb.append("    </R_INSU_OPT_INF>                          \n");
			sb.append("    <R_NOTICE_CD></R_NOTICE_CD>                \n");
			sb.append("    <R_PRM_IDX/>                               \n");
			sb.append("    <R_GEN_INPRD_TP/>                          \n");
			sb.append("    <R_GEN_PMPRD_TP/>                          \n");
			sb.append("    <R_GEN_CONT_AMT/>                          \n");
			sb.append("  </INSU_INF>                                  \n");
			sb.append("  <R_COVERAGE_NOTICE_CD/>                      \n");
			sb.append("  <SURRENDER_NOTICE_CD/>                       \n");
			sb.append("  <ANNU_NOTICE_CD/>                            \n");
			sb.append("  <ACUM_NOTICE_CD/>                            \n");
			sb.append("  <SAVING_NOTICE_CD/>                          \n");
			sb.append("  <PUB_RATE/>                                  \n");
			sb.append("  <PLLN_RATE/>                                 \n");
			sb.append("  <ETC_RATE/>                                  \n");
			sb.append("  <ADM_PLN_NO/>                                \n");
			sb.append("  <CAPITAL_ATTAIN_MONTH/>                      \n");
			sb.append("  <POLY_NOTICE_CD/>                            \n");
			sb.append("  <CRNCY_GB>KRW</CRNCY_GB>                     \n");
			sb.append("  <RTCD/>                                      \n");
			sb.append("  <MSG2000/>                                   \n");
			sb.append("</BusinessArea></Bancassurance>                \n");
		}
		
		String strReqXml = null;
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes("EUC-KR"));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				//strReqSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
				strReqXml = XmlTool.transNodeToXml(document, true, "EUC-KR");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqXml;
	}
	
	///////////////////////////////////////////////////////////////////////
	
	private static String makeReq073100Xml() {
		boolean flag = true;
		
		StringBuffer sb = new StringBuffer();
		
		if (flag) {   // DATE.2013.06.12  : ��������
			sb.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>           \n");
			sb.append("<Bancassurance><HeaderArea>                           \n");
			sb.append("  <HDR_TRX_ID>HWI01</HDR_TRX_ID>                      \n");
			sb.append("  <HDR_SYS_ID>BAS</HDR_SYS_ID>                        \n");
			sb.append("  <HDR_DOC_LEN>7888</HDR_DOC_LEN>                     \n");
			sb.append("  <HDR_DAT_GBN>T</HDR_DAT_GBN>                        \n");
			sb.append("  <HDR_INS_ID>L01</HDR_INS_ID>                        \n");
			sb.append("  <HDR_BAK_CLS>02</HDR_BAK_CLS>                       \n");
			sb.append("  <HDR_BAK_ID>269</HDR_BAK_ID>                        \n");
			sb.append("  <HDR_DOC_CODE>0200</HDR_DOC_CODE>                   \n");
			sb.append("  <HDR_BIZ_CODE>073100</HDR_BIZ_CODE>                 \n");
			sb.append("  <HDR_TRA_FLAG>1</HDR_TRA_FLAG>                      \n");
			sb.append("  <HDR_DOC_STATUS>1</HDR_DOC_STATUS>                  \n");
			sb.append("  <HDR_RET_CODE>000</HDR_RET_CODE>                    \n");
			sb.append("  <HDR_SND_DATE>20130701</HDR_SND_DATE>               \n");
			sb.append("  <HDR_SND_TIME>161635</HDR_SND_TIME>                 \n");
			sb.append("  <HDR_BAK_DOCSEQ>00001047</HDR_BAK_DOCSEQ>           \n");
			sb.append("  <HDR_INS_DOCSEQ></HDR_INS_DOCSEQ>                   \n");
			sb.append("  <HDR_TXN_DATE>20130701</HDR_TXN_DATE>               \n");
			sb.append("  <HDR_TOT_DOC>01</HDR_TOT_DOC>                       \n");
			sb.append("  <HDR_CUR_DOC>01</HDR_CUR_DOC>                       \n");
			sb.append("  <HDR_AGT_CODE>111111111155555</HDR_AGT_CODE>        \n");
			sb.append("  <HDR_BAK_EXT></HDR_BAK_EXT>                         \n");
			sb.append("  <HDR_INS_EXT></HDR_INS_EXT>                         \n");
			sb.append("</HeaderArea><BusinessArea>                           \n");
			sb.append("  <BANK_CD>269</BANK_CD>                              \n");
			sb.append("  <BRAN_CD></BRAN_CD>                                 \n");
			sb.append("  <ISUE_YMD>20130701</ISUE_YMD>                       \n");
			sb.append("  <INSR_CD>L01</INSR_CD>                              \n");
			sb.append("  <PE_CD></PE_CD>                                     \n");
			sb.append("  <PITEM>1718035</PITEM>                              \n");
			sb.append("  <PRE_PM_NTS>0</PRE_PM_NTS>                          \n");
			sb.append("  <PRDIC_AMT></PRDIC_AMT>                             \n");
			sb.append("  <LCYM></LCYM>                                       \n");
			sb.append("  <PLCY_CVY_MTH>2</PLCY_CVY_MTH>                      \n");
			sb.append("  <ETC_PST_RCV>1</ETC_PST_RCV>                        \n");
			sb.append("  <B_CONT_NO></B_CONT_NO>                             \n");
			sb.append("  <APPL_NO></APPL_NO>                                 \n");
			sb.append("  <FTM_PM_METH>30</FTM_PM_METH>                       \n");
			sb.append("  <PM_METH>03</PM_METH>                               \n");
			sb.append("  <PM_CYL>01</PM_CYL>                                 \n");
			sb.append("  <TAXBT_REQ>1</TAXBT_REQ>                            \n");
			sb.append("  <TAXBT_GB></TAXBT_GB>                               \n");
			sb.append("  <IMRC_GB>0</IMRC_GB>                                \n");
			sb.append("  <TOT_PRM>20000000</TOT_PRM>                         \n");
			sb.append("  <DISC_PRM></DISC_PRM>                               \n");
			sb.append("  <EXTR_PRM></EXTR_PRM>                               \n");
			sb.append("  <FREE_CONT_AMT></FREE_CONT_AMT>                     \n");
			sb.append("  <FREE_PRM>0</FREE_PRM>                              \n");
			sb.append("  <EXCE_PRM>0</EXCE_PRM>                              \n");
			sb.append("  <RLPM></RLPM>                                       \n");
			sb.append("  <CONT_YMD>20130628</CONT_YMD>                       \n");
			sb.append("  <CONT_STS></CONT_STS>                               \n");
			sb.append("  <GRP_NO></GRP_NO>                                   \n");
			sb.append("  <OCP_NO></OCP_NO>                                   \n");
			sb.append("  <GRP_NM></GRP_NM>                                   \n");
			sb.append("  <GRP_GB></GRP_GB>                                   \n");
			sb.append("  <ADM_PLN_NO>2013061000006</ADM_PLN_NO>              \n");
			sb.append("  <PITEM_NM></PITEM_NM>                               \n");
			sb.append("  <INSU_INF>                                          \n");
			sb.append("    <IITEM>1718035</IITEM>                            \n");
			sb.append("    <IITEM_GB>1</IITEM_GB>                            \n");
			sb.append("    <CONT_AMT/>                                       \n");
			sb.append("    <INM></INM>                                       \n");
			sb.append("    <INPRD_TP>01</INPRD_TP>                           \n");
			sb.append("    <INPRD_TP_VAL>7</INPRD_TP_VAL>                    \n");
			sb.append("    <PMPRD_TP>000</PMPRD_TP>                          \n");
			sb.append("    <PMPRD_TP_VAL>1</PMPRD_TP_VAL>                    \n");
			sb.append("    <PRM>1111111</PRM>                                \n");
			sb.append("    <INSU_OPT_INF>                                    \n");
			sb.append("      <INSU_OPTION_KEY></INSU_OPTION_KEY>             \n");
			sb.append("      <INSU_OPTION_VAL></INSU_OPTION_VAL>             \n");
			sb.append("    </INSU_OPT_INF>                                   \n");
			sb.append("  </INSU_INF>                                         \n");
			sb.append("  <HUNM_INF>                                          \n");
			sb.append("    <CUST_CONT_RTP>11</CUST_CONT_RTP>                 \n");
			sb.append("    <CUST_SORT_CD>1</CUST_SORT_CD>                    \n");
			sb.append("    <CUST_NM>������</CUST_NM>                         \n");
			sb.append("    <SSN>8409302222222</SSN>                          \n");
			sb.append("    <HUNM_AGE></HUNM_AGE>                             \n");
			sb.append("    <DIAG_YN></DIAG_YN>                               \n");
			sb.append("    <DIAG_LVL></DIAG_LVL>                             \n");
			sb.append("    <DIAG_METH></DIAG_METH>                           \n");
			sb.append("    <GENL_GRDE></GENL_GRDE>                           \n");
			sb.append("    <INJU_GRDE></INJU_GRDE>                           \n");
			sb.append("    <ISD_REL>02</ISD_REL>                             \n");
			sb.append("    <DISABLED_GB>0</DISABLED_GB>                      \n");
			sb.append("    <JOB_CD>22212</JOB_CD>                            \n");
			sb.append("    <CAR_KND>2</CAR_KND>                              \n");
			sb.append("    <DRV_YN>Y</DRV_YN>                                \n");
			sb.append("    <CARKND_CODE>1</CARKND_CODE>                      \n");
			sb.append("    <HOB_CODE>01</HOB_CODE>                           \n");
			sb.append("    <HGHT>170</HGHT>                                  \n");
			sb.append("    <WGHT>60</WGHT>                                   \n");
			sb.append("    <WEKY_DRNK_CNT>1</WEKY_DRNK_CNT>                  \n");
			sb.append("    <T01_DRNK_QNTY>1</T01_DRNK_QNTY>                  \n");
			sb.append("    <SMOK_YN>1</SMOK_YN>                              \n");
			sb.append("    <DALY_SMOK_QNTY>10</DALY_SMOK_QNTY>               \n");
			sb.append("    <SMOK_TERM>5</SMOK_TERM>                          \n");
			sb.append("    <OVS_ARE>�Ϻ�</OVS_ARE>                           \n");
			sb.append("    <OVS_OBJ>����</OVS_OBJ>                           \n");
			sb.append("    <OVS_PRD>2013070120130707</OVS_PRD>               \n");
			sb.append("    <SDJB_NAME>����</SDJB_NAME>                       \n");
			sb.append("    <OTCM_INSR_JNCO>3</OTCM_INSR_JNCO>                \n");
			sb.append("    <OTCM_INSR_JNCT>8</OTCM_INSR_JNCT>                \n");
			sb.append("    <OTCM_INSR_JNPM>45</OTCM_INSR_JNPM>               \n");
			sb.append("    <YEAR_INCM_AMT/>                                  \n");
			sb.append("    <CTOR_DWEL_TPDV></CTOR_DWEL_TPDV>                 \n");
			sb.append("    <CO_NM/>                                          \n");
			sb.append("    <DEPT_NM/>                                        \n");
			sb.append("    <EMAIL_ID>haha@naver.com</EMAIL_ID>               \n");
			sb.append("    <CUST_SORT_CD>1</CUST_SORT_CD>                    \n");
			sb.append("    <CUST_NM>��ȣ��</CUST_NM>                         \n");
			sb.append("    <SSN>7908092222222</SSN>                          \n");
			sb.append("    <HUNM_AGE></HUNM_AGE>                             \n");
			sb.append("    <DIAG_YN></DIAG_YN>                               \n");
			sb.append("    <DIAG_LVL></DIAG_LVL>                             \n");
			sb.append("    <DIAG_METH></DIAG_METH>                           \n");
			sb.append("    <GENL_GRDE></GENL_GRDE>                           \n");
			sb.append("    <INJU_GRDE></INJU_GRDE>                           \n");
			sb.append("    <ISD_REL>01</ISD_REL>                             \n");
			sb.append("    <DISABLED_GB>0</DISABLED_GB>                      \n");
			sb.append("    <JOB_CD>31521</JOB_CD>                            \n");
			sb.append("    <CAR_KND>3</CAR_KND>                              \n");
			sb.append("    <DRV_YN>Y</DRV_YN>                                \n");
			sb.append("    <CARKND_CODE>1</CARKND_CODE>                      \n");
			sb.append("    <HOB_CODE>01</HOB_CODE>                           \n");
			sb.append("    <HGHT>170</HGHT>                                  \n");
			sb.append("    <WGHT>60</WGHT>                                   \n");
			sb.append("    <WEKY_DRNK_CNT>1</WEKY_DRNK_CNT>                  \n");
			sb.append("    <T01_DRNK_QNTY>1</T01_DRNK_QNTY>                  \n");
			sb.append("    <SMOK_YN>1</SMOK_YN>                              \n");
			sb.append("    <DALY_SMOK_QNTY>10</DALY_SMOK_QNTY>               \n");
			sb.append("    <SMOK_TERM>5</SMOK_TERM>                          \n");
			sb.append("    <OVS_ARE>�Ϻ�</OVS_ARE>                           \n");
			sb.append("    <OVS_OBJ>����</OVS_OBJ>                           \n");
			sb.append("    <OVS_PRD>2013070120130707</OVS_PRD>               \n");
			sb.append("    <SDJB_NAME>����</SDJB_NAME>                       \n");
			sb.append("    <OTCM_INSR_JNCO>3</OTCM_INSR_JNCO>                \n");
			sb.append("    <OTCM_INSR_JNCT>8</OTCM_INSR_JNCT>                \n");
			sb.append("    <OTCM_INSR_JNPM>45</OTCM_INSR_JNPM>               \n");
			sb.append("    <YEAR_INCM_AMT/>                                  \n");
			sb.append("    <CTOR_DWEL_TPDV></CTOR_DWEL_TPDV>                 \n");
			sb.append("    <CO_NM/>                                          \n");
			sb.append("    <DEPT_NM/>                                        \n");
			sb.append("    <EMAIL_ID>haha@naver.com</EMAIL_ID>               \n");
			sb.append("    <EMAIL_SERV_YN>1</EMAIL_SERV_YN>                  \n");
			sb.append("    <SMS_SERV_YN>1</SMS_SERV_YN>                      \n");
			sb.append("    <HP_CNTY></HP_CNTY>                               \n");
			sb.append("    <HP_COM>010</HP_COM>                              \n");
			sb.append("    <HP_GUK>333</HP_GUK>                              \n");
			sb.append("    <HP_NO>5555</HP_NO>                               \n");
			sb.append("    <ADDR_INF>                                        \n");
			sb.append("      <ADDR_GB>1</ADDR_GB>                            \n");
			sb.append("      <PST_NO1>222</PST_NO1>                          \n");
			sb.append("      <PST_NO2>234</PST_NO2>                          \n");
			sb.append("      <PST_ADDR>����� ������ ������</PST_ADDR>       \n");
			sb.append("      <ETC_ADDR>��������Ʈ 202-515</ETC_ADDR>         \n");
			sb.append("      <TEL_CNTY></TEL_CNTY>                           \n");
			sb.append("      <TEL_DDD>02</TEL_DDD>                           \n");
			sb.append("      <TEL_GUK>555</TEL_GUK>                          \n");
			sb.append("      <TEL_NO>8888</TEL_NO>                           \n");
			sb.append("    </ADDR_INF>                                       \n");
			sb.append("    <ADDR_INF>                                        \n");
			sb.append("      <ADDR_GB>1</ADDR_GB>                            \n");
			sb.append("      <PST_NO1>333</PST_NO1>                          \n");
			sb.append("      <PST_NO2>555</PST_NO2>                          \n");
			sb.append("      <PST_ADDR>����� �������� ���ǵ���</PST_ADDR>   \n");
			sb.append("      <ETC_ADDR>55</ETC_ADDR>                         \n");
			sb.append("      <TEL_CNTY></TEL_CNTY>                           \n");
			sb.append("      <TEL_DDD>02</TEL_DDD>                           \n");
			sb.append("      <TEL_GUK>666</TEL_GUK>                          \n");
			sb.append("      <TEL_NO>7777</TEL_NO>                           \n");
			sb.append("    </ADDR_INF>                                       \n");
			sb.append("  </HUNM_INF>                                         \n");
			sb.append("  <BNFR_INF>                                          \n");
			sb.append("    <CUST_CONT_RTP_BNFR>36</CUST_CONT_RTP_BNFR>       \n");
			sb.append("    <CUST_BNFR_SORT_CD/>                              \n");
			sb.append("    <CUST_NM_BNFR/>                                   \n");
			sb.append("    <SSN_BNFR/>                                       \n");
			sb.append("    <BNFR_AGE></BNFR_AGE>                             \n");
			sb.append("    <ISD_REL_BNFR>11</ISD_REL_BNFR>                   \n");
			sb.append("    <SHR_RTO_BNFR>100</SHR_RTO_BNFR>                  \n");
			sb.append("  </BNFR_INF>                                         \n");
			sb.append("  <ANTY_CL_TP></ANTY_CL_TP>                           \n");
			sb.append("  <MULTI_ANTY_INF>                                    \n");
			sb.append("    <MULTI_CL_TP></MULTI_CL_TP>                       \n");
			sb.append("    <ANTY_CL_RAT></ANTY_CL_RAT>                       \n");
			sb.append("    <ANST_AGE></ANST_AGE>                             \n");
			sb.append("    <ANTY_CL_PRD></ANTY_CL_PRD>                       \n");
			sb.append("    <ANTY_CL_CYL></ANTY_CL_CYL>                       \n");
			sb.append("    <MIN_GUR_PRD></MIN_GUR_PRD>                       \n");
			sb.append("    <GINCR_TP></GINCR_TP>                             \n");
			sb.append("    <GINCR_PRD></GINCR_PRD>                           \n");
			sb.append("    <GINCR_VALUE></GINCR_VALUE>                       \n");
			sb.append("  </MULTI_ANTY_INF>                                   \n");
			sb.append("  <FUND_INF>                                          \n");
			sb.append("    <FUND_PRM_GB></FUND_PRM_GB>                       \n");
			sb.append("    <FUND_SH_RAT></FUND_SH_RAT>                       \n");
			sb.append("    <VARI_FUND_TP></VARI_FUND_TP>                     \n");
			sb.append("    <FUND_SPCL_OPT_INF>                               \n");
			sb.append("      <FUND_SPCL_OPTION_KEY></FUND_SPCL_OPTION_KEY>   \n");
			sb.append("      <FUND_SPCL_OPTION_VAL></FUND_SPCL_OPTION_VAL>   \n");
			sb.append("    </FUND_SPCL_OPT_INF>                              \n");
			sb.append("  </FUND_INF>                                         \n");
			sb.append("  <FUND_DIST_GB></FUND_DIST_GB>                       \n");
			sb.append("  <FUND_DIST_CYL></FUND_DIST_CYL>                     \n");
			sb.append("  <FUND_DIST_DAY></FUND_DIST_DAY>                     \n");
			sb.append("  <FUND_DIST_NEXTYMD></FUND_DIST_NEXTYMD>             \n");
			sb.append("  <DCA_GB></DCA_GB>                                   \n");
			sb.append("  <DCA_CYL></DCA_CYL>                                 \n");
			sb.append("  <DCA_DAY></DCA_DAY>                                 \n");
			sb.append("  <FUND_OPT_INF>                                      \n");
			sb.append("    <FUND_OPTION_KEY></FUND_OPTION_KEY>               \n");
			sb.append("    <FUND_OPTION_VAL></FUND_OPTION_VAL>               \n");
			sb.append("  </FUND_OPT_INF>                                     \n");
			sb.append("  <ACNO_INF>                                          \n");
			sb.append("    <AP_ACNO_GB>10</AP_ACNO_GB>                       \n");
			sb.append("    <ACNO>1111111</ACNO>                              \n");
			sb.append("    <TR_BANK_CD>269</TR_BANK_CD>                      \n");
			sb.append("    <DEPOR_NM>������</DEPOR_NM>                       \n");
			sb.append("    <DEPOR_SSN>8409302222222</DEPOR_SSN>              \n");
			sb.append("    <REL>01</REL>                                     \n");
			sb.append("    <TRSF_HOP_DD/>                                    \n");
			sb.append("    <DIV_RIMI_YN/>                                    \n");
			sb.append("  </ACNO_INF>                                         \n");
			sb.append("  <ACNO_INF>                                          \n");
			sb.append("    <AP_ACNO_GB>11</AP_ACNO_GB>                       \n");
			sb.append("    <ACNO>1111111</ACNO>                              \n");
			sb.append("    <TR_BANK_CD>269</TR_BANK_CD>                      \n");
			sb.append("    <DEPOR_NM>������</DEPOR_NM>                       \n");
			sb.append("    <DEPOR_SSN>8409302222222</DEPOR_SSN>              \n");
			sb.append("    <REL>01</REL>                                     \n");
			sb.append("    <TRSF_HOP_DD>17</TRSF_HOP_DD>                     \n");
			sb.append("    <DIV_RIMI_YN/>                                    \n");
			sb.append("  </ACNO_INF>                                         \n");
			sb.append("  <PRT_INF>                                           \n");
			sb.append("    <INSU_PRT></INSU_PRT>                             \n");
			sb.append("  </PRT_INF>                                          \n");
			sb.append("  <OPT_INF>                                           \n");
			sb.append("    <OPTION_KEY></OPTION_KEY>                         \n");
			sb.append("    <OPTION_VAL></OPTION_VAL>                         \n");
			sb.append("  </OPT_INF>                                          \n");
			sb.append("  <CRNCY_GB>KRW</CRNCY_GB>                            \n");
			sb.append("  <RTCD></RTCD>                                       \n");
			sb.append("  <MSG2000></MSG2000>                                 \n");
			sb.append("</BusinessArea></Bancassurance>                       \n");
		}
		
		String strReqXml = null;
		
		if (flag) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes("EUC-KR"));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);
				bais.close();
				
				// TO DO : HDR_BAK_DOCSEQ ���� ����
				String strDocSeq = String.format("99%06d", SeqGender.getInstance().getInt());
				System.out.println("[DOCSEQ=" + strDocSeq + "]");
				document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).setTextContent(strDocSeq);
				
				//strReqSampleXml = XmlTool.transNodeToXml(document, false, strCharset);
				strReqXml = XmlTool.transNodeToXml(document, true, "EUC-KR");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strReqXml;
	}
	
	/////////////////////////////////////////////////////////////////

	public static String getReqXml() {
		return makeReqSampleXml();
	}

	public static String getReqXml(String depNo) {
		return makeReqSampleXml(depNo);
	}

	public static String getReqXml(String fepId, String depNo) {
		return makeReqSampleXml(fepId, depNo);
	}

	public static String getResXml() {
		return makeResSampleXml();
	}
	
	public static String getStreamXml() {
		return makeStreamXml();
	}
	
	/////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		boolean flag = true;
		
		if (!flag) {
			// ���� XML�� ��´�. �迭�� ���Ե�
			System.out.println("SoapXML   REQ [" + Sample01Xml.getReqXml() + "]");
			System.out.println("-------------------------------");
			System.out.println("SoapXML   RES [" + Sample01Xml.getResXml() + "]");
			System.out.println("-------------------------------");
			System.out.println("StreamXML RES [" + Sample01Xml.getStreamXml() + "]");
			System.out.println("-------------------------------");
			
			//return;
		}
		
		if (!flag) {
			// String, byte[] XML ���ڿ��� document �� �����.
			try {
				String strXml = "<?xml version=\"1.0\" encoding=\"" + strCharset + "\"?><Test><item>aaa</item><item>bbb</item></Test>";
				strXml = "<Test><item>aaa</item><item>bbb</item></Test>";
				
				byte[] byteXml = strXml.getBytes(strCharset);
				ByteArrayInputStream bais = new ByteArrayInputStream(byteXml);
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(bais);

				Element rootElement = document.getDocumentElement();
				
				System.out.println(">" + rootElement.getTextContent());
				
				bais.close();
				System.out.println("----------------------------------------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!flag) {
			try {
				String strMainXml = Sample01Xml.getReqXml();
				ByteArrayInputStream bais = new ByteArrayInputStream(strMainXml.getBytes(strCharset));
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				// Document document = builder.newDocument();
				Document document = builder.parse(bais);
				bais.close();
				
				System.out.println("-----------------------------------------------");
				Element tagBancassurance = document.getDocumentElement();
				System.out.println(">" + tagBancassurance.getTextContent());

				System.out.println("-----------------------------------------------");
				Node tagHeaderArea = document.getElementsByTagName("HeaderArea").item(0);
				System.out.println(">" + tagHeaderArea.getTextContent());

				System.out.println("-----------------------------------------------");
				Node tagBusinessArea = document.getElementsByTagName("BusinessArea").item(0);
				System.out.println(">" + tagBusinessArea.getTextContent());
				System.out.println("-----------------------------------------------");

				// remove header from document
				tagBancassurance.removeChild(tagHeaderArea);

				if (flag) {
					// display tabBancassurance
					
					// transformer
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer();
					// transformer.setOutputProperty("indent", "yes");

					// source document
					DOMSource domSource = new DOMSource(tagBancassurance);

					// target document
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					StreamResult streamResult = new StreamResult(byteStream);

					// do the transformer
					transformer.transform(domSource, streamResult);
					
					byte[] byteXml = byteStream.toByteArray();
					//String strXml = byteStream.toString();
					
					System.out.println("size > " + byteStream.size());
					System.out.println("[" + new String(byteXml) + "]");
					System.out.println("[" + XmlTool.transNodeToXml(tagBancassurance, true, strCharset) + "]");
					
					byteStream.close();

					System.out.println("-----------------------------------------------");
				}
				
				if (flag) {
					// display tagHeaderArea
					
					// transformer
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer();
					// transformer.setOutputProperty("indent", "yes");

					// source document
					DOMSource domSource = new DOMSource(tagHeaderArea);

					// target document
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					StreamResult streamResult = new StreamResult(byteStream);

					// do the transformer
					transformer.transform(domSource, streamResult);
					
					byte[] byteXml = byteStream.toByteArray();
					//String strXml = byteStream.toString();
					
					System.out.println("size > " + byteStream.size());
					System.out.println("[" + new String(byteXml) + "]");
					System.out.println("[" + XmlTool.transNodeToXml(tagHeaderArea, true, strCharset) + "]");
					
					byteStream.close();

					System.out.println("-----------------------------------------------");
				}
				
				if (!flag) {
					// display tagBusinessArea
					
					// transformer
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer();
					// transformer.setOutputProperty("indent", "yes");

					// source document
					DOMSource domSource = new DOMSource(tagBusinessArea);

					// target document
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					StreamResult streamResult = new StreamResult(byteStream);

					// do the transformer
					transformer.transform(domSource, streamResult);
					
					byte[] byteXml = byteStream.toByteArray();
					//String strXml = byteStream.toString();
					
					System.out.println("size > " + byteStream.size());
					System.out.println("[" + new String(byteXml) + "]");
					System.out.println("[" + XmlTool.transNodeToXml(tagBusinessArea, true, strCharset) + "]");
					
					byteStream.close();

					System.out.println("-----------------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!flag) {
			try {
				String strMainXml = Sample01Xml.getReqXml();
				ByteArrayInputStream bais = new ByteArrayInputStream(strMainXml.getBytes(strCharset));
						
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				Document document = builder.parse(bais);

				System.out.println("[" + document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent() + "]");  // TRX ID          [ 9] S   : ������
				System.out.println("[" + document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent() + "]");  // SYSTEM ID       [ 3] S   : BAS
				System.out.println("[" + document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent() + "]");  // ��ü��������    [ 5] S R : ��ü����
				System.out.println("[" + document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent() + "]");  // �ڷᱸ��        [ 1] S   : T:Test, R:real
				System.out.println("[" + document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent() + "]");  // ����� ID       [ 3] S R : ������ڵ�(�����ڵ�1033����)
				System.out.println("[" + document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent() + "]");  // ���� ����       [ 2] S   : �������Ǳ���(�����ڵ�1022����)
				System.out.println("[" + document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent() + "]");  // ���� ID         [ 3] S R : �����ڵ�(�����ڵ�1024����)
				System.out.println("[" + document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent() + "]");  // �������� �ڵ�   [ 4] S R : ���������ڵ�(����LIST����)
				System.out.println("[" + document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent() + "]");  // �ŷ����� �ڵ�   [ 6] S   : �ŷ������ڵ�(����LIST����)
				System.out.println("[" + document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent() + "]");  // �ۼ��� FLAG     [ 1] S R : ���� <-> ����(->1, <- 5),����<->����(->2, <-6)
				System.out.println("[" + document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent() + "]");  // STATUS          [ 3] S R : ����-�÷�ID(������׸�)
				System.out.println("[" + document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent() + "]");  // �����ڵ�        [ 3] S R : �����ڵ�(����ο����ø����)
				System.out.println("[" + document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent() + "]");  // ����������      [ 8] S R : YYYYMMDD
				System.out.println("[" + document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent() + "]");  // �������۽ð�    [ 6] S R : HHMMSS
				System.out.println("[" + document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent() + "]");  // ���� ������ȣ   [ 8] S   : �����Ϸù�ȣ
				System.out.println("[" + document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent() + "]");  // ����� ������ȣ [ 8]   R : �����Ϸù�ȣ
				System.out.println("[" + document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent() + "]");  // �ŷ��߻���      [ 8] S R : YYYYMMDD
				System.out.println("[" + document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent() + "]");  // ��ü ���� ��    [ 2] S   : ��ü����:01
				System.out.println("[" + document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent() + "]");  // ������������    [ 2] S   : ��������:01
				System.out.println("[" + document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent() + "]");  // ó����/�ܸ���ȣ [15] S   : ó���� ���/ �ܸ���ȣ (���10byte,�ܸ���ȣ5byte)
				System.out.println("[" + document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent() + "]");  // ���� �߰�����   [30] S R : FILLER
				System.out.println("[" + document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent() + "]");  // ����� �߰����� [30] S R : FILLER
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!flag) {
			try {
				String strMainXml = Sample01Xml.getReqXml();
				ByteArrayInputStream bais = new ByteArrayInputStream(strMainXml.getBytes(strCharset));
						
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				Document document = builder.parse(bais);
				
				System.out.println("-----------------------------------------------");
				System.out.println("HDR_TRX_ID    : TRX ID          [ 9] S   :[" + document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent() + "]");  // TRX ID          [ 9] S   : ������
				System.out.println("HDR_SYS_ID    : SYSTEM ID       [ 3] S   :[" + document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent() + "]");  // SYSTEM ID       [ 3] S   : BAS
				System.out.println("HDR_DOC_LEN   : ��ü��������    [ 5] S R :[" + document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent() + "]");  // ��ü��������    [ 5] S R : ��ü����
				System.out.println("HDR_DAT_GBN   : �ڷᱸ��        [ 1] S   :[" + document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent() + "]");  // �ڷᱸ��        [ 1] S   : T:Test, R:real
				System.out.println("HDR_INS_ID    : ����� ID       [ 3] S R :[" + document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent() + "]");  // ����� ID       [ 3] S R : ������ڵ�(�����ڵ�1033����)
				System.out.println("HDR_BAK_CLS   : ���� ����       [ 2] S   :[" + document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent() + "]");  // ���� ����       [ 2] S   : �������Ǳ���(�����ڵ�1022����)
				System.out.println("HDR_BAK_ID    : ���� ID         [ 3] S R :[" + document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent() + "]");  // ���� ID         [ 3] S R : �����ڵ�(�����ڵ�1024����)
				System.out.println("HDR_DOC_CODE  : �������� �ڵ�   [ 4] S R :[" + document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent() + "]");  // �������� �ڵ�   [ 4] S R : ���������ڵ�(����LIST����)
				System.out.println("HDR_BIZ_CODE  : �ŷ����� �ڵ�   [ 6] S   :[" + document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent() + "]");  // �ŷ����� �ڵ�   [ 6] S   : �ŷ������ڵ�(����LIST����)
				System.out.println("HDR_TRA_FLAG  : �ۼ��� FLAG     [ 1] S R :[" + document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent() + "]");  // �ۼ��� FLAG     [ 1] S R : ���� <-> ����(->1, <- 5),����<->����(->2, <-6)
				System.out.println("HDR_DOC_STATUS: STATUS          [ 3] S R :[" + document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent() + "]");  // STATUS          [ 3] S R : ����-�÷�ID(������׸�)
				System.out.println("HDR_RET_CODE  : �����ڵ�        [ 3] S R :[" + document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent() + "]");  // �����ڵ�        [ 3] S R : �����ڵ�(����ο����ø����)
				System.out.println("HDR_SND_DATE  : ����������      [ 8] S R :[" + document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent() + "]");  // ����������      [ 8] S R : YYYYMMDD
				System.out.println("HDR_SND_TIME  : �������۽ð�    [ 6] S R :[" + document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent() + "]");  // �������۽ð�    [ 6] S R : HHMMSS
				System.out.println("HDR_BAK_DOCSEQ: ���� ������ȣ   [ 8] S   :[" + document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent() + "]");  // ���� ������ȣ   [ 8] S   : �����Ϸù�ȣ
				System.out.println("HDR_INS_DOCSEQ: ����� ������ȣ [ 8]   R :[" + document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent() + "]");  // ����� ������ȣ [ 8]   R : �����Ϸù�ȣ
				System.out.println("HDR_TXN_DATE  : �ŷ��߻���      [ 8] S R :[" + document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent() + "]");  // �ŷ��߻���      [ 8] S R : YYYYMMDD
				System.out.println("HDR_TOT_DOC   : ��ü ���� ��    [ 2] S   :[" + document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent() + "]");  // ��ü ���� ��    [ 2] S   : ��ü����:01
				System.out.println("HDR_CUR_DOC   : ������������    [ 2] S   :[" + document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent() + "]");  // ������������    [ 2] S   : ��������:01
				System.out.println("HDR_AGT_CODE  : ó����/�ܸ���ȣ [15] S   :[" + document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent() + "]");  // ó����/�ܸ���ȣ [15] S   : ó���� ���/ �ܸ���ȣ (���10byte,�ܸ���ȣ5byte)
				System.out.println("HDR_BAK_EXT   : ���� �߰�����   [30] S R :[" + document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent() + "]");  // ���� �߰�����   [30] S R : FILLER
				System.out.println("HDR_INS_EXT   : ����� �߰����� [30] S R :[" + document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent() + "]");  // ����� �߰����� [30] S R : FILLER

				System.out.println("-----------------------------------------------");
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				
				ps.printf("%-9.9s%-3.3s%05d%-1.1s%-3.3s%-2.2s%-3.3s%-4.4s%-6.6s%-1.1s%03d%-3.3s%-8.8s%-6.6s%-8.8s%-8.8s%-8.8s%-2.2s%-2.2s%-15.15s%-30.30s%-30.30s"
						, document.getElementsByTagName("HDR_TRX_ID"    ).item(0).getTextContent()                    // TRX ID          [ 9] S   : ������
						, document.getElementsByTagName("HDR_SYS_ID"    ).item(0).getTextContent()                    // SYSTEM ID       [ 3] S   : BAS
						, Integer.parseInt(document.getElementsByTagName("HDR_DOC_LEN"   ).item(0).getTextContent())  // ��ü��������    [ 5] S R : ��ü����  TO DO : 00000
						, document.getElementsByTagName("HDR_DAT_GBN"   ).item(0).getTextContent()                    // �ڷᱸ��        [ 1] S   : T:Test, R:real
						, document.getElementsByTagName("HDR_INS_ID"    ).item(0).getTextContent()                    // ����� ID       [ 3] S R : ������ڵ�(�����ڵ�1033����)
						, document.getElementsByTagName("HDR_BAK_CLS"   ).item(0).getTextContent()                    // ���� ����       [ 2] S   : �������Ǳ���(�����ڵ�1022����)
						, document.getElementsByTagName("HDR_BAK_ID"    ).item(0).getTextContent()                    // ���� ID         [ 3] S R : �����ڵ�(�����ڵ�1024����)
						, document.getElementsByTagName("HDR_DOC_CODE"  ).item(0).getTextContent()                    // �������� �ڵ�   [ 4] S R : ���������ڵ�(����LIST����)
						, document.getElementsByTagName("HDR_BIZ_CODE"  ).item(0).getTextContent()                    // �ŷ����� �ڵ�   [ 6] S   : �ŷ������ڵ�(����LIST����)
						, document.getElementsByTagName("HDR_TRA_FLAG"  ).item(0).getTextContent()                    // �ۼ��� FLAG     [ 1] S R : ���� <-> ����(->1, <- 5),����<->����(->2, <-6)
						, Integer.parseInt(document.getElementsByTagName("HDR_DOC_STATUS").item(0).getTextContent())  // STATUS          [ 3] S R : ����-�÷�ID(������׸�)
						, document.getElementsByTagName("HDR_RET_CODE"  ).item(0).getTextContent()                    // �����ڵ�        [ 3] S R : �����ڵ�(����ο����ø����)
						, document.getElementsByTagName("HDR_SND_DATE"  ).item(0).getTextContent()                    // ����������      [ 8] S R : YYYYMMDD
						, document.getElementsByTagName("HDR_SND_TIME"  ).item(0).getTextContent()                    // �������۽ð�    [ 6] S R : HHMMSS
						, document.getElementsByTagName("HDR_BAK_DOCSEQ").item(0).getTextContent()                    // ���� ������ȣ   [ 8] S   : �����Ϸù�ȣ
						, document.getElementsByTagName("HDR_INS_DOCSEQ").item(0).getTextContent()                    // ����� ������ȣ [ 8]   R : �����Ϸù�ȣ
						, document.getElementsByTagName("HDR_TXN_DATE"  ).item(0).getTextContent()                    // �ŷ��߻���      [ 8] S R : YYYYMMDD
						, document.getElementsByTagName("HDR_TOT_DOC"   ).item(0).getTextContent()                    // ��ü ���� ��    [ 2] S   : ��ü����:01
						, document.getElementsByTagName("HDR_CUR_DOC"   ).item(0).getTextContent()                    // ������������    [ 2] S   : ��������:01
						, document.getElementsByTagName("HDR_AGT_CODE"  ).item(0).getTextContent()                    // ó����/�ܸ���ȣ [15] S   : ó���� ���/ �ܸ���ȣ (���10byte,�ܸ���ȣ5byte)
						, document.getElementsByTagName("HDR_BAK_EXT"   ).item(0).getTextContent()                    // ���� �߰�����   [30] S R : FILLER
						, document.getElementsByTagName("HDR_INS_EXT"   ).item(0).getTextContent()                    // ����� �߰����� [30] S R : FILLER
						);

				System.out.println("[" + baos.toString() + "]");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {   // TODO DATE.2013.07.01 : ����
			try {
				String strSoapXml = null;
				String strStreamXml = null;
				int iSoapXml = 0;
				int iStreamXml = 0;
				
				strSoapXml = Sample01Xml.makeReq072100Xml();
				strStreamXml = XmlTool.transferSoapXmlToStreamXml(strSoapXml, "EUC-KR");
				iSoapXml = strSoapXml.getBytes("EUC-KR").length;
				iStreamXml = strStreamXml.getBytes("EUC-KR").length;
				
				System.out.println("[" + iSoapXml + "][" + strSoapXml + "]");
				System.out.println("[" + iStreamXml + "][" + strStreamXml + "]");
				
				strSoapXml = Sample01Xml.makeReq073100Xml();
				strStreamXml = XmlTool.transferSoapXmlToStreamXml(strSoapXml, "EUC-KR");
				iSoapXml = strSoapXml.getBytes("EUC-KR").length;
				iStreamXml = strStreamXml.getBytes("EUC-KR").length;
				
				System.out.println("[" + iSoapXml + "][" + strSoapXml + "]");
				System.out.println("[" + iStreamXml + "][" + strStreamXml + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
