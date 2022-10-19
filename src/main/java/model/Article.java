package model;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Article extends RentableItem {

    private String parentOrganisation;

}
