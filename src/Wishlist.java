import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Wishlist implements Serializable {
    public static final long serialVersionUID = 1L;

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

    public boolean productInWishlist(String productId) {
        for (WishlistItem wishlistItem : wishlist) {
            if (wishlistItem.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public WishlistItem getWishlistItem(String productId) {
        for (WishlistItem wishlistItem : wishlist) {
            if (wishlistItem.getProductId().equals(productId)) {
                return wishlistItem;
            }
        }
        return null;
    }
}
