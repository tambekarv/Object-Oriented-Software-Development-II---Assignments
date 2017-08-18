package edu.umb.cs.cs681;
import java.util.Date;
import java.util.ArrayList;

public class FSElement 
{
	private Directory parent;
	private String name;
	private String owner;
	private Date created;
	private Date lastModified;
	private int size;
	private boolean isFile;
	private boolean isDir;
	public FSElement(Directory parent, String name, String owner, int size, boolean isFile)
	{
		this.parent = parent;
		this.name = name;
		this.owner = owner;
		this.size = size;
		this.isFile = isFile;
		this.created = new Date();
		this.lastModified = new Date();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public Date getDateCreated()
	{
		return new Date(this.created.getDate());
	}
	
	public void setLastModifiedDate(Date lastModified)
	{
		this.lastModified = new Date(lastModified.getDate());
	}
	
	public Date getLastModifiedDate(){
		return new Date(this.lastModified.getDate());
	}
	
	public Directory getParent()
	{
		return this.parent;
	}

	public boolean isFile()
	{
		return this.isFile;
	}
	
	public boolean isDir(){
		return this.isDir;
	}
	
	public int getSize()
	{
		if(isFile)
		{
			return this.size;	
		}
		else if(this instanceof Link){
			return this.size;
		}
		else{
			return dirSize(this);
		}
	
	}

	private int dirSize(FSElement fse) 
	{
		if(fse instanceof Directory)
		{
			Directory directory = (Directory) fse ;
			int size = 0;
			ArrayList<FSElement> e = directory.getChildren();
			
			for (int i = 0; i<e.size(); i++)
			{
				size = size + dirSize(e.get(i));
			}
			return size;
		}
		else
		{
			return fse.getSize();
		}
	}
}