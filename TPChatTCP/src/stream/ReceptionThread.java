package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReceptionThread extends Thread{
	
	private BufferedReader socIn ;
	
	private final SimpleStringProperty content = new SimpleStringProperty(this, "content"); // NOI18N.
    
    public final String getContent() {
        return content.get();
    }
            
    public final void setContent(final String value) {
        content.set(value);
    }
            
    public final StringProperty contentProperty() {
        return content;
    }

	
	public ReceptionThread(Socket clientSocket) {
		setContent("");
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
				setContent(line +"\r\n");
				
				System.out.println(line);
			}
			
		}catch(Exception e) {
	    	System.err.println("ReceptionThread:" + e);
	    }
	}
	

	
}
