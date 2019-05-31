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
 * Cette classe permet de r�cup�rer les informations d'une carte pour en extraire
 * les donn�es et �crire ces informations dans un fichier texte.
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
	 * M�thodes
	 */
	
	
	/**
	 * On veut r�cup�rer :
	 * - le nombre total de noeuds
	 * - le nombre d'arcs
	 * 
	 * On veut pouvoir cr�er des paires de sommets al�atoirement.	 
	 */
	public void EcritureFichier(String nomCarte, int type, Graph graph) {
		
		//long init = System.currentTimeMillis();
		
		if(!(type==0 || type==1)) {
			   System.out.print("Type d'�valutation invalide \n");
		}		
		
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
			origine = rand.nextInt(graph.getNodes().size());
			dest = rand.nextInt(graph.getNodes().size());
			this.listeOrigine.add(origine);
			this.listeDest.add(dest);
			
			
		}
		
		if (type ==0) { //distance
			nomFichier = nomCarte+"_"+ nbPaires +"_distance_data.txt";			
		}		
		else { //temps
			nomFichier = nomCarte+"_"+ nbPaires +"_temps_data.txt";			
		}
		File file = new File(LaunchTest.dataDirectory.get(5) + nomFichier);
		// Cr�e le fichier s'il n'existe pas
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
			bw.newLine();
			bw.write(Integer.toString(nbPaires));	
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
	
	}
	
	
	public String getnomFichier() {
		return this.nomFichier;
	}
	
}



