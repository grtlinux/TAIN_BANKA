package ic.vela.ibridge.server.ib.servant;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.common.version.Version;
import ic.vela.ibridge.server.ib.ibmanager.IBridgeManager;

import java.util.HashMap;

/*==================================================================*/
/**
 * IBridgeServant Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeServant
 * @author  ����
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * * IBridgeServant ��ü�� �����Ǳ� ���� IBridgeManager �� ����Ǿ� ��.
 * - 
 *  
 */
/*==================================================================*/
public class IBridgeServant
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
	private IBridgeManager             ibManager           = null;
	
	private IBridgeQueue               myQueue             = null;  // recvQueue
	private IBridgeQueue               sendQueue           = null;

	private HashMap<String,XmlObject>  hashMapXmlObject    = null;
	
	private HashMap<String,String>     hashMapFepidInfo    = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public IBridgeServant() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.ibManager = IBridgeManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				this.myQueue = new IBridgeQueue();
				this.sendQueue = this.ibManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_REQ_QUEUE");
				this.hashMapFepidInfo = (HashMap<String,String>) this.ibManager.getResourceBanker().getHashMapBanker().getHashMap().get("FEPIDINFO_HASHMAP");
				
				/*
				 * ������ü�� ������ HashMap �� �����Ѵ�.
				 */
				this.hashMapXmlObject = new HashMap<String,XmlObject>(5, 5);
				
				if (!flag) Logger.log("STATUS : IBridgeServant �����Ǿ����ϴ�.");
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : IBridgeServant constructor");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * send strReqSoapXml
	 * @param strReqSoapXml
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public synchronized String sendXml(String strReqSoapXml) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strKey = null;
		String strFepid = null;
		String strFepidName = null;
		
		if (flag) {
			try {
				/*
				 * XmlObject �� �����Ѵ�.
				 * - strReqSoapXml �� �����Ѵ�.
				 * - �ڵ����� strReqStreamXml �� �����ȴ�.
				 * - �ڵ����� strKey �� �����ȴ�.
				 * - ó���Ŀ��� sendQueue �� put �Ѵ�.
				 */
				XmlObject xmlObject = new XmlObject(this.myQueue);
				
				/*
				 * ��û������ �����Ѵ�.
				 */
				xmlObject.setReqSoapXml(strReqSoapXml);
				if (!flag) Logger.log("REQ = [" + strReqSoapXml + "]");
				
				/*
				 * FEPID�� �����ϴ����� Ȯ���Ѵ�.
				 * �������� ������ ���� Exception �߻�
				 */
				strFepid = xmlObject.getFepid();
				strFepidName = this.hashMapFepidInfo.get(strFepid);
				if (strFepidName == null) {
					throw new IllegalStateException("ERROR : wrong FEPID [" + strFepid + "](HashMapBanker.FEPIDINFO)");
				}
				
				/*
				 * �ش� Ű�� ���Ѵ�.
				 */
				strKey = xmlObject.getKey();
				
				if (flag) Logger.log("\n================================ [START-REQ:%s:%s] =====================================\nREQ ��û���� = [%s]"
						, strFepidName, strKey, strReqSoapXml);

				if (flag) Logger.log("REQ KEY = [" + strKey + "]");
				
				/*
				 * ������ Map �� XmlObject �� put �Ѵ�.
				 * ���߿� ��û������ �ش��ϴ� ���������� ã�� ���ؼ�
				 */
				this.hashMapXmlObject.put(strKey, xmlObject);
				
				/*
				 * ��û������ü�� �����Ѵ�.
				 * ��û������ StreamXml�̴�.
				 */
				this.sendQueue.put(xmlObject);
				if (!flag) Logger.log("REQ �� �۽��Ͽ����ϴ�.");
				
			} catch (IllegalStateException e) {
				//e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in sendXml method");
			}
		}
		
		return strKey;
	}
	
	/*==================================================================*/
	/**
	 * recv and return strResSoapXml : Blocking
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String recvXml() throws Exception
	/*------------------------------------------------------------------*/
	{
		return recvXml(0);
	}
	
	/*==================================================================*/
	/**
	 * recv and return strResSoapXml : Non Blocking
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String recvXml(long lTimeOut) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strResSoapXml = null;
		
		String strKey = null;
		String strFepid = null;
		String strFepidName = null;
		
		if (flag) {    // DATE.2013.06.22
			try {
				/*
				 * myQueue ���� XmlObject �� get �Ѵ�.
				 * ���⼭ ���� XmlObject ��ü�� ��û�� ���� ����ó���� ��ģ ���̴�.
				 * �׸���, strResSoapXml �� ��´�.
				 */
				XmlObject xmlObject = (XmlObject) this.myQueue.get(lTimeOut);
				
				if (xmlObject == null) {
					/*
					 * �ð��� �ʰ��ǰų� �ڷᰡ ����.
					 */
					return null;
				}
				
				/*
				 * ��û������ ���������� ���Ͽ� �´��� Ȯ���Ѵ�.
				 * 
				 * ���������� Key�� ��� ������ Map���� �˻��Ѵ�.
				 * ������ ��û������ �ش��ϴ� ���������̴�.
				 */
				strKey = xmlObject.getKey();
				if (!flag) Logger.log("RES KEY = [" + strKey + "]");
				
				XmlObject obj = this.hashMapXmlObject.get(strKey);
				if (obj == null) {
					/*
					 * �ش� ���������� �̳��� ���� ������ �ƴϴ�.
					 * ���������� �ش��ϴ� ��û������ ����.
					 */
					if (flag) Logger.error("RES ��������[" + strKey + "]Ű�� �ش��ϴ� ��û���� �ڷᰡ �����ϴ�.");
					
					return null;
				}
				
				if (flag) Logger.log("RES ��������[" + strKey + "]Ű�� �ش��ϴ� �ڷḦ ã�ҽ��ϴ�.");

				/*
				 * ������������ FEPID�� ���Ѵ�.
				 */
				strFepid = xmlObject.getFepid();
				strFepidName = this.hashMapFepidInfo.get(strFepid);
				
				/*
				 * ��û�� �ش��ϴ� ���������� ã�Ҵ�.
				 * ���������� �����ϸ� �ȴ�.
				 */
				strResSoapXml = xmlObject.getResSoapXml();
				
				if (flag) Logger.log("\nRES �������� [%s]\n================================ [END-RES:%s:%s] ====================================="
						, strResSoapXml, strFepidName, strKey);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("ERROR : processing in recvXml method");
			}
		}
		
		return strResSoapXml;
	}
	
	/*==================================================================*/
	/**
	 * ���α׷��� version�� ��´�.
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public String getVersion() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strVersion = null;
		
		if (flag) {
			strVersion = Version.getVersion();
		}
		
		return strVersion;
	}
	
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
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/

