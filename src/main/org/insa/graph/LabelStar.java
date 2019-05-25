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

	
}