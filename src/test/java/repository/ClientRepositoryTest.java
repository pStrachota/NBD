package repository;

import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class ClientRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @Test
    void addClientTest() {

        try (ClientRepository clientRepository = new ClientRepository()) {
            System.out.println(clientRepository.getClients().size());
            clientRepository.addClient(client2);
            assertThat(clientRepository.findByID(client2.getPersonalId())).isEqualTo(client2);
        }
    }

        @Test
    void optimisticLockTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);

        try {
            persistClient();
            es.execute(() -> {
                try {
                    updateClient1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    updateClient2();
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

    private static void persistClient() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
        em.close();
    }

    private static void updateClient1() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Client client1 = em.find(Client.class, 1L);
        client1.setName("Updated 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("committing first transaction");
        em.getTransaction().commit();
        em.close();
    }

    private static void updateClient2() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Client client2 = em.find(Client.class, 1L);
        client2.setName("Updated 2");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void removeClientTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            clientRepository.addClient(client3);
            assertThat(clientRepository.getClients()).contains(client3);
            clientRepository.removeClient(client3);
            assertThat(clientRepository.getClients()).doesNotContain(client3);
        }
    }
}