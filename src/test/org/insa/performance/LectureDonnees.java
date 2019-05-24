package org.insa.performance;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import org.insa.graph.Graph;

/** 
 * Cette classe permet de lire et r�cup�rer les donn�es d'un fichier texte
 * pour ensuite les utiliser afin de cr�er les tests de performance.
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
	
	public ArrayList<Integer> getListeOri () {return listeOrigine; }
	public ArrayList<Integer> getListeDest() { return listeDest;}
	
	/** 
	 * M�thodes
	 */	
	
	
	// On r�cup�re les donn�es des fichiers textes pour faire 
	//les calculs des chemins
	
	//Scanner 
	//BufferedWriter
	
	//Lancer les calculs s�par�ments
	
	//On r�cup�re dans carte : nom, type, sommets
	
	public void LectureFichier(String nomFichier) {
		
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
	
	public void EcritureResultats() {
		
	}
	
	
	
	
	
	

}
