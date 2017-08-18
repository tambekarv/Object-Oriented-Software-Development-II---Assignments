package edu.umb.cs.cs681;

public class BankAccount{
	private double balance = 0;
	
	public double getBalance(){
		return balance;
	}
	
	public double deposit(double amount){
		balance += amount;
		return balance;
	}
	
	public double withdraw(double amount){
		balance -= amount;
		return balance;
	}
	
}
