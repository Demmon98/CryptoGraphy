package com.company;

import java.math.BigInteger;

import static java.math.BigInteger.*;

public class ModPowBin {

    public static BigInteger modPowBin(BigInteger a, BigInteger b, BigInteger mod) { //a^b
        if (b.equals(ZERO))
            return ONE;
        if (b.equals(ONE))
            return a.mod(mod);

        if (b.mod(TWO).equals(ONE)) {
            return a.multiply(modPowBin(a, b.subtract(ONE), mod)).mod(mod);
        }

        return modPowBin(a, b.divide(TWO), mod).pow(2).mod(mod);

    }

    public static void main(String[] args) {
        BigInteger a = ONE;
        BigInteger b = TWO;
        BigInteger c = TWO;
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) //System.out.println(i);
            a = a.add(TWO);
            for (int j = 0; j < 100; j++) {
                b = b.add(ONE);
                for (int k = 0; k < 100; k++) {
                    c = c.add(ONE);
                    if (!modPowBin(a, b, c).equals(a.modPow(b, c))) {
                        System.out.println("error a=" + a + "\tb=" + b + "\tc=" + c);
                    }
                }
            }
        }
        //a^b%c
        System.out.println("modPowBin(38, 45, 100) = " + modPowBin(BigInteger.valueOf(38), BigInteger.valueOf(45), BigInteger.valueOf(100)).toString());
        System.out.println("modPowBin(9, 2, 10) = " + modPowBin(TEN.subtract(ONE), TWO, TEN).toString());
        System.out.println("modPowBin(2, 10, 2) = " + modPowBin(TWO, TEN, TWO).toString());
    }

}
