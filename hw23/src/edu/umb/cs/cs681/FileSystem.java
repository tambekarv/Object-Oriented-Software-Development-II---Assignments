package edu.umb.cs.cs681;
import java.util.ArrayList;

public class FileSystem 
{

	private Directory rootDirectory;
	private static FileSystem instance = null;

	private FileSystem(){}


	public static FileSystem getFileSystem()
	{
		if(instance == null) 
		{
			instance = new FileSystem();
		}
		return instance;
	}

	public void setRootDirectory(Directory rootDirectory)
	{
		this.rootDirectory = rootDirectory;
	}


	public void showAllElements()
	{
		if(rootDirectory == null)
		{
			System.out.println("File System is Empty");
		}
		else
		{
			System.out.println("Name  owner  created  modified  size");
			System.out.println("\n");
			display(rootDirectory);
		}
	}


	private void display(FSElement element) 
	{
	
		 if(element instanceof Link)
			{
				Link link = (Link) element;
				System.out.println("Name:"+link.getName()+
						" Owner: "+link.getOwner()+
						" Date Created: "+link.getDateCreated()+
						" Date Modified: "+link.getLastModifiedDate()+
						" Size: "+link.getSize());
					System.out.println("This is link to "+link.getProxy().getName());
			}
		 
		else if(element.isFile() == false)
		{
			Directory directory = (Directory) element;
			System.out.println("Name:"+directory.getName()+
					" Owner: "+directory.getOwner()+
					" Date Created: "+directory.getDateCreated()+
					" Date Modified: "+directory.getLastModifiedDate()+
					" Size: "+directory.getSize());
			ArrayList<FSElement> e = directory.getChildren();	
			for(int i = 0; i< e.size(); i++)
			{
				display(e.get(i));
			}		
		}
		
		else
		{
			System.out.println("Name:"+element.getName()+
					" Owner: "+element.getOwner()+
					" Date Created: "+element.getDateCreated()+
					" Date Modified: "+element.getLastModifiedDate()+
					" Size: "+element.getSize());
		}

	}

	public static void main(String args[])
	{		
		FileCrawler crawler = new FileCrawler();
		FileIndexer indexer = new FileIndexer();
		Thread thread1 = new Thread(crawler);
		Thread thread2 = new Thread(indexer);
		thread1.start();
		thread2.start();
		
		try{
			Thread.sleep(1000);
		}catch (InterruptedException ie) {}
		thread1.interrupt();
		thread2.interrupt();
		crawler.setDone();
		indexer.setDone();
	}

}
