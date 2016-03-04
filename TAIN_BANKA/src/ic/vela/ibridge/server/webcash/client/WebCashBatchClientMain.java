package ic.vela.ibridge.server.webcash.client;

import ic.vela.ibridge.server.webcash.common.WebCashBatchChkFile;
import ic.vela.ibridge.server.webcash.common.WebCashBatchFile;
import ic.vela.ibridge.server.webcash.common.WebCashBatchFolder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * WebCashBatchClientMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchClientMain
 * @author  강석
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 * 
 * 환경파일 : D:/KANG/WORK/workspace/IB_Vela.2/server/ webcash/cfg/ webcash.properties
 *  
 */
/*==================================================================*/
public class WebCashBatchClientMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";
	
	private static final String        BASE_FOLDER         = "D:/KANG/WORK/workspace/IB_Vela.2/server/";
	private static final String        CFG_FILE            = "webcash.properties";
	
	private static final int           MAX_FOLDER_CNT      = 10;
	private static final int           MAX_FEPID_CNT       = 50;
	private static final int           MAX_CONNECTION_TRY  = 3;
	
	private static final String        DELAY_MIN           = "30";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private CharsetEncoder             encoder             = null;  // String -> Byte
	@SuppressWarnings("unused")
	private CharsetDecoder             decoder             = null;  // Byte -> String
	
	private String  strChkFileName   = null;
	
	/*
	 * 배치파일이 있는 대상 폴더
	 */
	private HashMap<String,WebCashBatchFolder>   hashMapFolder = null;
	
	/*
	 * 배치파일에 관련된 정보
	 */
	private HashMap<String,WebCashBatchFile>     hashMapFile = null;
	
	/*
	 * 배치 처리를 끝낸 파일에 대한 처리 결과
	 */
	private HashMap<String,WebCashBatchChkFile>  hashMapChkFile = null;
	
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;
	
	private int                        iRecLen             = 0;
	private int                        iRecCount           = 0;

	/*
	 * 배치파일 생성후 몇분후에 처리되는지 나타내는 변수 단위는 분으로 한다.
	 */
	private int                        iDelayMin           = 0;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	private WebCashBatchClientMain()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 * 송수신용 charset 객체를 정의한다.
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*==================================================================*/
	/**
	 * socket receiver
	 * 
	 * @param size
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private byte[] recv(final int size) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (socket == null)
			return null;
		
		byte[] buf = new byte[size];

		if (flag) {
			int ret = 0;
			int readed = 0;
			
			socket.setSoTimeout(0);
			while (readed < size) {
				ret = dis.read(buf, readed, size - readed);
				
				if (ret == 0) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {}
				} else if (ret < 0) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {}
					throw new IOException("recv EOF (-1)");
				} else {
					socket.setSoTimeout(0);
				}
				
				readed += ret;
			}
		}
		
		return buf;
	}
	
	/*==================================================================*/
	/**
	 * socket sender
	 * 
	 * @param buf
	 * @param off
	 * @param len
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void send(final byte[] buf, final int off, final int len) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (socket == null)
			return;
		
		if (flag) {
			dos.write(buf, off, len);
			dos.flush();
		}
	}
	
	/*==================================================================*/
	/**
	 * hashMap for Batch Folder
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapOfBatchFolder() throws Exception
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
				String strFileName = strBaseFolder + "webcash/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}

			if (flag) {
				/*
				 * 배치파일처리 delay 시간을 구한다. 단위는 분이다.
				 */
				
				String strDelayMin = System.getProperty("system.ib.param.delay.min", DELAY_MIN);
				this.iDelayMin = Integer.parseInt(strDelayMin);
			}
			
			if (flag) {
				/*
				 * hashMapFolder를 생성한다.
				 */
				hashMapFolder = new HashMap<String,WebCashBatchFolder> (5, 5);

				/*
				 * MAX_FOLDER_CNT 만큼 반복하면서 관련 폴더를 얻는다.
				 */
				for (int i=1; i <= MAX_FOLDER_CNT; i++) {
					
					/*
					 * Properties 정보를 읽는다.
					 */
					String strSrc = String.format("system.ib.param.batch.%d.folder.src", i);
					String strTgt = String.format("system.ib.param.batch.%d.folder.tgt", i);
					
					String strSrcFolderName = prop.getProperty(strSrc);
					String strTgtFolderName = prop.getProperty(strTgt);
					if (strSrcFolderName != null) {
						if (strTgtFolderName == null) {
							strTgtFolderName = "";
						}
						
						String strKey = Integer.toString(i);
						
						/*
						 * 저장객체를 생성한다.
						 */
						WebCashBatchFolder folder = new WebCashBatchFolder(strKey, strSrcFolderName, strTgtFolderName);
						
						/*
						 * 저장객체를 hashMapFolder에 저장한다.
						 */
						hashMapFolder.put(strKey, folder);
					}
				}
			}
			
			if (flag) {  // TO DO DATE.2013.06.27
				
				String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
				
				System.out.println("----------------- getHashMapOfBatchFolder [" + strDateTime + "] ------------------");
				
				/*
				 * hashMapFolder 를 확인 출력한다.
				 */
				Set<String> setKeys = hashMapFolder.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					WebCashBatchFolder info = hashMapFolder.get(key);
					
					info.toPrint();
				}
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * hashMap for Batch File
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getHashMapOfBatchFile() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			Properties prop = null;
			
			if (flag) {
				/*
				 * Properties 파일을 읽는다.
				 * TO DO DATE.2013.06.28 : 한글처리필요 -> 한글처리 됨.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "webcash/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapFolder를 생성한다.
				 */
				hashMapFile = new HashMap<String,WebCashBatchFile> (5, 5);

				/*
				 * MAX_FEPID_CNT 만큼 반복하면서 관련 폴더를 얻는다.
				 */
				for (int i=1; i <= MAX_FEPID_CNT; i++) {
					
					/*
					 * Properties 정보를 읽는다.
					 */
					String strFepId       = prop.getProperty(String.format("system.ib.param.batchfile.%02d.fepid"      , i));
					String strName        = prop.getProperty(String.format("system.ib.param.batchfile.%02d.name"       , i));
					String strTrCode      = prop.getProperty(String.format("system.ib.param.batchfile.%02d.trcode"     , i));
					String strSrcFileName = prop.getProperty(String.format("system.ib.param.batchfile.%02d.file.src"   , i));
					String strTgtFileName = prop.getProperty(String.format("system.ib.param.batchfile.%02d.file.tgt"   , i));
					String strHostIp      = prop.getProperty(String.format("system.ib.param.batchfile.%02d.host.ip"    , i));
					String strHostPort    = prop.getProperty(String.format("system.ib.param.batchfile.%02d.host.port"  , i));
					String strBackupPath  = prop.getProperty(String.format("system.ib.param.batchfile.%02d.path.backup", i));
					String strOrgPath     = prop.getProperty(String.format("system.ib.param.batchfile.%02d.path.org"   , i));
					
					if (!flag) System.out.println("(" + i + ") " + strFepId);
					
					if (strFepId != null) {
						
						/*
						 * TO DO : 적당한 Key를 선택한다.
						 * properties 파일에서 key 에 해당하는 값을 넣는다.
						 */
						String strKey = strFepId;
						strKey = strSrcFileName;
						
						/*
						 * 저장객체를 생성한다.
						 */
						WebCashBatchFile file = new WebCashBatchFile(strFepId, strName, strTrCode
								, strSrcFileName, strTgtFileName, strHostIp, strHostPort, strBackupPath
								, strOrgPath);
						
						/*
						 * 저장객체를 hashMapFile 에 저장한다.
						 */
						hashMapFile.put(strKey, file);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- WebCashBatchFile ------------------");
				
				/*
				 * hashMapFolder 를 확인 출력한다.
				 */
				Set<String> setKeys = hashMapFile.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					WebCashBatchFile info = hashMapFile.get(key);
					
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
	private void getHashMapOfChkFile() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (flag) {

			if (flag) {
				/*
				 * check file 명을 얻는다.
				 */
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				strChkFileName = strBaseFolder + "webcash/chk/YYYYMMDD.chk";
				
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
				hashMapChkFile = new HashMap<String,WebCashBatchChkFile> (5, 5);

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
						WebCashBatchChkFile chkFile = new WebCashBatchChkFile(strLine);
						
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
			
			System.out.println("----------------- WebCashBatchChkFile ------------------");
			
			/*
			 * hashMapFolder 를 확인 출력한다.
			 */
			Set<String> setKeys = hashMapChkFile.keySet();
			Iterator<String> iterKeys = setKeys.iterator();
			
			while (iterKeys.hasNext()) {
				String key = iterKeys.next();
				WebCashBatchChkFile info = hashMapChkFile.get(key);
				
				info.toPrint();
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * 서버에 접속하고 배치자료를 송신한다.
	 * 
	 * @param strFileName
	 * @param infoBatchFile
	 */
	/*------------------------------------------------------------------*/
	private boolean doConnectAndSendData(File fileFolder, File file, WebCashBatchFile infoBatchFile)
	{
		boolean flag = true;
		
		if (flag) {
			
			/*
			 * Host 접속 정보
			 */
			if (flag) System.out.println(String.format("접속[%s:%d]", infoBatchFile.getStrHostIp(), infoBatchFile.getIntHostPort()));
			
			if (flag) {    // DATE.2013.06.27
				try {
					/* 접속시도를 한다. 2초간격으로 최대 MAX_CONNECTION_TRY번 접속시도
					 *  to get the socket connected to server
					 *  connection try count is n
					 */
					socket = null;
					for (int i=0; socket == null && i < MAX_CONNECTION_TRY; i++) {
						
						try {
							socket = new Socket(infoBatchFile.getStrHostIp(), infoBatchFile.getIntHostPort());
						} catch (Exception e) {
							// e.printStackTrace();
							if (flag) System.out.println("ERROR : couldn't connect to server. TRYCNT[" + (i+1) + "]");
							
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e1) {}
						}
					}
					
					if (socket == null) {
						/*
						 * 접속시도 결과 null 이면 10분 후에 다시 접속시도 한다.
						 */
						System.out.println("ERROR : socket not connect to Server.....");
						return false;
					}

					if (flag) System.out.println("CLIENT : socket connection to Server.....");
					
					/*
					 *  make input/output stream
					 */
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			
			/*
			 * 배치파일을 전송한다.
			 */
			if (flag) System.out.println(String.format("처리할 배치파일 [%s]", file.getAbsoluteFile()));
			
			if (flag) {   // DATE.2013.06.27
				try {
					/*
					 *  파일을 open 한다.
					 */
					BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
					
					/*
					 *  파일을 읽어 출력한다.
					 */
					String strLine = null;
					
					this.iRecCount = 0;
					
					while ((strLine=br.readLine()) != null) {
						
						/*
						 *  transfer from the String line data to a byte line data
						 */
						byte[] bLine = strLine.getBytes();
						this.iRecLen = bLine.length;
						byte[] bSize = new DecimalFormat("0000").format(this.iRecLen).getBytes();

						/*
						 *  send a length data to the client
						 */
						send(bSize, 0, 4);

						/*
						 *  send the byte line data to the client
						 */
						send(bLine, 0, this.iRecLen);
						
						this.iRecCount ++;
						
						// display the the byte line data
						if (flag) System.out.println(String.format("[%s][%s]", new String(bSize), strLine));
					}
					
					// file close
					br.close();
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			if (!flag) {    // DATE.2013.06.27
				try {
					/*
					 *  파일을 open 한다.
					 */
					LineNumberReader lnr = new LineNumberReader(new FileReader(file.getAbsoluteFile()));
					
					/*
					 *  파일을 읽어 출력한다.
					 */
					String strLine = null;
					int iLine = 0;
					while (true) {
						
						if (flag) {
							strLine = lnr.readLine();
							if (strLine == null) {
								/*
								 * file EOF
								 */
								break;
							}
							
							iLine = lnr.getLineNumber();

							// print the line readed 
							if (flag) System.out.println(String.format("[%05d] [%s]", iLine, strLine));
							
						}
						
						if (flag) {
							;
						}
					}
					
					lnr.close();

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}


			/*
			 * Host close
			 */
			if (flag) {
				try {
					/*
					 *  close the session socket and stream in/out
					 */
					if (socket != null) {
						dis.close();
						dos.close();
						socket.close();
						
						socket = null;
					}
				
					if (flag) System.out.println("close Socket.....");

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * 정상처리된 결과를 chk file에 기록한다.
	 */
	/*------------------------------------------------------------------*/
	private void doPostProcess(File fileFolder, File file, WebCashBatchFile infoBatchFile)
	{
		boolean flag = true;
		
		if (flag) {
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
			
			//////////////////////////////////////////////////////////////////
			/*
			 * 처리일시
			 */
			String strJobDateTime = strDateTime;
			
			/*
			 * 파일 path
			 */
			String strJobPath = fileFolder.getPath().replace('\\', '/');
			
			/*
			 * 배치파일명
			 */
			String strJobFileName = file.getName();
					
			/*
			 * 배치파일 속성일시
			 */
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * 서버에 생성되는 배치파일명
			 */
			String strSendFileName = infoBatchFile.getStrTgtFileName().replaceAll("YYYYMMDD", strDate);
			
			/*
			 * 처리결과.
			 */
			String strStat = "SUCCESS";
			
			//////////////////////////////////////////////////////////////////
			/*
			 * 결과 문자열을 만든다.
			 */
			String strResultLine = String.format("%s;%s;%s;%s;%s;%05d;%05d;%s", strJobDateTime
					, strJobPath, strJobFileName, strFileDateTime, strSendFileName, this.iRecLen, this.iRecCount, strStat);
			if (flag) System.out.println("[" + strResultLine + "]");
			
			/*
			 * 처리결과를 기록한다.
			 */
			if (flag) {
				try {
					PrintStream ps = new PrintStream(new FileOutputStream(this.strChkFileName, true), true, CHARSET);
					ps.println(strResultLine);
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
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
					getHashMapOfBatchFolder();

					/*
					 * 배치처리 파일명 정보를 얻는다.
					 */
					getHashMapOfBatchFile();
					
					/*
					 * 배치처리를 마친 파일 정보를 얻는다.
					 */
					getHashMapOfChkFile();
					
					//////////////////////////////////////////////////////////////////
					/*
					 * 배치처리폴더를 읽는다.
					 */
					Set<String> setKeyFolder = hashMapFolder.keySet();
					Iterator<String> iterKeyFolder = setKeyFolder.iterator();
					
					while (iterKeyFolder.hasNext()) {
						/*
						 * 배치처리폴더명을 얻는다.
						 */
						String keyFolder = iterKeyFolder.next();
						WebCashBatchFolder batchFolder = hashMapFolder.get(keyFolder);
						
						File fileFolder = new File(batchFolder.getStrSrcFolder());
						if (!flag) System.out.println("PATH=" + fileFolder.getAbsolutePath());   // 출력:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getCanonicalPath());  // 출력:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getParent());         // 출력:PATH=D:\KANG\WORK\webCash
						if (!flag) System.out.println("PATH=" + fileFolder.getPath());           // 출력:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getName());           // 출력:PATH=data3
						
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
											Date date = new Date();
											String strCompareDate = new SimpleDateFormat("yyyyMMdd000000", Locale.KOREA).format(date);
											String strDelayDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(date.getTime() - (iDelayMin * 60 * 1000));
											String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
											
											if (!flag) {   // TO DO : 테스트용 20130515000000
												strCompareDate = "20130515000000";
												//strCompareDate = "20130607000000";
												if (!flag) System.out.println("[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
											}

											/*
											 * 파일 속성일시에 해당하는 경우
											 * 파일의 속성날짜가 아래와 같이 되어 있어야 한다.
											 *     yyyyMMdd000000 <= strLastModifiedDate <= strDelayDate(00분 이전)
											 */
											if ((strCompareDate.compareTo(strLastModifiedDate) <= 0) && (strLastModifiedDate.compareTo(strDelayDate) <= 0)) {
												if (!flag) System.out.println("\t속성일시OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
												/*
												 * TO DO : hashMapBatchFile에서 파일명이 조건에 맞는 경우를 찾는다.
												 * 조건에 맞는 파일은 배치처리를 해야하는 파일들이다.
												 * key를 구성한다. 파일명.replace(3, 8) <- YYYYMMDD
												 */
												String strKey = file.getName();
												strKey = strKey.replace(strKey.substring(3, 11), "YYYYMMDD");
												
												WebCashBatchFile batchFile = hashMapFile.get(strKey);
												if (batchFile != null) {
													/*
													 * 이 파일은 해당일에도 속하고
													 * 배치파일 목록에도 포함된 파일이다.
													 */
													if (!flag) System.out.println("\t\t배치목록OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
													
													return true;
												}
											}
										}
										
										return false;
									}  // public boolean accept(File file)
								}
								////////////////////////////////////////////////////////////////////
								);
						
						if (files == null) {
							System.out.println("files is null point.......강석.");
							
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
								WebCashBatchChkFile obj = (WebCashBatchChkFile) hashMapChkFile.get(strKey);
								if (obj == null) {
									/*
									 * 배치처리를 한다.
									 */
									strKey = files[i].getName();
									strKey = strKey.replace(strKey.substring(3, 11), "YYYYMMDD");
									
									WebCashBatchFile batchFile = hashMapFile.get(strKey);
									if (batchFile != null) {
										/*
										 * 이 파일은 해당일에도 속하고
										 * 배치파일 목록에도 포함된 파일이다.
										 */
										if (flag) System.out.println("[KEY=" + strKey + "] > 배치처리 할 파일을 찾았습니다.[" + files[i].getPath() + "]");
										if (flag) batchFile.toPrint();
										
										//////////////////////////////////////////////////////////////////
										/*
										 * Host 접속하고 배치자료를 송신한다.
										 */
										if (flag && doConnectAndSendData(fileFolder, files[i], batchFile)) {
											/*
											 * 처리가 정상적으로 끝났으면 처리결과를 기록한다.
											 */
											doPostProcess(fileFolder, files[i], batchFile);
										}
										//////////////////////////////////////////////////////////////////
										
										if (flag) System.out.println();
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
							Thread.sleep(10 * 60 * 1000);
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
		
		if (!flag) {
			String strLine = "D:\\abc\\ddd/eee";
			System.out.println("[" + strLine.replace('\\', '/') + "]");
		}
		
		if (flag) {
			Date date = new Date();
			
			String strNowDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(date);
			String strDelayDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(date.getTime() - (30 * 60 * 1000));

			System.out.println("[" + strNowDate + "] [" + strDelayDate + "]");
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
				new WebCashBatchClientMain().execute();
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
