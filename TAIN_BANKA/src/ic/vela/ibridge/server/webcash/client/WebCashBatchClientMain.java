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
 * WebCashBatchClientMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchClientMain
 * @author  ����
 * @version 1.0, 2013/06/26
 * @since   jdk1.6.0_45
 * 
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 * 
 * ȯ������ : D:/KANG/WORK/workspace/IB_Vela.2/server/ webcash/cfg/ webcash.properties
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
	 * ��ġ������ �ִ� ��� ����
	 */
	private HashMap<String,WebCashBatchFolder>   hashMapFolder = null;
	
	/*
	 * ��ġ���Ͽ� ���õ� ����
	 */
	private HashMap<String,WebCashBatchFile>     hashMapFile = null;
	
	/*
	 * ��ġ ó���� ���� ���Ͽ� ���� ó�� ���
	 */
	private HashMap<String,WebCashBatchChkFile>  hashMapChkFile = null;
	
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;
	
	private int                        iRecLen             = 0;
	private int                        iRecCount           = 0;

	/*
	 * ��ġ���� ������ ����Ŀ� ó���Ǵ��� ��Ÿ���� ���� ������ ������ �Ѵ�.
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
				 * �ۼ��ſ� charset ��ü�� �����Ѵ�.
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
				 * Properties ������ �д´�.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "webcash/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}

			if (flag) {
				/*
				 * ��ġ����ó�� delay �ð��� ���Ѵ�. ������ ���̴�.
				 */
				
				String strDelayMin = System.getProperty("system.ib.param.delay.min", DELAY_MIN);
				this.iDelayMin = Integer.parseInt(strDelayMin);
			}
			
			if (flag) {
				/*
				 * hashMapFolder�� �����Ѵ�.
				 */
				hashMapFolder = new HashMap<String,WebCashBatchFolder> (5, 5);

				/*
				 * MAX_FOLDER_CNT ��ŭ �ݺ��ϸ鼭 ���� ������ ��´�.
				 */
				for (int i=1; i <= MAX_FOLDER_CNT; i++) {
					
					/*
					 * Properties ������ �д´�.
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
						 * ���尴ü�� �����Ѵ�.
						 */
						WebCashBatchFolder folder = new WebCashBatchFolder(strKey, strSrcFolderName, strTgtFolderName);
						
						/*
						 * ���尴ü�� hashMapFolder�� �����Ѵ�.
						 */
						hashMapFolder.put(strKey, folder);
					}
				}
			}
			
			if (flag) {  // TO DO DATE.2013.06.27
				
				String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
				
				System.out.println("----------------- getHashMapOfBatchFolder [" + strDateTime + "] ------------------");
				
				/*
				 * hashMapFolder �� Ȯ�� ����Ѵ�.
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
				 * Properties ������ �д´�.
				 * TO DO DATE.2013.06.28 : �ѱ�ó���ʿ� -> �ѱ�ó�� ��.
				 */
				prop = new Properties();
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				String strCfgFile = System.getProperty("system.ib.param.cfgfile", CFG_FILE);
				String strFileName = strBaseFolder + "webcash/cfg/" + strCfgFile;
				prop.load(new InputStreamReader(new FileInputStream(strFileName), CHARSET));
			}
			
			if (flag) {
				/*
				 * hashMapFolder�� �����Ѵ�.
				 */
				hashMapFile = new HashMap<String,WebCashBatchFile> (5, 5);

				/*
				 * MAX_FEPID_CNT ��ŭ �ݺ��ϸ鼭 ���� ������ ��´�.
				 */
				for (int i=1; i <= MAX_FEPID_CNT; i++) {
					
					/*
					 * Properties ������ �д´�.
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
						 * TO DO : ������ Key�� �����Ѵ�.
						 * properties ���Ͽ��� key �� �ش��ϴ� ���� �ִ´�.
						 */
						String strKey = strFepId;
						strKey = strSrcFileName;
						
						/*
						 * ���尴ü�� �����Ѵ�.
						 */
						WebCashBatchFile file = new WebCashBatchFile(strFepId, strName, strTrCode
								, strSrcFileName, strTgtFileName, strHostIp, strHostPort, strBackupPath
								, strOrgPath);
						
						/*
						 * ���尴ü�� hashMapFile �� �����Ѵ�.
						 */
						hashMapFile.put(strKey, file);
					}
				}
			}
			
			if (!flag) {  // TO DO DATE.2013.06.27
				
				System.out.println("----------------- WebCashBatchFile ------------------");
				
				/*
				 * hashMapFolder �� Ȯ�� ����Ѵ�.
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
	private void getHashMapOfChkFile() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (flag) {

			if (flag) {
				/*
				 * check file ���� ��´�.
				 */
				String strBaseFolder = System.getProperty("system.ib.param.basefolder", BASE_FOLDER);
				strChkFileName = strBaseFolder + "webcash/chk/YYYYMMDD.chk";
				
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
				hashMapChkFile = new HashMap<String,WebCashBatchChkFile> (5, 5);

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
						WebCashBatchChkFile chkFile = new WebCashBatchChkFile(strLine);
						
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
			
			System.out.println("----------------- WebCashBatchChkFile ------------------");
			
			/*
			 * hashMapFolder �� Ȯ�� ����Ѵ�.
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
	 * ������ �����ϰ� ��ġ�ڷḦ �۽��Ѵ�.
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
			 * Host ���� ����
			 */
			if (flag) System.out.println(String.format("����[%s:%d]", infoBatchFile.getStrHostIp(), infoBatchFile.getIntHostPort()));
			
			if (flag) {    // DATE.2013.06.27
				try {
					/* ���ӽõ��� �Ѵ�. 2�ʰ������� �ִ� MAX_CONNECTION_TRY�� ���ӽõ�
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
						 * ���ӽõ� ��� null �̸� 10�� �Ŀ� �ٽ� ���ӽõ� �Ѵ�.
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
			 * ��ġ������ �����Ѵ�.
			 */
			if (flag) System.out.println(String.format("ó���� ��ġ���� [%s]", file.getAbsoluteFile()));
			
			if (flag) {   // DATE.2013.06.27
				try {
					/*
					 *  ������ open �Ѵ�.
					 */
					BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
					
					/*
					 *  ������ �о� ����Ѵ�.
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
					 *  ������ open �Ѵ�.
					 */
					LineNumberReader lnr = new LineNumberReader(new FileReader(file.getAbsoluteFile()));
					
					/*
					 *  ������ �о� ����Ѵ�.
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
	 * ����ó���� ����� chk file�� ����Ѵ�.
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
			 * ó���Ͻ�
			 */
			String strJobDateTime = strDateTime;
			
			/*
			 * ���� path
			 */
			String strJobPath = fileFolder.getPath().replace('\\', '/');
			
			/*
			 * ��ġ���ϸ�
			 */
			String strJobFileName = file.getName();
					
			/*
			 * ��ġ���� �Ӽ��Ͻ�
			 */
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * ������ �����Ǵ� ��ġ���ϸ�
			 */
			String strSendFileName = infoBatchFile.getStrTgtFileName().replaceAll("YYYYMMDD", strDate);
			
			/*
			 * ó�����.
			 */
			String strStat = "SUCCESS";
			
			//////////////////////////////////////////////////////////////////
			/*
			 * ��� ���ڿ��� �����.
			 */
			String strResultLine = String.format("%s;%s;%s;%s;%s;%05d;%05d;%s", strJobDateTime
					, strJobPath, strJobFileName, strFileDateTime, strSendFileName, this.iRecLen, this.iRecCount, strStat);
			if (flag) System.out.println("[" + strResultLine + "]");
			
			/*
			 * ó������� ����Ѵ�.
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
					getHashMapOfBatchFolder();

					/*
					 * ��ġó�� ���ϸ� ������ ��´�.
					 */
					getHashMapOfBatchFile();
					
					/*
					 * ��ġó���� ��ģ ���� ������ ��´�.
					 */
					getHashMapOfChkFile();
					
					//////////////////////////////////////////////////////////////////
					/*
					 * ��ġó�������� �д´�.
					 */
					Set<String> setKeyFolder = hashMapFolder.keySet();
					Iterator<String> iterKeyFolder = setKeyFolder.iterator();
					
					while (iterKeyFolder.hasNext()) {
						/*
						 * ��ġó���������� ��´�.
						 */
						String keyFolder = iterKeyFolder.next();
						WebCashBatchFolder batchFolder = hashMapFolder.get(keyFolder);
						
						File fileFolder = new File(batchFolder.getStrSrcFolder());
						if (!flag) System.out.println("PATH=" + fileFolder.getAbsolutePath());   // ���:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getCanonicalPath());  // ���:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getParent());         // ���:PATH=D:\KANG\WORK\webCash
						if (!flag) System.out.println("PATH=" + fileFolder.getPath());           // ���:PATH=D:\KANG\WORK\webCash\data3
						if (!flag) System.out.println("PATH=" + fileFolder.getName());           // ���:PATH=data3
						
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
											Date date = new Date();
											String strCompareDate = new SimpleDateFormat("yyyyMMdd000000", Locale.KOREA).format(date);
											String strDelayDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(date.getTime() - (iDelayMin * 60 * 1000));
											String strLastModifiedDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
											
											if (!flag) {   // TO DO : �׽�Ʈ�� 20130515000000
												strCompareDate = "20130515000000";
												//strCompareDate = "20130607000000";
												if (!flag) System.out.println("[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
											}

											/*
											 * ���� �Ӽ��Ͻÿ� �ش��ϴ� ���
											 * ������ �Ӽ���¥�� �Ʒ��� ���� �Ǿ� �־�� �Ѵ�.
											 *     yyyyMMdd000000 <= strLastModifiedDate <= strDelayDate(00�� ����)
											 */
											if ((strCompareDate.compareTo(strLastModifiedDate) <= 0) && (strLastModifiedDate.compareTo(strDelayDate) <= 0)) {
												if (!flag) System.out.println("\t�Ӽ��Ͻ�OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
												/*
												 * TO DO : hashMapBatchFile���� ���ϸ��� ���ǿ� �´� ��츦 ã�´�.
												 * ���ǿ� �´� ������ ��ġó���� �ؾ��ϴ� ���ϵ��̴�.
												 * key�� �����Ѵ�. ���ϸ�.replace(3, 8) <- YYYYMMDD
												 */
												String strKey = file.getName();
												strKey = strKey.replace(strKey.substring(3, 11), "YYYYMMDD");
												
												WebCashBatchFile batchFile = hashMapFile.get(strKey);
												if (batchFile != null) {
													/*
													 * �� ������ �ش��Ͽ��� ���ϰ�
													 * ��ġ���� ��Ͽ��� ���Ե� �����̴�.
													 */
													if (!flag) System.out.println("\t\t��ġ���OK[" + strCompareDate + "] [" + strLastModifiedDate + "] [" + file.getName() + "]");
													
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
							System.out.println("files is null point.......����.");
							
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
								WebCashBatchChkFile obj = (WebCashBatchChkFile) hashMapChkFile.get(strKey);
								if (obj == null) {
									/*
									 * ��ġó���� �Ѵ�.
									 */
									strKey = files[i].getName();
									strKey = strKey.replace(strKey.substring(3, 11), "YYYYMMDD");
									
									WebCashBatchFile batchFile = hashMapFile.get(strKey);
									if (batchFile != null) {
										/*
										 * �� ������ �ش��Ͽ��� ���ϰ�
										 * ��ġ���� ��Ͽ��� ���Ե� �����̴�.
										 */
										if (flag) System.out.println("[KEY=" + strKey + "] > ��ġó�� �� ������ ã�ҽ��ϴ�.[" + files[i].getPath() + "]");
										if (flag) batchFile.toPrint();
										
										//////////////////////////////////////////////////////////////////
										/*
										 * Host �����ϰ� ��ġ�ڷḦ �۽��Ѵ�.
										 */
										if (flag && doConnectAndSendData(fileFolder, files[i], batchFile)) {
											/*
											 * ó���� ���������� �������� ó������� ����Ѵ�.
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
						 * Ư���ð����� ��ٸ��� �ٽ� ��ġó���� �Ѵ�.
						 * ����ð��� 10������ �Ѵ�.
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
			item = strLine.split("|");  // ��� : 95
			item = strLine.split("^");  // ��� : 1
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
