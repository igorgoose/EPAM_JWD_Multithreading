package epam.schepov.multithreading.exception;

public class NullFilePassedWriterException extends Exception {
    public NullFilePassedWriterException() {
    }

    public NullFilePassedWriterException(String message) {
        super(message);
    }

    public NullFilePassedWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullFilePassedWriterException(Throwable cause) {
        super(cause);
    }
}
