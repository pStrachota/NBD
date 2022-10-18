package dataForTests;

import java.time.LocalDateTime;
import java.util.Arrays;
import model.Address;
import model.Book;
import model.Client;
import model.ClientType;
import model.Rent;
import model.RentableItem;

public class testData {

    public static Address address = Address.builder()
            .street("Garwolinska")
            .city("Garwolin")
            .number("42")
            .build();

    public static Address address2 = Address.builder()
            .street("Garwolinska")
            .city("Garwolin")
            .number("42")
            .build();

    public static Address address3 = Address.builder()
            .street("Garwolinska")
            .city("Garwolin")
            .number("42")
            .build();


    public static Client client = Client.builder()
            .name("Adam")
            .surname("Kowalski")
            .address(address)
            .clientType(ClientType.STUDENT)
            .build();

    public static Client client2 = Client.builder()
            .name("Andrzej")
            .surname("Kowalski")
            .address(address2)
            .clientType(ClientType.OUTSIDER)
            .build();

    public static Client client3 = Client.builder()
            .name("Tomasz")
            .surname("Kowalski")
            .address(address3)
            .clientType(ClientType.UNIVERSITY_EMPLOYEE)
            .build();

    public static RentableItem book = new Book(
            "1234567890",
            "J.K. Rowling",
            "Harry Potter",
            "PWN");

    public static RentableItem book2 = new Book(
            "1234567891",
            "J.K. Rowling",
            "Harry Potter II",
            "PWN");

    public static RentableItem book3 = new Book(
            "1234567892",
            "J.K. Rowling",
            "Harry Potter III",
            "PWN");

    public static Rent rent = Rent.builder()
            .beginTime(LocalDateTime.now())
            .rentableItem(Arrays.asList((book)))
            .build();

    public static Rent rent2 = Rent.builder()
            .beginTime(LocalDateTime.now())
            .rentableItem(Arrays.asList((book2)))
            .build();

    public static Rent rent3 = Rent.builder()
            .beginTime(LocalDateTime.now())
            .rentableItem(Arrays.asList((book3)))
            .build();

}
