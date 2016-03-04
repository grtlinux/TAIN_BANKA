package ic.vela.ibridge.serverbatch.ksi51.client;

import ic.vela.ibridge.serverbatch.ksi51.common.BatchFolder;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchHostInfo;
import ic.vela.ibridge.serverbatch.ksi51.common.BatchTrInfo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*==================================================================*/
/**
 * BatchClientHWI51 Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchClientHWI51
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
public class BatchClientHWI51_20130708
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	
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
	
	private BufferedReader             bufferedReader      = null;
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
	public BatchClientHWI51_20130708(
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
				 * �ۼ��ſ� charset ��ü�� �����Ѵ�.
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
	 * ������ �õ��Ѵ�.
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void doConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		/*
		 * Host ���� ����
		 */
		if (flag) System.out.println(String.format("��������[%s:%d]", batchHostInfo.getStrHostIp(), batchHostInfo.getIntHostPort()));
		
		if (flag) {    // DATE.2013.07.07
			try {
				/* ���ӽõ��� �Ѵ�. 2�ʰ������� �ִ� MAX_CONNECTION_TRY�� ���ӽõ�
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
					 * ���ӽõ� ��� null �̸� 10�� �Ŀ� �ٽ� ���ӽõ� �Ѵ�.
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
	 * ���������� ������ ������ �޴´�.
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
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
				String strFile = "L01701101_" + strDate;
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
				 * ��û������ �����.
				 */
				byteTRX_ID     = String.format("%9.9s"  , ""        ).getBytes(CHARSET);
				byteSYS_ID     = String.format("%3.3s"  , "K11"     ).getBytes(CHARSET);
				byteDOC_LEN    = String.format("%015d"  , 188       ).getBytes(CHARSET);
				byteDAT_GBN    = String.format("%1.1s"  , "T"       ).getBytes(CHARSET);
				byteSND_CLS    = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteSND_ID     = String.format("%4.4s"  , "L001"    ).getBytes(CHARSET);
				byteRSV_CLS    = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteRSV_ID     = String.format("%4.4s"  , "L008"    ).getBytes(CHARSET);
				byteDOC_CODE   = String.format("%4.4s"  , "0800"    ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%4.4s"  , "0100"    ).getBytes(CHARSET);
				byteTRA_FLAG   = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteDOC_STATUS = String.format("%3.3s"  , ""        ).getBytes(CHARSET);
				byteRET_CODE   = String.format("%4.4s"  , "0000"    ).getBytes(CHARSET);
				byteSND_DATE   = String.format("%8.8s"  , strDate   ).getBytes(CHARSET);
				byteSND_TIME   = String.format("%6.6s"  , strTime   ).getBytes(CHARSET);
				byteSND_DOCSEQ = String.format("%8.8s"  , "00000000").getBytes(CHARSET);
				byteRSV_DOCSEQ = String.format("%8.8s"  , "00000000").getBytes(CHARSET);
				byteTXN_DATE   = String.format("%8.8s"  , strDate   ).getBytes(CHARSET);
				byteTOT_FILE   = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteCUR_FILE   = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteDOC_NAME   = String.format("%35.35s", strFile   ).getBytes(CHARSET);
				byteCUR_TOT    = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteCUR_NOW    = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteDOC_CASE   = String.format("%6.6s"  , ""        ).getBytes(CHARSET);
				byteSND_EXT    = String.format("%26.26s", ""        ).getBytes(CHARSET);
				byteRSV_EXT    = String.format("%21.21s", ""        ).getBytes(CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.allocate(intLength);
				
				byteReqBuffer.put(byteTRX_ID    );
				byteReqBuffer.put(byteSYS_ID    );
				byteReqBuffer.put(byteDOC_LEN   );
				byteReqBuffer.put(byteDAT_GBN   );
				byteReqBuffer.put(byteSND_CLS   );
				byteReqBuffer.put(byteSND_ID    );
				byteReqBuffer.put(byteRSV_CLS   );
				byteReqBuffer.put(byteRSV_ID    );
				byteReqBuffer.put(byteDOC_CODE  );
				byteReqBuffer.put(byteBIZ_CODE  );
				byteReqBuffer.put(byteTRA_FLAG  );
				byteReqBuffer.put(byteDOC_STATUS);
				byteReqBuffer.put(byteRET_CODE  );
				byteReqBuffer.put(byteSND_DATE  );
				byteReqBuffer.put(byteSND_TIME  );
				byteReqBuffer.put(byteSND_DOCSEQ);
				byteReqBuffer.put(byteRSV_DOCSEQ);
				byteReqBuffer.put(byteTXN_DATE  );
				byteReqBuffer.put(byteTOT_FILE  );
				byteReqBuffer.put(byteCUR_FILE  );
				byteReqBuffer.put(byteDOC_NAME  );
				byteReqBuffer.put(byteCUR_TOT   );
				byteReqBuffer.put(byteCUR_NOW   );
				byteReqBuffer.put(byteDOC_CASE  );
				byteReqBuffer.put(byteSND_EXT   );
				byteReqBuffer.put(byteRSV_EXT   );
				
				byte[] byteReq = byteReqBuffer.array();
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				/*
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ��û������ ������.
				 */
				send(byteReq, 0, intReqLen);
				
				/*
				 * ���������� �޴´�.
				 */
				byte[] byteRes = recv(intLength);
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.wrap(byteRes);
				
				byteResBuffer.get(byteTRX_ID    );
				byteResBuffer.get(byteSYS_ID    );
				byteResBuffer.get(byteDOC_LEN   );
				byteResBuffer.get(byteDAT_GBN   );
				byteResBuffer.get(byteSND_CLS   );
				byteResBuffer.get(byteSND_ID    );
				byteResBuffer.get(byteRSV_CLS   );
				byteResBuffer.get(byteRSV_ID    );
				byteResBuffer.get(byteDOC_CODE  );
				byteResBuffer.get(byteBIZ_CODE  );
				byteResBuffer.get(byteTRA_FLAG  );
				byteResBuffer.get(byteDOC_STATUS);
				byteResBuffer.get(byteRET_CODE  );
				byteResBuffer.get(byteSND_DATE  );
				byteResBuffer.get(byteSND_TIME  );
				byteResBuffer.get(byteSND_DOCSEQ);
				byteResBuffer.get(byteRSV_DOCSEQ);
				byteResBuffer.get(byteTXN_DATE  );
				byteResBuffer.get(byteTOT_FILE  );
				byteResBuffer.get(byteCUR_FILE  );
				byteResBuffer.get(byteDOC_NAME  );
				byteResBuffer.get(byteCUR_TOT   );
				byteResBuffer.get(byteCUR_NOW   );
				byteResBuffer.get(byteDOC_CASE  );
				byteResBuffer.get(byteSND_EXT   );
				byteResBuffer.get(byteRSV_EXT   );
				
				/*
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
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
	 * ���������� ������ ������ �޴´�.
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
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
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
				
				byteTRX_ID     = String.format("%9.9s"  , ""         ).getBytes(CHARSET);
				byteSYS_ID     = String.format("%3.3s"  , "BAS"      ).getBytes(CHARSET);
				byteDOC_LEN    = String.format("%010d"  , 190        ).getBytes(CHARSET);
				byteDAT_GBN    = String.format("%1.1s"  , "T"        ).getBytes(CHARSET);
				byteINS_ID     = String.format("%3.3s"  , "L01"      ).getBytes(CHARSET);
				byteBAK_CLS    = String.format("%2.2s"  , "02"       ).getBytes(CHARSET);
				byteBAK_ID     = String.format("%3.3s"  , "269"      ).getBytes(CHARSET);
				byteDOC_CODE   = String.format("%4.4s"  , "0800"     ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%6.6s"  , "000100"   ).getBytes(CHARSET);
				byteTRA_FLAG   = String.format("%1.1s"  , "1"        ).getBytes(CHARSET);
				byteDOC_STATUS = String.format("%3.3s"  , ""         ).getBytes(CHARSET);
				byteRET_CODE   = String.format("%3.3s"  , "000"      ).getBytes(CHARSET);
				byteSND_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteSND_TIME   = String.format("%6.6s"  , strTime    ).getBytes(CHARSET);
				byteBAK_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteINS_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteTXN_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteTOT_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_NAME   = String.format("%30.30s", strFileName).getBytes(CHARSET);
				byteCUR_TOT    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_NOW    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_CASE   = String.format("%6.6s"  , ""         ).getBytes(CHARSET);
				byteBAK_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);
				byteINS_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);

				ByteBuffer byteReqBuffer = ByteBuffer.allocate(intLength);
				
				byteReqBuffer.put(byteTRX_ID    );
				byteReqBuffer.put(byteSYS_ID    );
				byteReqBuffer.put(byteDOC_LEN   );
				byteReqBuffer.put(byteDAT_GBN   );
				byteReqBuffer.put(byteINS_ID    );
				byteReqBuffer.put(byteBAK_CLS   );
				byteReqBuffer.put(byteBAK_ID    );
				byteReqBuffer.put(byteDOC_CODE  );
				byteReqBuffer.put(byteBIZ_CODE  );
				byteReqBuffer.put(byteTRA_FLAG  );
				byteReqBuffer.put(byteDOC_STATUS);
				byteReqBuffer.put(byteRET_CODE  );
				byteReqBuffer.put(byteSND_DATE  );
				byteReqBuffer.put(byteSND_TIME  );
				byteReqBuffer.put(byteBAK_DOCSEQ);
				byteReqBuffer.put(byteINS_DOCSEQ);
				byteReqBuffer.put(byteTXN_DATE  );
				byteReqBuffer.put(byteTOT_FILE  );
				byteReqBuffer.put(byteCUR_FILE  );
				byteReqBuffer.put(byteDOC_NAME  );
				byteReqBuffer.put(byteCUR_TOT   );
				byteReqBuffer.put(byteCUR_NOW   );
				byteReqBuffer.put(byteDOC_CASE  );
				byteReqBuffer.put(byteBAK_EXT   );
				byteReqBuffer.put(byteINS_EXT   );

				byte[] byteReq = byteReqBuffer.array();
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				/*
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ��û������ ������.
				 */
				send(byteReq, 0, intReqLen);
				
				/*
				 * ���������� �޴´�.
				 */
				byte[] byteRes = recv(intLength);
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.wrap(byteRes);
				
				byteResBuffer.get(byteTRX_ID    );
				byteResBuffer.get(byteSYS_ID    );
				byteResBuffer.get(byteDOC_LEN   );
				byteResBuffer.get(byteDAT_GBN   );
				byteResBuffer.get(byteINS_ID    );
				byteResBuffer.get(byteBAK_CLS   );
				byteResBuffer.get(byteBAK_ID    );
				byteResBuffer.get(byteDOC_CODE  );
				byteResBuffer.get(byteBIZ_CODE  );
				byteResBuffer.get(byteTRA_FLAG  );
				byteResBuffer.get(byteDOC_STATUS);
				byteResBuffer.get(byteRET_CODE  );
				byteResBuffer.get(byteSND_DATE  );
				byteResBuffer.get(byteSND_TIME  );
				byteResBuffer.get(byteBAK_DOCSEQ);
				byteResBuffer.get(byteINS_DOCSEQ);
				byteResBuffer.get(byteTXN_DATE  );
				byteResBuffer.get(byteTOT_FILE  );
				byteResBuffer.get(byteCUR_FILE  );
				byteResBuffer.get(byteDOC_NAME  );
				byteResBuffer.get(byteCUR_TOT   );
				byteResBuffer.get(byteCUR_NOW   );
				byteResBuffer.get(byteDOC_CASE  );
				byteResBuffer.get(byteBAK_EXT   );
				byteResBuffer.get(byteINS_EXT   );
				
				/*
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� RES [%d][%s]", intResLen, strRes));

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
	private int doDataSender(String strLine) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		byte[] byteLine = null;
		
		if (flag) {
			try {
				/*
				 * byte ��ȯ
				 */
				byteLine = strLine.getBytes(CHARSET);

				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
				int intLength = 190 + byteLine.length;

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

				byteTRX_ID     = String.format("%9.9s"  , ""         ).getBytes(CHARSET);
				byteSYS_ID     = String.format("%3.3s"  , "BAS"      ).getBytes(CHARSET);
				byteDOC_LEN    = String.format("%010d"  , intLength  ).getBytes(CHARSET);
				byteDAT_GBN    = String.format("%1.1s"  , "T"        ).getBytes(CHARSET);
				byteINS_ID     = String.format("%3.3s"  , "L01"      ).getBytes(CHARSET);
				byteBAK_CLS    = String.format("%2.2s"  , "02"       ).getBytes(CHARSET);
				byteBAK_ID     = String.format("%3.3s"  , "269"      ).getBytes(CHARSET);
				byteDOC_CODE   = String.format("%4.4s"  , "0600"     ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%6.6s"  , "701101"   ).getBytes(CHARSET);
				byteTRA_FLAG   = String.format("%1.1s"  , "1"        ).getBytes(CHARSET);
				byteDOC_STATUS = String.format("%3.3s"  , ""         ).getBytes(CHARSET);
				byteRET_CODE   = String.format("%3.3s"  , "000"      ).getBytes(CHARSET);
				byteSND_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteSND_TIME   = String.format("%6.6s"  , strTime    ).getBytes(CHARSET);
				byteBAK_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteINS_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteTXN_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteTOT_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_NAME   = String.format("%30.30s", strFileName).getBytes(CHARSET);
				byteCUR_TOT    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_NOW    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_CASE   = String.format("%6.6s"  , ""         ).getBytes(CHARSET);
				byteBAK_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);
				byteINS_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);

				ByteBuffer byteReqBuffer = ByteBuffer.allocate(intLength);
				
				byteReqBuffer.put(byteTRX_ID    );
				byteReqBuffer.put(byteSYS_ID    );
				byteReqBuffer.put(byteDOC_LEN   );
				byteReqBuffer.put(byteDAT_GBN   );
				byteReqBuffer.put(byteINS_ID    );
				byteReqBuffer.put(byteBAK_CLS   );
				byteReqBuffer.put(byteBAK_ID    );
				byteReqBuffer.put(byteDOC_CODE  );
				byteReqBuffer.put(byteBIZ_CODE  );
				byteReqBuffer.put(byteTRA_FLAG  );
				byteReqBuffer.put(byteDOC_STATUS);
				byteReqBuffer.put(byteRET_CODE  );
				byteReqBuffer.put(byteSND_DATE  );
				byteReqBuffer.put(byteSND_TIME  );
				byteReqBuffer.put(byteBAK_DOCSEQ);
				byteReqBuffer.put(byteINS_DOCSEQ);
				byteReqBuffer.put(byteTXN_DATE  );
				byteReqBuffer.put(byteTOT_FILE  );
				byteReqBuffer.put(byteCUR_FILE  );
				byteReqBuffer.put(byteDOC_NAME  );
				byteReqBuffer.put(byteCUR_TOT   );
				byteReqBuffer.put(byteCUR_NOW   );
				byteReqBuffer.put(byteDOC_CASE  );
				byteReqBuffer.put(byteBAK_EXT   );
				byteReqBuffer.put(byteINS_EXT   );

				byteReqBuffer.put(byteLine      );  // DATA Header
				
				byte[] byteReq = byteReqBuffer.array();
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);

				/*
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("������ SEND [%05d][%s]", intReqLen, strReq));
				
				/*
				 * ��û������ ������.
				 */
				send(byteReq, 0, intReqLen);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return byteLine.length;
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
			
			String strLine = this.bufferedReader.readLine();
			doDataSender(strLine);
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
		
		if (flag) {     // DATE.2013.07.07
			/*
			 * ��ġ���� body ������ ������.
			 */
			
			String strLine = null;
			
			this.intRecCount = 0;
			int intRecLen = 0;
			
			while ((strLine = this.bufferedReader.readLine()) != null) {
				
				intRecLen = doDataSender(strLine);
				if (intRecLength < intRecLen) {
					this.intRecLength = intRecLen;
				}
				
				this.intRecCount ++;
				
				// display the the byte line data
				if (!flag) System.out.println(String.format("[%04d][%05d][%s]", intRecCount, intRecLength, strLine));
				
				if (intRecLen < intRecLength) {
					break;
				}
			}
		}
		
		if (flag) {
			;
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
			
			String strLine = this.bufferedReader.readLine();
			doDataSender(strLine);
		}
	}
	
	/*==================================================================*/
	/**
	 * do data transfer
	 * ��ġ������ ����.
	 * �����͸� �����ϰ� ���������� �޴´�.
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
		String strFile = this.batchFolder.getStrSrcFolder() + "/" + this.strFileName;
		if (flag) System.out.println(String.format("ó���� ��ġ���� [%s]", strFile));
		
		if (flag) {   // DATE.2013.06.27

			try {
				/*
				 *  ��ġ������ open �Ѵ�.
				 */
				FileInputStream fileInputStream = new FileInputStream(strFile);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET);
				this.bufferedReader = new BufferedReader(inputStreamReader);
				
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
				this.bufferedReader.close();
				
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * do close job
	 * ���������� ������ ���������� �޴´�.
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
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
				String strFile = "L01701101_" + strDate;
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
				 * ��û������ �����.
				 */
				byteTRX_ID     = String.format("%9.9s"  , ""        ).getBytes(CHARSET);
				byteSYS_ID     = String.format("%3.3s"  , "K11"     ).getBytes(CHARSET);
				byteDOC_LEN    = String.format("%015d"  , 188       ).getBytes(CHARSET);
				byteDAT_GBN    = String.format("%1.1s"  , "T"       ).getBytes(CHARSET);
				byteSND_CLS    = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteSND_ID     = String.format("%4.4s"  , "L001"    ).getBytes(CHARSET);
				byteRSV_CLS    = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteRSV_ID     = String.format("%4.4s"  , "L008"    ).getBytes(CHARSET);
				byteDOC_CODE   = String.format("%4.4s"  , "0800"    ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%4.4s"  , "0200"    ).getBytes(CHARSET);
				byteTRA_FLAG   = String.format("%1.1s"  , "1"       ).getBytes(CHARSET);
				byteDOC_STATUS = String.format("%3.3s"  , ""        ).getBytes(CHARSET);
				byteRET_CODE   = String.format("%4.4s"  , "0000"    ).getBytes(CHARSET);
				byteSND_DATE   = String.format("%8.8s"  , strDate   ).getBytes(CHARSET);
				byteSND_TIME   = String.format("%6.6s"  , strTime   ).getBytes(CHARSET);
				byteSND_DOCSEQ = String.format("%8.8s"  , "00000000").getBytes(CHARSET);
				byteRSV_DOCSEQ = String.format("%8.8s"  , "00000000").getBytes(CHARSET);
				byteTXN_DATE   = String.format("%8.8s"  , strDate   ).getBytes(CHARSET);
				byteTOT_FILE   = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteCUR_FILE   = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteDOC_NAME   = String.format("%35.35s", strFile   ).getBytes(CHARSET);
				byteCUR_TOT    = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteCUR_NOW    = String.format("%2.2s"  , ""        ).getBytes(CHARSET);
				byteDOC_CASE   = String.format("%6.6s"  , ""        ).getBytes(CHARSET);
				byteSND_EXT    = String.format("%26.26s", ""        ).getBytes(CHARSET);
				byteRSV_EXT    = String.format("%21.21s", ""        ).getBytes(CHARSET);
				
				ByteBuffer byteReqBuffer = ByteBuffer.allocate(intLength);
				
				byteReqBuffer.put(byteTRX_ID    );
				byteReqBuffer.put(byteSYS_ID    );
				byteReqBuffer.put(byteDOC_LEN   );
				byteReqBuffer.put(byteDAT_GBN   );
				byteReqBuffer.put(byteSND_CLS   );
				byteReqBuffer.put(byteSND_ID    );
				byteReqBuffer.put(byteRSV_CLS   );
				byteReqBuffer.put(byteRSV_ID    );
				byteReqBuffer.put(byteDOC_CODE  );
				byteReqBuffer.put(byteBIZ_CODE  );
				byteReqBuffer.put(byteTRA_FLAG  );
				byteReqBuffer.put(byteDOC_STATUS);
				byteReqBuffer.put(byteRET_CODE  );
				byteReqBuffer.put(byteSND_DATE  );
				byteReqBuffer.put(byteSND_TIME  );
				byteReqBuffer.put(byteSND_DOCSEQ);
				byteReqBuffer.put(byteRSV_DOCSEQ);
				byteReqBuffer.put(byteTXN_DATE  );
				byteReqBuffer.put(byteTOT_FILE  );
				byteReqBuffer.put(byteCUR_FILE  );
				byteReqBuffer.put(byteDOC_NAME  );
				byteReqBuffer.put(byteCUR_TOT   );
				byteReqBuffer.put(byteCUR_NOW   );
				byteReqBuffer.put(byteDOC_CASE  );
				byteReqBuffer.put(byteSND_EXT   );
				byteReqBuffer.put(byteRSV_EXT   );
				
				byte[] byteReq = byteReqBuffer.array();
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				/*
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ��û������ ������.
				 */
				send(byteReq, 0, intReqLen);
				
				/*
				 * ���������� �޴´�.
				 */
				byte[] byteRes = recv(intLength);
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.wrap(byteRes);
				
				byteResBuffer.get(byteTRX_ID    );
				byteResBuffer.get(byteSYS_ID    );
				byteResBuffer.get(byteDOC_LEN   );
				byteResBuffer.get(byteDAT_GBN   );
				byteResBuffer.get(byteSND_CLS   );
				byteResBuffer.get(byteSND_ID    );
				byteResBuffer.get(byteRSV_CLS   );
				byteResBuffer.get(byteRSV_ID    );
				byteResBuffer.get(byteDOC_CODE  );
				byteResBuffer.get(byteBIZ_CODE  );
				byteResBuffer.get(byteTRA_FLAG  );
				byteResBuffer.get(byteDOC_STATUS);
				byteResBuffer.get(byteRET_CODE  );
				byteResBuffer.get(byteSND_DATE  );
				byteResBuffer.get(byteSND_TIME  );
				byteResBuffer.get(byteSND_DOCSEQ);
				byteResBuffer.get(byteRSV_DOCSEQ);
				byteResBuffer.get(byteTXN_DATE  );
				byteResBuffer.get(byteTOT_FILE  );
				byteResBuffer.get(byteCUR_FILE  );
				byteResBuffer.get(byteDOC_NAME  );
				byteResBuffer.get(byteCUR_TOT   );
				byteResBuffer.get(byteCUR_NOW   );
				byteResBuffer.get(byteDOC_CASE  );
				byteResBuffer.get(byteSND_EXT   );
				byteResBuffer.get(byteRSV_EXT   );
				
				/*
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("RES [%d][%s]", intResLen, strRes));
				
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
	 * ���������� ������ ���������� �޴´�.
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
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
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
				
				byteTRX_ID     = String.format("%9.9s"  , ""         ).getBytes(CHARSET);
				byteSYS_ID     = String.format("%3.3s"  , "BAS"      ).getBytes(CHARSET);
				byteDOC_LEN    = String.format("%010d"  , 190        ).getBytes(CHARSET);
				byteDAT_GBN    = String.format("%1.1s"  , "T"        ).getBytes(CHARSET);
				byteINS_ID     = String.format("%3.3s"  , "L01"      ).getBytes(CHARSET);
				byteBAK_CLS    = String.format("%2.2s"  , "02"       ).getBytes(CHARSET);
				byteBAK_ID     = String.format("%3.3s"  , "269"      ).getBytes(CHARSET);
				byteDOC_CODE   = String.format("%4.4s"  , "0800"     ).getBytes(CHARSET);
				byteBIZ_CODE   = String.format("%6.6s"  , "000200"   ).getBytes(CHARSET);
				byteTRA_FLAG   = String.format("%1.1s"  , "1"        ).getBytes(CHARSET);
				byteDOC_STATUS = String.format("%3.3s"  , ""         ).getBytes(CHARSET);
				byteRET_CODE   = String.format("%3.3s"  , "000"      ).getBytes(CHARSET);
				byteSND_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteSND_TIME   = String.format("%6.6s"  , strTime    ).getBytes(CHARSET);
				byteBAK_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteINS_DOCSEQ = String.format("%8.8s"  , "00000000" ).getBytes(CHARSET);
				byteTXN_DATE   = String.format("%8.8s"  , strDate    ).getBytes(CHARSET);
				byteTOT_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_FILE   = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_NAME   = String.format("%30.30s", strFileName).getBytes(CHARSET);
				byteCUR_TOT    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteCUR_NOW    = String.format("%2.2s"  , ""         ).getBytes(CHARSET);
				byteDOC_CASE   = String.format("%6.6s"  , ""         ).getBytes(CHARSET);
				byteBAK_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);
				byteINS_EXT    = String.format("%30.30s", ""         ).getBytes(CHARSET);

				ByteBuffer byteReqBuffer = ByteBuffer.allocate(intLength);
				
				byteReqBuffer.put(byteTRX_ID    );
				byteReqBuffer.put(byteSYS_ID    );
				byteReqBuffer.put(byteDOC_LEN   );
				byteReqBuffer.put(byteDAT_GBN   );
				byteReqBuffer.put(byteINS_ID    );
				byteReqBuffer.put(byteBAK_CLS   );
				byteReqBuffer.put(byteBAK_ID    );
				byteReqBuffer.put(byteDOC_CODE  );
				byteReqBuffer.put(byteBIZ_CODE  );
				byteReqBuffer.put(byteTRA_FLAG  );
				byteReqBuffer.put(byteDOC_STATUS);
				byteReqBuffer.put(byteRET_CODE  );
				byteReqBuffer.put(byteSND_DATE  );
				byteReqBuffer.put(byteSND_TIME  );
				byteReqBuffer.put(byteBAK_DOCSEQ);
				byteReqBuffer.put(byteINS_DOCSEQ);
				byteReqBuffer.put(byteTXN_DATE  );
				byteReqBuffer.put(byteTOT_FILE  );
				byteReqBuffer.put(byteCUR_FILE  );
				byteReqBuffer.put(byteDOC_NAME  );
				byteReqBuffer.put(byteCUR_TOT   );
				byteReqBuffer.put(byteCUR_NOW   );
				byteReqBuffer.put(byteDOC_CASE  );
				byteReqBuffer.put(byteBAK_EXT   );
				byteReqBuffer.put(byteINS_EXT   );

				byte[] byteReq = byteReqBuffer.array();
				int intReqLen = byteReq.length;
				String strReq = new String(byteReq, CHARSET);
				
				/*
				 * ��û������ ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� REQ [%d][%s]", intReqLen, strReq));
				
				/*
				 * ��û������ ������.
				 */
				send(byteReq, 0, intReqLen);
				
				/*
				 * ���������� �޴´�.
				 */
				byte[] byteRes = recv(intLength);
				int intResLen = byteRes.length;
				String strRes = new String(byteRes, CHARSET);
				
				ByteBuffer byteResBuffer = ByteBuffer.wrap(byteRes);
				
				byteResBuffer.get(byteTRX_ID    );
				byteResBuffer.get(byteSYS_ID    );
				byteResBuffer.get(byteDOC_LEN   );
				byteResBuffer.get(byteDAT_GBN   );
				byteResBuffer.get(byteINS_ID    );
				byteResBuffer.get(byteBAK_CLS   );
				byteResBuffer.get(byteBAK_ID    );
				byteResBuffer.get(byteDOC_CODE  );
				byteResBuffer.get(byteBIZ_CODE  );
				byteResBuffer.get(byteTRA_FLAG  );
				byteResBuffer.get(byteDOC_STATUS);
				byteResBuffer.get(byteRET_CODE  );
				byteResBuffer.get(byteSND_DATE  );
				byteResBuffer.get(byteSND_TIME  );
				byteResBuffer.get(byteBAK_DOCSEQ);
				byteResBuffer.get(byteINS_DOCSEQ);
				byteResBuffer.get(byteTXN_DATE  );
				byteResBuffer.get(byteTOT_FILE  );
				byteResBuffer.get(byteCUR_FILE  );
				byteResBuffer.get(byteDOC_NAME  );
				byteResBuffer.get(byteCUR_TOT   );
				byteResBuffer.get(byteCUR_NOW   );
				byteResBuffer.get(byteDOC_CASE  );
				byteResBuffer.get(byteBAK_EXT   );
				byteResBuffer.get(byteINS_EXT   );
				
				/*
				 * ���������� ����Ѵ�.
				 */
				if (flag) System.out.println(String.format("�������� RES [%d][%s]", intResLen, strRes));

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
			String strDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
			
			//////////////////////////////////////////////////////////////////
			/*
			 * ó���Ͻ�
			 */
			String strJobDateTime = strDateTime;
			
			/*
			 * ���� path
			 */
			String strJobPath = batchFolder.getStrSrcFolder().replace('\\', '/');
			
			/*
			 * ��ġ���ϸ�
			 */
			String strJobFileName = strFileName;
					
			/*
			 * ��ġ���� �Ӽ��Ͻ�
			 */
			File file = new File(strJobPath + "/" + strJobFileName);
			String strFileDateTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date(file.lastModified()));
			
			
			/*
			 * ������ �����Ǵ� ��ġ���ϸ�
			 */
			String strSendFileName = strFileName;
			
			/*
			 * ó�����.
			 */
			String strStat = "SUCCESS";
			
			//////////////////////////////////////////////////////////////////
			/*
			 * ��� ���ڿ��� �����.
			 */
			String strResultLine = String.format("%s;%s;%s;%s;%s;%05d;%05d;%s", strJobDateTime
					, strJobPath, strJobFileName, strFileDateTime, strSendFileName, this.intRecLength, this.intRecCount, strStat);
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
					 * ������ �õ��Ѵ�.
					 */
					doConnection();
				}
				
				if (flag) {
					/*
					 * ���������� ������.
					 */
					doOpen();
				}
				
				if (flag) {
					/*
					 * DATA ������ ������.
					 */
					doDataTransfer();
				}
				
				if (flag) {
					/*
					 * ���������� ������.
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
