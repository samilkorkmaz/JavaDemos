package main;
import main.common.AddInterface;
import main.common.MathOperations;
public class Add {
    public static void add() {
        AddInterface a = new MathOperations();
        System.out.println("3 + 5 = " + a.add(3, 5));
        //Note that multiply() method is not visible to a.
    }
}

