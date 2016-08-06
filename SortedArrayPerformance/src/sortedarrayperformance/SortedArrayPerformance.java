package sortedarrayperformance;

import java.util.Arrays;
import java.util.Random;

/**
 * Performance difference when an array is unsorted and sorted.<br/>
 * Reference: http://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-an-unsorted-array
 *
 * @author skorkmaz, Dec 2015
 */
public class SortedArrayPerformance {
    
    private static final int SUM_TRESHOLD = 500; //Values larger than this will be used in calculating the sum.
    private static final int MAX_INDEX = 100000;

    public static void main(String[] args) {
        System.out.println("Calculating...");
        int arraySize = 32768;
        int data[] = new int[arraySize];

        Random rnd = new Random(0);
        for (int c = 0; c < arraySize; ++c) {
            int i = rnd.nextInt(); //an integer between MIN and MAX integer.
            data[c] = i % 2*SUM_TRESHOLD;
        }
        
        long start_ns = System.nanoTime();
        long sumUnsorted = sum(data);
        System.out.println("sumUnsorted = " + sumUnsorted);
        System.out.println("dt          = " + ns2s((System.nanoTime() - start_ns)));

        start_ns = System.nanoTime();
        long sumTernary = sumUsingTernary(data);
        System.out.println("sumTernary  = " + sumTernary);
        System.out.println("dt          = " + ns2s((System.nanoTime() - start_ns)));

        start_ns = System.nanoTime();
        // Bit shifting eliminates if check in sum which improves performance by 2x.
        long sumBitShift = sumBitShift(data);
        System.out.println("sumBitShift = " + sumBitShift);
        System.out.println("dt          = " + ns2s((System.nanoTime() - start_ns)));

        start_ns = System.nanoTime();
        // With the following sort, the loop in sum runs faster (3x)
        Arrays.sort(data);
        long sumSorted = sum(data);
        System.out.println("sumSorted   = " + sumSorted);
        System.out.println("dt          = " + ns2s((System.nanoTime() - start_ns)));
    }
    
    public static double ns2s(long ns) {
        return ns*1e-9;
    }

    public static long sum(int[] data) {
        long sum = 0;
        for (int i = 0; i < MAX_INDEX; ++i) {
            for (int c = 0; c < data.length; ++c) {
                if (data[c] >= SUM_TRESHOLD) {
                    sum += data[c];
                }
            }
        }
        return sum;
    }

    public static long sumUsingTernary(int[] data) {
        long sum = 0;
        for (int i = 0; i < MAX_INDEX; ++i) {
            for (int c = 0; c < data.length; ++c) {
                sum += data[c] >= SUM_TRESHOLD ? data[c] : 0;
            }
        }
        return sum;
    }

    public static long sumBitShift(int[] data) {
        long sum = 0;
        for (int i = 0; i < MAX_INDEX; ++i) {
            for (int c = 0; c < data.length; ++c) {
                //Note that the following hack is not strictly equivalent to the original if-statement. But in this case, it's valid for all the input values of data[] (-255..255)
                int t = (data[c] - SUM_TRESHOLD) >> 31; //It will be 0 for values >= 128 and -1 for < 128.
                sum += ~t & data[c]; //Values less than 128 will be multiplied by 0. ~0 = -1 = 1111...1111 (all 64 bits). ~(-1) = 0.
            }
        }
        return sum;
    }

}
