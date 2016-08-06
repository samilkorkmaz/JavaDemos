package main;
/**
 * Demonstrate information hiding via interfaces.<br>
 * /common/MathOperations class is used by two other classes (Add & Multiply) but 
 * each class uses different public methods. We use interfaces to hide unused methods.
 *
 * @author skorkmaz
 */
public class Main {
    public static void main(String[] args) {
        Add.add();
        Multiply.multiply();
    }
}

