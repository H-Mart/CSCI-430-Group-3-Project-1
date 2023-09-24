import java.io.Serializable;

public class IdServer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idCounter = 1;

    public IdServer() {
    }

    public int getNewId() {
        return idCounter++;
    }

    public int getIdCounter() {
        return idCounter;
    }

    /**
     * String form of the collection
     */
    @Override
    public String toString() {
        return ("IdServer" + idCounter);
    }
}
