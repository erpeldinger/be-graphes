package org.insa.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Graph;

/** 
 * 
 * Cette classe permet de lire les données d'une carte et de générer un 
 * fichier texte regroupant les données du graphe.
 * 
 */ 
	
//Automatiser lecture des fichiers .mapgr ?

public class Donnees {
	

	/** 
	 * Attributs
	 */

	protected String nomCarte;
	ShortestPathData data = getInputData();	
	Graph graph;	
	   
	protected static final int nbPaires = 125;
	public static final String[] dataDirectory = {"/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps", 
			"C:/Users/clariDocuments/3MIC/S2/graphes/Maps"};
	

	/** 
	 * Constructeur
	 */
	protected static final String[] cartes = {"fractal.mapgr", "midi-pyrenees.mapgr", "new-zealand.mapgr"};
	
	public Lecture(String nomCarte) {
		this.LectureFichier(nomCarte);
		
	}
	
	/** 
	 * Méthodes
	 */
	
	
	/**
	 * On veut récupérer :
	 * - le nombre total de noeuds
	 * - le nombre d'arcs
	 * 
	 * On veut pouvoir créer des paires de sommets aléatoirement.	 
	 */
	public void LectureFichier(String nomCarte) {
		
		try {
			//BufferWriter pour écrire, Scanner pour récupérer données
			/**Scanner sc = new Scanner(new File(nomCarte));*/
			int origine, dest;		
			Random rand = new Random();
			int sizeTab = data.getGraph().getNodes().size();
			
			
		
			for (int i =0 ; i<nbPaires; i++) {
				origine = rand.nextInt(nbPaires);
				dest = rand.nextInt(nbPaires);
				
				//on écrit dans le fichier
				//Carte
				//type
				//nb paires
				//paires

			}
			
			//On prend 125 tests, on en veut 100, comme ça on a une marge pour les tests. 
			//C'est pdt les tests qu'on supprimera les chemins impossibles
			
			//Vérifier que chemin entre 2 sommets existe
			
			//On récupère les id des sommets de la carte
			/**while(sc.hasNextInt()) {
				origine = sc.nextInt();
				this.listeOrigines.add(origine);
				//if() {}
			}*/
			
			//sc.close();
		}
		
		catch(Exception e) {
			
		} //FileNotFound
		
		
	}
	
	
	public String getnomCarte() {
		return this.nomCarte;
	}

	public ArrayList<Integer> getListeOrigines(){
		return this.listeOrigines;
	}

	public ArrayList<Integer> getListeDest(){
		return this.listeDest;
	}
	
}



