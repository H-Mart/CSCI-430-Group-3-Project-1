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
            System.out.println("    1. Login as client");
            System.out.println("    2. Login as manager");
            System.out.println("    3. Add client");
            System.out.println("    4. Add product");
            System.out.println("    5. Print clients");
            System.out.println("    6. Print products");
            System.out.println("    0. Exit");
            System.out.print("> ");


            // get user input
            String input = UserInterface.getUserInput();
            switch (input) {
                case "1":
                    UserInterface.asClient();
                    break;
                case "2":
                    UserInterface.asManager();
                    break;
                case "3":
                    addClient();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    printClients();
                    break;
                case "6":
                    printProducts();
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

    private static void asClient() {
        System.out.println("Please enter your client id: ");
        String clientId = UserInterface.getUserInput();
        if (OrderingSystem.instance().getClientById(clientId).isEmpty()) {
            System.out.println("Client not found");
            return;
        }
        // client menu
        System.out.println("Logged in as client " + clientId);
        System.out.println("Please select an option:");
        while (true) {
            System.out.println("    1. Add product to wishlist");
            System.out.println("    2. Remove product from wishlist");
            System.out.println("    3. View wishlist");
            System.out.println("    0. Logout");
            System.out.print("> ");

            String input = UserInterface.getUserInput();
            switch (input) {
                case "1":
                    UserInterface.addProductToWishlist(clientId);
                    break;
                case "2":
                    UserInterface.removeProductFromWishlist(clientId);
                    break;
                case "3":
                    UserInterface.printWishlist(clientId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void asManager() {
        // manager menu
        System.out.println("Logged in as manager");
        System.out.println("Please select an option:");
        while (true) {
            System.out.println("    1. Add product");
            System.out.println("    2. Remove product");
            System.out.println("    3. View products");
            System.out.println("    0. Logout");
            System.out.print("> ");

            String input = UserInterface.getUserInput();
            switch (input) {
                case "1":
                    UserInterface.addProduct();
                    break;
                case "2":
                    UserInterface.removeProduct();
                    break;
                case "3":
                    UserInterface.printProducts();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    // asks user to add a client
    // then passes the information to the ordering system

    /**
     * @precondition none
     * @postcondition a new client is added to the ordering system and printed if successful
     */
    public static void addClient() {
        System.out.print("Enter client name: ");
        String name = UserInterface.getUserInput();

        System.out.print("\nEnter client address: ");
        String address = UserInterface.getUserInput();

        var addedId = OrderingSystem.instance().addClient(name, address);

        if (OrderingSystem.instance().getClientById(addedId).isPresent()) {
            System.out.println("\nClient added - " + OrderingSystem.instance().getClientById(addedId).get());
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

        var addedId = OrderingSystem.instance().addProduct(name, price, quantity);

        if (OrderingSystem.instance().getProductById(addedId).isPresent()) {
            System.out.println("\nProduct added - " + OrderingSystem.instance().getProductById(addedId).get());
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
     * @precondition none
     * @postcondition the product is removed from the ordering system
     */
//    public static void removeProduct() {
//        System.out.print("Enter product id: ");
//        String productId = UserInterface.getUserInput();
//
//        OrderingSystem.instance().removeProduct(productId);
//    }
    public static void printClients() {
        var clientIterator = OrderingSystem.instance().getClientIterator();
        while (clientIterator.hasNext()) {
            System.out.println(clientIterator.next());
        }
    }

    public static void printProducts() {
        var productIterator = OrderingSystem.instance().getProductIterator();
        while (productIterator.hasNext()) {
            System.out.println(productIterator.next());
        }

    }

    /**
     * @param clientId the id of the client to add the product to
     * @precondition clientId is not null
     * @postcondition the product is added to the client's wishlist
     */
    public static void addProductToWishlist(String clientId) {
        System.out.print("Enter product id: ");
        String productId = UserInterface.getUserInput();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(UserInterface.getUserInput());

        OrderingSystem.instance().addProductToWishlist(productId, clientId, quantity);
    }

    /**
     * @param clientId the id of the client to remove the product from
     * @precondition clientId is not null
     * @postcondition the product is removed from the client's wishlist
     */
    public static void removeProductFromWishlist(String clientId) {
        System.out.print("Enter product id: ");
        String productId = UserInterface.getUserInput();

        OrderingSystem.instance().removeProductFromWishlist(productId, clientId);
    }

    /**
     * @param clientId the id of the client to print the wishlist of
     * @precondition clientId is not null
     * @postcondition the client's wishlist is printed
     */
    public static void printWishlist(String clientId) {
        OrderingSystem.instance().printWishlist(clientId);
    }

    // prints a message saying the option is not implemented, for use in stubs
    public static void optionNotImplemented() {
        System.out.println("Option not implemented");
    }
}
