import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class UserInterface {
    // class for user interaction with the system
    // all methods are static and constructor is private
    // thus no state is stored in this class

    private static final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    private UserInterface() {
    }

    /**
     * Gets user input from the console
     *
     * @return user input as a string
     */
    public static String getUserInput() {
        String inputLine = "";
        try {
            inputLine = UserInterface.inputReader.readLine().trim();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }

    public static void main(String[] args) {
        // print welcome message
        System.out.println("Welcome to the ordering system!");
        System.out.println("Please select an option:");
        while (true) {

            // print menu
            System.out.println("    1. Add client");
            System.out.println("    2. Add products");
            System.out.println("    3. Add products to Client wishlist");
            System.out.println("    4. Print clients");
            System.out.println("    5. Print products");
            System.out.println("    6. Print Client wishlist");
            System.out.println("    0. Exit");
            System.out.print("> ");


            // get user input
            String input = UserInterface.getUserInput();
            switch (input) {
                case "1":
                    addClient();
                    break;
                case "2":
                    addProducts();
                    break;
                case "3":
                    addProductsToClientWishlist();
                    break;
                case "4":
                    printClients();
                    break;
                case "5":
                    printProducts();
                    break;
                case "6":
                    printClientWishlist();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    /**
     * @precondition none
     * @postcondition a new client is added to the ordering system and printed if successful
     */
    public static void addClient() {
        System.out.print("Enter client name: ");
        String name = UserInterface.getUserInput();

        System.out.print("\nEnter client address: ");
        String address = UserInterface.getUserInput();

        var addedId = Warehouse.instance().addClient(name, address);

        if (Warehouse.instance().getClientById(addedId).isPresent()) {
            System.out.println("\nClient added - " + Warehouse.instance().getClientById(addedId).get());
        } else {
            System.out.println("\nClient not added");
        }
    }

    // asks user to add a product
    // then passes the information to the ordering system

    /**
     * @precondition none
     * @postcondition a new product is added to the ordering system and printed if successful
     */
    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = UserInterface.getUserInput();

        System.out.print("\nEnter product price: ");
        double price = Double.parseDouble(UserInterface.getUserInput());

        System.out.println("\nEnter product quantity: ");
        int quantity = Integer.parseInt(UserInterface.getUserInput());

        var addedId = Warehouse.instance().addProduct(name, price, quantity);

        if (Warehouse.instance().getProductById(addedId).isPresent()) {
            System.out.println("\nProduct added - " + Warehouse.instance().getProductById(addedId).get());
        } else {
            System.out.println("\nProduct not added");
        }
    }

    /**
     * @precondition none
     * @postcondition multiple products are added to the ordering system
     */
    public static void addProducts() {
        while (true) {
            addProduct();
            System.out.println("Add another product? (y/n)");
            String input = UserInterface.getUserInput();
            if (input.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    public static void addProductsToClientWishlist() {
        System.out.println("Please enter your client id: ");
        String clientId = UserInterface.getUserInput();
        if (Warehouse.instance().getClientById(clientId).isEmpty()) {
            System.out.println("Client not found");
            return;
        }

        while (true) {
            System.out.println("Please enter the product id: ");
            String productId = UserInterface.getUserInput();
            if (Warehouse.instance().getProductById(productId).isEmpty()) {
                System.out.println("Product not found");
                return;
            }

            System.out.println("Please enter the quantity: ");
            int quantity = Integer.parseInt(UserInterface.getUserInput());

            Warehouse.instance().addProductToClientWishlist(clientId, productId, quantity);

            printClientWishlist(clientId);

            System.out.println("\nAdd another product? (y/n)");
            String input = UserInterface.getUserInput();
            if (input.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    public static void printClients() {
        var idPadding = 4;
        var namePadding = 12;
        var addressPadding = 15;
        var idHeader = "ID";
        var nameHeader = "Name";
        var addressHeader = "Address";

        var idColWidth = idPadding * 2 + idHeader.length();
        var nameColWidth = namePadding * 2 + nameHeader.length();
        var addressColWidth = addressPadding * 2 + addressHeader.length();

        String horizontalLine = "-".repeat(idColWidth + nameColWidth + addressColWidth + 4);
        System.out.println(horizontalLine);

// @formatter:off
        System.out.printf("|%" + idPadding       + "s" + "%s"  + "%-" + idPadding       + "s" +
                          "|%" + namePadding     + "s" + "%s"  + "%-" + namePadding     + "s" +
                          "|%" + addressPadding  + "s" + "%s"  + "%-" + addressPadding  + "s" + "|\n",
                " ", idHeader, " ",
                " ", nameHeader, " ",
                " ", addressHeader, " ");
// @formatter:on

        System.out.println(horizontalLine);

        var clientIterator = Warehouse.instance().getClientIterator();
        while (clientIterator.hasNext()) {
            var client = clientIterator.next();
            System.out.printf("|%" + (idColWidth - 1) + "s | %-" + (nameColWidth - 1) + "s| %-" + (addressColWidth - 1) + "s|\n",
                    client.getId(), client.getName(), client.getAddress());
        }
        System.out.println(horizontalLine);
    }

    public static void printProducts() {

        // amount of padding for each column, on the left and right side of the text
        var idPadding = 4;
        var namePadding = 8;
        var pricePadding = 8;
        var quantityPadding = 8;
        var idHeader = "ID";
        var nameHeader = "Name";
        var priceHeader = "Price";
        var quantityHeader = "Quantity";

        var idColWidth = idPadding * 2 + idHeader.length();
        var nameColWidth = namePadding * 2 + nameHeader.length();
        var priceColWidth = pricePadding * 2 + priceHeader.length();
        var quantityColWidth = quantityPadding * 2 + quantityHeader.length();

        String horizontalLine = "-".repeat(idColWidth + nameColWidth + priceColWidth + quantityColWidth + 5);
        System.out.println(horizontalLine);

// @formatter:off
        System.out.printf("|%" + idPadding       + "s" + "%s"  + "%-" + idPadding       + "s" +
                          "|%" + namePadding     + "s" + "%s"  + "%-" + namePadding     + "s" +
                          "|%" + pricePadding    + "s" + "%s"  + "%-" + pricePadding    + "s" +
                          "|%" + quantityPadding + "s" + "%s"  + "%-" + quantityPadding + "s" + "|\n",
                " ", idHeader, " ",
                " ", nameHeader, " ",
                " ", priceHeader, " ",
                " ", quantityHeader, " ");
// @formatter:on

        System.out.println(horizontalLine);

        var productIterator = Warehouse.instance().getProductIterator();
        while (productIterator.hasNext()) {
            var product = productIterator.next();
            System.out.printf("|%" + (idColWidth - 1) + "s | %-" + (nameColWidth - 1) + "s|%" + (priceColWidth - 1) +
                            "s |%" + (quantityColWidth - 1) + "s |\n",
                    product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        }
        System.out.println(horizontalLine);
    }

    /**
     * @postcondition the client's wishlist is printed
     */
    public static void printClientWishlist() {
        System.out.print("Enter client id: ");
        String clientId = UserInterface.getUserInput();

        printClientWishlist(clientId);
    }

    private static void printClientWishlist(String clientId) {
        var client = Warehouse.instance().getClientById(clientId);
        if (client.isEmpty()) {
            System.out.println("Client not found");
            return;
        }

        var clientWishlistIterator = client.get().getWishlist().getIterator();

        System.out.println("Current Wishlist: ");

        while (clientWishlistIterator.hasNext()) {
            var wishlistItem = clientWishlistIterator.next();
            var product = Warehouse.instance().getProductById(wishlistItem.getProductId());

            if (product.isEmpty()) {
                System.err.println("The product with id " + wishlistItem.getProductId() + " was not found");
                return;
            }

            System.out.println("\tProduct ID: " + wishlistItem.getProductId() + "\n\tProduct Name: "
                    + product.get().getName() + "\n\tWishlist Quantity: " + wishlistItem.getQuantity());
            System.out.println();
        }
    }

    // prints a message saying the option is not implemented, for use in stubs
    public static void optionNotImplemented() {
        System.out.println("Option not implemented");
    }
}
