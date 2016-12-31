package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleCvt {
    public static void main(String[] args) {
        bin_test_d2f();
        dec_test_d2f();
        bin_test_f2d();
        dec_test_f2d();
    }

    static void bin_test_d2f() {
        System.out.println("bin_test0d");
        double d = 0.1;
        float f = (float) d;
        System.out.println("\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        System.out.println("\t" + Float.toHexString(f) + "\t" + Float.toString(f));
    }

    @DecimalFP
    static void dec_test_d2f() {
        System.out.println("dec_test0d");
        double d = 0.1;
        float f = (float) d;
        System.out.println("\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        System.out.println("\t" + Float.toHexString(f) + "\t" + Float.toString(f));
    }

    static void bin_test_f2d() {
        System.out.println("bin_test0f");
        float f = 0.1f;
        double d = (double) f;
        System.out.println("\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        System.out.println("\t" + Double.toHexString(d) + "\t" + Double.toString(d));
    }

    @DecimalFP
    static void dec_test_f2d() {
        System.out.println("dec_test0f");
        float f = 0.1f;
        double d = (double) f;
        System.out.println("\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        System.out.println("\t" + Double.toHexString(d) + "\t" + Double.toString(d));
    }
}
