package edu.umb.cs.cs681;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class FileCrawler implements Runnable {

	private FileSystem fs = FileSystem.getFileSystem();
	private Directory root;
	private FileQueue queue = FileQueue.getInstance();
	private static ReentrantLock lock = new ReentrantLock();
	private boolean done = false;

	public void setDone()
	{
		done = true;
	}

	public void setup()
	{
		root = new Directory(null, "root", "Vinit");
		Directory home = new Directory(root,"home","Vinit");
		Directory system = new Directory(root,"system","Vinit");
		Directory pictures = new Directory(home,"pictures","Vinit");
		File a = new File(system, "a", "Vinit", 1);
		File b = new File(system, "b","Vinit", 1);
		File c = new File(system, "c","Vinit",1);
		File d = new File(home, "d","Vinit", 1);
		File e = new File(pictures, "e","Vinit", 1);
		File f = new File(pictures, "f","Vinit", 1);
		Link x = new Link(home, "x", system, "Vinit", 1, false);
		Link y = new Link(pictures, "y", e,"Vinit", 1, false);
		fs.setRootDirectory(root);
		root.appendChild(home);
		root.appendChild(system);
		system.appendChild(a);
		system.appendChild(b);
		system.appendChild(c);
		home.appendChild(pictures);
		home.appendChild(d);
		home.appendChild(x);
		pictures.appendChild(e);
		pictures.appendChild(f);
		pictures.appendChild(y);
		
	}

	@Override
	public void run() 
	{
		lock.lock();
		try{
			while(done==false)
			{
				setup();
				crawl(root);
			}
		}finally{
			lock.unlock();
		}
	}

	private void crawl(Directory dir)
	{
		ArrayList<FSElement> children = dir.getChildren();
		for(int i = 0; i < children.size(); i++)
		{
			if(children.get(i) instanceof Directory)
			{
				crawl((Directory) children.get(i));
			}

			if(children.get(i) instanceof Link)
			{
				return;
			}

			if(children.get(i) instanceof File)
			{
				queue.put((File) children.get(i));
			}
		}
	}
}
