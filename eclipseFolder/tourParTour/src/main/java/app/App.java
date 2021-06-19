package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.popUp.NewPersoScene;
import app.popUp.NewSave;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
 
public class App extends Application {
	
    private DataFormat dataFormat;
	private List<Personnage> listPerso;
	private SaveListPerso listSavedPerso;
	private Ecouteur ecouteur;
	private GridPane grilleScroll,grilleTurn;
	public static boolean ctrlHover,ctrlH2,isDragging;
	public static Scene s;
	private int pos;
	private MenuBar menuBar;
	private VBox vb;
	private GridPane grille;
	
    public void pied(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	pos =-1;
    	ctrlHover = false;
    	ctrlH2 = false;
    	isDragging = false;
    	ecouteur = new Ecouteur(this);
    	listPerso = new ArrayList<Personnage>();
    	dataFormat = new DataFormat("DragDropFormat");
        primaryStage.setTitle("DnD!");
        Button btn = new Button();
        btn.setText("new char");
        btn.setOnAction(ecouteur.addPerso());

        Button btn2 = new Button();
        btn2.setText("ordre");
        btn2.setOnAction(ecouteur.order(listPerso));
        
        Button btn3 = new Button();
        btn3.setText("next");
        btn3.setOnAction(ecouteur.next());
        
        grille = new GridPane();
        GridPane gridBtnBottom = new GridPane();
        grilleScroll = new GridPane();
        grilleTurn = new GridPane();
        ScrollPane scroll = new ScrollPane();
        BorderPane grilleWrap = new BorderPane();
        BorderPane boutonWarp = new BorderPane();
        BorderPane boutonWarp2 = new BorderPane();
        gridBtnBottom.add(btn2, 0, 0);
        gridBtnBottom.add(btn3, 1, 0);
        gridBtnBottom.setHgap(80);
        gridBtnBottom.setAlignment(Pos.CENTER);
        boutonWarp.setCenter(btn);
        boutonWarp2.setCenter(gridBtnBottom);
        grilleWrap.setCenter(grilleScroll);
        
        scroll.setContent(grilleWrap);
      
        Label l = new Label("Joueur Actuel :");
        grilleTurn.add(l, 0, 0);
        grilleTurn.add(new Label(""), 0, 1);
        l.setTextAlignment(TextAlignment.CENTER);
        grilleTurn.setAlignment(Pos.CENTER);
        grilleTurn.setVgap(5);
        
        grille.add(boutonWarp, 0, 0);
        grille.add(grilleTurn, 0, 1);
        grille.add(scroll, 0, 2);
        grille.add(boutonWarp2, 0, 3);
        
        scroll.setMinWidth(380);
        scroll.setMaxWidth(380);
        scroll.setMinHeight(360);
        scroll.setMaxHeight(500);

        scroll.setFocusTraversable(true);
        scroll.setFitToWidth(true);
        //scroll.setFocusTraversable(true);
        grille.setVgap(10);
        grilleScroll.setVgap(3);
        grilleScroll.setMaxWidth(253);
        grilleScroll.setAlignment(Pos.CENTER);
        grille.setAlignment(Pos.CENTER);
        
        vb = new VBox();
        actuMenuBar();

        vb.setSpacing(10);
        
        s = new Scene(vb, 600, 550);
        
        s.setOnKeyPressed((KeyEvent event) -> {
        	if(event.isControlDown())
        		if(ctrlHover)
        			s.setCursor(Cursor.HAND);
        });
        s.setOnKeyReleased((KeyEvent event) -> {
        	if(ctrlHover)
        		if(!ctrlH2)	
        			s.setCursor(Cursor.CLOSED_HAND);
        		else
        			s.setCursor(Cursor.HAND);
        	else
        		s.setCursor(Cursor.DEFAULT);
        });
        
        Image logo = new Image("ressource/DnD_logo.jpg");
        
        primaryStage.getIcons().add(logo);
        primaryStage.setScene(s);
        primaryStage.show();
    }

	private void actuMenuBar() {
		menuBar = new MenuBar();
        Menu menu1 = new Menu("Gestion");
        Menu menu2 = new Menu("Aide");
        MenuItem subMenu1_1 = new MenuItem("sauvegarder");
		Menu subMenu1_2 = new Menu("charger");
        menu1.getItems().addAll(subMenu1_1,subMenu1_2);
        //listSavedPerso = null;
        
        lireSave();
        
        System.out.println("au read de actuMenuBar");
        //System.out.println(listSavedPerso);
        if(listSavedPerso!=null)
        	for(String s: listSavedPerso.getSaves())
        		chargeMenu(subMenu1_2,s);
        
        subMenu1_1.setOnAction(event -> {
        	NewSave s = new NewSave(this);
        	s.show();
        });

        menuBar.getMenus().addAll(menu1,menu2);
        vb.getChildren().clear();
        vb.getChildren().addAll(menuBar,grille);
	}
	
	private void chargeMenu(Menu m,String s) {
		MenuItem charge = new MenuItem("charger");
		MenuItem supp = new MenuItem("supprimer");
		
		supp.setOnAction(event -> {
			listSavedPerso.delListSave(s);
			ecrireSave();
			actuMenuBar();
        });
		
		charge.setOnAction(event -> {
			List<Personnage> temp = new ArrayList<Personnage>();
			
			for(SavePerso sp : listSavedPerso.getSavePerso(s))
				temp.add(new Personnage(sp.getNom(), sp.getCa() , sp.getPv(), sp.isFaction(),sp.isDisable(),sp.getIni()));
				
			listPerso = temp;
			
			ecrireSave();
			actuMenuBar();
			resetGraph();
        });
		
		Menu save = new Menu(s);
		save.getItems().addAll(charge,supp);
		m.getItems().add(save);
	}
	
	private void lireSave() {
		 try {
	        	File file = new File(this.getClass().getResource("/ressource/save.ser").toURI());
	        	FileInputStream fileIn = new FileInputStream(file);
	        	if(fileIn.available()!=0) {
		        	ObjectInputStream in = new ObjectInputStream(fileIn);
		        	SaveListPerso list = (SaveListPerso) in.readObject();
		        	if(list==null)
		        		System.out.println("LISTE EST NULLE");
		        	listSavedPerso = list;
		        	in.close();
	        	}
	        	fileIn.close();
	        }catch(IOException | URISyntaxException | ClassNotFoundException i) {
	           i.printStackTrace();
	        }
	}
	
	private void ecrireSave() {
		try {
         	File file = new File(this.getClass().getResource("/ressource/save.ser").toURI());
         	FileOutputStream fileOut = new FileOutputStream(file);
         	ObjectOutputStream out = new ObjectOutputStream(fileOut);
         	out.writeObject(listSavedPerso);
         	out.close();
         	fileOut.close();
         	System.out.println("save on ressource/save.txt");
         }catch(IOException | URISyntaxException i) {
            i.printStackTrace();
         }
	}
	
	public void addNewSave(String nom) {
     	ArrayList<SavePerso> savedList = new ArrayList<SavePerso>();
		
     	for(Personnage p : listPerso)
     		savedList.add(new SavePerso(p.getPN(),p.getCA(),p.getPV(),p.getIni(),p.getFaction(),p.getDisabled()));
     	
     	if(listSavedPerso == null)
     		listSavedPerso = new SaveListPerso();
     	
     	listSavedPerso.addListSave(nom, savedList);
		
		ecrireSave();
		actuMenuBar();
	}

	
	public void addListPerso(Personnage p) {
		listPerso.add(p);
		resetGraph();
	}
	
	public void editListPerso(Personnage p,int ordre) {
		Personnage old =listPerso.get(ordre);
		Collections.replaceAll(listPerso, old, p);
		
		resetGraph();
	}
	
	private void delListPerso(Personnage p) {
		listPerso.remove(p);
		 resetGraph();
	}
	
	public void sortList(List<Personnage> ps) {
		listPerso = ps;
		resetGraph();
	}
	
	private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
	
	private void resetGraph() {
		if(getRowCount(grilleTurn)>1)
			grilleTurn.getChildren().remove(1);
		grilleScroll.getChildren().clear();
		for(int i=0;i<listPerso.size();i++)
			if(i==0)
				ajouteCurrentG(listPerso.get(i));
			else
				ajouteGraph(listPerso.get(i));
	}
	
	public void next() {
		if(listPerso.size()>0) {
			Personnage p = listPerso.get(0);
			listPerso.remove(0);
			listPerso.add(p);
			if(listPerso.get(0).getDisabled())
				next();
			resetGraph();
		}
	}
	
	public void ajouteCurrentG(Personnage p){
		grilleTurn.add(p, 0, 1);
		Personnage currentP = (Personnage) grilleTurn.getChildren().get(1);
		persoEvent(currentP,0);
	}
	
	public void ajouteGraph(Personnage p){
		int n = getRowCount(grilleScroll);
		grilleScroll.add(p, 0, n);
		//grilleScroll.getChildren().get(n).isHover();
		Personnage currentP = (Personnage) grilleScroll.getChildren().get(n);
		persoEvent(currentP,n+1);
	}
	
	private void persoEvent(Personnage currentP,int n) {
		currentP.getDel().setOnMouseClicked( event ->  {
			delListPerso(currentP);
		});
		
		currentP.getInfoPane().setOnMouseClicked( event ->  {
			if(event.isControlDown())
				currentP.disableHover();
		});
		currentP.getEdit().setOnMouseClicked( event ->  {
			NewPersoScene nps = new NewPersoScene(this,currentP,n);
            nps.show();
		});
		
		currentP.getInfoPane().setOnDragDetected(event -> {
			currentP.setIsDragging(false);
			Dragboard db = currentP.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
            content.put(dataFormat,n); // normally, ID of node
            db.setContent(content);
            event.consume();
        });
		
		currentP.getInfoPane().setOnDragDropped(event -> {
		 	int index = (Integer) event.getDragboard().getContent(dataFormat);
		 	
		 	Personnage move = listPerso.get(index);
		 	
		 	 if(index != pos) {
	             listPerso.remove(move);
	             listPerso.add(n,move);
		 	 }
             else
            	 System.err.println("Drag sur lui meme la!");
		 	 move.setIsDragging(true);
             event.setDropCompleted(true);
             event.consume();
             
             resetGraph();
		});
		
		currentP.setOnDragOver(event -> {
			pos = n;
			event.acceptTransferModes(TransferMode.ANY);
			event.consume();
        });
		
	}

	public static boolean isNumeric(String s) {
		try {
		  Integer.parseInt(s);
		} catch (NumberFormatException e) {
		    return false;
		}
		return true;
	}

	
}