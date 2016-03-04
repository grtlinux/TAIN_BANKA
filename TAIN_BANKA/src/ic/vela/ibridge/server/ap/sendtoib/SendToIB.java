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
 * SendToIB Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SendToIB
 * @author  ����
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
				 * IBridgeServant�� �����ϴ� Manager ���μ����� ��´�.
				 */
				this.apManager = ApManager.getInstance();
				
				/*
				 * ��������� ���ҽ� ť�� ��´�.
				 */
				// RestoreAgent -> SendToIB
				this.readQueue  = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SENDTOIB_QUEUE");
				
				// SendToIB -> ServerSocketSelector
				this.writeQueue = this.apManager.getResourceBanker().getQueueBanker().getQueue().get("RECV_SERVERSELECTOR_QUEUE");
				
				/*
				 * �������� ��ü�� ��´�.
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
					 * SendToFepSendQueue ���� ��û���� ������ ������
					 */
					
					strResStreamXml = (String) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
					if (strResStreamXml == null) {
						continue;
					}
				}
				
				if (flag) {
					try {
						/*
						 * ������ ��û������ ã�´�.
						 */
						String key = XmlTool.getStreamKey(strResStreamXml);

						synchronized (this.hashMapXml) {
							xmlObject = this.hashMapXml.get(key);
							this.hashMapXml.remove(key);
						}
						
						/*
						 *  ���������� �����Ѵ�.
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
						 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
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
					 * SendToFepSendQueue ���� ��û���� ������ ������
					 */
					
					strResStreamXml = (String) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
					if (strResStreamXml == null) {
						continue;
					}
				}
				
				if (flag) {
					try {

						if (flag) Logger.log("8.SendToIB-RES : [" + strResStreamXml + "]");
						
						if (flag) {
							String key = XmlTool.getStreamKey(strResStreamXml);
							System.out.println("\t\tSUCCESS [KEY=" + key + "] ã�ҽ��ϴ�.");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (flag) {
					try {
						/*
						 * ��û������ ���� ������ ���������� ������.
						 */
						this.writeQueue.put(strResStreamXml);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (!flag) {
					try {
						/*
						 * ��û������ ���� ������ ���������� ������.
						 */
						//synchronized (this.hashMapQueue)
						{
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							this.writeQueue = (IBridgeQueue) this.hashMapQueue.get(key);
							if (this.writeQueue != null) {
								/*
								 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
								 */
								this.writeQueue.put(strResStreamXml);
								
								this.hashMapQueue.remove(key);
								
								System.out.println("\t\tSUCCESS [KEY=" + key + "] ã�ҽ��ϴ�.");
								
							} else {
								
								System.out.println("\t\tERROR [KEY=" + key + "] ��ã�ҽ��ϴ�.");
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
					 * SendToFepSendQueue ���� ��û���� ������ ������
					 */
					
					object = (Object) this.readQueue.get();  // 1�� ��ĩ�Ѵ�.
					if (object == null) {
						continue;
					}
				}
				
				if (flag) {
					/*
					 * object �� XmlObject �̸� ���� �ڷḦ hashMapXml �� �����Ѵ�.
					 */
					if (object instanceof XmlObject) {
						try {
							/*
							 * ��ü�� ������
							 */
							xmlObject = (XmlObject) object;
							
							/*
							 * ������ ��ü�� key �� ��´�.
							 */
							String key = xmlObject.getKey();
							
							/*
							 * ��ü�� �����Ѵ�.
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
					 * object �� String �̸� ���������̴�.
					 * ���������̸� hashMapXml���� �����ϴ� ��û������ ã�´�.
					 * ã�� ��ü���� getParentQueue ���� ���� queue ��
					 * ���������� �����Ѵ�.
					 * 
					 */
					if (object instanceof String) {
						try {
							/*
							 * ��ü�� ������
							 */
							strResStreamXml = (String) object;
							
							/*
							 * key�� ��´�.
							 */
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							/*
							 * hashMapXml���� ��û������ü�� ã�´�.
							 */
							xmlObject = this.hashMapXml.get(key);
							if (xmlObject != null) {
								/*
								 * ã�Ҵ�.
								 * ã������ ���������� parent queue �� ������.
								 */
								xmlObject.getParentQueue().put(strResStreamXml);
								
								/*
								 * ó���Ǿ����� key�� �ش��ϴ� �ڷḦ �����Ѵ�.
								 */
								this.hashMapXml.remove(key);
								
								if (flag) Logger.log("RES ��������[" + key + "]Ű�� �ش��ϴ� �ڷḦ ã�ҽ��ϴ�.");

							} else {
								/*
								 * ��ã�Ҵ�.
								 * ��ã�� ���� �Ʒ��� ���� ��찡 �ִ�.
								 * 1. ��û������ ���� ���� ���̿� ���������� �Դ�. : ����ó�� synch ó���δ� �Ұ���
								 * 2. ��û������ key�� ���������� key �� �ٲ� ��� : ��ܱ������ ���������� �ٲ�
								 * 3. ��Ÿ ������ ����...�Ф�
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
					 * �̰� ���͵� �ƴѰ��� ȭ������ ����Ѵ�.
					 */
					String msg = "STATUS : Not checked......in SendToIB";
					Logger.log(msg);
					System.out.println(msg);
				}
				
				if (!flag) {
					try {
						/*
						 * ��û������ ���� ������ ���������� ������.
						 */
						//synchronized (this.hashMapQueue)
						{
							String key = XmlTool.getStreamKey(strResStreamXml);
							
							this.writeQueue = (IBridgeQueue) this.hashMapQueue.get(key);
							if (this.writeQueue != null) {
								/*
								 *  ��û���� ������ SelectorSendQueue �� �ִ´�.
								 */
								this.writeQueue.put(strResStreamXml);
								
								this.hashMapQueue.remove(key);
								
								System.out.println("\t\tSUCCESS [KEY=" + key + "] ã�ҽ��ϴ�.");
								
							} else {
								
								System.out.println("\t\tERROR [KEY=" + key + "] ��ã�ҽ��ϴ�.");
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
