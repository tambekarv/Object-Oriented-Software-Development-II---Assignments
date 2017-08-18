package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

public class SecurityGate 
{
	private SecurityGate(){}
	private static SecurityGate instance = null;
	private static ReentrantLock instanceLock = new ReentrantLock();
	private ReentrantLock counterLock = new ReentrantLock();
	private int counter = 0;

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
		counterLock.lock();
		try
		{
			counter++;
		}
		finally{
			counterLock.unlock();
		}
	}

	public void exit()
	{
		counterLock.lock();
		try
		{
			counter--;
		}
		finally
		{
			counterLock.unlock();
		}
	}
	
	public void getCount()
	{
		System.out.println(counter);
	}

	public static void main(String args[])
	{
		Guest guest = new Guest();
		Guest guest1 = new Guest();
		Guest guest2 = new Guest();
		Guest guest3 = new Guest();
		Guest guest4 = new Guest();
		Guest guest5 = new Guest();

		Thread thread = new Thread(guest);
		Thread thread1 = new Thread(guest1);
		Thread thread2 = new Thread(guest2);
		Thread thread3 = new Thread(guest3);
		Thread thread4 = new Thread(guest4);
		Thread thread5 = new Thread(guest5);
		
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
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}*/
	
	}
}