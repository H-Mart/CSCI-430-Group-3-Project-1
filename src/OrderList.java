import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Order> orderList;

    public OrderList() {
        orderList = new ArrayList<>();
    }

    public boolean insertOrder(Order order) {
        orderList.add(order);
        return true;
    }

    public Iterator<Order> getOrders() {
        return orderList.iterator();
    }

    public String toString() {
        return orderList.toString();
    }
}
