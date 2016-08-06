package math;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author skorkmaz
 */
public class FloatingPointDemo {

    static double A(double X) {
        double Y, Z;  // [1]
        Y = X - 1.0;
        Z = Math.exp(Y);
        if (Z != 1.0) {
            Z = Y / (Z - 1.0); // [2]
        }
        return (Z);
    }

    static double B(double X) {
        double Y, Z;  // [1]
        Y = X - 1.0;
        Z = Math.exp(Y);
        if (Z != 1.0) {
            Z = Math.log(Z) / (Z - 1.0); // [2]
        }
        return (Z);
    }

    static double getEps() {
        double a = 1;
        double eps = 1;
        while (a != a + eps) {
            eps = eps / 2;
        }
        return eps;
    }

    public static void main(String[] args) {
        System.out.println("1/3 = " + 1 / 3);
        System.out.println("1d/3 = " + 1.0 / 3);
        System.out.println("NaN/0 = " + Double.NaN / 0.0);
        System.out.println("0/NaN = " + 0 / Double.NaN);
        System.out.println("NaN == NaN = " + (Double.NaN == Double.NaN));
        System.out.println("isNaN(NaN) = " + Double.isNaN(Double.NaN));
        System.out.println("parseDouble(\"5.3\") = " + Double.parseDouble("5.3"));
        System.out.println("1d/3 + 1d/3 + 1d/3 = " + (1d / 3 + 1d / 3 + 1d / 3));
        System.out.println("3 * 1d/3 = " + (3 * 1d / 3));
        System.out.println("0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 = " + (0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1));
        System.out.println("10 * 0.1 = " + (10 * 0.1));
        System.out.println("0.1 * 0.1 = " + (0.1 * 0.1));
        System.out.println("(int) 63d/9d = " + ((int) (62d / 9d)));
        System.out.println("(int) 0.63/0.09 = " + ((int) (0.62 / 0.09)));
        System.out.println("0/0 = " + (0d / 0d));
        System.out.println("sqrt(-1) = " + (Math.sqrt(-1)));
        System.out.println("inf * 0 = " + Double.NEGATIVE_INFINITY * 0);
        System.out.println("NaN^0 = " + Math.pow(Double.NaN, 0));
        double eps = getEps();
        System.out.println("eps = " + eps);
        System.out.println("1+eps = " + (1 + 2 * eps));
        System.out.println("Math.ulp(1d) = " + Math.ulp(1d));
        System.out.println("Math.nextUp(0d) = " + Math.nextUp(0d));
        System.out.println("Math.nextUp(1d) = " + Math.nextUp(1d));
        System.out.println("Math.nextUp(1e6d) = " + Math.nextUp(1e6d));
        BigDecimal bd1 = new BigDecimal(1d);
        BigDecimal bd2 = new BigDecimal(3d);
        //System.out.println("1/3 = " + bd1.divide(bd2)); //--> throws ArithmericException
        System.out.println("1/3 = " + bd1.divide(bd2, MathContext.DECIMAL128));
        System.out.println("1/3 = " + bd1.divide(bd2, MathContext.DECIMAL64));
        BigDecimal bd3 = new BigDecimal(Math.PI, MathContext.DECIMAL128);
        System.out.println("Pi = " + bd3);
    }

}
