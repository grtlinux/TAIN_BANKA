package ic.vela.ibridge.base.queue;

public class SendToFepSendQueue extends IBridgeQueue {

	/////////////////////////////////////////////////////////////////
	/*
	 * Final Variables
	 */
	private static final String   NAME         = "FEP_SELECTOR_RECV_QUEUE";
	
	/////////////////////////////////////////////////////////////////
	/*
	 * Constructor
	 */
	public SendToFepSendQueue(String name, boolean flag) {
		super(name, flag);
	}
	
	/*
	 * Constructor
	 */
	public SendToFepSendQueue() {
		this(NAME, true);
	}

	/////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		
		SendToFepSendQueue queue = new SendToFepSendQueue();
		
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
