package edu.umb.cs.cs681;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Client {
	//private MilageComparator milageComparator;

	public Client(){
		//milageComparator = new MilageComparator();

	}

	public static void main(String args[])
	{
		Client c = new Client();		
		ArrayList<Car> usedCars = new ArrayList<Car>();
		Car c1 = new Car(1000, 1, 20);
		Car c2 = new Car(2000, 3, 15);
		Car c3 = new Car(1500, 2, 18);
		Car c4 = new Car(800, 5, 13);
		Car c5 = new Car(2500, 1, 17);
		
		usedCars.add(c1);
		usedCars.add(c2);
		usedCars.add(c3);
		usedCars.add(c4);
		usedCars.add(c5);
		/*Collections.sort(usedCars, c.milageComparator);
	Iterator<Car> iterator = usedCars.iterator();
	while(iterator.hasNext())
	{
		System.out.println(iterator.next().getMilage());
	}
		 */System.out.println("\nThis is Milage Comparator by Lambda Expression");
		 Collections.sort(usedCars,(Car o1,Car o2)->(int)(o2.getMilage()-o1.getMilage()));
		 usedCars.forEach((car)->System.out.println(car.getMilage()));
		 
		 System.out.println("\nThis is price Comparator by Lambda Expression ");
		 Collections.sort(usedCars,(Car o1, Car o2)->o1.getPrice()-o2.getPrice());
		 usedCars.forEach((car)->System.out.println(car.getPrice()));
		 
		 System.out.println("\nThis is price Comparator in reverse order by Lambda Expression");
		 Collections.sort(usedCars, (Car o1,Car o2)->o2.getPrice()-o1.getPrice());
		 usedCars.forEach((car)->System.out.println(car.getPrice()));
		 
		 System.out.println("\nThis is Year Comparator by Lambda Expression");
		 Collections.sort(usedCars,(Car o1, Car o2)->o1.getYear()-o2.getYear());
		 usedCars.forEach((car)->System.out.println(car.getYear()));
		 //forEach((usedCars)->)
		 //	Collections.sort(usedCars, (Car c1,Car c2)->c1.getMilage()-c2.getMilage());

	}
}
