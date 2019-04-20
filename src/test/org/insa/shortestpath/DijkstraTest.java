package org.insa.shortestpath;

import org.insa.algo.*;
import org.insa.graph.*;
import org.insa.algo.shortestpath.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest {

    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;
    
    //On note a le noeud x1, b le noeud x2 etc.
    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;

   
    
    @BeforeClass
    public static void initAll() throws IOException {

        RoadInformation infoRoute = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, "");
               

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs        
        a2b = Node.linkNodes(nodes[0], nodes[1], 7, infoRoute, null);
		a2c = Node.linkNodes(nodes[0], nodes[2], 8, infoRoute, null);
		b2d = Node.linkNodes(nodes[1], nodes[3], 4, infoRoute, null);
		b2e = Node.linkNodes(nodes[1], nodes[4], 1, infoRoute, null);
		b2f = Node.linkNodes(nodes[1], nodes[5], 5, infoRoute, null);
		c2a = Node.linkNodes(nodes[2], nodes[0], 7, infoRoute, null);
		c2b = Node.linkNodes(nodes[2], nodes[1], 2, infoRoute, null);
		c2f = Node.linkNodes(nodes[2], nodes[5], 2, infoRoute, null);
		e2c = Node.linkNodes(nodes[4], nodes[2], 2, infoRoute, null);
		e2d = Node.linkNodes(nodes[4], nodes[3], 2, infoRoute, null);
		e2f = Node.linkNodes(nodes[4], nodes[5], 3, infoRoute, null);
		f2e = Node.linkNodes(nodes[5], nodes[4], 3, infoRoute, null);
        
		//Initialise le graphe
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
 
    }

    @Test
    public void testDoRun() {
    	
    	//-------------- Test comparatif des algo Bellman-Ford et Dijkstra ---------------------------- 
    	System.out.println("/-------------- 1.2 - Tests de validité avec oracle sur un exemple simple --------------/"+"\n");
    	
    	//On affiche les points de départ (colonnes)
    	for (int i=0; i < nodes.length; i++) {
    		
    		System.out.println("x" + (nodes[i].getId()+1) + ":");
    		
    		//Affichage des lignes du tableau
    		for (int j=0;  j < nodes.length; j++) {
    			
    			//Si le noeud destinataire est le noeud initial
    			if(nodes[i]==nodes[j]) {
					System.out.println("   -   ");
				}
    			
    			else {
    				//On prend le 1er ArcInspector de la liste
    				ArcInspector arcInspectorDijkstra = new ArcInspectorFactory().getAllFilters().get(0);
    				ShortestPathData data = new ShortestPathData(graph, nodes[i],nodes[j], arcInspectorDijkstra);
    				
    				//On crée les deux algorithmes (Bellman-Ford et Dijkstra)
    				BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);
					
    				//On récupère les solutions des algorithmes Bellman-Ford et Dijkstra
    				ShortestPathSolution solutionDijkstra = D.run();
					ShortestPathSolution solutionBF = B.run();
					
					//Si le chemin est inexistant
					if (solutionDijkstra.getPath() == null) {
						assertEquals(solutionBF.getPath(), solutionDijkstra.getPath());
						System.out.println("\\infty ");
					}
					
					//Sinon, il existe un plus court chemin
					else {
						
						//On calcule le coût du plus court chemin
						float costDijkstra = solutionDijkstra.getPath().getLength();
						float costBF = solutionBF.getPath().getLength();
						assertEquals(costDijkstra, costBF, 0);

						//On récupère le père du sommet de la destionnation
						List<Arc> listeArcs = solutionDijkstra.getPath().getArcs();
						Node pereDestination = listeArcs.get(listeArcs.size()-1).getOrigin();

						System.out.println(costDijkstra+ ", (x" + (pereDestination.getId()+1) + ") ");
					}
    			}    			
    		}
    		// Retour à la ligne
			System.out.println();
    	}
    	// Retour à la ligne 
		System.out.println();
    }
    
    //---------------------------- Scénarios ------------------------------
    


}
