package manager;

import exception.ItemNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import model.user.Client;
import repository.ClientRepository;

@AllArgsConstructor
public class ClientManager {

    ClientRepository clientRepository;

    public void registerClient(Client client) {
        clientRepository.add(client);
    }

    public void unregisterClient(Client client) {
        clientRepository.remove(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.update(client);
    }

    public Client findClientByID(Long id) {
        return clientRepository.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Client not found"));
    }
    public List<Client> findAll() {
        return clientRepository.getItems();
    }
}
