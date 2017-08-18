package edu.umb.cs.cs681;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable
{
	private Map<String, Float> events;
	private ReentrantLock lockTQ = new ReentrantLock();

	public StockQuoteObservable()
	{
		events = new LinkedHashMap<String, Float>();
	}

	public void ChangeQuote(String t,float q)
	{
		lockTQ.lock();
		try{
			events.put(t, q);
			setChanged();
			notifyObservers(new StockEvent(t,q));
		}finally{
			lockTQ.unlock();
		}
	}
	public static void main(String args[])
	{
		StockQuoteObservable stockObservable = new StockQuoteObservable();
		stockObservable.addObserver((Observer) new TableObserver());
		stockObservable.addObserver((Observer) new PieChartObserver());
		stockObservable.addObserver((Observer) new ThreeDObserver());
		stockObservable.ChangeQuote("Google",56);
		stockObservable.ChangeQuote("Google", 56.3f);
		stockObservable.ChangeQuote("Apple", 92);
	}
}