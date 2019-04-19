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
    protected ShortestPathSolution doRun() {
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
        		

        		
        		//Je ne sais pas si c'est au bon endroit
        		// Notifie les observateurs du départ de l'origine
        		notifyOriginProcessed(data.getOrigin());
        		
        		
        		
        	}
        }
        
        Label labelCourant, labelSuccesseur = null;
        
        //Tant qu'il existe des sommets non marques
        while(!tas.isEmpty()) {
        	labelCourant = tas.findMin();
        	tas.remove(labelCourant);
        	
        	
        	
        	
        	
        	// On indique que le Node a été marqué
			notifyNodeMarked(current.getNode());
			
			
			
			
        	labelCourant.setMark();
        
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
		        
		        	
		        	
	        /**		//Je crois qu'il faut ajuter un if
	        		// On indique que l'on atteint un Node pour la première fois
					notifyNodeReached(arcIter.getDestination()); 
			*/
					
		        	
		        	
		        	
	        	}
	        	if (labelSuccesseur.marked() == false) {
	        		if (labelSuccesseur.getCost() > labelCourant.getCost() + arcCourant.getLength()) {
	        			labelSuccesseur.setCost(labelCourant.getCost() + (int)arcCourant.getLength());
	        			labelSuccesseur.setPere(arcCourant);
	        			
			        	//on insere un label s'il n'Ã©tait pas deja dans la liste
			        	try {
			        		tas.remove(labelSuccesseur);
			        	}
			        	catch (ElementNotFoundException e){
			        	}
						tas.insert(labelSuccesseur);
						//System.out.println(labelSuccesseur);
			        	}
	        	}
        	}
	        	        	
    	}
        
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
        	// on cherche le label associÃ© au noeud pere
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
        
        
        
        
        //Je ne suis pas sûre de l'endroit
        // The destination has been found, notify the observers.
        notifyDestinationReached(data.getDestination());
        
        
        
        
        // Create the final solution.
        Path p = Path.createShortestPathFromNodes(this.data.getGraph(), solutionNode);
        solution = new ShortestPathSolution(data, Status.OPTIMAL, p);
        
        return solution;
    }
}

