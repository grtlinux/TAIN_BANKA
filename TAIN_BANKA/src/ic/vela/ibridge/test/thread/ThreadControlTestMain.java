package ic.vela.ibridge.test.thread;


/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/**
 * @name   MyRunnable
 * @author lee
 *
 */
class MyThread implements Runnable {
	
	private static final int STATE_INIT      = 0x0001 << 0;
	private static final int STATE_STARTED   = 0x0001 << 1;
	private static final int STATE_SUSPENDED = 0x0001 << 2;
	private static final int STATE_STOPPED   = 0x0001 << 3;

	private int stateCode = STATE_INIT;
	
	private Thread myThread = null;
	private String name = null; 
	
	//
	public MyThread(String name) {
		this.stateCode = STATE_INIT;
		this.name = name;
	}
	
	// �⺻���� �����ϰ� 
	public void start() {   // �ܺο��� call �Ѵ�.
		synchronized (this) {
			switch (this.stateCode) {
			case STATE_INIT :
				this.myThread = new Thread(this);
				if (this.name != null) {
					this.myThread.setName(name);
				}
				
				this.myThread.start();
				this.stateCode = STATE_STARTED;
				break;

			case STATE_STARTED :
			case STATE_SUSPENDED :
			case STATE_STOPPED :
				throw new IllegalStateException("STATE : already started....");
			default :
				throw new IllegalStateException("STATE : Not Available Code....");
			}
		}
	}
	
	// Thread�� �����Ѵ�.
	public void stop() {   // �ܺο��� call �Ѵ�.
		synchronized (this) {
			switch (this.stateCode) {
			case STATE_INIT :
			case STATE_STARTED :
			case STATE_SUSPENDED :
				this.myThread.interrupt();
				this.stateCode = STATE_STOPPED;
				break;

			case STATE_STOPPED :
				throw new IllegalStateException("STATE : already stopped....");
			default :
				throw new IllegalStateException("STATE : Not Available Code....");
			}
		}
	}
	
	// Thread�� �����.
	public void suspend() {   // �ܺο��� call �Ѵ�.
		synchronized (this) {
			switch (this.stateCode) {
			case STATE_INIT :
				throw new IllegalStateException("STATE : not started yet..");
			case STATE_STARTED :
				this.stateCode = STATE_SUSPENDED;
				break;
				
			case STATE_SUSPENDED :
				throw new IllegalStateException("STATE : already suspended....");
			case STATE_STOPPED :
				throw new IllegalStateException("STATE : already stopped....");
			default :
				throw new IllegalStateException("STATE : Not Available Code....");
			}
		}
	}
	
	// ���� Thread�� ������Ѵ�.
	public void resume() {   // �ܺο��� call �Ѵ�.
		synchronized (this) {
			switch (this.stateCode) {
			case STATE_INIT :
				throw new IllegalStateException("STATE : not started yet..");
			case STATE_STARTED :
				throw new IllegalStateException("STATE : already started....");
			case STATE_SUSPENDED :
				this.stateCode = STATE_STARTED;
				break;
			case STATE_STOPPED :
				throw new IllegalStateException("STATE : already stopped....");
			default :
				throw new IllegalStateException("STATE : Not Available Code....");
			}
			
			this.myThread.interrupt();
		}
	}
	
	// Thread�� �����Ѵ�.
	public void run() {   // Thread run

		while (this.stateCode != STATE_STOPPED) {
			// �����ڵ尡 �Ͻ�������� while������ ��� ����ϵ��� �Ѵ�.
			while (this.stateCode == STATE_SUSPENDED) {
				try {
					System.out.println("[HANDLE] suspending....");
					// Thread.sleep(24 * 60 * 60 * 1000);
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					if (this.stateCode != STATE_SUSPENDED) {
						System.out.println("[HANDLE] resumeing....");
						break;
					}
				}
			}
			
			if (this.stateCode == STATE_STOPPED) {
				System.out.println("[HANDLE] stopping....");
			}
			
			////////////////////
			// JOB PROCESSING
			System.out.println("Hello running....");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {}
		}
	}
	
	// Thread�� ���¸� ��´�.
	public String getStateCode() {
		synchronized (this) {
			switch (this.stateCode) {
			case STATE_INIT :
				return "STATE_INIT";
			case STATE_STARTED :
				return "STATE_STARTED";
			case STATE_SUSPENDED :
				return "STATE_SUSPENDED";
			case STATE_STOPPED :
				return "STATE_STOPPED";
			default :
				return "NOT AVAILABLE CODE";
			}
		}
	}
}

/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/**
* @name   MyThread
* @author lee
*
*/
@SuppressWarnings("unused")
class MyThread2 extends Thread {

	private static final int STATE_INIT      = 0x0001 << 0;
	private static final int STATE_STARTED   = 0x0001 << 1;
	private static final int STATE_SUSPENDED = 0x0001 << 2;
	private static final int STATE_STOPED    = 0x0001 << 3;

	public MyThread2() {
	
	}
	
	public void run() {
		try {
			////////////////////
			// JOB PROCESSING
			System.out.println("Hello running....");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/**
 * @name   ThreadControlTestMain
 * @author Kang Seok
 * @date   2013.05.28
 *
 */
public class ThreadControlTestMain {

	/////////////////////////////////////////////////////////////////////

	/*
	 * Runnable Thread
	 */
	private static void execute01() {

		MyThread thr = new MyThread("KANG");
		System.out.println(">" + thr.getStateCode());
		
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}

		thr.start();
		System.out.println(">" + thr.getStateCode());

		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}
		
		thr.suspend();
		System.out.println(">" + thr.getStateCode());

		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}
		
		thr.resume();
		System.out.println(">" + thr.getStateCode());

		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}
		
		thr.stop();
		System.out.println(">" + thr.getStateCode());
		
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}
	}
	
	/*
	 * 
	 */
	private static void execute02() {
		
	}
	
	/////////////////////////////////////////////////////////////////////
	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		switch (1) {
		case  1 : execute01(); break;
		case  2 : execute02(); break;
		default : break;
		}
	}
}
