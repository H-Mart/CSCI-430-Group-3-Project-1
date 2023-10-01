import java.io.Serializable;
import java.util.ArrayList;

public class Waitlist implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<WaitlistItem> waitlist;

    public Waitlist() {
        waitlist = new ArrayList<>();
    }

    public boolean addWaitlistItem(WaitlistItem item) {
        return waitlist.add(item);
    }


}
