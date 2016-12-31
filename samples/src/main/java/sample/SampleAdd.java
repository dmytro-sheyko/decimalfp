package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleAdd {
    public static void main(String[] args) {
        bin_test0d();
        dec_test0d();
        bin_test0f();
        dec_test0f();
    }

    static void bin_test0d() {
        System.out.println("bin_test0d");
        double d = 0.1;
        for (int i = 0; i < 10; i += 1, d += 0.1) {
            System.out.println(i + "\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        }
    }

    @DecimalFP(true)
    static void dec_test0d() {
        System.out.println("dec_test0d");
        double d = 0.1;
        for (int i = 0; i < 10; i += 1, d += 0.1) {
            System.out.println(i + "\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        }
    }

    @DecimalFP(false)
    static void bin_test0f() {
        System.out.println("bin_test0f");
        float f = 0.1f;
        for (int i = 0; i < 10; i += 1, f += 0.1f) {
            System.out.println(i + "\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        }
    }

    @DecimalFP
    static void dec_test0f() {
        System.out.println("dec_test0f");
        float f = 0.1f;
        for (int i = 0; i < 10; i += 1, f += 0.1f) {
            System.out.println(i + "\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        }
    }
}
