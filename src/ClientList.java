import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class ClientList implements Serializable {
    public static final long serialVersionUID = 1L;

    // singleton class for storing clients
    private final ArrayList<Client> clientArrayList;

    public ClientList() {
        clientArrayList = new ArrayList<>();
    }

    /**
     * @param clientId the id of the client
     * @return an optional containing the client if it exists otherwise an empty optional
     * @precondition clientId is not null
     * @postcondition if the client exists, an optional containing the client is returned otherwise an empty optional is returned
     */
    public Optional<Client> getClientById(String clientId) {
        for (Client client : clientArrayList) {
            if (client.getId().equals(clientId)) {
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

    public Iterator<Client> getIterator() {
        return clientArrayList.iterator();
    }

    /**
     * @param client the client to add
     * @precondition client is not null
     * @postcondition the client is added to the client list
     */
    public boolean insertClient(Client client) {
        clientArrayList.add(client);
        return true;
    }

    /**
     * checks if a client with the given id exists in the client list
     * @param clientId the id of the client to remove
     * @precondition clientId is not null
     * @return true if the client exists, false otherwise
     */
    public boolean clientIdInList(String clientId) {
        for (Client client : clientArrayList) {
            if (client.getId().equals(clientId)) {
                return true;
            }
        }
        return false;
    }
}
