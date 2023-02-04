package repository.impl;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import java.util.Optional;
import java.util.UUID;
import model.user.Client;
import repository.Schemas.ClientSchema;
import repository.dao_mapper.ClientDao;
import repository.dao_mapper.ClientMapper;

public class ClientRepository {

    private final ClientDao clientDao;

    public ClientRepository(CqlSession session) {
        session.execute(dropTable(ClientSchema.clients).ifExists().build());

        SimpleStatement createClientTable = createTable(ClientSchema.clients)
                .ifNotExists()
                .withPartitionKey(ClientSchema.clientUuid, DataTypes.UUID)
                .withColumn(ClientSchema.firstName, DataTypes.TEXT)
                .withColumn(ClientSchema.lastName, DataTypes.TEXT)
                .withColumn(ClientSchema.city, DataTypes.TEXT)
                .withColumn(ClientSchema.street, DataTypes.TEXT)
                .withColumn(ClientSchema.streetNr, DataTypes.TEXT)
                .withColumn(ClientSchema.clientType, DataTypes.TEXT)
                .build();

        session.execute(createClientTable);

        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        clientDao = clientMapper.clientDao();
    }

    public boolean add(Client client) {
        return clientDao.add(client.toClientHelper());
    }

    public void remove(Client client) {
        clientDao.remove(client.toClientHelper());
    }

    public Optional<Client> findById(UUID clientId) {
        return Optional.ofNullable(clientDao.findById(clientId).toClient());
    }

    public void update(Client client) {
        clientDao.update(client.toClientHelper());
    }

}
