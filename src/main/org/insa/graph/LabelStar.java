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
			double vitesseGraph;
			if (data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED) {
				vitesseGraph = 80/3.6 ; // m/s
			}
			else {
				vitesseGraph = data.getMaximumSpeed() ;
			}
			//double vitesseArc = Math.max(vitesseArc, data.getGraph().getGraphInformation().getMaximumSpeed());
			//Conversion, distance en m
			this.borneInf = sc.getPoint().distanceTo(data.getDestination().getPoint()) / (vitesseGraph/3.6);
		}
	}

	public double getBorne() { return this.borneInf; }

	public double getTotalCost() {
		return this.borneInf + this.cout; //pour l'ordre des sommets dans le tas
	}
	public int compareTo(LabelStar l) {


		int res = Double.compare(this.getTotalCost(), ((LabelStar)l).getTotalCost());
		if(res == 0) {
			res = Double.compare(this.borneInf, ((LabelStar)l).getBorne());
		}
		return res; 
	}
	/*
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
	 */
}

