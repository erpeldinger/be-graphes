package org.insa.performance;

import org.insa.graph.* ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import org.insa.graph.Graph;

/** 
 * 
 * Cette classe permet d'�crire dans un fichier texte??? les r�sultats des tests de performance.
 * 
 * Format des donn�es : 
 * - nom de la carte
 * - nombre de paires de sommets
 * - algorithme utilis�
 * - "origine destination val_solution temps_cpu nb_explor�s nbmarqu�s max tas"
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
	
	//LECTURE fichier donnees
public void LectureFichier(String nomFichier) {
		String nomCarte = "";
		try {
			Scanner sc = new Scanner(new File(nomFichier));
			int origine, dest, type;
			//Recupere le nom de la carte
			if (sc.hasNext()) {
				nomCarte = sc.nextLine();				
			}
			//Recupere le type d'�valuation
			if (sc.hasNextInt()) {
				type = sc.nextInt();				
			}
			//Recupere les paires de sommets
			while(sc.hasNextInt()) {
				origine = sc.nextInt();
				this.listeOrigine.add(origine);
				//Si le nombre de sommets est correct
				if (sc.hasNextInt()) {
					dest = sc.nextInt();
					this.listeDest.add(dest);
				}
				//Si le nombre de sommets est impair
				//TRAITEMENT
				//else{}
			}
			sc.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	// ECRITURE fichier resultat
	public void EcritureCalculs(String nomCarte, int type, int algo) {
		
		//Si le type et l'algo ne sont pa bons
		if(!((type==0 || type==1) && (algo==0 || algo==1))) {
			   System.out.print("Type d'�valutation etou d'algorithme invalides \n");
		}	
		
		String nomAlgo = "", nomEval="";

		if (type == 0) { //distance
			nomEval = "distance";
		}
		else if (type == 1) {//temps
			nomEval = "temps";
		} 
		if(algo == 0) {
			nomAlgo = "dijkstra";
		}
		else if(algo == 1) {
			nomAlgo = "aStar";
		}
		
		//QUESTION : combien cree-t-on de fichiers de resultats : 1 par trajet !
		for (int i=0; i<EcritureDonnees.nbPaires; i++) {
			nomFichier= nomCarte+"_"+ nomEval + EcritureDonnees.nbPaires + nomAlgo + "_" +(i+1)+".txt";	
			File file = new File(nomFichier);
			// Cr�e le fichier s'il n'existe pas
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
				bw.write(EcritureDonnees.nbPaires);	
				bw.newLine();
				bw.write(nomAlgo);	
				bw.newLine();
			
			//Ecrit les donnees sur la paire origine/destination correspondante
				//Ecrit les numeros des sommets
				bw.write(listeOrigine.get(i));
				bw.write(";");
				bw.write(listeDest.get(i));
				bw.write(";");
				
				//Si c'est Dijsktra
				
				//Si c'est AStar
				
				//Ecrit les valeur calculees

				//valeur solution
				bw.write();
				bw.write(";");
				
				//temps cpu
				bw.write();
				bw.write(";");
				
				//nb sommets explorés

				bw.write();
				bw.write(";");
				//nb sommets marqués

				bw.write();
				bw.write(";");
				//taille max du tas
				
				
			
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
		//Les tests doivent �tre reproductibles, vous pouvez initialiser de mani�re d�terministe 
		//l�utilisation d�un g�n�rateur al�atoire afin d�obtenir les m�mes jeux de donn�es.
		
		//on r�cup�re les nodes, on regarde si peuvent cr�er chemins
		for (int i=0; i<nbPaires ; i++) {
			origine = rand.nextInt(pairesCreees);
			dest = rand.nextInt(pairesCreees);
			nouveauChemin.add(listeNode.get(origine));
			nouveauChemin.add(listeNode.get(dest));
			
			chemin = Path.createShortestPathFromNodes(graph, nouveauChemin);
			
			//Si le chemin existe, on ajoute les sommets � la liste
			if (chemin.isValid()) {
				listeOrigine.add(origine);
				listeDest.add(dest);
				pairesCreees++;
			}	
			nouveauChemin.clear();
		}
		
		//Calcule le temps d'ex�cution du CPU
		long calculCPU = System.currentTimeMillis()-init ;
	*/
	}
	
	/**
	public String getnomFichier() {
		return this.nomFichier;
	}
	*/
}



