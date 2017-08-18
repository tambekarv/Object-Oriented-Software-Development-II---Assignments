package edu.umb.cs.cs681;
import java.awt.Point;
import java.util.ArrayList;

public class Main {
	public static void main(String args[])
	{
		ArrayList<Point> al = new ArrayList<Point>();
		al.add(new Point(2,2));
		al.add(new Point(2,5));
		al.add(new Point(5,5));

		Polygon p = new Polygon(al);
		System.out.println("Area of triangle "+p.getArea()); //=> Triangle
		p.addPoint(new Point(5,2));
		p.setAreaCalc((Polygon p1)->{
			double width = (double) Math.sqrt((Math.pow(p1.getPoints().get(0).getX()-p1.getPoints().get(1).getX(),2))+(Math.pow(p1.getPoints().get(0).getY()-p1.getPoints().get(1).getY(), 2)));	
			double height = (double) Math.sqrt((Math.pow(p1.getPoints().get(1).getX()-p1.getPoints().get(2).getX(),2))+(Math.pow(p1.getPoints().get(1).getY()-p1.getPoints().get(2).getY(), 2)));
			return (float) (width * height);
		});
		System.out.println("Area of rectangle "+p.getArea());
	}
}
