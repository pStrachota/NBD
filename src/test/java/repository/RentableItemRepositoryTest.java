package repository;

import static dataForTests.testData.book;
import static dataForTests.testData.book2;
import static dataForTests.testData.book3;
import static dataForTests.testData.client2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import model.Book;
import model.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class RentableItemRepositoryTest {

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

        entityManager.persist(book);
        entityManager.getTransaction().commit();

        Book bookEm = entityManager.find(Book.class, book.getId());
        Book bookEm1 = entityManager1.find(Book.class, book.getId());

        entityManager.getTransaction().begin();
        bookEm.setSerialNumber("new serial number");
        entityManager.getTransaction().commit();

        bookEm1.setSerialNumber("new serial number1");

        assertThatThrownBy(() -> entityManager1.getTransaction().commit()).isInstanceOf(
                RollbackException.class);

        entityManager.close();
        entityManager1.close();
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