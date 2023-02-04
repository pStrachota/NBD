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
public class Article extends RentableItem {

    @CqlName("parent_organisation")
    private String parentOrganisation;

    @Builder
    public Article(
            String title,
            String author,
            String serialNumber,
            String parentOrganisation
    ) {
        super(title, author, serialNumber, "Article");
        this.parentOrganisation = parentOrganisation;
    }

}