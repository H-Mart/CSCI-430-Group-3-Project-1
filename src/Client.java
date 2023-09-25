import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String address;

    private final Wishlist wishlist;

    /**
     * @param name    the name of the client
     * @param address the address of the client
     * @precondition name and address are not null, idServer is a valid IdServer instance
     * @postcondition name and address are set, id is set to nextId, nextId is incremented, wishlist is initialized
     */
    public Client(String name, String address, IdServer idServer) {
        this.id = Integer.toString(idServer.getNewId());
        this.name = name;
        this.address = address;
        this.wishlist = new Wishlist();
    }

    /**
     * Method for adding a WishlistItem to the wishlist from a Product
     *
     * @param product  the product to get the id from to add to the wishlist
     * @param quantity the quantity of the product to add
     * @precondition product is not null, quantity is greater than 0
     * @postcondition the product and quantity are added to the wishlist as a WishlistItem
     */
    public void addToWishlist(Product product, int quantity) {
        addToWishlist(product.getId(), quantity);
    }

    /**
     * Method for adding a WishlistItem to the wishlist from a Product id
     *
     * @param productId the id of the product to add to the wishlist
     * @param quantity  the quantity of the product to add
     * @precondition productId is not null, quantity is greater than 0
     * @postcondition the product and quantity are added to the wishlist as a WishlistItem
     */
    public void addToWishlist(String productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        WishlistItem wishlistItem = new WishlistItem(productId, quantity);
        wishlist.addWishlistItem(wishlistItem);
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "id: " + id + " Name: " + name + " Address: " + address;
    }
}
