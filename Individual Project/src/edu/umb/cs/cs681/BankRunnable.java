package edu.umb.cs.cs681;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class BankRunnable implements Runnable
{
	private BankServer bankServer = new BankServer();
	private Socket socket;
	private CopyOnWriteArrayList<BankAccount> accounts;

	public BankRunnable(Socket socket, CopyOnWriteArrayList<BankAccount> accounts) 
	{
		this.socket = socket;
		this.accounts = accounts;
	}	

	@Override
	public void run() 
	{	
		bankServer.executeCommand(socket, accounts);
	}
}
