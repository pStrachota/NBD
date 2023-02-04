package repository;

import static dataForTests.testData.book;
import static dataForTests.testData.book2;
import static dataForTests.testData.book3;
import static org.assertj.core.api.Assertions.assertThat;

import model.resource.Book;
import org.junit.jupiter.api.Test;
import repository.impl.RentableItemRepository;
import repository.impl.RepoProducer;

class RentableItemRepositoryTest {

    private RentableItemRepository rentableItemRepository =
            RepoProducer.getRentableItemRepository();

    @Test
    void addRentableItemPositiveTest() {
        assertThat(rentableItemRepository.add(book)).isEqualTo(true);
        Book addedBook = (Book) rentableItemRepository.findById(book.getUuid()).get();
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
        assertThat(rentableItemRepository.findById(book2.getUuid()).get().getTitle())
                .isEqualTo("newTitle");
    }

    @Test
    void removeRentableItemTest() {
        rentableItemRepository.add(book3);
        rentableItemRepository.remove(book3);
        assertThat(rentableItemRepository.findById(book3.getUuid())).isNull();
    }

}