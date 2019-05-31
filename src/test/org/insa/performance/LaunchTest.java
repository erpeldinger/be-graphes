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
 * plusieurs tests sur diff�rentes cartes
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
		dataDirectory.add("C:/Users/clari/Documents/3MIC/S2/graphes/Result/");
		dataDirectory.add("C:/Users/clari/Documents/3MIC/S2/graphes/Data/");
		cartes.add("carre-dense");
		cartes.add("midi-pyrenees");
		cartes.add("new-zealand");
		cartes.add(".mapgr");
	}


	public static void lancementTest() {

		GraphReader reader;
		Graph graph;

		//Dijkstra
		//Boucle pour le type d'�valuation (temps, distance)
		for (int i=0; i<2; i++) {
			//Boucle pour les cartes
			for (int l=0; l<cartes.size(); l++) {
				try {
					reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(dataDirectory.get(3) + cartes.get(l) + ".mapgr"))));
					graph = reader.read();
					EcritureDonnees D = new EcritureDonnees(cartes.get(l),i,graph);
					EcritureResultats F1 = new EcritureResultats(D, 0);
					EcritureResultats F2= new EcritureResultats(D, 1);
				}
				catch (Exception e) {}
			}			
		}
	}

	public static void main (String args[]) {
		initAll();
		lancementTest();
	}


}
