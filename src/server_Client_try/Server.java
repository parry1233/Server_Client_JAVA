package server_Client_try;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;


public class Server extends Thread
{
	public static final int Listen_port=8887;
	
	public static void main(String[] args)
	{
		Server server=new Server();
		server.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			ServerSocket serverSocket=new ServerSocket(Listen_port);
			System.out.println("[Server]Waiting for connect...");
			Socket socket=serverSocket.accept();
			System.out.println("[Server]Connect Success!!");
			serverSocket.close();
			
			DataInputStream input=new DataInputStream(socket.getInputStream());
			DataOutputStream output =new DataOutputStream(socket.getOutputStream());
			
			Random number=new Random();
			int result_number=number.nextInt(100)+1;
			int tmp;
			
			while(!Client.finished)
			{
				tmp=Integer.valueOf(input.readUTF());
				System.out.println("[Server]Listen number="+tmp);
				if(result_number!=tmp)
				{
					if(result_number>tmp)
					{
						output.writeUTF("[Server]The answer is bigger than the input number");
					}
					else if(result_number<tmp)
					{
						output.writeUTF("[Server]The answer is smaller than the input number");	
					}
				}
				else if(result_number==tmp)
				{
					output.writeUTF("[Server]The answer is correct!");
					break;//jump out from while loop.
				}
				else
				{
					output.writeUTF("[Server]Unknown Error.");
				}
				output.flush();
			}
			//close input, output and socket while answer is correct. 
			input.close();
			output.close();
			socket.close();
			
			
		} 
		catch (BindException e) 
		{
			// TODO: handle bind exception
			System.out.println("Address Already in use. Change a port and try it again.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			// TODO: handle IOexception
			Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE,null,e);
		}
	}
	
}
