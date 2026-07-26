import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitaire partagée pour le calcul de hash MD5.
 *
 * Centralise la logique de hachage utilisée par toutes les stratégies
 * ({@code DictionaryHashCracker}, {@code BruteForceHashCracker}, etc.)
 * afin d'éviter toute duplication de code, conformément à la contrainte
 * du sujet ("les duplications de code doivent être évitées").
 */
public final class HashUtils {

    // Constructeur privé : classe utilitaire non instanciable.
    private HashUtils() {
    }

    /**
     * Calcule le hash MD5 d'une chaîne de caractères et le retourne sous
     * forme hexadécimale (minuscules).
     *
     * @param input la chaîne à hacher
     * @return la représentation hexadécimale du hash MD5
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Impossible de calculer le hash MD5", e);
        }
    }
}