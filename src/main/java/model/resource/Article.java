package model.resource;

import java.util.UUID;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "article")
public class Article extends RentableItem {

    @BsonProperty("parent_organisation")
    private String parentOrganisation;

    @BsonCreator
    public Article(@BsonProperty("id") UUID id,
                   @BsonProperty("isAvailable") boolean isAvailable,
                   @BsonProperty("serialNumber") String serialNumber,
                   @BsonProperty("author") String author,
                   @BsonProperty("title") String title,
                   @BsonProperty("parentOrganisation") String parentOrganisation) {
        super(id, isAvailable, serialNumber, author, title);
        this.parentOrganisation = parentOrganisation;
    }

}
