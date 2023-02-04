package repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class RentableItemSchema {
    public static final CqlIdentifier rentableItems = CqlIdentifier.fromCql("rentable_items");
    public static final CqlIdentifier rentableItemUuid = CqlIdentifier.fromCql("rentable_item_uuid");
    public static final CqlIdentifier title = CqlIdentifier.fromCql("title");
    public static final CqlIdentifier author = CqlIdentifier.fromCql("author");
    public static final CqlIdentifier serialNumber = CqlIdentifier.fromCql("serial_number");
    public static final CqlIdentifier discriminator = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier parentOrganisation =
            CqlIdentifier.fromCql("parent_organisation");
    public static final CqlIdentifier publishingHouse = CqlIdentifier.fromCql("publishing_house");
    public static final CqlIdentifier isAvailable = CqlIdentifier.fromCql("is_available");
}
