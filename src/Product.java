import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private double price;
    private int quantity;
    private final Waitlist waitlist;

    /**
     * @param name     name of product
     * @param price    price of product
     * @param quantity quantity of product
     * @postcondition name, price, and quantity are set, id is set to nextId, nextId is incremented
     */
    public Product(String name, double price, int quantity, IdServer idServer) {
        this.id = Integer.toString(idServer.getNewId());
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.waitlist = new Waitlist();
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

    public boolean addToWaitlist(String clientId, int quantity) {
        return waitlist.addWaitlistItem(new WaitlistItem(clientId, quantity));
    }

    @Override
    public String toString() {
        return "ID: " + id + " Price: " + price + " Quantity: " + quantity + " Name: " + name;
    }
}
