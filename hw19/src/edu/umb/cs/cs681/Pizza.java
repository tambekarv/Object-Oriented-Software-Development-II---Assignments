package edu.umb.cs.cs681;
import java.util.concurrent.TimeoutException;

public interface Pizza{
	public abstract String getPizza();
	public abstract String getPizza(long timeout) throws TimeoutException;
}
