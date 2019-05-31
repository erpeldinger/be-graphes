package org.insa.performance;
import org.insa.algo.*;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.* ;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;

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

	protected ArrayList<String> nomFichier;	
	protected ArrayList<Integer> listeOrigine;
	protected ArrayList<Integer> listeDest;		
	protected ArrayList<Double> valeurSol;
	protected ArrayList<Long> tempsCpu;
	protected int nbMarques ;
	protected int maxTas ;
	
	protected String nomCarte;
	protected int type;
	protected int nbPairesDonnees;
	
	Graph graph;	
	
	/**
	 * Constructeur
	 */
	
	public EcritureResultats(EcritureDonnees D, int algo) {	
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();		
		this.nomFichier = new ArrayList<String>();		
		this.valeurSol = new ArrayList<Double>();		
		this.tempsCpu = new ArrayList<Long>();		
		this.nomCarte ="";
		this.LectureFichier(D.getNomFichier());
		this.EcritureCalculs(nomCarte, type, algo);	
	}
	
	
	//Methodes

	public ArrayList<String> getnomFichier() {return nomFichier;}
	
	/** On note que : 
	 * - type 0 = distance
	 * - type 1 = temps
	 * - algo 0 = Dijkstra
	 * - algo 1 = AStar
	 */
	
	//LECTURE fichier donnees
	public void LectureFichier(String nomFichier) {
		try {
			Scanner sc = new Scanner(new File(LaunchTest.dataDirectory.get(5) + nomFichier));
			int origine, dest;
			//Recupere le nom de la carte
			if (sc.hasNext()) {
				this.nomCarte = sc.nextLine();			
			}
			//Recupere le type d'�valuation
			if (sc.hasNextInt()) {
				this.type = sc.nextInt();		
			}
			//Recupere le nombre de paires
			if (sc.hasNextInt()) {
				this.nbPairesDonnees = sc.nextInt();				
			}
			//Recupere les paires de sommets (boucle usque fin du fichier)
			while(sc.hasNextInt()) {
				//recuperation du sommet origine
				origine = sc.nextInt();
				this.listeOrigine.add(origine);
				//Recuperation du  dest
				if (sc.hasNextInt()) {
					dest = sc.nextInt();
					this.listeDest.add(dest);
				}
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

		if (type == 0) {
			nomEval = "distance";
		}
		else if (type == 1) {
			nomEval = "temps";
		} 
		if(algo == 0) {
			nomAlgo = "dijkstra";
		}
		else if(algo == 1) {
			nomAlgo = "aStar";
		}

		
		
		// Entete du fichier
		
		String nomFichierActuel = "";
		nomFichierActuel+= nomCarte+"_"+ nomEval + "_" + EcritureDonnees.nbPaires + "_" +nomAlgo+".txt";	
		nomFichier.add(nomFichierActuel);
		File file = new File(LaunchTest.dataDirectory.get(4) + nomFichierActuel);
		// Cr�e le fichier s'il n'existe pas
		try {
			if (!file.exists()) {
				file.createNewFile();
			};
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Ecrit le nom de la carte et le nombre de paires
			bw.write(nomCarte);
			bw.newLine();	
			bw.write(nomEval);
			bw.newLine();
			bw.write(Integer.toString(EcritureDonnees.nbPaires));	
			bw.newLine();
			bw.write(nomAlgo);	
			bw.newLine();
		
		//Donn�es pour chaque paires
			
			for (int i=0; i<EcritureDonnees.nbPaires; i++) {
			//Ecrit les donnees sur la paire origine/destination correspondante
				//Ecrit les numeros des sommets
				bw.write(Integer.toString(listeOrigine.get(i)));
				bw.write(";");
				bw.write(Integer.toString(listeDest.get(i)));
				bw.write(";");
				
				//creation des data
				GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(LaunchTest.dataDirectory.get(3) + nomCarte + ".mapgr"))));
				Graph graph = reader.read();
				ArcInspector arc = ArcInspectorFactory.getAllFilters().get(0);
				ShortestPathData data = new ShortestPathData(graph, graph.get(listeOrigine.get(i)),graph.get(listeDest.get(i)), arc);
				long debut, total;
				double cost;
				
				//Si c'est Dijsktra
				if (algo == 0) {
				    DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
				    
				    debut = System.currentTimeMillis();
					ShortestPathSolution solutionDijkstra = Dijkstra.doRun();
				    total = System.currentTimeMillis() - debut;
					if (type == 1) {
						cost = solutionDijkstra.getPath().getMinimumTravelTime();;
					}
					else {
						cost = solutionDijkstra.getPath().getLength();
					}
					
					//Ecrit les valeur calculees

					//valeur solution
					bw.write(Integer.toString((int)cost));
					bw.write(";");
					
					//temps cpu
					bw.write(Integer.toString((int)total));
					bw.write(";");
					
					//nb sommets explorés

					bw.write(Integer.toString(Dijkstra.getSomVisites()));
					bw.write(";");
					
					//nb sommets marqués

					bw.write(Integer.toString(Dijkstra.getSomMarques()));
					bw.write(";");
					
					//taille max du tas
					bw.write(Integer.toString(Dijkstra.getTailleTas()));
					bw.write(";");
					bw.newLine();	
				}
				
				//Si c'est AStar
				else if (algo == 1) {
					   AStarAlgorithm AStar = new AStarAlgorithm(data);
					   debut = System.currentTimeMillis();
					   ShortestPathSolution solutionAStar = AStar.doRun();
					   total = System.currentTimeMillis() - debut;
					   if (type == 1) {
						   cost= solutionAStar.getPath().getMinimumTravelTime();
					   }
					   else {
							cost = solutionAStar.getPath().getLength();
						}
					   //Ecrit les valeur calculees

						//valeur solution
						bw.write(Integer.toString((int)cost));
						bw.write(";");
						
						//temps cpu
						bw.write(Integer.toString((int)total));
						bw.write(";");
						
						//nb sommets explorés

						bw.write(Integer.toString(AStar.getSomVisites()));
						bw.write(";");
						//nb sommets marqués

						bw.write(Integer.toString(AStar.getSomMarques()));
						bw.write(";");
						
						//taille max du tas
						bw.write(Integer.toString(AStar.getTailleTas()));
						bw.write(";");
						
						bw.newLine();	
				}	

			}
			bw.close();
		}
	
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}



