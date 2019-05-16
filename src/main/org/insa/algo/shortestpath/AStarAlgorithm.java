package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.ElementNotFoundException;
import org.insa.graph.Arc;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }    
    
    //A corriger, dans DijkstraAlgo il faudrait cr�er cette m�thode comment � on a juste � la red�finir ici pour ne pas r��crire tout l'algo
    //Ou alors on copie/colle tout comme �a et on modifie "extends ShortestPathAlgorithm : 
    
    @Override
    protected LabelStar creerLabel(Node sc, ShortestPathData data) {
		return new LabelStar(sc, data);
	}

    protected LabelStar creerLabel () {
    	LabelStar result = null;
		return result;
	}
    
}

