import java.util.Iterator;
import java.util.Optional;

public class OrderingSystem {
    // singleton class for coupling the user interface to the back end

    private static OrderingSystem orderingSystem;

    private final ProductList productList;

    private final ClientList clientList;

    private OrderingSystem() {
        productList = new ProductList();
        clientList = new ClientList();
    }

    /**
     * @return the instance of the OrderingSystem
     * @precondition none
     * @postcondition if the instance did not exist, it is created and returned otherwise the existing instance is returned
     */
    public static OrderingSystem instance() {
        if (orderingSystem == null) {
            orderingSystem = new OrderingSystem();
            // add test clients and products
            orderingSystem.addClient("Test Client", "Test Address");
            orderingSystem.addProduct("Test Product", 1.99, 10);

            orderingSystem.addClient("Test Client 2", "Test Address 2");
            orderingSystem.addProduct("Test Product 2", 2.99, 20);

            orderingSystem.addClient("Test Client 3", "Test Address 3");
            orderingSystem.addProduct("Test Product 3", 3.99, 30);
        }
        return orderingSystem;
    }

    /**
     * @param name    the name of the client
     * @param address the address of the client
     * @return the id of the client
     * @precondition name and address are not null
     * @postcondition the client is added to the client list
     */
    public String addClient(String name, String address) {
        Client client = new Client(name, address);
        clientList.addClient(client);
        return client.getId();
    }

    public Optional<Client> getClientById(String clientId) {
        return clientList.getClientById(clientId);
    }

    /**
     * @param name     the name of the product
     * @param price    the price of the product
     * @param quantity the quantity of the product
     * @return the id of the product added
     * @precondition name and address are not null
     * @postcondition the product is added to the product list and the id is returned
     */
    public String addProduct(String name, double price, int quantity) {
        Product product = new Product(name, price, quantity);
        productList.addProduct(product);
        return product.getId();
    }

    /**
     * @param productId the id of the product to remove
     * @precondition productId is not null
     * @postcondition the product is removed from the product list
     */
    public void removeProduct(String productId) {
        productList.removeProduct(productId);
    }

    /**
     * @param clientId the id of the client to remove
     * @precondition clientId is not null
     * @postcondition the client is removed from the client list
     */
    public Optional<Product> getProductById(String productId) {
        return productList.getProductById(productId);
    }

    /**
     * @param productId the id of the product to add to the client's wishlist
     * @param clientId  the id of the client to add the product to
     * @param quantity  the quantity of the product to add
     * @return the id of the product added
     * @precondition productId and clientId are not null
     * @postcondition the product is added to the client's wishlist
     */
    public String addProductToWishlist(String productId, String clientId, int quantity) {
        var client = clientList.getClientById(clientId);
        var product = productList.getProductById(productId);

        if (client.isEmpty()) {
            System.out.println("Client not found");
        } else if (product.isEmpty()) {
            System.out.println("Product not found");
        } else {
            client.get().addToWishlist(product.get(), quantity);
            return product.get().getId();
        }
        return "";
    }

    /**
     * @param productId the id of the product to remove from the client's wishlist
     * @param clientId  the id of the client to remove the product from
     * @precondition productId and clientId are not null
     * @postcondition the product is removed from the client's wishlist
     */
    public void removeProductFromWishlist(String productId, String clientId) {
        var client = clientList.getClientById(clientId);
        var product = productList.getProductById(productId);

        if (client.isEmpty()) {
            System.out.println("Client not found");
        } else if (product.isEmpty()) {
            System.out.println("Product not found");
        } else {
            client.get().removeFromWishlist(productId);
        }
    }

    public Iterator<Client> getClientIterator() {
        return clientList.getIterator();
    }

    public Iterator<Product> getProductIterator() {
        return productList.getIterator();
    }

    /**
     * @postcondition the client's wishlist is printed
     */
//    public void printClients() {
//        clientList.printClients();
//    }

    /**
     * @postcondition the product list is printed
     */
//    public void printProducts() {
//        productList.printProducts();
//    }

    /**
     * @param clientId the id of the client to print the wishlist of
     * @precondition clientId is not null
     * @postcondition the client's wishlist is printed
     */
    public void printWishlist(String clientId) {
        var client = clientList.getClientById(clientId);
        if (client.isPresent()) {
            client.get().printWishlist();
        } else {
            System.out.println("Client not found");
        }
    }
}
