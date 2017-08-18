package edu.umb.cs.cs681;

public class BankAccount{
	private double balance;
	private int AccountNumber;
	public BankAccount(int AccountNumber, double balance)
	{
		this.AccountNumber = AccountNumber;
		this.balance = balance;
	}
	
	public int getAccountNumber()
	{
		return this.AccountNumber;
	}
	
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
