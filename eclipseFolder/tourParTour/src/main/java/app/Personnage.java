package app;

import app.elements.DelButton;
import app.elements.EditButton;
import app.elements.ElementWrapper;
import app.elements.RectPerso;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Personnage extends GridPane implements Comparable<Personnage>{
	
	private String np;
	private int ca,pv;
	
	private boolean disabled=false;
	
	private RectPerso info;
	private DelButton del;
	private EditButton edit;
	
	public boolean isHover, isDragging;
	
	public Personnage(String pn, int c, int p, boolean f, int i) {

		np = pn; //Prenom Nom

		ca = c;//armure
		pv = p;//point de vie
		
		isHover = false;
		isDragging = true;
		
		info = new RectPerso(np,ca,pv,f);
		del = new DelButton();
		edit = new EditButton();

		add(del,0,0);
		add(edit,1,0);
		add(info,2,0);
		setHgap(15);
		setAlignment(Pos.CENTER);
		
		init();
		
		info.setIni(i);
	}

	public Personnage(String nom, int ca2, int pv2, boolean faction, boolean d, int i) {
		this(nom, ca2, pv2, faction,i);
		disabled =d;
		info.disableHover(!disabled);
	}


	private void init() {

		hoverEcouteur(info);
		hoverEcouteur(del);
		hoverEcouteur(edit);
		
		setOnDragEntered(event -> {
			if(isDragging)
				info.setOpacity(0.3);
        });

		setOnDragExited(event -> {
			if(isDragging)
				info.setOpacity(1);
        });
		
	}

	
	private void hoverEcouteur(ElementWrapper e) {
		boolean b = (e != info);
		e.setOnMouseExited( event ->  {
			App.ctrlHover=false;
			if(b)
				App.ctrlH2 = false;
			App.s.setCursor(Cursor.DEFAULT);
			e.setHoverOn();
		});
		
		e.setOnMouseEntered( event ->  {
			App.ctrlHover=true;
			if(b)
				App.ctrlH2 = true;
			if(event.isControlDown() || b)
				App.s.setCursor(Cursor.HAND);
			else
				App.s.setCursor(Cursor.CLOSED_HAND);
			e.setHoverOff();
		});
	}
	
	public int getIni() {
		return info.getIni();
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
		info.disableHover(disabled);
		disabled =! disabled;
	}

	public StackPane getDel() {
		return del;
	}
	
	public StackPane getInfoPane() {
		return info;
	}
	
	public StackPane getEdit() {
		return edit;
	}
	
	public boolean getFaction() {
		return info.getFaction();
	}
	
	public String getPN() {
		return np;
	}
	
	public int getCA() {
		return ca;
	}
	
	public int getPV() {
		return pv;
	}
	
	public void setIsDragging(boolean b) {
		isDragging = b;
	}
	
	public boolean getIsDragging() {
		return isDragging;
	}
}
