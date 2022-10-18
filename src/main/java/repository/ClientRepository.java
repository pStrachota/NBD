package repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Client;

public class ClientRepository implements AutoCloseable {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public ClientRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addClient(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    public List<Client> getClients() {
        List<Client> clients =
                entityManager.createQuery("from Client", Client.class).getResultList();
        return clients;
    }

    public void removeClient(Client client) {
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }

    public Client findByID(Long id) {
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, id);
        entityManager.getTransaction().commit();
        return client;

    }

    public String getReport() {
        entityManager.getTransaction().begin();
        List<Client> clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        entityManager.getTransaction().commit();
        StringBuilder stringBuilder = new StringBuilder();
        for (Client client : clients) {
            stringBuilder.append(client.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        System.out.println("Closing tango");
        entityManagerFactory.close();
        entityManager.close();
    }
}
