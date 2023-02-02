package model.resource;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Book extends RentableItem {

    private String publishingHouse;

}
