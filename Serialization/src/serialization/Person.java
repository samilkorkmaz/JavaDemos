package serialization;

import java.io.Serializable;

/**
 *
 * @author skorkmaz
 */
public class Person implements Serializable{
    
    private final String name;
    private final int age;
    
    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "name: " + name + ", age: " + age;
    }
    
}
