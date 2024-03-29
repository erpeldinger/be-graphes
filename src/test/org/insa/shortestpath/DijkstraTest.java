package org.insa.shortestpath;

import org.insa.algo.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.graph.*;
import org.insa.algo.shortestpath.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import org.insa.graph.RoadInformation.RoadType;
import org.insa.performance.LaunchTest;
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
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

   
    
    @BeforeClass
    public static void initAll() throws IOException {
    	
        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
        				speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");
               
        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);
        
        
		//Initialise le graphe
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
 
    }
    //---------------------------- 1.3 Caractéristiques des solutions obtenues -----------------
    
    
    @Test
	public void testDikjstraValidSol() {
	    Arc[] expected;
	
	    // Chemin 1
	    ArcInspector AI = new ArcInspectorFactory().getAllFilters().get(0);
	    ShortestPathData data = new ShortestPathData(graph, nodes[0], nodes[1], AI);
	    DijkstraAlgorithm Dijkstra1 = new DijkstraAlgorithm(data);
	    ShortestPathSolution Solution1 = Dijkstra1.doRun();
	    Path path1 = Solution1.getPath();
	    boolean validite = path1.isValid();
	    System.out.println("chemin 1 (1) est " + validite);
	    

	    ArcInspector AI3 = new ArcInspectorFactory().getAllFilters().get(2);
	    ShortestPathData data3 = new ShortestPathData(graph, nodes[0], nodes[1], AI3);
	    DijkstraAlgorithm Dijkstra3 = new DijkstraAlgorithm(data3);
	    ShortestPathSolution Solution3 = Dijkstra1.doRun();
	    Path path3 = Solution3.getPath();
	    validite = path3.isValid();
	    System.out.println("chemin 1 (3) est " + validite);
	    
	    // Chemin 2
	    data = new ShortestPathData(graph, nodes[4], nodes[0], AI);
	    Dijkstra1 = new DijkstraAlgorithm(data);
	    Solution1 = Dijkstra1.doRun();
	    path1 = Solution1.getPath();
	    validite = path1.isValid();
	    System.out.println("chemin 2 (1) est " + validite);
	    

	    data3 = new ShortestPathData(graph, nodes[4], nodes[0], AI3);
	    Dijkstra3 = new DijkstraAlgorithm(data3);
	    Solution3 = Dijkstra1.doRun();
	    path3 = Solution3.getPath();
	    validite = path3.isValid();
	    System.out.println("chemin 2 (3) est " + validite);
	    

	    // Chemin 3 
	    data = new ShortestPathData(graph, nodes[0], nodes[2], AI);
	    Dijkstra1 = new DijkstraAlgorithm(data);
	    Solution1 = Dijkstra1.doRun();
	    path1 = Solution1.getPath();
	    validite = path1.isValid();
	    System.out.println("chemin 3 (1) est " + validite);
	    

	    data3 = new ShortestPathData(graph, nodes[0], nodes[2], AI3);
	    Dijkstra3 = new DijkstraAlgorithm(data3);
	    Solution3 = Dijkstra1.doRun();
	    path3 = Solution3.getPath();
	    validite = path3.isValid();
	    System.out.println("chemin 3 (3) est " + validite);
	    
	    

	    // Chemin 4 - deux arcs 
	    data = new ShortestPathData(graph, nodes[0], nodes[3], AI);
	    Dijkstra1 = new DijkstraAlgorithm(data);
	    Solution1 = Dijkstra1.doRun();
	    path1 = Solution1.getPath();
	    validite = path1.isValid();
	    System.out.println("chemin 4 (1) est " + validite);
	    

	    data3 = new ShortestPathData(graph, nodes[0], nodes[3], AI3);
	    Dijkstra3 = new DijkstraAlgorithm(data3);
	    Solution3 = Dijkstra1.doRun();
	    path3 = Solution3.getPath();
	    validite = path3.isValid();
	    System.out.println("chemin 4 (3) est " + validite);
	    
	    // Chemin 5 - trois arcs 
	    data = new ShortestPathData(graph, nodes[2], nodes[1], AI);
	    Dijkstra1 = new DijkstraAlgorithm(data);
	    Solution1 = Dijkstra1.doRun();
	    path1 = Solution1.getPath();
	    validite = path1.isValid();
	    System.out.println("chemin 4 (1) est " + validite);
	    

	    data3 = new ShortestPathData(graph, nodes[2], nodes[1], AI3);
	    Dijkstra3 = new DijkstraAlgorithm(data3);
	    Solution3 = Dijkstra1.doRun();
	    path3 = Solution3.getPath();
	    validite = path3.isValid();
	    System.out.println("chemin 4 (3) est " + validite);
	    

		System.out.println("fin test validite");

	    
    }
    //---------------------------- 2.1 Test des sc�narios avec oracle ------------------------------------------------------

    @Test
    public void testDoScenarioDistance() throws Exception {
    	LaunchTest.initAll();
    	
    	
		System.out.println("/-------------- Tests de scenarios en distance (Dijkstra) --------------/"+"\n");
		String carte = LaunchTest.dataDirectory.get(3)+LaunchTest.cartes.get(1)+ LaunchTest.cartes.get(3);
				
		System.out.println("Carte : " + LaunchTest.cartes.get(1)+" \n");
		System.out.println("Mode : distance\n");
		
		DijkstraScenarioTest test = new  DijkstraScenarioTest();
		int ori, dest;
		int mode = 0;
		
		System.out.println("----- Cas : chemin avec origine = destination -----");
		ori = 0;
		dest = 0;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("----- Cas : sommet origine inexistant -----");
		ori = -1;
		dest = 0;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("-----  Cas : sommet destination inexistant -----");
		ori = 0;
		dest = -1;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("----- Cas : sommets origine et destination inexistants -----");
		ori = -1;
		dest = -1;		
		test.testScenario(carte, mode, ori, dest);		
		
		System.out.println("----- Cas : chemin existant -----");
		ori = 2;
		dest = 53;		
		test.testScenario(carte, mode, ori, dest);
		
				
	}
	

    @Test
	public void testDoScenarioTemps() throws Exception {
    	LaunchTest.initAll();
		System.out.println("/-------------- Tests de scenarios en temps --------------/"+"\n");
		String carte = LaunchTest.dataDirectory.get(3)+LaunchTest.cartes.get(1)+ LaunchTest.cartes.get(3);
		
		
		System.out.println("Carte : " + LaunchTest.cartes.get(1)+" \n");
		System.out.println("Mode : distance\n");
		
		DijkstraScenarioTest test = new  DijkstraScenarioTest();
		int ori, dest;
		int mode = 1;
		
		System.out.println("----- Cas : chemin avec origine = destination -----");
		ori = 0;
		dest = 0;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("----- Cas : sommet origine inexistant -----");
		ori = -1;
		dest = 0;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("-----  Cas : sommet destination inexistant -----");
		ori = 0;
		dest = -1;		
		test.testScenario(carte, mode, ori, dest);
		
		System.out.println("----- Cas : sommets origine et destination inexistants -----");
		ori = -1;
		dest = -1;		
		test.testScenario(carte, mode, ori, dest);
		
		
		System.out.println("----- Cas : chemin existant -----");
		ori = 2;
		dest = 53;		
		test.testScenario(carte, mode, ori, dest);
		
	}
	
	
	
}
