package repository.providers;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import java.util.List;
import java.util.UUID;
import model.rent.Rent;
import model.resource.RentableItem;
import model.user.Client;
import repository.Schemas.RentsSchema;
import repository.impl.ClientRepository;
import repository.impl.RentableItemRepository;
import repository.impl.RepoProducer;

public class RentRepositoryProvider {
    private final CqlSession session;
    ClientRepository clientRepository = RepoProducer.getClientRepository();
    RentableItemRepository rentableItemRepository = RepoProducer.getRentableItemRepository();

    public RentRepositoryProvider(MapperContext context) {
        this.session = context.getSession();
    }

    public boolean add(Rent rent) {
        SimpleStatement insertRentByClient = QueryBuilder
                .insertInto(RentsSchema.rentsByClient)
                .value(RentsSchema.clientUuid, literal(rent.getClient().getUuid()))
                .value(RentsSchema.beginTime, literal(rent.getBeginTime()))
                .value(RentsSchema.rentUuid, literal(rent.getUuid()))
                .value(RentsSchema.endTime, literal(rent.getEndTime()))
                .value(RentsSchema.rentableItemUuid, literal(rent.getRentableItem().getUuid()))
                .value(RentsSchema.rentCost, literal(rent.getRentCost()))
                .build();

        SimpleStatement insertRentByRentableItem = QueryBuilder
                .insertInto(RentsSchema.rentsByClient)
                .value(RentsSchema.clientUuid, literal(rent.getClient().getUuid()))
                .value(RentsSchema.beginTime, literal(rent.getBeginTime()))
                .value(RentsSchema.endTime, literal(rent.getEndTime()))
                .value(RentsSchema.rentUuid, literal(rent.getUuid()))
                .value(RentsSchema.rentableItemUuid, literal(rent.getRentableItem().getUuid()))
                .value(RentsSchema.rentCost, literal(rent.getRentCost()))
                .build();

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(insertRentByClient)
                .addStatement(insertRentByRentableItem)
                .build();

        return session.execute(batchStatement).wasApplied();
    }

    public boolean remove(Rent rent) {
        SimpleStatement deleteRentByClient = QueryBuilder
                .deleteFrom(RentsSchema.rentsByClient)
                .whereColumn(RentsSchema.clientUuid).isEqualTo(literal(rent.getClient().getUuid()))
                .whereColumn(RentsSchema.beginTime).isEqualTo(literal(rent.getBeginTime()))
                .whereColumn(RentsSchema.endTime).isEqualTo(literal(rent.getEndTime()))
                .build();

        SimpleStatement deleteRentByRentableItem = QueryBuilder
                .deleteFrom(RentsSchema.rentsByRentableItem)
                .whereColumn(RentsSchema.rentableItemUuid)
                .isEqualTo(literal(rent.getRentableItem().getUuid()))
                .whereColumn(RentsSchema.beginTime).isEqualTo(literal(rent.getBeginTime()))
                .whereColumn(RentsSchema.endTime).isEqualTo(literal(rent.getEndTime()))
                .build();

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(deleteRentByClient)
                .addStatement(deleteRentByRentableItem)
                .build();

        return session.execute(batchStatement).wasApplied();
    }

    public void update(Rent rent) {
        SimpleStatement updateRentByClient = QueryBuilder
                .update(RentsSchema.rentsByClient)
                .setColumn(RentsSchema.rentCost, literal(rent.getRentCost()))
                .whereColumn(RentsSchema.clientUuid).isEqualTo(literal(rent.getClient().getUuid()))
                .whereColumn(RentsSchema.beginTime).isEqualTo(literal(rent.getBeginTime()))
                .whereColumn(RentsSchema.endTime).isEqualTo(literal(rent.getEndTime()))
                .build();

        SimpleStatement updateRentByRentableItem = QueryBuilder
                .update(RentsSchema.rentsByRentableItem)
                .setColumn(RentsSchema.rentCost, literal(rent.getRentCost()))
                .whereColumn(RentsSchema.rentableItemUuid)
                .isEqualTo(literal(rent.getRentableItem().getUuid()))
                .whereColumn(RentsSchema.beginTime).isEqualTo(literal(rent.getBeginTime()))
                .whereColumn(RentsSchema.endTime).isEqualTo(literal(rent.getEndTime()))
                .build();

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(updateRentByClient)
                .addStatement(updateRentByRentableItem)
                .build();

        session.execute(batchStatement).wasApplied();
    }

    public List<Rent> findByClientId(UUID clientId) {
        SimpleStatement selectRentByClient = QueryBuilder
                .selectFrom(RentsSchema.rentsByClient)
                .all()
                .whereColumn(RentsSchema.clientUuid).isEqualTo(literal(clientId))
                .build();

        return session.execute(selectRentByClient)
                .map(this::getRent)
                .all();
    }

    private Rent getRent(Row row) {

        RentableItem rentableItem =
                rentableItemRepository.findById(row.getUuid(RentsSchema.rentableItemUuid)).get();
        Client client = clientRepository.findById(row.getUuid(RentsSchema.clientUuid)).get();

        return new Rent(
                row.getLocalDate(RentsSchema.beginTime),
                row.getLocalDate(RentsSchema.endTime),
                rentableItem,
                client
        );
    }

    public Rent findByRentId(UUID rentId) {
        SimpleStatement selectRentByClient = QueryBuilder
                .selectFrom(RentsSchema.rentsByClient)
                .all()
                .whereColumn(RentsSchema.rentUuid).isEqualTo(literal(rentId))
                .build();

        return session.execute(selectRentByClient)
                .map(this::getRent)
                .one();
    }

    public List<Rent> findByRentableItemId(UUID rentableItemId) {
        SimpleStatement selectRentByRentableItem = QueryBuilder
                .selectFrom(RentsSchema.rentsByRentableItem)
                .all()
                .whereColumn(RentsSchema.rentableItemUuid).isEqualTo(literal(rentableItemId))
                .build();

        return session.execute(selectRentByRentableItem)
                .map(this::getRent)
                .all();
    }

}
