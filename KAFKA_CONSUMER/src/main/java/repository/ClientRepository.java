package repository;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.user.Client;
import org.bson.conversions.Bson;

public class ClientRepository extends MongoRepository implements Repository<Client> {

    MongoCollection<Client> clientsCollection = mongoDatabase.getCollection("clients", Client.class);

    public ClientRepository() {
        clientsCollection.drop();
        clientsCollection = mongoDatabase.getCollection("clients", Client.class);
    }

    @Override
    public Client add(Client item) {
        try {
            clientsCollection.insertOne(item);
            return item;
        } catch (MongoWriteException e) {
            return null;
        }
    }

    @Override
    public void remove(Client item) {
        Bson filter = Filters.eq("_id", item.getUuid());
        clientsCollection.findOneAndDelete(filter);
    }

    @Override
    public Optional<Client> findByID(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return Optional.ofNullable(clientsCollection.find(filter).first());
    }

    @Override
    public boolean update(Client client) {

        Bson filter = Filters.eq("_id", client.getUuid());
        Bson update = Updates.combine(
                Updates.set("name", client.getName()),
                Updates.set("surname", client.getSurname()),
                Updates.set("clientType", client.getClientType()),
                Updates.set("address", client.getAddress())
        );
        return clientsCollection.updateOne(filter, update).wasAcknowledged();

    }

    @Override
    public List<Client> getItems() {
        return clientsCollection.find().into(new ArrayList<>());
    }
}
