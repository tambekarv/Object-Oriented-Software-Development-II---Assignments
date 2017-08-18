package edu.umb.cs.cs681;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.PrintWriter;
import java.io.IOException;

public class BankServer{
	private static final int BANKPORT = 8888;
	ExecutorService executor = Executors.newWorkStealingPool();
	private static CopyOnWriteArrayList<BankAccount> accounts;
	public BankServer()
	{
		accounts = new CopyOnWriteArrayList<>();
	}

	public void init(CopyOnWriteArrayList<BankAccount> accounts) throws InterruptedException, ExecutionException
	{
		try{
			try(ServerSocket serverSocket = new ServerSocket(BANKPORT)){
				System.out.println("Socket created.");

				while(true){	
					System.out.println( "Listening for a connection on the local port " +
							serverSocket.getLocalPort() + "..." );

					Socket socket = serverSocket.accept();
					System.out.println( "\nA connection established with the remote port " + 
							socket.getPort() + " at " +
							socket.getInetAddress().toString() );

					Runnable task = new BankRunnable(socket, accounts);
					executor.execute(task);
				}
			}
		}
		catch(IOException exception){}
	}

	public void executeCommand( Socket socket, CopyOnWriteArrayList<BankAccount> accounts ){
		try{
			try{
				Scanner in = new Scanner( socket.getInputStream() );
				PrintWriter out = new PrintWriter( socket.getOutputStream() );
				System.out.println( "I/O setup done" );

				while(true){

					if( in.hasNext() ){

						String command = in.next();
						if( command.equals("QUIT") ){
							System.out.println( "QUIT: Connection being closed." );
							out.println( "QUIT accepted. Connection being closed." );
							out.close();
							return;
						}
						if(command.equals("TERMINATE"))
						{
							System.out.println("\nExecutor Terminated");
							executor.shutdownNow();	
							out.println("Executor Terminated successfully");
							out.flush();
							in.close();
							out.close();							
							return;
						}
						accessAccount( command, in, out, accounts);
					}
				}

			}	finally{
				socket.close();
				System.out.println( "A connection is closed." );
			}
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}

	private void accessAccount( String command, Scanner in, PrintWriter out, CopyOnWriteArrayList<BankAccount> accounts ){
		double amount;
		int accountNumber;
		if( command.equals("DEPOSIT") )
		{
			amount = in.nextDouble();
			accountNumber = in.nextInt();
			for(int i = 0; i < accounts.size(); i++)
			{
				if(accountNumber == accounts.get(i).getAccountNumber())
				{	
					System.out.println("\nDeposit");
					System.out.println("Account Number "+accounts.get(i).getAccountNumber()+" Current Balance "+accounts.get(i).getBalance());
					System.out.println("Deposit amount "+amount);
					accounts.get(i).deposit( amount );
					System.out.println("Account Number "+accounts.get(i).getAccountNumber()+" DEPOSIT: Current balance: " + accounts.get(i).getBalance() );
					out.println("Account Number "+accounts.get(i).getAccountNumber()+" DEPOSIT Done. Current balance: " + accounts.get(i).getBalance() );
				}
			}
		}
		else if( command.equals("WITHDRAW") )
		{
			amount = in.nextDouble();
			accountNumber = in.nextInt();
			for(int i = 0; i < accounts.size(); i++)
			{
				if(accountNumber == accounts.get(i).getAccountNumber())
				{
					System.out.println("\nWithdraw");
					System.out.println("Account Number "+accounts.get(i).getAccountNumber()+" Current Balance "+accounts.get(i).getBalance());
					System.out.println("Withdraw amount "+amount);
					accounts.get(i).withdraw( amount );
					System.out.println("Account Number "+accounts.get(i).getAccountNumber()+" WITHDRAW: Current balance: " + accounts.get(i).getBalance() );
					out.println("Account Number "+accounts.get(i).getAccountNumber()+" WITHDRAW Done. Current balance: " + accounts.get(i).getBalance() );
				}
			}
		}
		
		else if( command.equals("BALANCE") )
		{ 
			accountNumber = in.nextInt();
			for(int i = 0; i < accounts.size(); i++)
			{
				if(accountNumber == accounts.get(i).getAccountNumber())
				{	
					System.out.println("\nBalance");
					System.out.println( "Account Number "+accounts.get(i).getAccountNumber()+" BALANCE: Current balance: " + accounts.get(i).getBalance() );
					out.println( "BALANCE accepted. Current balance: " + accounts.get(i).getBalance() );
				}
			}
		}

		else if( command.equals("TRANSFER") )
		{
			amount = in.nextDouble();
			int source = in.nextInt();
			int destination = in.nextInt();
			BankAccount SourceAccount = null;
			BankAccount DestinationAccount = null;
			System.out.println("\nTransfer");
			System.out.println("Source Account Number "+source+" Destination Account Number "+destination);
			System.out.println("Transfer amount "+amount);
			
			for(int i = 0; i < accounts.size(); i++)
			{
				if(accounts.get(i).getAccountNumber() == source)
				{
					SourceAccount = accounts.get(i);
				}
				if(accounts.get(i).getAccountNumber() == destination)
				{
					DestinationAccount = accounts.get(i);
				}
			}

			if(SourceAccount.getBalance() > amount)
			{
				System.out.println("Source account balance before transfer "+SourceAccount.getBalance());
				System.out.println("Destination account balance before transfer "+DestinationAccount.getBalance());
				SourceAccount.withdraw(amount);
				DestinationAccount.deposit(amount);
				System.out.println("Source account balance after transfer "+SourceAccount.getBalance());
				System.out.println("Destination account balance after transfer "+DestinationAccount.getBalance());
				out.println("TRANSFER SUCCESSFUL");
			}
			else
			{
				System.out.println("Trasfer failed");
				System.out.println("Insufficient Funds : Current Balance "+SourceAccount.getBalance());
				out.println("TRANSFER FAILED : Insufficient Balance");
			}
		}		
		else
		{
			System.out.println( "Invalid Command" );
			out.println( "Invalid Command. Try another command." );
		}
		out.flush();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException{
		BankServer server = new BankServer();
		BankAccount bankAccount1 = new BankAccount(1230321, 200);
		BankAccount bankAccount2 = new BankAccount(4560654, 500);
		BankAccount bankAccount3 = new BankAccount(7890987, 700);
		accounts.add(bankAccount1);
		accounts.add(bankAccount2);
		accounts.add(bankAccount3);
		server.init(accounts);
	}
}