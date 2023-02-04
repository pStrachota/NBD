package manager;

import exception.ItemNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.user.Address;
import model.user.Client;
import model.user.ClientType;
import repository.ClientRepository;

@AllArgsConstructor
public class ClientManager {

    ClientRepository clientRepository;

    public Client registerClient(String name, String surname, Address address, ClientType clientType) {
        Client client = new Client(name, surname, address, clientType);
        return clientRepository.add(client);
    }

    public void unregisterClient(Client client) {
        clientRepository.remove(client);
    }

    public boolean updateClient(Client client) {
        return clientRepository.update(client);
    }

    public Client findClientByID(UUID id) {
        return clientRepository.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Client not found"));
    }
    public List<Client> findAll() {
        return clientRepository.getItems();
    }
}
