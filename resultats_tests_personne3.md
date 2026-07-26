# Résultats des tests — BruteForceHashCracker (Personne 3)

## Méthodologie

Tests effectués via `BruteForceHashCrackerTest.java`, sur 3 hashs MD5
correspondant à des mots de longueurs différentes (1, 2 et 4 caractères),
plus un cas de hash inexistant (mot non trouvé). Pour chaque cas, le mot
retrouvé, le nombre de tentatives et le temps d'exécution sont mesurés.

## Compilation

javac HashCracker.java HashUtils.java BruteForceHashCracker.java BruteForceHashCrackerTest.java -d out

Compilation réalisée sans erreur ni avertissement.

## Exécution des tests
java -cp out BruteForceHashCrackerTest

### Sortie obtenue

Hash testé      : 0cc175b9c0f1b6a831c399e269772661
Résultat attendu: a
Password found: a
Tentatives      : 1
Temps d'exécution: 35 ms
------------------------------------------
Hash testé      : 187ef4436122d1cc2f40dc2b92f0eba0
Résultat attendu: ab
Password found: ab
Tentatives      : 28
Temps d'exécution: 8 ms
------------------------------------------
Hash testé      : 098f6bcd4621d373cade4e832627b4f6
Résultat attendu: test
Password found: test
Tentatives      : 355414
Temps d'exécution: 2701 ms
------------------------------------------
Hash testé      : ffffffffffffffffffffffffffffffff
Résultat attendu: null
Password not found
Tentatives      : 475254
Temps d'exécution: 2704 ms
------------------------------------------

## Observations

- Le temps d'exécution et le nombre de tentatives augmentent fortement
  avec la longueur du mot recherché, car le nombre de combinaisons à
  tester croît exponentiellement (26^n combinaisons pour une longueur n).
- Le premier test ("a") prend un peu plus de temps (35 ms) malgré une
  seule tentative : ce délai correspond au démarrage de la JVM, pas au
  calcul lui-même. Les tests suivants sont plus rapides à tentative
  égale car la JVM est déjà initialisée.
- Le cas du hash inexistant est le plus coûteux (475 254 tentatives) :
  n'ayant aucune correspondance, le programme est contraint de tester
  l'intégralité des combinaisons possibles jusqu'à 4 caractères
  (26 + 26² + 26³ + 26⁴ = 475 254), ce qui confirme que l'algorithme
  parcourt bien l'espace complet des combinaisons sans erreur ni oubli.
- Le cas "test" (355 414 tentatives) illustre le pire cas d'un mot
  trouvé tardivement : il faut épuiser toutes les combinaisons de
  longueur 1 à 3 avant d'atteindre la longueur 4, puis parcourir une
  bonne partie des combinaisons de 4 lettres avant d'arriver
  alphabétiquement à "test".

## Conclusion

L'implémentation de `BruteForceHashCracker` fonctionne correctement sur
l'ensemble des cas testés (mots courts, mots longs, hash inexistant).
Le nombre de tentatives mesuré correspond exactement au calcul théorique
attendu, ce qui valide la couverture complète de l'espace de recherche
par l'algorithme. 