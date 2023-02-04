package repository.providers;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import java.util.UUID;
import model.user.ClientHelper;
import repository.Schemas.ClientSchema;

public class ClientRepositoryProvider {
    private final CqlSession session;

    private EntityHelper<ClientHelper> clientEntityHelper;

    public ClientRepositoryProvider(MapperContext context,
                                    EntityHelper<ClientHelper> clientEntityHelper) {
        this.session = context.getSession();
        this.clientEntityHelper = clientEntityHelper;
    }


    public ClientHelper findById(UUID uuid) {
        Select selectClient = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("clients"))
                .all()
                .where(Relation.column(ClientSchema.clientUuid).isEqualTo(literal(uuid)));
        Row row = session.execute(selectClient.build()).one();
        try {
            return getClient(row);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ClientHelper getClient(Row row) {
        if (row != null) {
            return new ClientHelper(
                    row.getUuid(ClientSchema.clientUuid),
                    row.getString(ClientSchema.firstName),
                    row.getString(ClientSchema.lastName),
                    row.getString(ClientSchema.clientType),
                    row.getString(ClientSchema.city),
                    row.getString(ClientSchema.street),
                    row.getString(ClientSchema.streetNr)
            );
        }
        return null;
    }
}
