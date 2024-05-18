package Compiler;

import java.util.Random;

public class StdinStdout {
    public static String generateNumbers(long seed) {
        Random random = new Random(seed);
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        return number1 + " " + number2;
    }

    public static String verifyNumbers(long seed, int userResult) {
        Random random = new Random(seed);
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        int expectedResult = number1 * number2;
        if (userResult == expectedResult) {
            return "Résultat correct";
        } else {
            return "Erreur: Résultat incorrect";
        }
    }

    public static String getNumbersForVerification(long seed) {
        Random random = new Random(seed);
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        return number1 + " " + number2;
    }
}
