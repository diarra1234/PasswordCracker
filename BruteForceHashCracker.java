public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;
    private int attempts;

    @Override
    public String crack(String hash) {
        attempts = 0;
        String targetHash = hash.trim().toLowerCase();

        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = tryAllCombinations(new char[length], 0, targetHash);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private String tryAllCombinations(char[] current, int index, String targetHash) {
        if (index == current.length) {
            String candidate = new String(current);
            attempts++;
            String candidateHash = HashUtils.md5(candidate);
            if (candidateHash.equals(targetHash)) {
                return candidate;
            }
            return null;
        }

        for (char c : ALPHABET.toCharArray()) {
            current[index] = c;
            String result = tryAllCombinations(current, index + 1, targetHash);
            if (result != null) {
                return result;
            }
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
        test("a", "0cc175b9c0f1b6a831c399e269772661");
        test("ab", "187ef4436122d1cc2f40dc2b92f0eba0");
        test("test", "098f6bcd4621d373cade4e832627b4f6");
        test(null, "ffffffffffffffffffffffffffffffff");
    }

    private static void test(String expectedWord, String hash) {
        BruteForceHashCracker cracker = new BruteForceHashCracker();

        long start = System.currentTimeMillis();
        String result = cracker.crack(hash);
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Hash testé      : " + hash);
        System.out.println("Résultat attendu: " + expectedWord);
        System.out.println(result != null
                ? "Password found: " + result
                : "Password not found");
        System.out.println("Tentatives      : " + cracker.getAttempts());
        System.out.println("Temps d'exécution: " + elapsed + " ms");
        System.out.println("------------------------------------------");
    }
}