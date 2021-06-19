package app;


import java.io.Serializable;

public class SavePerso implements Serializable{

	private static final long serialVersionUID = 3590018757807627730L;
	
	private String nom;
	private int ca,pv,ini;
	private boolean faction,disable;
	
	public SavePerso(String n,int c, int p, int i, boolean f, boolean d) {
		nom =n;
		ca = c;
		pv = p;
		ini = i;
		faction = f;
		disable = d;
	}
	
	public String toString() {
		return "	nom :"+nom+"\n"+
	"		ca :"+ca+
	"		pv :"+pv+
	"		ini :"+ini+"\n";
	}

	public String getNom() {
		return nom;
	}

	public int getCa() {
		return ca;
	}

	public int getPv() {
		return pv;
	}

	public int getIni() {
		return ini;
	}

	public boolean isFaction() {
		return faction;
	}

	public boolean isDisable() {
		return disable;
	}
}
