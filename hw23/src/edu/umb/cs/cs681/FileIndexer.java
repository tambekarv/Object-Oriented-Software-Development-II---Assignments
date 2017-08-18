package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

public class FileIndexer implements Runnable {
	private FileQueue queue = FileQueue.getInstance();
	private static ReentrantLock lock = new ReentrantLock();
	private boolean done = false;
	public void setDone()
	{
		done = true;
	}

	@Override
	public void run() 
	{/*
		while(true)
		{*/
			lock.lock();
			try{
				while(done==false)
				{
					File file = queue.get();
					if(file!=null){
						indexFile(file);
					}
				}/*
				else{
					break;
				}*/
			}finally{
				lock.unlock();
			}
		//}
	}

	public void indexFile(File file)
	{
		System.out.println(file.getName());
	}
}
