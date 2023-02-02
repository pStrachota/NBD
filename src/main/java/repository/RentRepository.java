package repository;

import exception.MaxItemLimitExceededException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.user.Client;
import model.Rent;

public class RentRepository implements AutoCloseable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RentRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addArchiveRent(Rent rent) {
        entityManager.getTransaction().begin();
        entityManager.persist(rent);
        entityManager.getTransaction().commit();
    }

    public void addCurrentRent(Rent rent) {

        Client client = rent.getClient();

        //if client has size
        if (client.getClientType().getMaxItems() > client.getRents().size()) {
            entityManager.getTransaction().begin();
            entityManager.persist(rent);
            entityManager.getTransaction().commit();
        } else {
            throw new MaxItemLimitExceededException("Client has reached the maximum number of items");
        }
    }

    public List<Rent> getRents() {
        return entityManager.createQuery("from Rent", Rent.class).getResultList();
    }

    public void removeRent(Rent rent) {

        Client client = rent.getClient();
        rent.setEndTime(LocalDateTime.now());

        if (rent.getEndTime().isAfter(rent.getBeginTime().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEndTime().getDayOfYear() - rent.getBeginTime().plusDays(client.getClientType().getMaxDays()).getDayOfYear();
            rent.setRentCost(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        entityManager.getTransaction().begin();
        entityManager.remove(rent);
        entityManager.getTransaction().commit();
    }

    public Rent findByID(Long id) {
        entityManager.getTransaction().begin();
        Rent rent = entityManager.find(Rent.class, id);
        entityManager.getTransaction().commit();
        return rent;
    }

    public String getReport() {

        entityManager.getTransaction().begin();
        List<Rent> rents = entityManager.createQuery("SELECT r FROM Rent r").getResultList();
        entityManager.getTransaction().commit();

        StringBuilder stringBuilder = new StringBuilder();
        for (Rent rent : rents) {
            stringBuilder.append(rent.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}

