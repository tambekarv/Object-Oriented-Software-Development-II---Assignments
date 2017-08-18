package edu.umb.cs.cs681;

public class AutoSaver implements Runnable{
	private boolean done = false;
	private File aFile;
	
	public AutoSaver(File file)
	{
		this.aFile = file;
	}
	@Override
	public void run() 
	{
		aFile.save();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) 
		{	
			e.printStackTrace();
		}
	}

	public void setDone()
	{
		done  = true;
	}

}
