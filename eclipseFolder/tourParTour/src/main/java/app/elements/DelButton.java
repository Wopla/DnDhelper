package app.elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DelButton extends ElementWrapper implements java.io.Serializable{
	
	private static final long serialVersionUID = -2741214528079458324L;
	private Circle hoverDel;
	
	public DelButton() {
		
		Color fill=Color.LIGHTGREY;
		
		Circle delC = new Circle(11,fill);
		hoverDel = new Circle(12,fill);
        ImageView logoDel = new ImageView(new Image(getClass().getResource("/trash.png").toString()));
        logoDel.setFitWidth(28);
        logoDel.setFitHeight(28);
		
		getChildren().addAll(hoverDel,delC,logoDel);

		setAlignment(Pos.CENTER);

	}

	@Override
	public void setHoverOn() {
		hoverDel.setFill(Color.LIGHTGREY);
	}

	@Override
	public void setHoverOff() {
		hoverDel.setFill(Color.GREY);
	}

}
