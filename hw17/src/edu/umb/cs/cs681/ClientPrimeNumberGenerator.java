package edu.umb.cs.cs681;

public class ClientPrimeNumberGenerator 
{
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
	
		CancellablePrimeNumberGenerator cancellableGen = new CancellablePrimeNumberGenerator(1L, 1000000L);
		thread1 = new Thread(cancellableGen);
		thread1.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		thread1.interrupt();
		cancellableGen.setDone();
		try {
			thread1.join();
		} catch (InterruptedException e) {}
		System.out.println("\n Total prime numbers: "+cancellableGen.getPrimes().size());	
	}
}
