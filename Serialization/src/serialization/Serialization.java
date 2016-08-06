package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Save serializable object data to a file and retrieve object from file.
 *
 * @author skorkmaz, 2014
 */
public class Serialization {

    public static void main(String[] args) {
        try {
            String filename = "person.ser";
            FileOutputStream fos = new FileOutputStream(filename);
            Person p = new Person("Şamil Korkmaz", 42);
            try (ObjectOutputStream out = new ObjectOutputStream(fos)) {
                // save the object to file
                out.writeObject(p);
            }
            FileInputStream fis = new FileInputStream(filename);
            try (ObjectInputStream in = new ObjectInputStream(fis)) {
                // read the object from file
                p = (Person) in.readObject();
            }
            System.out.println(p);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
