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
 * Cette classe permet d'ï¿½crire dans un fichier texte??? les rï¿½sultats des tests de performance.
 * 
 * Format des donnï¿½es : 
 * - nom de la carte
 * - nombre de paires de sommets
 * - algorithme utilisï¿½
 * - "origine destination val_solution temps_cpu nb_explorï¿½s nbmarquï¿½s max tas"
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
		System.out.println("LectureFichier in");
		try {
			Scanner sc = new Scanner(new File(LaunchTest.dataDirectory.get(2) + nomFichier));
			int origine, dest;
			//Recupere le nom de la carte
			if (sc.hasNext()) {
				this.nomCarte = sc.nextLine();	
				System.out.println("lectureFichier nomcarte ok " + nomCarte);			
			}
			//Recupere le type d'ï¿½valuation
			if (sc.hasNextInt()) {
				this.type = sc.nextInt();	
				System.out.println("lectureFichier type eval ok " + type);			
			}
			//Recupere le nombre de paires
			if (sc.hasNextInt()) {
				this.nbPairesDonnees = sc.nextInt();	
				System.out.println("lectureFichier nombre paires ok " + type);			
			}
			//Recupere les paires de sommets (boucle usque fin du fichier)
			while(sc.hasNextInt()) {
				//recuperation du sommet origine
				origine = sc.nextInt();
				this.listeOrigine.add(origine);
				System.out.println("liste origine ok : " + listeOrigine);
				//Recuperation du  dest
				if (sc.hasNextInt()) {
					dest = sc.nextInt();
					this.listeDest.add(dest);
					System.out.println("liste dest ok : " + listeDest);
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

		System.out.println("Calculs in");
		//Si le type et l'algo ne sont pa bons
		if(!((type==0 || type==1) && (algo==0 || algo==1))) {
			   System.out.print("Type d'ï¿½valutation etou d'algorithme invalides \n");
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

		System.out.println("Creation fichier avant for ok");
		
		
		// Entete du fichier
		
		String nomFichierActuel = "";
		nomFichierActuel+= nomCarte+"_"+ nomEval + "_" + EcritureDonnees.nbPaires + "_" +nomAlgo+".txt";
		System.out.println("nomfichier " + nomFichierActuel);	
		nomFichier.add(nomFichierActuel);
		System.out.println("ajout nomfichier ok");
		File file = new File(LaunchTest.dataDirectory.get(2) + nomFichierActuel);
		// Crï¿½e le fichier s'il n'existe pas
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("Creation fichier ok");
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Ecrit le nom de la carte et le nombre de paires
			bw.write(nomCarte);
			System.out.println("carte ecrite : " + nomCarte);
			bw.newLine();	
			bw.write(nomEval);
			System.out.println("type ecrit : " + type);
			bw.newLine();
			bw.write(Integer.toString(EcritureDonnees.nbPaires));	
			System.out.println("nbpaires ecrites : " + EcritureDonnees.nbPaires);
			bw.newLine();
			bw.write(nomAlgo);	
			bw.newLine();
		
		//Données pour chaque paires
			
			for (int i=0; i<EcritureDonnees.nbPaires; i++) {
			//Ecrit les donnees sur la paire origine/destination correspondante
				//Ecrit les numeros des sommets
				bw.write(Integer.toString(listeOrigine.get(i)));
				bw.write(";");
				bw.write(Integer.toString(listeDest.get(i)));
				bw.write(";");
				System.out.println("couple orig dest ecrit : " + listeOrigine.get(i) + " - " + listeDest.get(i));
				
				//creation des data
				GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(LaunchTest.dataDirectory.get(1) + nomCarte + ".mapgr"))));
				Graph graph = reader.read();
				ArcInspector arc = ArcInspectorFactory.getAllFilters().get(0);
				ShortestPathData data = new ShortestPathData(graph, graph.get(listeOrigine.get(i)),graph.get(listeDest.get(i)), arc);
				long debut, total;
				double cost;
				System.out.println("data créées");
				
				//Si c'est Dijsktra
				if (algo == 0) {
				    DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
				    
				    debut = System.currentTimeMillis();
					ShortestPathSolution solutionDijkstra = Dijkstra.run();
					System.out.println("aldo Dijkstra run ok");
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
					System.out.println("valeur solution ecrite : " + (int)cost);
					
					//temps cpu
					bw.write(Integer.toString((int)total));
					bw.write(";");
					System.out.println("temps cpu ecrit : " + (int)total);
					
					//nb sommets explorÃ©s

					bw.write(Integer.toString(Dijkstra.getSomVisites()));
					bw.write(";");
					System.out.println("nb sommet explorés ecrit : " + Dijkstra.getSomVisites());
					
					//nb sommets marquÃ©s

					bw.write(Integer.toString(Dijkstra.getSomMarques()));
					bw.write(";");
					System.out.println("nb sommet marqués ecrit : " + Dijkstra.getSomMarques());
					
					//taille max du tas
					bw.write(Integer.toString(Dijkstra.getTailleTas()));
					bw.write(";");
					System.out.println("taille max tas ecrit : " + Dijkstra.getTailleTas());
				
					bw.newLine();	
				}
				
				//Si c'est AStar
				else if (algo == 1) {
					   AStarAlgorithm AStar = new AStarAlgorithm(data);
					   debut = System.currentTimeMillis();
					   ShortestPathSolution solutionAStar = AStar.run();
						System.out.println("aldo Astar run ok");
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
						
						//nb sommets explorÃ©s

						bw.write(Integer.toString(AStar.getSomVisites()));
						bw.write(";");
						//nb sommets marquÃ©s

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



