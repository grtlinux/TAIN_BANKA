package ic.vela.ibridge.base.xml;

import ic.vela.ibridge.base.queue.IBridgeQueue;

/*==================================================================*/
/**
 * XmlBox Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    XmlBox
 * @author  ����
 * @version 1.0, 2013/06/16
 * @since   jdk1.6.0_45
 * 
 * -
 * 
 */
/*==================================================================*/
public class XmlBox 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private IBridgeQueue servantQueue = null;
	
	@SuppressWarnings("unused")
	private String strFepId  = null;
	private String strSeq    = null;
	private String strReqXml = null;
	private String strResXml = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param servantQueue
	 * @param strFepId
	 * @param strSeq
	 * @param strReqXml
	 */
	/*------------------------------------------------------------------*/
	public XmlBox(
			IBridgeQueue servantQueue,
			String strFepId,
			String strSeq,
			String strReqXml
			)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.servantQueue = servantQueue;
			
			this.strFepId = strFepId;
			this.strSeq = strSeq;
			
			this.strReqXml = strReqXml;
		}
	}
	
	/*==================================================================*/
	/**
	 * �� ������ Ű�� �����Ѵ�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public String getXmlKey()
	{
		boolean flag = true;
		
		String xmlKey = null;
		
		if (flag) {
			/*
			 * TODO : ���߿� FepId ���� HDR_TRX_ID �� ������ �ȴٸ� �����Ѵ�.
			 */
			// xmlKey = this.strFepId + this.strSeq;
			xmlKey = this.strSeq;
		}
		
		return xmlKey;
	}
	
	/*==================================================================*/
	/**
	 * ��û������ ��´�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public String getReqXml()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strReqXml = null;
		
		if (flag) {
			strReqXml = this.strReqXml;
		}
		
		return strReqXml;
	}
	
	/*==================================================================*/
	/**
	 * ���������� �����Ѵ�.
	 * @param strResXml
	 */
	/*------------------------------------------------------------------*/
	public void setResXml(String strResXml)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.strResXml = strResXml;
		}
	}
	
	/*==================================================================*/
	/**
	 * ���������� ��´�.
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public String getResXml()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strResXml = null;
		
		if (flag) {
			strResXml = this.strResXml;
		}
		
		return strResXml;
	}
	
	/*==================================================================*/
	/**
	 * IBridgeServant �� XmlBox ��ü�� instance�� �ѱ��.
	 */
	/*------------------------------------------------------------------*/
	public void putServentQueue()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			this.servantQueue.put(this);
		}
	}
	
	/*==================================================================*/
	/**
	 * XmlBox ��ü�� servantQueue �� ��´�.
	 */
	/*------------------------------------------------------------------*/
	public IBridgeQueue getServentQueue()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		IBridgeQueue queue = null;
		
		if (flag) {
			queue = this.servantQueue;
		}
		
		return queue;
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
				;
			}
			break;
		default:
			if (flag) {
				;
			}
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
