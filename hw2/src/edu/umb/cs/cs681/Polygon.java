package edu.umb.cs.cs681;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

public class Polygon 
{
	ArrayList<Point> points;
	AreaCalculator areaCalc;
	public Polygon(ArrayList<Point> points, AreaCalculator areaCalc)
	{
		this.points = Objects.requireNonNull(points);
		this.areaCalc = Objects.requireNonNull(areaCalc);
	}
	public void setAreaCalc(AreaCalculator calc)
	{
		areaCalc = Objects.requireNonNull(calc);
	}
	public void addPoint(Point point)
	{
		points.add(point);
	}
	
	public float getArea()
	{
		return	areaCalc.getArea(points);
	}
	
	/*@Override
	public float getArea(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return areaCalc.getArea(points);
	}*/
}