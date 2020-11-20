package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class EnvoiThread extends Thread{

	private PrintStream socOut ;
	
	private BufferedReader stdIn;
	
	public EnvoiThread (Socket clientSocket) {
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
				socOut.println(line);
				System.out.println("me : "+line+"\r\n");
			}
		}catch(Exception e) {
	    	System.err.println("EnvoiThread:" + e);
	    }

	}
	
}
