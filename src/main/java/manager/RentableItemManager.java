package manager;

import exception.ItemNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.resource.Article;
import model.resource.Book;
import model.resource.RentableItem;
import repository.RentableItemRepository;

@AllArgsConstructor
public class RentableItemManager {

    RentableItemRepository rentableItemRepository;

    public RentableItem addBook(String title, String author, String serialNumber,
                                String publishingHouse) {
        Book book = new Book(UUID.randomUUID(), true, title, author, serialNumber, publishingHouse);
        return rentableItemRepository.add(book);
    }

    public RentableItem addArticle(String title, String author, String serialNumber,
                                   String parentOrganisation) {
        Article article = new Article(UUID.randomUUID(), true, title, author, serialNumber,
                parentOrganisation);
        return rentableItemRepository.add(article);
    }

    public RentableItem findRentableItemById(UUID id) {
        return rentableItemRepository.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
    }

    public boolean updateRentableItem(RentableItem rentableItem) {
        return rentableItemRepository.update(rentableItem);
    }

    public void removeRentableItem(RentableItem rentableItem) {
        rentableItemRepository.remove(rentableItem);
    }

    public List<RentableItem> findAll() {
        return rentableItemRepository.getItems();
    }

}
