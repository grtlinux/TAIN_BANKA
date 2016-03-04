package ic.vela.ibridge.common.filequeue;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/*==================================================================*/
/**
 * FileQueue 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    FileQueue
 * @author  강석
 * @version 1.0, 2013/06/16
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class FileQueue 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String  FQ_DAT_FOLDER = "D:/KANG/WORK/webCash/ap_batch/DAT/YYYYMMDD/";
	private static final String  FQ_SEQ_FOLDER = "D:/KANG/WORK/webCash/ap_batch/SEQ/YYYYMMDD/";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String strFepId       = null;
	private String strDatFileName = null;
	private String strSeqFileName = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 * @param strFepId
	 */
	/*------------------------------------------------------------------*/
	public FileQueue(String strFepId)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				this.strFepId = strFepId;
				
				String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
				
				// data file
				this.strDatFileName = System.getProperty("system.ib.param.fq.dat.folder", FQ_DAT_FOLDER);
				this.strDatFileName += "OFR" + strFepId + "01";
				this.strDatFileName = this.strDatFileName.replace("YYYYMMDD", strDate);
				
				// status seq file
				this.strSeqFileName = System.getProperty("system.ib.param.fq.seq.folder", FQ_SEQ_FOLDER);
				this.strSeqFileName += "OFR" + strFepId + "01";
				this.strSeqFileName = this.strSeqFileName.replace("YYYYMMDD", strDate);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * to make the batch information from the parameters
	 * @param iRecLen
	 * @param iRecCnt
	 * @param strTrCode
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private String getBatchInfo(int iRecLen, int iRecCnt, String strTrCode) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		String strBatchInfo = null;
		
		if (flag) {
			String strRecLen    = String.format("%04d", iRecLen);
			String strRecCnt    = String.format("%07d", iRecCnt);
			String strSR        = "R";
			String strOrgCode   = strFepId.substring(0, 3);
			String strTrCodeLen = String.format("%02d", strTrCode.getBytes().length);
			String strDate      = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			String strTime      = new SimpleDateFormat("HHmmssSSS", Locale.KOREA).format(new Date());
			String strTrCodeSeq = "01";
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
	 * write the batch info to the dat file
	 * @param strBatchInfo
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
			
			RandomAccessFile raf = new RandomAccessFile(strDatFileName, "rw");
			
			raf.seek(raf.length());
			
			// add the new line character to the batch info data
			strBatchInfo += "\n";
			
			if (flag) {

				switch (1) {
				case 1:
					// write String -> TODO : Unicode 와 byte 코드의 차이로 인한 길이 문제 발생 확인
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
	 */
	/*------------------------------------------------------------------*/
	private void increaseWriteSeqInfo() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
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
	 * FQ 에 batch info 를 기록한다.
	 * @param iRecLen
	 * @param iRecCnt
	 * @param strTrCode
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public synchronized void writeFileQueueInfo(int iRecLen, int iRecCnt, String strTrCode) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			// batch info 에 해당하는 자료를 만든다.
			String strBatchInfo = getBatchInfo(iRecLen, iRecCnt, strTrCode);
			if (!flag) System.out.println("BATCH_INFO=[" + strBatchInfo + "]");
			
			// batch info 를 FQ dat 에 기록한다.
			writeBatchInfoToDat(strBatchInfo);
			
			// FQ dat 에 기록한 건수를 FQ seq 의 writeseq 에 기록한다.
			increaseWriteSeqInfo();
		}
	}
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 * test01 static method
	 */
	/*------------------------------------------------------------------*/
	private void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (!flag) {    // DATE.2013.06.17
			try {
				/*
				 *  reading the data file
				 */
				
				LineNumberReader lnr = new LineNumberReader(new FileReader(strDatFileName), 4096);
				String strLine = null;
				
				while ((strLine = lnr.readLine()) != null) {
					int iLength = strLine.getBytes().length;
					System.out.println("[" + iLength + "] " + strLine);
				}
				
				lnr.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!flag) {    // DATE.2013.06.17
			try {
				/*
				 *  reading the seq file
				 */
				
				RandomAccessFile raf = new RandomAccessFile(strSeqFileName, "rw");
				byte[] byteWriteSeq = new byte[8];
				byte[] byteReadSeq = new byte[8];
				long lPosition = 0;
				
				if (flag) {
					// read seq
					raf.seek(0);
					
					raf.readFully(byteWriteSeq);
					raf.readFully(byteReadSeq);
					lPosition = raf.readLong();
					
					System.out.println("WriteSeq  = " + new String(byteWriteSeq));
					System.out.println("ReadSeq   = " + new String(byteReadSeq));
					System.out.println("lPosition = " + lPosition);
				}
				
				if (flag) {
					// write
					raf.seek(0);
					
					raf.write(byteWriteSeq);
					raf.write(byteReadSeq);
					raf.writeLong(lPosition + 10);
				}

				if (flag) {
					// read seq
					raf.seek(0);
					
					raf.readFully(byteWriteSeq);
					raf.readFully(byteReadSeq);
					lPosition = raf.readLong();

					System.out.println("WriteSeq  = " + new String(byteWriteSeq));
					System.out.println("ReadSeq   = " + new String(byteReadSeq));
					System.out.println("lPosition = " + lPosition);
				}
				
				if (flag) {
					// write
					raf.seek(0);
					
					raf.write("00000060".getBytes());
					raf.write("00000000".getBytes());
					raf.writeLong(0);
				}

				raf.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {    // DATE.2013.06.17
			try {
				// write the batch information to the file queue system.
				writeFileQueueInfo(377, 124, "TRCODE12345");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
				if (!flag) {
					new FileQueue("HDC51").test01();
					System.out.println("--------------------------HDC51---------------------------------");
					new FileQueue("HDC52").test01();
					System.out.println("--------------------------HDC52---------------------------------");
					new FileQueue("NHC51").test01();
					System.out.println("--------------------------NHC51---------------------------------");
					new FileQueue("NHC52").test01();
					System.out.println("--------------------------NHC52---------------------------------");
					new FileQueue("BCC51").test01();
					System.out.println("--------------------------BCC51---------------------------------");
					new FileQueue("BCC52").test01();
					System.out.println("--------------------------BCC52---------------------------------");
					new FileQueue("KBC51").test01();
					System.out.println("--------------------------KBC51---------------------------------");
					new FileQueue("KBC52").test01();
					System.out.println("--------------------------KBC52---------------------------------");
					new FileQueue("SSC51").test01();
					System.out.println("--------------------------SSC51---------------------------------");
					new FileQueue("SSC52").test01();
					System.out.println("--------------------------SSC52---------------------------------");
				}

				if (flag) {
					new FileQueue("HDC51").test01();
					System.out.println("--------------------------HDC51---------------------------------");
				}
			}
			break;
		default:
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
