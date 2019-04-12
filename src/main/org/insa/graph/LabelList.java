package org.insa.graph;

import java.util.ArrayList;
import org.insa.algo.utils.BinaryHeap;
import java.util.List;

public class LabelList {

	//Sommet associé à ce label == lien avec un noeud
	private ArrayList<Label> labels;
	private int size;
	
	//Constructeur si le sommet est marqué
	public LabelList(ArrayList<Label> liste, int taille) {
		this.labels = liste;
		this.size = taille;
	}
	
	// Recuperer le label associé à un sommet
	public Label recupLabel(Node node) {
		boolean trouve = false;
		int i = 0;
		Label Result = null;
		while (!trouve && i <this.size) {
			if (this.labels.get(i).getSommet() == node) {
				trouve = true;
				Result = new Label(this.labels.get(i).getSommet(), this.labels.get(i).marked(),
							  this.labels.get(i).getCost(), this.labels.get(i).getPere());
				
			}
			i++;
		}
		return Result;
	}
	
}

