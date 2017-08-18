package edu.umb.cs.cs681;
import java.awt.Point;
import java.util.ArrayList;

@FunctionalInterface
public interface AreaCalculator {
	public abstract float getArea(Polygon p);
}
