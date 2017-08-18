package edu.umb.cs.cs681;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public class Casher{
	private Future future = new Future();
	private static ReentrantLock lock = new ReentrantLock();
	public Pizza order(){
		System.out.println("An order is made.");
		Thread t = new Thread(()->{
			RealPizza realPizza = new RealPizza();
			future.setRealPizza( realPizza );
		});
		t.start();
		return future;
	}

	public static void main(String[] args) throws TimeoutException{
		Casher casher = new Casher();
		System.out.println("Ordering pizzas at a casher counter.");
		Pizza p1 = casher.order();
		Pizza p2 = casher.order();
		Pizza p3 = casher.order();

		System.out.println("Doing something, reading newspapers, magazines, etc., " +
				"until pizzas are ready to pick up...");
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e){}

		System.out.println("Let's see if pizzas are ready to pick up...");
		/*System.out.println( p1.getPizza() );
		System.out.println( p2.getPizza() );
		System.out.println( p3.getPizza() );*/
		while(true){
			lock.lock();
			try{
				if( casher.future.isReady())
				{
					System.out.println(p1.getPizza(20)); 
					System.out.println(p2.getPizza(20));
					System.out.println(p3.getPizza(20));
					break; 
				}
			}
			finally{
				lock.unlock();
			}
			System.out.println("Doing something");}
	}
}
