import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<InvoiceItem> invoiceItemList;

    public Invoice() {
        invoiceItemList = new ArrayList<>();
    }

    public Iterator<InvoiceItem> getInvoiceItemIterator() {
        return invoiceItemList.iterator();
    }

    public boolean insertInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceItemList.add(invoiceItem);
    }
}

