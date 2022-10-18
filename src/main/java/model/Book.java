package model;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Book extends RentableItem {

    private String publishingHouse;

    public Book(String serialNumber, String author, String title, String publishingHouse) {
        super(serialNumber, author, title);
        this.publishingHouse = publishingHouse;
    }
}
