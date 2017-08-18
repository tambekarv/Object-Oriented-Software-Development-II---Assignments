package edu.umb.cs.cs681;
@FunctionalInterface
public interface Observer {
	public abstract void update(Observable obs,Object obj);
}
