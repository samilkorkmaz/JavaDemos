package collectionsframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skorkmaz
 */
public class Traversing {
    
    private static void printList(List<?> li) {
        for (Iterator<?> it = li.iterator(); it.hasNext();) {
            System.out.println("it = " + it.next());
        }
    }

    public static void main(String[] args) {
        List<String> ls = new ArrayList<>(Arrays.asList("a", "b", "c"));
        String[] strArray = ls.toArray(new String[0]);
        for (int i = 0; i < strArray.length; i++) {
            System.out.println("s[" + i + "] = " + strArray[i]);
        }

        List<Integer> li = new ArrayList<>(Arrays.asList(1, 2, 3));
        for (Iterator<?> it = li.iterator(); it.hasNext();) {
            Integer ci = (Integer) it.next();
            if (ci == 2) {
                it.remove(); //Iterator.remove is the only safe way to modify a collection during iteration; the behavior is unspecified if the underlying collection is modified in any other way while the iteration is in progress.
            }
        }
        System.out.println("After iterator:");
        printList(li);

        //Try to remove an element inside for-each block:
        for (Integer i : li) {
            if (i == 3) {
                li.remove(i); //will result in ConcurrentModificationException at runtime.
            }
        }
        System.out.println("After for-each:");
        printList(li);
    }

}
