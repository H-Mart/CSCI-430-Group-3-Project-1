import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class ProductList {
    // singleton class for storing products
    private final ArrayList<Product> productArrayList;

    /**
     * @precondition none
     * @postcondition a new ProductList is created and stored in productArrayList
     */
    public ProductList() {
        productArrayList = new ArrayList<>();
    }

    /**
     * @param productId the id of the product
     * @return an optional containing the product if it exists otherwise an empty optional
     * @precondition productId is not null
     * @postcondition if the product exists, an optional containing the product is returned otherwise an empty optional is returned
     */
    public Optional<Product> getProductById(String productId) {
        for (Product product : productArrayList) {
            if (product.getId().equals(productId)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public Iterator<Product> getIterator() {
        return productArrayList.iterator();
    }

    /**
     * @param product the product to add
     * @precondition product is not null
     * @postcondition the product is added to the product list
     */
    public void addProduct(Product product) {
        productArrayList.add(product);
    }

    /**
     * @param productId the id of the product to remove
     * @precondition productId is not null
     * @postcondition the product is removed from the product list
     */
    public void removeProduct(String productId) {
        productArrayList.removeIf(product -> product.getId().equals(productId));
    }
}
