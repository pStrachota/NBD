package repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class RentsSchema {
    public static final CqlIdentifier rentsByClient = CqlIdentifier.fromCql("rents_by_client");
    public static final CqlIdentifier rentsByRentableItem =
            CqlIdentifier.fromCql("rents_by_rentable_item");
    public static final CqlIdentifier rentUuid = CqlIdentifier.fromCql("rent_uuid");
    public static final CqlIdentifier rentableItemUuid = CqlIdentifier.fromCql("rentable_item_uuid");
    public static final CqlIdentifier clientUuid = CqlIdentifier.fromCql("client_uuid");
    public static final CqlIdentifier beginTime = CqlIdentifier.fromCql("begin_time");
    public static final CqlIdentifier endTime = CqlIdentifier.fromCql("end_time");
    public static final CqlIdentifier rentCost = CqlIdentifier.fromCql("rent_cost");
    public static final CqlIdentifier isEnded = CqlIdentifier.fromCql("is_ended");
}