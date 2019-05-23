package org.insa.performance;

import org.insa.graph.* ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import org.insa.graph.Graph;

/** 
 * 
 * Cette classe permet de récupérer les informations d'une carte pour en extraire
 * les données et écrire ces informations dans un fichier texte.
 * 
 */ 
	
//Automatiser lecture des fichiers .mapgr ?

public class EcritureDonnees {
	

	/** 
	 * Attributs
	 */

	protected String nomFichier;	
	protected ArrayList<Integer> listeOrigine;
	protected ArrayList<Integer> listeDest;			
	Graph graph;	
	  
	//On prend 125 tests, on en veut  environ 100, comme ça on a une marge pour les tests.
	protected static final int nbPaires = 125;
	
	public static final String[] dataDirectory = {"/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps", 
			"C:/Users/clariDocuments/3MIC/S2/graphes/Maps"};
	
	/** 
	 * Constructeur
	 */
	protected static final String[] cartes = {"fractal.mapgr", "midi-pyrenees.mapgr", "new-zealand.mapgr"};
	
	public EcritureDonnees(String nomCarte, int type) {
		this.EcritureFichier(nomCarte, type);		
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();		
		
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
	public void EcritureFichier(String nomCarte, int type) {
		
		//long init = System.currentTimeMillis();
		
		if(!(type==0 || type==1)) {
			   System.out.print("Type d'évalutation invalide \n");
		}		
		
		int pairesCreees =0;
		int origine, dest;
		Random rand = new Random();
		Path chemin;
		
		List<Node> listeNode = graph.getNodes();
		List<Node> nouveauChemin = new ArrayList<Node>();
		
		//CA VEUT DIRE QUOI ???
		//Les tests doivent être reproductibles, vous pouvez initialiser de manière déterministe 
		//l’utilisation d’un générateur aléatoire afin d’obtenir les mêmes jeux de données.
		
		//on récupère les nodes, on regarde si peuvent créer chemins
		for (int i=0; i<nbPaires ; i++) {
			origine = rand.nextInt(graph.getNodes().size());
			dest = rand.nextInt(graph.getNodes().size());
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
			nomFichier = nomCarte+"_"+ pairesCreees +"_distance_data.txt";			
		}		
		else { //temps
			nomFichier = nomCarte+"_"+ pairesCreees +"_temps_data.txt";			
		}
		File file = new File(nomFichier);
		// Crée le fichier s'il n'existe pas
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Ecrit le nom de la carte et le nombre de paires
			bw.write("nomCarte");
			bw.newLine();	
			bw.write(type);
			bw.newLine();
			bw.write(pairesCreees);	
			bw.newLine();
			
			//Ecrit les paires de sommets
			for (int i=0; i<pairesCreees ;i++) {			
				bw.write(listeOrigine.get(i));
				bw.write(" ");
				bw.write(listeDest.get(i));
				bw.newLine();
			}
			
			bw.close();	
		}
	
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	
		//Calcule le temps d'exécution du CPU
		//long calculCPU = System.currentTimeMillis()-init ;
	}
	
	
	public String getnomFichier() {
		return this.nomFichier;
	}
	
}



