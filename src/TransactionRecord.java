import java.io.Serializable;
import java.util.Date;

public class TransactionRecord implements Serializable {
private static final long serialVersionUID = 1L;
    private final Date date;

    private final String description;

    private final double totalCost;

    public TransactionRecord(String description, double totalCost) {
        this.date = new Date();
        this.description = description;
        this.totalCost = totalCost;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
