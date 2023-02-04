package manager;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.user.Client;
import repository.impl.ClientRepository;

@AllArgsConstructor
public class ClientManager {

    ClientRepository clientRepository;

    public void registerClient(Client client) {
        clientRepository.add(client);
    }

    public void unregisterClient(Client client) {
        clientRepository.remove(client);
    }

    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public Optional<Client> findClientByID(UUID id) {
        return clientRepository.findById(id);
    }
}
