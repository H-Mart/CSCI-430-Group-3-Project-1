import java.io.Serializable;

public final class WishlistItem implements Serializable {
    public static final long serialVersionUID = 1L;

    private final String productId;
    private int quantity;

    /**
     * @param productId The id of the product to be added to the wishlist
     * @param quantity  The quantity of the product to be added to the wishlist
     */
    public WishlistItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product: \n\t" + productId + "\n\tWishlist Quantity: " + quantity;
    }
}
