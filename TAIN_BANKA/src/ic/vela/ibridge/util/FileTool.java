package ic.vela.ibridge.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTool {

	// ㉠ 파일 읽기 메소드 - readFile
	public boolean readFile(File file, String mode) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ㉠-1 임의 접근 파일 객체를 만든다. 모드는 읽기 모드
		RandomAccessFile rFile = new RandomAccessFile(file, mode);
		int consoleIn;

		// ㉠-2 파일을 읽어와 콘솔에 출력한다.
		// 파일의 마지막에 도달해 더 이상 읽을 값이 없으면
		// read() 메소드의 결과는 -1이 된다.
		while(true) {
			consoleIn = rFile.read();
			if(consoleIn == -1) break;
			System.out.print((char)consoleIn);
		}

		// ㉠-3 반드시 파일을 닫는 습관을 들이자.
		rFile.close();
		return true;
	}

	// ㉡ 콘솔 내용을 파일에 저장하는 메소드 - writeFile
	public boolean writeFile(File file, String mode) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ㉡-1 임의 접근 파일 객체를 만든다. 모드는 읽기/쓰기 모드
		RandomAccessFile wFile = new RandomAccessFile(file, mode);
		int consoleIn;

		// ㉡-2 콘솔로 부터 바이트를 읽어 파일에 저장한다.
		// 사용자가 Ctrl+S를 누르면 작업을 종료한다.
		while(true) {
			consoleIn = System.in.read();
			if(consoleIn == 19) break;
			wFile.write(consoleIn);
		}

		wFile.close();

		return true;
	}

	// ㉢ 파일을 복사하는 메소드 - copyFile
	public boolean copyFile(File src, File trg) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ㉢-1 임의 접근 파일 객체를 원본/목적지 파일로 각각 만든다.
		// 원본은 읽기 보드, 목적지 파일은 읽기/쓰기 모드이다.
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile trgFile = new RandomAccessFile(trg, "rw");
		int consoleIn;

		// ㉢-2 원본 파일로부터 바이트를 읽어와 목적지 파일에 저장한다.
		// 원본 파일의 끝에 도달하면 read() 메소드의 결과는 -1이 된다.
		while(true) {
			consoleIn = srcFile.read();
			if(consoleIn == -1) break;
			trgFile.write(consoleIn);
		}

		srcFile.close();
		trgFile.close();

		return true;
	}

	// ㉣ 파일을 이동하는 메소드 - moveFile
	public boolean moveFile(File src, File trg) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ㉣-1 임의 접근 파일 객체를 원본/목적지 파일로 각각 만든다.
		// 원본은 읽기 모드, 목적지 파일은 읽기/쓰기 모드이다.
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile trgFile = new RandomAccessFile(trg, "rw");
		int consoleIn;

		// ㉣-2 원본 파일로 부터 바이트를 읽어와 목적지 파일에 저장한다.
		// 원본 파일의 끝에 도달하면 read() 메소드의 결과는 -1이 된다.
		while(true) {
			consoleIn = srcFile.read();
			if(consoleIn == -1) break;
			trgFile.write(consoleIn);
		}

		// ㉣-3 파일을 반드시 닫아야 한다.
		srcFile.close();
		trgFile.close();

		// ㉣-4 원본파일을 삭제한다.(이동이므로)
		// 만약 위에서 파일을 닫지 않으면 삭제가 안된다.
		src.delete();
		return true;
	}

	// ㉤ 애플리케이션 실행을 위한 main() 메소드
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ㉤-1 FileTool 객체 생성
		FileTool ft = new FileTool();
		boolean result;

		// ㉤-2 명령행 인자에 따른 메소드 호출
		try {
			if(args[0].equals("r")) {
				result = ft.readFile(new File(args[1]), args[0]);
			}
			else if(args[0].equals("rw")) {
				result = ft.writeFile(new File(args[1]), args[0]);
			}
			else if(args[0].equals("c")) {
				result = ft.copyFile(new File(args[1]), new File(args[2]));
			}
			else if(args[0].equals("m")) {
				result = ft.moveFile(new File(args[1]), new File(args[2]));
			}
			else System.out.println("옵션을 정확히 입력하세요.(r, rw, c, m)");
		}
		// ㉤-3 각각의 예외에 따른 에러 메시지의 출력
		catch(ArrayIndexOutOfBoundsException ae) {
			System.out.println("매개변수의 형식이 틀립니다.");
		}
		catch(IllegalArgumentException iae) {
			System.out.println("옵션을 정확히 입력하세요.(r, rw, c, m)");
		}
		catch(FileNotFoundException fe) {
			System.out.println("파일을 찾을 수 없습니다.");
		}
		catch(IOException ie) {
			System.out.println("입출력 에러가 발생했습니다.");
		}
	}
}
