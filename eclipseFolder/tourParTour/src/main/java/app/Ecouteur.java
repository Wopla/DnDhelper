package app;


import java.util.Collections;
import java.util.List;

import app.popUp.NewPersoScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Ecouteur {

	private FxMain main;
	
	public Ecouteur(FxMain m) {
		main = m;
	}
    
    public EventHandler<ActionEvent> addPerso(){

    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NewPersoScene nps = new NewPersoScene(main);
                nps.show();
            }
        };

    }

	public EventHandler<ActionEvent> order(List<Personnage> p) {
    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Collections.sort(p);
            	main.sortList(p);
                System.out.println("Liste trié");
            }
        };
	}
	
	public EventHandler<ActionEvent> next() {
    	return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	main.next();
            }
        };
	}

}
