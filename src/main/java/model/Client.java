package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "personalId")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personalId;

    private String name;

    @Version
    private Integer version;

    private String surname;

    @OneToMany(mappedBy = "client")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<Rent> rents;

    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    public void addRent(Rent rent) {
        if (rents == null) {
            rents = new ArrayList<>();
        }
        rents.add(rent);
        rent.setClient(this);
    }

    public void removeRent(Rent rent) {
        if (rents != null) {
            rents.remove(rent);
            rent.setClient(null);
        }
    }
}
