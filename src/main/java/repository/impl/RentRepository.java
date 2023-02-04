package repository.impl;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.rent.Rent;
import repository.Schemas.RentsSchema;
import repository.dao_mapper.RentDao;
import repository.dao_mapper.RentMapper;

public class RentRepository {

    private final RentDao rentDao;

    public RentRepository(CqlSession session) {

        session.execute(dropTable(RentsSchema.rentsByClient).ifExists().build());
        session.execute(dropTable(RentsSchema.rentsByRentableItem).ifExists().build());

        SimpleStatement createRentsByClientTable =
                SchemaBuilder.createTable(RentsSchema.rentsByClient)
                        .ifNotExists()
                        .withPartitionKey(RentsSchema.clientUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.beginTime, DataTypes.DATE)
                        .withClusteringColumn(RentsSchema.endTime, DataTypes.DATE)
                        .withColumn(RentsSchema.rentUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.rentableItemUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.rentCost, DataTypes.DOUBLE)
                        .withColumn(RentsSchema.isEnded, DataTypes.BOOLEAN)
                        .build();

        SimpleStatement createRentsByRentableItemTable =
                SchemaBuilder.createTable(RentsSchema.rentsByRentableItem)
                        .ifNotExists()
                        .withPartitionKey(RentsSchema.rentsByRentableItem, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.beginTime, DataTypes.DATE)
                        .withClusteringColumn(RentsSchema.endTime, DataTypes.DATE)
                        .withColumn(RentsSchema.rentUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.clientUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.rentCost, DataTypes.DOUBLE)
                        .withColumn(RentsSchema.isEnded, DataTypes.BOOLEAN)
                        .build();

        session.execute(createRentsByClientTable);
        session.execute(createRentsByRentableItemTable);


        RentMapper rentMapper = new RentMapperBuilder(session).build();
        rentDao = rentMapper.rentDao();
    }

    public boolean add(Rent rent) {
        return rentDao.add(rent);
    }

    public void remove(Rent rent) {
        rentDao.remove(rent);
    }

    public List<Rent> findByClientId(UUID clientUuid) {
        return rentDao.findByClientId(clientUuid);
    }

    public List<Rent> findByRentableItemId(UUID rentableItemUuid) {
        return rentDao.findByRentableItemId(rentableItemUuid);
    }

    public void update(Rent rent) {
        rentDao.update(rent);
    }

    public Optional<Rent> findById(UUID rentUuid) {
        return Optional.ofNullable(rentDao.findByRentId(rentUuid));
    }
}
