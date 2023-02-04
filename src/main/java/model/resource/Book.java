package model.resource;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Builder;
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
@EqualsAndHashCode(callSuper = false)
@ToString
public class Book extends RentableItem {

    @CqlName("publishing_house")
    private String publishingHouse;

    @Builder
    public Book(
            String title,
            String author,
            String serialNumber,
            String publishingHouse
    ) {
        super(title, author, serialNumber, "Book");
        this.publishingHouse = publishingHouse;
    }

}