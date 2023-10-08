import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Waitlist implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<WaitlistItem> waitlist;

    public Waitlist() {
        waitlist = new ArrayList<>();
    }

    public void addWaitlistItem(WaitlistItem item) {
        waitlist.add(item);
    }

    public Iterator<WaitlistItem> getIterator() {
        return waitlist.iterator();
    }


}
