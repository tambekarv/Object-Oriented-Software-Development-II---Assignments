package edu.umb.cs.cs681;

public class Car {
	
	private int price;
	private String maker; 

	public Car(String maker,int price)
	{
		this.price = price;
		this.maker = maker;
	}

	public void setMaker(String maker)
	{
		this.maker = maker;
	}

	public String getMaker()
	{
		return this.maker;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getPrice()
	{
		return this.price;
	}
}

