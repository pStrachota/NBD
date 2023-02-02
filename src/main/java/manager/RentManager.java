package manager;

import exception.EndedRentException;
import exception.ItemNotFoundException;
import exception.MaxItemLimitExceededException;
import exception.RentableItemNotAvailableException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import model.Rent;
import model.resource.RentableItem;
import model.user.Client;
import repository.ClientRepository;
import repository.RentRepository;
import repository.RentableItemRepository;

@AllArgsConstructor
public class RentManager {

    RentRepository rentRepository;
    ClientRepository clientRepository;
    RentableItemRepository rentableItemRepository;

    public Rent addRent(long clientId, List<Long> rentableItemIds) {

        Client client = clientRepository.findByID(clientId)
                .orElseThrow(() -> new ItemNotFoundException("Client not found"));

        List<RentableItem> rentableItems = new ArrayList<>();

        rentableItemIds.forEach(rentableItemId -> {
            RentableItem rentableItem = rentableItemRepository.findByID(rentableItemId)
                    .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
            if (!rentableItem.isAvailable()) {
                throw new RentableItemNotAvailableException("RentableItem is rented");
            }
            rentableItem.setAvailable(false);
            rentableItems.add(rentableItem);
        });

        int clientRents = rentRepository.getNumberOfClientRentedItems(client);

        Rent rent = new Rent();
        rent.setClient(client);
        rent.setEnded(false);
        rent.setRentableItems(rentableItems);
        rent.setBeginTime(LocalDateTime.now());
        rent.setEndTime(LocalDateTime.now().plusDays(client.getClientType().getMaxDays()));

        if (client.getClientType().getMaxItems() > clientRents) {
            return rentRepository.add(rent);
        } else {
            throw new MaxItemLimitExceededException("Client has reached max items");
        }
    }

    public boolean removeRent(Rent rent) {
        return rentRepository.remove(rent);
    }

    public Rent endRent(long id) {
        Rent rent = rentRepository.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Rent not found"));

        if (rent.isEnded()) {
            throw new EndedRentException("Rent is already ended");
        }

        Client client = rent.getClient();
        rent.setEndTime(LocalDateTime.now());
        rent.setEnded(true);

        rent.getRentableItems().forEach(rentableItem -> {
            rentableItem.setAvailable(true);
            rentableItemRepository.update(rentableItem);
        });

        if (rent.getEndTime()
                .isAfter(rent.getBeginTime().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEndTime().getDayOfYear() -
                    rent.getBeginTime().plusDays(client.getClientType().getMaxDays())
                            .getDayOfYear();
            rent.setRentCost(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        return rentRepository.update(rent);
    }

    public Rent findRentById(Long id) {
        return rentRepository.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Rent not found"));
    }

    public List<Rent> findAll() {
        return rentRepository.getItems();
    }
}
