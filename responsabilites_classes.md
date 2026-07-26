# Description des responsabilités des classes

## `HashCracker` (interface)
Définit le contrat commun à toute stratégie de cassage de mot de passe :
une seule méthode, `crack(String hash)`, qui retourne le mot de passe trouvé
ou `null`. Elle ne contient aucune logique — c'est le point d'abstraction qui
permet au reste du programme de traiter toutes les stratégies de façon
polymorphe.

## `DictionaryHashCracker` (classe concrète)
Implémente `HashCracker` selon la stratégie par dictionnaire. Responsable de :
- charger la liste de mots depuis le fichier dictionnaire ;
- calculer le hash MD5 de chaque mot ;
- comparer ce hash à celui recherché ;
- retourner le premier mot correspondant, ou `null` si aucun mot ne
  correspond.

## `BruteForceHashCracker` (classe concrète)
Implémente `HashCracker` selon la stratégie par force brute. Responsable de :
- générer systématiquement toutes les combinaisons possibles de l'alphabet
  `a-z`, de longueur 1 à 4 ;
- calculer le hash MD5 de chaque combinaison ;
- comparer ce hash à celui recherché ;
- retourner la combinaison correspondante, ou `null` si l'espace de
  recherche est épuisé sans résultat.

## `HashCrackerFactory` (fabrique)
Centralise la création des objets `HashCracker`. Responsable de :
- recevoir un identifiant de méthode (`"BRUTE"` ou `"DICO"`) ;
- instancier et retourner l'implémentation concrète correspondante ;
- être le seul point du programme où les classes concrètes
  (`DictionaryHashCracker`, `BruteForceHashCracker`) sont instanciées.

Cette centralisation respecte la contrainte du sujet : le programme principal
ne doit jamais instancier directement une classe concrète.

## Application console (`Main` / point d'entrée)
Responsable de :
- lire les arguments de la ligne de commande (`-m` pour la méthode, `-h`
  pour le hash) ;
- déléguer la création de la stratégie à `HashCrackerFactory` ;
- appeler `crack(hash)` sur l'objet obtenu ;
- afficher le résultat (`Password found: ...` ou `Password not found`) ainsi
  que les informations complémentaires utiles (temps d'exécution, nombre de
  tentatives).

Cette classe ne connaît que l'interface `HashCracker` et la fabrique — jamais
les classes concrètes.
