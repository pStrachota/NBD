package repository.dao_mapper;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import java.util.UUID;
import model.user.ClientHelper;
import repository.providers.ClientRepositoryProvider;

@Dao
public interface ClientDao {

    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean add(ClientHelper clientHelper);

    @QueryProvider(providerClass = ClientRepositoryProvider.class,
            entityHelpers = {ClientHelper.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    ClientHelper findById(UUID clientId);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(ClientHelper clientHelper);


    @Delete
    @StatementAttributes(consistencyLevel = "QUORUM")
    void remove(ClientHelper clientHelper);
}
