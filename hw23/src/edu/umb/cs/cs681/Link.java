package edu.umb.cs.cs681;

public class Link extends FSElement 
{
	private FSElement proxy;
	public Link(Directory parent, String name,FSElement proxy, String owner, int size, boolean isFile) 
	{
		super(parent, name, owner, size, false);
		this.proxy = proxy;
	}

	public FSElement getProxy(){
		return this.proxy;
	}
	public int getTargetSize()
	{
		if(proxy instanceof Link)
		{
			return trackProxy((Link) proxy);
		}
		else
			return proxy.getSize();
	}

	private int trackProxy(Link link) 
	{
		if(link.getProxy() instanceof Link)
		{
			return trackProxy((Link)link.getProxy());
		}
		else
			return link.getProxy().getSize();
	}
}
