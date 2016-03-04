package ic.vela.ibridge.serverbatch.ksi51.server;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * BatchServerMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchServerMain
 * @author  강석
 * @version 1.0, 2013/07/06
 * @since   jdk1.6.0_45
 * 
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 * 
 * 환경파일 : D:/KANG/WORK/workspace/IB_Vela.2/server/ bat/cfg/ bat.properties
 *  
 */
/*==================================================================*/
public class BatchServerMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";

	private static final String        BASE_FOLDER         = "D:/KANG/WORK/workspace/IB_Vela.2/server/";
	private static final String        CFG_FILE            = "bat.properties";

	private static final String        DEFAULT_FEPID       = "HWI51";
	
	//private static final int           MAX_FOLDER_CNT      = 10;
	private static final int           MAX_TRINFO_CNT      = 50;
	private static final int           MAX_HOSTINFO_CNT    = 50;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private ServerSocket               serverSocket        = null;

	private String                     strFepid            = null;  // FEPID
	
	/*
	 * 배치TR 관련된 정보
	 */
	private HashMap<String,BatchTrInfo>     hashMapTrInfo  = null;
	//private BatchTrInfo                     batchTrInfo    = null;
	
	/*
	 * 배치Info 관련된 정보
	 */
	private HashMap<String,BatchHostInfo>   hashMapHostInfo = null;
	private BatchHostInfo                   batchHostInfo   = null;

	/*
	 * 배치파일 생성폴더
	 */
	private String                     strBatchFileFolder  = null;

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	private BatchServerMain()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * 사용할 FEPID를 얻는다.
			 */
			strFepid = System.getProperty("system.ib.param.fepid", DEFAULT_FEPID);
			
			if (flag) {
				System.out.println("[FEPID = " + strFepid + "]");
			}
		}
	}

	/*==================================================================*/
	/**
	 * hashMap for Batch Tr Info
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapBatchTrInfo() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			Properties prop = null;
			
			if (flag) {
				/*
				 * Properties 파일을 읽는다.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapTrInfo 생성한다.
				 */
				this.hashMapTrInfo = new HashMap<String,BatchTrInfo> (5, 5);

				/*
				 * MAX_TRINFO_CNT 만큼 반복하면서 관련 폴더를 얻는다.
				 */
				for (int i=1; i <= MAX_TRINFO_CNT; i++) {
					
					/*
					 * Properties 정보를 읽는다.
					 */
					String strNo    = String.format("%02d", i);
					String strName  = prop.getProperty(String.format("system.ib.param.batch.tr.%s.name", strNo));
					String strCode  = prop.getProperty(String.format("system.ib.param.batch.tr.%s.code", strNo));
					
					if (!flag) System.out.println("(" + i + ") " + strCode);
					
					if (strName != null) {
						
						/*
						 * TO DO : 적당한 Key를 선택한다.
						 * properties 파일에서 key 에 해당하는 값을 넣는다.
						 */
						String strKey = strCode;
						
						/*
						 * 저장객체를 생성한다.
						 */
						BatchTrInfo info = new BatchTrInfo(strNo, strName, strCode);
						info.setStrKey(strKey);
						
						/*
						 * 저장객체를 hashMapTrInfo 에 저장한다.
						 */
						this.hashMapTrInfo.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- getHashMapBatchTrInfo ------------------");
				
				/*
				 * hashMapTrInfo 를 확인 출력한다.
				 */
				Set<String> setKeys = this.hashMapTrInfo.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					BatchTrInfo info = this.hashMapTrInfo.get(key);
					
					info.toPrint();
				}
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * hashMap for Batch Host Info
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapBatchHostInfo() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			Properties prop = null;
			
			if (flag) {
				/*
				 * Properties 파일을 읽는다.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapHostInfo를 생성한다.
				 */
				this.hashMapHostInfo = new HashMap<String,BatchHostInfo> (5, 5);

				/*
				 * MAX_FEPID_CNT 만큼 반복하면서 관련 폴더를 얻는다.
				 */
				for (int i=1; i <= MAX_HOSTINFO_CNT; i++) {
					
					/*
					 * Properties 정보를 읽는다.
					 */
					String strNo          = String.format("%02d", i);
					String strFepid       = prop.getProperty(String.format("system.ib.param.batchinfo.%s.fepid"      , strNo));
					String strInsid       = prop.getProperty(String.format("system.ib.param.batchinfo.%s.insid"      , strNo));
					String strName        = prop.getProperty(String.format("system.ib.param.batchinfo.%s.name"       , strNo));
					String strHostIp      = prop.getProperty(String.format("system.ib.param.batchinfo.%s.host.ip"    , strNo));
					String strHostPort    = prop.getProperty(String.format("system.ib.param.batchinfo.%s.host.port"  , strNo));
					String strPathOrg     = prop.getProperty(String.format("system.ib.param.batchinfo.%s.path.org"   , strNo));
					String strPathBackup  = prop.getProperty(String.format("system.ib.param.batchinfo.%s.path.backup", strNo));
					
					if (!flag) System.out.println("(" + i + ") " + strFepid);
					
					if (strFepid != null) {
						
						/*
						 * TO DO : 적당한 Key를 선택한다.
						 * properties 파일에서 key 에 해당하는 값을 넣는다.
						 */
						String strKey = strFepid;
						
						/*
						 * 저장객체를 생성한다.
						 */
						BatchHostInfo info = new BatchHostInfo(
								strNo,
								strFepid,
								strInsid,
								strName,
								strHostIp,
								strHostPort,
								strPathOrg,
								strPathBackup
								);
						info.setStrKey(strKey);
						
						/*
						 * 저장객체를 hashMapFile 에 저장한다.
						 */
						this.hashMapHostInfo.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- getHashMapBatchHostInfo ------------------");
				
				/*
				 * hashMapHostInfo 를 확인 출력한다.
				 */
				Set<String> setKeys = this.hashMapHostInfo.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					BatchHostInfo info = this.hashMapHostInfo.get(key);
					
					info.toPrint();
				}
			}
			
			if (flag) {
				/*
				 * 해당 FEPID에 대한 정보를 얻는다.
				 */
				
				this.batchHostInfo = this.hashMapHostInfo.get(strFepid);
				
				if (!flag) this.batchHostInfo.toPrint();
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * get the batch file folder
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getBatchFileFolder() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag  = true;
		
		if (flag) {
			Properties prop = null;
			
			if (flag) {
				/*
				 * Properties 파일을 읽는다.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * 배치파일을 저장할 폴더를 얻는다.
				 * 
				 * TODO DATE.2013.07.07
				 * 이 자료를 갖고 처리할 때에는 YYYYMMDD를 현재날짜로 변환하여
				 * 처리해야 한다.
				 */
				
				this.strBatchFileFolder = prop.getProperty("system.ib.param.batchfile.folder");
				if (this.strBatchFileFolder == null) {
					throw new IllegalStateException("ERROR : couldn't define the batch file folder...");
				}
			}
			
			if (flag) {
				System.out.println("[BATCH FOLDER=" + this.strBatchFileFolder + "]");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * make a server socket
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void makeServerSocket() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * make a server socket of the port
			 */
			serverSocket = new ServerSocket(batchHostInfo.getIntHostPort());
			
			/*
			 * reuse address information
			 */
			serverSocket.setReuseAddress(true);
		}
	}
	
	/*==================================================================*/
	/*
	 * process 실행
	 */
	/*------------------------------------------------------------------*/
	private void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				//////////////////////////////////////////////////////////////////
				/*
				 * 배치처리 TR 정보를 얻는다.
				 */
				getHashMapBatchTrInfo();
				
				/*
				 * 배치처리 HOST 정보를 얻는다.
				 */
				getHashMapBatchHostInfo();
				
				/*
				 * 배치파일을 저장할 폴더를 정한다.
				 */
				getBatchFileFolder();
				
				if (!flag) System.exit(0);
				
				/////////////////////////////////////

				/*
				 * 서버소켓을 열어 클라이언트로 부터 접속을 확인하여
				 * 배치자료를 수신한다.
				 */
				if (flag) {    // DATE.2013.06.27
					/*
					 *  make server socket
					 */
					makeServerSocket();
					
					if ("HWI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 한화생명 배치
						 */
						BatchServerHWI51 batchServer = new BatchServerHWI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("SHI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 신한생명 배치
						 */
						BatchServerSHI51 batchServer = new BatchServerSHI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("HNI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 하나생명 배치
						 */
						BatchServerHNI51 batchServer = new BatchServerHNI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("HKI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 흥국생명 배치
						 */
						BatchServerHKI51 batchServer = new BatchServerHKI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("KFI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 카디프생명 배치
						 */
						BatchServerKFI51 batchServer = new BatchServerKFI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("ALI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 알리안츠생명 배치
						 */
						BatchServerALI51 batchServer = new BatchServerALI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("SSI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 삼성생명 배치
						 */
						BatchServerSSI51 batchServer = new BatchServerSSI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("MII51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 미래에셋생명 배치
						 */
						BatchServerMII51 batchServer = new BatchServerMII51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("NHI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 농협생명 배치
						 */
						BatchServerNHI51 batchServer = new BatchServerNHI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else if ("NRI51".equals(this.batchHostInfo.getStrFepid())) {
						/*
						 * 노란우산공제 배치
						 */
						BatchServerNRI51 batchServer = new BatchServerNRI51(
								this.serverSocket,
								this.hashMapTrInfo,
								this.batchHostInfo,
								strFepid,
								strBatchFileFolder
								);
						batchServer.execute();

					} else {
						/*
						 * 그 외 배치
						 */
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
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
		
		switch (0) {
		case 0:
			if (flag) {
				BatchServerMain serverMain = new BatchServerMain();
				serverMain.execute();
			}
			break;
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
