package model.resource;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity(defaultKeyspace = "library")
@CqlName("rentable_items")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RentableItem implements Serializable {

    @CqlName("rentable_item_uuid")
    @PartitionKey
    private UUID uuid;

    @CqlName("title")
    private String title;

    @Size(min = 1)
    @CqlName("author")
    private String author;

    @Size(min = 9)
    @CqlName("serial_number")
    private String serialNumber;

    @CqlName("discriminator")
    private String discriminator;

    @CqlName("is_available")
    private boolean isAvailable;


    public RentableItem(
            String title,
            String author,
            String serialNumber,
            String discriminator
    ) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.serialNumber = serialNumber;
        this.discriminator = discriminator;
    }

}
