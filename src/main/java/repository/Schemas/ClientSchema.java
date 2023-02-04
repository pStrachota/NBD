package repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientSchema {
    public static final CqlIdentifier clients = CqlIdentifier.fromCql("clients");
    public static final CqlIdentifier clientUuid = CqlIdentifier.fromCql("client_uuid");
    public static final CqlIdentifier firstName = CqlIdentifier.fromCql("first_name");
    public static final CqlIdentifier lastName = CqlIdentifier.fromCql("last_name");
    public static final CqlIdentifier clientType = CqlIdentifier.fromCql("client_type");
    public static final CqlIdentifier city = CqlIdentifier.fromCql("city");
    public static final CqlIdentifier street = CqlIdentifier.fromCql("street");
    public static final CqlIdentifier streetNr = CqlIdentifier.fromCql("street_nr");
}
