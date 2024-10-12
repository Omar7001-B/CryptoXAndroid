package com.example.hillcipheromar.utils;
import java.util.Random;

public class HillCipher {

    // Encrypt function that takes plaintext and key matrix
    public static String encrypt(String plainText, int[][] key) {
        plainText = prepareText(plainText);
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i += 2) {
            int[] block = {plainText.charAt(i) - 'a', plainText.charAt(i + 1) - 'a'};
            int[] encryptedBlock = matrixMultiply(block, key);
            cipherText.append((char) ((encryptedBlock[0] % 26) + 'a'));
            cipherText.append((char) ((encryptedBlock[1] % 26) + 'a'));
        }
        return cipherText.toString();
    }

    // Decrypt function that takes cipherText and key matrix
    public static String decrypt(String cipherText, int[][] key) {
        int[][] inverseKey = inverseMatrix(key);
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 2) {
            int[] block = {cipherText.charAt(i) - 'a', cipherText.charAt(i + 1) - 'a'};
            int[] decryptedBlock = matrixMultiply(block, inverseKey);
            plainText.append((char) ((decryptedBlock[0] % 26) + 'a'));
            plainText.append((char) ((decryptedBlock[1] % 26) + 'a'));
        }
        return plainText.toString();
    }

    // Key generation function
    public static String generateLetterKey() {
        Random random = new Random();
        int[][] key;
        do {
            key = new int[][]{{random.nextInt(26), random.nextInt(26)}, {random.nextInt(26), random.nextInt(26)}};
        } while (gcd(positiveDeterminant(key), 26) != 1);
        return "" + (char)(key[0][0] + 'a') + (char)(key[0][1] + 'a') + (char)(key[1][0] + 'a') + (char)(key[1][1] + 'a');
    }



    // Convert key string (letters) to a 2x2 matrix of integers
    public static int[][] letterKeyToMatrix(String key) {
        int[][] keyMatrix = new int[2][2];
        keyMatrix[0][0] = key.charAt(0) - 'a';
        keyMatrix[0][1] = key.charAt(1) - 'a';
        keyMatrix[1][0] = key.charAt(2) - 'a';
        keyMatrix[1][1] = key.charAt(3) - 'a';
        return keyMatrix;
    }




    // Function to prepare the input text: convert to lowercase, remove non-letters, and pad if necessary
    private static String prepareText(String text) {
        text = text.toLowerCase().replaceAll("[^a-z]", ""); // Remove non-letter characters
        if (text.length() % 2 != 0) {
            text += "x"; // Pad with 'x' if necessary
        }
        return text;
    }

    // Helper function to perform matrix multiplication
    private static int[] matrixMultiply(int[] vector, int[][] matrix) {
        int[] result = new int[2];
        result[0] = vector[0] * matrix[0][0] + vector[1] * matrix[0][1];
        result[1] = vector[0] * matrix[1][0] + vector[1] * matrix[1][1];
        return result;
    }

    // Function to compute the determinant of a 2x2 matrix
    public static int determinant(int[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
    public static int positiveDeterminant(int[][] matrix) {
        return (determinant(matrix) % 26 + 26) % 26;
    }

    // Function to compute the GCD of two numbers
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to calculate the inverse of the 2x2 matrix modulo 26
    private static int[][] inverseMatrix(int[][] matrix) {
        int det = determinant(matrix);
        int detInverse = modInverse(det, 26);
        int[][] inverse = {
                {matrix[1][1] * detInverse % 26, (-matrix[0][1] * detInverse) % 26},
                {(-matrix[1][0] * detInverse) % 26, matrix[0][0] * detInverse % 26}
        };

        // Ensure all values are positive
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverse[i][j] = (inverse[i][j] + 26) % 26;
            }
        }
        return inverse;
    }

    // Function to find the modular inverse of a number under mod 26
    private static int modInverse(int a, int m) {
        int x1 = 1, x0 = 0, m0 = m;
        while (a > 1) {
            int q = a / m;
            int temp = a;
            a = m;
            m = temp % m;
            temp = x0;
            x0 = x1 - q * x0;
            x1 = temp;
        }
        return (x1 + m0) % m0;
    }
}
