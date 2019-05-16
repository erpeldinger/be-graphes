package org.insa.graph;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.*;

public class LabelStar extends Label implements Comparable<Label> {
	
	protected double borneInf;
	
	public LabelStar(Node sc, ShortestPathData data) {
		super(sc);
		
		if(data.getMode() == AbstractInputData.Mode.LENGTH) { // shortest path (distance)
			this.borneInf = sc.getPoint().distanceTo(data.getDestination().getPoint());			
		}
		else { // fastest path (temps)
			//V�rifier les histoires de vitesses du graphe et de la data
			double vitesseArc = data.getMaximumSpeed() ;
			double vitesseGraph = Math.max(vitesseArc, data.getGraph().getGraphInformation().getMaximumSpeed());
			//V�rifier les unit�s
			this.borneInf = sc.getPoint().distanceTo(data.getDestination().getPoint()) / vitesseGraph;
		}
	}
	
	public double getBorne() { return this.borneInf; }
	
	public double getTotalCost() {
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
}

