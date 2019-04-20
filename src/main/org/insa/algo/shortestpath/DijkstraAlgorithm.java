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
        
        int nb_iter = 0;
        
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
        //Je ne sais pas si c'est au bon endroit
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        Label labelCourant, labelSuccesseur = null;
        
        //Tant qu'il existe des sommets non marques
        while(!tas.isEmpty()) {
        	labelCourant = tas.findMin();
        	tas.remove(labelCourant);
        	labelCourant.setMark();
        	nb_iter++;
        	
        	//Affichage temporaire du coût des labels marqués
        	System.out.println("Noeud marqué, " + labelCourant.toString()+"\n");
        	
        	// On indique que le Node a été marqué
			notifyNodeMarked(labelCourant.getSommet());
        
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
		        		// On indique que l'on atteint un Node pour la première fois
		        		notifyNodeReached(arcCourant.getDestination()); 
		        		break;
		        	}		        	
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
        
        // Reverse the path
        Collections.reverse(solutionNode);
        
        // The destination has been found, notify the observers.
        notifyDestinationReached(data.getDestination());
        
        // Create the final solution.
        Path p = Path.createShortestPathFromNodes(this.data.getGraph(), solutionNode);
        solution = new ShortestPathSolution(data, Status.OPTIMAL, p);  
        
        //Affiche des infos sur le plus court chemin
        System.out.println("Infos du plus court chemin : "+ solution.toString()+"\n");
        System.out.println("Nb itérations Dijkstra : "+ nb_iter+"\n");        
        
        return solution;
    }
}

