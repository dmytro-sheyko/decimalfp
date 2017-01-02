package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleCvt {
    public static void main(String[] args) {
        dec_test_f2d();
    }

    @DecimalFP
    static void dec_test_f2d() {
        System.out.println("dec_test_f2d");
        float f = 0.1f;
        double d = (double) f;
        System.out.println("\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        System.out.println("\t" + Double.toHexString(d) + "\t" + Double.toString(d));
    }
}
