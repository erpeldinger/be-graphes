package org.insa.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * 
 * Cette classe permet de lire les donn�es d'une carte et de g�n�rer un 
 * fichier texte regroupant les donn�es du graphe.
 * 
 */ 
	
//Automatiser lecture des fichiers .mapgr ?

public class Lecture {
	

	/** 
	 * Attributs
	 */

	protected String nomCarte;
	protected ArrayList<Integer> listeOrigines;
	protected ArrayList<Integer> listeDest ;
	
	protected static final int nbPaires = 100;
	public static final String[] dataDirectory = {"/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps", 
			"C:/Users/clariDocuments/3MIC/S2/graphes/Maps"};
	

	/** 
	 * Constructeur
	 */
	protected static final String[] cartes = {"fractal.mapgr", "midi-pyrenees.mapgr", "new-zealand.mapgr"};
	
	public Lecture(String nomCarte) {
		this.listeOrigines = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();
		this.LectureFichier(nomCarte);
		
	}
	
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
	public void LectureFichier(String nomCarte) {
		
		try {
			Scanner sc = new Scanner(new File(nomCarte));
			
			int origine, dest;
			
			//V�rifier que chemin entre 2 sommets existe
			
			//On r�cup�re les id des sommets de la carte
			while(sc.hasNextInt()) {
				origine = sc.nextInt();
				this.listeOrigines.add(origine);
				//if() {}
			}
			
			sc.close();
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



