package edu.umb.cs.cs681;
import java.util.concurrent.TimeoutException;

public class RealPizza implements Pizza{
	private String realPizza;
	
	public RealPizza(){
		try{
			Thread.sleep(10);
		}
		catch(InterruptedException e){}
		System.out.println("A real pizza is made!");
		realPizza = "REAL PIZZA!";
	}

	public String getPizza(){
		return realPizza;
	}

	@Override
	public String getPizza(long timeout) throws TimeoutException {
		// TODO Auto-generated method stub
		return realPizza;
	}
}
