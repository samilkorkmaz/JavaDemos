package raceconditiondemo;

import java.awt.Point;

/**
 * Race condition demonstration. Reference: http://stackoverflow.com/a/36231109. <br>
 * If you run this demo multiple times, you will see that about 1/3rd of the time, the return of getPoint() won't be equal the object it used in p==null check.<br>
 * Note that using synchronized block in setP does not help because synchronized only ensures single thread access which is not what the problem is.
 *
 * @author skorkmaz, July 2016
 */
public class RaceConditionDemo {

    private Point p;

    public void setP(Point p) {
        this.p = p;
    }

    public static void main(String[] args) {
        RaceConditionDemo r = new RaceConditionDemo();
        Thread t1 = new Thread(() -> {
            r.setP(new Point(1, 2));
            System.out.println("Returned p: " + r.getPoint());
            //System.out.println("Returned p: " + r.getPointWithCache()); //correct form
            System.out.println("Current value of p: " + r.p);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                r.setP(null);
                System.out.print(""); //results in small time delay between setP calls to make sure that p is set to null while getPoint is waiting.
            }
        });
        t1.start();
        t2.start();
    }

    public Point getPoint() {
        if (p == null) {
            System.out.println("p during null check was null!");
        } else {
            System.out.println("p during null check was " + p);
        }
        waitALittle();
        return p;
    }

    public Point getPointWithCache() {
        Point localP = p; //caching p via local variable assignment
        if (localP == null) {
            System.out.println("p during null check was null!");
        } else {
            System.out.println("p during null check was " + localP);
        }
        waitALittle();
        return localP;
    }

    public void waitALittle() {
        for (int i = 0; i < 100; i++) { //results in small (~0.05 ms) time delay.
            System.out.print("");
        }
    }

}
