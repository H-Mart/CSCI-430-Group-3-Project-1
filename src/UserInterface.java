import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public final class UserInterface {
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

            System.out.println("    1. Add client");
            System.out.println("    2. Add products");
            System.out.println("    3. Add products to Client wishlist");
            System.out.println("    4. Print clients");
            System.out.println("    5. Print products");
            System.out.println("    6. Print Client wishlist");
            System.out.println("    0. Exit");
            System.out.print("> ");

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

        String addedId = Warehouse.instance().addClient(name, address);

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

        String addedId = Warehouse.instance().addProduct(name, price, quantity);

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

    /**
     * Allow the user to add products to a client's wishlist
     *
     * @precondition none
     * @postcondition if the client and product(s) exist,
     * the product(s) is/are added to the client's wishlist as a WishlistItem
     */
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

    /*
     * Prints all clients in the ordering system in a nicely organized table
     */
    public static void printClients() {
        var idHeader = "ID";
        var nameHeader = "Name";
        var addressHeader = "Address";

        var maxIdLength = idHeader.length();
        var maxNameLength = nameHeader.length();
        var maxAddressLength = addressHeader.length();

        var clientIterator = Warehouse.instance().getClientIterator();
        while (clientIterator.hasNext()) {
            var client = clientIterator.next();
            if (client.getId().length() > maxIdLength) {
                maxIdLength = client.getId().length();
            }
            if (client.getName().length() > maxNameLength) {
                maxNameLength = client.getName().length();
            }
            if (client.getAddress().length() > maxAddressLength) {
                maxAddressLength = client.getAddress().length();
            }
        }

        // default padding for each column, to be added to the left and right side of the headers
        var idPadding = 3;
        var namePadding = 3;
        var addressPadding = 3;

        // adjust column width to fit the longest string in the column plus the padding
        var idColWidth = idPadding * 2 + maxIdLength;
        var nameColWidth = namePadding * 2 + maxNameLength;
        var addressColWidth = addressPadding * 2 + maxAddressLength;

        // using the whole column width, calculate the amount of padding needed for each header
        var idHeaderPadding = (idColWidth - idHeader.length()) / 2;
        var nameHeaderPadding = (nameColWidth - nameHeader.length()) / 2;
        var addressHeaderPadding = (addressColWidth - addressHeader.length()) / 2;

        // recalculate the column width to account for the header length and padding
        idColWidth = idHeaderPadding * 2 + idHeader.length();
        nameColWidth = nameHeaderPadding * 2 + nameHeader.length();
        addressColWidth = addressHeaderPadding * 2 + addressHeader.length();

        String horizontalLine = "-".repeat(idColWidth + nameColWidth + addressColWidth + 4);
        System.out.println(horizontalLine);

// @formatter:off
        System.out.printf("|%" + idHeaderPadding       + "s" + "%s"  + "%-" +  idHeaderPadding      + "s" +
                          "|%" + nameHeaderPadding     + "s" + "%s"  + "%-" +  nameHeaderPadding    + "s" +
                          "|%" + addressHeaderPadding  + "s" + "%s"  + "%-" +  addressHeaderPadding + "s" + "|\n",
                " ", idHeader, " ",
                " ", nameHeader, " ",
                " ", addressHeader, " ");
// @formatter:on

        System.out.println(horizontalLine);

        clientIterator = Warehouse.instance().getClientIterator();
        while (clientIterator.hasNext()) {
            var client = clientIterator.next();
            // subtract 1 from the column width to account for a space between the column and the border
            System.out.printf("|%" + (idColWidth - 1) + "s | %-" + (nameColWidth - 1) + "s| %-" + (addressColWidth - 1) + "s|\n",
                    client.getId(), client.getName(), client.getAddress());
        }
        System.out.println(horizontalLine);
    }

    /*
     * Prints all products in the ordering system in a nicely organized table
     */
    public static void printProducts() {
        var idHeader = "ID";
        var nameHeader = "Name";
        var priceHeader = "Price";
        var quantityHeader = "Quantity";

        var maxIdLength = idHeader.length();
        var maxNameLength = nameHeader.length();
        var maxPriceLength = priceHeader.length();
        var maxQuantityLength = quantityHeader.length();

        var productIterator = Warehouse.instance().getProductIterator();
        while (productIterator.hasNext()) {
            var product = productIterator.next();
            if (product.getId().length() > maxIdLength) {
                maxIdLength = product.getId().length();
            }
            if (product.getName().length() > maxNameLength) {
                maxNameLength = product.getName().length();
            }
            if (Double.toString(product.getPrice()).length() > maxPriceLength) {
                maxPriceLength = Double.toString(product.getPrice()).length();
            }
            if (Integer.toString(product.getQuantity()).length() > maxQuantityLength) {
                maxQuantityLength = Integer.toString(product.getQuantity()).length();
            }
        }

        // default padding for each column, to be added to the left and right side of the headers
        var idPadding = 3;
        var namePadding = 3;
        var pricePadding = 3;
        var quantityPadding = 3;

        // adjust column width to fit the longest string in the column plus the padding
        var idColWidth = idPadding * 2 + maxIdLength;
        var nameColWidth = namePadding * 2 + maxNameLength;
        var priceColWidth = pricePadding * 2 + maxPriceLength;
        var quantityColWidth = quantityPadding * 2 + maxQuantityLength;

        // using the whole column width, calculate the amount of padding needed for each header
        var idHeaderPadding = (idColWidth - idHeader.length()) / 2;
        var nameHeaderPadding = (nameColWidth - nameHeader.length()) / 2;
        var priceHeaderPadding = (priceColWidth - priceHeader.length()) / 2;
        var quantityHeaderPadding = (quantityColWidth - quantityHeader.length()) / 2;

        // recalculate the column width to account for the header length and padding
        idColWidth = idHeaderPadding * 2 + idHeader.length();
        nameColWidth = nameHeaderPadding * 2 + nameHeader.length();
        priceColWidth = priceHeaderPadding * 2 + priceHeader.length();
        quantityColWidth = quantityHeaderPadding * 2 + quantityHeader.length();

        String horizontalLine = "-".repeat(idColWidth + nameColWidth + priceColWidth + quantityColWidth + 5);
        System.out.println(horizontalLine);

// @formatter:off
        System.out.printf("|%" + idHeaderPadding       + "s" + "%s"  + "%-" + idHeaderPadding       + "s" +
                          "|%" + nameHeaderPadding     + "s" + "%s"  + "%-" + nameHeaderPadding     + "s" +
                          "|%" + priceHeaderPadding    + "s" + "%s"  + "%-" + priceHeaderPadding    + "s" +
                          "|%" + quantityHeaderPadding + "s" + "%s"  + "%-" + quantityHeaderPadding + "s" + "|\n",
                " ", idHeader, " ",
                " ", nameHeader, " ",
                " ", priceHeader, " ",
                " ", quantityHeader, " ");
// @formatter:on

        System.out.println(horizontalLine);

        productIterator = Warehouse.instance().getProductIterator();
        while (productIterator.hasNext()) {
            var product = productIterator.next();
            System.out.printf("|%" + (idColWidth - 1) + "s | %-" + (nameColWidth - 1) + "s|%" + (priceColWidth - 1) +
                            ".2f |%" + (quantityColWidth - 1) + "s |\n",
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

    /*
     * Prints the client's wishlist including the product id, product name, and wishlist-ed quantity
     */
    private static void printClientWishlist(String clientId) {
        Optional<Client> client = Warehouse.instance().getClientById(clientId);
        if (client.isEmpty()) {
            System.out.println("Client not found");
            return;
        }

        var clientWishlistIterator = client.get().getWishlist().getIterator();

        System.out.println("Current Wishlist: ");

        while (clientWishlistIterator.hasNext()) {
            var wishlistItem = clientWishlistIterator.next();
            Optional<Product> product = Warehouse.instance().getProductById(wishlistItem.getProductId());

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
