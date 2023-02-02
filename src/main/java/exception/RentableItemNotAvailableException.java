package exception;

public class RentableItemNotAvailableException extends RuntimeException {

    public RentableItemNotAvailableException(String message) {
        super(message);
    }
}
