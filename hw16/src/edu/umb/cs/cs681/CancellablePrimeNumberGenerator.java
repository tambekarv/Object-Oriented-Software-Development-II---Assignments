package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

public class CancellablePrimeNumberGenerator extends PrimeNumberGenerator{
	private boolean done = false;
	private ReentrantLock lock = new ReentrantLock();

	public CancellablePrimeNumberGenerator(long from, long to) 
	{
		super(from, to);
	}

	public void setDone()
	{
		done = true;
	}

	public void run(){
		// Stop generating prime numbers if done==true

		for (long n = from; n <= to; n++) {
			lock.lock();
			try{
				if(done==true)
				{
					System.out.println("Stopped generating prime numbers.");
					this.primes.clear();
					break;
				}
			}
			finally{
				lock.unlock();
			}
			if(isPrime(n))
			{ 
				this.primes.add(n); 
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}