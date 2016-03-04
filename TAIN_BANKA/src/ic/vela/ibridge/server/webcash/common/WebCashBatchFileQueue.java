package ic.vela.ibridge.server.webcash.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;


/*==================================================================*/
/**
 * WebCashBatchFileQueue Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    WebCashBatchFileQueue
 * @author  ����
 * @version 1.0, 2013/06/27
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class WebCashBatchFileQueue
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";

	private static final String        BASE_FOLDER         = "D:/KANG/WORK/workspace/IB_Vela.2/server/";
	private static final String        CFG_FILE            = "webcash.properties";

	private static final String        DEFAULT_FOLDER      = "D:/KANG/WORK/webCash/data3/YYYYMMDD/";
	private static final String        DEFAULT_SEQ         = "D:/KANG/WORK/webCash/ap_batch/DAT/YYYYMMDD/";
	private static final String        DEFAULT_DAT         = "D:/KANG/WORK/webCash/ap_batch/SEQ/YYYYMMDD/";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private WebCashBatchFile           infoBatchFile       = null;
	
	private String                     strFepId            = null;  // FEPID
	private String                     strServiceName      = null;  // OFR+FEPID+01

	private String                     strBatchFileFolder  = null;  // ��ġ���� ��������
	private String                     strBatchSeqFolder   = null;  // FQ seq ����
	private String                     strBatchDatFolder   = null;  // FQ dat ����

	private String                     strDatFileName      = null;
	private String                     strSeqFileName      = null;

	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @param infoBatchFile
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public WebCashBatchFileQueue(WebCashBatchFile infoBatchFile) throws Exception
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
				 * ���� ������ ��´�.
				 */
				this.strBatchFileFolder = prop.getProperty("system.ib.param.batchfile.folder", DEFAULT_FOLDER);
				this.strBatchSeqFolder  = prop.getProperty("system.ib.param.filequeue.folder.seq", DEFAULT_SEQ);
				this.strBatchDatFolder  = prop.getProperty("system.ib.param.filequeue.folder.dat", DEFAULT_DAT);

				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				this.strBatchFileFolder = this.strBatchFileFolder.replace("YYYYMMDD", strDate);
				this.strBatchSeqFolder  = this.strBatchSeqFolder.replace("YYYYMMDD", strDate);
				this.strBatchDatFolder  = this.strBatchDatFolder.replace("YYYYMMDD", strDate);
			}
			
			
			if (flag) {
				/*
				 * file queue �� ���� ������ ��´�.
				 */
				this.infoBatchFile = infoBatchFile;
				
				this.strFepId = infoBatchFile.getStrFepId();
				this.strServiceName = "OFR" + strFepId + "01";
				this.strDatFileName = this.strBatchDatFolder + this.strServiceName;
				this.strSeqFileName = this.strBatchSeqFolder + this.strServiceName;
			}
			
			if (flag) {
				System.out.println("[strFepId           =" + this.strFepId + "]");
				System.out.println("[strServiceName     =" + this.strServiceName + "]");
				System.out.println("[strBatchFileFolder =" + this.strBatchFileFolder + "]");
				System.out.println("[strBatchSeqFolder  =" + this.strBatchSeqFolder + "]");
				System.out.println("[strBatchDatFolder  =" + this.strBatchDatFolder + "]");
				System.out.println("[strDatFileName     =" + this.strDatFileName + "]");
				System.out.println("[strSeqFileName     =" + this.strSeqFileName + "]");
			}
		}
	}
	/*==================================================================*/
	/**
	 * get the batch information to write
	 * 
	 * @param iRecLen
	 * @param iRecCount
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private String getBatchInfo(String strTrCodeSeq, int iRecLen, int iRecCount) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strBatchInfo = null;
		
		if (flag) {
			String strRecLen    = String.format("%04d", iRecLen);
			String strRecCnt    = String.format("%07d", iRecCount);
			String strSR        = "R";
			String strOrgCode   = strFepId.substring(0, 3);
			String strTrCode    = infoBatchFile.getStrTrCode();
			String strTrCodeLen = String.format("%02d", strTrCode.getBytes().length);
			String strDate      = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			String strTime      = new SimpleDateFormat("HHmmssSSS", Locale.KOREA).format(new Date());
			String strFiller    = "";
			
			strBatchInfo = String.format("%4.4s%7.7s%1.1s%3.3s%11.11s%2.2s%8.8s%9.9s%2.2s%53.53s"
					, strRecLen
					, strRecCnt
					, strSR
					, strOrgCode
					, strTrCode
					, strTrCodeLen
					, strDate
					, strTime
					, strTrCodeSeq
					, strFiller
					);

			if (!flag) {
				System.out.println("strRecLen    = [" + strRecLen    + "]");
				System.out.println("strRecCnt    = [" + strRecCnt    + "]");
				System.out.println("strSR        = [" + strSR        + "]");
				System.out.println("strOrgCode   = [" + strOrgCode   + "]");
				System.out.println("strTrCode    = [" + strTrCode    + "]");
				System.out.println("strTrCodeLen = [" + strTrCodeLen + "]");
				System.out.println("strDate      = [" + strDate      + "]");
				System.out.println("strTime      = [" + strTime      + "]");
				System.out.println("strTrCodeSeq = [" + strTrCodeSeq + "]");
				System.out.println("strFiller    = [" + strFiller    + "]");
				System.out.println("----------------------------------------------");
				System.out.println("strBatchInfo = [" + strBatchInfo + "]");
			}
		}
		
		return strBatchInfo;
	}

	/*==================================================================*/
	/**
	 * write the batch information into the data file
	 * 
	 * @param strBatchInfo
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void writeBatchInfoToDat(String strBatchInfo) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * write the batch information to the file by append mode
			 */
			
			RandomAccessFile raf = new RandomAccessFile(this.strDatFileName, "rw");
			
			/*
			 * ������ Permission�� �ٲ۴�.
			 */
			new File(this.strDatFileName).setWritable(true, false);
			
			raf.seek(raf.length());
			
			// add the new line character to the batch info data
			strBatchInfo += "\n";
			
			if (flag) {

				switch (1) {
				case 1:
					// write String -> TODO : Unicode �� byte �ڵ��� ���̷� ���� ���� ���� �߻� Ȯ��
					raf.writeBytes(strBatchInfo);
					break;
				case 2:
					// write a string to the file using modified UTF-8
					raf.writeUTF(strBatchInfo);
					break;
				default:
					break;
				}
				
			} else {
				switch (1) {
				case 1:
					// write byte array  UTF-8
					raf.write(strBatchInfo.getBytes("UTF-8"));
					break;
				default:
					// write byte array
					raf.write(strBatchInfo.getBytes());
					break;
				}
			}

			raf.close();
		}
	}
	
	
	/*==================================================================*/
	/**
	 * write the information of the write seq increased
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void increaseWriteSeqInfo() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * file not found exception
			 */
			
			if (!new File(strSeqFileName).exists()) {
				/*
				 * ������ �����Ѵ�.
				 */
				RandomAccessFile raf = new RandomAccessFile(strSeqFileName, "rw");
				
				raf.seek(0);
				raf.write("00000000".getBytes());
				raf.write("00000000".getBytes());
				raf.writeLong(0);
				raf.writeBytes("\n");

				raf.close();
			}
		}
		
		if (flag) {
			/*
			 * write the seq information to the the seq file, increasing the writeseq
			 */
			
			byte[] byteWriteSeq = new byte[8];
			byte[] byteReadSeq = new byte[8];
			long lPosition = 0;

			RandomAccessFile raf = new RandomAccessFile(strSeqFileName, "rw");

			// read the seq information from the seq file
			raf.seek(0);
			raf.readFully(byteWriteSeq);
			raf.readFully(byteReadSeq);
			lPosition = raf.readLong();
			
			if (!flag) {
				// display the seq information before the processing
				raf.seek(0);
				raf.readFully(byteWriteSeq);
				raf.readFully(byteReadSeq);
				lPosition = raf.readLong();

				System.out.println("Before WriteSeq  = " + new String(byteWriteSeq));
				System.out.println("Before ReadSeq   = " + new String(byteReadSeq));
				System.out.println("Before lPosition = " + lPosition);
			}
			
			// increase the writeseq item
			byteWriteSeq = String.format("%08d", Integer.parseInt(new String(byteWriteSeq)) + 1).getBytes();
			
			// write the seq informat to the seq file
			raf.seek(0);
			raf.write(byteWriteSeq);
			
			if (!flag) {
				// display the seq information after the processing
				raf.seek(0);
				raf.readFully(byteWriteSeq);
				raf.readFully(byteReadSeq);
				lPosition = raf.readLong();

				System.out.println("After WriteSeq  = " + new String(byteWriteSeq));
				System.out.println("After ReadSeq   = " + new String(byteReadSeq));
				System.out.println("After lPosition = " + lPosition);
			}

			raf.close();
		}
	}
	
	/*==================================================================*/
	/**
	 * write the batch info to the dat file
	 * 
	 * @param strBatchInfo
	 * @param iRecLen
	 * @param iRecCnt
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private void writeBatchInfoToDatSeq(String strBatchInfo, int iRecLen, int iRecCnt) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		RandomAccessFile rafSeq = null;
		RandomAccessFile rafDat = null;
		
		int iWriteSeq = 0;
		
		byte[] byteWriteSeq = new byte[8];
		byte[] byteReadSeq  = new byte[8];
		long lPosition = 0;
		
		if (flag) {
			/*
			 * file not found exception
			 */
			
			if (!new File(strSeqFileName).exists()) {
				
				rafSeq = new RandomAccessFile(strSeqFileName, "rw");
				
				if (flag) {
					// �����ڷḦ �����Ѵ�.
					byteWriteSeq = "00000000".getBytes();
					byteReadSeq = "00000000".getBytes();
					lPosition = 0;
					
					rafSeq.seek(0);
					rafSeq.write(byteWriteSeq);
					rafSeq.write(byteReadSeq);
					rafSeq.writeLong(lPosition);
					rafSeq.writeBytes("\n");
				}
			}
			else {
				rafSeq = new RandomAccessFile(strSeqFileName, "rw");
				
				if (flag) {
					// display the seq information before the processing
					rafSeq.seek(0);
					rafSeq.readFully(byteWriteSeq);
					rafSeq.readFully(byteReadSeq);
					lPosition = rafSeq.readLong();

					if (!flag) {
						System.out.println("Before WriteSeq  = " + new String(byteWriteSeq));
						System.out.println("Before ReadSeq   = " + new String(byteReadSeq));
						System.out.println("Before lPosition = " + lPosition);
					}
				}
			}
		}
		
		if (flag) {
			/*
			 * write seq ���� 1 ������Ų��.
			 */
			
			iWriteSeq = Integer.parseInt(new String(byteWriteSeq)) + 1;
		}
		
		if (flag) {
			/*
			 * write the batch information to the file by append mode
			 */
			
			rafDat = new RandomAccessFile(strDatFileName, "rw");
			
			// position to write
			rafDat.seek(rafDat.length());
			
			if (flag) {
				byte[] byteSequence = new byte[8];
				byte[] byteReceiver = new byte[10];
				byte[] byteDataLen  = new byte[5];
				
				byteSequence = String.format("%08d", iWriteSeq).getBytes();
				byteReceiver = String.format("%10s", "").getBytes();
				// byteDataLen  = String.format("%05d", iRecLen).getBytes();
				byteDataLen  = String.format("%05d", 101).getBytes();
				
				rafDat.write(byteSequence);
				rafDat.write(byteReceiver);
				rafDat.write(byteDataLen);
			}
			
			// add the new line character to the batch info data
			strBatchInfo += "\n";
			
			if (flag) {

				switch (1) {
				case 1:
					// write String -> TODO : Unicode �� byte �ڵ��� ���̷� ���� ���� ���� �߻� Ȯ��
					rafDat.writeBytes(strBatchInfo);
					break;
				case 2:
					// write a string to the file using modified UTF-8
					rafDat.writeUTF(strBatchInfo);
					break;
				default:
					break;
				}
				
			} else {
				switch (1) {
				case 1:
					// write byte array  UTF-8
					rafDat.write(strBatchInfo.getBytes("UTF-8"));
					break;
				default:
					// write byte array
					rafDat.write(strBatchInfo.getBytes());
					break;
				}
			}
		}

		if (flag) {
			/*
			 * write the seq information to the the seq file, increasing the writeseq
			 */
			
			// increase the writeseq item
			byteWriteSeq = String.format("%08d", iWriteSeq).getBytes();

			// write the seq informat to the seq file
			rafSeq.seek(0);
			rafSeq.write(byteWriteSeq);
		}
		
		rafSeq.close();
		rafDat.close();
		
		if (flag) {
			/*
			 * ������ Permission�� �ٲ۴�.
			 */
			if (new File(this.strSeqFileName).setWritable(true, false)) {
				/*
				 * ���� permission �� ����Ǿ���.
				 */
				System.out.println("SUCCESS : change the permission...[SEQ:" + this.strSeqFileName + "]");
			} else {
				/*
				 * ���� permission �� ������� �ʾҴ�.
				 */
				System.out.println("FAIL : NOT change the permission...[SEQ:" + this.strSeqFileName + "]");
			}
		}
		
		if (flag) {
			/*
			 * ������ Permission�� �ٲ۴�.
			 */
			if (new File(this.strDatFileName).setWritable(true, false)) {
				/*
				 * ���� permission �� ����Ǿ���.
				 */
				System.out.println("SUCCESS : change the permission...[DAT:" + this.strDatFileName + "]");
			} else {
				/*
				 * ���� permission �� ������� �ʾҴ�.
				 */
				System.out.println("FAIL : NOT change the permission...[DAT:" + this.strDatFileName + "]");
			}
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/**
	 * write the information of the batchfile into the file queue
	 * 
	 * @param iRecLen
	 * @param iRecCount
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public synchronized void writeFileQueueInfo(String strTrCodeSeq, int iRecLen, int iRecCount) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {  // DATE.2013.06.18
			/*
			 *  batch info �� �ش��ϴ� �ڷḦ �����.
			 */
			String strBatchInfo = getBatchInfo(strTrCodeSeq, iRecLen, iRecCount);
			if (!flag) System.out.println("BATCH_INFO=[" + strBatchInfo + "]");
			
			/*
			 *  batch info �� FQ dat �� ����Ѵ�.
			 */
			writeBatchInfoToDat(strBatchInfo);
			
			/*
			 *  FQ dat �� ����� �Ǽ��� FQ seq �� writeseq �� ����Ѵ�.
			 * ���α׷� ������ ���� writeBatchInfoToDat �� ������.
			 */
			increaseWriteSeqInfo();
		}
		
		if (flag) {   // DATE.2013.06.19
			/*
			 *  batch info �� �ش��ϴ� �ڷḦ �����.
			 */
			String strBatchInfo = getBatchInfo(strTrCodeSeq, iRecLen, iRecCount);
			if (!flag) System.out.println("BATCH_INFO=[" + strBatchInfo + "]");
			
			/*
			 *  batch info �� FQ dat, seq �� ����Ѵ�.
			 */
			writeBatchInfoToDatSeq(strBatchInfo, iRecLen, iRecCount);
		}
	}


	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * 
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
