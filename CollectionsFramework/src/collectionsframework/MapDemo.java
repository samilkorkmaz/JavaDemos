package collectionsframework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author skorkmaz
 */
public class MapDemo {

    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<>();
        String[] strArray = {"d", "a", "b", "a", "c", "c", "a", "b"};
        System.out.println("strArray = " + Arrays.toString(strArray));
        // Initialize frequency table from command line
        for (String key : strArray) {
            Integer freq = m.get(key);
            m.put(key, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " distinct words:");
        System.out.println(m);
    }
}
