package ic.vela.ibridge.serverbatch.ksi51.server;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchCommonHeader;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/*==================================================================*/
/**
 * BatchServerHWI51 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchServerHWI51
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
public class BatchServerHWI51
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	private static final int           HEADERLENGTH        = 190;

	//private static final String        INS_ID              = "LO1";
	//private static final String        BAK_CLS             = "02";
	//private static final String        BAK_ID              = "269";
	
	//private static final int           MAX_CONNECTION_TRY  = 3;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private HashMap<String,BatchTrInfo>  hashMapTrInfo     = null;
	private BatchHostInfo              batchHostInfo       = null;
	private String                     strFepid            = null;
	private String                     strBatchFileFolder  = null;
	
	@SuppressWarnings("unused")
	private CharsetEncoder             encoder             = null;  // String -> Byte
	@SuppressWarnings("unused")
	private CharsetDecoder             decoder             = null;  // Byte -> String
	
	private ServerSocket               serverSocket        = null;
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;
	
	private String                     strFileName         = null;

	private int                        iRecLen             = 0;
	private int                        iRecCount           = 0;
	
	/*==================================================================*/
	/**
	 * contructor
	 * 
	 * @param serverSocket
	 * @param hashMapTrInfo
	 * @param batchHostInfo
	 * @param strFepid
	 * @param strBatchFileFolder
	 */
	/*------------------------------------------------------------------*/
	public BatchServerHWI51(
			ServerSocket   serverSocket,
			HashMap<String,BatchTrInfo>    hashMapTrInfo,
			BatchHostInfo  batchHostInfo,
			String         strFepid,
			String         strBatchFileFolder
			)
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
			
			this.serverSocket       = serverSocket;
			this.hashMapTrInfo      = hashMapTrInfo;
			this.batchHostInfo      = batchHostInfo;
			this.strFepid           = strFepid;
			this.strBatchFileFolder = strBatchFileFolder;
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

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
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * do accept
	 * 접속을 기다린다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doAccept() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * Host 접속 정보
		 */
		if (flag) System.out.println(String.format("LISTEN 정보[PORT=%d]", batchHostInfo.getIntHostPort()));
		
		if (flag) {    // DATE.2013.07.07
			try {
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
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * do open 
	 * 개시전문을 받고 응답한다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doOpen() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.07.07 : 방카4차에서 사용중인 공통정보부
			try {
				/*
				 * batch common header
				 */
				BatchCommonHeader commonHeader = new BatchCommonHeader();

				if (flag) {
					/*
					 * 요청전문을 받는다.
					 */
					byte[] byteReq = recv(HEADERLENGTH);
					commonHeader.set(byteReq);
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("개시전문 REQ    [%d][%s]", intReqLen, strReq));
				}

				if (flag) {
					commonHeader.setStrDOC_CODE("0810");
					commonHeader.setStrTRA_FLAG("6");

					byte[] byteRes = commonHeader.get();
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("개시전문 RES    [%d][%s]", intResLen, strRes));
					
					/*
					 * 요청전문을 보낸다.
					 */
					send(byteRes, 0, intResLen);
				}
				
				if (!flag) System.exit(0);
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * do data transfer
	 * 배치파일을 open 한다.
	 * 데이터를 받는고 배치파일에 저장한다.
	 * 배치파일을 닫는다. 
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDataTransfer() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * 배치파일을 전송한다.
		 */
		String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
		String strFileFolder = this.strBatchFileFolder.replace("YYYYMMDD", strDate);
		
		if (flag) {    // TO DO DATE.2013.07.11
			/*
			 * 배치파일을 생성할 폴더를 확인한다.
			 */
			File dir = new File(strFileFolder);
			if (!dir.exists()) {
				/*
				 * 폴더가 존재하지 않으면 폴더를 생성한다.
				 */
				if (dir.mkdirs()) {
					if (flag) System.out.println("SUCCESS : create the folder. [" + strFileFolder + "]");
				} else {
					if (flag) System.out.println("FAIL : couldn't create the folder. [" + strFileFolder + "]");
				}
			}
		}
		
		if (flag) {   // DATE.2013.07.08

			try {
				/*
				 * 배치송신전문을 만든다.
				 */
				BatchCommonHeader commonHeader = new BatchCommonHeader();
				
				/*
				 * 배치파일 사이즈
				 */
				int intFileSize = 0;
				
				/*
				 * 배치파일 데이터
				 */
				byte[] byteFile = null;

				if (flag) {
					/*
					 * 요청전문을 받는다.
					 */
					byte[] byteReq = recv(HEADERLENGTH);
					commonHeader.set(byteReq);
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("DATA HEADER REQ [%d][%s]", intReqLen, strReq));
					
					/*
					 * 배치파일 사이즈를 구한다.
					 */
					int intDocLen = Integer.parseInt(commonHeader.getStrDOC_LEN());
					
					intFileSize = intDocLen - 190;
					
					/*
					 * 배치파일명을 구한다.
					 */
					this.strFileName = commonHeader.getStrDOC_NAME().trim();
					
				}
				
				String strFile = strFileFolder + "/" + this.strFileName;

				if (flag) {
					/*
					 * 배치파일의 크기의 byte 배열을 얻는다.
					 * 배치파일을 session에서 받는다.
					 */
					byteFile = new byte[intFileSize];
					byteFile = recv(intFileSize);

					/*
					 * 배치파일을 생성한다.
					 */
					FileOutputStream fileOutputStream = new FileOutputStream(strFile);
					fileOutputStream.write(byteFile);
					fileOutputStream.close();
				}
				
				if (flag) {
					System.out.println(String.format("처리할 배치파일 [%s]", strFile));
					System.out.println(String.format("INFO [initFileSize=%d] [strFileName=%s]", intFileSize, strFileName));
				}
				
				if (flag) {
					commonHeader.setStrDOC_LEN (190);
					commonHeader.setStrDOC_CODE("0610");
					commonHeader.setStrTRA_FLAG("6");
					
					/*
					 * header를 만든다.
					 */
					byte[] byteRes = commonHeader.get();
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("DATA HEADER RES [%d][%s]", intResLen, strRes));
					
					/*
					 * 응답전문을 보낸다.
					 */
					send(byteRes, 0, intResLen);
				}
				
				if (!flag) System.exit(0);
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * do close job
	 * 마감전문을 받고 응답을 보낸다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doClose() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.07.07 : 방카4차에서 사용중인 공통정보부
			try {
				/*
				 * batch common header
				 */
				BatchCommonHeader commonHeader = new BatchCommonHeader();

				if (flag) {
					/*
					 * 요청전문을 받는다.
					 */
					byte[] byteReq = recv(HEADERLENGTH);
					commonHeader.set(byteReq);
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("마감전문 REQ    [%d][%s]", intReqLen, strReq));
				}

				if (flag) {
					commonHeader.setStrDOC_CODE("0810");
					commonHeader.setStrTRA_FLAG("6");

					byte[] byteRes = commonHeader.get();
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("마감전문 RES    [%d][%s]", intResLen, strRes));
					
					/*
					 * 요청전문을 보낸다.
					 */
					send(byteRes, 0, intResLen);
				}
				
				if (!flag) System.exit(0);
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * do disconnection to the server
	 * 배치처리를 마치고 관련된 세션을 종료한다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDisConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
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
				//e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/**
	 * do post process
	 * 정상처리된 결과를 chk file에 기록한다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doPostProcess() throws Exception
	/*------------------------------------------------------------------*/
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
			String strJobPath = this.strBatchFileFolder.replace('\\', '/').replace("YYYYMMDD", strDate);
			
			/*
			 * 배치파일명
			 */
			String strJobFileName = this.strFileName;
					
			/*
			 * 배치파일 속성일시
			 */
			File file = new File(strJobPath + "/" + strJobFileName);
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * 서버에 생성되는 배치파일명
			 */
			String strSendFileName = this.strFileName;
			
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
//			if (!flag) {
//				try {
//					PrintStream ps = new PrintStream(new FileOutputStream(this.strChkFileName, true), true, CHARSET);
//					ps.println(strResultLine);
//					ps.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	/*==================================================================*/
	/**
	 * execute the batch job
	 */
	/*------------------------------------------------------------------*/
	public void execute()
	{
		boolean flag = true;
		
		if (flag) {

			if (flag) {
				/*
				 * 기본정보를 확인한다.
				 */
				System.out.println("---------------- execute ------------------");
				System.out.println("[strFepid     = " + this.batchHostInfo.getStrFepid()   + "]");
				System.out.println("[strInsid     = " + this.batchHostInfo.getStrInsid()   + "]");
				System.out.println("[strName      = " + this.batchHostInfo.getStrName()    + "]");
				System.out.println("[strFepid     = " + this.strFepid                      + "]");
				System.out.println("[strBatchFileFolder = " + this.strBatchFileFolder + "]");
			}
			
			while (true) {
				try {
					if (flag) {
						/*
						 * 접속을 시도한다.
						 */
						doAccept();
					}
					
					if (flag) {
						/*
						 * 개시전문을 받는다
						 */
						doOpen();
					}
					
					if (flag) {
						/*
						 * DATA 전문을 받는다.
						 */
						doDataTransfer();
					}
					
					if (flag) {
						/*
						 * 마감전문을 받는다.
						 */
						doClose();
					}
					
					if (flag) {
						/*
						 * 접속을 종료한다.
						 */
						doDisConnection();
					}
					
					if (flag) {
						/*
						 * 후처리를 한다.
						 */
						doPostProcess();
					}
				} catch (IOException e) {
					/*
					 * IOException of recv 
					 */
					e.printStackTrace();

					if (flag) {
						/*
						 * 접속을 종료한다.
						 */
						try {
							doDisConnection();
						} catch (Exception e1) {}
					}
					
				} catch (Exception e) {
					/*
					 * Other Exception move to break;
					 */
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/**
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}
	
	/*==================================================================*/
	/**
	 * default test : 단순한 문자 출력
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
