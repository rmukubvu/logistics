package za.charurama.logistics.exceptions;

public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
