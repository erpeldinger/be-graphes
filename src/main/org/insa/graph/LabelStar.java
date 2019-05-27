package org.insa.graph;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.*;

public class LabelStar extends Label implements Comparable<Label> {
	protected double borneInf;

	public LabelStar(Node sc, ShortestPathData data) {
		super(sc);

		if(data.getMode() == AbstractInputData.Mode.LENGTH) { // shortest path (distance)
			this.borneInf = Point.distance(sc.getPoint(), data.getDestination().getPoint());			
		}

		else if (data.getMode() == AbstractInputData.Mode.TIME) { // fastest path (temps) 
			double vitesseGraph;
			double vitesseMax = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			if (data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED) {
				vitesseGraph = 80/3.6 ; // m/s
			}
			else {
				vitesseGraph = data.getMaximumSpeed() ;
			}
			//Conversion, distance en m
			this.borneInf = Point.distance(sc.getPoint(), data.getDestination().getPoint()) / (vitesseMax*1000/3600);
		}
	}

	public double getBorne() { return this.borneInf; }

	@Override
	public double getTotalCost() {
		return this.borneInf + this.cout; //pour l'ordre des sommets dans le tas
	}
	
}