package view;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stream.ChatClientTCP;
import stream.MultiThreadedChatServer;
import stream.ReceptionThread;

public class WindowChatFX extends Application {
    
	//private ObservableList<ReceptionThread> listReception = FXCollections.observableArrayList();
	private final ObservableList<ReceptionThread> listReception = FXCollections.observableArrayList(receptionThread -> new ObservableValue[]{receptionThread.contentProperty()});

	
	@Override
    public void start(Stage primaryStage) {
		
		Label label1 = new Label("Pseudo : ");
	    TextField textField = new TextField ();
	    HBox hb = new HBox();
	    hb.getChildren().addAll(label1, textField);
	    hb.setSpacing(10);
		
		
        Button btn = new Button();
        btn.setText("Connect to the Chat");
        btn.setOnAction(e->{
        	BorderPane border = new BorderPane();
            //primaryStage.close(); // you can close the first stage from the beginning
        	String pseudo =  "defaultUsername";
        	if ((textField.getText() != null && !textField.getText().isEmpty())) {
        		pseudo = textField.getText();
        	} 
        	//server on port 8080 -> pour l'instant à lancer en parallèle pour éviter de le lancer à chaque fois qu'un nouveau client veuille se co 
        	//MultiThreadedChatServer server = new MultiThreadedChatServer("8080");
    	  	//server.start();
        	ChatClientTCP c = new ChatClientTCP(pseudo,"127.0.0.1","8080");
        	
        	Label label2 = new Label("Tapez votre message : ");
    	    TextField textField2 = new TextField ();
        	Button btnSend = new Button();
            btnSend.setText("Envoyer message");
            btnSend.setOnAction(e2->{
            	c.getEnvoieThread().envoyerMessage(textField2.getText());
            	//listReception.get(0).setContent("test");
            });
            
            Button btnDeconnection = new Button();
            btnDeconnection.setText("Se déconnecter");
            
            
            HBox hb2 = new HBox();
    	    hb2.getChildren().addAll(label2, textField2,btnSend,btnDeconnection);
        	
        	TextArea textArea = new TextArea();
        	textArea.setEditable(false);
        	textArea.setPrefWidth(620.0);
        	textArea.setPrefHeight(650.0); 
        	
        	 c.start();
        	listReception.add(c.getReceptionThread());
        	
	        
	        listReception.addListener((ListChangeListener.Change<? extends ReceptionThread> change) -> {
	          
	        	while (change.next()) {
                    if (change.wasUpdated()) {
                    	textArea.appendText(listReception.get(0).getContent()); // NOI18N.
                    	System.out.println("update : "+listReception.get(0).getContent());
                    }
                }    
	           
	        });
  
        	
            // create the structure again for the second GUI
            // Note that you CAN use the previous root and scene and just create a new Stage 
            //(of course you need to remove the button first from the root like this, root.getChildren().remove(0); at index 0)
            //StackPane root2 = new StackPane();
            Label label = new Label("Your are now in the chat");
            border.setBottom(hb2);
            VBox vb = new VBox();
            vb.getChildren().addAll(label);
            border.setTop(vb);
            VBox vb2 = new VBox();
            vb2.getChildren().addAll(textArea);
            border.setCenter(vb2);
            
            
            Scene secondScene = new Scene(border, 620,700);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene); // set the scene
            secondStage.setTitle("Chat");
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
            btnDeconnection.setOnAction(e3->{
            	//close window and close threads 
            	
            	StackPane root3 = new StackPane();
                Label label3 = new Label("Your are disconnected from the chat !");
                root3.getChildren().addAll(label3);
                
                
                
                Scene thirdScene = new Scene(root3, 650,800);
                Stage thirdStage = new Stage();
                thirdStage.setScene(secondScene); // set the scene
                thirdStage.setTitle("Chat");
                thirdStage.show();
            	secondStage.close(); 
            	c.close();
            });
        });
        

        
//        hb.setLayoutX(10);
//        hb.setLayoutY(100);

        StackPane root = new StackPane();
        root.getChildren().addAll(hb,btn);
        //root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 150);

        primaryStage.setTitle("Accueil du chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
