package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

public class ClientPrimeNumberGenerator 
{
	private static ReentrantLock lock = new ReentrantLock();
	public static void main(String args[])
	{
		PrimeNumberGenerator gen1 = new PrimeNumberGenerator(1L, 1000000L);
		PrimeNumberGenerator gen2 = new PrimeNumberGenerator(1000000L, 2000000L);
		Thread thread1 = new Thread(gen1);
		thread1.start();
		Thread thread2 = new Thread(gen2);
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen1.getPrimes().forEach((Long prime)->System.out.print(prime + ", "));
		System.out.println("\n Total prime numbers: "+gen1.getPrimes().size());
		System.out.println("\n");
		gen2.getPrimes().forEach((Long prime)->System.out.print(prime + ", "));
		System.out.println("\n Total prime numbers: "+gen2.getPrimes().size());

		InterruptiblePrimeNumberGenerator interruptibleGen = new InterruptiblePrimeNumberGenerator(1L, 1000000L);
		thread2 = new Thread(interruptibleGen);
		thread2.start();
		lock.lock();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		thread2.interrupt();

		try {
			thread2.join();
		} catch (InterruptedException e) {}
		finally{
			lock.unlock();	
		}
		System.out.println("\n Total prime numbers: " +interruptibleGen.getPrimes().size());        

	}
}
