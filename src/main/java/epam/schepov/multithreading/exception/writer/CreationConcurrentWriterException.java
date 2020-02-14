package epam.schepov.multithreading.exception.writer;


public class CreationConcurrentWriterException extends Exception {
    public CreationConcurrentWriterException() {
    }

    public CreationConcurrentWriterException(String message) {
        super(message);
    }

    public CreationConcurrentWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationConcurrentWriterException(Throwable cause) {
        super(cause);
    }
}
