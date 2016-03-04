package ic.vela.ibridge.test;

/**
 * @name   ThreadGroupTestMain
 * @author Kang Seok
 * @date   2013.05.28
 *
 */
public class ThreadGroupTestMain {
	
	public static void main(String[] args) {
		ThreadGroup main = Thread.currentThread().getThreadGroup();
		
		ThreadGroup tg1 = new ThreadGroup("Group1");
		ThreadGroup tg2 = new ThreadGroup("Group2");
		
		ThreadGroup subGrp = new ThreadGroup(tg1, "SubGroup11");
		
		tg1.setMaxPriority(3);
		
		Thread t1 = new Thread(tg1, "thread-1");
		Thread t2 = new Thread(subGrp, "thread-2");
		Thread t3 = new Thread(tg2, "thread-3");
		
		t1.start();
		t2.start();
		t3.start();
		
		System.out.println("main.getName()          : " + main.getName());
		System.out.println("main.activeGroupCount() : " + main.activeGroupCount());
		System.out.println("main.activeCount()      : " + main.activeCount());
		
		main.list();
		tg1.list();
		tg2.list();
	}
}

/*

------------------------------------------------
class PrimeThread extends Thread {
    long minPrime;
    PrimeThread(long minPrime) {
        this.minPrime = minPrime;
    }
 
    public void run() {
        // compute primes larger than minPrime
        . . .
    }
}

PrimeThread p = new PrimeThread(143);
     p.start();

------------------------------------------------

class PrimeRun implements Runnable {
    long minPrime;
    PrimeRun(long minPrime) {
        this.minPrime = minPrime;
    }
 
    public void run() {
        // compute primes larger than minPrime
        . . .
    }
}

PrimeRun p = new PrimeRun(143);
     new Thread(p).start();
 
------------------------------------------------

public class ThreadHandle implements Runnable {
	final private static int STATE_INIT      = 0x0001 ;
	final private static int STATE_STARTED   = 0x0001 << 1;
	final private static int STATE_SUSPENDED = 0x0001 << 2;
	final private static int STATE_STOPED    = 0x0001 << 3;
	
	private int stateCode = STATE_INIT;
	
	public void start() {    // �ܺο��� call �Ѵ�.
		synchronized (this) {
			if (stateCode != STATE_INIT)  // INIT ���°� �ƴϸ� �̹� ����Ǿ���.
				throw new IllegalStateException("already started");
			
			thisThread = new Thread(this);
			if (threadName != null) thisThread.setName(threadName);
			thisThread.start();
			stateCode = STATE_STARTED;
		}
	}
	
	public void stop() {    // �ܺο��� call �Ѵ�.
		synchronized (this) {
			if (stateCode == STATE_STOPED)   // �̹� ��?�ٸ� �� ȣ���� �ʿ䰡 ����.
				throw new IllegalStateException("already stopped");
			thisThread.interrupt();
			stateCode = STATE_STOPED;
		}
	}
	
	public void suspend() {   // �ܺο��� call �Ѵ�.
		synchronized (this) {
			if (stateCode == STATE_SUSPENDED) return;
			if (stateCode == STATE_INIT)
				throw new IllegalStateException("not started yet");
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			
			stateCode = STATE_SUSPENDED;
		}
	}
	
	public void run() {    // thisThread.start()�� ���� �ڵ����� �����Ѵ�. �ܺο��� ������ �������� �ʴ´�.
		while (true) {
			// �����ڵ尡 �Ͻ� ������� while ������ ��� ����ϵ��� �Ѵ�.
			while (stateCode == STATE_SUSPENDED) {
				try {
					System.out.println("[handle] suspending...");
					Thread.sleep(24 * 60 * 60 * 1000); // �Ϸ�
				} catch (InterruptedException e) {
					if (stateCode != STATE_SUSPENDED) {
						System.out.println("[handle] resuming....");
						break;
					}
				}
			}
			
			if (stateCode == STATE_STOPPED) {
				System.out.println("[handle] stopping...");
				break;
			}
			
			processComplexJob();
		}
	}
	
	public void resume() {   // �ܺο��� call �Ѵ�.
		synchronized(this) {
			if (stateCode == STATE_STARTED || stateCode == STATE_INIT) return;
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			
			stateCode = STATE_STARTED;
			
			thisThread.interrupt(); // �� ����� �Ѵ�.
		}
	}
	
}

------------------------------------------------






























*/
