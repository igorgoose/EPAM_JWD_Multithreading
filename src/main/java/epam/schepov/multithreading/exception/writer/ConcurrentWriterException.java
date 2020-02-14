package epam.schepov.multithreading.exception.writer;

public class ConcurrentWriterException extends Exception {
    public ConcurrentWriterException() {
    }

    public ConcurrentWriterException(String message) {
        super(message);
    }

    public ConcurrentWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentWriterException(Throwable cause) {
        super(cause);
    }
}
