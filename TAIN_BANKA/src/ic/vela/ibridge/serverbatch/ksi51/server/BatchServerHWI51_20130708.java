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
 * BatchServerHWI51 Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchServerHWI51
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
				 * �ۼ��ſ� charset ��ü�� �����Ѵ�.
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
	 * ������ ��ٸ���.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doAccept() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * Host ���� ����
		 */
		if (flag) System.out.println(String.format("LISTEN ����[PORT=%d]", batchHostInfo.getIntHostPort()));
		
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
	 * ���������� �ް� �����Ѵ�.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private void doOpen_old() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.07.07 : ��ġ��������������
			try {
				int intLength = 188;

				byte[] byteTRX_ID     = new byte[ 9];   // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];   // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[15];   // ��ü��������
				byte[] byteDAT_GBN    = new byte[ 1];   // �ڷᱸ��
				byte[] byteSND_CLS    = new byte[ 1];   // �۽ű������
				byte[] byteSND_ID     = new byte[ 4];   // �۽ű���ڵ�
				byte[] byteRSV_CLS    = new byte[ 1];   // ���ű������
				byte[] byteRSV_ID     = new byte[ 4];   // ���ű���ڵ�
				byte[] byteDOC_CODE   = new byte[ 4];   // �������� �ڵ�
				byte[] byteBIZ_CODE   = new byte[ 4];   // �ŷ����� �ڵ�
				byte[] byteTRA_FLAG   = new byte[ 1];   // �ۼ��� FLAG
				byte[] byteDOC_STATUS = new byte[ 3];   // STATUS
				byte[] byteRET_CODE   = new byte[ 4];   // �����ڵ�
				byte[] byteSND_DATE   = new byte[ 8];   // ����������
				byte[] byteSND_TIME   = new byte[ 6];   // �������۽ð�
				byte[] byteSND_DOCSEQ = new byte[ 8];   // �۽ű��������ȣ
				byte[] byteRSV_DOCSEQ = new byte[ 8];   // ���ű��������ȣ
				byte[] byteTXN_DATE   = new byte[ 8];   // �ŷ��߻���
				byte[] byteTOT_FILE   = new byte[ 2];   // ��ü ���� ��
				byte[] byteCUR_FILE   = new byte[ 2];   // ���� ���� ����
				byte[] byteDOC_NAME   = new byte[35];   // ���� ���ϸ�
				byte[] byteCUR_TOT    = new byte[ 2];   // ����������ü������
				byte[] byteCUR_NOW    = new byte[ 2];   // ���������������
				byte[] byteDOC_CASE   = new byte[ 6];   // ���� CASE��
				byte[] byteSND_EXT    = new byte[26];   // �۽Ż��߰�����
				byte[] byteRSV_EXT    = new byte[21];   // ���Ż��߰�����

				/*
				 * ��û������ �޴´�.
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
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ���������� �����.
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
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
				/*
				 * ���������� ������.
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
	 * ���������� �ް� �����Ѵ�.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doOpen() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.07.07 : ��ī4������ ������� ����������
			try {
				int intLength = 190;

				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // ��ü��������
				byte[] byteDAT_GBN    = new byte[ 1];    // �ڷᱸ��
				byte[] byteINS_ID     = new byte[ 3];    // ������� ID
				byte[] byteBAK_CLS    = new byte[ 2];    // ���౸��
				byte[] byteBAK_ID     = new byte[ 3];    // ���� ID
				byte[] byteDOC_CODE   = new byte[ 4];    // �������� �ڵ�
				byte[] byteBIZ_CODE   = new byte[ 6];    // �ŷ����� �ڵ�
				byte[] byteTRA_FLAG   = new byte[ 1];    // �ۼ��� FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // �����ڵ�
				byte[] byteSND_DATE   = new byte[ 8];    // ����������
				byte[] byteSND_TIME   = new byte[ 6];    // �������۽ð�
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // ���� ������ȣ
				byte[] byteINS_DOCSEQ = new byte[ 8];    // ����� ������ȣ
				byte[] byteTXN_DATE   = new byte[ 8];    // �ŷ��߻���
				byte[] byteTOT_FILE   = new byte[ 2];    // ��ü ���� ��
				byte[] byteCUR_FILE   = new byte[ 2];    // ���� ���� ����
				byte[] byteDOC_NAME   = new byte[30];    // ���� ���ϸ�
				byte[] byteCUR_TOT    = new byte[ 2];    // ����������ü������
				byte[] byteCUR_NOW    = new byte[ 2];    // ���������������
				byte[] byteDOC_CASE   = new byte[ 6];    // ���� CASE ��
				byte[] byteBAK_EXT    = new byte[30];    // �����߰�����
				byte[] byteINS_EXT    = new byte[30];    // ������߰�����
				
				/*
				 * ��û������ �޴´�.
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
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ���������� �����.
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
				
				if (flag) System.out.println(String.format("�������� RES [%d][%s]", intResLen, strRes));
				
				/*
				 * ��ġ���ϸ��� ��´�.
				 */
				this.strFileName = new String(byteDOC_NAME, CHARSET).trim();
				if (!flag) System.out.println("[BatchFile=" + strFileName + "]");
				
				/*
				 * ���������� ������.
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
				 * �ڷḦ �����Ѵ�.
				 */
				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // ��ü��������
				byte[] byteDAT_GBN    = new byte[ 1];    // �ڷᱸ��
				byte[] byteINS_ID     = new byte[ 3];    // ������� ID
				byte[] byteBAK_CLS    = new byte[ 2];    // ���౸��
				byte[] byteBAK_ID     = new byte[ 3];    // ���� ID
				byte[] byteDOC_CODE   = new byte[ 4];    // �������� �ڵ�
				byte[] byteBIZ_CODE   = new byte[ 6];    // �ŷ����� �ڵ�
				byte[] byteTRA_FLAG   = new byte[ 1];    // �ۼ��� FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // �����ڵ�
				byte[] byteSND_DATE   = new byte[ 8];    // ����������
				byte[] byteSND_TIME   = new byte[ 6];    // �������۽ð�
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // ���� ������ȣ
				byte[] byteINS_DOCSEQ = new byte[ 8];    // ����� ������ȣ
				byte[] byteTXN_DATE   = new byte[ 8];    // �ŷ��߻���
				byte[] byteTOT_FILE   = new byte[ 2];    // ��ü ���� ��
				byte[] byteCUR_FILE   = new byte[ 2];    // ���� ���� ����
				byte[] byteDOC_NAME   = new byte[30];    // ���� ���ϸ�
				byte[] byteCUR_TOT    = new byte[ 2];    // ����������ü������
				byte[] byteCUR_NOW    = new byte[ 2];    // ���������������
				byte[] byteDOC_CASE   = new byte[ 6];    // ���� CASE ��
				byte[] byteBAK_EXT    = new byte[30];    // �����߰�����
				byte[] byteINS_EXT    = new byte[30];    // ������߰�����
				
				/*
				 * header �� �����Ѵ�.
				 */
				int intHeaderLength = 190;
				byteHeader = recv(intHeaderLength);

				/*
				 * header �� ����Ѵ�.
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
				 * data �� �����Ѵ�.
				 */
				int intDataLength = Integer.parseInt(new String(byteDOC_LEN)) - intHeaderLength;
				byteData = recv(intDataLength);

				/*
				 * data �� ����Ѵ�.
				 */
				if (!flag) {
					String strData = new String(byteData, CHARSET);
					System.out.println(String.format("DATA [%d][%s]", intDataLength, strData));
				}
				
				if (flag) {
					String strHeader = new String(byteHeader, CHARSET);
					String strData = new String(byteData, CHARSET);

					System.out.println(String.format("������ RECV [%05d][%s]", intHeaderLength + intDataLength, strHeader + strData));
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
			 * ��ġ���� header ������ ������.
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
			 * ��ġ���� body ������ �޴´�.
			 */
			
			this.iRecLen = 0;
			this.iRecCount = 0;
			
			//while (true) {
			for (int i=0; i < 50; i++)
			{
				/*
				 * �����͸� ���� �޴´�.
				 */
				byte[] byteLine = doDataReceiver();
				String strLine = new String(byteLine, CHARSET);
				
				/*
				 * �����͸� ��ġ���Ͽ� �����Ѵ�.
				 */
				this.bufferedWriter.write(strLine);
				this.bufferedWriter.newLine();
				this.iRecCount ++;
				this.iRecLen = Math.max(this.iRecLen, byteLine.length);
				
				// display the the byte line data
				if (!flag) System.out.println(String.format("[%04d][%05d][%s]", this.iRecCount, this.iRecLen, strLine));

				/*
				 * body �� �������̸� break �Ѵ�. 
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
			 * ��ġ���� tailer ������ ������.
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
	 * ��ġ������ open �Ѵ�.
	 * �����͸� �޴°� ��ġ���Ͽ� �����Ѵ�.
	 * ��ġ������ �ݴ´�. 
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doDataTransfer() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * ��ġ������ �����Ѵ�.
		 */
		String strFile = this.strBatchFileFolder + "/" + this.strFileName;
		String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
		strFile = strFile.replace("YYYYMMDD", strDate);
		
		if (flag) System.out.println(String.format("ó���� ��ġ���� [%s]", strFile));
		
		if (flag) {   // DATE.2013.06.27

			try {
				/*
				 *  ��ġ������ open �Ѵ�.
				 */
				FileOutputStream fileOutputStream = new FileOutputStream(strFile);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET);
				this.bufferedWriter = new BufferedWriter(outputStreamWriter);
				
				/*
				 * ��ġ���� �ش��� �����Ѵ�.
				 */
				doDataTransferHeader();
				
				/*
				 * ��ġ���� �����͸� �����Ѵ�.
				 */
				doDataTransferBody();
				
				/*
				 * ��ġ���� ���Ϸ��� �����Ѵ�.
				 */
				doDataTransferTailer();

				/*
				 * ��ġ������ close �Ѵ�.
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
	 * ���������� �ް� ������ ������.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private void doClose_old() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.07.07 : ��ġ��������������
			try {
				int intLength = 188;

				byte[] byteTRX_ID     = new byte[ 9];   // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];   // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[15];   // ��ü��������
				byte[] byteDAT_GBN    = new byte[ 1];   // �ڷᱸ��
				byte[] byteSND_CLS    = new byte[ 1];   // �۽ű������
				byte[] byteSND_ID     = new byte[ 4];   // �۽ű���ڵ�
				byte[] byteRSV_CLS    = new byte[ 1];   // ���ű������
				byte[] byteRSV_ID     = new byte[ 4];   // ���ű���ڵ�
				byte[] byteDOC_CODE   = new byte[ 4];   // �������� �ڵ�
				byte[] byteBIZ_CODE   = new byte[ 4];   // �ŷ����� �ڵ�
				byte[] byteTRA_FLAG   = new byte[ 1];   // �ۼ��� FLAG
				byte[] byteDOC_STATUS = new byte[ 3];   // STATUS
				byte[] byteRET_CODE   = new byte[ 4];   // �����ڵ�
				byte[] byteSND_DATE   = new byte[ 8];   // ����������
				byte[] byteSND_TIME   = new byte[ 6];   // �������۽ð�
				byte[] byteSND_DOCSEQ = new byte[ 8];   // �۽ű��������ȣ
				byte[] byteRSV_DOCSEQ = new byte[ 8];   // ���ű��������ȣ
				byte[] byteTXN_DATE   = new byte[ 8];   // �ŷ��߻���
				byte[] byteTOT_FILE   = new byte[ 2];   // ��ü ���� ��
				byte[] byteCUR_FILE   = new byte[ 2];   // ���� ���� ����
				byte[] byteDOC_NAME   = new byte[35];   // ���� ���ϸ�
				byte[] byteCUR_TOT    = new byte[ 2];   // ����������ü������
				byte[] byteCUR_NOW    = new byte[ 2];   // ���������������
				byte[] byteDOC_CASE   = new byte[ 6];   // ���� CASE��
				byte[] byteSND_EXT    = new byte[26];   // �۽Ż��߰�����
				byte[] byteRSV_EXT    = new byte[21];   // ���Ż��߰�����

				/*
				 * ��û������ �޴´�.
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
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ���������� �����.
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
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
				/*
				 * ���������� ������.
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
	 * ���������� �ް� ������ ������.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doClose() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.07.07 : ��ī4������ ������� ����������
			try {
				int intLength = 190;

				byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
				byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
				byte[] byteDOC_LEN    = new byte[10];    // ��ü��������
				byte[] byteDAT_GBN    = new byte[ 1];    // �ڷᱸ��
				byte[] byteINS_ID     = new byte[ 3];    // ������� ID
				byte[] byteBAK_CLS    = new byte[ 2];    // ���౸��
				byte[] byteBAK_ID     = new byte[ 3];    // ���� ID
				byte[] byteDOC_CODE   = new byte[ 4];    // �������� �ڵ�
				byte[] byteBIZ_CODE   = new byte[ 6];    // �ŷ����� �ڵ�
				byte[] byteTRA_FLAG   = new byte[ 1];    // �ۼ��� FLAG
				byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
				byte[] byteRET_CODE   = new byte[ 3];    // �����ڵ�
				byte[] byteSND_DATE   = new byte[ 8];    // ����������
				byte[] byteSND_TIME   = new byte[ 6];    // �������۽ð�
				byte[] byteBAK_DOCSEQ = new byte[ 8];    // ���� ������ȣ
				byte[] byteINS_DOCSEQ = new byte[ 8];    // ����� ������ȣ
				byte[] byteTXN_DATE   = new byte[ 8];    // �ŷ��߻���
				byte[] byteTOT_FILE   = new byte[ 2];    // ��ü ���� ��
				byte[] byteCUR_FILE   = new byte[ 2];    // ���� ���� ����
				byte[] byteDOC_NAME   = new byte[30];    // ���� ���ϸ�
				byte[] byteCUR_TOT    = new byte[ 2];    // ����������ü������
				byte[] byteCUR_NOW    = new byte[ 2];    // ���������������
				byte[] byteDOC_CASE   = new byte[ 6];    // ���� CASE ��
				byte[] byteBAK_EXT    = new byte[30];    // �����߰�����
				byte[] byteINS_EXT    = new byte[30];    // ������߰�����
				
				/*
				 * ��û������ �޴´�.
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
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ���������� �����.
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
				
				if (flag) System.out.println(String.format("�������� RES [%d][%s]", intResLen, strRes));
				
				/*
				 * ���������� ������.
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
	 * ��ġó���� ��ġ�� ���õ� ������ �����Ѵ�.
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
	 * ����ó���� ����� chk file�� ����Ѵ�.
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
			 * ó���Ͻ�
			 */
			String strJobDateTime = strDateTime;
			
			/*
			 * ���� path
			 */
			String strJobPath = this.strBatchFileFolder.replace('\\', '/').replace("YYYYMMDD", strDate);
			
			/*
			 * ��ġ���ϸ�
			 */
			String strJobFileName = this.strFileName;
					
			/*
			 * ��ġ���� �Ӽ��Ͻ�
			 */
			File file = new File(strJobPath + "/" + strJobFileName);
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * ������ �����Ǵ� ��ġ���ϸ�
			 */
			String strSendFileName = this.strFileName;
			
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
				 * �⺻������ Ȯ���Ѵ�.
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
						 * ������ �õ��Ѵ�.
						 */
						doAccept();
					}
					
					if (flag) {
						/*
						 * ���������� �޴´�
						 */
						doOpen();
					}
					
					if (flag) {
						/*
						 * DATA ������ �޴´�.
						 */
						doDataTransfer();
					}
					
					if (flag) {
						/*
						 * ���������� �޴´�.
						 */
						doClose();
					}
					
					if (flag) {
						/*
						 * ������ �����Ѵ�.
						 */
						doDisConnection();
					}
					
					if (flag) {
						/*
						 * ��ó���� �Ѵ�.
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
	 * default test : �ܼ��� ���� ���
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
