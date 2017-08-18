package edu.umb.cs.cs681;
public class Editor implements Runnable
{
	private boolean done = false;
	private File aFile;
	
	public Editor(File file)
	{
		this.aFile = file;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			if(done==true)
			{
				System.out.println("File changed");
				break;
			}
			aFile.change();
			aFile.save();
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}	
		}
	}
	public void setDone()
	{
		done  = true;
	}
}
