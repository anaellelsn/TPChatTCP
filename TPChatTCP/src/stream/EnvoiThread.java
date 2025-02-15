package stream;

/**
 * @author Anaelle Lesne & Agathe Liguori
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class EnvoiThread extends Thread{
	
	private String pseudo;

	private PrintStream socOut ;
	
	private BufferedReader stdIn;
	
	/**
	 * Creates a thread to send messages
	 * @param pseudo
	 * @param clientSocket
	 */
	public EnvoiThread (String pseudo,Socket clientSocket) {
		this.pseudo=pseudo;
		try {
			this.socOut= new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		stdIn = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void run() {
		try {
			String line;
			while(true) {
				line=stdIn.readLine();
				if(line!=null) {
					socOut.println(pseudo+"|"+line);
					System.out.println("moi : "+line+"\r\n");
				}
				
			}
		}catch(Exception e) {
	    	System.err.println("EnvoiThread:" + e);
	    }

	}
	
}
