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
 * @name    SocketChannelSessionTestMain
 * @author  강석
 * @version 1.0, 2013/06/13
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class ServerSocketChannelSessionTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private static final String      HOST_IP                = "127.0.0.1";
	private static final String      HOST_PORT              = "2025";
	private static final int         SERVER_SELECTION_KEY   = SelectionKey.OP_ACCEPT;
	private static final int         SOCKET_SELECTION_KEY   = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static Selector            selector              = null;
	private static ServerSocketChannel serverSocketChannel   = null;
	private static SocketChannel       socketChannel         = null;
	private static SelectableChannel   selectableChannel     = null;
	
	private static int                 selectorSize          = 0;
	private static int                 selectCount           = 0;
	private static int                 socketChannelCount    = 0;

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
		int iMaxCount = 20;
		
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
		int iMaxCount = 20;
		
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
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static boolean keyConnectableEvent(AttachObject object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			println("\t\t# SocketChannel [" + object.getName() + "].isConnectable()");
			
			if (socketChannel.isConnectionPending()) {
				println("\t\t# SocketChannel [" + object.getName() + "].isConnectionPending()");
				socketChannel.finishConnect();
				socketChannel.close();
				
				return false;
			}
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static boolean keyReadableEvent(AttachObject object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			println("\t\t# SocketChannel [" + object.getName() + "].isReadable()");
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(10);
			byteBuffer.clear();
			
			int iLen = -1;
			
			try {
				// iLen = socketChannel.read(byteBuffer);
				iLen = recv(byteBuffer);
			} catch (IOException e) {
				println("\t\t\tSERVER - Recv : IOException..........");
				socketChannel.finishConnect();
				socketChannel.close();
				return false;
			}
			
			byteBuffer.flip();
			
			printf("\t\t\tSERVER - Recv : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
		}
		
		return true;
	}
	
	/*==================================================================*/
	/**
	 * 
	 * @param socketChannelName
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static boolean keyWritableEvent(AttachObject object) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			println("\t\t# SocketChannel [" + object.getName() + "].isWritable()");
			
			object.increaseCount();
			ByteBuffer byteBuffer = ByteBuffer.wrap(object.getMessage().getBytes());
			
			int iLen = -1;
			
			try {
				// iLen = socketChannel.write(byteBuffer);
				iLen = send(byteBuffer);
			} catch (IOException e) {
				println("\t\t\tSERVER - Send : IOException..........");
				socketChannel.finishConnect();
				socketChannel.close();
				return false;
			}
			
			printf("\t\t\tSERVER - Send : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
		}
		
		return true;
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/**
	 * 
	 * @param key
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	private static void serverSocketChannelEvent(SelectionKey key) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			println("\t# ServerSocketChannel");
			
			serverSocketChannel = (ServerSocketChannel) selectableChannel;
			
			if (key.isAcceptable()) {
				/*
				 * key.isAcceptable
				 */
				println("\t\t# ServerSocketChannel.isAcceptable()");
				
				socketChannel = serverSocketChannel.accept();
				if (socketChannel == null) {
					
					println("\t\t\t# SERVER : null socketChannel....");
					return;
				}
				
				if (selectorSize > 2) {
					// 특정 selector의 갯수 초과이면 deny 한다.
					println("# ACCESS DENIED....");
					socketChannel.finishConnect();
					socketChannel.close();
					return;
				}
				
				socketChannel.configureBlocking(false);
				
				SelectionKey selectionKey = socketChannel.register(selector, SOCKET_SELECTION_KEY);
				
				selectionKey.attach(new AttachObject("KANG SEOK-" + socketChannelCount));
				
				println("\t\t\t# SERVER : make a connection from the client. [" + socketChannelCount + "]");

				socketChannelCount ++;
			}
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
				if (!keyConnectableEvent(object))
					return;
			}
			if (key.isReadable()) {
				/*
				 * key.isReadable
				 */
				if (!keyReadableEvent(object))
					return;
			}
			if (key.isWritable()) {
				/*
				 * key.isWritable
				 */
				if (!keyWritableEvent(object))
					return;
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
			
			try {
				
				// open the selector
				selector = Selector.open();
				
				// make the server socket channel
				serverSocketChannel = ServerSocketChannel.open();
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.socket().bind(new InetSocketAddress(Integer.parseInt(HOST_PORT)));
				serverSocketChannel.register(selector, SERVER_SELECTION_KEY);
				
				// get the select item of the selector
				while (flag) {
					print("#  selector.");
					selectorSize = selector.select();
					println("select() -> " + selectorSize);
					
					if (selectorSize <= 0) {
						break;
					}
					
					if (flag) {
						if (flag) println("============ " + selectCount + " START ============");
					}
					
					// to get the iterator selected key
					Set<SelectionKey> setKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterKeys = setKeys.iterator();
					
					while (iterKeys.hasNext()) {
						
						// to get the key
						SelectionKey key = iterKeys.next();
						iterKeys.remove();
						
						// selectable channel
						selectableChannel = key.channel();
						
						if (selectableChannel instanceof ServerSocketChannel) 
						{
							// serverSocketChannelEvent
							serverSocketChannelEvent(key);
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
						else {
							// otherEvent
						}
						
					}   // while (iterKeys.hasNext())
					
					if (flag) {
						if (flag) println("============ " + selectCount + " END ============");
						selectCount ++;
						
						if (flag) {
							// 모든 클라이언트에 송수신하고 기다린다.
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {}
						}
					}
					
				}   // while (selector.select() > 0)
				
			} catch (Exception e) {
				e.printStackTrace();
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
