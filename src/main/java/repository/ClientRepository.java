package repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.user.Client;

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
        return entityManager.createQuery("from Client", Client.class).getResultList();
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
        entityManagerFactory.close();
        entityManager.close();
    }
}
