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
		
		/**
		//Si les sommets ne sont pas dans le graphe
		if (data.getOrigin().getId() > this.data.getGraph().getNodes().size()) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
			return solution;
		}
	*/
		
		for (Node courantNode : this.data.getGraph().getNodes()) {
			listeLabel.add(creerLabel(courantNode, data));

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

		Label labelCourant = creerLabel();
		Label labelSuccesseur = creerLabel();

		//Notifie les observateurs du depart de l'origine
		notifyOriginProcessed(data.getOrigin());        

		//Tant qu'il existe des sommets non marques
		while(!tas.isEmpty()) {

			labelCourant = tas.deleteMin();

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
						labelSuccesseur.setCost(cout);
						labelSuccesseur.setPere(labelCourant.getSommet());						

						//Si le label est deja dans le tas, on met a jour sa position
						if (labelSuccesseur.getInTas()) {
							tas.remove(labelSuccesseur);
						}
						else {
							labelSuccesseur.setInTas();
						}

						tas.insert(labelSuccesseur);
						// sommets visites
						this.nbSommetsVisites+=1;
						// taille max tas
						if (tas.size() > tailleMaxTas) {
							tailleMaxTas = tas.size();
						}
					}
				}
			}
		}

		//Si on n'arrive pas à la destination
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
		//On recupere le bon label (bonne origine et destination)
		for (Label l : listeLabel) {
			if (l.getSommet().equals(data.getOrigin())) {
				labelOrigine = l;
			}
			if (l.getSommet().equals(data.getDestination())) {
				labelDestination = l;
			}
		}
		Label aux = labelDestination;

		while (!aux.equals(labelOrigine)) {
			if (aux.getPere().equals(null)) {
				solution = new ShortestPathSolution(data, Status.INFEASIBLE);
				return solution;
			}
			solutionNode.add(aux.getSommet());
			//On cherche le label associe au noeud pere
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