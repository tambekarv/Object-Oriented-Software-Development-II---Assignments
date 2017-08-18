package edu.umb.cs.cs681;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TableObserver implements Observer
{
	private Map<String,Float> events;
	
	 public TableObserver() 
	{
		 events = new LinkedHashMap<String,Float>();
	}
	
	@Override
	public void update(Observable observable, Object arg) 
	{
		StockEvent stockevent = (StockEvent) arg;
		events.put(stockevent.getTicker(), stockevent.getQuote());
		draw(stockevent.getTicker(),stockevent.getQuote());
	}

	private void draw(String ticker, float quote) 
	{
		System.out.println("\nAfter Updating Table");
		Set<Entry<String, Float>> keys = events.entrySet();
		for(Entry<String, Float> k:keys)
		{
			System.out.println("Ticker:" +k.getKey()+ " Quote:" +k.getValue());
		}
	}
}