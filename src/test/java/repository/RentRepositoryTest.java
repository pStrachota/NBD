package repository;

import static dataForTests.testData.book;
import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static dataForTests.testData.rent;
import static dataForTests.testData.rent2;
import static dataForTests.testData.rent3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import model.Book;
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
            client3.addRent(rent3);
            rentRepository.addCurrentRent(rent3);
            assertThat(rentRepository.getRents()).contains(rent3);
            rentRepository.removeRent(rent3);
            assertThat(rentRepository.getRents()).doesNotContain(rent3);
        }
    }

    @Test
    void addRent() {
        try (RentRepository rentRepository = new RentRepository()) {
            client.addRent(rent2);
            rentRepository.addCurrentRent(rent2);
            assertThat(rentRepository.findByID(1L)).isEqualTo(rent2);
        }
    }
}