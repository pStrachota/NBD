package model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
public class AbstractEntity {

    @BsonProperty("_id")
    private UUID uuid;

    public AbstractEntity(UUID uuid) {
        this.uuid = uuid;
    }
}
