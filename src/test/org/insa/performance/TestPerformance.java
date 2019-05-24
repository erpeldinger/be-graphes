package org.insa.performance;

import org.junit.Test;

/** 
 * Cette classe d�crit le fonctionnement d'un test de performance
 * Cette classe sera appel�e par la classe LaunchTest, quui permettra
 * de lancer plusieurs tests sur diff�rentes cartes.
 *
*/

protected ArrayList<>

public class TestPerformance {

	public void doRun(String nomCarte, int typeEval, int algo) {
		//Dans le doRun : nomCarte, typeEvaluation, algo
	
	//On appelle dans l'ordre 
		//- EcritureFichier (EcritureDOnees) -> �crit les donnees du fichier txt � partir des infos carte
		//- LectureFichier (EcritureResultats) -> lis les donn�es du fichier txt
		//- EcritureCalculs (EcritureResultats) -> ex�cute algo + �crit fichier txt
	
		//VOIR COMMENT C'EST FAIT DANS ERCRITURERESULTATS
		EcritureDonnees writeData = new EcritureDonnees(nomCarte, typeEval);
		EcritureResultats writeResult = new EcritureResultats(nomCarte, typeEval, algo);
		
		
		//String cheminDonnee = writeData.getNomFichier();
		//String cheminResultat = writeResult.getNomFichier().get();
		
	}
	

}
