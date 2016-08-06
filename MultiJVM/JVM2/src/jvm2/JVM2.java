package jvm2;

/**
 *
 * @author skorkmaz
 */
public class JVM2 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("This is jvm2:");
        System.out.println("bitSize : " + System.getProperty("sun.arch.data.model"));
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Java home: " + System.getProperty("java.home"));
        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i + " / 4");
            Thread.sleep(1000);
        }
        throw new IllegalArgumentException("jvm2 has thrown exception.");
    }
    
}
