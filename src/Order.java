import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String clientId;
    private boolean orderComplete;

    private final Invoice invoice;
    private TransactionRecord transactionRecord = null;


    public Order(String customerId) {
        this.clientId = customerId;
        this.invoice = new Invoice();
        this.orderComplete = false;
    }

    public String getClientId() {
        return clientId;
    }

    }
}
