package model.user;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AbstractEntity;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Client extends AbstractEntity {

    @BsonProperty("name")
    private String name;

    @BsonProperty("surname")
    private String surname;

    @BsonProperty("address")
    private Address address;

    @BsonProperty(useDiscriminator = true)
    private ClientType clientType;

    @BsonCreator
    public Client(@BsonProperty("id") UUID id,
                  @BsonProperty("name") String name,
                  @BsonProperty("surname") String surname,
                  @BsonProperty("address") Address address,
                  @BsonProperty("clientType") ClientType clientType) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.clientType = clientType;
    }

    public Client(String name, String surname, Address address, ClientType clientType) {
        super(UUID.randomUUID());
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.clientType = clientType;
    }

}
