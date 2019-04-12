package org.insa.graph;

import java.util.List;
import org.insa.algo.utils.BinaryHeap;

public class Label {

	//Sommet associé à ce label == lien avec un noeud
	private Node sommetCourant;

	//Vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marque ;
	
	//Valeur courante du plus court chemin depuis l'origine vers le sommet.
	private int cout;
	
	//sommet précédent sur le chemin correspondant au plus court chemin 
	private Arc arcPere;
	
	
	
	//Constructeur si le sommet est marqué
	public Label(Node sc, boolean m, int c, Arc p) {
		this.sommetCourant = sc;
		this.marque = m;
		this.cout = c;
		this.arcPere = p;
	}

	//Constructeur si le sommet n'est pas marqué, le met à false par défaut
	public Label(Node sc, int c, Arc p) {
		this.sommetCourant = sc;
		this.marque = false;
		this.cout = c;
		this.arcPere = p;
	}
	//Méthode
	public int getCost() {
		return this.cout;
	}
	
	public boolean marked() {
		return this.marque;
	}

	public Node getSommet() {
		return this.sommetCourant;
	}
	
	public Arc getPere() {
		return this.arcPere;
	}
}

