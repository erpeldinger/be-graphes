package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;
import java.util.*;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    //Cette mï¿½thode permet de crï¿½er un Label associï¿½ ï¿½ un Node
    protected Label creerLabel(Node sc, ShortestPathData data) {
		return new Label(sc);
	}

    //Cette mï¿½thode permet de crï¿½er un Label null
    protected Label creerLabel () {
    	Label result = null;
		return result;
	}
    
    @Override
    public ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        //Initialisation
        ArrayList<Label> listeLabel = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        for (Node courantNode : this.data.getGraph().getNodes()) {
        	listeLabel.add(creerLabel(courantNode, data));
        	//listeLabel.add(new Label(courantNode));
        	if (courantNode.equals(data.getOrigin())) {
        		listeLabel.get(listeLabel.size() - 1).setCost(0);
        		tas.insert(listeLabel.get(listeLabel.size() - 1 ));
        	}
        }
        //Label labelCourant, labelSuccesseur = null;
        Label labelCourant = creerLabel();
        Label labelSuccesseur = creerLabel();
        //int iter = 0;
        //int nbarc = 0;
        //int successeurTestes=  0;
        
        //Notifie les observateurs du départ de l'origine
        notifyOriginProcessed(data.getOrigin());        
        
        //Tant qu'il existe des sommets non marques
        while(!tas.isEmpty()) {
        	//successeurTestes = 0;
        	//iter++;
        	labelCourant = tas.findMin();
        	tas.remove(labelCourant);
        	labelCourant.setMark();
        	//Notifie les observateurs que le sommet courant a été marqué
        	//)notifyNodeMarked(labelCourant.getSommet());
        	//System.out.println("label " + labelCourant.getCost()); // COUT CROISSANT OK
        
	        //On parcourt les successeurs y de x
	        for (Arc arcCourant : labelCourant.getSommet().getSuccessors()) {
                // Small test to check allowed roads...
                if (!data.isAllowed(arcCourant)) {
                    continue;
                }
                
                labelSuccesseur = listeLabel.get(arcCourant.getDestination().getId());
		        
	        	//successeurTestes++;
                
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
						//Notification aux obervateurs que l'on a atteint un noeud pour la première fois
		                notifyNodeReached(labelSuccesseur.getSommet());
		                //nbarc++;
						//System.out.println("label successeur " + labelSuccesseur);
			        	}
	        	}
        	}
	        //System.out.println("nb successeurs testes Ã  l'iteration " + iter + " : " + successeurTestes); //DE 1 A 4 TESTS a chaque iter
	        //System.out.println("taille du tas " + tas.size()); // ENVIRON 3 3 4 3 4 4 5 5 4 5 6 6 .... ~croisant jusqu'Ã  47 puis dÃ©croissant
        }
        
		// System.out.println("nb iter : " + iter);  // NB ITERATIONS : 1164
        //System.out.println("nb arcs " + nbarc);    // NB ARCS : 1262
        // TESTE SUR LA CARTE INSA : 1349 NODES ET 2887 ARCS
        
        //Notification de la destination
        notifyDestinationReached(data.getDestination());
        
        //Reconstitution de la solution
        ArrayList<Node> solutionNode = new ArrayList<Node>();
        Label labelDestination=null, labelOrigine = null;
        // on recupere le bon label (bonne origine et destination)
        for (Label l : listeLabel) {
        	if (l.getSommet().equals(data.getOrigin())) {
        		//meme modif que l56
        		labelOrigine = l;
        		//labelOrigine = creerLabel(l.getSommet(), data);
        	}
        	if (l.getSommet().equals(data.getDestination())) {
        		//idem
        		labelDestination = l;
        		//labelDestination = creerLabel(l.getSommet(), data);
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
        
        // Create the final solution.
        Path p = Path.createShortestPathFromNodes(this.data.getGraph(), solutionNode);
        
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, p);
        
        return solution;
    }
}

