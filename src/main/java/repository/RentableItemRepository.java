package repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.RentableItem;

public class RentableItemRepository implements AutoCloseable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RentableItemRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addRentableItem(RentableItem rentableItem) {
        entityManager.getTransaction().begin();
        entityManager.persist(rentableItem);
        entityManager.getTransaction().commit();
    }

    public List<RentableItem> getAllRentableItems() {
        return entityManager.createQuery("from RentableItem", RentableItem.class).getResultList();
    }

    public void removeRentableItem(RentableItem rentableItem) {
        entityManager.getTransaction().begin();
        entityManager.remove(rentableItem);
        entityManager.getTransaction().commit();
    }

    public RentableItem findByID(Long id) {
        entityManager.getTransaction().begin();
        RentableItem rentableItem = entityManager.find(RentableItem.class, id);
        entityManager.getTransaction().commit();
        return rentableItem;
    }

    public String getReport() {

        entityManager.getTransaction().begin();
        List<RentableItem> rentableItems =
                entityManager.createQuery("SELECT r FROM RentableItem r").getResultList();
        entityManager.getTransaction().commit();

        StringBuilder stringBuilder = new StringBuilder();
        for (RentableItem rentableItem : rentableItems) {
            stringBuilder.append(rentableItem.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
