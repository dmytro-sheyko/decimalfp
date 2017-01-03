package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleMul {
    public static void main(String[] args) {
        dec_test_dmul();
        dec_test_fmul();
    }

    @DecimalFP
    static void dec_test_dmul() {
        System.out.println("dec_test_dmul");
        double[] dd = { 0.33, 0.34, };
        for (double d : dd) {
            double q = d * d;
            System.out.println(d + " * " + d + " -> " + q);
        }
    }

    @DecimalFP
    static void dec_test_fmul() {
        System.out.println("dec_test_fmul");
        float[] dd = { 0.33f, 0.34f, };
        for (float d : dd) {
            float q = d * d;
            System.out.println(d + " * " + d + " -> " + q);
        }
    }
}
