package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2{
	private double balance = 0;
	private ReentrantLock lock;
	private Condition sufficientFundsCondition;
	private Condition belowUpperLimitFundsCondition;
	private ThreadSafeBankAccount2 account;

	public ThreadSafeBankAccount2(){
		lock = new ReentrantLock();
		sufficientFundsCondition = lock.newCondition();
		belowUpperLimitFundsCondition = lock.newCondition();
		account =  this;
	}

	public ReentrantLock getLock()
	{
		return this.lock;
	}

	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (d): current balance: " + balance);

			balance += amount;
			System.out.println(Thread.currentThread().getId() + 
					" (d): new balance: " + balance);
		}finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);

			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);

		}finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void transfer(ThreadSafeBankAccount2 source,ThreadSafeBankAccount2 destination, double amount) throws InterruptedException
	{
		Random random = new Random();
		while(true){
			if( source.getLock().tryLock() ){
				try{
					if( destination.getLock().tryLock() ){
						try{
							if( balance < amount )
							{
								System.out.println("Insufficient funds to make this transfer");
							}
								else{
									source.withdraw(amount);
									destination.deposit(amount);
								}
							return;
						}finally{
							destination.getLock().unlock();
						}
					}
				}finally{
					source.getLock().unlock();
				}
			}
			Thread.sleep(random.nextInt(1000));
		}

	}

	public static void main(String[] args) throws InterruptedException{
		ThreadSafeBankAccount2 bankAccount1 = new ThreadSafeBankAccount2();
		ThreadSafeBankAccount2 bankAccount2 = new ThreadSafeBankAccount2();

		Thread t1 = null;
		for(int i = 0; i < 5; i++){
			t1 = new Thread( bankAccount1.new DepositRunnable() );
			t1.start();
		}
		Thread t2 =	new Thread( bankAccount1.new WithdrawRunnable() );
		t2.start();
		try{
			Thread.sleep(5000);
		}catch (InterruptedException ie) {}
		t1.interrupt();
		t2.interrupt();

		System.out.println("\nThis is transfer");
		bankAccount1.transfer(bankAccount1,bankAccount2,100);
		System.out.println("amount transfered");
	}

	private class DepositRunnable implements Runnable{

		public void run(){
			try{
				for(int i = 0; i < 10; i++){
					account.deposit(100);
					Thread.sleep(2);
				}
			}
			catch(InterruptedException exception){}
		}
	}

	private class WithdrawRunnable implements Runnable{

		public void run(){
			try{
				for(int i = 0; i < 10; i++){
					account.withdraw(100);
					Thread.sleep(2);
				}
			}
			catch(InterruptedException exception){}
		}


	}
}
