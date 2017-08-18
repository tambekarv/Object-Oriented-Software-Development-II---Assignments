package edu.umb.cs.cs681;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {

	private ArrayList<Observer> observers;
	private boolean objectChanged = false;
	private ReentrantLock lockObs = new ReentrantLock();

	public Observable()
	{
		observers = new ArrayList<Observer>();
	}

	public void addObserver(Observer o)
	{
		lockObs.lock();
		try{
			if(!observers.contains(o))
			{
				observers.add(Objects.requireNonNull(o));
			}
			else
			{
				System.out.println("This Observer is already added");
			}
		}finally{
			lockObs.unlock();
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
		ArrayList<Observer> arrLocal;
		lockObs.lock();
		try{
			if(!hasChanged())
			{
				return;
			}
			arrLocal = observers;
			clearChanged();
		}finally{
			lockObs.unlock();
		}
		
		for(int i = 0; i<arrLocal.size(); i++)
		{
			arrLocal.get(i).update(this, obj); //This is OPEN CALL
		}
		clearChanged();
	}
}