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


		Polygon p = new Polygon(al,(ArrayList<Point>  points)->{
			double side1 = (double) Math.sqrt((Math.pow(points.get(0).getX()-points.get(1).getX(),2))+(Math.pow(points.get(0).getY()-points.get(1).getY(), 2)));
			double side2 = (double) Math.sqrt((Math.pow(points.get(1).getX()-points.get(2).getX(),2))+(Math.pow(points.get(1).getY()-points.get(2).getY(), 2)));
			double side3 = (double) Math.sqrt((Math.pow(points.get(0).getX()-points.get(2).getX(),2))+(Math.pow(points.get(0).getY()-points.get(2).getY(), 2)));

			double S = (side1+side2+side3)/2;
			return (float) Math.sqrt(S*(S-side1)*(S-side2)*(S-side3));
		});

		System.out.println("Area of traingle "+p.getArea());

		p.addPoint(new Point(5,2));
		p.setAreaCalc((ArrayList<Point>points)->{
			double width = (double) Math.sqrt((Math.pow(points.get(0).getX()-points.get(1).getX(),2))+(Math.pow(points.get(0).getY()-points.get(1).getY(), 2)));	
			double height = (double) Math.sqrt((Math.pow(points.get(1).getX()-points.get(2).getX(),2))+(Math.pow(points.get(1).getY()-points.get(2).getY(), 2)));

			return (float) (width * height);
		});

		System.out.println("Area of rectangle "+p.getArea());

	}
}
