package server_Client_try;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
	private String address="127.0.0.1";
	private int port=8887;
	static boolean finished=false;
	
	public static void main(String[] args)
	{
		Client client=new Client();
	}

	public Client()
	{
		Socket client_socket=new Socket();
		
		InetSocketAddress isa=new InetSocketAddress(this.address, this.port);
		
		DataInputStream input=null;
		DataOutputStream output=null;
		
		try
		{
			client_socket.connect(isa, 10000);
			input=new DataInputStream(client_socket.getInputStream());
			output=new DataOutputStream(client_socket.getOutputStream());
			String s;
			while(!finished)
			{
				Scanner in=new Scanner(System.in);
				System.out.print("[Client]Guess Number:");
				int guess=in.nextInt();
				output.writeUTF(String.valueOf(guess));
				output.flush();
				s=input.readUTF();
				System.out.println(s);
				if(s.equals("[Server]The answer is correct!"))
				{
					System.out.println("End of the program.Server connection closed.");
					break;//jump out from while loop.
				}
			}
			//set the check boolean to true 
			finished=true;
			//close input, output and client socket.
			output.close();
			input.close();
			client_socket.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Socket connection error!");
			System.out.println("IOException: "+e.toString());
		}
	}
}
