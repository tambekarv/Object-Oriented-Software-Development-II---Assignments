package edu.umb.cs.cs681;
import java.util.ArrayList;

public class Directory extends FSElement
{
	private ArrayList<FSElement> children;
	public Directory(Directory parent, String name, String owner) 
	{
		super(parent, name, owner, 0, false);
		children = new ArrayList<FSElement>();
	}

	public ArrayList<FSElement> getChildren()
	{
		return this.children;
	}
	public void appendChild(FSElement child)
	{
		children.add(child);
	}
}
