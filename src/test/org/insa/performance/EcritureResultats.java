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
 * Cette classe permet d'écrire dans un fichier texte??? les résultats des tests de performance.
 * 
 * Format des données : 
 * - nom de la carte
 * - nombre de paires de sommets
 * - algorithme utilisé
 * - "origine destination val_solution temps_cpu nb_explorés nbmarqués max tas"
 * 
 */ 


public class EcritureResultats {
	

	/** 
	 * Attributs
	 */

	protected String nomFichier;	
	protected ArrayList<Integer> listeOrigine;
	protected ArrayList<Integer> listeDest;		
	protected ArrayList<Double> valeurSol;
	protected ArrayList<Long> tempsCpu;
	protected int nbMarques ;
	protected int maxTas ;
	
	Graph graph;	
	
	/**
	 * Constructeur
	 */
	
	public EcritureResultats(String nomCarte, int type, int algo) {
		this.EcritureCalculs(nomCarte, type, algo);		
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();		
		
	}
	
	
	//Methodes
	
	/** On note que : 
	 * - type 0 = distance
	 * - type 1 = temps
	 * - algo 0 = Dijkstra
	 * - algo 1 = AStar
	 */
	public void EcritureCalculs(String nomCarte, int type, int algo) {
		
		//Si le type et l'algo ne sont pa bons
		if(!((type==0 || type==1) && (algo==0 || algo==1))) {
			   System.out.print("Type d'évalutation etou d'algorithme invalides \n");
		}	
		
		String nomAlgo, nomEval;

		if (type == 0) { //distance
			nomEval = "distance";
		}
		else if (type == 1) {//temps
			nomEval = "temps";
		} 
		else if(algo == 0) {
			nomAlgo = "dijkstra";
		}
		else if(algo == 1) {
			nomAlgo = "aStar";
		}
		
		//QUESTION : est-ce qu'on cree un fichier texte ou autre pour traiter les données ?
		
		//QUESTION : combien cree-t-on de fichiers de resultats : 1 par trajet ou 1 par carte ?
		for (int i=0; i<nbPaires; i++) {
			nomFichier= nomCarte+"_"+ nomEval + nbPaires + nomAlgo + "_" +(i+1)+".txt";			
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
			bw.write(nbPaires);	
			bw.newLine();
			bw.write(nomAlgo);	
			bw.newLine();
			
			//Ecrit les donnees sur les sommets
			for (int i=0; i<nbPaires ;i++) {
				//Ecrit les numeros des sommets
				bw.write(listeOrigine.get(i));
				bw.write(" ");
				bw.write(listeDest.get(i));
				
				//Si c'est Dijsktra
				
				//Si c'est AStar
				
				//Ecrit les valeur calculees
				
				
				
				
			}
			
			bw.close();	
		}
	
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	
		
		
		
		/**
		long init = System.currentTimeMillis();
		
				
		
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
		
		//Calcule le temps d'exécution du CPU
		long calculCPU = System.currentTimeMillis()-init ;
	*/
	}
	
	/**
	public String getnomFichier() {
		return this.nomFichier;
	}
	*/
}



