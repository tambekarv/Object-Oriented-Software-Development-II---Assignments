package edu.umb.cs.cs681;
import java.util.ArrayList;
import java.util.Comparator;

public class Client {
 
	public static void main(String args[])
	{
		ArrayList<Car> cars = new ArrayList<Car>();
		Car car1 = new Car("Porche", 25000);
		Car car2 = new Car("Honda", 15000);
		Car car3 = new Car("Tesla", 30000);
		Car car4 = new Car("Lamborghini", 28000);
		
		cars.add(car1);
		cars.add(car2);
		cars.add(car3);
		cars.add(car4);
	
		
		Integer lowestPrice = cars.stream()
				.map((Car car)-> car.getPrice())
				.reduce(0, (result, carPrice)->{
				if(result==0) return carPrice;
				else if(carPrice < result) return carPrice;
				else return result;} );
	
		System.out.println("This is price using reduce()");
		System.out.println(lowestPrice);
		
		Integer price = cars.stream()
				.map((Car car)-> car.getPrice())
				.min(Comparator.comparing(price2-> price2 ))
				.get();

		System.out.println("\nThis is price using min()");
		System.out.println(price);
	}
}
