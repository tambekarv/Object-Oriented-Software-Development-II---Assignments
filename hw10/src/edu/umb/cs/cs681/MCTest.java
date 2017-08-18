package edu.umb.cs.cs681;
import java.util.ArrayList;

class MCTest {
  public static void main(String[] args) throws Exception {
	  long startTime = System.nanoTime();
	  ArrayList<Thread> threads = new ArrayList<Thread>();
 
    final long nTimes  = Long.parseLong(args[0]);
    final int nThread = Integer.parseInt(args[1]);

    for (int i = 0; i < nThread; i++) 
    {
      Thread t = new Thread(()->{ int n = 25; 
      for(long j =0;j<nTimes;j++)
    	  {
    		  n *= 25;
    	  }
    	 });
     
      threads.add(t);
      t.start();
    }

    for (Thread t: threads) {
      t.join();
    }
    
  long execusionTime = System.nanoTime()-startTime;
  System.out.println("Time needed to execute in nano seconds: "+execusionTime);
  }
}
