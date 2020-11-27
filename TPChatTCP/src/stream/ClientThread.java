/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors: B06 + code from the teachers 
 */

package stream;

/**
 * @author Anaelle Lesne & Agathe Liguori
 */

import java.io.*;
import java.net.*;

public class ClientThread
	extends Thread {
	
	private Socket communicationSocket;
	
	private String pseudo;
	
	private BufferedReader socIn;
	
	private PrintStream socOut;
	
	private MultiThreadedChatServer server;
	
	/**
	 * Creates a thread for the client
	 * @param s
	 * @param server
	 */
	
	ClientThread(Socket s,MultiThreadedChatServer server) {
		this.communicationSocket = s;
		this.pseudo=null;
		this.server=server;
		try{
			socIn = new BufferedReader(
					new InputStreamReader(communicationSocket.getInputStream()));    
		    socOut = new PrintStream(communicationSocket.getOutputStream());
		} catch(Exception e) {
			System.err.println("Error in ClientThread constructor:" + e); 
		}
		
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	*
  	**/
	public void run() {
    	  try {
    		
    		while (true) {
    			//communication et rediffusion des messages aux bons clients 
    			/*
    			 * Format des messages envoyé par les clients 
    			 * "message"
    			 */
    		    String line = socIn.readLine();
    		    if(line!=null) server.redirigerMessage(line, this);
    		    
    		    
    		}
    	} catch (Exception e) {
        	System.err.println("Error in ClientThread run:" + e); 
        }
    }
	
	/**
	 * Sends a message via the out socket
	 * @param msg
	 */
	
	public void envoyer (String msg) {
		socOut.println(msg);
	}
	
	/**
	 * Getter of the attribute pseudo
	 * @return pseudo
	 */
	
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Method to parse the user's name on the chat
	 * @param msg
	 * @return the user's pseudo
	 */
	
	public String definePseudo(String msg) {
		String res; 
		int index = msg.indexOf("|");
		if(pseudo==null) pseudo= msg.substring(0, index);
		res=msg.substring(index+1,msg.length());
		return res;
	}

  
  }