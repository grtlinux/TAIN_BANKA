package ic.vela.ibridge.server.ap.sendtoib;

import ic.vela.ibridge.base.log.Logger;
import ic.vela.ibridge.base.param.IBridgeParameter;
import ic.vela.ibridge.base.queue.IBridgeQueue;
import ic.vela.ibridge.base.xml.XmlObject;
import ic.vela.ibridge.server.ap.apmanager.ApManager;
import ic.vela.ibridge.util.XmlTool;

import java.util.HashMap;

/*==================================================================*/
/**
 * SendToIB 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SendToIB
 * @author  강석
 * @version 1.0, 2013/06/23
 * @since   jdk1.6.0_45
 * 
 * * 
 *  
 */
/*==================================================================*/
public class SendToIB extends Thread 
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
	private ApManager                  apManager           = null;
	
	@SuppressWarnings("unused")
	private IBridgeParameter           ibParam             = null;

	private IBridgeQueue               readQueue           = null;
	private IBridgeQueue               writeQueue          = null;
	
	private HashMap<String,XmlObject>  hashMapXml          = null;
	
	private HashMap<String,IBridgeQueue> hashMapQueue      = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public SendToIB()
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.06.22
			try {
				/*
				 * IBridgeServant를 관리하는 Manager 프로세스를 얻는다.
				 */
				this.apManager = ApManager.getInstance();
				
				/*
				 * 관리대상의 리소스 큐를 얻는다.
				 */
				// RestoreAgent -> SendToIB
				this.readQueue  = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOIB_QUEUE");
				
				// SendToIB -> ServerSocketSelector
				this.writeQueue = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SERVERSELECTOR_QUEUE");
				
				/*
				 * 전문저장 객체를 얻는다.
				 */
				// this.hashMapXml = (HashMap<String,XmlObject>) this.apManager.getResourceBanker().getHashMapBanker().getHashMap().get("XMLOBJECT_HASHMAP");
				this.hashMapXml = new HashMap<String,XmlObject> (5, 5);  // used in only local

				this.hashMapQueue = (HashMap<String,IBridgeQueue>) this.apManager.getResourceBanker().getHashMapBanker().getHashMap().get("IBRIDGEQUEUE_HASHMAP");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*==================================================================*/
	/**
	 * Thread running method
	 */
	/*------------------------------------------------------------------*/
	public void run()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		Object object = null;
		String strResStreamXml = null;
		XmlObject xmlObject = null;
		
		if (!flag) {    // DATE.2013.06.23
			
			while (true) {
				
				if (flag) {
					/*
					 * SendToFepSendQueue 에서 요청전문 봉투를 꺼낸다
					 */
					
					strResStreamXml = (String) this.readQueue.get();  // 1초 멈칫한다.
					if (strResStreamXml == null) {
						continue;
					}
				}
				
				if (flag) {
					try {
						/*
						 * 저장한 요청전문을 찾는다.
						 */
						String key = XmlTool.getStreamKey(strResStreamXml);

						synchronized (this.hashMapXml) {
							xmlObject = this.hashMapXml.get(key);
							this.hashMapXml.remove(key);
						}
						
						/*
						 *  응답전문을 세팅한다.
						 */
						if (xmlObject != null) 
						{
							xmlObject.setResStreamXml(strResStreamXml);
							
							if (flag) Logger.log("8.SendToIB-RES : [key=" + key + "][" + xmlObject.getResStreamXml() + "]");
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (flag) {
					try {
						/*
						 *  요청전문 봉투를 SelectorSendQueue 에 넣는다.
						 */
						xmlObject.getParentQueue().put(xmlObject);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (!flag) {    // TO DO DATE.2013.06.23
			
			while (true) {
				
				if (flag) {
					/*
					 * SendToFepSendQueue 에서 요청전문 봉투를 꺼낸다
					 */
					
					strResStreamXml = (String) this.readQueue.get();  // 1초 멈칫한다.
					if (strResStreamXml == null) {
						continue;
					}
				}
				
				if (flag) {
					try {

						if (flag) Logger.log("8.SendToIB-RES : [" + strResStreamXml + "]");
						
						if (flag) {
							String key = XmlTool.getStreamKey(strResStreamXml);
							System.out.println("\t\tSUCCESS [KEY=" + key + "] 찾았습니다.");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (flag) {
					try {
						/*
						 * 요청전문을 받은 곳으로 응답전문을 보낸다.
						 */
						this.writeQueue.put(strResStreamXml);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (!flag) {
					try {
						/*
						 * 요청전문을 받은 곳으로 응답전문을 보낸다.
						 */
						//synchronized (this.hashMapQueue)
						{
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							this.writeQueue = (IBridgeQueue) this.hashMapQueue.get(key);
							if (this.writeQueue != null) {
								/*
								 *  요청전문 봉투를 SelectorSendQueue 에 넣는다.
								 */
								this.writeQueue.put(strResStreamXml);
								
								this.hashMapQueue.remove(key);
								
								System.out.println("\t\tSUCCESS [KEY=" + key + "] 찾았습니다.");
								
							} else {
								
								System.out.println("\t\tERROR [KEY=" + key + "] 못찾았습니다.");
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (flag) {    // TODO DATE.2013.06.24
			
			while (true) {
				
				if (flag) {
					/*
					 * SendToFepSendQueue 에서 요청전문 봉투를 꺼낸다
					 */
					
					object = (Object) this.readQueue.get();  // 1초 멈칫한다.
					if (object == null) {
						continue;
					}
				}
				
				if (flag) {
					/*
					 * object 가 XmlObject 이면 받은 자료를 hashMapXml 에 저장한다.
					 */
					if (object instanceof XmlObject) {
						try {
							/*
							 * 객체를 재지정
							 */
							xmlObject = (XmlObject) object;
							
							/*
							 * 저장할 객체의 key 를 얻는다.
							 */
							String key = xmlObject.getKey();
							
							/*
							 * 객체를 저장한다.
							 */
							this.hashMapXml.put(key, xmlObject);
							if (flag) Logger.log("REQ KEY = [" + key + "]");

						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					}
				}

				if (flag) {
					/*
					 * object 가 String 이면 응답전문이다.
					 * 응답전문이면 hashMapXml에서 대응하는 요청전문을 찾는다.
					 * 찾은 객체에서 getParentQueue 에서 얻은 queue 에
					 * 응답전문을 전달한다.
					 * 
					 */
					if (object instanceof String) {
						try {
							/*
							 * 객체를 재지정
							 */
							strResStreamXml = (String) object;
							
							/*
							 * key를 얻는다.
							 */
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							/*
							 * hashMapXml에서 요청전문객체를 찾는다.
							 */
							xmlObject = this.hashMapXml.get(key);
							if (xmlObject != null) {
								/*
								 * 찾았다.
								 * 찾았으니 응답전문을 parent queue 로 보낸다.
								 */
								xmlObject.getParentQueue().put(strResStreamXml);
								
								/*
								 * 처리되었으니 key에 해당하는 자료를 삭제한다.
								 */
								this.hashMapXml.remove(key);
								
								if (flag) Logger.log("RES 응답전문[" + key + "]키에 해당하는 자료를 찾았습니다.");

							} else {
								/*
								 * 못찾았다.
								 * 못찾은 것은 아래와 같은 경우가 있다.
								 * 1. 요청전문을 저장 못한 사이에 응답전문이 왔다. : 지금처럼 synch 처리로는 불가능
								 * 2. 요청전문의 key와 응답전문의 key 가 바뀐 경우 : 대외기관에서 인위적으로 바꿈
								 * 3. 기타 문제로 인해...ㅠㅠ
								 */
								String msg = "STATUS : Not search XmlObject in hashMapXml..in SendToIB";
								Logger.log(msg);
								System.out.println(msg);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						continue;
					}
				}
				
				if (flag) {
					/*
					 * 이것 저것도 아닌경우는 화면으로 출력한다.
					 */
					String msg = "STATUS : Not checked......in SendToIB";
					Logger.log(msg);
					System.out.println(msg);
				}
				
				if (!flag) {
					try {
						/*
						 * 요청전문을 받은 곳으로 응답전문을 보낸다.
						 */
						//synchronized (this.hashMapQueue)
						{
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							this.writeQueue = (IBridgeQueue) this.hashMapQueue.get(key);
							if (this.writeQueue != null) {
								/*
								 *  요청전문 봉투를 SelectorSendQueue 에 넣는다.
								 */
								this.writeQueue.put(strResStreamXml);
								
								this.hashMapQueue.remove(key);
								
								System.out.println("\t\tSUCCESS [KEY=" + key + "] 찾았습니다.");
								
							} else {
								
								System.out.println("\t\tERROR [KEY=" + key + "] 못찾았습니다.");
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

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
			;
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
