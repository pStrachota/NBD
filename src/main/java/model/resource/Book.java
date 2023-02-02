package model.resource;

import java.util.UUID;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "book")
public class Book extends RentableItem {

    @BsonProperty("publishing_house")
    private String publishingHouse;

    @BsonCreator
    public Book(@BsonProperty("id") UUID id,
                @BsonProperty("isAvailable") boolean isAvailable,
                @BsonProperty("serialNumber") String serialNumber,
                @BsonProperty("author") String author,
                @BsonProperty("title") String title,
                @BsonProperty("publishingHouse") String publishingHouse) {
        super(id, isAvailable, serialNumber, author, title);
        this.publishingHouse = publishingHouse;
    }

}
