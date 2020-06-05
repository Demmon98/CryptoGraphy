package com.company;

import java.math.BigInteger;
import java.util.Random;

public class FermatPrimalityTest {
    private final static Random rand = new Random();

    private static BigInteger getRandomFermatBase(BigInteger n) {
        while (true) {
            final BigInteger a = new BigInteger(n.bitLength(), rand);

            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0)
                return a;
        }
    }

    public static boolean testFerma(BigInteger n, int maxIterations) {
        if (n.equals(BigInteger.ONE))
            return false;

        // Run Fermat Test for some numbers
        for (int i = 0; i < maxIterations; i++) {
            BigInteger a = getRandomFermatBase(n);
            a = a.modPow(n.subtract(BigInteger.ONE), n);    // a^(n - 1) mod n

            // If (a^(n - 1) mod n) != 1 then n is definitely not prime
            if (!a.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int k = 50;
        System.out.println("is prime 7499: " + testFerma(BigInteger.valueOf(7499), k));
        System.out.println("is prime 6563: " + testFerma(BigInteger.valueOf(6563), k));
        System.out.println("is prime 1277: " + testFerma(BigInteger.valueOf(1277), k));
        System.out.println("is prime 719: " + testFerma(BigInteger.valueOf(719), k));
        System.out.println("is prime 33145213: " + testFerma(BigInteger.valueOf(33145213), k));
    }
}
