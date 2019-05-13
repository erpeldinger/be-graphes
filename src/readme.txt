Date : 13/05/2019
A corriger : 

// noté la dernière fois, je ne sais pas à quoi ça correspond : - pb dans Path -> createShortestPathFromNodes -> throw IllegalArgumentException
- gestion de l'exception IOException dans DijkstraScenarioTest à vérifier

A* :
- pas fait sorted()

DijkstraScenarioTest : 
- l46 : arc = null ??
- l47:  try/catch ??

A*ScenarioTest : 
- get(0) ou get(1) pour get filters ?

DijkstraScenarioTest : 
- get(0) ou get(1) pour get filters ?

DijkstraAlgorithm :
- modification l56, cf commentaire + l93 + l97

LabelStar : 
- vérifier le calcul de la distance/vitesse/temps (vérifier unités)



Rq : Je n'ai fait que deux types de test (distance et temps) sur une seule carte mais on peut en faire plein d'autres !