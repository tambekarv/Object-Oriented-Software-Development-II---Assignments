package edu.umb.cs.cs681;
import java.util.ArrayList;

public class FileQueue {
	private ArrayList<File> queue;
	private static FileQueue instance = null;
	private FileQueue()
	{
		queue = new ArrayList<File>();
	}
	public static FileQueue getInstance()
	{
		if(instance == null)
		{
			instance = new FileQueue();
		}
		return instance;
	}
	
	public void put(File file)
	{
		queue.add(file);
	}

	public File get()
	{
		if(!queue.isEmpty()){
			File file = queue.get(0);
			queue.remove(0);
			return file;	
		}
		return null;	
	}
}
