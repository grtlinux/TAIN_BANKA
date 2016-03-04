package ic.vela.ibridge.serverbatch.hwi51.server;

import ic.vela.ibridge.serverbatch.hwi51.common.BatchFile;
import ic.vela.ibridge.serverbatch.hwi51.common.BatchFileQueue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/*==================================================================*/
/**
 * WebCashBatchServerMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchServerMain
 * @author  강석
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 *  
 */
/*==================================================================*/
public class BatchServerMain
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String        CHARSET             = "EUC-KR";

	private static final String        BASE_FOLDER         = "D:/KANG/WORK/workspace/IB_Vela.2/server/";
	private static final String        CFG_FILE            = "webcash.properties";

	private static final String        DEFAULT_FOLDER      = "D:/KANG/WORK/webCash/data3/YYYYMMDD/";
	private static final String        DEFAULT_SEQ         = "D:/KANG/WORK/webCash/ap_batch/DAT/YYYYMMDD/";
	private static final String        DEFAULT_DAT         = "D:/KANG/WORK/webCash/ap_batch/SEQ/YYYYMMDD/";
	
	private static final String        DEFAULT_FEPID       = "BCC51";
	
	private static final int           MAX_FEPID_CNT       = 50;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private CharsetEncoder             encoder             = null;  // String -> Byte
	@SuppressWarnings("unused")
	private CharsetDecoder             decoder             = null;  // Byte -> String
	
	private ServerSocket               serverSocket        = null;
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;
	private PrintStream                ps                  = null;

	private String                     strFepId            = null;  // FEPID
	private String                     strServiceName      = null;  // OFR+FEPID+01
	private String                     strBatchFileFolder  = null;  // 배치파일 생성폴더
	private String                     strBatchSeqFolder   = null;  // FQ seq 폴더
	private String                     strBatchDatFolder   = null;  // FQ dat 폴더
	
	/*
	 * 배치파일에 관련된 정보
	 */
	private HashMap<String,BatchFile>   hashMapFile = null;

	private BatchFile           infoBatchFile       = null;
	
	private String                     strRecLen           = null;
	private String                     strTrCodeSeq        = null;
	private int                        iRecLen             = 0;
	private int                        iRecCount           = 0;
	
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
			try {
				/*
				 * 송수신용 charset 객체를 정의한다.
				 */
				Charset charset = Charset.forName(CHARSET);
				this.encoder = charset.newEncoder();
				this.decoder = charset.newDecoder();
				
				strFepId = System.getProperty("system.ib.param.fepid", DEFAULT_FEPID);
				strServiceName = "OFR" + strFepId + "01";
				
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
	private byte[] recv(final int size) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
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
	@SuppressWarnings("unused")
	private void send(final byte[] buf, final int off, final int len) throws Exception 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			dos.write(buf, off, len);
			dos.flush();
		}
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
				hashMapFile = new HashMap<String,BatchFile> (5, 5);

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
						// strKey = strSrcFileName;
						
						/*
						 * 저장객체를 생성한다.
						 */
						BatchFile file = new BatchFile(strFepId, strName, strTrCode
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
					BatchFile info = hashMapFile.get(key);
					
					info.toPrint();
				}
			}
			
			if (flag) {
				/*
				 * 해당 서비스 정보를 얻는다.
				 */
				infoBatchFile = hashMapFile.get(strFepId);
				
				if (flag) {
					infoBatchFile.toPrint();
					System.out.println("[FEPID=" + strFepId + "]");
					System.out.println("[SERVICE=" + strServiceName + "]");
				}
			}
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * WebCash 서버의 정보를 얻는다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void getWebCashServerInfo() throws Exception
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
				strBatchFileFolder = prop.getProperty("system.ib.param.batchfile.folder", DEFAULT_FOLDER);
				strBatchSeqFolder  = prop.getProperty("system.ib.param.filequeue.folder.seq", DEFAULT_SEQ);
				strBatchDatFolder  = prop.getProperty("system.ib.param.filequeue.folder.dat", DEFAULT_DAT);
				
				if (flag) {
					System.out.println("[strBatchFileFolder =" + strBatchFileFolder + "]");
					System.out.println("[strBatchSeqFolder  =" + strBatchSeqFolder  + "]");
					System.out.println("[strBatchDatFolder  =" + strBatchDatFolder  + "]");
				}
			}
		}
		
		if (!flag) System.exit(0);
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
			serverSocket = new ServerSocket(infoBatchFile.getIntHostPort());
			
			/*
			 * reuse address information
			 */
			serverSocket.setReuseAddress(true);
		}
	}
	
	/*==================================================================*/
	/**
	 * accept the client socket connection and make a socket
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void acceptClientSocket() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
			if (flag) System.out.println("SERVER : Listening....[PORT=" + infoBatchFile.getIntHostPort() + "]");
			/*
			 *  wait for accept... listening
			 */
			socket = serverSocket.accept();
			
			/*
			 * accepting address
			 */
			InetAddress inetAddress = socket.getInetAddress();
			if (flag) {
				System.out.println("SERVER : Accepted ");
				//System.out.println("[inetAddress.getCanonicalHostName() =" + inetAddress.getCanonicalHostName() + "]");
				//System.out.println("[inetAddress.getHostAddress()       =" + inetAddress.getHostAddress()       + "]");
				//System.out.println("[inetAddress.getHostName()          =" + inetAddress.getHostName()          + "]");
				System.out.println("[inetAddress.toString() =" + inetAddress.toString() + "]");
			}
			
			/*
			 *  make input/output stream
			 */
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		}
		
		if (!flag) System.exit(0);
	}
	
	/*==================================================================*/
	/**
	 * receive the batch data from the web cash.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void recvFileData() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (flag) {
			/*
			 * open a batch file
			 */
			try {
				
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strFile = strBatchFileFolder + infoBatchFile.getStrTgtFileName();
				strFile = strFile.replaceAll("YYYYMMDD", strDate);
				
				/*
				 * TODO DATE.2013.06.28 아래부분은 로직이 변경되면 수정이 필요
				 */
				for (int i=1; i < 100; i++) {
					strTrCodeSeq = String.format("%02d", i);
					String strFileName = strFile.replaceAll("XX", strTrCodeSeq);
					
					/*
					 * 파일이 이미 있으면 SEQ를 증가시킨다.
					 */
					File file = new File(strFileName);
					if (!file.exists() || file.length() == 0) {
						/*
						 * 해당파일명이 존재하지 않거나
						 * 해당파일의 크기가 0 인 경우
						 */
						strFile = strFileName;
						break;
					}
				}
				
				/*
				 *  create the data file received from the server
				 */
				ps = new PrintStream(strFile);
				if (flag) System.out.println("SERVER : create Data File.....[" + strFile + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			/*
			 * receive the batch data and write the data to the batch file
			 */
			try {
				
				this.iRecCount = 0;
				
				while (true) {
					/*
					 *  receive a length data from the server socket
					 */
					byte[] bSize = recv(4);
					this.strRecLen = new String(bSize);
					this.iRecLen = Integer.parseInt(this.strRecLen);
					
					/*
					 *  receive a data line from the server socket
					 */
					byte[] bLine = recv(this.iRecLen);
					
					/*
					 *  write the data line to the data file
					 */
					ps.println(new String(bLine, CHARSET));
					this.iRecCount ++;

					// display the the byte line data
					if (flag) System.out.println(String.format("[%04d][%04d][%s]", iRecCount, iRecLen, new String(bLine, CHARSET)));
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				/*
				 *  close the data file from the server
				 */
				ps.close();

				if (flag) System.out.println("close Data File.....");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * close the client socket
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void closeClientSocket() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				/*
				 *  close the session socket and stream in/out
				 */
				dos.close();
				dis.close();
				socket.close();

				if (flag) System.out.println("close Socket.....");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * write the transferm information to the file queue
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void writeFileQueue() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 *  set the information to the file queue
			 */
			
			BatchFileQueue fileQueue = new BatchFileQueue(infoBatchFile);
			
			fileQueue.writeFileQueueInfo(strTrCodeSeq, iRecLen, iRecCount);
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
				/////////////////////////////////////
				/*
				 * 배치처리 파일명 정보를 얻는다.
				 */
				getHashMapOfBatchFile();
				
				/*
				 * WebCash 서버정보를 얻는다.
				 */
				getWebCashServerInfo();
				
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
					
					while (true) {
						/*
						 *  listen a socket from client
						 */
						acceptClientSocket();

						if (socket != null) {
							/*
							 *  receive file data
							 */
							recvFileData();
							
							/*
							 *  close the client socket
							 */
							closeClientSocket();
							
							/*
							 * write batch file queue
							 * WebCash 정보를 읽어 file queue 를 실행한다.
							 */
							if (flag) writeFileQueue();
						}
					}
				}
				
				/*
				 * 
				 */
				if (flag) {   // DATE.2013.06.27
					;
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
