package repository;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Rent;
import model.user.Client;
import org.bson.conversions.Bson;

public class RentRepository extends MongoRepository implements Repository<Rent> {

    MongoCollection<Rent>
            rentsCollection = mongoDatabase.getCollection("rents", Rent.class);

    public RentRepository() {
        rentsCollection.drop();
        rentsCollection = mongoDatabase.getCollection("rents", Rent.class);
    }

    @Override
    public Rent add(Rent rent) {
        try {
            rentsCollection.insertOne(rent);
            return rent;
        } catch (MongoWriteException e) {
            return null;
        }
    }

    @Override
    public void remove(Rent rent) {
        Bson filter = Filters.eq("_id", rent.getUuid());
        rentsCollection.findOneAndDelete(filter);
    }

    @Override
    public Optional<Rent> findByID(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return Optional.ofNullable(rentsCollection.find(filter).first());
    }

    @Override
    public boolean update(Rent rent) {

        Bson filter = Filters.eq("_id", rent.getUuid());
        Bson update = Updates.combine(
                Updates.set("is_ended", rent.isEnded()),
                Updates.set("rent_cost", rent.getRentCost())
        );
        return rentsCollection.updateOne(filter, update).wasAcknowledged();
    }

    public List<Rent> getClientsRents(Client client) {
        Bson filter = Filters.eq("client._id", client.getUuid());
        return rentsCollection.find(filter).into(new ArrayList<>());
    }

    @Override
    public List<Rent> getItems() {
        return rentsCollection.find().into(new ArrayList<>());
    }

    public int getNumberOfClientRentedItems(Client client) {
        Bson filter = Filters.eq("client._id", client.getUuid());
        return (int) rentsCollection.countDocuments(filter);
    }
}

