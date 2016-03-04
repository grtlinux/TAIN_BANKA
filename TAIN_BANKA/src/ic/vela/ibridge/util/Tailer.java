package ic.vela.ibridge.util;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*==================================================================*/
/**
 * �α׸� ���� Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @author  ����
 * @version 1.0, 2005/07/18
 * @since   jdk1.6.0_45
 * 
 * �ý����� ó���ϰ� �׾Ƴ� �α׸� ���� ���α׷�..
 * �α� ������ ���Ƿ� �����Ͽ� ���ϻ���� ���̰ų� ������ ��쿡��
 * 
 * 
 */
/*==================================================================*/
public class Tailer
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String PARAM_PATH = "system.ib.param.basefolder";
	private static final String PARAM_NAME = "system.ib.param.filename";
	
	private static final String FILE_PATH = "D:/KANG/WORK/server/fep/log/";
	private static final String FILE_NAME = "YYYYMMDD.log";
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private String strFileName = null;
	
	private RandomAccessFile randFile = null;
	private LineNumberReader lnr = null;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	public Tailer()
	{
		boolean flag = true;
		
		if (flag) {
			String strPath = System.getProperty(PARAM_PATH, FILE_PATH);
			String strName = System.getProperty(PARAM_NAME, FILE_NAME);
			
			strFileName = strPath + strName;
		}
	}
	
	/*==================================================================*/
	/**
	 * ���ϸ��� ã�� �����ϴ��� Ȯ���Ѵ�.
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	private void searchFile(String[] args) throws Exception
	{
		boolean flag = true;
		
		if (flag) {

			if (args.length > 1) {
				// ���ϸ��� ������ ����ó�� -> �� �ո� �������� �����ʿ�
				// log("ERROR : too many arguments.");
				throw new IllegalStateException("IB_ERROR : too many arguments.");
			}
			else if (args.length == 1) {
				// ���ϸ��� ������ ���ϸ� ����
				
				strFileName = args[0];
			}
			
			// ��¥���ڰ� ������ replace �Ѵ�.
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			strFileName = strFileName.replace("YYYYMMDD", strDate);
			
			// ������ ���縦 Ȯ���Ѵ�.
			if (!new File(strFileName).exists()) {
				// log("ERROR : file not found (" + strFileName + ")");
				throw new IllegalStateException("IB_ERROR : file not found (" + strFileName + ")");
			}

			if (flag) log(">>>>> ���ϸ� : [" + strFileName + "]");
		}
	}
	/*==================================================================*/
	/**
	 * ���� tailing ó���� �Ѵ�.
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public void execute(String[] args)
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.06.20 : �̻��
			
			try {
				while (true) {
					
					// ���ϸ��� ��´�. �׸���, �������縦 Ȯ���Ѵ�.
					searchFile(args);
					
					// ������ open �Ѵ�.
					randFile = new RandomAccessFile(strFileName, "r");
					
					// ���ϳ� �����͸� ��´�.
					long pos = randFile.length();
					
					// ���� ���������͸� ����Ѵ�.
					pos -= 1024;
					if (pos < 0) pos = 0;
					
					// ���� ���������͸� �̵��Ѵ�.
					randFile.seek(pos);
					
					// CR/LF ���� �д´�.
					
					// ������ �о� ����Ѵ�.
					while (true) {
						
						if (pos > randFile.length()) {
							// ���ϻ���� ���ҵǾ���.
							// ������ Ŭ�ο��� �ϰ� �ٽ� �д´�.
							System.out.println("#################### 2���� ���� Tailer ����� ##################");
							try {
								Thread.sleep(2000);  // 1��
							} catch (InterruptedException e) {}
							break;
						}
						else if (pos == randFile.length()) {
							// ���������Ͱ� ���̸� ��� �����ٰ� �д´�.
							
							try {
								Thread.sleep(1000);  // 1��
							} catch (InterruptedException e) {}
							
							continue;
						}
						
						// TODO : charset ���� �ʿ�
						if (!flag) {
							String line = randFile.readLine();
							pos = randFile.getFilePointer();
							
							byte[] byteLine = line.getBytes();
							System.out.println(">" + new String(byteLine, "UTF-8"));
						}

						if (flag) {
							byte[] line = randFile.readLine().getBytes();
							pos = randFile.getFilePointer();
							
							System.out.println(">" + new String(line, "EUC-KR"));
						}
						
						if (!flag) return;
					}
					
					// ������ close �Ѵ�.
					randFile.close();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // DATE.2013.06.20 : ����
			
			try {
				// ���ϸ��� ��´�. �׸���, �������縦 Ȯ���Ѵ�.
				searchFile(args);
				
				// ������ open �Ѵ�.
				FileReader fr = new FileReader(strFileName);
				lnr = new LineNumberReader(fr);
				
				if (flag) System.out.println(">" + fr.getEncoding());
				
				// ������ �о� ����Ѵ�.
				while (true) {
					
					if (flag) {
						String strLine = lnr.readLine();
						if (strLine != null) {
							int iLine = lnr.getLineNumber();
							
							System.out.println(String.format("[%05d] %s", iLine, strLine));
						}
						else {
							try {
								Thread.sleep(1000);  // 1��
							} catch (InterruptedException e) {}
						}
					}
					
					if (!flag) return;
				}
				
				// ������ close �Ѵ�.
			} catch (Exception e) {
				e.printStackTrace();

				try {
					lnr.close();
				} catch (Exception e1) {}
			}
		}
	}
	
	private void log(String msg)
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println(">" + msg);
		}
	}
	/*==================================================================*/
	/**
	 * Main Entry Point : �����׽�Ʈ�� ���� static �Լ�
	 * @param args �������ڵ�
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args)
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		switch (1) {
		case 1:
			if (flag) {
				new Tailer().execute(args);
			}
			break;
			
		default:
			if (flag) {
				System.out.println("Wrong switch number");
			}
			break;
		}
		
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
