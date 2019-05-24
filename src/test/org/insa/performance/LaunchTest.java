package org.insa.performance;


/** 
 * Ici, on utilisera la classe PerformanceTest pour lancer
 * plusieurs tests sur différentes cartes
*/

public class LaunchTest {
	
	@Test
	public void lancementTest() {
		//On lance les tests avec les différentes cartes, évaluations, algo
		
		ArrayList<TestPerformance> tableauTest = new Arraylist<TestPerformance>();
		
		//Dijkstra
		//Boucle pour le type d'évaluation (temps, distance)
		for (int i=0; i<2; i++) {
			//Boucle pour les cartes
			for (int l=0; i<cartes.size()) {
				tableauTest.add(new TestPerformance);
				tableauTest.get(i).doRun(dataDirectory.get(0)+cartes.get(k), i, 0);
			//Dans le doRun : nomFichierEcritureDonnees, nomFichierEcritureREsultats, typeEvaluation, algo
			}			
		}
		
		//AStar
		//Boucle pour les cartes
		for (int l=0; i<cartes.size) {
			tableauTest.add(new TestPerformance);
			tableauTest.get(i).doRun(dataDirectory.get(0)+cartes.get(k), i);
		//Dans le doRun : nomFichierEcritureDonnees, nomFichierEcritureREsultats, typeEvaluation, algo
		}			
	}
	

}
