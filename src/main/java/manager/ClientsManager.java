package manager;

import java.util.List;
import model.user.Client;
import repository.ClientRepository;

public class ClientsManager {

    ClientRepository clientRepository = new ClientRepository();

    public void registerClient(Client client) {
        clientRepository.addClient(client);
    }

    public void unregisterClient(Client client) {
        clientRepository.removeClient(client);
    }

    public void findByID(Long id) {
        clientRepository.findByID(id);
    }

    public String getReport() {
        return clientRepository.getReport();
    }

    public List<Client> findAll() {
        return clientRepository.getClients();
    }

}
