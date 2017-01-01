package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleAdd {
    public static void main(String[] args) {
        dec_test_dadd();
        dec_test_fadd();
    }

    @DecimalFP
    static void dec_test_dadd() {
        System.out.println("dec_test_dadd");
        double d = 0.1;
        for (int i = 0; i < 10; i += 1, d += 0.1) {
            System.out.println(i + "\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        }
    }

    @DecimalFP
    static void dec_test_fadd() {
        System.out.println("dec_test_fadd");
        float f = 0.1f;
        for (int i = 0; i < 10; i += 1, f += 0.1f) {
            System.out.println(i + "\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        }
    }
}
