package ic.vela.ibridge.test.fileio;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class FileIO {

	private static final String   FILE_PATH     = "D:/KANG/WORK/workspace/KangProj/src/ic/test/fileio/";
	
	private static final String[] FILES         = { "test.xml", "test.xsl", "out.test.xml" };
	private static final String   FILE          = FILE_PATH + FILES[0];

	/////////////////////////////////////////////////////////////////////////////////
	// process01
	private static void process01() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE));
			String sLine = null;
			while ((sLine=br.readLine()) != null) {
				byte[] bLine = sLine.getBytes();
				int iSize = bLine.length;
				String sSize = new DecimalFormat("0000").format(iSize);
				
				System.out.println(sSize + ">[" + sLine + "]");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// process02
	private static void process02() {
		try {
			//Properties prop = new Properties();
			Properties prop = System.getProperties();
			prop.list(System.out);
			
			System.out.println(">dev.author=" + System.getProperty("dev.author", "imsi를 실행"));
			System.out.println(">dev.version=" + System.getProperty("dev.version", "난 몰라"));
			System.out.println(">CLASSPATH=" + System.getenv("CLASSPATH"));
			System.out.println(">CP=" + System.getenv("CP"));
			
			System.out.println(">user.dir=" + prop.getProperty("user.dir"));
			
			String fileName = null;
			fileName = prop.getProperty("user.dir") + "/imsi.xml";
			prop.storeToXML(new FileOutputStream(fileName), "This is the comment by Kang Seok about property.(XML)");
			prop.loadFromXML(new FileInputStream(fileName));
			
			fileName = prop.getProperty("user.dir") + "/imsi.prop";
			prop.store(new FileOutputStream(fileName), "This is the comment by Kang Seok about property.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// process03
	private static void process03() {
		try {
			String simpleFormat = System.getProperty("sys.datetime.format", "yyyy/MM/dd HH:mm:ss.SSS");
			SimpleDateFormat sdf = new SimpleDateFormat(simpleFormat, Locale.KOREA);
			
			String strDate = sdf.format(new Date());
			System.out.println("strDate > [" + strDate + "]");
			
			int year = 1;
			int month = 1;
			int day = -25;
			
			Calendar cal = sdf.getCalendar();
			cal.add(Calendar.YEAR, year);
			cal.add(Calendar.MONTH, month);
			cal.add(Calendar.DAY_OF_MONTH, day);
			
			strDate = sdf.format(cal.getTime());
			System.out.println("strDate > [" + strDate + "]");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// process04
	private static void process04() {
		try {
			String strFileName = "D:/KANG/WORK/workBatch/dataclient/" + "imsi.txt";
			
			PrintStream ps = new PrintStream(strFileName);
			
			for (int i=0; i < 10; i++) {
				String strLine = "This is BufferedWriter...강석입니다.";
				
				ps.println(i + ")" + strLine);
			}
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// process05
	private static void process05() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			
			ps.printf("[%-20s][%010d]\n", "abcd", 12345);
			
			ps.close();
			
			System.out.println(">" + baos.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// main
	public static void main(String[] args) {
		switch (5) {
		case  1 : process01(); break;
		case  2 : process02(); break;
		case  3 : process03(); break;
		case  4 : process04(); break;
		case  5 : process05(); break;
		default : break;
		}
	}
}
