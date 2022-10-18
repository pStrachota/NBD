package model;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Article extends RentableItem {

    private String parentOrganisation;

    public Article(String serialNumber, String author, String title,
                   String parentOrganisation) {
        super(serialNumber, author, title);
        this.parentOrganisation = parentOrganisation;
    }
}
