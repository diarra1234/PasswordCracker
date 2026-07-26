# Résultats de tests — DictionaryHashCracker (Personne 2)

## Contexte

Ces tests valident, **de manière isolée** (sans passer par la fabrique ni
par l'application console, qui seront développées par Personne 4), le bon
fonctionnement de la stratégie `DictionaryHashCracker`.

Fichiers concernés :
- `HashCracker.java` — interface fournie par Personne 1 (copiée telle
  quelle, non modifiée).
- `DictionaryHashCracker.java` — implémentation développée par Personne 2.
- `dictionnaire.txt` — fichier dictionnaire d'exemple (10 mots).
- `DictionaryHashCrackerTest.java` — classe de test avec méthode `main`.

## Dictionnaire d'exemple utilisé (`dictionnaire.txt`)

| Mot          | Hash MD5                          |
|--------------|------------------------------------|
| bonjour      | f02368945726d5fc2a14eb576f7276c0   |
| secret       | 5ebe2294ecd0e0f08eab7690d2a6ee69   |
| admin        | 21232f297a57a5a743894a0e4a801fc3   |
| password     | 5f4dcc3b5aa765d61d8327deb882cf99   |
| azerty       | ab4f63f9ac65152575886860dde480a1   |
| test         | 098f6bcd4621d373cade4e832627b4f6   |
| motdepasse   | b6edd10559b20cb0a3ddaeb15e5267cc   |
| soleil       | 23206deb7eba65b3fbc80a2ffbc53c28   |
| chocolat     | caf973c16410b87b3a996405f421ec14   |
| ordinateur   | 5a4103053ca58b8bb883c67c12aaef03   |

## Compilation

```
javac HashCracker.java DictionaryHashCracker.java DictionaryHashCrackerTest.java
```

Compilation réalisée sans erreur ni avertissement (testé avec OpenJDK 21).

## Exécution des tests

```
java DictionaryHashCrackerTest
```

### Sortie obtenue

```
[OK] crack(5f4dcc3b...) doit retourner "password"
       -> résultat=password, tentatives=4, durée=59 ms
[OK] crack(5ebe2294...) doit retourner "secret"
       -> résultat=secret, tentatives=2, durée=1 ms
[OK] crack(f0236894...) doit retourner "bonjour"
       -> résultat=bonjour, tentatives=1, durée=0 ms
[OK] crack(ab4f63f9...) doit retourner "azerty"
       -> résultat=azerty, tentatives=5, durée=9 ms
[OK] crack(01234567...) doit retourner null (hash absent)
       -> résultat=null, tentatives=10, durée=6 ms
Erreur de lecture du dictionnaire : fichier_inexistant.txt (No such file or directory)
[OK] crack(...) avec un dictionnaire inexistant doit retourner null
       -> résultat=null, tentatives=0, durée=0 ms

=== Résumé : 6 test(s) réussi(s), 0 test(s) échoué(s) ===
```

## Analyse des résultats

| # | Cas de test                                   | Attendu                        | Obtenu    | Statut |
|---|------------------------------------------------|---------------------------------|-----------|--------|
| 1 | Hash de "password" (dernier mot du fichier)     | "password", 4 tentatives min.   | password  | OK |
| 2 | Hash de "secret" (2ᵉ mot du fichier)            | "secret"                        | secret    | OK |
| 3 | Hash de "bonjour" (1ᵉʳ mot du fichier)          | "bonjour"                       | bonjour   | OK |
| 4 | Hash de "azerty" (5ᵉ mot du fichier)            | "azerty"                        | azerty    | OK |
| 5 | Hash ne correspondant à aucun mot               | `null`, 10 tentatives (tout le dictionnaire parcouru) | null | OK |
| 6 | Dictionnaire inexistant (chemin invalide)       | `null`, message d'erreur affiché, aucune exception propagée | null | OK |

Points vérifiés :
- Le hash MD5 de chaque mot est calculé correctement (comparaison directe
  avec les hashs de référence calculés indépendamment via `md5sum`).
- Le nombre de tentatives correspond bien à la position du mot dans le
  fichier (recherche séquentielle, arrêt dès qu'une correspondance est
  trouvée).
- Le cas d'échec (hash absent) est géré : le dictionnaire entier est
  parcouru puis `null` est retourné.
- Le cas d'erreur (fichier introuvable) est géré proprement : aucune
  exception n'est propagée à l'appelant, un message d'erreur est affiché
  sur la sortie d'erreur, et `crack` retourne `null`.

## Conclusion

Les 6 tests passent avec succès. `DictionaryHashCracker` respecte le
contrat de l'interface `HashCracker` fournie par Personne 1 (retourne le
mot trouvé ou `null`) et est prêt à être intégré par Personne 4 via
`HashCrackerFactory`.
