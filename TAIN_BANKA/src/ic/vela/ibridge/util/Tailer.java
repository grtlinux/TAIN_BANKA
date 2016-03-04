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
 * 로그를 보는 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @author  강석
 * @version 1.0, 2005/07/18
 * @since   jdk1.6.0_45
 * 
 * 시스템이 처리하고 쌓아논 로그를 보는 프로그램..
 * 로그 파일을 임의로 조작하여 파일사이즈를 줄이거나 삭제한 경우에는
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
	 * 파일명을 찾고 존재하는지 확인한다.
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	private void searchFile(String[] args) throws Exception
	{
		boolean flag = true;
		
		if (flag) {

			if (args.length > 1) {
				// 파일명이 많으면 에러처리 -> 맨 앞만 받을지를 결정필요
				// log("ERROR : too many arguments.");
				throw new IllegalStateException("IB_ERROR : too many arguments.");
			}
			else if (args.length == 1) {
				// 파일명이 있으면 파일명 변경
				
				strFileName = args[0];
			}
			
			// 날짜문자가 있으면 replace 한다.
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			strFileName = strFileName.replace("YYYYMMDD", strDate);
			
			// 파일의 존재를 확인한다.
			if (!new File(strFileName).exists()) {
				// log("ERROR : file not found (" + strFileName + ")");
				throw new IllegalStateException("IB_ERROR : file not found (" + strFileName + ")");
			}

			if (flag) log(">>>>> 파일명 : [" + strFileName + "]");
		}
	}
	/*==================================================================*/
	/**
	 * 파일 tailing 처리를 한다.
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public void execute(String[] args)
	{
		boolean flag = true;
		
		if (!flag) {   // DATE.2013.06.20 : 미사용
			
			try {
				while (true) {
					
					// 파일명을 얻는다. 그리고, 파일존재를 확인한다.
					searchFile(args);
					
					// 파일을 open 한다.
					randFile = new RandomAccessFile(strFileName, "r");
					
					// 파일끝 포인터를 얻는다.
					long pos = randFile.length();
					
					// 읽을 파일포인터를 계산한다.
					pos -= 1024;
					if (pos < 0) pos = 0;
					
					// 읽을 파일포인터를 이동한다.
					randFile.seek(pos);
					
					// CR/LF 까지 읽는다.
					
					// 파일을 읽어 출력한다.
					while (true) {
						
						if (pos > randFile.length()) {
							// 파일사이즈가 감소되었다.
							// 파일을 클로우즈 하고 다시 읽는다.
							System.out.println("#################### 2초후 파일 Tailer 재실행 ##################");
							try {
								Thread.sleep(2000);  // 1초
							} catch (InterruptedException e) {}
							break;
						}
						else if (pos == randFile.length()) {
							// 파일포인터가 끝이면 잠시 쉬었다가 읽는다.
							
							try {
								Thread.sleep(1000);  // 1초
							} catch (InterruptedException e) {}
							
							continue;
						}
						
						// TODO : charset 설정 필요
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
					
					// 파일을 close 한다.
					randFile.close();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (flag) {    // DATE.2013.06.20 : 변경
			
			try {
				// 파일명을 얻는다. 그리고, 파일존재를 확인한다.
				searchFile(args);
				
				// 파일을 open 한다.
				FileReader fr = new FileReader(strFileName);
				lnr = new LineNumberReader(fr);
				
				if (flag) System.out.println(">" + fr.getEncoding());
				
				// 파일을 읽어 출력한다.
				while (true) {
					
					if (flag) {
						String strLine = lnr.readLine();
						if (strLine != null) {
							int iLine = lnr.getLineNumber();
							
							System.out.println(String.format("[%05d] %s", iLine, strLine));
						}
						else {
							try {
								Thread.sleep(1000);  // 1초
							} catch (InterruptedException e) {}
						}
					}
					
					if (!flag) return;
				}
				
				// 파일을 close 한다.
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
	 * Main Entry Point : 단위테스트를 위한 static 함수
	 * @param args 실행인자들
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
