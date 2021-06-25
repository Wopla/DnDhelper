package app.popUp;

import app.FxMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewSave extends Stage{
	
	private TextField nomsave;
	private FxMain main;
	
	public NewSave(FxMain m) {
		main = m;
		nomsave = new TextField();
		
		Button save = new Button("sauvegarder");
		save.setOnAction(nameNClose());
		
		BorderPane warpButton = new BorderPane();		
		warpButton.setCenter(save);
		
		GridPane grille = new GridPane();
		grille.add(nomsave, 0, 0);
		grille.add(warpButton, 0, 1);
		grille.setVgap(20);
		grille.setAlignment(Pos.CENTER);
		
		BorderPane content = new BorderPane();
		content.setCenter(grille);

		setScene(new Scene(content,180,100));
		
        Image logo = new Image(getClass().getResource("/DnD_logo.jpg").toString());
        getIcons().add(logo);
	}
	
    private EventHandler<ActionEvent> nameNClose(){
    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String nom = nomsave.getText();
            	main.addNewSave(nom);
            	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            }
        };
    }
   
}
