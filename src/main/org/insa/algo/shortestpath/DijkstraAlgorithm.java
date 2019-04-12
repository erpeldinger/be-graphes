package org.insa.algo.shortestpath;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        
        boolean toutMarque = false;
        Label labelCourant;
        LabelList liste;
        
        //Tant qu'il existe des sommets non marqués
        for (Node courant : graph.getNodes()) {
        	labelCourant = liste.recupLabel(courant);
        	//ArrayList <> x = labelCourant.
        	//.marque = true;
        	/*
	        for(tous les successeurs) {

	        	if(! Label.marked()) {
	        		if(coutY > coutX + w) {
	        			coutY = coutX + w;
	        			
	        			if(existYTas) {
	        				updateYTas;
	        			}
	        			
	        			else {
	        				insertYTas;
	        			}
	        		}	        		
	        	}
	        }    
	        */    
        }
        return solution;
    }

}
