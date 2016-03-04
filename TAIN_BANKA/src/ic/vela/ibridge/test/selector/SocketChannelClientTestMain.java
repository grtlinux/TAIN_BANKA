package ic.vela.ibridge.test.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/**
* @name   SocketChannelClientTestMain
* @author Kang Seok
* @date   2013.05.31
* 
* @comment : 아래 소스를 참조하세요.
*          ic.vela.ibridge.test.selector.SocketChannelServerTestMain
*          ic.vela.ibridge.test.selector.SocketChannelClientTestMain
*
*/
public class SocketChannelClientTestMain {

	/////////////////////////////////////////////////////////////////
	/*
	 * Private Static Final Variables
	 */
	private static final String   HOST    = "127.0.0.1";
	private static final String   PORT             = "2345";
	//private static final int     SVR_SELECTION_KEY = (SelectionKey.OP_ACCEPT);
	private static final int     CLI_SELECTION_KEY = (SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	

	/////////////////////////////////////////////////////////////////
	/*
	 * Private Variables
	 */
	private CharsetEncoder encoder = null;
	private CharsetDecoder decoder = null;
	
	/*
	 * Private Variables
	 */
	private Selector selector = null;
	private int port = 2345;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 생성자 : socket 을 넘겨 받는다.
	 */
	public SocketChannelClientTestMain() {
		
		Charset charset = Charset.forName("EUC-KR");
		this.encoder = charset.newEncoder();
		this.decoder = charset.newDecoder();

		this.port = Integer.parseInt(System.getProperty("ic.vela.ibridge.property.port", PORT));

		try {
			selector = Selector.open();
		} catch (Exception e) {
			e.printStackTrace();  // TODO : 나중에 처리할 것임.
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Execution Part
	 */
	@SuppressWarnings("unused")
	public void execute() {
		
		boolean flag = true;
		boolean errFlag = false;
		boolean readed = true;  // 최초에 자료를 보내기 위해
		
		if (flag) {
			try {
				// Client socketChannel을 생성한다. Non-Blocking
				SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HOST, port));
				socketChannel.configureBlocking(false);
				SelectionKey clientKey = socketChannel.register(selector, CLI_SELECTION_KEY);

				String nameClient = "CLIENT-" + new DecimalFormat("00000").format(1);
				clientKey.attach(nameClient);
				
				System.out.println("CLIENT [" + nameClient + "] : connect to the server. : " + socketChannel);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			try {
				for (int i=0; !errFlag && i < 60 && selector.select() > 0; i++) {
					// selector.select() 갯수만큼 반복한다.
					// 즉, 이벤트 객체의 갯수만큼 반복한다. 위 socketChannel 은 3개의 이벤트 객체가 있다.
					Set<SelectionKey> setKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterKeys = setKeys.iterator();
					
					// 각 Channel 을 확인한다.
					// SocketChannel
					while (!errFlag && iterKeys.hasNext()) {
						SelectionKey key = iterKeys.next();
						iterKeys.remove();
						
						SelectableChannel channel = key.channel();
						
						if (channel instanceof SocketChannel) {
							if (flag) System.out.println("# SocketChannel");

							SocketChannel socketChannel = (SocketChannel) channel;
							String nameClient = (String) key.attachment();
							
							try {
								
								if (key.isConnectable()) {
									if (flag) System.out.println("# SocketChannel isConnectable");
									
									// 클라이언트의 접속을 확인한다.
									System.out.println("CLIENT : Client와 연결 설정[" + nameClient + "] : " + socketChannel);
									
									if (socketChannel.isConnectionPending()) {
										System.out.println("CLIENT : Client와 연결 설정종료[" + nameClient + "] : " + socketChannel);

										socketChannel.finishConnect();
										socketChannel.close();
										continue;
									}
								}

								
								if (key.isReadable()) {
									if (flag) System.out.println("# SocketChannel isReadable");
									
									ByteBuffer byteBuffer = ByteBuffer.allocate(70);
									//byteBuffer.clear();
									
									int length = socketChannel.read(byteBuffer);
									if (length > 0) {
										byteBuffer.flip();
										
										CharBuffer charBuffer = decoder.decode(byteBuffer);
										
										System.out.println("CLIENT : RECV : [" + charBuffer.toString() + "]");
									}

									if (flag) {
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {}
									}
								}
								
								//if (readed && key.isWritable()) {
								if (key.isWritable()) {
									if (flag) System.out.println("" + i + "# SocketChannel isWritable");

									// 다른 망을 원하면 queue 에서 읽는 처리가 필요함.
									// 요청전문을 만들어 전송한다.
									String strHdr = "ABCDEFGHIJ";
									String strData = "" ;
									strData += "12345678901234567890";
									//strData += "12345678901234567890";
									//strData += "12345678901234567890";
									//strData += "12345678901234567890";
									//strData += "12345678901234567890";

									CharBuffer charReqHdrBuffer = CharBuffer.wrap(strHdr);
									CharBuffer charReqDataBuffer = CharBuffer.wrap(strData);
									
									ByteBuffer byteReqHdrBuffer = encoder.encode(charReqHdrBuffer);
									ByteBuffer byteReqDataBuffer = encoder.encode(charReqDataBuffer);
									
									ByteBuffer byteReqBuffer = ByteBuffer.allocate(10 + 20);
									
									byteReqBuffer.put(byteReqHdrBuffer);
									byteReqBuffer.put(byteReqDataBuffer);
									byteReqBuffer.rewind();
									
									// 응답전문을 clientChannel 로 보낸다
									int length = socketChannel.write(byteReqBuffer);
									if (length > 0) {
										byteReqBuffer.rewind();
										System.out.println("CLIENT [" + nameClient + "] : REQ HDR+DATA : [" + new String(byteReqBuffer.array()) + "]");
									}
									
									readed = false;
								}

								/*
								if (key.isWritable()) {
									if (flag) System.out.println("# SocketChannel isWritable");
									
									String msg = "(클라이언트) 이것은 테스트용 문자열입니다. This";
									
									CharBuffer charBuffer = CharBuffer.wrap(msg);
									//System.out.println("   원문 : [" + charBuffer.toString() + "]");
									System.out.println("CLIENT : SEND : [" + charBuffer.toString() + "]");
									
									ByteBuffer byteBuffer = encoder.encode(charBuffer);
									
									socketChannel.write(byteBuffer);
									
									//System.out.println("CLIENT : SEND : " + charBuffer.toString());
									
									if (flag) {
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {}
									}
								}
								*/

							} catch (IOException e) {
								
								// e.printStackTrace();
								
								System.out.println("CLIENT : 클라이언트접속에서 Exception 발생 " + socketChannel);

								socketChannel.finishConnect();
								socketChannel.close();
								
								errFlag = true;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flag) {
			System.out.println("CLIENT : 프로그램을 종료합니다.");
		}
	}

	
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
	/*
	 *  Async 통신
	 */
	private static void test02() {
		
		boolean flag = true;
		
		try {
			// Character Set 을 설정한다. 
			Charset charset = Charset.forName("EUC-KR");
			CharsetEncoder encoder = charset.newEncoder();
			CharsetDecoder decoder = charset.newDecoder();

			Selector selector = Selector.open();
			
			// Client socketChannel을 생성한다. Non-Blocking
			SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 2345));
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			
			for (int i=0; i < 3 && selector.select() > 0; i++) {
			// while (selector.select() > 0) {
				
				Set<SelectionKey> setKeys = selector.selectedKeys();
				Iterator<SelectionKey> iteratorKeys = setKeys.iterator();
				
				while (iteratorKeys.hasNext()) {
					
					SelectionKey key = iteratorKeys.next();
					iteratorKeys.remove();
					
					SelectableChannel channel = key.channel();
					
					if (channel instanceof SocketChannel) {
						SocketChannel clientChannel = (SocketChannel) channel;
						
						try {
							
							if (key.isConnectable()) {
								
								System.out.println("CLIENT : Client와 연결 설정 OK...." + clientChannel);
								if (clientChannel.isConnectionPending()) {
									clientChannel.finishConnect();
									clientChannel.close();
									System.out.println("CLIENT : Client와 연결 설정을 마무리 합니다.");
								}
							}
							
							if (key.isReadable()) {
								
								ByteBuffer byteBuffer = ByteBuffer.allocate(70);
								//byteBuffer.clear();
								
								int length = clientChannel.read(byteBuffer);
								if (length > 0) {
									byteBuffer.flip();
									
									CharBuffer charBuffer = decoder.decode(byteBuffer);
									
									System.out.println("CLIENT : RECV : [" + charBuffer.toString() + "]");
								}

								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {}
								}
							}
							
							if (key.isWritable()) {
								
								String msg = "(클라이언트) 이것은 테스트용 문자열입니다. This";
								
								CharBuffer charBuffer = CharBuffer.wrap(msg);
								//System.out.println("   원문 : [" + charBuffer.toString() + "]");
								System.out.println("CLIENT : SEND : [" + charBuffer.toString() + "]");
								
								ByteBuffer byteBuffer = encoder.encode(charBuffer);
								
								clientChannel.write(byteBuffer);
								
								//System.out.println("CLIENT : SEND : " + charBuffer.toString());
								
								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {}
								}
							}

						} catch (IOException e) {
							
							// e.printStackTrace();
							
							System.out.println("CLIENT : 클라이언트접속에서 Exception 발생 " + clientChannel);

							clientChannel.finishConnect();
							clientChannel.close();
						}
					}
				}
			}
			
			System.out.println("CLIENT : finish Client....");
			
			if (!flag) {

				// 메시지를 EUC-KR로 encoding 하여 서버로 보낸다.
				String msg = "(클라이언트) 이것은 테스트용 문자열입니다. This is testing string...";
				
				CharBuffer charBuffer = CharBuffer.wrap(msg);
				
				ByteBuffer byteBuffer = encoder.encode(charBuffer);
				
				socketChannel.write(byteBuffer);

				System.out.println("CLIENT : 클라이언트에서 보낸 내용 : " + charBuffer.toString());
			}
			
			if (!flag) {

				// 서버로 부터 메시를 받고 EUC-KR로 decoding 한다.
				ByteBuffer byteBuffer = ByteBuffer.allocate(128);
				byteBuffer.clear();
				
				socketChannel.read(byteBuffer);
				byteBuffer.rewind();
				
				CharBuffer charBuffer = decoder.decode(byteBuffer);
				
				System.out.println("CLIENT : 서버로부터 받은 내용 : " + charBuffer.toString());
			}
			
			if (!flag) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}
			
			socketChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	private static void test04()
	{
		boolean flag = true;
		boolean errFlag = false;
		
		int socketCount = 0;

		Selector selector = null;
		SocketChannel socketChannel = null;
		
		if (flag) {   // DATE.2013.06.13

			while (!errFlag) {
				try {
					
					selector = Selector.open();

					socketChannel = null;
					while (true) {
						try {
							socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 2025));
							break;
						} catch (Exception e) {
							// e.printStackTrace();
							printf("CLIENT : try to connect to the server...\n");
						}
						
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {}
					}

					socketChannel.configureBlocking(false);
					SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					
					selectionKey.attach("KANG SEOK");
					
					while (selector.select() > 0) {
						
						Set<SelectionKey> setKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterKeys = setKeys.iterator();
						
						while (iterKeys.hasNext()) {
							
							SelectionKey key = iterKeys.next();
							iterKeys.remove();
							
							SelectableChannel selectableChannel = key.channel();
							
							if (selectableChannel instanceof SocketChannel) {
								println("\t# SocketChannel");
								//////////////////////////////////////////////////////////
								//////////////////////////////////////////////////////////
								// SocketChannel : Client Event
								//////////////////////////////////////////////////////////
								//////////////////////////////////////////////////////////
								
								socketChannel = (SocketChannel) selectableChannel;
								String socketChannelName = (String) key.attachment();
								
								if (key.isConnectable()) {
									println("\t\t### SocketChannel.isConnectable()");
									/* --------------------------------------------------------
									 * SocketChannel.isConnectable
									 * --------------------------------------------------------
									 */
									
									if (socketChannel.isConnectionPending()) {
										socketChannel.finishConnect();
										socketChannel.close();
										
										printf("\t\tCLIENT : disconnection to the client. [%s]\n", socketChannelName);
									
										// continue;
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
										
										int iLen = socketChannel.read(byteBuffer);
										byteBuffer.flip();
										
										printf("\t\tCLIENT - Recv : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
										
									}
								}
								
								if (!flag) {
									if (key.isWritable()) {
										println("\t\t### SocketChannel.isWritable()");
										/* --------------------------------------------------------
										 * SocketChannel.isWritable
										 * --------------------------------------------------------
										 */
										
										ByteBuffer byteBuffer = ByteBuffer.wrap("1234567890".getBytes());
										
										int iLen = socketChannel.write(byteBuffer);
										
										printf("\t\tCLIENT - Send : [iLen=%d][%s]\n", iLen, new String(byteBuffer.array()));
									}
								}
							}

						}  // while (iterKeys.hasNext())
						
						if (flag) {
							if (flag) println("##### " + socketCount + " #####");
							socketCount ++;
							
							if (flag) {
								try {
									Thread.sleep(800);
								} catch (InterruptedException e) {}
							}
						}

					}  // while (selector.select() > 0)
					
				} catch (Exception e) {
					//e.printStackTrace();
					println("CLIENT : close the connection because of the server...");
				}
				
				// clear the selector
				if (selector != null) {
					try {
						socketChannel.close();
						selector.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					selector = null;
					println("CLIENT : clear the selector....");
				}
			}  // while (!errFlag)
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
		switch (4) {
		case  1 : test01(); break;
		case  2 : test02(); break;
		case  3 : new SocketChannelClientTestMain().execute(); break;
		case  4 : test04(); break;
		default : break;
		}
	}
}
