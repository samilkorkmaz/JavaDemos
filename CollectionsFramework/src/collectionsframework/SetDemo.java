package collectionsframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Order is unimportant when checking for equality in sets.
 *
 * @author skorkmaz
 */
public class SetDemo {

    private static <E> void printCollection(Collection<E> c) {
        for (E e : c) {
            System.out.println("e = " + e);
        }
    }

    public static void main(String[] args) {
        //List<?> c = new ArrayList<>(Arrays.asList(30, 20, -1));
        List<?> c = new ArrayList<>(Arrays.asList("z", "y", "i", "z", "i"));
        Collection<?> noDups = new HashSet<>(c);
        Collection<?> orderedByValue = new TreeSet<>(c);

        System.out.println("Original list:");
        printCollection(c);
        System.out.println("Collection with no duplicates: ");
        printCollection(noDups);
        System.out.println("Collection ordered by value: ");
        printCollection(orderedByValue);
        System.out.println("c.equals(c) = " + c.equals(c));
        System.out.println("orderedByValue.equals(c) = " + orderedByValue.equals(c));
        System.out.println("orderedByValue.equals(noDups) = " + orderedByValue.equals(noDups));
    }

}
