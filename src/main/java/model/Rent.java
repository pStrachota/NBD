package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.resource.RentableItem;
import model.user.Client;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
public class Rent extends AbstractEntity {

    @BsonProperty("begin_time")
    private LocalDateTime beginTime;

    @BsonProperty("end_time")
    private LocalDateTime endTime;


    @BsonProperty("rent_cost")
    private double rentCost;

    @BsonProperty("client")
    private Client client;

    @BsonProperty("is_ended")
    private boolean isEnded;

    @BsonProperty("rentable_items")
    private List<RentableItem> rentableItems;

    @BsonCreator
    public Rent(@BsonProperty("id") UUID id,
                @BsonProperty("beginTime") LocalDateTime beginTime,
                @BsonProperty("endTime") LocalDateTime endTime,
                @BsonProperty("rentCost") double rentCost,
                @BsonProperty("client") Client client,
                @BsonProperty("isEnded") boolean isEnded,
                @BsonProperty("rentableItems") List<RentableItem> rentableItems) {
        super(id);
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.client = client;
        this.isEnded = isEnded;
        this.rentableItems = rentableItems;
    }

    public Rent(@BsonProperty("beginTime") LocalDateTime beginTime,
                @BsonProperty("endTime") LocalDateTime endTime,
                @BsonProperty("rentCost") double rentCost,
                @BsonProperty("client") Client client,
                @BsonProperty("isEnded") boolean isEnded,
                @BsonProperty("rentableItems") List<RentableItem> rentableItems) {
        super(UUID.randomUUID());
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.client = client;
        this.isEnded = isEnded;
        this.rentableItems = rentableItems;
    }

}
