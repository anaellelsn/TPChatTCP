package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceptionThread extends Thread{
	
	private BufferedReader socIn ;
	
	/**
	 * Creates a thread to receive messages using the client socket
	 * @param clientSocket
	 */
	
	public ReceptionThread(Socket clientSocket) {
		try {
			this.socIn = new BufferedReader(
			          new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			String line;
			while(true) {
				line=socIn.readLine();
				System.out.println(line);
			}
			
		}catch(Exception e) {
	    	System.err.println("ReceptionThread:" + e);
	    }
	}
}
