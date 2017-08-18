package edu.umb.cs.cs681;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class LFU extends FileCache {
	private static LFU instance = null;
	private AccessCounter access = AccessCounter.getInstance();
	private LFU(){}
	
	public static LFU getInstance()
	{
		if(instance == null)
		{
			instance = new LFU();
		}
		return instance;
	}
	
	@Override
	public void replace(LinkedHashMap<Path, String> cache, File targetFile) {
		Path path = targetFile.toPath();
		String file = targetFile.toString();
		
		Set<Path> keys = cache.keySet();
		ArrayList<Path> keysList = new ArrayList<Path>(keys);
		Collections.sort(keysList,(Path p1,Path p2)->access.getCount(p1)-access.getCount(p2));
		for(Path key:keys)
		{
			if(key==keysList.get(1))
			{
				cache.remove(key);
				cache.put(path, file);
			}
		}
		
		System.out.println("Cache Replaced with LFU");
		System.out.println(keysList.get(1)+" is replaced by" +path);	
	}

	@Override
	public String fileFetch(String targetFile) throws FileNotFoundException {
		super.fetch(targetFile);
		return targetFile;
	}
}
