Date : 13/05/2019
A corriger : 

// not� la derni�re fois, je ne sais pas � quoi �a correspond : - pb dans Path -> createShortestPathFromNodes -> throw IllegalArgumentException
- gestion de l'exception IOException dans DijkstraScenarioTest � v�rifier

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
- v�rifier le calcul de la distance/vitesse/temps (v�rifier unit�s)



Rq : Je n'ai fait que deux types de test (distance et temps) sur une seule carte mais on peut en faire plein d'autres !