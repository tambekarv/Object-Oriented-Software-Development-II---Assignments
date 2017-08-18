package edu.umb.cs.cs681;
import java.util.ArrayList;
import java.util.Objects;

public class Observable {

	private ArrayList<Observer> observers;
	//private Object obj;
	//int flag;
	int HashCode = 0;
	private boolean objectChanged = false;

	public Observable()
	{
		observers = new ArrayList<Observer>();
	}

	public void addObserver(Observer o)
	{
		if(!observers.contains(o))
		{
			observers.add(Objects.requireNonNull(o));
		}
		else
		{
			System.out.println("This Observer is already added");
		}
	}

	public void deleteObserver(Observer o)
	{
		observers.remove(o);
	}

	protected void setChanged()
	{
		objectChanged = true;
	}

	protected void clearChanged()
	{
		objectChanged = false;
	}

	public boolean hasChanged()
	{
		return objectChanged;
	}
	public void notifyObservers(Object obj)
	{
		if(!hasChanged())
		{
			return;
		}
		for(int i = 0; i<observers.size(); i++)
		{
			observers.get(i).update(this, obj);
		}
		clearChanged();
	}

	public static void main(String args[])
	{
		Observable observable = new Observable();
		observable.setChanged();
		observable.addObserver((Observable o, Object obj)->{System.out.println(obj);});
		observable.addObserver((Observable o,Object obj)->{System.out.println(obj);});
		observable.notifyObservers("Hello World!");
		observable.setChanged();
		observable.notifyObservers("Hey there");
	}
}