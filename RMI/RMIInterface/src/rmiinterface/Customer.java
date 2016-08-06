package rmiinterface;

import java.io.Serializable;

/**
 * Data object sent from server to client.
 *
 * @author skorkmaz
 */
public class Customer implements Serializable {

    private final String name;
    private final int id;

    public Customer(final String name, final int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
