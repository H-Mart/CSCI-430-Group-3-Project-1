import java.io.Serializable;

public class Product implements Serializable {
    public static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private double price;
    private int quantity;

    private static int nextId = 1;

    /**
     * @param name     name of product
     * @param price    price of product
     * @param quantity quantity of product
     * @postcondition id is set to nextId, nextId is incremented
     */
    public Product(String name, double price, int quantity) {
        // cast nextId to string for id and increment nextId
        this.id = Integer.toString(nextId++);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // private attribute getters

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    // private attribute setters

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Price: " + price + " Quantity: " + quantity + " Name: " + name;
    }
}
