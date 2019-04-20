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

   /* ------------PATH TEST------------------------------------------
    @Test(expected = UnsupportedOperationException.class)
    public void testImmutability() {
        emptyPath.getArcs().add(a2b);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(emptyPath.isEmpty());

        assertFalse(singleNodePath.isEmpty());
        assertFalse(shortPath.isEmpty());
        assertFalse(longPath.isEmpty());
        assertFalse(loopPath.isEmpty());
        assertFalse(longLoopPath.isEmpty());
        assertFalse(invalidPath.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, emptyPath.size());
        assertEquals(1, singleNodePath.size());
        assertEquals(4, shortPath.size());
        assertEquals(5, longPath.size());
        assertEquals(5, loopPath.size());
        assertEquals(10, longLoopPath.size());
    }

    @Test
    public void testIsValid() {
        assertTrue(emptyPath.isValid());
        assertTrue(singleNodePath.isValid());
        assertTrue(shortPath.isValid());
        assertTrue(longPath.isValid());
        assertTrue(loopPath.isValid());
        assertTrue(longLoopPath.isValid());

        assertFalse(invalidPath.isValid());
    }

    @Test
    public void testGetLength() {
        assertEquals(0, emptyPath.getLength(), 1e-6);
        assertEquals(0, singleNodePath.getLength(), 1e-6);
        assertEquals(40, shortPath.getLength(), 1e-6);
        assertEquals(62.8, longPath.getLength(), 1e-6);
        assertEquals(55, loopPath.getLength(), 1e-6);
        assertEquals(120, longLoopPath.getLength(), 1e-6);
    }

    @Test
    public void testGetTravelTime() {
        // Note: 18 km/h = 5m/s
        assertEquals(0, emptyPath.getTravelTime(18), 1e-6);
        assertEquals(0, singleNodePath.getTravelTime(18), 1e-6);
        assertEquals(8, shortPath.getTravelTime(18), 1e-6);
        assertEquals(12.56, longPath.getTravelTime(18), 1e-6);
        assertEquals(11, loopPath.getTravelTime(18), 1e-6);
        assertEquals(24, longLoopPath.getTravelTime(18), 1e-6);

        // Note: 28.8 km/h = 8m/s
        assertEquals(0, emptyPath.getTravelTime(28.8), 1e-6);
        assertEquals(0, singleNodePath.getTravelTime(28.8), 1e-6);
        assertEquals(5, shortPath.getTravelTime(28.8), 1e-6);
        assertEquals(7.85, longPath.getTravelTime(28.8), 1e-6);
        assertEquals(6.875, loopPath.getTravelTime(28.8), 1e-6);
        assertEquals(15, longLoopPath.getTravelTime(28.8), 1e-6);
    }

    @Test
    public void testGetMinimumTravelTime() {
        assertEquals(0, emptyPath.getMinimumTravelTime(), 1e-4);
        assertEquals(0, singleNodePath.getLength(), 1e-4);
        assertEquals(4, shortPath.getMinimumTravelTime(), 1e-4);
        assertEquals(5.14, longPath.getMinimumTravelTime(), 1e-4);
        assertEquals(5.5, loopPath.getMinimumTravelTime(), 1e-4);
        assertEquals(11.25, longLoopPath.getMinimumTravelTime(), 1e-4);
    }

    @Test
    public void testCreateFastestPathFromNodes() {
        Path path;
        Arc[] expected;

        // Simple construction
        path = Path.createFastestPathFromNodes(graph,
                Arrays.asList(new Node[] { nodes[0], nodes[1], nodes[2] }));
        expected = new Arc[] { a2b, b2c };
        assertEquals(expected.length, path.getArcs().size());
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], path.getArcs().get(i));
        }

        // Not so simple construction
        path = Path.createFastestPathFromNodes(graph,
                Arrays.asList(new Node[] { nodes[0], nodes[1], nodes[2], nodes[3] }));
        expected = new Arc[] { a2b, b2c, c2d_3 };
        assertEquals(expected.length, path.getArcs().size());
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], path.getArcs().get(i));
        }

        // Trap construction!
        path = Path.createFastestPathFromNodes(graph, Arrays.asList(new Node[] { nodes[1] }));
        assertEquals(nodes[1], path.getOrigin());
        assertEquals(0, path.getArcs().size());

        // Trap construction - The return!
        path = Path.createFastestPathFromNodes(graph, Arrays.asList(new Node[0]));
        assertEquals(null, path.getOrigin());
        assertEquals(0, path.getArcs().size());
        assertTrue(path.isEmpty());
    }

    @Test
    public void testCreateShortestPathFromNodes() {
        Path path;
        Arc[] expected;

        // Simple construction
        path = Path.createShortestPathFromNodes(graph,
                Arrays.asList(new Node[] { nodes[0], nodes[1], nodes[2] }));
        expected = new Arc[] { a2b, b2c };
        assertEquals(expected.length, path.getArcs().size());
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], path.getArcs().get(i));
        }

        // Not so simple construction
        path = Path.createShortestPathFromNodes(graph,
                Arrays.asList(new Node[] { nodes[0], nodes[1], nodes[2], nodes[3] }));
        expected = new Arc[] { a2b, b2c, c2d_2 };
        assertEquals(expected.length, path.getArcs().size());
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], path.getArcs().get(i));
        }

        // Trap construction!
        path = Path.createShortestPathFromNodes(graph, Arrays.asList(new Node[] { nodes[1] }));
        assertEquals(nodes[1], path.getOrigin());
        assertEquals(0, path.getArcs().size());

        // Trap construction - The return!
        path = Path.createShortestPathFromNodes(graph, Arrays.asList(new Node[0]));
        assertEquals(null, path.getOrigin());
        assertEquals(0, path.getArcs().size());
        assertTrue(path.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFastestPathFromNodesException() {
        Path.createFastestPathFromNodes(graph, Arrays.asList(new Node[] { nodes[1], nodes[0] }));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateShortestPathFromNodesException() {
        Path.createShortestPathFromNodes(graph, Arrays.asList(new Node[] { nodes[1], nodes[0] }));
    }
    */

}
