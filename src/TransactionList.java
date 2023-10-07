import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionList implements Serializable {
    private List<TransactionRecord> transactionList;

    public TransactionList() {
        transactionList = new ArrayList<>();
    }

    public boolean insertTransaction(TransactionRecord transaction) {
        return transactionList.add(transaction);
    }

    public Iterator<TransactionRecord> getTransactionList() {
        return transactionList.iterator();
    }
}
