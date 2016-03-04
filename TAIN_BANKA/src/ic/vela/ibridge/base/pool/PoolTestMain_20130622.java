package ic.vela.ibridge.base.pool;

import ic.vela.ibridge.base.queue.AgentSendQueue;
import ic.vela.ibridge.base.queue.IBridgeQueue;

public class PoolTestMain_20130622 extends IBridgePoolThread {
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	//private static final boolean    FLAG         = false;
	//private static final String     CRLF         = "\r\n";
	//private static final int        WAITTIMEOUT  = 60 * 1;
	
	/*
	 * Class Variables
	 */
	private final IBridgeQueue queue;
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public PoolTestMain_20130622(String name, boolean flag, IBridgeQueue queue) {
		
		super(name, flag);
		
		this.queue = queue;
	}

	/*
	 * 
	 */
	public void run() {
		
		try {
			while (true) {
				String msg = (String) queue.get();
				
				println("(THREAD:" + Thread.currentThread().getName() + ") get()=" + msg);
				
				// threadSleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * execute : 해당하는 갯수만큼 Thread를 실행한다.
	 * 각 Thread는 queue를 바라보며 Object가 쌓이길 기다린다.
	 * 1:N 방식의 queue 사용
	 */
	public static void execute(int count, IBridgeQueue queue) throws Exception {
		
		// ThreadGroup threadGroup = new ThreadGroup("PoolTestMain");
		
		PoolTestMain_20130622[] threads = new PoolTestMain_20130622[count];
		
		for (int i=0; i < count; i++) {
			threads[i] = new PoolTestMain_20130622("POOL_TEST-" + i, true, queue);
			threads[i].start();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		String message;
		try {
			AgentSendQueue queue = new AgentSendQueue();
			
			PoolTestMain_20130622.execute(10, queue);
			
			for (int i=0; i < 100; i++) {
				message = "This is a message for the Queue....(" + i + ")";
				
				System.out.println("\nMAIN PUT : " + message);
				queue.put(message);
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
