package org.insa.performance;


/** 
 * Ici, on utilisera la classe PerformanceTest pour lancer
 * plusieurs tests sur diff�rentes cartes
*/

public class LaunchTest {
	
	@Test
	public void lancementTest() {
		//On lance les tests avec les diff�rentes cartes, �valuations, algo
		
		ArrayList<TestPerformance> tableauTest = new Arraylist<TestPerformance>();
		
		//Dijkstra
		//Boucle pour le type d'�valuation (temps, distance)
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
