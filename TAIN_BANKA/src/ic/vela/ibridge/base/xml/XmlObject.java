package ic.vela.ibridge.base.xml;

import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.util.XmlTool;

/*==================================================================*/
/**
 * XmlObject 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlObject
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class XmlObject 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	@SuppressWarnings("unused")
	private static final long          MILLISEC_DAY        = 24 * 60 * 60 * 1000;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	// this 객체생성한 부모객체(IBridgeServant)의 큐
	private IBridgeQueue               parentQueue         = null;
	
	// 요청전문 / 응답전문
	private String                     strReqSoapXml       = null;  // 요청
	private String                     strResSoapXml       = null;  // 응답
	
	private String                     strReqStreamXml     = null;
	private String                     strResStreamXml     = null;
	
	private String                     strReqCharset       = null;
	private String                     strResCharset       = null;

	// KEY : FEPID(5) + NOSEQ(NO(2) + SEQ(6)) : HWI01 + 22 + 000012
	private String                     strKey              = null;
	
	private String                     strFepid            = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * @param parentQueue
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public XmlObject(IBridgeQueue parentQueue) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * xml 객체를 돌려줄 큐를 세팅한다.
				 */
				this.parentQueue = parentQueue;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing XmlObject constructor");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 요청전문을 세팅한다.
	 * @param strReqSoapXml
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public void setReqSoapXml(String strReqSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * setting strReqSoapXml
				 */
				this.strReqSoapXml = strReqSoapXml;
				this.strReqCharset = XmlTool.getXmlCharset(this.strReqSoapXml);
				
				/*
				 * KEY : FEPID(5) + SEQ(8) 를 구한다.
				 */
				this.strKey = XmlTool.getSoapKey(this.strReqSoapXml, this.strReqCharset);
				
				/*
				 * SoapXml -> StreamXml 변환한다.
				 */
				this.strReqStreamXml = XmlTool.transferSoapXmlToStreamXml(this.strReqSoapXml, this.strReqCharset);
				
				/*
				 * 전문의 FEPID를 구한다.
				 */
				this.strFepid = XmlTool.getSoapFepid(this.strReqSoapXml, this.strReqCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in setting ReqSoapXml");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 요청전문을 세팅한다.
	 * @param strReqSoapXml
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public void setReqStreamXml(String strReqStreamXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * setting strReqSoapXml
				 */
				this.strReqStreamXml = strReqStreamXml;
				this.strReqCharset = XmlTool.getXmlCharset(this.strReqStreamXml);
				/*
				 * KEY : FEPID(5) + SEQ(8) 를 구한다.
				 */
				this.strKey = XmlTool.getStreamKey(this.strReqStreamXml);
				
				/*
				 * StreamXml -> SoapXml 변환한다.
				 */
				this.strReqSoapXml = XmlTool.transferStreamXmlToSoapXml(this.strReqStreamXml, this.strReqCharset);
				
				/*
				 * 전문의 FEPID를 구한다.
				 */
				this.strFepid = XmlTool.getStreamFepid(this.strReqStreamXml);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in setting ReqSoapXml");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 응답전문을 세팅한다.
	 * @param strReqSoapXml
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public void setResSoapXml(String strResSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * setting strReqSoapXml
				 */
				this.strResSoapXml = strResSoapXml;
				this.strResCharset = XmlTool.getXmlCharset(this.strResSoapXml);
				/*
				 * KEY : FEPID(5) + SEQ(8) 를 구한다.
				 */
				this.strKey = XmlTool.getSoapKey(this.strResSoapXml, this.strResCharset);
				
				/*
				 * SoapXml -> StreamXml 변환한다.
				 */
				this.strResStreamXml = XmlTool.transferSoapXmlToStreamXml(this.strResSoapXml, this.strResCharset);
				
				/*
				 * 전문의 FEPID를 구한다.
				 */
				this.strFepid = XmlTool.getSoapFepid(this.strResSoapXml, this.strResCharset);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in setting ReqSoapXml");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 응답전문을 세팅한다.
	 * @param strResSoapXml
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public void setResStreamXml(String strResStreamXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * setting strResSoapXml
				 */
				this.strResStreamXml = strResStreamXml;
				this.strResCharset = XmlTool.getXmlCharset(this.strResStreamXml);

				/*
				 * StreamXml -> SoapXml 변환한다.
				 * TODO DATE.20130820 : 변환없이 처리
				 */
				this.strResSoapXml = XmlTool.transferStreamXmlToSoapXml(this.strResStreamXml, this.strResCharset);
				
				/*
				 * KEY : FEPID(5) + SEQ(8) 를 구한다.
				 * 
				 * TODO DATE.2013.08.15 : &lt; br &gt; 에 의해 XPath 파서 문제가 발생하여 아래와 같이 수정함.
				 */
				if (!flag) {
					this.strKey = XmlTool.getSoapKey(this.strResSoapXml, this.strResCharset);
				} else {
					this.strKey = XmlTool.getStreamKey(this.strResStreamXml);
				}
				
				/*
				 * 전문의 FEPID를 구한다.
				 */
				this.strFepid = XmlTool.getStreamFepid(this.strResStreamXml);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in setting ResSoapXml");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 전문을 검색할 Key 값을 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getKey() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strKey = null;
		
		if (flag) {
			strKey = this.strKey;
		}
		
		return strKey;
	}

	/*==================================================================*/
	/**
	 * FEPID를 얻는다.
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getFepid() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strFepid = null;
		
		if (flag) {
			strFepid = this.strFepid;
		}
		
		return strFepid;
	}

	/*==================================================================*/
	/**
	 * 이 객체생성한 부모객체(IBridgeServant)의 큐를 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public IBridgeQueue getParentQueue() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		IBridgeQueue queue = null;
		
		if (flag) {
			try {
				/*
				 * getting Parent Queue
				 */
				queue = this.parentQueue;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in getting Parent Queue");
			}
		}
		
		return queue;
	}
	
	/*==================================================================*/
	/**
	 * 요청전문을 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getReqSoapXml() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strReqSoapXml = null;
		
		if (flag) {
			try {
				/*
				 * getting Req Soap Xml
				 */
				strReqSoapXml = this.strReqSoapXml;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in getting ReqSoapXml");
			}
		}
		
		return strReqSoapXml;
	}
	
	/*==================================================================*/
	/**
	 * 응답전문을 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getResSoapXml() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strResSoapXml = null;
		
		if (flag) {
			try {
				/*
				 * getting Res Soap Xml
				 */
				strResSoapXml = this.strResSoapXml;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in getting ResSoapXml");
			}
		}
		
		return strResSoapXml;
	}
	
	/*==================================================================*/
	/**
	 * 요청전문을 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getReqStreamXml() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strReqStreamXml = null;
		
		if (flag) {
			try {
				/*
				 * getting Res Soap Xml
				 */
				strReqStreamXml = this.strReqStreamXml;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in getting strReqStreamXml");
			}
		}
		
		return strReqStreamXml;
	}
	
	/*==================================================================*/
	/**
	 * 응답전문을 얻는다.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getResStreamXml() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strResStreamXml = null;
		
		if (flag) {
			try {
				/*
				 * getting Res Soap Xml
				 */
				strResStreamXml = this.strResStreamXml;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in getting strResStreamXml");
			}
		}
		
		return strResStreamXml;
	}
	
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
