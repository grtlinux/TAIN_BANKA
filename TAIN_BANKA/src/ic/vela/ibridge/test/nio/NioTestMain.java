package ic.vela.ibridge.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class NioTestMain {
	private static final String   NIOPATH       = "D:/KANG/WORK/workXML/";
	private static final String   NIOFILE       = "FileTransTest.txt";

	/////////////////////////////////////////////////////////////////////////////////
	// test01 : NativeByteOrder
	private static void test01() {
		System.out.println("현재 시스템의 바이트 오더 " + ByteOrder.nativeOrder());
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test02 : NIOFileMapping
	@SuppressWarnings("resource")
	private static void test02() {
		try {
			// 파일 채널을 만든다.
			FileChannel fc = (new RandomAccessFile(NIOPATH + NIOFILE, "rw")).getChannel();
			
			// 메모리에 매핑
			MappedByteBuffer fileMap = fc.map(FileChannel.MapMode.READ_WRITE, 0, 50);
			
			// 바이트 버퍼에 데이터를 쓴다.
			if (!fileMap.isReadOnly())
				fileMap.put((new String("NIO는 편리하고 재미있습니다.")).getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test03 : NIOFileToConsole
	@SuppressWarnings("resource")
	private static void test03() {
		try {
			// 파일 채널
			FileChannel fc = (new FileInputStream(NIOPATH + "NIOFileToConsole.java")).getChannel();

			// 표준 출력 채널
			WritableByteChannel wbc = Channels.newChannel(System.out);
			
			// 파일의 크기를 알아낸다
			ByteBuffer fileData = ByteBuffer.allocate(100);
			int readCnt = 0;
			while (true) {
				readCnt = fc.read(fileData);
				if (readCnt <= 0)
					break;
				
				// 읽어온 데이터를 출력한다.
				// 버퍼의 데이터를 읽을 준비를 한다.
				System.out.println("0 >" + fileData);
				fileData.flip();      System.out.println("1 >" + fileData);
				wbc.write(fileData);  System.out.println("2 >" + fileData);
				fileData.flip();      System.out.println("3 >" + fileData);
				fileData.clear();     System.out.println("4 >" + fileData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test04 : NIOFileTransfer
	@SuppressWarnings("resource")
	private static void test04() {
		try {
			// 파일 읽기, 쓰기 채널을 가져온다
			FileChannel readFc = new FileInputStream(NIOPATH + "NIOFileToConsole.java").getChannel();
			FileChannel writeFc = new FileOutputStream(NIOPATH + "FileTransTest.txt").getChannel();
			
			// readFc가 열고 잇는 파일의 데이터를 writeFc가 열고 있는 파일로 전송
			writeFc.transferFrom(readFc, 0, 200);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test05 : NIOFileTransferConsole
	@SuppressWarnings("resource")
	private static void test05() {
		try {
			// 파일 채널
			FileChannel fc = new FileInputStream(NIOPATH + "NIOFileToConsole.java").getChannel();
			
			// 표준 출력 채널
			WritableByteChannel wbc = Channels.newChannel(System.out);
			
			// 전송
			fc.transferTo(0, fc.size(), wbc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test06 : NIOJavaWhois
	private static void test06() {
		try {
			// 소켓 채널을 가져온다.
			SocketChannel whoisSock = SocketChannel.open();
			
			InetSocketAddress sockAddr = new InetSocketAddress("whois.internic.net", 43);
			
			whoisSock.connect(sockAddr);
			
			// 알고자 하는 호스트를 전달
			Charset iso = Charset.forName("ISO-8859-1");
			whoisSock.write(iso.encode("yahoo.co.kr \r\n"));  // TODO : 2013.05.24
			
			// 읽어온 정보를 시스템에 출력
			ByteBuffer addrInfo = ByteBuffer.allocate(1024);
			
			while (true) {
				addrInfo.clear();
				
				if (whoisSock.read(addrInfo) <= 0)
					break;
				
				addrInfo.flip();
				
				System.out.print(iso.decode(addrInfo));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test07 : NIO
	private final static int REC_SZ = 4054;
	private static void test07() {
		try {
			FileInputStream fis = new FileInputStream("D:\\TEMP\\PRM1.LOGDATA.P110.DAY");
			FileChannel fc = fis.getChannel();
			
			ByteBuffer buf = ByteBuffer.allocate(REC_SZ);
			
			byte[] fld1 = new byte[   2];
			byte[] fld2 = new byte[   5];
			byte[] fld3 = new byte[  10];
			byte[] fld4 = new byte[  90];
			byte[] fld5 = new byte[ 550];
			byte[] fld6 = new byte[  40];
			byte[] fld7 = new byte[  70];
			byte[] fld8 = new byte[ 200];
			byte[] fld9 = new byte[ 600];
			byte[] fldA = new byte[ 750];

			for (int i=0; i < 1000; i++) {
				buf.clear();
				if (fc.read(buf) < 0)
					break;
				System.out.println("[" + new String(buf.array()) + "]");
				buf.flip();
				
				buf.get(fld1);
				buf.get(fld2);
				buf.get(fld3);
				buf.get(fld4);
				buf.get(fld5);
				buf.get(fld6);
				buf.get(fld7);
				buf.get(fld8);
				buf.get(fld9);
				buf.get(fldA);
				
				System.out.println("  < " + i + " >");
				System.out.println("-[" + new String(fld1) + "]");
			}
			
			fc.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test08 : ByteBuffer
	private static void test08() {
		try {
			if (true) {
				byte[] byteArray = "01234567890ABCDEFGHI".getBytes();
				
				ByteBuffer buf = ByteBuffer.allocate(100);
				buf.clear();
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				
				buf.put(byteArray);
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				buf.flip();
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				System.out.println("[" + new String(buf.array()) + "]");

				byte[] dest = new byte[4];
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				buf.rewind();
				buf.position(5);
				buf.get(dest);
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				System.out.println("[" + new String(dest) + "]");
				
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				buf.rewind();
				buf.position(10);
				buf.put("------".getBytes());
				System.out.println("[P:L] [" + buf.position() + ":" + buf.limit() + "]");
				System.out.println("[" + new String(buf.array()) + "]");
				
				System.out.println("[" + buf.position() + ":" + buf.limit() + "]");
				
			}
			System.out.println("--------------------------------------------------------");
			if (true) {
				ByteBuffer byteBuffer = ByteBuffer.wrap("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
				byteBuffer.rewind();
				System.out.println("[LEN:" + byteBuffer.limit() + "] [" + new String(byteBuffer.array()) + "]");

				byteBuffer.position(15);
				byteBuffer.put("0610".getBytes());
				
				byteBuffer.position(42);
				byteBuffer.put("001".getBytes());
				
				System.out.println("[LEN:" + byteBuffer.limit() + "] [" + new String(byteBuffer.array()) + "]");
				System.out.printf("RECV<- [LEN:%04d] [%s]\n", byteBuffer.limit(), new String(byteBuffer.array()));
				System.out.printf("RECV<- [LEN:%04d] [%s]\n", byteBuffer.limit(), byteBuffer.array());
			}
			System.out.println("--------------------------------------------------------");
			if (true){
				ByteBuffer byteBuffer = ByteBuffer.allocate(10);
				byteBuffer.clear();
				byteBuffer.position(5);
				byteBuffer.put("ABCDE".getBytes());
				System.out.println("[LEN:" + byteBuffer.limit() + "] [" + new String(byteBuffer.array(), "EUC-KR") + "]");
				
				byteBuffer.rewind();
				byteBuffer.position(0);
				byteBuffer.put("12345".getBytes());
				System.out.println("[LEN:" + byteBuffer.limit() + "] [" + new String(byteBuffer.array(), "EUC-KR") + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test09 : ByteBuffer 2
	private static void test09() {
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(10);
			byteBuffer.clear();
			
			byteBuffer.rewind();
			byteBuffer.put("A".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("B".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("C".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("E".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("F".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("G".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));

			byteBuffer.clear();
			byteBuffer.rewind();
			byteBuffer.put("1".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("2".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("3".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("4".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("5".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.put("6".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));

			byteBuffer.position(8);
			byteBuffer.put("#".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
			byteBuffer.position(2);
			byteBuffer.put("#".getBytes()); System.out.printf("[Position:%04d][Limit:%04d] [%s]\n", byteBuffer.position(), byteBuffer.limit(), new String(byteBuffer.array()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// main
	public static void main(String[] args) {
		switch (4) {
		case  1: test01(); break; // NativeByteOrder
		case  2: test02(); break; // NIOFileMapping
		case  3: test03(); break; // NIOFileToConsole
		case  4: test04(); break; // NIOFileTransfer
		case  5: test05(); break; // NIOFileTransferConsole
		case  6: test06(); break; // NIOJavaWhois
		case  7: test07(); break; // NIO
		case  8: test08(); break; // ByteBuffer 1
		case  9: test09(); break; // ByteBuffer 2
		default: break;
		}
	}
}
