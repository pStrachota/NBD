package model.resource;

import java.util.UUID;
import lombok.Data;
import model.AbstractEntity;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@BsonDiscriminator(key = "_clazz")
public class RentableItem extends AbstractEntity {

    @BsonProperty("isAvailable")
    private boolean isAvailable;

    @BsonProperty("serialNumber")
    private String serialNumber;

    @BsonProperty("author")
    private String author;

    @BsonProperty("title")
    private String title;

    @BsonCreator
    public RentableItem(@BsonProperty("id") UUID id,
                        @BsonProperty("isAvailable") boolean isAvailable,
                        @BsonProperty("serialNumber") String serialNumber,
                        @BsonProperty("author") String author,
                        @BsonProperty("title") String title) {
        super(id);
        this.isAvailable = isAvailable;
        this.serialNumber = serialNumber;
        this.author = author;
        this.title = title;
    }

}
