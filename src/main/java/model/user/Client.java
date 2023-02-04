package model.user;


import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class Client implements Serializable {

    private String firstName;

    private UUID uuid;

    private String lastName;

    private Address address;

    private ClientType clientType;

    public Client(String firstName, String lastName, Address address, ClientType clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
        this.uuid = UUID.randomUUID();
    }

    public ClientHelper toClientHelper() {
        return new ClientHelper(
                uuid,
                firstName,
                lastName,
                clientType.getTypeInfo(),
                address.getCity(),
                address.getStreet(),
                address.getNumber()
        );
    }

}