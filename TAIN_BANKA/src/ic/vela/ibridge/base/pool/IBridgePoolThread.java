package ic.vela.ibridge.base.pool;


/**
 * @name   IBridgePoolThread
 * @author Kang Seok
 * @date   2013.05.29
 *
 */
public abstract class IBridgePoolThread extends Thread implements Cloneable {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final boolean    FLAG         = false;
	private static final String     CRLF         = "\r\n";
	//private static final int        WAITTIMEOUT  = 60 * 1;
	
	/*
	 * Class Variables
	 */
	private String                 name;    // this name
	private boolean                flag;   // print flag

	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public IBridgePoolThread(String name, boolean flag) {
		
		super();
		
		this.name = name;
		this.flag = flag;
		
		println("Constructor....");
	}
	/*
	 * Constructor 
	 */
	public IBridgePoolThread() {
		
		this("POOL_THREAD", FLAG);
	}
	
	/*
	 * 추상 Method
	 */
	public abstract void run();
	
	/////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	public synchronized boolean threadWait() {
		
		boolean bReturn = true;
		
		try {
			wait();
		} catch (Exception e) {
			bReturn = false;
		}
		
		return bReturn;
	}
	
	/*
	 * 
	 */
	public synchronized boolean threadWait(long milliseconds) {
		
		boolean bReturn = true;
		
		try {
			wait(milliseconds);
		} catch (Exception e) {
			bReturn = false;
		}
		
		return bReturn;
	}
	
	/*
	 * 
	 */
	public synchronized boolean threadNotify() {
		
		boolean bReturn = true;
		
		try {
			notify();
		} catch (Exception e) {
			bReturn = false;
		}
		
		return bReturn;
	}
	
	/*
	 * 
	 */
	public void threadSleep(long milliseconds) {
		
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {}
	}

	/*
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		
		Object object = (Object) super.clone();
		
		return object;
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
}
