package model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import model.resource.RentableItem;
import model.user.Client;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "rentId", callSuper = false)
@AllArgsConstructor
public class Rent extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    @NotNull
    private LocalDateTime beginTime;

    @NotNull
    private LocalDateTime endTime;

    private double rentCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private Client client;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean isEnded;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private List<RentableItem> rentableItems;

}
