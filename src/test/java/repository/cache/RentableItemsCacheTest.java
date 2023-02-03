package repository.cache;

import static dataForTests.testData.book;
import static dataForTests.testData.book2;

import java.util.Optional;
import model.resource.RentableItem;
import org.junit.jupiter.api.Test;

class RentableItemsCacheTest {

    @Test
    void second_read_from_redisCache_instead_of_database() {
        RentableItemsCache rentableItemService = new RentableItemsCache();
        System.out.println("read when item is not in redis repository.cache nor in mongodb");

        Optional<RentableItem> rentableItemMdb = rentableItemService.findByID(book.getUuid());
        if (!rentableItemMdb.isPresent()) {
            System.out.println("There is no item in redis repository.cache nor in mongodb");
        }
        rentableItemService.add(book);
        System.out.println("Read from redis repository.cache instead of database");
        rentableItemService.findByID(book.getUuid());
    }

    @Test
    void read_from_mongodb_when_lost_connection_to_redis() {
        RentableItemsCache rentableItemService = new RentableItemsCache();
        rentableItemService.add(book2);

        System.out.println("Read from mongodb when lost connection to redis");

        rentableItemService.findByID(book2.getUuid());
    }
}