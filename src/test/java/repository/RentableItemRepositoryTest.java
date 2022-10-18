package repository;

import static dataForTests.testData.book;
import static dataForTests.testData.book2;
import static dataForTests.testData.book3;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Book;
import org.junit.jupiter.api.Test;

class RentableItemRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @Test
    void optimisticLockTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);

        try {
            persistRentableItem();
            es.execute(() -> {
                try {
                    updateRentableItem1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    updateRentableItem2();
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

    private static void persistRentableItem() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    private static void updateRentableItem1() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Book rentableItem1 = em.find(Book.class, 1L);
        rentableItem1.setTitle("new title");
        try {
            System.out.println("Pausing first transaction for 1 second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("committing first transaction");
        em.getTransaction().commit();
        em.close();
    }

    private static void updateRentableItem2() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Book rentableItem2 = em.find(Book.class, 1L);
        rentableItem2.setTitle("new title 2");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void addRentableItemTest() {
        try (RentableItemRepository rentableItemRepository = new RentableItemRepository()) {
            rentableItemRepository.addRentableItem(book2);
            assertThat(rentableItemRepository.findByID(1L)).isEqualTo(book2);
        }
    }

    @Test
    void removeRentableItemTest() {
        try (RentableItemRepository rentableItemRepository = new RentableItemRepository()) {
            rentableItemRepository.addRentableItem(book3);
            assertThat(rentableItemRepository.getAllRentableItems()).contains(book3);
            rentableItemRepository.removeRentableItem(book3);
            assertThat(rentableItemRepository.getAllRentableItems()).doesNotContain(book3);
        }
    }

}