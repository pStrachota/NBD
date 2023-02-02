package manager;

import exception.ItemNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import model.resource.RentableItem;
import repository.RentableItemRepository;

@AllArgsConstructor
public class RentableItemManager {

    RentableItemRepository rentableItemRepository;

    public RentableItem addRentableItem(RentableItem rentableItem) {
        return rentableItemRepository.add(rentableItem);
    }

    public RentableItem findRentableItemById(Long id) {
        return rentableItemRepository.findByID(id).orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
    }

    public RentableItem updateRentableItem(RentableItem rentableItem) {
        return rentableItemRepository.update(rentableItem);
    }

    public List<RentableItem> findAll() {
        return rentableItemRepository.getItems();
    }

}
