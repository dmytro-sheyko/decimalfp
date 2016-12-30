package sample;

public class Sample {
    public static void main(String[] args) {
        test0d();
        test0f();
    }

    static void test0d() {
        double d = 0.1;
        for (int i = 0; i < 10; i += 1, d += 0.1) {
            System.out.println(i + "\t" + Double.toHexString(d) + "\t" + Double.toString(d));
        }
    }

    static void test0f() {
        float f = 0.1f;
        for (int i = 0; i < 10; i += 1, f += 0.1f) {
            System.out.println(i + "\t" + Float.toHexString(f) + "\t" + Float.toString(f));
        }
    }
}
