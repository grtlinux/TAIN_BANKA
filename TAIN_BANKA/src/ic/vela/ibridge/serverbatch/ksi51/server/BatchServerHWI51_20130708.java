package ic.vela.ibridge.serverbatch.ksi51.server;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
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
public class BatchServerHWI51_20130708
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	
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
	
	private BufferedWriter             bufferedWriter      = null;

	private String                     strFileName         = null;
	//private String                     strRecLen           = null;
	//private String                     strTrCodeSeq        = null;
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
	public BatchServerHWI51_20130708(
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
	@SuppressWarnings("unused")
	private void doOpen_old() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.07.07 : 배치전문공통정보부
			try {
				int intLength = 188;

				byte[] byteTRX_ID     = new byte[ 9];   // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];   // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[15];   // 전체전문길이
				byte[] byteDAT_GBN    = new byte[ 1];   // 자료구분
				byte[] byteSND_CLS    = new byte[ 1];   // 송신기관구분
				byte[] byteSND_ID     = new byte[ 4];   // 송신기관코드
				byte[] byteRSV_CLS    = new byte[ 1];   // 수신기관구분
				byte[] byteRSV_ID     = new byte[ 4];   // 수신기관코드
				byte[] byteDOC_CODE   = new byte[ 4];   // 전문종별 코드
				byte[] byteBIZ_CODE   = new byte[ 4];   // 거래구분 코드
				byte[] byteTRA_FLAG   = new byte[ 1];   // 송수신 FLAG
				byte[] byteDOC_STATUS = new byte[ 3];   // STATUS
				byte[] byteRET_CODE   = new byte[ 4];   // 응답코드
				byte[] byteSND_DATE   = new byte[ 8];   // 전문전송일
				byte[] byteSND_TIME   = new byte[ 6];   // 전문전송시간
				byte[] byteSND_DOCSEQ = new byte[ 8];   // 송신기관전문번호
				byte[] byteRSV_DOCSEQ = new byte[ 8];   // 수신기관전문번호
				byte[] byteTXN_DATE   = new byte[ 8];   // 거래발생일
				byte[] byteTOT_FILE   = new byte[ 2];   // 전체 파일 수
				byte[] byteCUR_FILE   = new byte[ 2];   // 현재 파일 순번
				byte[] byteDOC_NAME   = new byte[35];   // 현재 파일명
				byte[] byteCUR_TOT    = new byte[ 2];   // 현재파일전체전문수
				byte[] byteCUR_NOW    = new byte[ 2];   // 현재파일현재순번
				byte[] byteDOC_CASE   = new byte[ 6];   // 전문 CASE명
				byte[] byteSND_EXT    = new byte[26];   // 송신사추가정의
				byte[] byteRSV_EXT    = new byte[21];   // 수신사추가정의

				/*
				 * 요청전문을 받는다.
				 */
				byte[] byteReq = recv(intLength);
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.wrap(byteReq);
				
				byteReqBuffer.get(byteTRX_ID    );
				byteReqBuffer.get(byteSYS_ID    );
				byteReqBuffer.get(byteDOC_LEN   );
				byteReqBuffer.get(byteDAT_GBN   );
				byteReqBuffer.get(byteSND_CLS   );
				byteReqBuffer.get(byteSND_ID    );
				byteReqBuffer.get(byteRSV_CLS   );
				byteReqBuffer.get(byteRSV_ID    );
				byteReqBuffer.get(byteDOC_CODE  );
				byteReqBuffer.get(byteBIZ_CODE  );
				byteReqBuffer.get(byteTRA_FLAG  );
				byteReqBuffer.get(byteDOC_STATUS);
				byteReqBuffer.get(byteRET_CODE  );
				byteReqBuffer.get(byteSND_DATE  );
				byteReqBuffer.get(byteSND_TIME  );
				byteReqBuffer.get(byteSND_DOCSEQ);
				byteReqBuffer.get(byteRSV_DOCSEQ);
				byteReqBuffer.get(byteTXN_DATE  );
				byteReqBuffer.get(byteTOT_FILE  );
				byteReqBuffer.get(byteCUR_FILE  );
				byteReqBuffer.get(byteDOC_NAME  );
				byteReqBuffer.get(byteCUR_TOT   );
				byteReqBuffer.get(byteCUR_NOW   );
				byteReqBuffer.get(byteDOC_CASE  );
				byteReqBuffer.get(byteSND_EXT   );
				byteReqBuffer.get(byteRSV_EXT   );
				
				/*
				 * 요청전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * 응답전문을 만든다.
				 */
				byteDOC_CODE   = String.format("%4.4s", "0810").getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%4.4s", "0100").getBytes(CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.allocate(intLength);
				
				byteResBuffer.put(byteTRX_ID    );
				byteResBuffer.put(byteSYS_ID    );
				byteResBuffer.put(byteDOC_LEN   );
				byteResBuffer.put(byteDAT_GBN   );
				byteResBuffer.put(byteSND_CLS   );
				byteResBuffer.put(byteSND_ID    );
				byteResBuffer.put(byteRSV_CLS   );
				byteResBuffer.put(byteRSV_ID    );
				byteResBuffer.put(byteDOC_CODE  );
				byteResBuffer.put(byteBIZ_CODE  );
				byteResBuffer.put(byteTRA_FLAG  );
				byteResBuffer.put(byteDOC_STATUS);
				byteResBuffer.put(byteRET_CODE  );
				byteResBuffer.put(byteSND_DATE  );
				byteResBuffer.put(byteSND_TIME  );
				byteResBuffer.put(byteSND_DOCSEQ);
				byteResBuffer.put(byteRSV_DOCSEQ);
				byteResBuffer.put(byteTXN_DATE  );
				byteResBuffer.put(byteTOT_FILE  );
				byteResBuffer.put(byteCUR_FILE  );
				byteResBuffer.put(byteDOC_NAME  );
				byteResBuffer.put(byteCUR_TOT   );
				byteResBuffer.put(byteCUR_NOW   );
				byteResBuffer.put(byteDOC_CASE  );
				byteResBuffer.put(byteSND_EXT   );
				byteResBuffer.put(byteRSV_EXT   );
				
				byte[] byteRes = byteResBuffer.array();
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				/*
				 * 응답전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
				/*
				 * 응답전문을 보낸다.
				 */
				send(byteRes, 0, intResLen);
				
				if (!flag) System.exit(0);
				
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
				int intLength = 190;

				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // 전체전문길이
				byte[] byteDAT_GBN    = new byte[ 1];    // 자료구분
				byte[] byteINS_ID     = new byte[ 3];    // 보험사기관 ID
				byte[] byteBAK_CLS    = new byte[ 2];    // 은행구분
				byte[] byteBAK_ID     = new byte[ 3];    // 은행 ID
				byte[] byteDOC_CODE   = new byte[ 4];    // 전문종별 코드
				byte[] byteBIZ_CODE   = new byte[ 6];    // 거래구분 코드
				byte[] byteTRA_FLAG   = new byte[ 1];    // 송수신 FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // 응답코드
				byte[] byteSND_DATE   = new byte[ 8];    // 전문전송일
				byte[] byteSND_TIME   = new byte[ 6];    // 전문전송시간
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // 은행 전문번호
				byte[] byteINS_DOCSEQ = new byte[ 8];    // 보험사 전문번호
				byte[] byteTXN_DATE   = new byte[ 8];    // 거래발생일
				byte[] byteTOT_FILE   = new byte[ 2];    // 전체 파일 수
				byte[] byteCUR_FILE   = new byte[ 2];    // 현재 파일 순번
				byte[] byteDOC_NAME   = new byte[30];    // 현재 파일명
				byte[] byteCUR_TOT    = new byte[ 2];    // 현재파일전체전문수
				byte[] byteCUR_NOW    = new byte[ 2];    // 현재파일현재순번
				byte[] byteDOC_CASE   = new byte[ 6];    // 전문 CASE 명
				byte[] byteBAK_EXT    = new byte[30];    // 은행추가정의
				byte[] byteINS_EXT    = new byte[30];    // 보험사추가정의
				
				/*
				 * 요청전문을 받는다.
				 */
				byte[] byteReq = recv(intLength);
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.wrap(byteReq);
				
				byteReqBuffer.get(byteTRX_ID    );
				byteReqBuffer.get(byteSYS_ID    );
				byteReqBuffer.get(byteDOC_LEN   );
				byteReqBuffer.get(byteDAT_GBN   );
				byteReqBuffer.get(byteINS_ID    );
				byteReqBuffer.get(byteBAK_CLS   );
				byteReqBuffer.get(byteBAK_ID    );
				byteReqBuffer.get(byteDOC_CODE  );
				byteReqBuffer.get(byteBIZ_CODE  );
				byteReqBuffer.get(byteTRA_FLAG  );
				byteReqBuffer.get(byteDOC_STATUS);
				byteReqBuffer.get(byteRET_CODE  );
				byteReqBuffer.get(byteSND_DATE  );
				byteReqBuffer.get(byteSND_TIME  );
				byteReqBuffer.get(byteBAK_DOCSEQ);
				byteReqBuffer.get(byteINS_DOCSEQ);
				byteReqBuffer.get(byteTXN_DATE  );
				byteReqBuffer.get(byteTOT_FILE  );
				byteReqBuffer.get(byteCUR_FILE  );
				byteReqBuffer.get(byteDOC_NAME  );
				byteReqBuffer.get(byteCUR_TOT   );
				byteReqBuffer.get(byteCUR_NOW   );
				byteReqBuffer.get(byteDOC_CASE  );
				byteReqBuffer.get(byteBAK_EXT   );
				byteReqBuffer.get(byteINS_EXT   );
				
				/*
				 * 요청전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("개시전문 REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * 응답전문을 만든다.
				 */
				byteDOC_CODE   = String.format("%4.4s", "0810"  ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%6.6s", "000100").getBytes(CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.allocate(intLength);
				
				byteResBuffer.put(byteTRX_ID    );
				byteResBuffer.put(byteSYS_ID    );
				byteResBuffer.put(byteDOC_LEN   );
				byteResBuffer.put(byteDAT_GBN   );
				byteResBuffer.put(byteINS_ID    );
				byteResBuffer.put(byteBAK_CLS   );
				byteResBuffer.put(byteBAK_ID    );
				byteResBuffer.put(byteDOC_CODE  );
				byteResBuffer.put(byteBIZ_CODE  );
				byteResBuffer.put(byteTRA_FLAG  );
				byteResBuffer.put(byteDOC_STATUS);
				byteResBuffer.put(byteRET_CODE  );
				byteResBuffer.put(byteSND_DATE  );
				byteResBuffer.put(byteSND_TIME  );
				byteResBuffer.put(byteBAK_DOCSEQ);
				byteResBuffer.put(byteINS_DOCSEQ);
				byteResBuffer.put(byteTXN_DATE  );
				byteResBuffer.put(byteTOT_FILE  );
				byteResBuffer.put(byteCUR_FILE  );
				byteResBuffer.put(byteDOC_NAME  );
				byteResBuffer.put(byteCUR_TOT   );
				byteResBuffer.put(byteCUR_NOW   );
				byteResBuffer.put(byteDOC_CASE  );
				byteResBuffer.put(byteBAK_EXT   );
				byteResBuffer.put(byteINS_EXT   );
				
				byte[] byteRes = byteResBuffer.array();
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				if (flag) System.out.println(String.format("개시전문 RES [%d][%s]", intResLen, strRes));
				
				/*
				 * 배치파일명을 얻는다.
				 */
				this.strFileName = new String(byteDOC_NAME, CHARSET).trim();
				if (!flag) System.out.println("[BatchFile=" + strFileName + "]");
				
				/*
				 * 응답전문을 보낸다.
				 */
				send(byteRes, 0, intResLen);
				
				if (!flag) System.exit(0);
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}

	}
	
	/*==================================================================*/
	/**
	 * do the data sender
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private byte[] doDataReceiver() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		byte[] byteHeader = null;
		byte[] byteData = null;
		
		if (flag) {
			try {
				/*
				 * 자료를 수신한다.
				 */
				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // 전체전문길이
				byte[] byteDAT_GBN    = new byte[ 1];    // 자료구분
				byte[] byteINS_ID     = new byte[ 3];    // 보험사기관 ID
				byte[] byteBAK_CLS    = new byte[ 2];    // 은행구분
				byte[] byteBAK_ID     = new byte[ 3];    // 은행 ID
				byte[] byteDOC_CODE   = new byte[ 4];    // 전문종별 코드
				byte[] byteBIZ_CODE   = new byte[ 6];    // 거래구분 코드
				byte[] byteTRA_FLAG   = new byte[ 1];    // 송수신 FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // 응답코드
				byte[] byteSND_DATE   = new byte[ 8];    // 전문전송일
				byte[] byteSND_TIME   = new byte[ 6];    // 전문전송시간
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // 은행 전문번호
				byte[] byteINS_DOCSEQ = new byte[ 8];    // 보험사 전문번호
				byte[] byteTXN_DATE   = new byte[ 8];    // 거래발생일
				byte[] byteTOT_FILE   = new byte[ 2];    // 전체 파일 수
				byte[] byteCUR_FILE   = new byte[ 2];    // 현재 파일 순번
				byte[] byteDOC_NAME   = new byte[30];    // 현재 파일명
				byte[] byteCUR_TOT    = new byte[ 2];    // 현재파일전체전문수
				byte[] byteCUR_NOW    = new byte[ 2];    // 현재파일현재순번
				byte[] byteDOC_CASE   = new byte[ 6];    // 전문 CASE 명
				byte[] byteBAK_EXT    = new byte[30];    // 은행추가정의
				byte[] byteINS_EXT    = new byte[30];    // 보험사추가정의
				
				/*
				 * header 를 수신한다.
				 */
				int intHeaderLength = 190;
				byteHeader = recv(intHeaderLength);

				/*
				 * header 를 출력한다.
				 */
				if (!flag) {
					String strHeader = new String(byteHeader, CHARSET);
					System.out.println(String.format("HEADER [%d][%s]", intHeaderLength, strHeader));
				}

				ByteBuffer byteHeaderBuffer = ByteBuffer.wrap(byteHeader);

				byteHeaderBuffer.get(byteTRX_ID    );
				byteHeaderBuffer.get(byteSYS_ID    );
				byteHeaderBuffer.get(byteDOC_LEN   );
				byteHeaderBuffer.get(byteDAT_GBN   );
				byteHeaderBuffer.get(byteINS_ID    );
				byteHeaderBuffer.get(byteBAK_CLS   );
				byteHeaderBuffer.get(byteBAK_ID    );
				byteHeaderBuffer.get(byteDOC_CODE  );
				byteHeaderBuffer.get(byteBIZ_CODE  );
				byteHeaderBuffer.get(byteTRA_FLAG  );
				byteHeaderBuffer.get(byteDOC_STATUS);
				byteHeaderBuffer.get(byteRET_CODE  );
				byteHeaderBuffer.get(byteSND_DATE  );
				byteHeaderBuffer.get(byteSND_TIME  );
				byteHeaderBuffer.get(byteBAK_DOCSEQ);
				byteHeaderBuffer.get(byteINS_DOCSEQ);
				byteHeaderBuffer.get(byteTXN_DATE  );
				byteHeaderBuffer.get(byteTOT_FILE  );
				byteHeaderBuffer.get(byteCUR_FILE  );
				byteHeaderBuffer.get(byteDOC_NAME  );
				byteHeaderBuffer.get(byteCUR_TOT   );
				byteHeaderBuffer.get(byteCUR_NOW   );
				byteHeaderBuffer.get(byteDOC_CASE  );
				byteHeaderBuffer.get(byteBAK_EXT   );
				byteHeaderBuffer.get(byteINS_EXT   );

				/*
				 * data 를 수신한다.
				 */
				int intDataLength = Integer.parseInt(new String(byteDOC_LEN)) - intHeaderLength;
				byteData = recv(intDataLength);

				/*
				 * data 를 출력한다.
				 */
				if (!flag) {
					String strData = new String(byteData, CHARSET);
					System.out.println(String.format("DATA [%d][%s]", intDataLength, strData));
				}
				
				if (flag) {
					String strHeader = new String(byteHeader, CHARSET);
					String strData = new String(byteData, CHARSET);

					System.out.println(String.format("데이터 RECV [%05d][%s]", intHeaderLength + intDataLength, strHeader + strData));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return byteData;
	}
	
	/*==================================================================*/
	/**
	 * do the data transfer of header
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDataTransferHeader() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;

		if (flag) {
			/*
			 * 배치파일 header 정보를 보낸다.
			 */
			byte[] byteLine = doDataReceiver();
			String strLine = new String(byteLine, CHARSET);
			
			this.bufferedWriter.write(strLine);
			this.bufferedWriter.newLine();
		}
	}
	
	/*==================================================================*/
	/**
	 * do the data transfer of body
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDataTransferBody() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {     // DATE.2013.07.08
			/*
			 * 배치파일 body 정보를 받는다.
			 */
			
			this.iRecLen = 0;
			this.iRecCount = 0;
			
			//while (true) {
			for (int i=0; i < 50; i++)
			{
				/*
				 * 데이터를 수신 받는다.
				 */
				byte[] byteLine = doDataReceiver();
				String strLine = new String(byteLine, CHARSET);
				
				/*
				 * 데이터를 배치파일에 저장한다.
				 */
				this.bufferedWriter.write(strLine);
				this.bufferedWriter.newLine();
				this.iRecCount ++;
				this.iRecLen = Math.max(this.iRecLen, byteLine.length);
				
				// display the the byte line data
				if (!flag) System.out.println(String.format("[%04d][%05d][%s]", this.iRecCount, this.iRecLen, strLine));

				/*
				 * body 의 마지막이면 break 한다. 
				 */
				if ("".equals(strLine)) {
					break;
				}
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * do the data transfer of tailer
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDataTransferTailer() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * 배치파일 tailer 정보를 보낸다.
			 */
			byte[] byteLine = doDataReceiver();
			String strLine = new String(byteLine, CHARSET);
			
			this.bufferedWriter.write(strLine);
			this.bufferedWriter.newLine();
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
		String strFile = this.strBatchFileFolder + "/" + this.strFileName;
		String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
		strFile = strFile.replace("YYYYMMDD", strDate);
		
		if (flag) System.out.println(String.format("처리할 배치파일 [%s]", strFile));
		
		if (flag) {   // DATE.2013.06.27

			try {
				/*
				 *  배치파일을 open 한다.
				 */
				FileOutputStream fileOutputStream = new FileOutputStream(strFile);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET);
				this.bufferedWriter = new BufferedWriter(outputStreamWriter);
				
				/*
				 * 배치파일 해더를 전송한다.
				 */
				doDataTransferHeader();
				
				/*
				 * 배치파일 데이터를 전송한다.
				 */
				doDataTransferBody();
				
				/*
				 * 배치파일 테일러를 전송한다.
				 */
				doDataTransferTailer();

				/*
				 * 배치파일을 close 한다.
				 */
				this.bufferedWriter.close();
				
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
	@SuppressWarnings("unused")
	private void doClose_old() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.07.07 : 배치전문공통정보부
			try {
				int intLength = 188;

				byte[] byteTRX_ID     = new byte[ 9];   // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];   // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[15];   // 전체전문길이
				byte[] byteDAT_GBN    = new byte[ 1];   // 자료구분
				byte[] byteSND_CLS    = new byte[ 1];   // 송신기관구분
				byte[] byteSND_ID     = new byte[ 4];   // 송신기관코드
				byte[] byteRSV_CLS    = new byte[ 1];   // 수신기관구분
				byte[] byteRSV_ID     = new byte[ 4];   // 수신기관코드
				byte[] byteDOC_CODE   = new byte[ 4];   // 전문종별 코드
				byte[] byteBIZ_CODE   = new byte[ 4];   // 거래구분 코드
				byte[] byteTRA_FLAG   = new byte[ 1];   // 송수신 FLAG
				byte[] byteDOC_STATUS = new byte[ 3];   // STATUS
				byte[] byteRET_CODE   = new byte[ 4];   // 응답코드
				byte[] byteSND_DATE   = new byte[ 8];   // 전문전송일
				byte[] byteSND_TIME   = new byte[ 6];   // 전문전송시간
				byte[] byteSND_DOCSEQ = new byte[ 8];   // 송신기관전문번호
				byte[] byteRSV_DOCSEQ = new byte[ 8];   // 수신기관전문번호
				byte[] byteTXN_DATE   = new byte[ 8];   // 거래발생일
				byte[] byteTOT_FILE   = new byte[ 2];   // 전체 파일 수
				byte[] byteCUR_FILE   = new byte[ 2];   // 현재 파일 순번
				byte[] byteDOC_NAME   = new byte[35];   // 현재 파일명
				byte[] byteCUR_TOT    = new byte[ 2];   // 현재파일전체전문수
				byte[] byteCUR_NOW    = new byte[ 2];   // 현재파일현재순번
				byte[] byteDOC_CASE   = new byte[ 6];   // 전문 CASE명
				byte[] byteSND_EXT    = new byte[26];   // 송신사추가정의
				byte[] byteRSV_EXT    = new byte[21];   // 수신사추가정의

				/*
				 * 요청전문을 받는다.
				 */
				byte[] byteReq = recv(intLength);
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.wrap(byteReq);
				
				byteReqBuffer.get(byteTRX_ID    );
				byteReqBuffer.get(byteSYS_ID    );
				byteReqBuffer.get(byteDOC_LEN   );
				byteReqBuffer.get(byteDAT_GBN   );
				byteReqBuffer.get(byteSND_CLS   );
				byteReqBuffer.get(byteSND_ID    );
				byteReqBuffer.get(byteRSV_CLS   );
				byteReqBuffer.get(byteRSV_ID    );
				byteReqBuffer.get(byteDOC_CODE  );
				byteReqBuffer.get(byteBIZ_CODE  );
				byteReqBuffer.get(byteTRA_FLAG  );
				byteReqBuffer.get(byteDOC_STATUS);
				byteReqBuffer.get(byteRET_CODE  );
				byteReqBuffer.get(byteSND_DATE  );
				byteReqBuffer.get(byteSND_TIME  );
				byteReqBuffer.get(byteSND_DOCSEQ);
				byteReqBuffer.get(byteRSV_DOCSEQ);
				byteReqBuffer.get(byteTXN_DATE  );
				byteReqBuffer.get(byteTOT_FILE  );
				byteReqBuffer.get(byteCUR_FILE  );
				byteReqBuffer.get(byteDOC_NAME  );
				byteReqBuffer.get(byteCUR_TOT   );
				byteReqBuffer.get(byteCUR_NOW   );
				byteReqBuffer.get(byteDOC_CASE  );
				byteReqBuffer.get(byteSND_EXT   );
				byteReqBuffer.get(byteRSV_EXT   );
				
				/*
				 * 요청전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * 응답전문을 만든다.
				 */
				byteDOC_CODE   = String.format("%4.4s", "0810").getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%4.4s", "0200").getBytes(CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.allocate(intLength);
				
				byteResBuffer.put(byteTRX_ID    );
				byteResBuffer.put(byteSYS_ID    );
				byteResBuffer.put(byteDOC_LEN   );
				byteResBuffer.put(byteDAT_GBN   );
				byteResBuffer.put(byteSND_CLS   );
				byteResBuffer.put(byteSND_ID    );
				byteResBuffer.put(byteRSV_CLS   );
				byteResBuffer.put(byteRSV_ID    );
				byteResBuffer.put(byteDOC_CODE  );
				byteResBuffer.put(byteBIZ_CODE  );
				byteResBuffer.put(byteTRA_FLAG  );
				byteResBuffer.put(byteDOC_STATUS);
				byteResBuffer.put(byteRET_CODE  );
				byteResBuffer.put(byteSND_DATE  );
				byteResBuffer.put(byteSND_TIME  );
				byteResBuffer.put(byteSND_DOCSEQ);
				byteResBuffer.put(byteRSV_DOCSEQ);
				byteResBuffer.put(byteTXN_DATE  );
				byteResBuffer.put(byteTOT_FILE  );
				byteResBuffer.put(byteCUR_FILE  );
				byteResBuffer.put(byteDOC_NAME  );
				byteResBuffer.put(byteCUR_TOT   );
				byteResBuffer.put(byteCUR_NOW   );
				byteResBuffer.put(byteDOC_CASE  );
				byteResBuffer.put(byteSND_EXT   );
				byteResBuffer.put(byteRSV_EXT   );
				
				byte[] byteRes = byteResBuffer.array();
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				/*
				 * 응답전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
				/*
				 * 응답전문을 보낸다.
				 */
				send(byteRes, 0, intResLen);
				
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
				int intLength = 190;

				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // 전체전문길이
				byte[] byteDAT_GBN    = new byte[ 1];    // 자료구분
				byte[] byteINS_ID     = new byte[ 3];    // 보험사기관 ID
				byte[] byteBAK_CLS    = new byte[ 2];    // 은행구분
				byte[] byteBAK_ID     = new byte[ 3];    // 은행 ID
				byte[] byteDOC_CODE   = new byte[ 4];    // 전문종별 코드
				byte[] byteBIZ_CODE   = new byte[ 6];    // 거래구분 코드
				byte[] byteTRA_FLAG   = new byte[ 1];    // 송수신 FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // 응답코드
				byte[] byteSND_DATE   = new byte[ 8];    // 전문전송일
				byte[] byteSND_TIME   = new byte[ 6];    // 전문전송시간
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // 은행 전문번호
				byte[] byteINS_DOCSEQ = new byte[ 8];    // 보험사 전문번호
				byte[] byteTXN_DATE   = new byte[ 8];    // 거래발생일
				byte[] byteTOT_FILE   = new byte[ 2];    // 전체 파일 수
				byte[] byteCUR_FILE   = new byte[ 2];    // 현재 파일 순번
				byte[] byteDOC_NAME   = new byte[30];    // 현재 파일명
				byte[] byteCUR_TOT    = new byte[ 2];    // 현재파일전체전문수
				byte[] byteCUR_NOW    = new byte[ 2];    // 현재파일현재순번
				byte[] byteDOC_CASE   = new byte[ 6];    // 전문 CASE 명
				byte[] byteBAK_EXT    = new byte[30];    // 은행추가정의
				byte[] byteINS_EXT    = new byte[30];    // 보험사추가정의
				
				/*
				 * 요청전문을 받는다.
				 */
				byte[] byteReq = recv(intLength);
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.wrap(byteReq);
				
				byteReqBuffer.get(byteTRX_ID    );
				byteReqBuffer.get(byteSYS_ID    );
				byteReqBuffer.get(byteDOC_LEN   );
				byteReqBuffer.get(byteDAT_GBN   );
				byteReqBuffer.get(byteINS_ID    );
				byteReqBuffer.get(byteBAK_CLS   );
				byteReqBuffer.get(byteBAK_ID    );
				byteReqBuffer.get(byteDOC_CODE  );
				byteReqBuffer.get(byteBIZ_CODE  );
				byteReqBuffer.get(byteTRA_FLAG  );
				byteReqBuffer.get(byteDOC_STATUS);
				byteReqBuffer.get(byteRET_CODE  );
				byteReqBuffer.get(byteSND_DATE  );
				byteReqBuffer.get(byteSND_TIME  );
				byteReqBuffer.get(byteBAK_DOCSEQ);
				byteReqBuffer.get(byteINS_DOCSEQ);
				byteReqBuffer.get(byteTXN_DATE  );
				byteReqBuffer.get(byteTOT_FILE  );
				byteReqBuffer.get(byteCUR_FILE  );
				byteReqBuffer.get(byteDOC_NAME  );
				byteReqBuffer.get(byteCUR_TOT   );
				byteReqBuffer.get(byteCUR_NOW   );
				byteReqBuffer.get(byteDOC_CASE  );
				byteReqBuffer.get(byteBAK_EXT   );
				byteReqBuffer.get(byteINS_EXT   );
				
				/*
				 * 요청전문을 출력한다.
				 */
				if (flag) System.out.println(String.format("마감전문 REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * 응답전문을 만든다.
				 */
				byteDOC_CODE   = String.format("%4.4s", "0810"  ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%6.6s", "000200").getBytes(CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.allocate(intLength);
				
				byteResBuffer.put(byteTRX_ID    );
				byteResBuffer.put(byteSYS_ID    );
				byteResBuffer.put(byteDOC_LEN   );
				byteResBuffer.put(byteDAT_GBN   );
				byteResBuffer.put(byteINS_ID    );
				byteResBuffer.put(byteBAK_CLS   );
				byteResBuffer.put(byteBAK_ID    );
				byteResBuffer.put(byteDOC_CODE  );
				byteResBuffer.put(byteBIZ_CODE  );
				byteResBuffer.put(byteTRA_FLAG  );
				byteResBuffer.put(byteDOC_STATUS);
				byteResBuffer.put(byteRET_CODE  );
				byteResBuffer.put(byteSND_DATE  );
				byteResBuffer.put(byteSND_TIME  );
				byteResBuffer.put(byteBAK_DOCSEQ);
				byteResBuffer.put(byteINS_DOCSEQ);
				byteResBuffer.put(byteTXN_DATE  );
				byteResBuffer.put(byteTOT_FILE  );
				byteResBuffer.put(byteCUR_FILE  );
				byteResBuffer.put(byteDOC_NAME  );
				byteResBuffer.put(byteCUR_TOT   );
				byteResBuffer.put(byteCUR_NOW   );
				byteResBuffer.put(byteDOC_CASE  );
				byteResBuffer.put(byteBAK_EXT   );
				byteResBuffer.put(byteINS_EXT   );
				
				byte[] byteRes = byteResBuffer.array();
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				if (flag) System.out.println(String.format("마감전문 RES [%d][%s]", intResLen, strRes));
				
				/*
				 * 응답전문을 보낸다.
				 */
				send(byteRes, 0, intResLen);
				
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
				System.out.println("---------------- HWI51 ------------------");
				System.out.println("[strFepid     = " + this.batchHostInfo.getStrFepid()   + "]");
				System.out.println("[strInsid     = " + this.batchHostInfo.getStrInsid()   + "]");
				System.out.println("[strName      = " + this.batchHostInfo.getStrName()    + "]");
				System.out.println("[strFepid     = " + this.strFepid                      + "]");
				System.out.println("[strBatchFileFolder = " + this.strBatchFileFolder + "]");
			}
			
			try {
				while (true) {
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
