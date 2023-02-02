package repository;

import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import model.user.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class ClientRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @AfterAll
    static void close() {
        entityManagerFactory.close();
    }

    @Test
    void addClientTest() {

        try (ClientRepository clientRepository = new ClientRepository()) {
            System.out.println(clientRepository.getClients().size());
            clientRepository.addClient(client);
            assertThat(clientRepository.findByID(client.getPersonalId())).isEqualTo(client);
        }
    }

    @Test
    void OptimisticLockExceptionTest() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager1.getTransaction().begin();

        entityManager.persist(client2);
        entityManager.getTransaction().commit();

        Client clientEm = entityManager.find(Client.class, client2.getPersonalId());
        Client clientEm1 = entityManager1.find(Client.class, client2.getPersonalId());

        entityManager.getTransaction().begin();
        clientEm.setName("newName");
        entityManager.getTransaction().commit();

        clientEm1.setName("newName1");

        assertThatThrownBy(() -> entityManager1.getTransaction().commit()).isInstanceOf(RollbackException.class);

        entityManager.close();
        entityManager1.close();
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