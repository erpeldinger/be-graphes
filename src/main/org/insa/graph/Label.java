package org.insa.graph;

public class Label implements Comparable <Label> {

	//Sommet associé à ce label == lien avec un noeud
	private Node sommetCourant;

	//Vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marque ;
	
	//Valeur courante du plus court chemin depuis l'origine vers le sommet.
	protected double cout;
	
	//sommet précédent sur le chemin correspondant au plus court chemin 
	private Node noeudPere;
	
	//Variable vraie si le sommet est dans le tas
	private boolean inTas;
	

	//Constructeur si le sommet n'est pas marqué, le met à false par défaut
	public Label(Node sc) {
		this.sommetCourant = sc;
		this.marque = false;
		this.cout = Integer.MAX_VALUE;
		this.noeudPere = null;
		this.inTas = false;
	}
	//Méthode
	public double getCost() {
		return this.cout;
	}
	
	public boolean getInTas() {
		return this.inTas;
	}
	
	public double getTotalCost() {
		return this.cout; //pour l'ordre des sommets dans le tas
	}

	public boolean marked() {
		return this.marque;
	}
	
	public double setCost(double cout) {
		this.cout = cout;
		return this.cout;
	}
	
	public boolean setMark() {
		this.marque = true ;
		return this.marque;
	}
	
	public Node getSommet() {
		return this.sommetCourant;
	}
	
	public Node getPere() {
		return this.noeudPere;
	}
	
	public void setPere(Node p) {
		this.noeudPere = p;
	}
	
	public void setInTas() {
		this.inTas = true;
	}
	
	public String toString(){
		return "Sommet n� "+this.sommetCourant+" Cout: "+this.cout;
		
	}

	public int compareTo(Label l) {
		int comp=0;

		if (this.getTotalCost() < l.getTotalCost()) {
			comp = -1;
		}

		else if (this.getTotalCost() == l.getTotalCost()) {
			if(this.cout > l.cout ) {
				comp = 1;
			}
			else if (this.cout < l.cout) {
				comp = -1;
			}
			comp = 0;
		}

		else if(this.getTotalCost() > l.getTotalCost()){
			comp = 1;
		}			
		return comp;
	}
	

	public boolean equals(Label x) {
		return this.sommetCourant.equals(x.getSommet());
	}
	
}

