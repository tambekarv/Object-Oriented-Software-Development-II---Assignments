package edu.umb.cs.cs681;

public class Car 
{
	private int price;
	private int year;
	private float milage;

	public Car(int price, int year, float milage)
	{
		this.price = price;
		this.year = year;
		this.milage = milage;

	}
	public int getPrice()
	{
		return price;
	}

	public int getYear()
	{
		return year;
	}
	public float getMilage()
	{
		return milage;
	}

}
