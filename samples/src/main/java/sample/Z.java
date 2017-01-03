package sample;

public class Z {
    public static void main(String[] args) {
        {
            double[] dd = { 0.33, 0.34, 0.35, 0.111 };
            for (double d : dd) {
                double f1 = d * d;
                double f2 = dmul(d, d);
                System.out.println(d);
                System.out.println(f1);
                System.out.println(f2);
            }
        }
        {
            float[] dd = { 0.33f, 0.34f, 0.35f, 0.0000000111f };
            for (float d : dd) {
                float f1 = d * d;
                float f2 = fmul(d, d);
                System.out.println(d);
                System.out.println(f1);
                System.out.println(f2);
            }
        }
    }

    static double dmul(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            int dsa = Scale.getDesiredScale(a);
            a = Scale.scale(a, dsa);
            a = Math.rint(a);
            int dsb = Scale.getDesiredScale(b);
            b = Scale.scale(b, dsb);
            b = Math.rint(b);
            int dsr = -(dsa + dsb);
            double r = a * b;
            r = Math.rint(r);
            r = Scale.scale(r, dsr);
            return r;
        } else {
            return a * b;
        }
    }

    static float fmul(float a, float b) {
        if (Float.isFinite(a) && Float.isFinite(b)) {
            int dsa = Scale.getDesiredScale(a) - 4;
            a = Scale.scale(a, dsa);
            a = (float) Math.rint(a);
            int dsb = Scale.getDesiredScale(b) - 3;
            b = Scale.scale(b, dsb);
            b = (float) Math.rint(b);
            int dsr = -(dsa + dsb);
            float r = a * b;
            r = (float) Math.rint(r);
            r = Scale.scale(r, dsr);
            return r;
        } else {
            return a * b;
        }
    }
}
