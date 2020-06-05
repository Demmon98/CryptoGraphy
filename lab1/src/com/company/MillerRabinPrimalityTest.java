package com.company;

import java.math.BigInteger;
import java.util.Random;

import static java.math.BigInteger.*;

public class MillerRabinPrimalityTest {
    private static final BigInteger THREE = BigInteger.valueOf(3);

    public static boolean testMillerRabin(BigInteger n, int maxIterations) {
        if (n.compareTo(THREE) < 0)            // n > 2
            return true;

        int t = 0;
        BigInteger m = n.subtract(ONE);        // n - 1 = 2^s * m, where m % 2 = 1

        // while m is even, make it odd dividing by two
        while (m.mod(TWO).equals(ZERO)) {
            t++;
            m = m.divide(TWO);
        }

        for (int i = 0; i < maxIterations; i++) {
            BigInteger a = uniformRandom(n.subtract(ONE));
            BigInteger u = a.modPow(m, n);    // x = a^m mod n

            // if u == 1 or u == (n - 1) then it may be prime
            if (u.equals(ONE) || u.equals(n.subtract(ONE)))
                continue;

            int j = 0;
            for (; j < t; j++) {
                u = u.modPow(TWO, n);    // u = u^2 mod n

                // n is definitely not prime
                if (u.equals(ONE))
                    return false;

                if (u.equals(n.subtract(ONE)))
                    break;
            }

            // None of the steps made x equal n - 1 then n is definitely not prime
            if (j == t)
                return false;
        }
        return true;
    }

    // Returns number between bottom and top
    private static BigInteger uniformRandom(BigInteger top) {
        Random rand = new Random();

        BigInteger result;
        do {
            result = new BigInteger(top.bitLength(), rand);
        } while (result.compareTo(BigInteger.TWO) < 0 || result.compareTo(top) > 0);
        return result;
    }


    public static void main(String[] args) {
        int k = 50;
        System.out.println("is prime 7499: " + testMillerRabin(BigInteger.valueOf(7499), k));
        System.out.println("is prime 6563: " + testMillerRabin(BigInteger.valueOf(6563), k));
        System.out.println("is prime 1277: " + testMillerRabin(BigInteger.valueOf(1277), k));
        System.out.println("is prime 719: " + testMillerRabin(BigInteger.valueOf(719), k));
        System.out.println("is prime 33145213: " + testMillerRabin(BigInteger.valueOf(33145213), k));
    }
}
