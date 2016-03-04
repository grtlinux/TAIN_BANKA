package ic.vela.ibridge.test.selector.sessiontest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/*==================================================================*/
/**
 * 테스트 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    ServerSocketChannelSessionTestMain
 * @author  강석
 * @version 1.0, 2013/06/13
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class SocketChannelSessionTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	private static final String      HOST_IP                = "127.0.0.1";
	private static final String      HOST_PORT              = "2025";
	@SuppressWarnings("unused")
	private static final int         SERVER_SELECTION_KEY   = SelectionKey.OP_ACCEPT;
	private static final int         SOCKET_SELECTION_KEY   = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static Selector            selector              = null;
	private static SocketChannel       socketChannel         = null;
	private static SelectableChannel   selectableChannel     = null;
	
	private static boolean             errFlag               = false;
	
	private static int                 selectCount           = 0;
	
	/*==================================================================*/
	/**
	 * 
	 * @param byteBuffer
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static int recv(ByteBuffer byteBuffer) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		int iLen = 0;
		int iReaded = 0;
		int iCount = 0;
		int iMaxCount = 8;
		
		if (flag) {
			
			while (byteBuffer.hasRemaining() && iCount < iMaxCount) {
				// 첨 일부자료를 읽었는데 1초 동안 나머지를 읽지 못하면
				// 그냥 return 한다. 즉 원하는 길이의 자료는 못 얻는다.
				// 에러처리 또는 로그처리를 한다.
				
				iReaded = socketChannel.read(byteBuffer);
				
				if (iReaded < 0) {
					throw new IOException("channel READ EOF...");
				}
				
				if (iReaded == 0) {
					
					if (iLen == 0) {
						// non blocking 모드 처리를 위해
						// 읽을 자료가 없으면 무조건 return 한다.
						return 0;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					
					iCount ++;
				}
				else {
					iCount = 0;
				}
				
				iLen += iReaded;
			}
		}
		
		return iLen;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param byteBuffer
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static int send(ByteBuffer byteBuffer) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		int iLen = 0;
		int iWritten = 0;
		int iCount = 0;
		int iMaxCount = 10;
		
		if (flag) {
			
			while (byteBuffer.hasRemaining() && iCount < iMaxCount) {
				
				iWritten = socketChannel.write(byteBuffer);
				
				if (iWritten < 0) {
					throw new IOException("channel WRITE EOF...");
				}
				
				if (iWritten == 0) {
					if (iLen == 0) {
						// non blocking 모드 처리를 위해
						// 쓸 자료가 없으면 무조건 return 한다.
						return 0;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					
					iCount ++;
				}
				else {
					iCount = 0;
				}
				
				iLen += iWritten;
			}
		}
		
		return iLen;
	}

	/*==================================================================*/
	/**
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void tryConnection() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			selector = Selector.open();
			
			for (int tryCnt = 1; tryCnt < 5 * 60 * 6; tryCnt ++) {
				try {
					socketChannel = SocketChannel.open(new InetSocketAddress(HOST_IP, Integer.parseInt(HOST_PORT)));
					break;
				} catch (Exception e) {
					//e.printStackTrace();
					println("CLIENT : try to connect to the server.....[TRYCNT=" + tryCnt + "]");
				}
				
				// wait for the next connection
				try {
					Thread.sleep(10000);   // 10 seconds
				} catch (InterruptedException e) {}
			}
			
			socketChannel.configureBlocking(false);
			SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
			selectionKey.attach(new AttachObject("KANG SEOK"));
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void keyConnectableEvent(AttachObject object) throws Exception
	{
		boolean flag = true;
		
		if (flag) {
			println("\t\t# SocketChannel [" + object.getName() + "].isConnectable()");
			
			if (socketChannel.isConnectionPending()) {
				socketChannel.finishConnect();
				socketChannel.close();
				
				throw new IOException("\t\t\tCLIENT - Connection : close the session..");
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void keyReadableEvent(AttachObject object) throws Exception
	{
		boolean flag = true;
		
		if (flag) {
			println("\t\t# SocketChannel [" + object.getName() + "].isReadable()");
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(30);
			byteBuffer.clear();
			
			int iLen = -1;
			
			try {
				// iLen = socketChannel.read(byteBuffer);
				iLen = recv(byteBuffer);
			} catch (IOException e) {
				println("\t\t\tCLIENT - Recv : IOException..........");
				socketChannel.finishConnect();
				socketChannel.close();
				
				throw new IOException("\t\t\tCLIENT - Connection : close the session..");
			}
			
			byteBuffer.flip();
			
			printf("\t\t\tCLIENT - Recv : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void keyWritableEvent(AttachObject object) throws Exception
	{
		boolean flag = true;
		
		if (!flag) {  // TODO : 테스트 후 처리
			println("\t\t# SocketChannel [" + object.getName() + "].isWritable()");
			
			ByteBuffer byteBuffer = ByteBuffer.wrap("ABCDEFGHIJ".getBytes());
			
			int iLen = -1;
			
			try {
				// iLen = socketChannel.write(byteBuffer);
				iLen = send(byteBuffer);
			} catch (IOException e) {
				println("\t\t\tCLIENT - Send : IOException..........");
				socketChannel.finishConnect();
				socketChannel.close();
				
				throw new IOException("\t\t\tCLIENT - Connection : close the session..");
			}
			
			printf("\t\t\tCLIENT - Send : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
		}
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param key
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void socketChannelEvent(SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			socketChannel = (SocketChannel) selectableChannel;
			
			AttachObject object = (AttachObject) key.attachment();

			println("\t# SocketChannel [" + object.getName() + "]");
			
			if (key.isConnectable()) {
				/*
				 * key.isConnectable
				 */
				keyConnectableEvent(object);
			}
			if (key.isReadable()) {
				/*
				 * key.isReadable
				 */
				keyReadableEvent(object);
			}
			if (key.isWritable()) {
				/*
				 * key.isWritable
				 */
				keyWritableEvent(object);
			}
		}
	}

	/*==================================================================*/
	/**
	 * test method
	 */
	/*------------------------------------------------------------------*/
	private static void test01()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {   // DATE.2013.06.13
			
			while (!errFlag) {
				try {
					
					// try to connect to server
					tryConnection();
					
					while (flag) {
						print("#  selector.");
						int selectSize = selector.select();
						println("select() -> " + selectSize);
						if (selectSize <= 0) {
							break;
						}
						
						if (flag) {
							if (flag) println("============ " + selectCount + " START ============");
						}
						
						Set<SelectionKey> setKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterKeys = setKeys.iterator();
						
						while (iterKeys.hasNext()) {
							
							SelectionKey key = iterKeys.next();
							iterKeys.remove();
							
							selectableChannel = key.channel();
							
							if (selectableChannel instanceof ServerSocketChannel) 
							{
								// serverSocketChannelEvent
							}
							else if (selectableChannel instanceof SocketChannel) 
							{
								// socketChannelEvent
								socketChannelEvent(key);
								
							}
							else if (selectableChannel instanceof DatagramChannel) 
							{
								// datagramChannelEvent
							}
							else if (selectableChannel instanceof Pipe.SinkChannel) 
							{
								// pipeSinkChannelEvent
							}
							else if (selectableChannel instanceof Pipe.SourceChannel) 
							{
								// pipeSourceChannelEvent
							}
							else 
							{
								// otherEvent
							}
							
						}   // while (iterKeys.hasNext())
						
						if (flag) {
							if (flag) println("============ " + selectCount + " END ============");
							selectCount ++;
							
							if (flag) {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {}
							}
						}
						
					}   // while (selector.select() > 0)
					
				} catch (IOException e) {
					// close the session because the server closed
					println(e.getMessage());

					if (flag) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {}
					}
				} catch (Exception e) { 
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * default method
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
		}
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private static void println(String msg) 
	/*------------------------------------------------------------------*/
	{
		print(msg + "\n");
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private static void print(String msg) 
	/*------------------------------------------------------------------*/
	{
		System.out.print(msg);
	}

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	private static void printf(String format, Object... args) 
	/*------------------------------------------------------------------*/
	{
		System.out.printf(format, args);
	}

	/*==================================================================*/
	/**
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args) {
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
