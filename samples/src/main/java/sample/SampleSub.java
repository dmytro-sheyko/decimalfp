package sample;

import org.decimalfp.annotation.DecimalFP;

public class SampleSub {
    public static void main(String[] args) {
        dec_test_dsub();
        dec_test_fsub();
    }

    @DecimalFP
    static void dec_test_dsub() {
        System.out.println("dec_test_dsub");
        double d = 1.0;
        for (int i = 0; i < 10; i += 1, d -= 0.1) {
            System.out.println(i + "\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        }
    }

    @DecimalFP
    static void dec_test_fsub() {
        System.out.println("dec_test_fsub");
        float f = 1.0f;
        for (int i = 0; i < 10; i += 1, f -= 0.1f) {
            System.out.println(i + "\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        }
    }
}
