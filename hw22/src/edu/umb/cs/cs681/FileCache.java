package edu.umb.cs.cs681;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class FileCache 
{
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static ReentrantLock lock = new ReentrantLock();
	private LinkedHashMap<Path, String> cache;

	public FileCache() 
	{
		cache = new LinkedHashMap<Path, String>(4);
	}

	public String fetch(String targetFile) throws FileNotFoundException
	{	

		File file = new File(targetFile);
		Path path = file.toPath();
		Set<Path> keys = this.cache.keySet();
		if(file.exists())
		{	
			rwLock.writeLock().lock();
			try{
				if(!keys.contains(path))
				{
					if(cache.size() != 4)
					{
						cache.put(path, targetFile);
						System.out.println("File Cached");
					}
					else {
						replace(cache,file);
					}
				}
			}finally{
				rwLock.readLock().lock();
				rwLock.writeLock().unlock();
			}try{
					FiletoString(file);
				}finally
				{
					rwLock.readLock().unlock();
				}
			
			//try{
			
				
			//}finally{
			
				
			//}
			//finally{
				
			//}
		}	
		else
		{
			throw new FileNotFoundException("File does not exist");	
		}
		return targetFile;
	}

	private void FiletoString(File file) throws FileNotFoundException 
	{
		lock.lock();
		try 
		{

			FileInputStream fis = new FileInputStream(file);
			int content;		
			while ((content = fis.read()) != -1) 
			{
				System.out.print((char) content);
			}
			System.out.println("\n");
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("File not available");
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}

	public abstract void replace(LinkedHashMap<Path, String>cache,File targetFile);

	public abstract String fileFetch(String targetFile) throws FileNotFoundException;

}




/*if (keys.contains(path)) 
{
	System.out.println("Cache contains this file");
	//file = this.cache.get(targetFile);
	for(Path key:keys)
	{
		if(key.equals(path))
		{
			String fileContent = cache.get(key);
			file = new File(fileContent);
		}
	}
	FiletoString(file);
}*/

/*else if(cache.size() != 4)
{
	cache.put(path, targetFile);
	System.out.println("File Cached");
	FiletoString(file);
}
else if(cache.size() == 4) 
{
	replace(cache,file);
}*/