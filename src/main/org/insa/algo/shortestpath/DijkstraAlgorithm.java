package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;
import java.util.*;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    public ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        //Initialisation
        ArrayList<Label> listeLabel = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        for (Node courantNode : this.data.getGraph().getNodes()) {
        	listeLabel.add(new Label(courantNode));
        	if (courantNode.equals(data.getOrigin())) {
        		listeLabel.get(listeLabel.size() - 1).setCost(0);
        		tas.insert(listeLabel.get(listeLabel.size() - 1 ));
        	}
        }
        
        Label labelCourant, labelSuccesseur = null;
        int iter = 0;
        int nbarc = 0;
        int successeurTestes=  0;
        //Tant qu'il existe des sommets non marques
        while(!tas.isEmpty()) {
        	successeurTestes = 0;
        	iter++;
        	labelCourant = tas.findMin();
        	tas.remove(labelCourant);
        	labelCourant.setMark();
        	//System.out.println("label " + labelCourant.getCost()); // COUT CROISSANT OK
        
	        //On parcourt les successeurs y de x
	        for (Arc arcCourant : labelCourant.getSommet().getSuccessors()) {
                // Small test to check allowed roads...
                if (!data.isAllowed(arcCourant)) {
                    continue;
                }
	        	for (Label l : listeLabel) {
		        	//Si le successeur n'est pas encore marque
		        	if(l.getSommet().equals(arcCourant.getDestination())) {
		        		labelSuccesseur = l;
		        		break;
		        	}
	        	}
	        	successeurTestes++;
	        	if (labelSuccesseur.marked() == false) {
	        		if (labelSuccesseur.getCost() > labelCourant.getCost() + arcCourant.getLength()) {
	        			labelSuccesseur.setCost(labelCourant.getCost() + (int)arcCourant.getLength());
	        			labelSuccesseur.setPere(arcCourant);
	        			
			        	//on insere un label s'il n'était pas deja dans la liste
			        	try {
			        		tas.remove(labelSuccesseur);
			        	}
			        	catch (ElementNotFoundException e){
			        	}
						tas.insert(labelSuccesseur);
						nbarc++;
						System.out.println("label successeur " + labelSuccesseur);
			        	}
	        	}
        	}
	        //System.out.println("nb successeurs testes à l'iteration " + iter + " : " + successeurTestes); //DE 1 A 4 TESTS a chaque iter
	        //System.out.println("taille du tas " + tas.size()); // ENVIRON 3 3 4 3 4 4 5 5 4 5 6 6 .... ~croisant jusqu'à 47 puis décroissant
        }
		// System.out.println("nb iter : " + iter);  // NB ITERATIONS : 1164
        //System.out.println("nb arcs " + nbarc);    // NB ARCS : 1262
        // TESTE SUR LA CARTE INSA : 1349 NODES ET 2887 ARCS
        
        //Reconstitution de la solution
        ArrayList<Node> solutionNode = new ArrayList<Node>();
        Label labelDestination=null, labelOrigine = null;
        // on recupere le bon label (bonne origine et destination)
        for (Label l : listeLabel) {
        	if (l.getSommet().equals(data.getOrigin())) {
        		labelOrigine = l;
        	}
        	if (l.getSommet().equals(data.getDestination())) {
        		labelDestination = l;
        	}
        }
        
        Label aux = labelDestination;
        
        while (!aux.equals(labelOrigine)) {
        	if (aux.getPere().equals(null)) {
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        		return solution;
        	}
        	solutionNode.add(aux.getSommet());
        	// on cherche le label associé au noeud pere
        	for (Label l : listeLabel) {
        		if (l.getSommet().equals(aux.getPere().getOrigin())) {
        			aux = l;
        			break;
        		}
        	}
        }
        
        solutionNode.add(labelOrigine.getSommet());
        
        // Reverse the path...
        Collections.reverse(solutionNode);
        
        // Create the final solution.
        Path p = Path.createShortestPathFromNodes(this.data.getGraph(), solutionNode);
        
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, p);
        
        return solution;
    }
}

