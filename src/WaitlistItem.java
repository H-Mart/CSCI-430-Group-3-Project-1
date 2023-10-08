import java.io.Serializable;
import java.util.Date;

public class WaitlistItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String clientId;
    private final int quantity;
    private final Date date;

    public WaitlistItem(String clientId, int quantity) {
        this.clientId = clientId;
        this.quantity = quantity;
        this.date = new Date();
    }

    public String getClientId() {
        return clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        return "clientId: " + clientId + ", quantity: " + quantity;
    }
}
