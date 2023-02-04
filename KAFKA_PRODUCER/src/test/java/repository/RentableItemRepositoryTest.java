package repository;

import static dataForTests.testData.book;
import static dataForTests.testData.book2;
import static dataForTests.testData.book3;
import static org.assertj.core.api.Assertions.assertThat;

import model.resource.Book;
import org.junit.jupiter.api.Test;

class RentableItemRepositoryTest {

    private RentableItemRepository rentableItemRepository = new RentableItemRepository();

    @Test
    void addRentableItemPositiveTest() {
        Book addedBook = (Book) rentableItemRepository.add(book);
        assertThat(addedBook).isEqualTo(book);
    }

    @Test
    void addRentableItemNegativeTest() {
        rentableItemRepository.add(book);
        assertThat(rentableItemRepository.add(book)).isNull();
    }

    @Test
    void updateRentableItemTest() {
        rentableItemRepository.add(book2);
        book2.setTitle("newTitle");
        rentableItemRepository.update(book2);
        assertThat(rentableItemRepository.findByID(book2.getUuid()).get().getTitle())
                .isEqualTo("newTitle");
    }

    @Test
    void removeRentableItemTest() {
        rentableItemRepository.add(book3);
        rentableItemRepository.remove(book3);
        assertThat(rentableItemRepository.findByID(book3.getUuid())).isEmpty();
    }

}