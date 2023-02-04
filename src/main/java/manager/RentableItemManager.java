package manager;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.resource.RentableItem;
import repository.impl.RentableItemRepository;

@AllArgsConstructor
public class RentableItemManager {

    RentableItemRepository rentableItemRepository;

    public void addRentableItem(RentableItem rentableItem) {
        rentableItemRepository.add(rentableItem);
    }

    public Optional<RentableItem> findRentableItemById(UUID id) {
        return rentableItemRepository.findById(id);
    }

    public void updateRentableItem(RentableItem rentableItem) {
        rentableItemRepository.update(rentableItem);
    }
}
