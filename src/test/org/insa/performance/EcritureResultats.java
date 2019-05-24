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
	
	Graph graph;	
	
	/**
	 * Constructeur
	 */
	
	public EcritureResultats(String nomCarte, int type, int algo) {
		this.LectureFichier(EcritureDonnees.getNomFichier());
		this.EcritureCalculs(nomCarte, type, algo);		
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDest = new ArrayList<Integer>();		
		this.nomCarte ="";
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
			String nomFichierActuel;
			nomFichierActuel= nomCarte+"_"+ nomEval + EcritureDonnees.nbPaires + nomAlgo + "_" +(i+1)+".txt";	
			nomFichier.add(nomFichierActuel);
			File file = new File(nomFichierActuel);
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
				
				//creation des data
				GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(nomCarte))));
				Graph graph = reader.read();
				ArcInspector arc = ArcInspectorFactory.getAllFilters().get(0);
				ShortestPathData data = new ShortestPathData(graph, graph.get(listeOrigine.get(i)),graph.get(listeDest.get(i)), arc);
				long debut, total;
				double cost;
				
				//Si c'est Dijsktra
				if (algo == 0) {
				    DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
				    
				    debut = System.currentTimeMillis();
					ShortestPathSolution solutionDijkstra = Dijkstra.run();
				    total = System.currentTimeMillis() - debut;
					if (type == 1) {
						cost = solutionDijkstra.getPath().getMinimumTravelTime();;
					}
					else {
						cost = solutionDijkstra.getPath().getLength();
					}
					
					//Ecrit les valeur calculees

					//valeur solution
					bw.write((int)cost);
					bw.write(";");
					
					//temps cpu
					bw.write((int)total);
					bw.write(";");
					
					//nb sommets explorés

					bw.write(Dijkstra.getSomVisites());
					bw.write(";");
					//nb sommets marqués

					bw.write(Dijkstra.getSomMarques());
					bw.write(";");
					
					//taille max du tas
					bw.write(Dijkstra.getTailleTas());
					bw.write(";");
					
				}
				
				//Si c'est AStar
				else if (algo == 1) {
					   AStarAlgorithm AStar = new AStarAlgorithm(data);
					   debut = System.currentTimeMillis();
					   ShortestPathSolution solutionAStar = AStar.run();
					   total = System.currentTimeMillis() - debut;
					   if (type == 1) {
						   cost= solutionAStar.getPath().getMinimumTravelTime();
					   }
					   else {
							cost = solutionAStar.getPath().getLength();
						}
					   //Ecrit les valeur calculees

						//valeur solution
						bw.write((int)cost);
						bw.write(";");
						
						//temps cpu
						bw.write((int)total);
						bw.write(";");
						
						//nb sommets explorés

						bw.write(AStar.getSomVisites());
						bw.write(";");
						//nb sommets marqués

						bw.write(AStar.getSomMarques());
						bw.write(";");
						
						//taille max du tas
						bw.write(AStar.getTailleTas());
						bw.write(";");
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
}



