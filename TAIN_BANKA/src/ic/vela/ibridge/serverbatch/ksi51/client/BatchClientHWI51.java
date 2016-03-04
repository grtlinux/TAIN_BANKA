package ic.vela.ibridge.serverbatch.ksi51.client;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchCommonHeader;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchFolder;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*==================================================================*/
/**
 * BatchClientHWI51 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchClientHWI51
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
public class BatchClientHWI51
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	private static final int           HEADERLENGTH        = 190;

	private static final String        INS_ID              = "L01";   // 한화생명 배치클라이언트
	private static final String        BAK_CLS             = "02";
	private static final String        BAK_ID              = "269";
	
	private static final int           MAX_CONNECTION_TRY  = 3;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private BatchFolder                batchFolder         = null;
	private BatchTrInfo                batchTrInfo         = null;
	private BatchHostInfo              batchHostInfo       = null;
	private String                     strFileName         = null;
	private String                     strChkFileName      = null;
	
	@SuppressWarnings("unused")
	private CharsetEncoder             encoder             = null;  // String -> Byte
	@SuppressWarnings("unused")
	private CharsetDecoder             decoder             = null;  // Byte -> String
	
	private Socket                     socket              = null;
	private DataInputStream            dis                 = null;
	private DataOutputStream           dos                 = null;
	
	private int                        intRecLength        = 0;
	private int                        intRecCount         = 0;

	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param batchFolder
	 * @param batchTrInfo
	 * @param batchHostInfo
	 * @param strFileName
	 */
	/*------------------------------------------------------------------*/
	public BatchClientHWI51(
			BatchFolder    batchFolder,
			BatchTrInfo    batchTrInfo,
			BatchHostInfo  batchHostInfo,
			String         strFileName,
			String         strChkFileName
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
			
			this.batchFolder    = batchFolder;
			this.batchTrInfo    = batchTrInfo;
			this.batchHostInfo  = batchHostInfo;
			this.strFileName    = strFileName;
			this.strChkFileName = strChkFileName;
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
	 * do connection
	 * 접속을 시도한다.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * Host 접속 정보
		 */
		if (flag) System.out.println(String.format("접속정보[%s:%d]", batchHostInfo.getStrHostIp(), batchHostInfo.getIntHostPort()));
		
		if (flag) {    // DATE.2013.07.07
			try {
				/* 접속시도를 한다. 2초간격으로 최대 MAX_CONNECTION_TRY번 접속시도
				 *  to get the socket connected to server
				 *  connection try count is n
				 */
				socket = null;
				for (int i=0; socket == null && i < MAX_CONNECTION_TRY; i++) {
					
					try {
						socket = new Socket(batchHostInfo.getStrHostIp(), batchHostInfo.getIntHostPort());
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
					if (flag) System.out.println("ERROR : socket not connect to Server.....");
					throw new IllegalStateException("ERROR : socket not connect to Server.....");
				}

				if (flag) System.out.println("CLIENT : socket connection to Server.....");
				
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
	 * 개시전문을 보내고 응답을 받는다.
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
					commonHeader.setStrSYS_ID  ("FTP");
					commonHeader.setStrINS_ID  (INS_ID);
					commonHeader.setStrBAK_CLS (BAK_CLS);
					commonHeader.setStrBAK_ID  (BAK_ID);
					commonHeader.setStrDOC_CODE("0800");
					commonHeader.setStrBIZ_CODE("061000");
					commonHeader.setStrTRA_FLAG("2");
					commonHeader.setStrDOC_NAME(strFileName);

					byte[] byteReq = commonHeader.get();
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 요청전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("개시전문 REQ    [%d][%s]", intReqLen, strReq));
					
					/*
					 * 요청전문을 보낸다.
					 */
					send(byteReq, 0, intReqLen);
					
				}
				
				if (flag) {
					/*
					 * 응답전문을 받는다.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("개시전문 RES    [%d][%s]", intResLen, strRes));
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
	 * 배치파일을 연다.
	 * 데이터를 전달하고 종료응답을 받는다.
	 * 배치파일을 닫는다. 
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("resource")
	private void doDataTransfer() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * 배치파일을 전송한다.
		 */
		String strFile = this.batchFolder.getStrSrcFolder() + "/" + this.strFileName;
		
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
					 * 배치파일의 크기를 구한다.
					 */
					File file = new File(strFile);
					intFileSize = (int) file.length();
					
					/*
					 * 배치파일의 크기의 byte 배열을 얻는다.
					 */
					byteFile = new byte[intFileSize];

					/*
					 * 배치파일을 open 한다.
					 */
					FileInputStream fileInputStream = new FileInputStream(file);
					
					/*
					 * 배치파일을 read 한다.
					 */
					int intReaded = fileInputStream.read(byteFile);
					if (intReaded != intFileSize) {
						/*
						 * 파일을 다 못 읽었다.
						 */
						throw new IllegalStateException("ERROR : file read error....");
					}
					
					/*
					 * 배치파일을 close 한다.
					 */
					fileInputStream.close();
					
				}
				
				////////////////////////////////////////////////////////
				/*
				 * 배치송신전문의 길이를 구한다.
				 */
				int intDocLen = 190 + intFileSize;
				
				if (flag) {
					commonHeader.setStrSYS_ID  ("FTP");
					commonHeader.setStrDOC_LEN (intDocLen);
					commonHeader.setStrINS_ID  (INS_ID);
					commonHeader.setStrBAK_CLS (BAK_CLS);
					commonHeader.setStrBAK_ID  (BAK_ID);
					commonHeader.setStrDOC_CODE("0600");
					commonHeader.setStrBIZ_CODE(batchTrInfo.getStrTrCode());
					commonHeader.setStrTRA_FLAG("2");
					commonHeader.setStrDOC_NAME(strFileName);
					
					/*
					 * header를 만든다.
					 */
					byte[] byteReq = commonHeader.get();
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 요청전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("DATA HEADER REQ [%d][%s]", intReqLen, strReq));
					
					/*
					 * 요청전문을 보낸다.
					 */
					send(byteReq, 0, intReqLen);
				}
				
				if (flag) {
					/*
					 * 배치 data를 송신한다.
					 */
					send(byteFile, 0, intFileSize);
				}
				
				if (flag) {
					System.out.println(String.format("처리할 배치파일 [%s]", strFile));
					System.out.println(String.format("INFO [initFileSize=%d] [strFileName=%s]", intFileSize, strFileName));
				}
				
				if (flag) {
					/*
					 * 응답전문을 받는다.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("DATA HEADER RES [%d][%s]", intResLen, strRes));
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
	 * 마감전문을 보내고 응답전문을 받는다.
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
					commonHeader.setStrSYS_ID  ("FTP");
					commonHeader.setStrINS_ID  (INS_ID);
					commonHeader.setStrBAK_CLS (BAK_CLS);
					commonHeader.setStrBAK_ID  (BAK_ID);
					commonHeader.setStrDOC_CODE("0800");
					commonHeader.setStrBIZ_CODE("063000");
					commonHeader.setStrTRA_FLAG("2");
					commonHeader.setStrDOC_NAME(strFileName);

					byte[] byteReq = commonHeader.get();
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * 요청전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("마감전문 REQ    [%d][%s]", intReqLen, strReq));
					
					/*
					 * 요청전문을 보낸다.
					 */
					send(byteReq, 0, intReqLen);
					
				}
				
				if (flag) {
					/*
					 * 응답전문을 받는다.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * 응답전문을 출력한다.
					 */
					if (flag) System.out.println(String.format("마감전문 RES    [%d][%s]", intResLen, strRes));
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
			String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
			
			//////////////////////////////////////////////////////////////////
			/*
			 * 처리일시
			 */
			String strJobDateTime = strDateTime;
			
			/*
			 * 파일 path
			 */
			String strJobPath = batchFolder.getStrSrcFolder().replace('\\', '/');
			
			/*
			 * 배치파일명
			 */
			String strJobFileName = strFileName;
					
			/*
			 * 배치파일 속성일시
			 */
			File file = new File(strJobPath + "/" + strJobFileName);
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * 서버에 생성되는 배치파일명
			 */
			String strSendFileName = strFileName;
			
			/*
			 * 처리결과.
			 */
			String strStat = "SUCCESS";
			
			//////////////////////////////////////////////////////////////////
			/*
			 * 결과 문자열을 만든다.
			 */
			String strResultLine = String.format("%s;%s;%s;%s;%s;%05d;%05d;%s", strJobDateTime
					, strJobPath, strJobFileName, strFileDateTime, strSendFileName, this.intRecLength, this.intRecCount, strStat);
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
				System.out.println("[strSrcFolder = " + this.batchFolder.getStrSrcFolder() + "]");
				System.out.println("[strTrName    = " + this.batchTrInfo.getStrTrName()    + "]");
				System.out.println("[strTrCode    = " + this.batchTrInfo.getStrTrCode()    + "]");
				System.out.println("[strFepid     = " + this.batchHostInfo.getStrFepid()   + "]");
				System.out.println("[strInsid     = " + this.batchHostInfo.getStrInsid()   + "]");
				System.out.println("[strName      = " + this.batchHostInfo.getStrName()    + "]");
				System.out.println("[strFileName  = " + this.strFileName + "]");
				System.out.println("[strChkFileName = " + this.strChkFileName + "]");
			}
			
			try {
				
				if (flag) {
					/*
					 * 접속을 시도한다.
					 */
					doConnection();
				}
				
				if (flag) {
					/*
					 * 개시전문을 보낸다.
					 */
					doOpen();
				}
				
				if (flag) {
					/*
					 * DATA 전문을 보낸다.
					 */
					doDataTransfer();
				}
				
				if (flag) {
					/*
					 * 마감전문을 보낸다.
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
				
			} catch (Exception e) {
				e.printStackTrace();
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
