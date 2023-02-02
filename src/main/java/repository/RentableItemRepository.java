package repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.resource.RentableItem;

public class RentableItemRepository extends Repository<RentableItem> implements AutoCloseable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RentableItemRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public RentableItem add(RentableItem rentableItem) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(rentableItem);
            entityManager.getTransaction().commit();
            return rentableItem;
        } catch (Exception e) {
            return null;
        }
    }

    public List<RentableItem> getItems() {
        return entityManager.createQuery("from RentableItem", RentableItem.class).getResultList();
    }

    public boolean remove(RentableItem rentableItem) {

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(rentableItem);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<RentableItem> findByID(Long id) {
        entityManager.getTransaction().begin();
        RentableItem rentableItem = entityManager.find(RentableItem.class, id);
        entityManager.getTransaction().commit();
        return Optional.ofNullable(rentableItem);
    }

    @Override
    public RentableItem update(RentableItem rentableItem) {

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(rentableItem);
            entityManager.getTransaction().commit();
            return rentableItem;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
