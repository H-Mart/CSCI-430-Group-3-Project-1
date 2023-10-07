import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrelimOrder implements Serializable {
    public static final long serialVersionUID = 1L;

    private interface Action extends Serializable {
        public void execute();
    }

    private class OrderAction implements Serializable, Action {
        private Warehouse warehouse;
        private final String productId;
        private final int quantity;

        public OrderAction(Warehouse warehouse, String productId, int quantity) {
            this.warehouse = warehouse;
            this.productId = productId;
            this.quantity = quantity;
        }

        @Override
        public void execute() {
            InvoiceItem invoiceItem = warehouse.orderItem(PrelimOrder.this.clientId, this.productId, this.quantity);
            PrelimOrder.this.orderInvoice.insertInvoiceItem(invoiceItem);
        }
    }

    private class RemoveWishlistAction implements Serializable, Action {
        private Warehouse warehouse;

        private String productId;

        public RemoveWishlistAction(Warehouse warehouse, String productId) {
            this.warehouse = warehouse;
            this.productId = productId;
        }

        @Override
        public void execute() {
            warehouse.removeFromWishlist(PrelimOrder.this.clientId, this.productId);
        }
    }

    private String clientId;

    private List<Action> actions;

    public Invoice orderInvoice;

    public PrelimOrder(String clientId) {
        this.clientId = clientId;
        this.actions = new ArrayList<>();
        this.orderInvoice = new Invoice();
    }

    public void addOrderAction(String productId, int quantity) {
        actions.add(new OrderAction(Warehouse.instance(), productId, quantity));
    }

    public void addRemoveWishlistAction(String productId) {
        actions.add(new RemoveWishlistAction(Warehouse.instance(), productId));
    }
}
