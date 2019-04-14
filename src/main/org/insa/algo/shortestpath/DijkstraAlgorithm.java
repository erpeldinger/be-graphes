package org.insa.algo.shortestpath;
import org.insa.algo.utils.BinaryHeap;
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
        int tailleGraphe = graph.size();
        
        boolean toutMarque = false;
        boolean fin = false;
        
        /*A initialiser*/
        Label labelSuccesseur;
        Label labelCourant;
        LabelList liste;
        
        //Tas de labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
               
        
        //Tant qu'il existe des sommets non marqu�s
        while(/*!tas.isEmpty() && !fin*/) {
        	labelCourant = tas.findMin();
        	labelCourant.setMark();
        	//this.liste.add(labelCourant);
        
	        //On parcoure les successeurs y de x
	        for (Node courant : graph.getNodes()) {
	        	labelCourant = liste.recupLabel(courant);
	        	
	        	//Si le successeur n'est pas encore marqu�
	        	if(!labelSuccesseur.marked()) {
	        	
	        		//L� j'ai mis getCost mais il faudrait plut�t calculer un getTotalCost
	        		if(labelSuccesseur.getCost() > labelCourant.getCost() /*+ W(x,y)*/) {
	        			labelSuccesseur.setCost(labelCourant.getCost() /* + W(x,y)*/);
	        		
	        			// Si le successeur est d�j� dans le tas, on met � jour sa position
	        			if(labelSuccesseur./*A compl�ter*/) {
	        				tas.remove(labelSuccesseur);
	        			}
	        			//Si le successeur n'est pas dans le tas, on l'ajoute
	        			else {
	        				tas.insert(labelSuccesseur);
	        			}  
	        		}
	        	
	        	}
	        	        	
	        }
        }	
        	
       /* 
      //Tant qu'il existe des sommets non marqu�s

        for (Node courant : graph.getNodes()) {
        	labelCourant = liste.recupLabel(courant);

        }
    
        	
        	//ArrayList <> x = labelCourant.
        	//.marque = true;
        
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
        
        return solution;
    }
}

