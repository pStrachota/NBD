package repository.dao_mapper;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import java.util.List;
import java.util.UUID;
import model.rent.Rent;
import repository.providers.RentRepositoryProvider;

@Dao
public interface RentDao {

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean add(Rent rent);

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean remove(Rent rent);

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(Rent rent);

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    List<Rent> findByClientId(UUID clientId);

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    List<Rent> findByRentableItemId(UUID rentableItemId);

    @QueryProvider(providerClass = RentRepositoryProvider.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    Rent findByRentId(UUID uuid);

}
