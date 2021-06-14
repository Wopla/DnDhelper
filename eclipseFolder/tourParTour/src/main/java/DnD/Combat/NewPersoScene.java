package DnD.Combat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewPersoScene extends Stage{
	
	private App main;
	private TextField[] input;
	private GridPane grille;
	private Button btn;
	private boolean faction,edit=false;
	private int ordre;
	
	public NewPersoScene(App m,Personnage p, int o) {
		edit = true;
		main =m;
		ordre = o;
        init();
        initInput(p);
        if(!p.getFaction())
        	changeBtn();
	}
	
    public NewPersoScene(App m) {
    	edit = false;
    	main =m;
        init();
        initInput();
    }
    
    private void init() {
    	
    	setTitle("Hello World!");
        
        Button btn2 = new Button();
        String nomBtn;
        if(edit)
        	nomBtn ="Changer";
        else
        	nomBtn="Ajouter";
        btn2.setText(nomBtn);
        
        btn = new Button();
        btn.setText("gentil");
        btn.setStyle("-fx-background-color: #66ff99");
        faction = false;

        grille = new GridPane();
        
        input = new TextField[3];
        
        grille.add(new Label("Nom"), 0, 0);
        grille.add(new Label("CA"), 1, 0);
        grille.add(new Label("PV"), 2, 0);
        
        grille.setHgap(5);

        btn.setOnAction(faction());
        btn2.setOnAction(nps());
        
        GridPane grilleWrap = new GridPane();

        BorderPane center2 = new BorderPane();
        center2.setCenter(btn2);
        
        BorderPane center = new BorderPane();
        center.setCenter(btn);
        
        grilleWrap.setAlignment(Pos.CENTER);
        
        grilleWrap.add(center, 0, 1);
        grilleWrap.add(grille, 0, 2);
        grilleWrap.add(center2, 0, 3);
        
        grilleWrap.setVgap(5);
        
        setScene(new Scene(grilleWrap, 300, 250));
    }
    
    private void initInput(Personnage p) {
    	input[0] = new TextField(p.getPN());
    	input[1] = new TextField(""+p.getCA());
    	input[2] = new TextField(""+p.getPV());
    	
        for(int i=0;i<3;i++) {
        	grille.add(input[i], i, 1);
        }
    }

    private void initInput() {
        for(int i=0;i<3;i++) {
        	input[i] = new TextField();
        	grille.add(input[i], i, 1);
        }
    }
    
    private int val(String s) {
    	return App.isNumeric(s) ? Integer.parseInt(input[2].getText()) : 0;
    }
    
    private EventHandler<ActionEvent> nps(){
    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String nom = input[0].getText();
            	int ca = val(input[1].getText());
            	int pv = val(input[2].getText());
            	Personnage p = new Personnage(nom==""?"?":nom, ca, pv,!faction);
            	if(edit)
            		main.editListPerso(p,ordre);
            	else
            		main.addListPerso(p);
            	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            }
        };
    }
    
    private EventHandler<ActionEvent> faction(){
    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	changeBtn();
            }
        };
    }
    
    private void changeBtn() {
    	if(faction) {
            btn.setText("gentil");
            btn.setStyle("-fx-background-color: #66ff99");
            faction = !faction;
    	}
    	else {
            btn.setText("mechant");
            btn.setStyle("-fx-background-color: #FF6699");
            faction = !faction;
    	}
    }
   
}
