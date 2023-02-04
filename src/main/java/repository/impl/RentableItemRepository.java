package repository.impl;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import java.util.Optional;
import java.util.UUID;
import model.resource.RentableItem;
import repository.Schemas.RentableItemSchema;
import repository.dao_mapper.RentableItemDao;
import repository.dao_mapper.RentableItemMapper;

public class RentableItemRepository {

    private final RentableItemDao rentableItemDao;

    public RentableItemRepository(CqlSession session) {
        session.execute(dropTable(RentableItemSchema.rentableItems).ifExists().build());

        SimpleStatement createRentableItemTable =
                SchemaBuilder.createTable(RentableItemSchema.rentableItems)
                        .ifNotExists()
                        .withPartitionKey(RentableItemSchema.rentableItemUuid, DataTypes.UUID)
                        .withColumn(RentableItemSchema.title, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.author, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.serialNumber, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.publishingHouse, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.parentOrganisation, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.discriminator, DataTypes.TEXT)
                        .withColumn(RentableItemSchema.isAvailable, DataTypes.BOOLEAN)
                        .build();

        session.execute(createRentableItemTable);

        RentableItemMapper rentableItemMapper = new RentableItemMapperBuilder(session).build();
        rentableItemDao = rentableItemMapper.rentableItemDao();
    }

    public boolean add(RentableItem rentableItem) {
        return rentableItemDao.add(rentableItem);
    }

    public void remove(RentableItem rentableItem) {
        rentableItemDao.remove(rentableItem);
    }

    public Optional<RentableItem> findById(UUID rentableItemId) {
        return Optional.ofNullable(rentableItemDao.findById(rentableItemId));
    }

    public void update(RentableItem rentableItem) {
        rentableItemDao.update(rentableItem);
    }
}
