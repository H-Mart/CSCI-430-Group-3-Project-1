import java.io.*;
import java.util.Iterator;
import java.util.Optional;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    // singleton class for coupling the user interface to the back end

    private static Warehouse warehouse;

    private final ProductList productList;

    private final ClientList clientList;

    private final IdServer clientIdServer;
    private final IdServer productIdServer;

    private Warehouse() {
        productList = new ProductList();
        clientList = new ClientList();
        clientIdServer = new IdServer();
        productIdServer = new IdServer();
    }

    public static void serializeWarehouse() {
        try (var fileOut = new FileOutputStream("warehouse.ser");
             var objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(warehouse);
            fileOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializeWarehouse() {
        try (var fileIn = new FileInputStream("warehouse.ser");
             var objectIn = new ObjectInputStream(fileIn)) {
            warehouse = (Warehouse) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the instance of the Warehouse
     * @precondition none
     * @postcondition if the instance did not exist, it is created and returned otherwise the existing instance is returned
     */
    public static Warehouse instance() {
        if (warehouse == null) {
            warehouse = new Warehouse();
            // add test clients and products
//            warehouse.addClient("Test Client", "Test Address");
//            warehouse.addProduct("Test Product", 10.99, 10);
//
//            warehouse.addClient("Test Client 2", "Test Address 2");
//            warehouse.addProduct("Test Product 2", 2.99, 20);
//
//            warehouse.addClient("Test Client 3", "Test Address 3");
//            warehouse.addClient("Test Client 4", "Test Address 3 But it's really long so we can see what happens when it's really long");
//            warehouse.addProduct("Test Product 3", 123.99, 30);
//            warehouse.addProduct("Test Product 3", 123.9, 30);
        }
        return warehouse;
    }

    /**
     * @param name    the name of the client
     * @param address the address of the client
     * @return the id of the client
     * @precondition name and address are not null
     * @postcondition the client is added to the client list
     */
    public String addClient(String name, String address) {
        Client client = new Client(name, address, clientIdServer);
        clientList.insertClient(client);
        return client.getId();
    }

    /**
     * @param clientId the id of the client to fetch
     * @return an optional containing the client if it exists otherwise an empty optional
     * @precondition clientId is not null
     */
    public Optional<Client> getClientById(String clientId) {
        return clientList.getClientById(clientId);
    }

    /**
     * @param productId the id of the product to fetch
     * @return an optional containing the product if it exists otherwise an empty optional
     * @precondition productId is not null
     */
    public Optional<Product> getProductById(String productId) {
        return productList.getProductById(productId);
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
        Product product = new Product(name, price, quantity, productIdServer);
        productList.insertProduct(product);
        return product.getId();
    }

    /**
     * @param productId the id of the product to add to the client's wishlist
     * @param clientId  the id of the client to add the product to
     * @param quantity  the quantity of the product to add
     * @return the id of the product added
     * @precondition productId and clientId are not null
     * @postcondition the product is added to the client's wishlist
     */
    public void addProductToClientWishlist(String clientId, String productId, int quantity) {
        var client = clientList.getClientById(clientId);
        var product = productList.getProductById(productId);

        if (client.isEmpty()) {
            System.out.println("Client not found");
            return;
        } else if (product.isEmpty()) {
            System.out.println("Product not found");
            return;
        }

        var clientWishlist = client.get().getWishlist();
        if (clientWishlist.productInWishlist(productId)) {
            // if product is already in wishlist, update quantity
            clientWishlist.getWishlistItem(productId).setQuantity(quantity);
        } else {
            // otherwise add product to wishlist
            client.get().addToWishlist(productId, quantity);
        }

    }

    public Iterator<Client> getClientIterator() {
        return clientList.getIterator();
    }

    public Iterator<Product> getProductIterator() {
        return productList.getIterator();
    }

    public boolean clientExists(String clientId) {
        return clientList.getClientById(clientId).isPresent();
    }

    public boolean productExists(String productId) {
        return productList.getProductById(productId).isPresent();
    }
}
