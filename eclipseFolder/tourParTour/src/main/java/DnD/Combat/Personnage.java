package DnD.Combat;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class Personnage extends GridPane implements Comparable<Personnage>{
	
	private String np;
	private int stats[];
	TextField iniField;
	
	private ArrayList<StackPane> panes;
	
	private Rectangle hoverInfo;
	private Circle hoverDel,hoverEdit;
	
	private boolean faction,disabled=false;
	
	private Color hover,fill=Color.LIGHTGREY;
	
	public boolean isHover, isDragging;
	
	public Personnage (String pn, int c, int pv, boolean f) {
		super();
		faction = f;
		stats = new int[3];
		np = pn; //Prenom Nom

		stats[0] = c;//armure
		stats[1] = pv;//point de vie
		stats[2] = -1;//initiative
		
		isHover = false;
		isDragging = true;
		panes = new ArrayList<StackPane>();
		StackPane info = new StackPane();
		panes.add(info);
		GridPane grilleInfo = new GridPane();
		
		StackPane sNp = new StackPane();
		Label lNp = new Label(np);
		lNp.setMaxWidth(95);
		sNp.getChildren().addAll(new Rectangle(102	,27),new Rectangle(100,25,Color.LIGHTGREEN),lNp);
		sNp.setAlignment(Pos.CENTER);
		
		GridPane gArmor = new GridPane();
		Label lArmor = new Label(" : "+stats[0]);
		ImageView logoArmor = new ImageView(new Image("ressource/chestplate.png"));
		logoArmor.setFitWidth(30);
		logoArmor.setFitHeight(30);
		gArmor.add(logoArmor, 0, 0);
		gArmor.add(lArmor, 1, 0);
        gArmor.setAlignment(Pos.CENTER);
		
        GridPane gPv = new GridPane();
		Label lPv = new Label(" : "+stats[1]);
		ImageView logoPv = new ImageView(new Image("ressource/heart.png"));
		logoPv.setFitWidth(30);
		logoPv.setFitHeight(30);
		gPv.add(logoPv, 0, 0);
		gPv.add(lPv, 1, 0);
		gPv.setAlignment(Pos.CENTER);
		
		//Label displayL = new Label("; CA:"+stats[0]+"; PV: "+stats[1]+"; Ini :");
		iniField = new TextField();
		iniField.setMaxWidth(35);
		iniField.setMinWidth(34);
		grilleInfo.add(sNp, 0, 0);
		grilleInfo.add(gArmor, 1, 0);
		grilleInfo.add(gPv, 2, 0);
		grilleInfo.add(iniField, 3, 0);
		grilleInfo.setAlignment(Pos.CENTER);
		grilleInfo.setHgap(5);
		
		Rectangle infoR = new Rectangle(250, 36,fill);
		hoverInfo = new Rectangle(252, 38,fill);
		
		Circle delC = new Circle(11,fill);
		hoverDel = new Circle(12,fill);
		
        ImageView logoDel = new ImageView(new Image("ressource/trash.png"));
        logoDel.setFitWidth(28);
        logoDel.setFitHeight(28);
        
		Circle editC = new Circle(11,fill);
		hoverEdit = new Circle(12,fill);
        ImageView logoEdit = new ImageView(new Image("ressource/edit.png"));
        logoEdit.setFitWidth(16);
        logoEdit.setFitHeight(16);
        
		StackPane del = new StackPane();
		panes.add(del);
		StackPane edit = new StackPane();
		panes.add(edit);
		
		del.getChildren().addAll(hoverDel,delC,logoDel);
		edit.getChildren().addAll(hoverEdit,editC,logoEdit);
		info.getChildren().addAll(hoverInfo,infoR,grilleInfo);
		
		//info.getChildren().get(0).setC
		
		del.setAlignment(Pos.CENTER);
		edit.setAlignment(Pos.CENTER);
		info.setAlignment(Pos.CENTER);
		
		info.setMaxSize(253, 35);
		add(del,0,0);
		add(edit,1,0);
		add(info,2,0);
		setHgap(15);
		setAlignment(Pos.CENTER);
		
		init();

		infoR.setFill(fill);
		hoverInfo.setFill(fill);
	}

	private void init() {
		
		initColor(true);

		for(int i=0;i<panes.size();i++)
			hoverEcouteur(panes.get(i),i==0);

		setOnDragEntered(event -> {
			if(isDragging)
				panes.get(0).setOpacity(0.3);
        });

		setOnDragExited(event -> {
			if(isDragging)
			panes.get(0).setOpacity(1);
        });
		
	}
	
	private void initColor(boolean b) {
		if(b)
			if(faction) {
				fill = Color.web("#66ff99");
				hover = Color.web("#006600");
			}
			else {
				fill = Color.web("#FF6699");
				hover = Color.web("#cc0000");
			}
		else
			fill = Color.LIGHTGREY;
			hover = Color.GREY;
		
	}
	
	private void hoverEcouteur(StackPane p,boolean b) {
		p.setOnMouseExited( event ->  {
			App.ctrlHover=false;
			if(!b)
				App.ctrlH2 = false;
			App.s.setCursor(Cursor.DEFAULT);
			changeHover(false,p,b);
		});
		
		p.setOnMouseEntered( event ->  {
			App.ctrlHover=true;
			if(!b)
				App.ctrlH2 = true;
			if(event.isControlDown() || !b)
				App.s.setCursor(Cursor.HAND);
			else
				App.s.setCursor(Cursor.CLOSED_HAND);
			changeHover(true,p,b);
		});
	}
	
	private void changeHover(boolean b,StackPane p,boolean b2) {
		Color c;
		
		if(b2)
			if(!b)
				c = fill;
			else
				c = hover;
		else
			if(!b)
				c = Color.LIGHTGREY;
			else
				c = Color.GREY;
		if(b2)
			((Rectangle) p.getChildren().get(0)).setFill(c);
		else
			((Circle) p.getChildren().get(0)).setFill(c);
	}
	
	public int getIni() {
		stats[2] = 0;
		if (App.isNumeric(iniField.getText()))
			stats[2] =Integer.parseInt(iniField.getText());
		else
			iniField.setText("0");

		return stats[2];
	}
	
	public void setIni(int ini) {
		this.stats[2] = ini;
	}

	public boolean getDisabled() {
		return disabled;
	}
	
	@Override
	public int compareTo(Personnage p) {
		return compare(getIni(),p.getIni());
	}
	
	private int compare(int i1, int i2) {
		return i1 > i2 ? -1 : 1;
	}
	
	public void disableHover() {
		initColor(disabled);
		disabled =! disabled;
		
		StackPane p = panes.get(0);
		
		((Rectangle) p.getChildren().get(0)).setFill(hover);
		((Rectangle) p.getChildren().get(1)).setFill(fill);
	}

	public StackPane getDel() {
		return panes.get(1);
	}
	
	public StackPane getInfoPane() {
		return panes.get(0);
	}
	
	public StackPane getEdit() {
		return panes.get(2);
	}
	public boolean getFaction() {
		return faction;
	}
	
	public String getPN() {
		return np;
	}
	
	public int getCA() {
		return stats[0];
	}
	
	public int getPV() {
		return stats[1];
	}
	
	public void setIsDragging(boolean b) {
		isDragging = b;
	}
	
	public boolean getIsDragging() {
		return isDragging;
	}
}