package edu.umb.cs.cs681;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {

	private HashMap<Path, Integer> map;
	private ReentrantLock lock = new ReentrantLock();
	private static AccessCounter instance = null;

	private AccessCounter()
	{
		map = new HashMap<>();
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
		if(map.containsKey(path))
		{	
			Set<Path> keys = map.keySet();
			for (Path key : keys) {
				lock.lock();
				try {
					if(key.equals(path))
					{
						Integer value = map.get(key) + 1;
						map.put(key, value);
					}
					else{
						continue;
					}
				}
				finally
				{
					lock.unlock();
				}
			}
		}
		else
		{
			lock.lock();
			try{
				map.put(path, 1);
			}
			finally{
				lock.unlock();
			}
		}
	}

	public int getCount(Path path)
	{
		int count = 0; 
		if(map.containsKey(path))
		{
			Set<Entry<Path, Integer>> entrySet = map.entrySet();
			for(Entry<Path, Integer> entry: entrySet)
			{
				lock.lock();
				try{
					if(entry.getKey().equals(path))
					{
						count = entry.getValue();
						System.out.println("Count for file "+entry.getKey().getFileName()+" is "+count);
					}
				}finally{
					lock.unlock();
				}
			}
		}
		else
		{
			lock.lock();
			try{
				System.out.println(count);
			}
			finally{
				lock.unlock();
			}
		}
		return count;
	}

	public static void main(String args[])
	{
		String A = "a.html";
		File fileA = new File(A);
		RequestHandler request1 = new RequestHandler(fileA, FIFO.getInstance());
		Thread thread1 = new Thread(request1);
		Thread thread2 = new Thread(request1); 
		
		String B = "b.html";
		File fileB = new File(B);
		request1 = new RequestHandler(fileB, FIFO.getInstance());
		Thread thread3 = new Thread(request1);
		Thread thread4 = new Thread(request1);
		Thread thread5 = new Thread(request1);
		
		String C = "c.html";
		File fileC = new File(C);
		request1 = new RequestHandler(fileC, FIFO.getInstance());
		Thread thread6 = new Thread(request1);
		Thread thread7 = new Thread(request1);
		Thread thread8 = new Thread(request1);
		Thread thread9 = new Thread(request1);
		
		String D = "d.html";
		File fileD = new File(D);
		request1 = new RequestHandler(fileD, FIFO.getInstance());
		Thread thread10 = new Thread(request1);
		Thread thread11 = new Thread(request1);
		
		String E = "e.html";
		File fileE = new File(E);
		request1 = new RequestHandler(fileE, FIFO.getInstance());
		Thread thread12 = new Thread(request1);
		
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
		
		try{
			Thread.sleep(5000);
		}catch (InterruptedException e) {}
		thread1.interrupt();
		thread6.interrupt();
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
		request1.setDone();
	}
}
