package model.rent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.resource.RentableItem;
import model.user.Client;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Rent implements Serializable {

    private UUID uuid;

    private RentableItem rentableItem;

    private Client client;

    private LocalDate beginTime;

    private LocalDate endTime;

    private double rentCost;

    private boolean isEnded;

    public Rent(LocalDate beginTime, LocalDate endTime,
                RentableItem rentableItem, Client client) {

        this.uuid = UUID.randomUUID();
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentableItem = rentableItem;
        this.client = client;
    }

}