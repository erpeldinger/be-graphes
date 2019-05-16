package org.insa.graph;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.*;

public class LabelStar extends Label implements Comparable<Label> {
	
	protected int borneInf;
	
	public LabelStar(Node sc, ShortestPathData data) {
		super(sc);
		
		if(data.getMode() == AbstractInputData.Mode.LENGTH) { // shortest path (distance)
			this.borneInf = (int)sc.getPoint().distanceTo(data.getDestination().getPoint());			
		}
		else { // fastest path (temps)
			//V�rifier les histoires de vitesses du graphe et de la data
			int vitesseArc = data.getMaximumSpeed() ;
			int vitesseGraph = Math.max(vitesseArc, data.getGraph().getGraphInformation().getMaximumSpeed());
			//V�rifier les unit�s
			this.borneInf = (int)sc.getPoint().distanceTo(data.getDestination().getPoint()) / vitesseGraph;
		}
	}
	
	public int getBorne() { return this.borneInf; }
	
	public int getTotalCost() {
		return this.borneInf + this.cout; //pour l'ordre des sommets dans le tas
	}
	public int compareTo(LabelStar l) {
		
		int comp;
		
		if (this.getTotalCost() < l.getTotalCost()) {
			comp = -1;
		}
		
		else if (this.getTotalCost() == l.getTotalCost()) {
			if (this.getBorne() < l.getBorne()) {
				comp = -1 ;
			}
			else if (this.getBorne() > l.getBorne()) {
				comp = 1;
			}
			else {
				comp = 0;
			}
		}
		
		else {
			comp = 1;
		}			
		return comp;
	}

	/**
	
	//Sommet associé à ce label == lien avec un noeud
	private Node sommetCourant;

	//Vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marque ;
	
	//Valeur courante du plus court chemin depuis l'origine vers le sommet.
	private int cout;
	
	//sommet précédent sur le chemin correspondant au plus court chemin 
	private Arc arcPere;
	

	//Constructeur si le sommet n'est pas marqué, le met à false par défaut
	public LabelStar(Node sc) {
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

	public int compareTo(LabelStar x) {
		return Double.compare(this.cout, x.getCost());
	}

	public boolean equals(LabelStar x) {
		return this.sommetCourant.equals(x.getSommet());
	}
	*/
}

