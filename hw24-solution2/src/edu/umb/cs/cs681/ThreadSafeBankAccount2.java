package edu.umb.cs.cs681;
import java.util.concurrent.locks.ReentrantLock;

/*import ThreadSafeBankAccount2.DepositRunnable;
import ThreadSafeBankAccount2.WithdrawRunnable;*/

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2{
	private double balance = 0;
	private ReentrantLock lock;
	private Condition sufficientFundsCondition, belowUpperLimitFundsCondition;
	private ThreadSafeBankAccount2 account;

	public ThreadSafeBankAccount2(){
		lock = new ReentrantLock();
		sufficientFundsCondition = lock.newCondition();
		belowUpperLimitFundsCondition = lock.newCondition();
		account =  this;
	}

	public void deposit(double amount) throws InterruptedException{
		//lock.lock();
		if( !lock.tryLock(3, TimeUnit.SECONDS) ){
			throw new InterruptedException();
		}
		else
		{
			try{
				System.out.println("Lock obtained");
				System.out.println(Thread.currentThread().getId() + 
						" (d): current balance: " + balance);
				/*while(balance >= 300){
					System.out.println(Thread.currentThread().getId() + 
							" (d): await(): Balance exceeds the upper limit.");
					belowUpperLimitFundsCondition.await();
				}*/
				balance += amount;
				System.out.println(Thread.currentThread().getId() + 
						" (d): new balance: " + balance);
				//sufficientFundsCondition.signalAll();
			}

			/*catch (InterruptedException exception){
				exception.printStackTrace();
			}*/
			finally{
				lock.unlock();
				System.out.println("Lock released");
			}
		}
	}

	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);
			/*while(balance <= 0){
				System.out.println(Thread.currentThread().getId() + 
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}*/
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);
			//belowUpperLimitFundsCondition.signalAll();
		}/*
		catch (InterruptedException exception){
			exception.printStackTrace();
		}*/
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void transfer(ThreadSafeBankAccount2 destination, double amount) throws InterruptedException{
		lock.lock();
		try{
			if( this.balance < amount )
			{
				throw new InterruptedException();
			}
			else{
				this.withdraw(amount);
				destination.deposit(amount);
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		ThreadSafeBankAccount2 bankAccount2 = new ThreadSafeBankAccount2();
		
		Thread t1 = null;
		for(int i = 0; i < 5; i++){
			t1 = new Thread( bankAccount.new DepositRunnable() );
			t1.start();
			}
	Thread t2 =	new Thread( bankAccount.new WithdrawRunnable() );
	t2.start();
	try{
		Thread.sleep(5000);
	}catch (InterruptedException ie) {}
		t1.interrupt();
		t2.interrupt();

		System.out.println("\nThis is transfer");
		bankAccount.transfer(bankAccount2,100);
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
