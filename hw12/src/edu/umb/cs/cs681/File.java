package edu.umb.cs.cs681;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class File 
{
	private boolean changed = false;
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
	Date date = new Date();
	ReentrantLock lock = new ReentrantLock();

	public void change()
	{
		lock.lock();
		try{
			changed = true;
		}
		finally
		{
			lock.unlock();
		}
	}

	public void save()
	{
		lock.lock();
		try{
			if(changed == false)
				return;
			else{
				System.out.println("File saved at "+dateFormat.format(date));
				changed = false;
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	public static void main(String args[])
	{
		File aFile = new File();
		Editor editor = new Editor(aFile);
		AutoSaver autoSaver = new AutoSaver(aFile);
		Thread thread1 = new Thread(editor);
		Thread thread2 = new Thread(autoSaver);
		thread1.start();
		thread2.start();
		try
		{
			Thread.sleep(10000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		thread1.interrupt();
		editor.setDone();
		thread2.interrupt();
		autoSaver.setDone();
	}
}