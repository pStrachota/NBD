package manager;

import exception.EndedRentException;
import exception.ItemNotFoundException;
import exception.MaxItemLimitExceededException;
import exception.RentableItemNotAvailableException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.rent.Rent;
import model.resource.RentableItem;
import model.user.Client;
import repository.impl.ClientRepository;
import repository.impl.RentRepository;
import repository.impl.RentableItemRepository;

@AllArgsConstructor
public class RentManager {

    RentRepository rentRepository;
    ClientRepository clientRepository;
    RentableItemRepository rentableItemRepository;

    public boolean addRent(UUID clientId, UUID rentableItemId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ItemNotFoundException("Client not found"));

        RentableItem rentableItem = rentableItemRepository.findById(rentableItemId).orElseThrow(
                () -> new ItemNotFoundException("RentableItem not found"));
        if (!rentableItem.isAvailable()) {
            throw new RentableItemNotAvailableException("RentableItem is rented");
        }
        rentableItem.setAvailable(false);

        int clientRents = rentRepository.findByClientId(clientId).size();

        Rent rent = new Rent();
        rent.setClient(client);
        rent.setEnded(false);
        rent.setRentableItem(rentableItem);
        rent.setBeginTime(LocalDate.now());
        rent.setEndTime(LocalDate.now().plusDays(client.getClientType().getMaxDays()));

        if (client.getClientType().getMaxItems() > clientRents) {
            return rentRepository.add(rent);
        } else {
            throw new MaxItemLimitExceededException("Client has reached max items");
        }
    }

    public void removeRent(Rent rent) {
        rentRepository.remove(rent);
    }

    public void endRent(UUID id) {
        Rent rent = rentRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Rent not found"));

        if (rent.isEnded()) {
            throw new EndedRentException("Rent is already ended");
        }

        Client client = rent.getClient();
        rent.setEndTime(LocalDate.now());
        rent.setEnded(true);

        rent.getRentableItem().setAvailable(true);
        rentableItemRepository.update(rent.getRentableItem());

        if (rent.getEndTime()
                .isAfter(rent.getBeginTime().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEndTime().getDayOfYear() -
                    rent.getBeginTime().plusDays(client.getClientType().getMaxDays())
                            .getDayOfYear();
            rent.setRentCost(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        rentRepository.update(rent);
    }

    public Optional<Rent> findRentById(UUID id) {
        return rentRepository.findById(id);
    }
}
