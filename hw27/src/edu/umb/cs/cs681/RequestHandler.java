package edu.umb.cs.cs681;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable{
	private AccessCounter access = AccessCounter.getInstance();
	private ReentrantLock lock = new ReentrantLock();
	volatile boolean done = false;

	public void setDone()
	{
		done = true;
	}


	@Override
	public void run() 
	{
		lock.lock();
		try{
			while(done == false)
			{
				Path path = Paths.get("src/a.html");
				Path path2 = Paths.get("src/b.html");
				Path path3 = Paths.get("src/c.html");
				Path path4 = Paths.get("src/d.html");
				Path path5 = Paths.get("src/e.html");
				
				access.increment(path);
				access.getCount(path);
				access.increment(path2);
				access.getCount(path2);
				access.increment(path3);
				access.getCount(path3);
				access.increment(path4);
				access.getCount(path4);
				access.increment(path5);
				access.getCount(path5);
			}
		}finally{
			lock.unlock();
		}
	}
}
