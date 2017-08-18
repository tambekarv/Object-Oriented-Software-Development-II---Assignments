package edu.umb.cs.cs681;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class AccessCounter {

	private ConcurrentHashMap<Path, Integer> map;
	private ReentrantLock lock = new ReentrantLock();
	private static AccessCounter instance = null;

	private AccessCounter()
	{
		//map = new HashMap<>();
		map = new ConcurrentHashMap<>();
	}

	public static AccessCounter getInstance()
	{
		if(instance==null)
		{
			instance = new AccessCounter();
		}
		return instance;
	}


	public void increment(Path path)
	{		
		map.compute(path,(Path p,Integer v)->{return v==null ? 1 : ++v;});
		//map.computeIfPresent(path,(Path p,Integer v)->{return v++;});
		//map.computeIfAbsent(key, mappingFunction)
	}

	public int getCount(Path path)
	{
		int count = 0;
		if(map.containsKey(path))
		{
			count = map.computeIfPresent(path, (Path p, Integer v)->{return v;});
			System.out.println("Count for the file "+path.getFileName()+" is "+count);
			return count;
		}
		System.out.println(count);
		return count;
	}

	public static void main(String args[])
	{
		RequestHandler request1 = new RequestHandler();
		
		Thread thread1 = new Thread(request1);
		Thread thread2 = new Thread(request1);
		Thread thread3 = new Thread(request1);
		Thread thread4 = new Thread(request1);
		Thread thread5 = new Thread(request1);
		Thread thread6 = new Thread(request1);
		Thread thread7 = new Thread(request1);
		Thread thread8 = new Thread(request1);
		Thread thread9 = new Thread(request1);
		Thread thread10 = new Thread(request1);
		Thread thread11 = new Thread(request1);
		Thread thread12 = new Thread(request1);
		Thread thread13 = new Thread(request1);
		Thread thread14 = new Thread(request1);
		Thread thread15 = new Thread(request1);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		thread8.start();
		thread9.start();
		thread10.start();
		thread11.start();
		thread12.start();
		thread13.start();
		thread14.start();
		thread15.start();

		try{
			Thread.sleep(1000);
		}catch (InterruptedException e) {}
		thread1.interrupt();
		thread2.interrupt();
		thread3.interrupt();
		thread4.interrupt();
		thread5.interrupt();
		thread6.interrupt();
		thread7.interrupt();
		thread8.interrupt();
		thread9.interrupt();
		thread10.interrupt();
		thread11.interrupt();
		thread12.interrupt();
		thread13.interrupt();
		thread14.interrupt();
		thread15.interrupt();
		request1.setDone();
	
	}
}
