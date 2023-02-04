package kafka;

import static dataForTests.testData.rent;

import model.Rent;
import org.junit.jupiter.api.Test;
import repository.RentRepository;

public class KafkaProducerTest {

    private static final RentRepository rentRepository = new RentRepository();

    @Test
    public void rentProducerTest() {
        Rent addedRent = rentRepository.add(rent);
    }
}
