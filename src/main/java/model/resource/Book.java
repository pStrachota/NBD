package model.resource;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Book extends RentableItem {

    @NotBlank
    private String publishingHouse;

    @Builder
    public Book(long id, boolean isAvailable, String serialNumber,
                @NotEmpty String author,
                @NotEmpty String title, String publishingHouse) {
        super(id, isAvailable, serialNumber, author, title);
        this.publishingHouse = publishingHouse;
    }

}
