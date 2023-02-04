package repository;

import static dataForTests.testData.rent;
import static dataForTests.testData.rent2;
import static dataForTests.testData.rent3;
import static org.assertj.core.api.Assertions.assertThat;

import model.Rent;
import org.junit.jupiter.api.Test;

class RentRepositoryTest {

    private static final RentRepository rentRepository = new RentRepository();

    @Test
    void addRentPositiveTest() {
        Rent addedRent = rentRepository.add(rent);
        assertThat(addedRent).isEqualTo(rent);
    }

    @Test
    void addRentNegativeTest() {
        rentRepository.add(rent);
        assertThat(rentRepository.add(rent)).isNull();
    }

    @Test
    void updateRentTest() {
        rentRepository.add(rent2);
        rent2.setEnded(true);
        rentRepository.update(rent2);
        assertThat(rentRepository.findByID(rent2.getUuid()).get().isEnded()).isEqualTo(true);
    }

    @Test
    void removeRentTest() {
        rentRepository.add(rent3);
        rentRepository.remove(rent3);
        assertThat(rentRepository.findByID(rent3.getUuid())).isEmpty();
    }
}