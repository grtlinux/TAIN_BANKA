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
public class BatchClientHWI51
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	private static final int           HEADERLENGTH        = 190;

	private static final String        INS_ID              = "L01";   // ��ȭ���� ��ġŬ���̾�Ʈ
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
	private void doOpen() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.07.07 : ��ī4������ ������� ����������
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
					 * ��û������ ����Ѵ�.
					 */
					if (flag) System.out.println(String.format("�������� REQ    [%d][%s]", intReqLen, strReq));
					
					/*
					 * ��û������ ������.
					 */
					send(byteReq, 0, intReqLen);
					
				}
				
				if (flag) {
					/*
					 * ���������� �޴´�.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * ���������� ����Ѵ�.
					 */
					if (flag) System.out.println(String.format("�������� RES    [%d][%s]", intResLen, strRes));
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
	 * ��ġ������ ����.
	 * �����͸� �����ϰ� ���������� �޴´�.
	 * ��ġ������ �ݴ´�. 
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
		 * ��ġ������ �����Ѵ�.
		 */
		String strFile = this.batchFolder.getStrSrcFolder() + "/" + this.strFileName;
		
		if (flag) {   // DATE.2013.07.08

			try {
				/*
				 * ��ġ�۽������� �����.
				 */
				BatchCommonHeader commonHeader = new BatchCommonHeader();
				
				/*
				 * ��ġ���� ������
				 */
				int intFileSize = 0;

				/*
				 * ��ġ���� ������
				 */
				byte[] byteFile = null;

				if (flag) {
					/*
					 * ��ġ������ ũ�⸦ ���Ѵ�.
					 */
					File file = new File(strFile);
					intFileSize = (int) file.length();
					
					/*
					 * ��ġ������ ũ���� byte �迭�� ��´�.
					 */
					byteFile = new byte[intFileSize];

					/*
					 * ��ġ������ open �Ѵ�.
					 */
					FileInputStream fileInputStream = new FileInputStream(file);
					
					/*
					 * ��ġ������ read �Ѵ�.
					 */
					int intReaded = fileInputStream.read(byteFile);
					if (intReaded != intFileSize) {
						/*
						 * ������ �� �� �о���.
						 */
						throw new IllegalStateException("ERROR : file read error....");
					}
					
					/*
					 * ��ġ������ close �Ѵ�.
					 */
					fileInputStream.close();
					
				}
				
				////////////////////////////////////////////////////////
				/*
				 * ��ġ�۽������� ���̸� ���Ѵ�.
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
					 * header�� �����.
					 */
					byte[] byteReq = commonHeader.get();
					int intReqLen = byteReq.length;
					String strReq = commonHeader.toString();
					
					/*
					 * ��û������ ����Ѵ�.
					 */
					if (flag) System.out.println(String.format("DATA HEADER REQ [%d][%s]", intReqLen, strReq));
					
					/*
					 * ��û������ ������.
					 */
					send(byteReq, 0, intReqLen);
				}
				
				if (flag) {
					/*
					 * ��ġ data�� �۽��Ѵ�.
					 */
					send(byteFile, 0, intFileSize);
				}
				
				if (flag) {
					System.out.println(String.format("ó���� ��ġ���� [%s]", strFile));
					System.out.println(String.format("INFO [initFileSize=%d] [strFileName=%s]", intFileSize, strFileName));
				}
				
				if (flag) {
					/*
					 * ���������� �޴´�.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * ���������� ����Ѵ�.
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
					 * ��û������ ����Ѵ�.
					 */
					if (flag) System.out.println(String.format("�������� REQ    [%d][%s]", intReqLen, strReq));
					
					/*
					 * ��û������ ������.
					 */
					send(byteReq, 0, intReqLen);
					
				}
				
				if (flag) {
					/*
					 * ���������� �޴´�.
					 */
					byte[] byteRes = recv(HEADERLENGTH);
					commonHeader.set(byteRes);
					int intResLen = byteRes.length;
					String strRes = commonHeader.toString();
					
					/*
					 * ���������� ����Ѵ�.
					 */
					if (flag) System.out.println(String.format("�������� RES    [%d][%s]", intResLen, strRes));
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
