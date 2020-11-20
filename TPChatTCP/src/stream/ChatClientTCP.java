package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientTCP 
			extends Thread{

	 private static Socket clientSocket;

	 
	 private EnvoiThread envoiThread;
	
	 private ReceptionThread receptionThread;
	 
	 public ChatClientTCP(String host,String port) {
		
		try {
			clientSocket = new Socket(host,new Integer(port).intValue());

			envoiThread = new EnvoiThread(clientSocket);
			
			receptionThread = new ReceptionThread(clientSocket);
			
			envoiThread.start();
		    receptionThread.start();
			
		} catch (UnknownHostException e) {
			 System.err.println("Don't know about host:" + host);
	         System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
                    + "the connection to:"+ host);
			System.exit(1);
		}
	 }
	 
	 
	 
	 
	 /**
	  *  main method
	  *  accepts a connection, receives a message from client then sends an echo to the client
	  **/
	    public static void main(String[] args) throws IOException {

	        if (args.length != 2) {
	          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
	          System.exit(1);
	        }

	        ChatClientTCP c = new ChatClientTCP(args[0],args[1]);
	        
	        c.start();
  
	    }
	    
	    
	    public void run () {
	    	
	    	try{ 
	    		String line;
			    while (true) {
			       
			    }
		    }catch(Exception e) {
		    	System.err.println("ChatClient:" + e);
		    }
	    }
	    	
	    
	    public void close() {
	    			    
		    try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
}
