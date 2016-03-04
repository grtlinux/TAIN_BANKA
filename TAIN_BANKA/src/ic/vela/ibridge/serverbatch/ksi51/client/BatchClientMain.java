package ic.vela.ibridge.serverbatch.ksi51.client;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchChkFile;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchFolder;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * BatchClientMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchClientMain
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
public class BatchClientMain
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
	
	private static final int           MAX_FOLDER_CNT      = 10;
	private static final int           MAX_TRINFO_CNT      = 50;
	private static final int           MAX_HOSTINFO_CNT    = 50;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String                          strChkFileName = null;
	
	/*
	 * 배치파일이 있는 대상 폴더
	 */
	private HashMap<String,BatchFolder>     hashMapFolder  = null;
	private BatchFolder                     batchFolder    = null;
	
	/*
	 * 배치TR 관련된 정보
	 */
	private HashMap<String,BatchTrInfo>     hashMapTrInfo  = null;
	private BatchTrInfo                     batchTrInfo    = null;
	
	/*
	 * 배치Info 관련된 정보
	 */
	private HashMap<String,BatchHostInfo>   hashMapHostInfo = null;
	private BatchHostInfo                   batchHostInfo   = null;
	
	/*
	 * 배치 처리를 끝낸 파일에 대한 처리 결과
	 */
	private HashMap<String,BatchChkFile>    hashMapChkFile = null;
	

	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	private BatchClientMain()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}

	/*==================================================================*/
	/**
	 * hashMap for Batch Folder
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapBatchFolder() throws Exception
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
				 * hashMapFolder를 생성한다.
				 */
				this.hashMapFolder = new HashMap<String,BatchFolder> (5, 5);
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());

				/*
				 * MAX_FOLDER_CNT 만큼 반복하면서 관련 폴더를 얻는다.
				 */
				for (int i=1; i <= MAX_FOLDER_CNT; i++) {
					
					/*
					 * Properties 정보를 읽는다.
					 */
					String strNo = String.format("%02d", i);
					String strSrcFolder = prop.getProperty(String.format("system.ib.param.batch.folder.%s.src", strNo));
					String strTgtFolder = prop.getProperty(String.format("system.ib.param.batch.folder.%s.tgt", strNo));
					if (strSrcFolder != null) {
						if (strTgtFolder == null) {
							strTgtFolder = "";
						}
						
						/*
						 * TO DO : 적당한 Key를 선택한다.
						 * properties 파일에서 key 에 해당하는 값을 넣는다.
						 */
						String strKey = strNo;

						/*
						 * 저장객체를 생성한다.
						 */
						strSrcFolder = strSrcFolder.replace("YYYYMMDD", strDate);
						strTgtFolder = strTgtFolder.replace("YYYYMMDD", strDate);
						
						BatchFolder info = new BatchFolder(strNo, strSrcFolder, strTgtFolder);
						info.setStrKey(strKey);
						
						/*
						 * 저장객체를 hashMapFolder에 저장한다.
						 */
						this.hashMapFolder.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.07.06
				
				String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
				
				System.out.println("----------------- getHashMapBatchFolder [" + strDateTime + "] ------------------");
				
				/*
				 * hashMapFolder 를 확인 출력한다.
				 */
				Set<String> setKeys = hashMapFolder.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					BatchFolder info = hashMapFolder.get(key);
					
					info.toPrint();
				}
			}
		}
		
		if (!flag) System.exit(0);
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
						String strKey = strInsid;
						
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
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * hashMap for Check File
	 * 
	 * Line
	 *     처리일시       파일위치   파일명          파일속성일시   송신된파일명         레코드길이 레코드건수    처리결과
	 *     YYYYMMDDhhmmss;D:/server/;DN_YYYYMMDD.TXT;YYYYMMDDhhmmss;R_BCC_FMA26000003_YYYYMMDD_01;00999;00999;SUCCESS
	 *     
	 *     작업일시;파일위치;배치파일;파일속성일시;송신된파일명;레코드길이;레코드건수;실행결과
	 *     
	 * DATE.2013.06.28 : 레코드길이, 레코드건수 추가
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapChkFile() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (flag) {

			if (flag) {
				/*
				 * check file 명을 얻는다.
				 */
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				strChkFileName = strBaseFolder + "bat/chk/YYYYMMDD.chk";
				
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				strChkFileName = strChkFileName.replace("YYYYMMDD", strDate);
				
				/*
				 * TO DO : 파일이 없으면 생성한다.
				 */
				if (!new File(strChkFileName).exists()) {
					try {
						PrintStream ps = new PrintStream(new FileOutputStream(this.strChkFileName, true), true, CHARSET);
						String strInfoLine = "# 작업일시;배치파일위치;배치파일;파일속성일시;생성배치파일;레코드길이;레코드건수;실행결과";
						ps.println(strInfoLine);
						ps.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			if (flag) {
				/*
				 * hashMapChkFile 생성한다.
				 */
				hashMapChkFile = new HashMap<String,BatchChkFile> (5, 5);

				/*
				 * 파일을 open 한다.
				 * 각 라인을 읽어 WebCashBatchChkFile 객체를 생성한다.
				 * 파일을 close 한다.
				 */

				FileReader fr = new FileReader(strChkFileName);
				LineNumberReader lnr = new LineNumberReader(fr);
				
				// 파일을 읽어 출력한다.
				while (true) {
					
					/*
					 * 라인을 읽는다.
					 * null 리턴이면 EOF 가 된다.
					 */
					String strLine = lnr.readLine();
					if (strLine == null) {
						break;
					}
					
					/*
					 * 라인번호를 구한다.
					 */
					int iLine = lnr.getLineNumber();
					
					/*
					 * comment는 skip한다.
					 * 앞 '#'은 comment로 정한다.
					 */
					if (strLine.trim().charAt(0) == '#') {
						if (!flag) System.out.println(String.format("[%02d] [%s] COMMENT SKIP", iLine, strLine));
						continue;
					} else {
						if (!flag) System.out.println(String.format("[%02d] [%s]", iLine, strLine));
					}
					
					if (flag) {
						/*
						 * 저장객체를 생성한다.
						 */
						BatchChkFile chkFile = new BatchChkFile(strLine);
						
						/*
						 * 저장객체를 hashMapChkFile 에 저장한다.
						 */
						hashMapChkFile.put(chkFile.getStrKey(), chkFile);
					}
				}
				
				lnr.close();
				fr.close();
			}
		}
		
		if (!flag) {  // TO DO DATE.2013.06.27
			
			System.out.println("----------------- BatchChkFile ------------------");
			
			/*
			 * hashMapFolder 를 확인 출력한다.
			 */
			Set<String> setKeys = hashMapChkFile.keySet();
			Iterator<String> iterKeys = setKeys.iterator();
			
			while (iterKeys.hasNext()) {
				String key = iterKeys.next();
				BatchChkFile info = hashMapChkFile.get(key);
				
				info.toPrint();
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/**
	 * process 실행
	 */
	/*------------------------------------------------------------------*/
	private void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.26
			
			try {
				/*
				 * 주기적으로 반복하면서 배치 폴더를 검색한다.
				 */
				while (true) {
					
					//////////////////////////////////////////////////////////////////
					/*
					 * 배치처리 폴더 정보를 얻는다.
					 */
					getHashMapBatchFolder();

					/*
					 * 배치처리 TR 정보를 얻는다.
					 */
					getHashMapBatchTrInfo();
					
					/*
					 * 배치처리 HOST 정보를 얻는다.
					 */
					getHashMapBatchHostInfo();
					
					/*
					 * 배치처리를 마친 파일 정보를 얻는다.
					 */
					getHashMapChkFile();
					
					if (!flag) System.exit(0);
					
					//////////////////////////////////////////////////////////////////
					/*
					 * 배치처리폴더를 읽는다.
					 */
					Set<String> setKeyFolder = this.hashMapFolder.keySet();
					Iterator<String> iterKeyFolder = setKeyFolder.iterator();
					
					while (iterKeyFolder.hasNext()) {
						/*
						 * 배치처리폴더명을 얻는다.
						 */
						String keyFolder = iterKeyFolder.next();
						this.batchFolder = this.hashMapFolder.get(keyFolder);
						
						File fileFolder = new File(this.batchFolder.getStrSrcFolder());
						if (!flag) {
							if (flag) System.out.println("PATH=" + fileFolder.getAbsolutePath());   // 출력:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getCanonicalPath());  // 출력:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getParent());         // 출력:PATH=D:\KANG\WORK\webCash
							if (flag) System.out.println("PATH=" + fileFolder.getPath());           // 출력:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getName());           // 출력:PATH=data3
						}
						
						/*
						 * 당일날자의 파일들을 얻는다.
						 */
						File[] files = fileFolder.listFiles(     // FileFilter 처리를 한다.
								////////////////////////////////////////////////////////////////////
								new FileFilter() {
									public boolean accept(File file) {
										boolean flag = true;
										
										if (flag) {
											/*
											 * 파일이 아니면 false 를 돌려준다.
											 */
											if (!file.isFile()) {
												return false;
											}
										}
										
										if (flag) {
											/*
											 * 파일 속성 날짜를 얻는다.
											 * 현재날짜에 해당하는 파일들만 읽을 수 있도록 한다.
											 */

											String strCompareDate = new SimpleDateFormat("yyyyMMdd000000", Locale.KOREA).format(new Date());
											String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
											
											if (!flag) {   // TO DO : 테스트용 20130515000000
												strCompareDate = "20130515000000";
												//strCompareDate = "20130607000000";
												if (!flag) System.out.println("[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
											}

											/*
											 * 파일 속성일시에 해당하는 경우
											 */
											if (strCompareDate.compareTo(strLastModifiedDate) <= 0) {
												if (!flag) System.out.println("속성일시OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
												/*
												 * TO DO : 파일명이 조건에 맞는 경우를 찾는다.
												 * 1. substring(0,3) -> 보험사
												 * 2. substring(3,6) -> 거래코드
												 */
												String strFileName = file.getName();
												String strInsid = strFileName.substring(0, 3);
												String strBizCode = strFileName.substring(3, 9);
												if (!flag) {
													System.out.println("[strFileName = " + strFileName + "]");
													System.out.println("[strInsid    = " + strInsid    + "]");
													System.out.println("[strBizCode  = " + strBizCode  + "]");
												}
												
												if (hashMapHostInfo.get(strInsid) == null) {
													/*
													 * 관련된보험사가 없다.
													 */
													return false;
												}
												
												if (hashMapTrInfo.get(strBizCode) == null) {
													/*
													 * 관련된 배치파일이 없다.
													 */
													return false;
												}
												
												/*
												 * 처리할 배치파일을 찾았다.
												 */
												if (!flag) System.out.println("배치목록OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + strFileName + "]");
												
												return true;
											}
										}
										
										return false;
									}  // public boolean accept(File file)
								}
								////////////////////////////////////////////////////////////////////
								);
						
						if (files == null) {
							if (flag) System.out.println("files is null point.......강석.");
							
							try {
								// Thread.sleep(10 * 60 * 1000);
								Thread.sleep(5 * 1000);
							} catch (InterruptedException e) {}
							
							continue;
						}
						
						/*
						 * 당일날짜의 파일들을 읽으면서
						 * 처리된 것은 skip 하고 그렇지 않은 것은
						 * 배치처리를 하고 chk 파일에 기록한다.
						 */
						for (int i=0; i < files.length; i++) {
							/*
							 * 파일들만 선택한다.
							 */
							if (files[i].isFile()) {
								/*
								 * 비교 key를 얻어 hashMap 에서 검색한다.
								 */
								String strFileName = files[i].getName();
								String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(files[i].lastModified()));
								String strKey = strFileName + strLastModifiedDate;
								if (!flag) {
									System.out.println("[KEY=" + strKey + "]");
								}
								
								/*
								 * obj 가 null이 아니면 관련된 배치 파일은 기처리된 파일이다.
								 * 그래서 null 인 경우가 처리해야하는 배치파일이다.
								 */
								BatchChkFile obj = (BatchChkFile) this.hashMapChkFile.get(strKey);
								if (obj == null) {
									
									if (flag) {    // TODO DATE.20130706
										String strInsid = strFileName.substring(0, 3);
										String strBizCode = strFileName.substring(3, 9);
										
										this.batchHostInfo = hashMapHostInfo.get(strInsid);
										this.batchTrInfo = hashMapTrInfo.get(strBizCode);
										
										if (!flag) {
											System.out.println("[strSrcFolder = " + this.batchFolder.getStrSrcFolder() + "]");
											System.out.println("[strTrName    = " + this.batchTrInfo.getStrTrName()    + "]");
											System.out.println("[strTrCode    = " + this.batchTrInfo.getStrTrCode()    + "]");
											System.out.println("[strFepid     = " + this.batchHostInfo.getStrFepid()   + "]");
											System.out.println("[strInsid     = " + this.batchHostInfo.getStrInsid()   + "]");
											System.out.println("[strName      = " + this.batchHostInfo.getStrName()    + "]");
											System.out.println("[strFileName  = " + strFileName + "]");
										}
										
										if ("HWI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 한화생명 배치
											 */
											BatchClientHWI51 batchClient = new BatchClientHWI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("SHI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 신한생명 배치
											 */
											BatchClientSHI51 batchClient = new BatchClientSHI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();
										
										} else if ("HNI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 하나생명 배치
											 */
											BatchClientHNI51 batchClient = new BatchClientHNI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("HKI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 흥국생명 배치
											 */
											BatchClientHKI51 batchClient = new BatchClientHKI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();
										
										} else if ("KFI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 카디프생명 배치
											 */
											BatchClientKFI51 batchClient = new BatchClientKFI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("ALI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 알리안츠생명 배치
											 */
											BatchClientALI51 batchClient = new BatchClientALI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("SSI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 삼성생명 배치
											 */
											BatchClientSSI51 batchClient = new BatchClientSSI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();
										
										} else if ("MII51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 미래에셋생명 배치
											 */
											BatchClientMII51 batchClient = new BatchClientMII51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("NHI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 농협생명 배치
											 */
											BatchClientNHI51 batchClient = new BatchClientNHI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else if ("NRI51".equals(this.batchHostInfo.getStrFepid())) {
											/*
											 * 노란우산공제 배치
											 */
											BatchClientNRI51 batchClient = new BatchClientNRI51(
													this.batchFolder,
													this.batchTrInfo,
													this.batchHostInfo,
													strFileName,
													strChkFileName
													);
											batchClient.execute();

										} else {
											/*
											 * 그 외 배치
											 */
										}
									}
									
								}
							}
						}  // for (int i...)
						
					}  // while (iterKeyFolder.hasNext())
					
					if (flag) {
						
						if (!flag) System.exit(0);
						/*
						 * 특정시간동안 기다리고 다시 배치처리를 한다.
						 * 예상시간은 10분으로 한다.
						 */
						try {
							// Thread.sleep(10 * 60 * 1000);
							Thread.sleep(1 * 60 * 1000);
						} catch (InterruptedException e) {}
					}
					
				}  // while (true)
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}   // if(flag)
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
		
		if (!flag) {    // DATE.2013.06.22
			try {
				String strFileName = "01_20130626_9100210861_HD.tXt";
				strFileName = "DN_20130510.TXT";
				
				String strResult = strFileName.replaceAll(strFileName.substring(3, 11), "YYYYMMDD");
				
				System.out.println("strFileName = [" + strFileName + "]");
				System.out.println("strResult   = [" + strResult + "]");
				
				String strKey = "01_20130626_9100210861_HD.tXt";
				strKey = strKey.replace(strKey.substring(3, 11), "YYYYMMDD");
				System.out.println("strKey = [" + strKey + "]");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {
			try {
				String strName = "D:/KANG/WORK/workspace/IB_Vela.2/server";
				File file = new File(strName);
				System.out.println("[" + file.getName() + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {
			String strLine = "YYYYMMDDhhmmss|D:/server/|DN_YYYYMMDD.TXT|YYYYMMDDhhmmss|R_BCC_FMA26000003_YYYYMMDD_01|SUCCESS";
			//strLine = "YYYYMMDDhhmmss D:/server/ DN_YYYYMMDD.TXT YYYYMMDDhhmmss R_BCC_FMA26000003_YYYYMMDD_01 SUCCESS";
			strLine = "YYYYMMDDhhmmss;D:/server/;DN_YYYYMMDD.TXT;YYYYMMDDhhmmss;R_BCC_FMA26000003_YYYYMMDD_01;SUCCESS";
			String[] item = strLine.split(" ");
			item = strLine.split("|");  // 출력 : 95
			item = strLine.split("^");  // 출력 : 1
			item = strLine.split(";");
			
			System.out.println(">" + item.length);
		}
		
		if (flag) {
			String strLine = "D:\\abc\\ddd/eee";
			System.out.println("[" + strLine.replace('\\', '/') + "]");
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
				new BatchClientMain().execute();
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
