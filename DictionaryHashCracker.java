import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryHashCracker implements HashCracker {

    private final String dictionaryPath;
    private int attempts;
    
    // Variables pour les tests
    private static int passed = 0;
    private static int failed = 0;

    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        attempts = 0;
        String targetHash = hash.trim().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.isEmpty()) {
                    continue;
                }
                attempts++;
                String candidateHash = HashUtils.md5(word);
                if (candidateHash.equals(targetHash)) {
                    return word;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du dictionnaire : " + e.getMessage());
            return null;
        }

        return null;
    }

    public int getAttempts() {
        return attempts;
    }

    // ==========================================
    // SECTION DE TESTS (Fusionnée depuis le Test)
    // ==========================================
    public static void main(String[] args) {
        String dictionaryPath = "dictionnaire.txt";

        testMotTrouve(dictionaryPath, "password", "5f4dcc3b5aa765d61d8327deb882cf99");
        testMotTrouve(dictionaryPath, "secret", "5ebe2294ecd0e0f08eab7690d2a6ee69");
        testMotTrouve(dictionaryPath, "bonjour", "f02368945726d5fc2a14eb576f7276c0");
        testMotTrouve(dictionaryPath, "azerty", "ab4f63f9ac65152575886860dde480a1");
        testMotNonTrouve(dictionaryPath, "0123456789abcdef0123456789abcdef");
        testDictionnaireInexistant();

        System.out.println();
        System.out.println("=== Résumé : " + passed + " test(s) réussi(s), "
                + failed + " test(s) échoué(s) ===");
    }

    private static void testMotTrouve(String dictionaryPath, String motAttendu, String hash) {
        DictionaryHashCracker cracker = new DictionaryHashCracker(dictionaryPath);
        long debut = System.nanoTime();
        String resultat = cracker.crack(hash);
        long duree = (System.nanoTime() - debut) / 1_000_000;

        boolean ok = motAttendu.equals(resultat);
        afficherResultat("crack(" + hash.substring(0, 8) + "...) doit retourner \"" + motAttendu + "\"",
                ok, resultat, cracker.getAttempts(), duree);
    }

    private static void testMotNonTrouve(String dictionaryPath, String hash) {
        DictionaryHashCracker cracker = new DictionaryHashCracker(dictionaryPath);
        long debut = System.nanoTime();
        String resultat = cracker.crack(hash);
        long duree = (System.nanoTime() - debut) / 1_000_000;

        boolean ok = (resultat == null);
        afficherResultat("crack(" + hash.substring(0, 8) + "...) doit retourner null (hash absent)",
                ok, resultat, cracker.getAttempts(), duree);
    }

    private static void testDictionnaireInexistant() {
        DictionaryHashCracker cracker = new DictionaryHashCracker("fichier_inexistant.txt");
        String resultat = cracker.crack("5f4dcc3b5aa765d61d8327deb882cf99");
        boolean ok = (resultat == null);
        afficherResultat("crack(...) avec un dictionnaire inexistant doit retourner null",
                ok, resultat, cracker.getAttempts(), 0);
    }

    private static void afficherResultat(String description, boolean ok, String resultat,
                                          int attempts, long dureeMs) {
        if (ok) {
            passed++;
        } else {
            failed++;
        }
        System.out.printf("[%s] %s%n", ok ? "OK" : "ECHEC", description);
        System.out.printf("       -> résultat=%s, tentatives=%d, durée=%d ms%n",
                resultat, attempts, dureeMs);
    }
}