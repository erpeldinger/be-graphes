package org.insa.graph;

public class Label implements Comparable <Label> {

	//Sommet associé à ce label == lien avec un noeud
	private Node sommetCourant;

	//Vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marque ;
	
	//Valeur courante du plus court chemin depuis l'origine vers le sommet.
	private int cout;
	
	//sommet précédent sur le chemin correspondant au plus court chemin 
	private Arc arcPere;
	

	//Constructeur si le sommet n'est pas marqué, le met à false par défaut
	public Label(Node sc) {
		this.sommetCourant = sc;
		this.marque = false;
		this.cout = Integer.MAX_VALUE;
		this.arcPere = null;
	}
	//Méthode
	public int getCost() {
		return this.cout;
	}
	
	public int setCost(int cout) {
		this.cout = cout;
		return this.cout;
	}
	
	
	public boolean marked() {
		return this.marque;
	}

	public boolean setMark() {
		this.marque = true ;
		return this.marque;
	}
	
	public Node getSommet() {
		return this.sommetCourant;
	}
	
	public Arc getPere() {
		return this.arcPere;
	}
	
	public void setPere(Arc p) {
		this.arcPere = p;
	}
	
	public String toString(){
		return "Sommet n� "+this.sommetCourant+" Cout: "+this.cout;
		
	}

	public int compareTo(Label x) {
		return Double.compare(this.cout, x.getCost());
	}

	public boolean equals(Label x) {
		return this.sommetCourant.equals(x.getSommet());
	}
	
}

