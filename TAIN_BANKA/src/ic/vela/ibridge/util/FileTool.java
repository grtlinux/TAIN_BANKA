package ic.vela.ibridge.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTool {

	// �� ���� �б� �޼ҵ� - readFile
	public boolean readFile(File file, String mode) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ��-1 ���� ���� ���� ��ü�� �����. ���� �б� ���
		RandomAccessFile rFile = new RandomAccessFile(file, mode);
		int consoleIn;

		// ��-2 ������ �о�� �ֿܼ� ����Ѵ�.
		// ������ �������� ������ �� �̻� ���� ���� ������
		// read() �޼ҵ��� ����� -1�� �ȴ�.
		while(true) {
			consoleIn = rFile.read();
			if(consoleIn == -1) break;
			System.out.print((char)consoleIn);
		}

		// ��-3 �ݵ�� ������ �ݴ� ������ ������.
		rFile.close();
		return true;
	}

	// �� �ܼ� ������ ���Ͽ� �����ϴ� �޼ҵ� - writeFile
	public boolean writeFile(File file, String mode) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ��-1 ���� ���� ���� ��ü�� �����. ���� �б�/���� ���
		RandomAccessFile wFile = new RandomAccessFile(file, mode);
		int consoleIn;

		// ��-2 �ַܼ� ���� ����Ʈ�� �о� ���Ͽ� �����Ѵ�.
		// ����ڰ� Ctrl+S�� ������ �۾��� �����Ѵ�.
		while(true) {
			consoleIn = System.in.read();
			if(consoleIn == 19) break;
			wFile.write(consoleIn);
		}

		wFile.close();

		return true;
	}

	// �� ������ �����ϴ� �޼ҵ� - copyFile
	public boolean copyFile(File src, File trg) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ��-1 ���� ���� ���� ��ü�� ����/������ ���Ϸ� ���� �����.
		// ������ �б� ����, ������ ������ �б�/���� ����̴�.
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile trgFile = new RandomAccessFile(trg, "rw");
		int consoleIn;

		// ��-2 ���� ���Ϸκ��� ����Ʈ�� �о�� ������ ���Ͽ� �����Ѵ�.
		// ���� ������ ���� �����ϸ� read() �޼ҵ��� ����� -1�� �ȴ�.
		while(true) {
			consoleIn = srcFile.read();
			if(consoleIn == -1) break;
			trgFile.write(consoleIn);
		}

		srcFile.close();
		trgFile.close();

		return true;
	}

	// �� ������ �̵��ϴ� �޼ҵ� - moveFile
	public boolean moveFile(File src, File trg) throws FileNotFoundException, IllegalArgumentException, IOException
	{
		// ��-1 ���� ���� ���� ��ü�� ����/������ ���Ϸ� ���� �����.
		// ������ �б� ���, ������ ������ �б�/���� ����̴�.
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile trgFile = new RandomAccessFile(trg, "rw");
		int consoleIn;

		// ��-2 ���� ���Ϸ� ���� ����Ʈ�� �о�� ������ ���Ͽ� �����Ѵ�.
		// ���� ������ ���� �����ϸ� read() �޼ҵ��� ����� -1�� �ȴ�.
		while(true) {
			consoleIn = srcFile.read();
			if(consoleIn == -1) break;
			trgFile.write(consoleIn);
		}

		// ��-3 ������ �ݵ�� �ݾƾ� �Ѵ�.
		srcFile.close();
		trgFile.close();

		// ��-4 ���������� �����Ѵ�.(�̵��̹Ƿ�)
		// ���� ������ ������ ���� ������ ������ �ȵȴ�.
		src.delete();
		return true;
	}

	// �� ���ø����̼� ������ ���� main() �޼ҵ�
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ��-1 FileTool ��ü ����
		FileTool ft = new FileTool();
		boolean result;

		// ��-2 ����� ���ڿ� ���� �޼ҵ� ȣ��
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
			else System.out.println("�ɼ��� ��Ȯ�� �Է��ϼ���.(r, rw, c, m)");
		}
		// ��-3 ������ ���ܿ� ���� ���� �޽����� ���
		catch(ArrayIndexOutOfBoundsException ae) {
			System.out.println("�Ű������� ������ Ʋ���ϴ�.");
		}
		catch(IllegalArgumentException iae) {
			System.out.println("�ɼ��� ��Ȯ�� �Է��ϼ���.(r, rw, c, m)");
		}
		catch(FileNotFoundException fe) {
			System.out.println("������ ã�� �� �����ϴ�.");
		}
		catch(IOException ie) {
			System.out.println("����� ������ �߻��߽��ϴ�.");
		}
	}
}
