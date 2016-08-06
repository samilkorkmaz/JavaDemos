package stringconcatenationdemo;

/**
 * String concatenation performance difference between String and StringBuilder.
 *
 * @author skorkmaz, August 2016
 */
public class StringConcatenationDemo {

    public static void main(String[] args) {
        int n = 10000;
        //String concatenation
        long t0_ns = System.nanoTime();
        String s = "";
        for (int i = 0; i < n; i++) {
            s = s + i;
        }
        long t1_ns = System.nanoTime();
        long dtS_ns = t1_ns-t0_ns;
        //StringBuilder concatenation
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i);
        }
        long t2_ns = System.nanoTime();
        long dtSB_ns = t2_ns-t1_ns;
        
        System.out.println("dtS_ns  = " + dtS_ns);
        System.out.println("dtSB_ns = " + dtSB_ns);
        System.out.println("factor  = " + Math.round(1.0*dtS_ns/dtSB_ns));
    }

}
