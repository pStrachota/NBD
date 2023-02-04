package repository;

import static dataForTests.testData.rent;
import static dataForTests.testData.rent2;
import static dataForTests.testData.rent3;
import static org.assertj.core.api.Assertions.assertThat;

import model.rent.Rent;
import org.junit.jupiter.api.Test;
import repository.impl.RentRepository;
import repository.impl.RepoProducer;

class RentRepositoryTest {

    private static final RentRepository rentRepository = RepoProducer.getRentRepository();

    @Test
    void addRentPositiveTest() {
        assertThat(rentRepository.add(rent)).isEqualTo(true);
        Rent addedRent = rentRepository.findById(rent.getUuid()).get();
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
        assertThat(rentRepository.findById(rent2.getUuid()).get().isEnded()).isEqualTo(true);
    }

    @Test
    void removeRentTest() {
        rentRepository.add(rent3);
        rentRepository.remove(rent3);
        assertThat(rentRepository.findById(rent3.getUuid())).isNull();
    }
}