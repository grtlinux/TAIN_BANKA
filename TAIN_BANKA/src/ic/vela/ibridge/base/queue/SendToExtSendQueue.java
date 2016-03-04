package ic.vela.ibridge.base.queue;

/**
 * @name   SendToExtSendQueue
 * @author Kang Seok
 * @date   2013.05.31
 *
 */
public class SendToExtSendQueue extends IBridgeQueue {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final String   NAME         = "FEP_SELECTOR_RECV_QUEUE";
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public SendToExtSendQueue(String name, boolean flag) {
		super(name, flag);
	}
	
	/*
	 * Constructor
	 */
	public SendToExtSendQueue() {
		this(NAME, true);
	}

	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		SendToExtSendQueue queue = new SendToExtSendQueue();
		
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
