package kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import model.Rent;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.MemberDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.StringDeserializer;
import repository.RentRepository;

public class RentConsumer {

    private final String consumerGroupName = "rent-consumer";
    private RentRepository rentRepository = new RentRepository();
    private Properties adminProperties;

    public RentConsumer() {
        this.adminProperties = new Properties();
        this.adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka1:9292,kafka3:9392");
    }

    public void consume() {
        try (Admin admin = Admin.create(this.adminProperties)) {
            admin.describeConsumerGroups(List.of(consumerGroupName));
        }

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupName);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka2:9292,kafka3:9392");

        try (KafkaConsumer consumer = new KafkaConsumer(consumerProperties)) {
            consumer.subscribe(List.of("reservations"));

            for (ConsumerGroupDescription desc : getConsumerGroupInfo()) {
                System.out.println(desc);
                for (MemberDescription memberDescription : desc.members()) {
                    System.out.println(memberDescription);
                }
            }

            while (true) {
                ConsumerRecords<String, String> consumerRecords =
                        consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    String key = consumerRecord.key();
                    String value = consumerRecord.value();
                    Gson mapper = new GsonBuilder()
                            .create();
                    Rent rent = mapper.fromJson(value, Rent.class);
                    System.out.println(key + " " + rent);
                    rentRepository.add(rent);
                    consumer.commitSync();
                }
            }
        }
    }

    private List<ConsumerGroupDescription> getConsumerGroupInfo() {
        List<ConsumerGroupDescription> descriptions = new ArrayList<ConsumerGroupDescription>();
        try (Admin admin = Admin.create(this.adminProperties)) {
            DescribeConsumerGroupsResult describeConsumerGroupsResult =
                    admin.describeConsumerGroups(List.of(consumerGroupName));
            Map<String, KafkaFuture<ConsumerGroupDescription>> descriptionGroups =
                    describeConsumerGroupsResult.describedGroups();
            for (Future<ConsumerGroupDescription> group : descriptionGroups.values()) {
                ConsumerGroupDescription consumerGroupDescription = null;
                try {
                    consumerGroupDescription = group.get();
                    descriptions.add(consumerGroupDescription);
                } catch (InterruptedException | ExecutionException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
        return descriptions;
    }
}
