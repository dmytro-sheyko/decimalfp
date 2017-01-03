package sample;

public class X {
    public static void main(String[] args) {
        for (long i = 0; i < 2047; i += 1) {
            long ipm = i << 52;
            long ipx = ipm | ((1L << 52) - 1);
            double fpx = Double.longBitsToDouble(ipx);
            int ds = findDesiredScale(fpx);
            System.out.printf("%4d,", ds);
            if (i % 16 == 15) {
                System.out.println();
            }
//            System.out.println(i + "\t" + ds);
//            double spx = Scale.scale(fpx, ds);
//            System.out.println("\t" + fpx + "\t" + Double.toHexString(fpx) + "\t" + Math.ulp(fpx));
//            System.out.println("\t" + spx + "\t" + Double.toHexString(spx) + "\t" + Math.ulp(spx));
//            System.out.println("\t" + Math.rint(spx));
        }
    }

    static int findDesiredScale(float f) {
        float ulp = Math.ulp(f);
        if (ulp == 1.0f) {
            return 0;
        } else if (ulp < 1.0f) {
            int i = 1;
            while (true) {
                float t = Scale.scale(f, i);
                ulp = Math.ulp(t);
                if (ulp > 1.0f) {
                    return i - 1;
                } else if (ulp == 1.0f) {
                    return i;
                }
                i += 1;
            }
        } else if (ulp > 1.0f) {
            int i = -1;
            while (true) {
                ulp = Math.ulp(Scale.scale(f, i));
                if (ulp <= 1.0f) {
                    return i;
                }
                i -= 1;
            }
        } else {
            throw new AssertionError();
        }
    }

    static int findDesiredScale(double f) {
        double ulp = Math.ulp(f);
        if (ulp == 1.0) {
            return 0;
        } else if (ulp < 1.0) {
            int i = 1;
            while (true) {
                ulp = Math.ulp(Scale.scale(f, i));
                if (ulp > 1.0) {
                    return i - 1;
                } else if (ulp == 1.0) {
                    return i;
                }
                i += 1;
            }
        } else if (ulp > 1.0) {
            int i = -1;
            while (true) {
                ulp = Math.ulp(Scale.scale(f, i));
                if (ulp <= 1.0) {
                    return i;
                }
                i -= 1;
            }
        } else {
            throw new AssertionError();
        }
    }

    static void test(float f) {
        System.out.println(f + "\t" + Float.toHexString(f));
        int s = Scale.getDesiredScale(f);
        float sf = Scale.scale(f, s);
        float ulp = Math.ulp(sf);
        System.out.print("\t" + s + ":\t" + sf + "\t" + Float.toHexString(sf) + "\t" + ulp);
        if (ulp > 1.0 || ulp < 0.0625) {
            System.out.print("\t!!!!");
        }
        System.out.println();
    }
}
