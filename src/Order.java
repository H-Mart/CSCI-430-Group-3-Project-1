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

    public void addToOrder(Product product, int orderQuantity) {
        if (orderQuantity <= product.getQuantity()) {
            invoice.insertInvoiceItem(new InvoiceItem(product.getId(), orderQuantity, product.getPrice()));
            product.setQuantity(product.getQuantity() - orderQuantity);
        } else {
            System.out.println("Order quantity exceeds product quantity.");
            System.out.println("Adding " + (orderQuantity - product.getQuantity()) + " to product waitlist.");
            invoice.insertInvoiceItem(new InvoiceItem(product.getId(), product.getQuantity(), product.getPrice()));
            product.addToWaitlist(clientId, orderQuantity - product.getQuantity());
            product.setQuantity(0);
        }
    }

    public void completeOrder(Client client) {
        double totalCost = 0;
        var invoiceItemIterator = invoice.getInvoiceItemIterator();
        while (invoiceItemIterator.hasNext()) {
            var invoiceItem = invoiceItemIterator.next();
            totalCost += invoiceItem.getTotalPrice();
        }
        this.transactionRecord = new TransactionRecord("Order for " + clientId, totalCost);
        this.orderComplete = true;
        client.subtractFromBalance(totalCost);
        client.addToOrderList(this);
    }
}
