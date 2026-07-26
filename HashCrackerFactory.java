public class HashCrackerFactory {

    /**
     * Crée et retourne l'instance appropriée selon la méthode demandée.
     * @param method "BRUTE" ou "DICO"
     * @return Une instance implémentant HashCracker
     */
    public static HashCracker create(String method) {
        if ("DICO".equalsIgnoreCase(method)) {
            // On suppose que le dictionnaire est à la racine du projet
            return new DictionaryHashCracker("dictionnaire.txt");
        } else if ("BRUTE".equalsIgnoreCase(method)) {
            return new BruteForceHashCracker();
        } else {
            throw new IllegalArgumentException("Méthode non reconnue. Utilisez BRUTE ou DICO.");
        }
    }
}