package edu.umb.cs.cs681;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FIFO extends FileCache 
{
	private ReentrantLock lock = new ReentrantLock();
	private static FIFO instance = null;
	private String targetFile;
	private FIFO(){}

	public static FIFO getInstance()
	{
		if(instance == null) 
		{
			instance = new FIFO();
		}
		return instance;
	}

	@Override
	public void replace(LinkedHashMap<Path, String> cache, File targetFile) {
				
		Path path = targetFile.toPath();
		String file = targetFile.toString();
		Object key  = cache.keySet().iterator().next();
		cache.remove(key);
		cache.put(path, file);
		System.out.println("cache replaced");
		System.out.println(key+" is replaced by " +path);
	
	}

	@Override
	public String fileFetch(String targetFile)  throws FileNotFoundException
	{
		super.fetch(targetFile);
		return targetFile;
	}
}