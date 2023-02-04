package repository.dao_mapper;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import java.util.UUID;
import model.resource.Article;
import model.resource.Book;
import model.resource.RentableItem;
import repository.providers.RentableItemRepositoryProvider;

@Dao
public interface RentableItemDao {

    @QueryProvider(providerClass = RentableItemRepositoryProvider.class,
            entityHelpers = {Article.class, Book.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean add(RentableItem rentableItem);

    @QueryProvider(providerClass = RentableItemRepositoryProvider.class,
            entityHelpers = {Article.class, Book.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    RentableItem findById(UUID uuid);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(RentableItem rentableItem);

    @Delete(entityClass = RentableItem.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    void remove(RentableItem rentableItem);
}
