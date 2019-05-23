package org.insa.performance;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import org.insa.graph.Graph;

/** 
 * Cette classe permet de lire et récupérer les données d'un fichier texte
 * pour ensuite les utiliser afin de créer les tests de performance.
*/


public class LectureDonnees{
	
	/** 
	 * Attributs
	 */

	protected String nomCarte;
	protected int type;
	protected ArrayList<Integer> listeOrigine;
	protected ArrayList<Integer> listeDest;			
	Graph graph;
	
	
	/** 
	 * Constructeur
	 */
	
	public LectureDonnees(String nomFichier) {
		this.LectureFichier(nomFichier);
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();		
		
	}
	
	/** 
	 * Méthodes
	 */	
	
	
	// On récupère les données des fichiers textes pour faire 
	//les calculs des chemins
	
	//Scanner 
	//BufferedWriter
	
	//Lancer les calculs séparéments
	
	//On récupère dans carte : nom, type, sommets
	
	public void LectureFichier(String nomFichier) {
		
		try {
			Scanner sc = new Scanner(new File(nomFichier));
			int origine, dest, type;
			
			//Recupere le nom de la carte
			if (sc.hasNext()) {
				nomCarte = sc.nextLine();				
			}
			
			//Recupere le type d'évaluation
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
	
	public void EcritureResultats() {
		
	}
	
	
	
	
	
	

}
