package dataForTests;

import java.time.LocalDate;
import model.rent.Rent;
import model.resource.Book;
import model.resource.RentableItem;
import model.user.Address;
import model.user.Client;
import model.user.ClientType;

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
            .firstName("Adam")
            .lastName("Kowalski")
            .address(address)
            .clientType(ClientType.STUDENT)
            .build();

    public static Client client2 = Client.builder()
            .firstName("Andrzej")
            .lastName("Kowalski")
            .address(address2)
            .clientType(ClientType.OUTSIDER)
            .build();

    public static Client client3 = Client.builder()
            .firstName("Tomasz")
            .lastName("Kowalski")
            .address(address3)
            .clientType(ClientType.UNIVERSITY_EMPLOYEE)
            .build();

    public static RentableItem book = Book.builder()
            .title("Pan Tadeusz")
            .author("Adam Mickiewicz")
            .publishingHouse("PWN")
            .serialNumber("123456789")
            .build();

    public static RentableItem book2 = Book.builder()
            .title("Harry Potter")
            .author("J.K. Rowling")
            .publishingHouse("PWN")
            .serialNumber("987654321")
            .build();

    public static RentableItem book3 = Book.builder()
            .title("W pustyni i w puszczy")
            .author("Henryk Sienkiewicz")
            .publishingHouse("PWN")
            .serialNumber("123456788")
            .build();

    public static Rent rent = Rent.builder()
            .beginTime(LocalDate.now())
            .rentableItem(book)
            .build();

    public static Rent rent2 = Rent.builder()
            .beginTime(LocalDate.now())
            .rentableItem(book2)
            .build();

    public static Rent rent3 = Rent.builder()
            .beginTime(LocalDate.now())
            .rentableItem(book3)
            .build();

}
