package edu.umb.cs.cs681;
public class HelloWorldTest2{
	public static void main(String[] args){
		GreetingRunnable runnable1 = new GreetingRunnable("Hello World");
		GreetingRunnable runnable2 = new GreetingRunnable("Goodbye World");
		runnable1.run();
		runnable2.run();
	}
}