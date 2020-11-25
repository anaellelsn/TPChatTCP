package stream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultiThreadedChatServer extends Thread {

	private ServerSocket connectionSocket;
		
	private List<ClientThread> threadsClientConnectes;

	public MultiThreadedChatServer(String port) {
		threadsClientConnectes = new ArrayList<ClientThread> ();
		
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
				
				ClientThread ct = new ClientThread(clientSocket,this);
				ct.start();
				threadsClientConnectes.add(ct);
				try {
				     	File myObj = new File("history.txt");
				        if (myObj.createNewFile()) {
				        	System.out.println("File created: " + myObj.getName());
				        } else {
				        	System.out.println("File already exists.");
				        }
				        File file = new File("history.txt"); 
				        
				        BufferedReader br = new BufferedReader(new FileReader(file)); 
				        
				        String st; 
				        while ((st = br.readLine()) != null) {
				    	    ct.envoyer(st);
				        }
				    } catch (IOException e) {
				      System.out.println("An error occurred.");
				      e.printStackTrace();
				    }
			}
	    } catch (Exception e) {
	        
	    }
	}
	
	public void redirigerMessage(String message, ClientThread client) {
		for(ClientThread c : threadsClientConnectes) {
			if(client.getId()!=c.getId()) {
				
				message = client.definePseudo(message);
				
				System.out.println("redirection de "+client.getId()+" a� "+c.getId()+" : "+message);
				
				//c.envoyer("Utilisateur "+c.getId()+" : "+message);

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				c.envoyer(dateFormat.format(new Date())+" - "+ client.getPseudo()+" : "+message);
				//this.history(dateFormat.format(new Date()) + " - Utilisateur "+c.getId()+" : "+message+ System.getProperty("line.separator"), "history.txt");
				this.history(dateFormat.format(new Date()) + " - "+client.getPseudo()+" : "+message+ System.getProperty("line.separator"), "history.txt");

			}
		}
		
	}
	
	public void history(String message, String fileName) {
		try {  
            // Open given file in append mode. 
            BufferedWriter out = new BufferedWriter( 
                   new FileWriter(fileName, true)); 
            out.write(message); 
            out.close(); 
        } 
        catch (IOException e) { 
            System.out.println("exception occured" + e); 
        } 
	}
	

	
	
	
	public void close () {
		
	}
}
