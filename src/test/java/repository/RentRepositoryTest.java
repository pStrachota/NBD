package repository;

import static dataForTests.testData.rent;
import static dataForTests.testData.rent2;
import static dataForTests.testData.rent3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import model.Rent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class RentRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @AfterAll
    static void close() {
        entityManagerFactory.close();
    }

    @Test
    void optimisticLockExceptionTest() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager1.getTransaction().begin();

        entityManager.persist(rent);
        entityManager.getTransaction().commit();

        Rent rentEm = entityManager.find(Rent.class, rent.getRentId());
        Rent rentEm1 = entityManager1.find(Rent.class, rent.getRentId());

        entityManager.getTransaction().begin();
        rentEm.setRentCost(100);
        entityManager.getTransaction().commit();

        rentEm1.setRentCost(200);

        assertThatThrownBy(() -> entityManager1.getTransaction().commit()).isInstanceOf(
                RollbackException.class);

        entityManager.close();
        entityManager1.close();
    }

    @Test
    void removeRent() {
        try (RentRepository rentRepository = new RentRepository()) {
            rentRepository.add(rent3);
            assertThat(rentRepository.getItems()).contains(rent3);
            rentRepository.remove(rent3);
            assertThat(rentRepository.getItems()).doesNotContain(rent3);
        }
    }

    @Test
    void addRent() {
        try (RentRepository rentRepository = new RentRepository()) {
            rentRepository.add(rent2);
            assertThat(rentRepository.findByID(1L)).isEqualTo(rent2);
        }
    }
}