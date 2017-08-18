package edu.umb.cs.cs681;

public class File extends FSElement {

	public File(Directory parent, String name, String owner, int size) 
	{
		super(parent, name, owner, size, true);
	}

}
