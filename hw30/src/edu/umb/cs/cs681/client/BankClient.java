
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.io.*;

public class BankClient{
	private static final int BANKPORT = 8888;

	private Scanner in;
	private PrintWriter out;
	public static final ThreadLocal<StringBuffer> local = ThreadLocal.<StringBuffer>withInitial(() -> 
	{
		return new StringBuffer(0).append("---Client Log---\n");
	});

	public void init(){
		try{
			try(Socket socket = new Socket( "localhost", BANKPORT )){

				System.out.println( "Socket created on the local port " +
						socket.getLocalPort() );
				System.out.println( "A connection established with the remote port " +
						socket.getPort() + " at " +
						socket.getInetAddress().toString() );

				in = new Scanner( socket.getInputStream() );
				out = new PrintWriter( socket.getOutputStream() );
				System.out.println( "I/O setup done." );
				sendCommands();
			}
		}catch(IOException exception){}
	}

	public void sendCommands(){
		sendCommand( "BALANCE\n" );
		System.out.println( getResponse() );

		sendCommand( "DEPOSIT 100\n" );
		System.out.println( getResponse() );

		sendCommand( "WITHDRAW 50\n" );
		System.out.println( getResponse() );
	
		sendCommand( "TERMINATE\n");
		System.out.println(getResponse());
		
		sendCommand( "QUIT\n" );		
	}

	public void sendCommand( String command ){
		System.out.print( "Sending " + command );

		String timeStamp = new SimpleDateFormat("HH:mm:ss_MM/dd/yyyy ").format(Calendar.getInstance().getTime());

		local.get().append(timeStamp);
		local.get().append(command);

		out.print( command );
		out.flush();
		if(command.equals("QUIT\n"))
		{
			System.out.println( "A connection closed." );
			System.out.println(local.get());
		}
	}

	public String getResponse(){
		return in.nextLine();
	}

	public static void main(String[] args){
		BankClient client = new BankClient();
		client.init();
	}
}
