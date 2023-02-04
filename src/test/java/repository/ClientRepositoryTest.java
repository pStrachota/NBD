package repository;

import static dataForTests.testData.client;
import static dataForTests.testData.client2;
import static dataForTests.testData.client3;
import static org.assertj.core.api.Assertions.assertThat;

import model.user.Client;
import org.junit.jupiter.api.Test;
import repository.impl.ClientRepository;
import repository.impl.RepoProducer;

class ClientRepositoryTest {

    private static final ClientRepository clientRepository = RepoProducer.getClientRepository();

    @Test
    void addClientPositiveTest() {
        assertThat(clientRepository.add(client)).isTrue();
        Client addedClient = clientRepository.findById(client.getUuid()).get();
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
        client2.setFirstName("newName");
        clientRepository.update(client2);
        assertThat(clientRepository.findById(client2.getUuid()).get().getFirstName())
                .isEqualTo("newName");
    }

    @Test
    void removeClientTest() {
        clientRepository.add(client3);
        clientRepository.remove(client3);
        assertThat(clientRepository.findById(client3.getUuid())).isEmpty();
    }
}