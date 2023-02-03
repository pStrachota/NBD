package repository;

import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import manager.ClientManager;
import model.user.Client;
import org.junit.jupiter.api.Test;

class ClientRepositoryTest {

    private static final ClientRepository clientRepository = new ClientRepository();

    @Test
    void addClientPositiveTest() {
        Client addedClient = clientRepository.add(client);
        assertThat(addedClient).isEqualTo(client);
    }

    @Test
    void addClientNegativeTest() {
        clientRepository.add(client);
        assertThat(clientRepository.add(client)).isNull();
    }

    @Test
    void updateClientTest() {
        clientRepository.add(client2);
        client2.setName("newName");
        clientRepository.update(client2);
        assertThat(clientRepository.findByID(client2.getUuid()).get().getName()).isEqualTo("newName");
    }

    @Test
    void removeClientTest() {
        clientRepository.add(client3);
        clientRepository.remove(client3);
        assertThat(clientRepository.findByID(client3.getUuid())).isEmpty();
    }

}