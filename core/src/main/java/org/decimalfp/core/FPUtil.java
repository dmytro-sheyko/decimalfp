package org.decimalfp.core;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author Dmytro.Sheyko
 */
public class FPUtil {
    private FPUtil() {}

    public static double bin_dadd(double a, double b) {
        return a + b;
    }

    public static double bin_dsub(double a, double b) {
        return a - b;
    }

    public static double bin_dmul(double a, double b) {
        return a * b;
    }

    public static double bin_ddiv(double a, double b) {
        return a / b;
    }

    public static double bin_drem(double a, double b) {
        return a % b;
    }

    public static float bin_fadd(float a, float b) {
        return a + b;
    }

    public static float bin_fsub(float a, float b) {
        return a - b;
    }

    public static float bin_fmul(float a, float b) {
        return a * b;
    }

    public static float bin_fdiv(float a, float b) {
        return a / b;
    }

    public static float bin_frem(float a, float b) {
        return a % b;
    }

    public static double dec_dadd(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b), MathContext.DECIMAL64).doubleValue();
        } else {
            return a + b;
        }
    }

    public static double dec_dsub(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b), MathContext.DECIMAL64).doubleValue();
        } else {
            return a - b;
        }
    }

    public static double dec_dmul(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b), MathContext.DECIMAL64).doubleValue();
        } else {
            return a * b;
        }
    }

    public static double dec_ddiv(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b) && b != 0) {
            return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), MathContext.DECIMAL64).doubleValue();
        } else {
            return a / b;
        }
    }

    public static double dec_drem(double a, double b) {
        if (Double.isFinite(a) && Double.isFinite(b) && b != 0) {
            return BigDecimal.valueOf(a).remainder(BigDecimal.valueOf(b), MathContext.DECIMAL64).doubleValue();
        } else {
            return a % b;
        }
    }

    public static float dec_fadd(float a, float b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b), MathContext.DECIMAL32).floatValue();
        } else {
            return a + b;
        }
    }

    public static float dec_fsub(float a, float b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b), MathContext.DECIMAL32).floatValue();
        } else {
            return a - b;
        }
    }

    public static float dec_fmul(float a, float b) {
        if (Double.isFinite(a) && Double.isFinite(b)) {
            return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b), MathContext.DECIMAL32).floatValue();
        } else {
            return a * b;
        }
    }

    public static float dec_fdiv(float a, float b) {
        if (Double.isFinite(a) && Double.isFinite(b) && b != 0) {
            return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), MathContext.DECIMAL32).floatValue();
        } else {
            return a / b;
        }
    }

    public static float dec_frem(float a, float b) {
        if (Double.isFinite(a) && Double.isFinite(b) && b != 0) {
            return BigDecimal.valueOf(a).remainder(BigDecimal.valueOf(b), MathContext.DECIMAL32).floatValue();
        } else {
            return a % b;
        }
    }
}
