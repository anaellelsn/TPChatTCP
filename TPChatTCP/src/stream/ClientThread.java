/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors: B06 + code from the teachers 
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientThread
	extends Thread {
	
	private Socket communicationSocket;
	
	private int id;
	
	private BufferedReader socIn;
	
	private PrintStream socOut;
	
	private MultiThreadedChatServer server;
	
	ClientThread(int id,Socket s,MultiThreadedChatServer server) {
		this.communicationSocket = s;
		this.id=id;
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
  	* @param clientSocket the client socket
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
	
	public void envoyer (String msg) {
		socOut.println(msg);
	}

  
  }