package repository.impl;

import com.datastax.oss.driver.api.core.CqlSession;
import java.net.InetSocketAddress;
import repository.Schemas.SchemaConst;

public class RepoProducer {

    private static final CqlSession session = CqlSession.builder()
            .addContactPoint(new InetSocketAddress("localhost", 9042))
            .addContactPoint(new InetSocketAddress("localhost", 9043))
            .withLocalDatacenter("dc1")
            .withKeyspace(SchemaConst.LIBRARY_NAMESPACE)
            .withAuthCredentials("cassandra", "cassandra")
            .build();
    private static final ClientRepository clientRepository = new ClientRepository(session);
    private static final RentableItemRepository rentableItemRepository =
            new RentableItemRepository(session);
    private static final RentRepository rentRepository = new RentRepository(session);

    public static ClientRepository getClientRepository() {
        return clientRepository;
    }

    public static RentableItemRepository getRentableItemRepository() {
        return rentableItemRepository;
    }

    public static RentRepository getRentRepository() {
        return rentRepository;
    }
}
