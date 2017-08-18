package edu.umb.cs.cs681;
import java.net.Socket;

public class BankRunnable implements Runnable{
	private BankServer bankServer = new BankServer();
	private Socket socket;

	public BankRunnable(Socket socket) 
	{
		this.socket = socket;
	}	

	@Override
	public void run() {
		bankServer.executeCommand(socket);
	}
}
