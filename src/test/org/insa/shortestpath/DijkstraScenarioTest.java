package org.insa.shortestpath;

import org.insa.algo.*;
import org.insa.graph.*;
import org.insa.graph.Graph;
import org.insa.algo.shortestpath.*;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;



import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraScenarioTest {

   @Test
   
   // Type correspond au type d'évaluation. On choisit 0 pour le temps, 1 pour la distance
   public void testScenario (String carte, int type, int origine, int dest) {
	
	 //A voir ?????????????????
	   // Create a graph reader.
	   GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(carte))));
	   // Read the graph.
	   Graph graph = reader.read();
	   ArcInspector arc;   
	   
	   
	   //Si le type d'évaluation n'existe pas 
	   if(!(type==0 || type==1)) {
		   System.out.print("Type d'évalutation invalide \n");
	   }
	   //Si l'origine ou la destination ne sont pas dans le graphe
	   else {
		   if((origine<0) || (dest<0) || (origine>graph.size()-1) || (dest>graph.size()-1) ) {
			   //!(graph.getNodes().contains(origine) || graph.getNodes().contains(dest))) --> non car dest et origine = int   
	  		   System.out.print("Origine ou destination pas dans le graphe \n");
		   }
		   //Si tous les arguments sont valides
		   else {
			   //Evaluation en temps (voiture)
			   if(type == 0) {
				   arc = ArcInspectorFactory.getAllFilters().get(2);
				   System.out.print("Type = temps \n");
			   }
			   
			   //Evaluation en distance (voiture)
			   else {
				   arc = ArcInspectorFactory.getAllFilters().get(1);				   
				   System.out.print("Type = distance \n");
			   }
		   }	
	   }	  
	   System.out.println("Origine : " + origine + "\n");
	   System.out.println("Destination : " + dest + "\n");
		
	   //Si l'origine et la destination sont les mêmes
	   
	   if(origine == dest) {
		   System.out.println("Origine = destination");
		   System.out.println("Coût trajet : 0");			
	   }
	   //Création des plus courts chemins
	   else {
		   //A voir ?????????????????
		   ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(dest), arc);
			
		   BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
		   DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		 		   
		   //On récupère les solutions obtenues avec Dijkstra et Bellman-Ford
		   ShortestPathSolution solutionDijkstra = D.run();
		   ShortestPathSolution solutionBF = B.run();
		   		   
		   double costDijkstra;
		   double costBF;		   
		   
		   //Si le chemin est inexistant
		   if(solutionDijkstra.getPath() == null) {
			  assertEquals(solutionBF.getPath(), solutionDijkstra.getPath());
			  System.out.println("Pas de chemin possible\n"); 
		   }
		   
		   //Si le chemin existe
		   else {
			   if(type == 0) {		
				   costDijkstra = solutionDijkstra.getPath().getMinimumTravelTime();
				   costBF = solutionBF.getPath().getMinimumTravelTime();
			   }
			   
			   else {
				   costDijkstra = solutionDijkstra.getPath().getLength();
				   costBF = solutionBF.getPath().getLength();
			   }
			   System.out.println("Coût trajet : " + costDijkstra);
			   // ??? assertEquals(costBF, costDijkstra, 1e6);
		   }
	   }
   }

}
