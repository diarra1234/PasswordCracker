/**
 * Interface commune à toutes les stratégies de cassage de mot de passe.
 *
 * Chaque implémentation reçoit un hash MD5 et tente de retrouver le mot de
 * passe en clair qui lui correspond, selon sa propre méthode (dictionnaire,
 * brute force, etc.).
 *
 * Ce contrat permet au reste du programme (fabrique, application console) de
 * manipuler n'importe quelle stratégie de manière polymorphe, sans connaître
 * son implémentation concrète.
 */
public interface HashCracker {

    /**
     * Tente de retrouver le mot de passe en clair correspondant au hash
     * fourni.
     *
     * @param hash le hash MD5 (32 caractères hexadécimaux) du mot de passe
     *             recherché
     * @return le mot de passe trouvé, ou {@code null} si aucune
     *         correspondance n'a été trouvée
     */
    String crack(String hash);
}
