package ic.vela.ibridge.base.queue;

/**
 * @name   ServantRecvQueue
 * @author Kang Seok
 * @date   2013.05.29
 *
 */
public class ServantRecvQueue extends IBridgeQueue {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final String   NAME         = "AGENT_RECV_QUEUE";
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public ServantRecvQueue(String name, boolean flag) {
		super(name, flag);
	}
	
	/*
	 * Constructor
	 */
	public ServantRecvQueue() {
		this(NAME, true);
	}

	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		ServantRecvQueue queue = new ServantRecvQueue();
		
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
