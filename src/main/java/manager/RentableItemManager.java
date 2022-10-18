package manager;

import model.RentableItem;
import repository.RentableItemRepository;

public class RentableItemManager {

    RentableItemRepository rentableItemRepository = new RentableItemRepository();

    public void registerRentableItem(RentableItem rentableItem) {
        rentableItemRepository.addRentableItem(rentableItem);
    }

    public void unregisterRentableItem(RentableItem rentableItem) {
        rentableItemRepository.removeRentableItem(rentableItem);
    }

    public String getReport() {
        return rentableItemRepository.getReport();
    }

    public RentableItem findById(Long id) {
        return rentableItemRepository.findByID(id);
    }


}
