package app.elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EditButton extends ElementWrapper{

	private Circle hoverEdit;
	
	public EditButton() {
		
		Color fill=Color.LIGHTGREY;
		
		Circle editC = new Circle(11,fill);
		hoverEdit = new Circle(12,fill);
		
        ImageView logoEdit = new ImageView(new Image("ressource/edit.png"));
        logoEdit.setFitWidth(16);
        logoEdit.setFitHeight(16);
        
		getChildren().addAll(hoverEdit,editC,logoEdit);
		setAlignment(Pos.CENTER);
		
	}

	@Override
	public void setHoverOn() {
		hoverEdit.setFill(Color.LIGHTGREY);
	}

	@Override
	public void setHoverOff() {
		hoverEdit.setFill(Color.GREY);
	}
	
}
