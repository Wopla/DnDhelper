package app;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SaveListPerso implements Serializable{
	
	private static final long serialVersionUID = 7543548216214132799L;
	
	private HashMap<String, ArrayList<SavePerso>> listSave;
	
	public SaveListPerso(HashMap<String, ArrayList<SavePerso>> ls) {
		listSave=ls;
	}
	
	public SaveListPerso() {
		listSave = new HashMap<>();
	}
	
	public void addListSave(String s,List<SavePerso> listPerso) {
		listSave.put(s, (ArrayList<SavePerso>) listPerso);
	}
	
	public void delListSave(String s) {
		listSave.remove(s);
	}

	public ArrayList<SavePerso> getSavePerso(String s) {
		ArrayList<SavePerso> retour = listSave.get(s);
		return retour;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toString() {
		String retour="";
		Iterator it =  listSave.entrySet().iterator();
	    while (it.hasNext()) {
	    	HashMap.Entry pair = (HashMap.Entry)it.next();
	    	retour +="  "+pair.getKey()+"\n";
	        for(SavePerso p : ((List<SavePerso>) pair.getValue()))
	        	retour +=p;
	        //System.out.println(pair.getValue());
	    }

		return retour+"\n";
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> getSaves(){
		List<String> retour = new ArrayList<String>();
		Iterator it =  listSave.entrySet().iterator();
	    while (it.hasNext()) {
	    	HashMap.Entry pair = (HashMap.Entry)it.next();
	    	retour.add((String) pair.getKey());
	    }
		return retour;
	}
	
	
	public HashMap<String, ArrayList<SavePerso>> getListSave(){
		return listSave;
	}
}
