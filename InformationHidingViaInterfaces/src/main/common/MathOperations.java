package main.common;
public class MathOperations implements AddInterface, MultiplyInterface{
    @Override
    public int add(int a, int b) {
        return a + b;
    }
    @Override
    public int multiply(int a, int b) {
        return a * b;
    }
}

