package kafka;

import org.junit.jupiter.api.Test;

public class KafkaConsumerTest {

    @Test
    public void rentConsumer1Test() {
        RentConsumer rentConsumer = new RentConsumer();
        rentConsumer.consume();
    }

    @Test
    public void rentConsumer2Test() {
        RentConsumer rentConsumer = new RentConsumer();
        rentConsumer.consume();
    }
}
