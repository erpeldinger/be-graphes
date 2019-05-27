package org.insa.performance;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.insa.graph.Graph;
import org.insa.graph.GraphStatistics;
import org.insa.graph.Node;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

/** 
 * Ici, on utilisera la classe PerformanceTest pour lancer
 * plusieurs tests sur diffï¿½rentes cartes
 */

public class LaunchTest {

	public static ArrayList<String> dataDirectory;

	public static ArrayList<String> cartes;//cartes et chemin

	public static void initAll() {
		dataDirectory = new ArrayList<String>();
		cartes = new ArrayList<String>();
		dataDirectory.add("/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/");
		dataDirectory.add("C:\\Users\\Cerise\\Documents\\insa\\cours\\A3\\S2\\be_graphe\\cartes\\");
		dataDirectory.add("C:\\Users\\Cerise\\Documents\\insa\\cours\\A3\\S2\\be_graphe\\res\\");
		dataDirectory.add("C:/Users/clari/Documents/3MIC/S2/graphes/Maps/");
		cartes.add("carre-dense");
		cartes.add("midi-pyrenees");
		cartes.add("new-zealand");
		cartes.add(".mapgr");
	}


	public static void lancementTest() {

		GraphReader reader;
		Graph graph;

		//Dijkstra
		//Boucle pour le type d'ï¿½valuation (temps, distance)
		for (int i=0; i<2; i++) {
			//Boucle pour les cartes
			for (int l=0; l<cartes.size(); l++) {
				try {
					reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(dataDirectory.get(1) + cartes.get(l) + ".mapgr"))));
					graph = reader.read();
					EcritureDonnees D = new EcritureDonnees(cartes.get(l),i,graph);
					System.out.println("lancementTest ecriture données ok");
					EcritureResultats F1 = new EcritureResultats(D, 0); //dijkstra
					EcritureResultats F2= new EcritureResultats(D, 1); //astar

					System.out.println("lancementTest ecriture données et res ok");
				}
				catch (Exception e) {}
			}			
		}

		//AStar
		//Boucle pour les cartes
		/*for (int l=0; l<cartes.size(); l++) {
			try {
				reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(dataDirectory.get(1) + cartes.get(l) + ".mapgr"))));
				graph = reader.read();
				EcritureDonnees D = new EcritureDonnees(cartes.get(l),0,graph);
				//EcritureResultats F = new EcritureResultats(cartes.get(l), 0, 1);
				EcritureResultats F = new EcritureResultats(D, 1); //modif constructeur
			}
			catch (Exception e) {}
		}	
		 */		
	}

	public static void main (String args[]) {
		//main
		initAll();
		System.out.println("init fini");
		lancementTest();
		System.out.println("lancementtest fini");
	}


}
