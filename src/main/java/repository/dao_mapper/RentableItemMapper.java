package repository.dao_mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface RentableItemMapper {

    @DaoFactory
    RentableItemDao rentableItemDao();
}
