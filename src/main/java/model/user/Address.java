package model.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
@Builder
public class Address {

    @BsonProperty("street")
    private String street;

    @BsonProperty("city")
    private String city;

    @BsonProperty("number")
    private String number;

    @BsonCreator
    public Address(@BsonProperty("street") String street,
                   @BsonProperty("city") String city,
                   @BsonProperty("number") String number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }
}
