package ic.vela.ibridge.base.queue;

import java.util.Vector;

/**
 * @name   IBridgeQueue
 * @author Kang Seok
 * @date   2013.05.29
 *
 */
public class IBridgeQueue {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final boolean    FLAG         = false;
	private static final String     CRLF         = "\r\n";
	//private static final int        WAITTIMEOUT  = 1 * 10 * 1000;

	/*
	 * Class Variables
	 */
	private String                 name;    // this name
	private boolean                flag;    // print flag
	private final Vector<Object>   queue;
	private int                    count;

	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public IBridgeQueue(String name, boolean flag) {
		
		this.name = name;
		this.flag = flag;
		this.queue = new Vector<Object> (5, 5);
		this.count = 0;
	}
	
	/*
	 * Constructor
	 */
	public IBridgeQueue(String name) {
		
		this(name, FLAG);
	}

	/*
	 * Constructor
	 */
	public IBridgeQueue() {
		this("IBQUEUE");
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * put to the queue.
	 */
	public synchronized int put(Object object) {
		
		//println("(THREAD:" + Thread.currentThread().getName() + ") IBridgeQueue.put()");
		
		try {
			if (object != null) {
				
				// object 객체가 null이 아니면 queue에 저장한다.
				this.queue.addElement(object);
				this.count ++;
				
				notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//println("(THREAD:" + Thread.currentThread().getName() + ") AFTER  IBridgeQueue.put()");
		
		return this.count;
	}
	
	/*
	 * get from the queue. blocking
	 */
	public synchronized Object get() {
	//public synchronized Object get() throws InterruptedException {
		
		Object object = null;
		
		//println("(THREAD:" + Thread.currentThread().getName() + ") BEFORE  IBridgeQueue.get()");

		try {
			for(int i=0; i < 1 && this.count <= 0; i++){
				// queue에 object객체가 쌓이길 기다린다.
				// WAITTIMEOUT * forLoop 만큼 기다리고 빠져나간다.
				
				wait();
				
				//println("(THREAD:" + Thread.currentThread().getName() + ") wait()");
			}
			
			if (this.count <= 0) {
				return object;
			}
			
			object = this.queue.elementAt(0);
			this.queue.remove(0);
			this.count --;
		
		} catch (InterruptedException e) {
		//	throw new InterruptedException("[HANDLE] interrupted Exception by Kang Seok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//println("(THREAD:" + Thread.currentThread().getName() + ") AFTER   IBridgeQueue.get()");

		return object;
	}
	
	/*
	 * get from the queue. blocking
	 */
	public synchronized Object get(long timeout) {
	//public synchronized Object get() throws InterruptedException {
		
		Object object = null;
		
		//println("(THREAD:" + Thread.currentThread().getName() + ") BEFORE  IBridgeQueue.get()");

		try {
			for(int i=0; i < 1 && this.count <= 0; i++){
				// queue 에 object 객체가 쌓이길 기다린다.
				// WAITTIMEOUT * forLoop 만큼 기다리고 빠져나간다.
				
				//wait(WAITTIMEOUT);  // milliseconds
				wait(timeout);
				
				//println("(THREAD:" + Thread.currentThread().getName() + ") wait(" + (WAITTIMEOUT) + ")");
			}
			
			if (this.count <= 0) {
				return object;
			}
			
			object = this.queue.elementAt(0);
			this.queue.remove(0);
			this.count --;
		
		} catch (InterruptedException e) {
		//	throw new InterruptedException("[HANDLE] interrupted Exception by Kang Seok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//println("(THREAD:" + Thread.currentThread().getName() + ") AFTER   IBridgeQueue.get()");

		return object;
	}
	
	/*
	 * clear the queue
	 */
	public synchronized void clear() {
		
		this.queue.removeAllElements();
		this.count = 0;
	}
	
	/*
	 * 
	 */
	public int getSize() {
		
		return this.count;
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 로그를 출력한다. 
	 */
	protected void print(String printString) {
		
		if (this.flag) {
			System.out.print("[" + name + "] " + printString);
		}
	}
	
	//
	protected void println(String printString) {
		
		print(printString + CRLF);
	}

	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		IBridgeQueue queue = new IBridgeQueue("TEST_QUEUE", true);
		
		String objectIn;
		String objectOut;

		objectIn = "This is a testing message....";
		
		System.out.println(">" + objectIn);
		
		// put into the queue
		queue.put(objectIn);
		
		// get out from the queue
		objectOut = (String) queue.get();
		
		System.out.println(">" + objectOut);
	}
}
