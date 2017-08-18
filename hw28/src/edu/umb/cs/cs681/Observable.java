package edu.umb.cs.cs681;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {

	private CopyOnWriteArrayList<Observer> observers;
	private boolean objectChanged = false;
	private ReentrantLock lockObs = new ReentrantLock();

	public Observable()
	{
		observers = new CopyOnWriteArrayList<Observer>();
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
		lockObs.lock();
		try{
			objectChanged = true;
		}finally{
			lockObs.unlock();
		}
	}

	protected void clearChanged()
	{
		lockObs.lock();
		try{
			objectChanged = false;
		}finally{
			lockObs.unlock();
		}
	}

	public boolean hasChanged()
	{
		return objectChanged;
	}

	public void notifyObservers(Object obj)
	{
		lockObs.lock();
		try{
			if(!objectChanged) return;
			objectChanged = false;
		}finally{
			lockObs.unlock();
		}
		Iterator<Observer> it = observers.iterator();
		while( it.hasNext() ){
			((Observer) it.next()).update(this, obj);
		}
		clearChanged();
	}
}