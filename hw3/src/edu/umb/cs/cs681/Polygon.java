package edu.umb.cs.cs681;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

public class Polygon 
{
	static ArrayList<Point> points;
	static AreaCalculator areaCalc;
	 
	public Polygon(ArrayList<Point> points, AreaCalculator areaCalc)
	{
		this.points = Objects.requireNonNull(points);
		this.areaCalc = Objects.requireNonNull(areaCalc);
	}
	
	
	public Polygon(ArrayList<Point> points)
	{
		//if(points.size()==3){
				this(points,(Polygon p)->{
				double side1 = (double) Math.sqrt((Math.pow(points.get(0).getX()-points.get(1).getX(),2))+(Math.pow(points.get(0).getY()-points.get(1).getY(), 2)));
				double side2 = (double) Math.sqrt((Math.pow(points.get(1).getX()-points.get(2).getX(),2))+(Math.pow(points.get(1).getY()-points.get(2).getY(), 2)));
				double side3 = (double) Math.sqrt((Math.pow(points.get(0).getX()-points.get(2).getX(),2))+(Math.pow(points.get(0).getY()-points.get(2).getY(), 2)));
						
				double S = (side1+side2+side3)/2;
			    return (float) Math.sqrt(S*(S-side1)*(S-side2)*(S-side3));
			});			
			
	}
	public void setAreaCalc(AreaCalculator calc)
	{
		areaCalc = Objects.requireNonNull(calc);
	}
	public void addPoint(Point point)
	{
		this.points.add(point);
	}
	
	public ArrayList<Point> getPoints()
	{
		return this.points;
	}
	
	public float getArea()
	{
		return	areaCalc.getArea(this);
	}
}