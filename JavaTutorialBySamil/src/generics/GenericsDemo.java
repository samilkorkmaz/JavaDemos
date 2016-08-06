package generics;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author skorkmaz
 */
public class GenericsDemo {

    public static double sumOfList(List<? extends Number> list) { //This is equal to public static <T extends Number> double sumOfList(List<T> list)
        double s = 0.0;
        for (Number n : list) {
            s += n.doubleValue();
        }
        return s;
    }

    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(1, 2, 3);
        System.out.println("sum int = " + sumOfList(li));
        
        List<Double> ld = Arrays.asList(1.0, 2.0, 3.0);
        System.out.println("sum double = " + sumOfList(ld));
    }

}
