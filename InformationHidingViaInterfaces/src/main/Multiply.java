package main;
import main.common.MathOperations;
import main.common.MultiplyInterface;
public class Multiply {
    public static void multiply() {
        MultiplyInterface b = new MathOperations();
        System.out.println("3 * 5 = " + b.multiply(3, 5));
        //Note that add() method is not visible to b.
    }
}

