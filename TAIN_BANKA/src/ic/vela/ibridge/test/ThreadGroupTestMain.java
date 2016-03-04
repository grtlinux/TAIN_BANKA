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
	
	public void start() {    // 외부에서 call 한다.
		synchronized (this) {
			if (stateCode != STATE_INIT)  // INIT 상태가 아니면 이미 실행되었다.
				throw new IllegalStateException("already started");
			
			thisThread = new Thread(this);
			if (threadName != null) thisThread.setName(threadName);
			thisThread.start();
			stateCode = STATE_STARTED;
		}
	}
	
	public void stop() {    // 외부에서 call 한다.
		synchronized (this) {
			if (stateCode == STATE_STOPED)   // 이미 멈?다면 또 호출할 필요가 없다.
				throw new IllegalStateException("already stopped");
			thisThread.interrupt();
			stateCode = STATE_STOPED;
		}
	}
	
	public void suspend() {   // 외부에서 call 한다.
		synchronized (this) {
			if (stateCode == STATE_SUSPENDED) return;
			if (stateCode == STATE_INIT)
				throw new IllegalStateException("not started yet");
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			
			stateCode = STATE_SUSPENDED;
		}
	}
	
	public void run() {    // thisThread.start()에 의해 자동으로 실행한다. 외부에서 강제로 실행하지 않는다.
		while (true) {
			// 상태코드가 일시 정지라면 while 문에서 계속 대기하도록 한다.
			while (stateCode == STATE_SUSPENDED) {
				try {
					System.out.println("[handle] suspending...");
					Thread.sleep(24 * 60 * 60 * 1000); // 하루
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
	
	public void resume() {   // 외부에서 call 한다.
		synchronized(this) {
			if (stateCode == STATE_STARTED || stateCode == STATE_INIT) return;
			if (stateCode == STATE_STOPPED)
				throw new IllegalStateException("already stopped");
			
			stateCode = STATE_STARTED;
			
			thisThread.interrupt(); // 꼭 해줘야 한다.
		}
	}
	
}

------------------------------------------------






























*/
