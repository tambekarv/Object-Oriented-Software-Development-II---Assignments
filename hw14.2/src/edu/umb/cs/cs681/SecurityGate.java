package edu.umb.cs.cs681;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SecurityGate 
{
	private SecurityGate(){}
	private static SecurityGate instance = null;
	private static ReentrantLock instanceLock = new ReentrantLock();
	AtomicInteger atomicInt = new AtomicInteger(10);
	
	public static SecurityGate getInstance()
	{
		instanceLock.lock();
		try
		{
			if(instance==null)
			{
				instance = new SecurityGate();
			}
		}
		finally
		{
			instanceLock.unlock();
		}
		return instance;
	}

	public void enter()
	{
		atomicInt.updateAndGet((int i)->++i);
	}

	public void exit()
	{
		atomicInt.updateAndGet((int i)->--i);
	}
	
	public void getCount()
	{
		System.out.println(atomicInt);
	}

	public static void main(String args[])
	{
		Guest guest = new Guest();
/*		Guest guest1 = new Guest();
		Guest guest2 = new Guest();
		Guest guest3 = new Guest();
		Guest guest4 = new Guest();
		Guest guest5 = new Guest();
*/
		Thread thread = new Thread(guest);
		Thread thread1 = new Thread(guest);
		Thread thread2 = new Thread(guest);
		Thread thread3 = new Thread(guest);
		Thread thread4 = new Thread(guest);
		Thread thread5 = new Thread(guest);
		
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
	/*	
		try{
			thread.join();
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}