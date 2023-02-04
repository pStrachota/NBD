package exception;

public class MaxItemLimitExceededException extends RuntimeException {
    public MaxItemLimitExceededException(String message) {
        super(message);
    }
}
