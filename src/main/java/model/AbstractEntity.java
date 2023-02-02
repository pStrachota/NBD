package model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Data;

@MappedSuperclass
@Data
public class AbstractEntity implements Serializable {

    @NotNull
    private UUID uuid;

    @Version
    private long version;

}
