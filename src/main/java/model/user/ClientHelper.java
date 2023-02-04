package model.user;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(defaultKeyspace = "library")
@CqlName("clients")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class ClientHelper implements Serializable {

    @CqlName("client_uuid")
    @PartitionKey
    private UUID uuid;

    @CqlName("first_name")
    private String firstName;

    @CqlName("last_name")
    private String lastName;

    @CqlName("client_type")
    private String clientType;

    @CqlName("city")
    private String city;

    @CqlName("street")
    private String street;

    @CqlName("street_nr")
    private String streetNr;

    public ClientHelper(UUID uuid, String firstName, String lastName, String clientType, String city, String street,
                        String streetNr) {
        this.uuid = uuid == null ? UUID.randomUUID() : uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public Client toClient() {
        return new Client(
                firstName,
                lastName,
                new Address(city, street, streetNr),
                ClientType.valueOf(clientType)
        );
    }


}