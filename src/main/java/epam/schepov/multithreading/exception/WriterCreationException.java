package epam.schepov.multithreading.exception;

public class WriterCreationException extends Exception {
    public WriterCreationException() {
    }

    public WriterCreationException(String message) {
        super(message);
    }

    public WriterCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterCreationException(Throwable cause) {
        super(cause);
    }
}
