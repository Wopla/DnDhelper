package app.elements;

import app.App;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectPerso extends ElementWrapper{
	
	private boolean faction;
	
	private Color fill = Color.LIGHTGREY,hover,cNom;
	private TextField iniField;
	private Rectangle infoR, hoverInfo, rNom;
	
	public RectPerso(String np, int ca, int pv, boolean f) {
		
		faction = f;
		
		GridPane grilleInfo = new GridPane();
		
		StackPane sNp = new StackPane();
		Label lNp = new Label(np);
		rNom = new Rectangle(100,25,fill);
		lNp.setMaxWidth(95);
		sNp.getChildren().addAll(new Rectangle(102	,27),rNom,lNp);
		sNp.setAlignment(Pos.CENTER);
		
		GridPane gArmor = new GridPane();
		Label lArmor = new Label(" : "+ca);
		ImageView logoArmor = new ImageView(new Image("ressource/chestplate.png"));
		logoArmor.setFitWidth(30);
		logoArmor.setFitHeight(30);
		gArmor.add(logoArmor, 0, 0);
		gArmor.add(lArmor, 1, 0);
        gArmor.setAlignment(Pos.CENTER);
		
        GridPane gPv = new GridPane();
		Label lPv = new Label(" : "+pv);
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
		grilleInfo.setMaxWidth(270);
		
		infoR = new Rectangle(280, 36,fill);
		hoverInfo = new Rectangle(282, 38,fill);
		
		getChildren().addAll(hoverInfo,infoR,grilleInfo);
		setAlignment(Pos.CENTER);
		setMaxSize(253, 35);
		colorize(true);
	}
	
	private void colorize(boolean b) {
	
		if(b) {
			if(faction) {//Vert
				fill = Color.web("#66ff99");
				hover = Color.web("#006600");
				cNom = Color.LIGHTGREEN;
			}
			else {//Rouge
				fill = Color.web("#FF6699");
				hover = Color.web("#cc0000");
				cNom = Color.LIGHTCORAL;
			}
			infoR.setFill(fill);
			hoverInfo.setFill(fill);
			rNom.setFill(cNom);
		}
		else {
			fill = Color.LIGHTGREY;
			hover = Color.GREY;
			cNom = Color.WHITESMOKE;	
		}

	}

	@Override
	public void setHoverOff() {
		infoR.setFill(fill);
		hoverInfo.setFill(hover);
	}

	@Override
	public void setHoverOn() {
		infoR.setFill(fill);
		hoverInfo.setFill(fill);
	}
	
	public boolean getFaction() {
		return faction;
	}
	
	public void disableHover(boolean b) {
		colorize(b);
		
		infoR.setFill(fill);
		hoverInfo.setFill(hover);
		rNom.setFill(cNom);
		
	}

	public int getIni() {
		int ini = 0;
		if (App.isNumeric(iniField.getText()))
			ini =Integer.parseInt(iniField.getText());
		else
			iniField.setText("0");

		return ini;
	}

	public void setIni(int i) {
		iniField.setText(""+i); 
	}

}
