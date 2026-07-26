public class PasswordCracker {

    public static void main(String[] args) {
        // Vérification des arguments passés en ligne de commande
        if (args.length != 4 || !args[0].equals("-m") || !args[2].equals("-h")) {
            System.err.println("Usage incorrect.");
            System.err.println("Exemple : java PasswordCracker -m BRUTE -h e7247759c1633c0f9f1485f3690294a9");
            System.exit(1);
        }

        String method = args[1];
        String hash = args[3];

        try {
            // 1. Instanciation via la Fabrique Simple
            HashCracker cracker = HashCrackerFactory.create(method);

            // 2. Lancement du chronomètre (optionnel mais utile pour l'affichage des infos pertinentes)
            long startTime = System.currentTimeMillis();

            // 3. Exécution du cassage
            String result = cracker.crack(hash);

            long elapsedTime = System.currentTimeMillis() - startTime;

            // 4. Affichage du résultat attendu
            if (result != null) {
                System.out.println("Password found: " + result);
            } else {
                System.out.println("Password not found");
            }
            
            // Affichage des informations supplémentaires
            System.out.println("[Info] Temps d'exécution : " + elapsedTime + " ms");

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}