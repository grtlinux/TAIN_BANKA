package ic.vela.ibridge.base.queue;

/**
 * @name   ServerSelectSendQueue
 * @author Kang Seok
 * @date   2013.05.31
 *
 */
public class ServerSelectSendQueue extends IBridgeQueue {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final String   NAME         = "FEP_SELECTOR_RECV_QUEUE";
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public ServerSelectSendQueue(String name, boolean flag) {
		super(name, flag);
	}
	
	/*
	 * Constructor
	 */
	public ServerSelectSendQueue() {
		this(NAME, true);
	}

	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		ServerSelectSendQueue queue = new ServerSelectSendQueue();
		
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
