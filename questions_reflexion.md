# Questions de réflexion

## 1. Quels avantages apporte la fabrique simple ?

- **Découplage** : le code appelant (`Main`) ne dépend que de l'interface
  `HashCracker` et de `HashCrackerFactory`, jamais des classes concrètes
  (`DictionaryHashCracker`, `BruteForceHashCracker`).
- **Centralisation de la logique de création** : un seul endroit du
  programme sait comment instancier chaque stratégie, ce qui évite de
  disperser des `new ...` dans tout le code.
- **Lisibilité et simplicité d'usage** : créer une stratégie se résume à
  un appel unique, `HashCrackerFactory.create("DICO")`, sans que
  l'appelant ait besoin de connaître les détails de construction (chemin
  du dictionnaire, paramètres internes, etc.).
- **Facilité de test** : on peut tester la logique de sélection de
  stratégie indépendamment des stratégies elles-mêmes.

## 2. Quels sont ses inconvénients ?

- **Violation du principe Open/Closed** : ajouter une nouvelle méthode de
  cassage oblige à modifier le code existant de `HashCrackerFactory`
  (ajout d'un nouveau `case`), plutôt que d'étendre le système sans le
  modifier.
- **Couplage de la fabrique à toutes les implémentations concrètes** :
  `HashCrackerFactory` doit connaître et importer chaque classe concrète,
  ce qui la rend de plus en plus volumineuse à mesure que le nombre de
  stratégies augmente.
- **Un seul point de défaillance / de complexité** : toute la logique de
  création est concentrée dans une seule classe statique, qui peut devenir
  difficile à maintenir si le nombre de cas grandit.
- **Manque de flexibilité à l'exécution** : il n'est pas possible
  d'enregistrer dynamiquement une nouvelle stratégie sans recompiler la
  fabrique (contrairement à un registre ou une Factory Method
  polymorphe).

## 3. Que faut-il modifier lorsqu'une nouvelle stratégie est ajoutée ?

Pour ajouter une nouvelle stratégie (par exemple `HybridHashCracker`), il
faut :

1. Créer la nouvelle classe implémentant `HashCracker`.
2. **Modifier `HashCrackerFactory`** pour ajouter un nouveau `case` (ou
   condition) qui reconnaît l'identifiant de cette méthode et instancie la
   nouvelle classe.

Aucune autre classe n'a besoin d'être modifiée (ni `Main`, ni les autres
stratégies), ce qui montre déjà un découplage partiel — mais le point 2
montre bien la limite : **la fabrique elle-même n'est pas fermée à la
modification**.

## 4. La fabrique respecte-t-elle le principe Open/Closed ?

**Non.** Le principe Open/Closed stipule qu'une classe devrait être
*ouverte à l'extension* mais *fermée à la modification*. Or, dans cette
implémentation, `HashCrackerFactory` doit être **modifiée** (ajout d'un
`case` dans un `switch`/`if-else`) à chaque nouvelle stratégie ajoutée :
elle n'est donc pas fermée à la modification.

C'est une limite assumée et volontaire de cette v1 (la Simple Factory
n'est pas, par nature, extensible sans modification) : elle est
explicitement identifiée dans l'énoncé comme le point de départ du
mini-projet suivant, qui devra proposer une architecture plus conforme au
principe Open/Closed (par exemple via une Factory Method, un patron
Registry, ou un chargement des stratégies par réflexion/configuration).
