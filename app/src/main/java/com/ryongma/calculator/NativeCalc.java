package com.ryongma.calculator;

public class NativeCalc {

    static {
        System.loadLibrary("nativecalc");
    }

    public static native double add(double a, double b);

    public static native double subtract(double a, double b);
}
