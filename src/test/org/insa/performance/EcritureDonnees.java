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
 * Cette classe permet de rï¿½cupï¿½rer les informations d'une carte pour en extraire
 * les donnï¿½es et ï¿½crire ces informations dans un fichier texte.
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
	  
	public static final int nbPaires = 10;
	

	/** 
	 * Constructeur
	 */
	public EcritureDonnees(String nomCarte, int type, Graph graph) {		
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();
		this.EcritureFichier(nomCarte, type, graph);	
	}
	
	public String getNomFichier() { return nomFichier;}
	
	/** 
	 * Mï¿½thodes
	 */
	
	
	/**
	 * On veut rï¿½cupï¿½rer :
	 * - le nombre total de noeuds
	 * - le nombre d'arcs
	 * 
	 * On veut pouvoir crï¿½er des paires de sommets alï¿½atoirement.	 
	 */
	public void EcritureFichier(String nomCarte, int type, Graph graph) {
		
		//long init = System.currentTimeMillis();
		
		if(!(type==0 || type==1)) {
			   System.out.print("Type d'ï¿½valutation invalide \n");
		}		
		
		int origine, dest;
		Random rand = new Random();
		Path chemin;
		
		List<Node> listeNode = graph.getNodes();
		List<Node> nouveauChemin = new ArrayList<Node>();
		
		//CA VEUT DIRE QUOI ???
		//Les tests doivent ï¿½tre reproductibles, vous pouvez initialiser de maniï¿½re dï¿½terministe 
		//lï¿½utilisation dï¿½un gï¿½nï¿½rateur alï¿½atoire afin dï¿½obtenir les mï¿½mes jeux de donnï¿½es.
		
		//on rï¿½cupï¿½re les nodes, on regarde si peuvent crï¿½er chemins
		for (int i=0; i<nbPaires ; i++) {
			origine = rand.nextInt(graph.getNodes().size());
			dest = rand.nextInt(graph.getNodes().size());
			//nouveauChemin.add(listeNode.get(origine));
			//nouveauChemin.add(listeNode.get(dest));
			
			//test sans verif --ici ça ne marche plus
			this.listeOrigine.add(origine);
			this.listeDest.add(dest);
			
			/*chemin = Path.createShortestPathFromNodes(graph, nouveauChemin);
			
			//Si le chemin existe, on ajoute les sommets ï¿½ la liste
			if (chemin.isValid()) {
				listeOrigine.add(origine);
				listeDest.add(dest);
			}
			else {
				i--;
			}
			*/
			//nouveauChemin.clear();
			
		}

		System.out.println("fin for " + listeOrigine);
		
		if (type ==0) { //distance
			nomFichier = nomCarte+"_"+ nbPaires +"_distance_data.txt";			
		}		
		else { //temps
			nomFichier = nomCarte+"_"+ nbPaires +"_temps_data.txt";			
		}
		File file = new File(LaunchTest.dataDirectory.get(5) + nomFichier);
		// Crï¿½e le fichier s'il n'existe pas
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Ecrit le nom de la carte et le nombre de paires
			bw.write(nomCarte);
			bw.newLine();	
			bw.write(Integer.toString(type));
			System.out.println("type ecrit: " + type);
			bw.newLine();
			bw.write(Integer.toString(nbPaires));	
			System.out.println(" nbpaires ecrites : " + nbPaires);
			bw.newLine();
			
			//Ecrit les paires de sommets
			for (int i=0; i<nbPaires ;i++) {			
				bw.write(Integer.toString(listeOrigine.get(i)));
				bw.write(" ");
				bw.write(Integer.toString(listeDest.get(i)));
				bw.newLine();
			}
			
			bw.close();	
		}
	
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	
		//Calcule le temps d'exï¿½cution du CPU
		//long calculCPU = System.currentTimeMillis()-init ;
		
	}
	
	
	public String getnomFichier() {
		return this.nomFichier;
	}
	
}



