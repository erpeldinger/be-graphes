package org.insa.performance;

import org.junit.Test;

/** 
 * Cette classe décrit le fonctionnement d'un test de performance
 * Cette classe sera appelée par la classe LaunchTest, quui permettra
 * de lancer plusieurs tests sur différentes cartes.
 *
*/

protected ArrayList<>

public class TestPerformance {

	public void doRun(String nomCarte, int typeEval, int algo) {
		//Dans le doRun : nomCarte, typeEvaluation, algo
	
	//On appelle dans l'ordre 
		//- EcritureFichier (EcritureDOnees) -> écrit les donnees du fichier txt à partir des infos carte
		//- LectureFichier (EcritureResultats) -> lis les données du fichier txt
		//- EcritureCalculs (EcritureResultats) -> exécute algo + écrit fichier txt
	
		//VOIR COMMENT C'EST FAIT DANS ERCRITURERESULTATS
		EcritureDonnees writeData = new EcritureDonnees(nomCarte, typeEval);
		EcritureResultats writeResult = new EcritureResultats(nomCarte, typeEval, algo);
		
		
		//String cheminDonnee = writeData.getNomFichier();
		//String cheminResultat = writeResult.getNomFichier().get();
		
	}
	

}
