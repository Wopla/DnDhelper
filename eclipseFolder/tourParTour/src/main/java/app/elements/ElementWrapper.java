package app.elements;

import javafx.scene.layout.StackPane;

public abstract class ElementWrapper extends StackPane{

	public ElementWrapper() {
		super();
	}
	
	public abstract void setHoverOn();
	
	public abstract void setHoverOff();
	
	//public abstract void setHoverOn();
}
