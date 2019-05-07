package org.insa.algo.shortestpath;

import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    
    //A corriger, dans DijkstraAlgo il faudrait créer cette méthode comment ç on a juste à la redéfinir ici pour ne pas réécrire tout l'algo
    protected Label creerLabel(Node sc, ShortestPathData data) {
		return new LabelStar(sc, data);
	}

}
