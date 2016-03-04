package ic.vela.ibridge.test.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class SocketChannelServerTestMain {

	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	private static void test01() {
		
		try {
			Charset charset = Charset.forName("EUC-KR");
			CharsetEncoder encoder = charset.newEncoder();
			CharsetDecoder decoder = charset.newDecoder();
			
			String msg = "이것은 테스트용 문자열입니다. This is testing string...";
			
			CharBuffer charMsg = CharBuffer.wrap(msg);
			System.out.println("원문 : [" + charMsg.toString() + "]");
			
			ByteBuffer byteMsg = encoder.encode(charMsg);
			System.out.println("encode.원문 : [" + new String(byteMsg.array()) + "]");

			CharBuffer charMsg2 = decoder.decode(byteMsg);
			System.out.println("decode.encode.원문 : [" + charMsg2.toString() + "]");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	private static void test02() {
		
		boolean flag = true;
		
		try {

			Charset charset = Charset.forName("EUC-KR");
			CharsetEncoder encoder = charset.newEncoder();
			CharsetDecoder decoder = charset.newDecoder();
			
			Selector selector = Selector.open();
			
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(2345));
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("SERVER : Client의 접속을 기다립니다." + serverChannel );
			
			while (selector.select() > 0) {
				
				Set<SelectionKey> setKeys = selector.selectedKeys();
				Iterator<SelectionKey> iteratorKeys = setKeys.iterator();
				
				while (iteratorKeys.hasNext()) {
					
					SelectionKey key = iteratorKeys.next();
					iteratorKeys.remove();
					
					SelectableChannel channel = key.channel();
					
					if (channel instanceof ServerSocketChannel) {
						
						if (key.isAcceptable()) {
							serverChannel = (ServerSocketChannel) channel;
							
							SocketChannel socketChannel = serverChannel.accept();
							if (socketChannel == null) {
								System.out.println("SERVER : null socketChannel...");
								continue;
							}
							
							socketChannel.configureBlocking(false);
							SelectionKey clientKey = socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
							clientKey.attach("This is First Message. 강석...");
							
							System.out.println("SERVER : Accepted client socket.." + socketChannel);
						}
						
					}
					else if (channel instanceof SocketChannel) {
						
						SocketChannel socketChannel = (SocketChannel) channel;
						
						try {
							
							if (key.isConnectable()) {
								
								System.out.println("SERVER : Client와 연결 설정 OK...." + socketChannel);
								if (socketChannel.isConnectionPending()) {
									socketChannel.finishConnect();
									socketChannel.close();
									System.out.println("SERVER : Client와 연결 설정을 마무리 합니다.");
								}
							}

							if (key.isReadable()) {
								
								ByteBuffer byteBuffer = ByteBuffer.allocate(70);
								// byteBuffer.clear();

								// header 를 읽고 XML 데이터의 길이를 얻어 다시 데이터를 읽어 수신단으로 보낸다. 
								int length = socketChannel.read(byteBuffer);
								if (length > 0) {
									byteBuffer.flip();
									//byteBuffer.rewind();
									
									CharBuffer charBuffer = decoder.decode(byteBuffer);
									
									System.out.println("SERVER : RECV : [" + charBuffer.toString() + "]");
								}
								
								if (flag) {
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {}
								}
							}

							if (key.isWritable()) {
								
								String msg = "(서버) 이것은 테스트용 문자열입니다. This is testing string...";
								
								// msg = (String) key.attachment();
								
								CharBuffer charBuffer = CharBuffer.wrap(msg);
								//System.out.println("   원문 : [" + charBuffer.toString() + "]");
								//System.out.println("SERVER : SEND : [" + charBuffer.toString() + "]");
								
								ByteBuffer byteBuffer = encoder.encode(charBuffer);
								charBuffer.rewind();
								//byteBuffer.rewind(); System.out.println("2 >[" + byteBuffer + "]");
								
								// Queue 의 object 의 갯수에 해당하는 만큼 읽어 write 한다.
								int length = socketChannel.write(byteBuffer); System.out.println("1 > byteBuffer >" + byteBuffer);
								byteBuffer.rewind(); System.out.println("2 > byteBuffer >" + byteBuffer);
								if (length > 0) {
									System.out.println("SERVER : SEND : (" + length + ") [" + new String(byteBuffer.array(), 0, length) + "]");
								}
								//System.out.println("SERVER : SEND : [" + charBuffer + "]");

								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {}
								}
							}

						} catch (IOException e) {
							
							// e.printStackTrace();
							
							System.out.println("SERVER : 클라이언트접속에서 Exception 발생 " + socketChannel);

							socketChannel.finishConnect();
							socketChannel.close();
						}
							
					}
					else if (channel instanceof DatagramChannel) {
						
					}
					else if (channel instanceof Pipe.SinkChannel) {
						
					}
					else if (channel instanceof Pipe.SourceChannel) {
						
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/**
	 * confirm the state of the server connection
	 */
	private static void test03()
	{
		boolean flag = true;
		int serverSocketCount = 0;
		int socketCount = 0;
		
		if (flag) {    // DATE.2013.06.13
			try {
				
				Selector selector = Selector.open();
				
				ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.socket().bind(new InetSocketAddress(2025));
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				
				while (selector.select() > 0) {
					Set<SelectionKey> setKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterKeys = setKeys.iterator();
					
					while (iterKeys.hasNext()) {
						SelectionKey key = iterKeys.next();
						iterKeys.remove();
						
						SelectableChannel selectableChannel = key.channel();
						
						if (selectableChannel instanceof ServerSocketChannel) {
							println("\t# ServerSocketChannel");
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// ServerSocketChannel : Server Event
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							
							serverSocketChannel = (ServerSocketChannel) selectableChannel;
							
							if (key.isAcceptable()) {
								println("\t\t### ServerSocketChannel.isAcceptable()");
								/* --------------------------------------------------------
								 * ServerSocketChannel accept
								 * make a SocketChannel of a connection from the client
								 * --------------------------------------------------------
								 */
								
								SocketChannel socketChannel = serverSocketChannel.accept();
								if (socketChannel == null) {
									println("\t\tSERVER : null socketChannel..");
								}
								
								socketChannel.configureBlocking(false);
								
								SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
								
								selectionKey.attach("KANG SEOK-" + serverSocketCount);
								
								serverSocketCount ++;
								
								println("\t\tSERVER : make a connection from the client. [" + serverSocketCount + "]");
							}
						}
						else if (selectableChannel instanceof SocketChannel) {
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// SocketChannel : Client Event
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////

							SocketChannel socketChannel = (SocketChannel) selectableChannel;
							String socketChannelName = (String) key.attachment();

							println("\t# SocketChannel [" + socketChannelName + "]");
							
							if (key.isConnectable()) {
								println("\t\t### SocketChannel.isConnectable()");
								/* --------------------------------------------------------
								 * SocketChannel.isConnectable
								 * --------------------------------------------------------
								 */
								if (socketChannel.isConnectionPending()) {
									socketChannel.finishConnect();
									socketChannel.close();
									
									printf("\t\tSERVER : disconnection to the client. [%s]\n", socketChannelName);
									
									//continue;
									break;
								}
							}
							
							if (flag) {
								if (key.isReadable()) {
									println("\t\t### SocketChannel.isReadable()");
									/* --------------------------------------------------------
									 * SocketChannel.isReadable
									 * --------------------------------------------------------
									 */
									
									ByteBuffer byteBuffer = ByteBuffer.allocate(10);
									byteBuffer.clear();
									
									int iLen = -1;
									try {
										iLen = socketChannel.read(byteBuffer);
									} catch (IOException e) {
										println("\t\tkey.isReadable() : IOException .................");
										socketChannel.finishConnect();
										socketChannel.close();
										continue;
									}
									byteBuffer.flip();
									
									printf("\t\tSERVER - Recv : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
									
								}
							}
							
							if (flag) {
								if (key.isWritable()) {
									println("\t\t### SocketChannel.isWritable()");
									/* --------------------------------------------------------
									 * SocketChannel.isWritable
									 * --------------------------------------------------------
									 */
									
									ByteBuffer byteBuffer = ByteBuffer.wrap("ABCDEFGHIJ".getBytes());
									
									int iLen = -1;
									try {
										iLen = socketChannel.write(byteBuffer);
									} catch (IOException e) {
										println("\t\tkey.isWritable() : IOException .................");
										socketChannel.finishConnect();
										socketChannel.close();
										continue;
									}
									
									printf("\t\tSERVER - Send : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
								}
							}
						}
						else if (selectableChannel instanceof DatagramChannel) {
							println("# DatagramChannel");
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// DatagramChannel
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							
						}
						else if (selectableChannel instanceof Pipe.SinkChannel) {
							println("# Pipe.SinkChannel");
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// Pipe.SinkChannel
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							
						}
						else if (selectableChannel instanceof Pipe.SourceChannel) {
							println("# Pipe.SourceChannel");
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// Pipe.SourceChannel
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							
						}
						else {
							println("# Nothing");
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							// Nothing
							//////////////////////////////////////////////////////////
							//////////////////////////////////////////////////////////
							
						}
					}  // while (iterKeys.hasNext())
					
					if (flag) {
						if (flag) println("##### " + socketCount + " #####");
						socketCount ++;
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}  // while (selector.select() > 0)
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param msg
	 */
	private static void println(String msg) {
		print(msg + "\n");
	}
	
	private static void print(String msg) {
		System.out.print(msg);
	}

	private static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	public static void main(String[] args) {
		switch (3) {
		case  1 : test01(); break;
		case  2 : test02(); break;
		case  3 : test03(); break;
		default : break;
		}
	}
}
