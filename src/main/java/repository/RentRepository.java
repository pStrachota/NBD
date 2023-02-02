package repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Rent;

public class RentRepository extends Repository<Rent> implements AutoCloseable {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RentRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Rent add(Rent rent) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(rent);
            entityManager.getTransaction().commit();
            return rent;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rent> getItems() {
        return entityManager.createQuery("from Rent", Rent.class).getResultList();
    }

    public boolean remove(Rent rent) {
        try {

            entityManager.getTransaction().begin();
            entityManager.remove(rent);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Rent> findByID(Long id) {
        entityManager.getTransaction().begin();
        Rent rent = entityManager.find(Rent.class, id);
        entityManager.getTransaction().commit();
        return Optional.ofNullable(rent);
    }

    @Override
    public Rent update(Rent rent) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(rent);
            entityManager.getTransaction().commit();
            return rent;
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

