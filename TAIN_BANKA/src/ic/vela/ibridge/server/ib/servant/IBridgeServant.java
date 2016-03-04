package ic.vela.ibridge.server.ib.servant;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.common.version.Version;
import ic.vela.ibridge.server.ib.ibmanager.IBridgeManager;

import java.util.HashMap;

/*==================================================================*/
/**
 * IBridgeServant 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeServant
 * @author  강석
 * @version 1.0, 2013/06/22
 * @since   jdk1.6.0_45
 * 
 * * IBridgeServant 객체가 생성되기 전에 IBridgeManager 가 실행되야 함.
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
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.ibManager = IBridgeManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				this.myQueue = new IBridgeQueue();
				this.sendQueue = this.ibManager.getResourceBanker().getQueueBanker().getQueue().get("SEND_REQ_QUEUE");
				this.hashMapFepidInfo = (HashMap<String,String>) this.ibManager.getResourceBanker().getHashMapBanker().getHashMap().get("FEPIDINFO_HASHMAP");
				
				/*
				 * 전문객체를 저장할 HashMap 을 생성한다.
				 */
				this.hashMapXmlObject = new HashMap<String,XmlObject>(5, 5);
				
				if (!flag) Logger.log("STATUS : IBridgeServant 생성되었습니다.");
				
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
				 * XmlObject 를 생성한다.
				 * - strReqSoapXml 을 세팅한다.
				 * - 자동으로 strReqStreamXml 이 생성된다.
				 * - 자동으로 strKey 가 생성된다.
				 * - 처리후에는 sendQueue 에 put 한다.
				 */
				XmlObject xmlObject = new XmlObject(this.myQueue);
				
				/*
				 * 요청전문을 세팅한다.
				 */
				xmlObject.setReqSoapXml(strReqSoapXml);
				if (!flag) Logger.log("REQ = [" + strReqSoapXml + "]");
				
				/*
				 * FEPID가 존재하는지를 확인한다.
				 * 존재하지 않으면 에러 Exception 발생
				 */
				strFepid = xmlObject.getFepid();
				strFepidName = this.hashMapFepidInfo.get(strFepid);
				if (strFepidName == null) {
					throw new IllegalStateException("ERROR : wrong FEPID [" + strFepid + "](HashMapBanker.FEPIDINFO)");
				}
				
				/*
				 * 해당 키를 구한다.
				 */
				strKey = xmlObject.getKey();
				
				if (flag) Logger.log("\n================================ [START-REQ:%s:%s] =====================================\nREQ 요청전문 = [%s]"
						, strFepidName, strKey, strReqSoapXml);

				if (flag) Logger.log("REQ KEY = [" + strKey + "]");
				
				/*
				 * 관리용 Map 에 XmlObject 를 put 한다.
				 * 나중에 요청전문에 해당하는 응답전문을 찾기 위해서
				 */
				this.hashMapXmlObject.put(strKey, xmlObject);
				
				/*
				 * 요청전문객체를 전송한다.
				 * 요청전문은 StreamXml이다.
				 */
				this.sendQueue.put(xmlObject);
				if (!flag) Logger.log("REQ 를 송신하였습니다.");
				
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
				 * myQueue 에서 XmlObject 를 get 한다.
				 * 여기서 얻은 XmlObject 객체는 요청에 대한 응답처리를 마친 것이다.
				 * 그리고, strResSoapXml 을 얻는다.
				 */
				XmlObject xmlObject = (XmlObject) this.myQueue.get(lTimeOut);
				
				if (xmlObject == null) {
					/*
					 * 시간이 초과되거나 자료가 없다.
					 */
					return null;
				}
				
				/*
				 * 요청전문과 응답전문을 비교하여 맞는지 확인한다.
				 * 
				 * 응답전문의 Key를 얻어 관리용 Map에서 검색한다.
				 * 있으면 요청전문에 해당하는 응답전문이다.
				 */
				strKey = xmlObject.getKey();
				if (!flag) Logger.log("RES KEY = [" + strKey + "]");
				
				XmlObject obj = this.hashMapXmlObject.get(strKey);
				if (obj == null) {
					/*
					 * 해당 응답전문은 이놈이 보낸 전문이 아니다.
					 * 응답전문에 해당하는 요청전문이 없다.
					 */
					if (flag) Logger.error("RES 응답전문[" + strKey + "]키에 해당하는 요청전문 자료가 없습니다.");
					
					return null;
				}
				
				if (flag) Logger.log("RES 응답전문[" + strKey + "]키에 해당하는 자료를 찾았습니다.");

				/*
				 * 수신전문에서 FEPID를 구한다.
				 */
				strFepid = xmlObject.getFepid();
				strFepidName = this.hashMapFepidInfo.get(strFepid);
				
				/*
				 * 요청에 해당하는 응답전문을 찾았다.
				 * 응답전문을 리턴하면 된다.
				 */
				strResSoapXml = xmlObject.getResSoapXml();
				
				if (flag) Logger.log("\nRES 응답전문 [%s]\n================================ [END-RES:%s:%s] ====================================="
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
	 * 프로그램의 version을 얻는다.
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

