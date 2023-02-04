package repository.providers;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import java.util.UUID;
import model.resource.Article;
import model.resource.Book;
import model.resource.RentableItem;
import repository.Schemas.RentableItemSchema;

public class RentableItemRepositoryProvider {

    private final CqlSession session;

    private final EntityHelper<Article> articleEntityHelper;
    private final EntityHelper<Book> bookEntityHelper;

    public RentableItemRepositoryProvider(MapperContext context,
                                          EntityHelper<Article> articleEntityHelper,
                                          EntityHelper<Book> bookEntityHelper) {
        this.session = context.getSession();
        this.articleEntityHelper = articleEntityHelper;
        this.bookEntityHelper = bookEntityHelper;
    }

    public void add(RentableItem rentableItem) {
        session.execute(
                switch (rentableItem.getDiscriminator()) {
                    case "Book" -> {
                        Book book = (Book) rentableItem;
                        yield session.prepare(bookEntityHelper.insert().build())
                                .bind()
                                .setUuid(RentableItemSchema.rentableItemUuid, book.getUuid())
                                .setString(RentableItemSchema.title, book.getTitle())
                                .setString(RentableItemSchema.author, book.getAuthor())
                                .setString(RentableItemSchema.serialNumber, book.getSerialNumber())
                                .setString(RentableItemSchema.publishingHouse,
                                        book.getPublishingHouse())
                                .setString(RentableItemSchema.discriminator,
                                        book.getDiscriminator());

                    }

                    case "Article" -> {
                        Article article = (Article) rentableItem;
                        yield session.prepare(articleEntityHelper.insert().build())
                                .bind()
                                .setUuid(RentableItemSchema.rentableItemUuid, article.getUuid())
                                .setString(RentableItemSchema.title, article.getTitle())
                                .setString(RentableItemSchema.author, article.getAuthor())
                                .setString(RentableItemSchema.serialNumber,
                                        article.getSerialNumber())
                                .setString(RentableItemSchema.parentOrganisation,
                                        article.getParentOrganisation())
                                .setString(RentableItemSchema.discriminator,
                                        article.getDiscriminator());

                    }

                    default -> throw new IllegalStateException(
                            "Unexpected value: " + rentableItem.getDiscriminator().toLowerCase());
                }
        );
    }

    public RentableItem findById(UUID key) {
        Select selectEquipment = QueryBuilder.selectFrom(RentableItemSchema.rentableItems)
                .all()
                .where(Relation.column(RentableItemSchema.rentableItemUuid)
                        .isEqualTo(literal(key)));
        Row row = session.execute(selectEquipment.build()).one();
        if (row != null) {
            String discriminator = row.getString(RentableItemSchema.discriminator);
            if (discriminator == null) {
                throw new NullPointerException("discriminator not found");
            }
            return switch (discriminator) {
                case "Article" -> {
                    Article article = new Article(
                            row.getString(RentableItemSchema.title),
                            row.getString(RentableItemSchema.author),
                            row.getString(RentableItemSchema.parentOrganisation),
                            row.getString(RentableItemSchema.discriminator));
                    article.setUuid(row.getUuid(RentableItemSchema.rentableItemUuid));
                    yield article;
                }

                case "Book" -> {
                    Book book = new Book(
                            row.getString(RentableItemSchema.title),
                            row.getString(RentableItemSchema.author),
                            row.getString(RentableItemSchema.publishingHouse),
                            row.getString(RentableItemSchema.discriminator));
                    book.setUuid(row.getUuid(RentableItemSchema.rentableItemUuid));
                    yield book;
                }
                default -> throw new IllegalStateException("Unexpected value: " + discriminator);
            };
        }
        return null;
    }
}
