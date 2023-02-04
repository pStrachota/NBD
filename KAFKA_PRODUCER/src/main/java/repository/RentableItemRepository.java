package repository;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.resource.RentableItem;
import org.bson.conversions.Bson;

public class RentableItemRepository extends MongoRepository implements Repository<RentableItem> {

    MongoCollection<RentableItem> rentableItemCollection = mongoDatabase.getCollection("rentable_items", RentableItem.class);

    public RentableItemRepository() {
        rentableItemCollection.drop();
        rentableItemCollection = mongoDatabase.getCollection("rentable_items", RentableItem.class);
    }

    @Override
    public RentableItem add(RentableItem rentableItem) {
        try {
            rentableItemCollection.insertOne(rentableItem);
            return rentableItem;
        } catch (MongoWriteException e) {
            return null;
        }
    }

    @Override
    public void remove(RentableItem rentableItem) {
        Bson filter = Filters.eq("_id", rentableItem.getUuid());
        rentableItemCollection.findOneAndDelete(filter);
    }

    @Override
    public Optional<RentableItem> findByID(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return Optional.ofNullable(rentableItemCollection.find(filter).first());
    }

    @Override
    public boolean update(RentableItem rentableItem) {
        Bson filter = Filters.eq("_id", rentableItem.getUuid());
        Bson update = Updates.combine(
                Updates.set("author", rentableItem.getAuthor()),
                Updates.set("title", rentableItem.getTitle()),
                Updates.set("serialNumber", rentableItem.getSerialNumber()),
                Updates.set("isAvailable", rentableItem.isAvailable())
        );
        return rentableItemCollection.updateOne(filter, update).wasAcknowledged();
    }

    @Override
    public List<RentableItem> getItems() {
        return rentableItemCollection.find().into(new ArrayList<>());
    }
}
