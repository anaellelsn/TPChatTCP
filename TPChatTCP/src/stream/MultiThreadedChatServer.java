package stream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedChatServer extends Thread {

	private ServerSocket connectionSocket;
	
	//id pour les clients ? plutot faire une hashmap ? avec directement les sockets ? 
	
	private List<ClientThread> threadsClientConnectes;
	
	private int idClients;
	
	public MultiThreadedChatServer(String port) {
		threadsClientConnectes = new ArrayList<ClientThread> ();
		idClients=0;
		try {
			connectionSocket = new ServerSocket(Integer.parseInt(port));
		} catch (Exception e) {
			System.err.println("Error in MultiThreadedChatServer:" + e);
		} 
	}
	
	public static void main(String args[]){ 

	  	if (args.length != 1) {
	          System.out.println("Usage: java EchoServer <EchoServer port>");
	          System.exit(1);
	  	}	
	  	MultiThreadedChatServer server = new MultiThreadedChatServer(args[0]);
	  	server.start();
	
    }
	
	public void run () {
		try {
			 
			System.out.println("Server ready..."); 
			while (true) {
				Socket clientSocket = connectionSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ClientThread ct = new ClientThread(idClients,clientSocket,this);
				ct.start();
				threadsClientConnectes.add(ct);
			}
	    } catch (Exception e) {
	        
	    }
	}
	
	public void redirigerMessage(String message, ClientThread client) {
		for(ClientThread c : threadsClientConnectes) {
			if(client.getId()!=c.getId()) {
				System.out.println("redirection de "+client.getId()+" à "+c.getId()+" : "+message);
				c.envoyer("Utilisateur "+c.getId()+" : "+message);
				
			}
		}
		
	}
	
	
	
	public void close () {
		
	}
}
