import java.util.ArrayList;
import java.util.Iterator;

public class Wishlist {
    // container class to hold wishlist items
    private final ArrayList<WishlistItem> wishlist;

    /**
     * @postcondition creates empty wishlist
     */
    public Wishlist() {
        wishlist = new ArrayList<>();
    }

    /**
     * @param wishlistItem wishlist item to add
     * @postcondition adds wishlist item to wishlist
     */
    public void addWishlistItem(WishlistItem wishlistItem) {
        wishlist.add(wishlistItem);
    }

    public Iterator<WishlistItem> getIterator() {
        return wishlist.iterator();
    }

    /**
     * @param wishlistItemProductId product id of wishlist item to remove
     * @postcondition removes wishlist item with matching product id
     */
    public void removeWishlistItem(String wishlistItemProductId) {
        wishlist.removeIf(item -> item.getProductId().equals(wishlistItemProductId));
    }
}
