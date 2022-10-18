package repository;

import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static dataForTests.testData.rent;
import static dataForTests.testData.rent2;
import static dataForTests.testData.rent3;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Rent;
import org.junit.jupiter.api.Test;

class RentRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @Test
    void optimisticLockTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);

        try {
            persistRent();
            es.execute(() -> {
                try {
                    updateRent1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    updateRent2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.shutdown();
            try {
                es.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } finally {
            entityManagerFactory.close();
        }

    }

    private static void persistRent() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(rent);
        em.getTransaction().commit();
        em.close();
    }

    private static void updateRent1() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Rent rent1 = em.find(Rent.class, 1L);
        rent1.setRentCost(1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        em.getTransaction().commit();
        em.close();
    }

    private static void updateRent2() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Rent rent2 = em.find(Rent.class, 1L);
        rent2.setRentCost(2000);
        em.getTransaction().commit();
        em.close();
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