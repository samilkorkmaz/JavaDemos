package collectionsframework;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Reference: Java tutorial, \collections\interfaces\collection.html
 *
 * @author skorkmaz
 */
public class CollectionsFramework {

    public static class MyShape {

        private final Color color;
        private final String name;

        public Color getColor() {
            return color;
        }

        public String getName() {
            return name;
        }

        public MyShape(Color color, String name) {
            this.color = color;
            this.name = name;
        }
    }

    public static class Employee {

        private final double salary;

        public double getSalary() {
            return salary;
        }

        public Employee(double salary) {
            this.salary = salary;
        }

    }
    
    private static final Collection<MyShape> myShapesCollection = new ArrayList<>();
    private static final Collection<Employee> employeeCollection = new ArrayList<>();

    public static void main(String[] args) {
        myShapesCollection.add(new MyShape(Color.BLACK, "Black"));
        myShapesCollection.add(new MyShape(Color.RED, "Red"));
        myShapesCollection.add(new MyShape(Color.PINK, "Pink"));

        myShapesCollection.stream()
                .filter(e -> e.getColor() == Color.RED)
                .forEach(e -> System.out.println(e.getName()));

        employeeCollection.add(new Employee(1.5));
        employeeCollection.add(new Employee(2));
        double total = employeeCollection.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println("total = " + total);
    }
}
