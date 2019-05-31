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

public class AStarScenarioTest {

   @Test
   
   // Type correspond au type d'�valuation. On choisit 1 pour le temps, 0 pour la distance
   public void testScenario (String carte, int type, int origine, int dest) throws Exception {

	   // Create a graph reader
	   GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(carte))));
	   	   
	   // Read the graph.
	   Graph graph;	
	   
	   ArcInspector arc = null; 
	   try {
			graph = reader.read();
		   //Si le type d'�valuation n'existe pas 
		   if(!(type==0 || type==1)) {
			   System.out.print("Type d'evalutation invalide \n");
		   }
		   
		   //Si l'origine ou la destination ne sont pas dans le graphe
		   else if((origine<0) || (dest<0) || (origine>graph.size()-1) || (dest>graph.size()-1) ) {
	  		   System.out.print("Origine ou destination pas dans le graphe \n");
	  		   }
		   
		   //Si tous les arguments sont valides
		   else {
			   //Evaluation en distance (voiture)
			   if(type == 0) {
				   arc = ArcInspectorFactory.getAllFilters().get(0);
				   System.out.print("Type = distance \n");
			   }
			   
			   //Evaluation en temps (voiture)
			   else {
				   arc = ArcInspectorFactory.getAllFilters().get(2);				   
				   System.out.print("Type = temps \n");
			   }
			   
			   //Si l'origine et la destination sont les memes
			   
			   if(origine == dest) {
				   System.out.println("Origine = destination");
				   System.out.println("Co�t trajet : 0");			
			   }
			   //Creation des plus courts chemins
			   else {
				   ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(dest), arc);
					
				   AStarAlgorithm A = new AStarAlgorithm(data);
				   DijkstraAlgorithm D = new DijkstraAlgorithm(data);
				 		   
				   //On recupere les solutions obtenues avec Dijkstra et AStar
				   ShortestPathSolution solutionAStar = A.run();
				   ShortestPathSolution solutionDijkstra = D.run();
				   
				   double costAStar;		   
				   double costDijkstra;
				   		   
				   
				   //Si le chemin est inexistant
				   if(solutionAStar.getPath() == null) {
					  assertEquals(solutionDijkstra.getPath(), solutionAStar.getPath());
					  System.out.println("Pas de chemin possible\n"); 
				   }
				   
				   //Si le chemin existe
				   else {
					   if(type == 1) {		
						   
						   costDijkstra = solutionDijkstra.getPath().getMinimumTravelTime();
					   }
					   
					   else {
						   costAStar = solutionAStar.getPath().getLength();
						   costDijkstra = solutionDijkstra.getPath().getLength();
					   }
					   System.out.println("Co�t trajet : " + costDijkstra);
				   }
			   }
			   
		   }  
		   System.out.println("Origine : " + origine + "\n");
		   System.out.println("Destination : " + dest + "\n");
		  
	   }
	   
	   catch (Exception e) {
	   e.printStackTrace();
	   }	   
   }
}
