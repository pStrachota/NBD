package repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.user.Client;

public class ClientRepository extends Repository<Client> implements AutoCloseable {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public ClientRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Client add(Client client) {

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
            return client;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Client> getItems() {
        return entityManager.createQuery("from Client", Client.class).getResultList();
    }

    public boolean remove(Client client) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Client> findByID(Long id) {
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, id);
        entityManager.getTransaction().commit();
        return Optional.ofNullable(client);

    }

    @Override
    public Client update(Client client) {

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(client);
            entityManager.getTransaction().commit();
            return client;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() {
        entityManagerFactory.close();
        entityManager.close();
    }
}
