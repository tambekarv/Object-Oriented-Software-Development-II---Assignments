package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

public class Singleton implements Runnable 
{
	private Singleton(){};
	private static Singleton instance = null;

	private static ReentrantLock lock = new ReentrantLock();

	public static Singleton getInstance()
	{
		lock.lock();
		try
		{
			if(instance==null)
				instance = new Singleton();
		}
		finally
		{
			lock.unlock();
		}
		return instance;
	}

	public static void main(String args[])
	{
		Singleton instance1 = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		Singleton instance3 = Singleton.getInstance();
		Thread thread1 = new Thread(instance1);
		Thread thread2 = new Thread(instance2);
		Thread thread3 = new Thread(instance3);
		thread1.start();
		thread2.start();
		thread3.start();
		try
		{
			thread1.join();
			thread2.join();
			thread3.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		System.out.println(Singleton.getInstance());
	}
}
