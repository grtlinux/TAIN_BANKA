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
 * BatchClientMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchClientMain
 * @author  ����
 * @version 1.0, 2013/07/06
 * @since   jdk1.6.0_45
 * 
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 * 
 * ȯ������ : D:/KANG/WORK/workspace/IB_Vela.2/server/ bat/cfg/ bat.properties
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
	 * ��ġ������ �ִ� ��� ����
	 */
	private HashMap<String,BatchFolder>     hashMapFolder  = null;
	private BatchFolder                     batchFolder    = null;
	
	/*
	 * ��ġTR ���õ� ����
	 */
	private HashMap<String,BatchTrInfo>     hashMapTrInfo  = null;
	private BatchTrInfo                     batchTrInfo    = null;
	
	/*
	 * ��ġInfo ���õ� ����
	 */
	private HashMap<String,BatchHostInfo>   hashMapHostInfo = null;
	private BatchHostInfo                   batchHostInfo   = null;
	
	/*
	 * ��ġ ó���� ���� ���Ͽ� ���� ó�� ���
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
				 * Properties ������ �д´�.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapFolder�� �����Ѵ�.
				 */
				this.hashMapFolder = new HashMap<String,BatchFolder> (5, 5);
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());

				/*
				 * MAX_FOLDER_CNT ��ŭ �ݺ��ϸ鼭 ���� ������ ��´�.
				 */
				for (int i=1; i <= MAX_FOLDER_CNT; i++) {
					
					/*
					 * Properties ������ �д´�.
					 */
					String strNo = String.format("%02d", i);
					String strSrcFolder = prop.getProperty(String.format("system.ib.param.batch.folder.%s.src", strNo));
					String strTgtFolder = prop.getProperty(String.format("system.ib.param.batch.folder.%s.tgt", strNo));
					if (strSrcFolder != null) {
						if (strTgtFolder == null) {
							strTgtFolder = "";
						}
						
						/*
						 * TO DO : ������ Key�� �����Ѵ�.
						 * properties ���Ͽ��� key �� �ش��ϴ� ���� �ִ´�.
						 */
						String strKey = strNo;

						/*
						 * ���尴ü�� �����Ѵ�.
						 */
						strSrcFolder = strSrcFolder.replace("YYYYMMDD", strDate);
						strTgtFolder = strTgtFolder.replace("YYYYMMDD", strDate);
						
						BatchFolder info = new BatchFolder(strNo, strSrcFolder, strTgtFolder);
						info.setStrKey(strKey);
						
						/*
						 * ���尴ü�� hashMapFolder�� �����Ѵ�.
						 */
						this.hashMapFolder.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.07.06
				
				String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
				
				System.out.println("----------------- getHashMapBatchFolder [" + strDateTime + "] ------------------");
				
				/*
				 * hashMapFolder �� Ȯ�� ����Ѵ�.
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
				 * Properties ������ �д´�.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapTrInfo �����Ѵ�.
				 */
				this.hashMapTrInfo = new HashMap<String,BatchTrInfo> (5, 5);

				/*
				 * MAX_TRINFO_CNT ��ŭ �ݺ��ϸ鼭 ���� ������ ��´�.
				 */
				for (int i=1; i <= MAX_TRINFO_CNT; i++) {
					
					/*
					 * Properties ������ �д´�.
					 */
					String strNo    = String.format("%02d", i);
					String strName  = prop.getProperty(String.format("system.ib.param.batch.tr.%s.name", strNo));
					String strCode  = prop.getProperty(String.format("system.ib.param.batch.tr.%s.code", strNo));
					
					if (!flag) System.out.println("(" + i + ") " + strCode);
					
					if (strName != null) {
						
						/*
						 * TO DO : ������ Key�� �����Ѵ�.
						 * properties ���Ͽ��� key �� �ش��ϴ� ���� �ִ´�.
						 */
						String strKey = strCode;
						
						/*
						 * ���尴ü�� �����Ѵ�.
						 */
						BatchTrInfo info = new BatchTrInfo(strNo, strName, strCode);
						info.setStrKey(strKey);
						
						/*
						 * ���尴ü�� hashMapTrInfo �� �����Ѵ�.
						 */
						this.hashMapTrInfo.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- getHashMapBatchTrInfo ------------------");
				
				/*
				 * hashMapTrInfo �� Ȯ�� ����Ѵ�.
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
				 * Properties ������ �д´�.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "bat/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapHostInfo�� �����Ѵ�.
				 */
				this.hashMapHostInfo = new HashMap<String,BatchHostInfo> (5, 5);

				/*
				 * MAX_FEPID_CNT ��ŭ �ݺ��ϸ鼭 ���� ������ ��´�.
				 */
				for (int i=1; i <= MAX_HOSTINFO_CNT; i++) {
					
					/*
					 * Properties ������ �д´�.
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
						 * TO DO : ������ Key�� �����Ѵ�.
						 * properties ���Ͽ��� key �� �ش��ϴ� ���� �ִ´�.
						 */
						String strKey = strInsid;
						
						/*
						 * ���尴ü�� �����Ѵ�.
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
						 * ���尴ü�� hashMapFile �� �����Ѵ�.
						 */
						this.hashMapHostInfo.put(strKey, info);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- getHashMapBatchHostInfo ------------------");
				
				/*
				 * hashMapHostInfo �� Ȯ�� ����Ѵ�.
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
	 *     ó���Ͻ�       ������ġ   ���ϸ�          ���ϼӼ��Ͻ�   �۽ŵ����ϸ�         ���ڵ���� ���ڵ�Ǽ�    ó�����
	 *     YYYYMMDDhhmmss;D:/server/;DN_YYYYMMDD.TXT;YYYYMMDDhhmmss;R_BCC_FMA26000003_YYYYMMDD_01;00999;00999;SUCCESS
	 *     
	 *     �۾��Ͻ�;������ġ;��ġ����;���ϼӼ��Ͻ�;�۽ŵ����ϸ�;���ڵ����;���ڵ�Ǽ�;������
	 *     
	 * DATE.2013.06.28 : ���ڵ����, ���ڵ�Ǽ� �߰�
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
				 * check file ���� ��´�.
				 */
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				strChkFileName = strBaseFolder + "bat/chk/YYYYMMDD.chk";
				
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				strChkFileName = strChkFileName.replace("YYYYMMDD", strDate);
				
				/*
				 * TO DO : ������ ������ �����Ѵ�.
				 */
				if (!new File(strChkFileName).exists()) {
					try {
						PrintStream ps = new PrintStream(new FileOutputStream(this.strChkFileName, true), true, CHARSET);
						String strInfoLine = "# �۾��Ͻ�;��ġ������ġ;��ġ����;���ϼӼ��Ͻ�;������ġ����;���ڵ����;���ڵ�Ǽ�;������";
						ps.println(strInfoLine);
						ps.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			if (flag) {
				/*
				 * hashMapChkFile �����Ѵ�.
				 */
				hashMapChkFile = new HashMap<String,BatchChkFile> (5, 5);

				/*
				 * ������ open �Ѵ�.
				 * �� ������ �о� WebCashBatchChkFile ��ü�� �����Ѵ�.
				 * ������ close �Ѵ�.
				 */

				FileReader fr = new FileReader(strChkFileName);
				LineNumberReader lnr = new LineNumberReader(fr);
				
				// ������ �о� ����Ѵ�.
				while (true) {
					
					/*
					 * ������ �д´�.
					 * null �����̸� EOF �� �ȴ�.
					 */
					String strLine = lnr.readLine();
					if (strLine == null) {
						break;
					}
					
					/*
					 * ���ι�ȣ�� ���Ѵ�.
					 */
					int iLine = lnr.getLineNumber();
					
					/*
					 * comment�� skip�Ѵ�.
					 * �� '#'�� comment�� ���Ѵ�.
					 */
					if (strLine.trim().charAt(0) == '#') {
						if (!flag) System.out.println(String.format("[%02d] [%s] COMMENT SKIP", iLine, strLine));
						continue;
					} else {
						if (!flag) System.out.println(String.format("[%02d] [%s]", iLine, strLine));
					}
					
					if (flag) {
						/*
						 * ���尴ü�� �����Ѵ�.
						 */
						BatchChkFile chkFile = new BatchChkFile(strLine);
						
						/*
						 * ���尴ü�� hashMapChkFile �� �����Ѵ�.
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
			 * hashMapFolder �� Ȯ�� ����Ѵ�.
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
	 * process ����
	 */
	/*------------------------------------------------------------------*/
	private void execute()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.26
			
			try {
				/*
				 * �ֱ������� �ݺ��ϸ鼭 ��ġ ������ �˻��Ѵ�.
				 */
				while (true) {
					
					//////////////////////////////////////////////////////////////////
					/*
					 * ��ġó�� ���� ������ ��´�.
					 */
					getHashMapBatchFolder();

					/*
					 * ��ġó�� TR ������ ��´�.
					 */
					getHashMapBatchTrInfo();
					
					/*
					 * ��ġó�� HOST ������ ��´�.
					 */
					getHashMapBatchHostInfo();
					
					/*
					 * ��ġó���� ��ģ ���� ������ ��´�.
					 */
					getHashMapChkFile();
					
					if (!flag) System.exit(0);
					
					//////////////////////////////////////////////////////////////////
					/*
					 * ��ġó�������� �д´�.
					 */
					Set<String> setKeyFolder = this.hashMapFolder.keySet();
					Iterator<String> iterKeyFolder = setKeyFolder.iterator();
					
					while (iterKeyFolder.hasNext()) {
						/*
						 * ��ġó���������� ��´�.
						 */
						String keyFolder = iterKeyFolder.next();
						this.batchFolder = this.hashMapFolder.get(keyFolder);
						
						File fileFolder = new File(this.batchFolder.getStrSrcFolder());
						if (!flag) {
							if (flag) System.out.println("PATH=" + fileFolder.getAbsolutePath());   // ���:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getCanonicalPath());  // ���:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getParent());         // ���:PATH=D:\KANG\WORK\webCash
							if (flag) System.out.println("PATH=" + fileFolder.getPath());           // ���:PATH=D:\KANG\WORK\webCash\data3
							if (flag) System.out.println("PATH=" + fileFolder.getName());           // ���:PATH=data3
						}
						
						/*
						 * ���ϳ����� ���ϵ��� ��´�.
						 */
						File[] files = fileFolder.listFiles(     // FileFilter ó���� �Ѵ�.
								////////////////////////////////////////////////////////////////////
								new FileFilter() {
									public boolean accept(File file) {
										boolean flag = true;
										
										if (flag) {
											/*
											 * ������ �ƴϸ� false �� �����ش�.
											 */
											if (!file.isFile()) {
												return false;
											}
										}
										
										if (flag) {
											/*
											 * ���� �Ӽ� ��¥�� ��´�.
											 * ���糯¥�� �ش��ϴ� ���ϵ鸸 ���� �� �ֵ��� �Ѵ�.
											 */

											String strCompareDate = new SimpleDateFormat("yyyyMMdd000000", Locale.KOREA).format(new Date());
											String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
											
											if (!flag) {   // TO DO : �׽�Ʈ�� 20130515000000
												strCompareDate = "20130515000000";
												//strCompareDate = "20130607000000";
												if (!flag) System.out.println("[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
											}

											/*
											 * ���� �Ӽ��Ͻÿ� �ش��ϴ� ���
											 */
											if (strCompareDate.compareTo(strLastModifiedDate) <= 0) {
												if (!flag) System.out.println("�Ӽ��Ͻ�OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
												/*
												 * TO DO : ���ϸ��� ���ǿ� �´� ��츦 ã�´�.
												 * 1. substring(0,3) -> �����
												 * 2. substring(3,6) -> �ŷ��ڵ�
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
													 * ���õȺ���簡 ����.
													 */
													return false;
												}
												
												if (hashMapTrInfo.get(strBizCode) == null) {
													/*
													 * ���õ� ��ġ������ ����.
													 */
													return false;
												}
												
												/*
												 * ó���� ��ġ������ ã�Ҵ�.
												 */
												if (!flag) System.out.println("��ġ���OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + strFileName + "]");
												
												return true;
											}
										}
										
										return false;
									}  // public boolean accept(File file)
								}
								////////////////////////////////////////////////////////////////////
								);
						
						if (files == null) {
							if (flag) System.out.println("files is null point.......����.");
							
							try {
								// Thread.sleep(10 * 60 * 1000);
								Thread.sleep(5 * 1000);
							} catch (InterruptedException e) {}
							
							continue;
						}
						
						/*
						 * ���ϳ�¥�� ���ϵ��� �����鼭
						 * ó���� ���� skip �ϰ� �׷��� ���� ����
						 * ��ġó���� �ϰ� chk ���Ͽ� ����Ѵ�.
						 */
						for (int i=0; i < files.length; i++) {
							/*
							 * ���ϵ鸸 �����Ѵ�.
							 */
							if (files[i].isFile()) {
								/*
								 * �� key�� ��� hashMap ���� �˻��Ѵ�.
								 */
								String strFileName = files[i].getName();
								String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(files[i].lastModified()));
								String strKey = strFileName + strLastModifiedDate;
								if (!flag) {
									System.out.println("[KEY=" + strKey + "]");
								}
								
								/*
								 * obj �� null�� �ƴϸ� ���õ� ��ġ ������ ��ó���� �����̴�.
								 * �׷��� null �� ��찡 ó���ؾ��ϴ� ��ġ�����̴�.
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
											 * ��ȭ���� ��ġ
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
											 * ���ѻ��� ��ġ
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
											 * �ϳ����� ��ġ
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
											 * �ﱹ���� ��ġ
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
											 * ī�������� ��ġ
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
											 * �˸��������� ��ġ
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
											 * �Ｚ���� ��ġ
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
											 * �̷����»��� ��ġ
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
											 * �������� ��ġ
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
											 * ��������� ��ġ
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
											 * �� �� ��ġ
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
						 * Ư���ð����� ��ٸ��� �ٽ� ��ġó���� �Ѵ�.
						 * ����ð��� 10������ �Ѵ�.
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
			item = strLine.split("|");  // ��� : 95
			item = strLine.split("^");  // ��� : 1
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
