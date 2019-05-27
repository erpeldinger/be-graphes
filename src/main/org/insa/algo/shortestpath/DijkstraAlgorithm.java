package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;
import java.util.*;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	protected int nbSommetsVisites;
	protected int nbSommetsMarques;
	protected int tailleMaxTas;

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
		this.nbSommetsVisites = 0;
		this.nbSommetsMarques = 0;
		this.tailleMaxTas = 0;
	}

	//getter pour les tests de performances
	public int getSomVisites() { return this.nbSommetsVisites; }

	public int getSomMarques() { return this.nbSommetsMarques; }

	public int getTailleTas() { return this.tailleMaxTas; }


	//Cette mï¿½thode permet de crï¿½er un Label associï¿½ ï¿½ un Node
	protected Label creerLabel(Node sc, ShortestPathData data) {
		return new Label(sc);
	}

	//Cette mï¿½thode permet de crï¿½er un Label null
	protected Label creerLabel () {
		Label result = null;
		return result;
	}

	@Override
	public ShortestPathSolution doRun() {
		ShortestPathData data = getInputData();
		ShortestPathSolution solution = null;

		//Initialisation
		int indexDest = 0, iter = 0;
		ArrayList<Label> listeLabel = new ArrayList<Label>();
		BinaryHeap<Label> tas = new BinaryHeap<Label>();

		for (Node courantNode : this.data.getGraph().getNodes()) {
			listeLabel.add(creerLabel(courantNode, data));
			//listeLabel.add(new Label(courantNode));*

			//On recupere l'index du label du noeud de destination
			if(listeLabel.get(iter).getSommet() == data.getDestination()) {
				indexDest = iter;
			}
			iter++;

			if (courantNode.equals(data.getOrigin())) {
				listeLabel.get(listeLabel.size() - 1).setCost(0);
				tas.insert(listeLabel.get(listeLabel.size() - 1 ));
			}
		}

		//TESTER SI ORIGINE DEST OU GRAPH SONT NULL -> retourne solution infeasible



		//Label labelCourant, labelSuccesseur = null;
		Label labelDest = listeLabel.get(indexDest);
		Label labelCourant = creerLabel();
		Label labelSuccesseur = creerLabel();
		//int iter = 0;
		//int nbarc = 0;
		//int successeurTestes=  0;

		//Notifie les observateurs du depart de l'origine
		notifyOriginProcessed(data.getOrigin());        

		//Tant qu'il existe des sommets non marques
		while(!tas.isEmpty()) {

			//SI LE NODE EST DEJA DANS LE TAS ON MET A JOUR SON COUT

			//successeurTestes = 0;
			//iter++;
			
			labelCourant = tas.deleteMin();

			//labelCourant = tas.findMin();
			//tas.remove(labelCourant);
			//Notification aux obervateurs que l'on a atteint un noeud pour la premiï¿½re fois
			notifyNodeReached(labelCourant.getSommet());
			labelCourant.setMark();
			//sommets marques
			this.nbSommetsMarques+=1;
			
			//Si on arrive à la destination
			if(data.getDestination().equals(labelCourant.getSommet())) {
				break;
			}


			//On parcourt les successeurs y de x
			for (Arc arcCourant : labelCourant.getSommet().getSuccessors()) {
				// Test pour verifier que le chemin est autorise
				if (!data.isAllowed(arcCourant)) {
					continue;
				}

				labelSuccesseur = listeLabel.get(arcCourant.getDestination().getId());

				//successeurTestes++;
				//POUR LE TAS ON FAIT JUSTE GETCOST ET PAS TOTALCOST	
				/**
				 * if (labelSuccesseur.marked() == false && (labelSuccesseur.getCost() > (labelCourant.getCost() + arcCourant.getLength()))) {
					labelSuccesseur.setCost(labelCourant.getCost() + (int)arcCourant.getLength());
					labelSuccesseur.setPere(arcCourant);
				 */
				
				//Si le label n'existe pas, on le crée
				if(labelSuccesseur == null) {
					notifyDestinationReached(arcCourant.getDestination());
					labelSuccesseur = creerLabel(arcCourant.getDestination(), data);
					listeLabel.set(labelSuccesseur.getSommet().getId(),labelSuccesseur)  ;
				}
				
				double coutSucc = labelSuccesseur.getCost();
				double coutLabelCourant = labelCourant.getCost();
				double cout = Math.min(coutSucc, coutLabelCourant + data.getCost(arcCourant));
				
				//Si le successeur n'est pas marqué
				if (labelSuccesseur.marked() == false) {
					if (cout != coutSucc || (coutSucc == Double.POSITIVE_INFINITY)){
							
							
						/**
						 * if : labelSuccesseur.getTotalCost() > (labelCourant.getTotalCost() + data.getCost(arcCourant)) 
						 */
						
						/**
						if : (labelSuccesseur.getTotalCost() > (labelCourant.getCost() + data.getCost(arcCourant) 
						+ (labelSuccesseur.getTotalCost() - labelSuccesseur.getCost())))
						|| (labelSuccesseur.getCost() == Double.POSITIVE_INFINITY)
						*/
						
						labelSuccesseur.setCost(cout);
						labelSuccesseur.setPere(labelCourant.getSommet());
						
						
						//Si le label est deja dans le tas, on met a jour sa position
						if (labelSuccesseur.getInTas()) {
							tas.remove(labelSuccesseur);
						}
						else {
							labelSuccesseur.setInTas();
						}
						/**
						//on insere un label s'il n'Ã©tait pas deja dans la liste
						try {
							tas.remove(labelSuccesseur);
						}
						catch (ElementNotFoundException e){
						}*/
						tas.insert(labelSuccesseur);
						// sommets visites
						this.nbSommetsVisites+=1;
						// taille max tas
						if (tas.size() > tailleMaxTas) {
							tailleMaxTas = tas.size();
						}
					}

					//nbarc++;
					//System.out.println("label successeur " + labelSuccesseur);
				}

			}
			//System.out.println("nb successeurs testes Ã  l'iteration " + iter + " : " + successeurTestes); //DE 1 A 4 TESTS a chaque iter
			//System.out.println("taille du tas " + tas.size()); // ENVIRON 3 3 4 3 4 4 5 5 4 5 6 6 .... ~croisant jusqu'Ã  47 puis dÃ©croissant
		}

		// System.out.println("nb iter : " + iter);  // NB ITERATIONS : 1164
		//System.out.println("nb arcs " + nbarc);    // NB ARCS : 1262
		// TESTE SUR LA CARTE INSA : 1349 NODES ET 2887 ARCS

		
		//Si on arrive à la destination
		if(!listeLabel.get(data.getDestination().getId()).marked()) {
			System.out.println("Pas de solution connexe");
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
			return solution;
		}
		//Notification de la destination
		notifyDestinationReached(data.getDestination());

		//Reconstitution de la solution
		ArrayList<Node> solutionNode = new ArrayList<Node>();
		Label labelDestination=null, labelOrigine = null;
		// on recupere le bon label (bonne origine et destination)
		for (Label l : listeLabel) {
			if (l.getSommet().equals(data.getOrigin())) {
				//meme modif que l56
				labelOrigine = l;
				//labelOrigine = creerLabel(l.getSommet(), data);
			}
			if (l.getSommet().equals(data.getDestination())) {
				//idem
				labelDestination = l;
				//labelDestination = creerLabel(l.getSommet(), data);
			}
		}

		Label aux = labelDestination;

		while (!aux.equals(labelOrigine)) {
			if (aux.getPere().equals(null)) {
				solution = new ShortestPathSolution(data, Status.INFEASIBLE);
				return solution;
			}
			solutionNode.add(aux.getSommet());
			// on cherche le label associÃ© au noeud pere
			for (Label l : listeLabel) {
				if (l.getSommet().equals(aux.getPere())) {
					aux = l;
					break;
				}
			}
		}

		solutionNode.add(labelOrigine.getSommet());

		// Reverse the path...
		Collections.reverse(solutionNode);

		// Create the final solution.
		Path p = Path.createShortestPathFromNodes(this.data.getGraph(), solutionNode);


		solution = new ShortestPathSolution(data, Status.OPTIMAL, p);


		return solution;
	}
}