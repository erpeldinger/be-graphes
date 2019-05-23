package org.insa.performance;

import org.insa.graph.* ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

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
	//ShortestPathData data = getInputData();	
	Graph graph;	
	  
	//On prend 125 tests, on en veut  environ 100, comme ça on a une marge pour les tests.
	protected static final int nbPaires = 125;
	
	public static final String[] dataDirectory = {"/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps", 
			"C:/Users/clariDocuments/3MIC/S2/graphes/Maps"};
	
	/** 
	 * Constructeur
	 */
	protected static final String[] cartes = {"fractal.mapgr", "midi-pyrenees.mapgr", "new-zealand.mapgr"};
	
	public Donnees(String nomCarte, int type) {
		this.CreationDonnees(nomCarte, type);		
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
	public void CreationDonnees(String nomCarte, int type) {		
		
		if(!(type==0 || type==1)) {
			   System.out.print("Type d'évalutation invalide \n");
		}		
		
		int pairesCreees =0;
		int origine, dest;
		
		//ArrayList de Node ou <Integer ?
		ArrayList<Integer> listeOrigine = new ArrayList<Integer>();
		ArrayList<Integer> listeDest = new ArrayList<Integer>();		
		Random rand = new Random();
		Path chemin;
		
		List<Node> listeNode = graph.getNodes();
		List<Node> nouveauChemin;
		
		//CA VEUT DIRE QUOI ???
		//Les tests doivent être reproductibles, vous pouvez initialiser de manière déterministe 
		//l’utilisation d’un générateur aléatoire afin d’obtenir les mêmes jeux de données.
		
		//ESTIMATION DUREE CALCULS ??
		
		//on récupère les nodes, on regarde si peuvent créer chemins
		for (int i=0; i<nbPaires ; i++) {
			origine = rand.nextInt(pairesCreees);
			dest = rand.nextInt(pairesCreees);
			nouveauChemin.add(listeNode.get(origine));
			nouveauChemin.add(listeNode.get(dest));
			
			chemin = Path.createShortestPathFromNodes(graph, nouveauChemin);
			
			//Si le chemin existe, on ajoute les sommets à la liste
			if (chemin.isValid()) {
				listeOrigine.add(origine);
				listeDest.add(dest);
				pairesCreees++;
			}	
			nouveauChemin.clear();
		}
				
		if (type ==0) { //distance		
			File file = new File(nomCarte+"_"+ nbPaires +"_distance_data.txt");
		}		
		else { //temps
			File file = new File(nomCarte+"_"+ nbPaires +"_temps_data.txt");			
		}
			
		// Crée le fichier s'il n'existe pas
		if (!file.exists()) {
			 file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		//Ecrit le contenu
		bw.write("nomCarte");
		bw.newLine();
		
		bw.write(pairesCreees);			
		
		for (int i=0; i<pairesCreees ;i++) {			
			bw.write(listeOrigine.get(i));
			bw.write(" ");
			bw.write(listeDest.get(i));
			bw.newLine();
		}
		
		bw.close();	
	}
	
	
	public String getnomCarte() {
		return this.nomCarte;
	}
	
}



