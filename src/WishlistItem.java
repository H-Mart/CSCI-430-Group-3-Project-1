public final class WishlistItem {
    private final Product product;
    private final int quantity;

    /**
     * @param product  The product to be added to the wishlist
     * @param quantity The quantity of the product to be added to the wishlist
     */
    public WishlistItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public String getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product: \n\t" + product + "\n\tWishlist Quantity: " + quantity;
    }
}
