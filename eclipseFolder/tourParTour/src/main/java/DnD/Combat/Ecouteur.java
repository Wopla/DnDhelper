package DnD.Combat;

import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Ecouteur {

	private App main;
	
	public Ecouteur(App m) {
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
