package epam.schepov.multithreading.exception.thread;

public class AccessNotGrantedException extends RuntimeException {
    public AccessNotGrantedException() {
    }

    public AccessNotGrantedException(String message) {
        super(message);
    }

    public AccessNotGrantedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessNotGrantedException(Throwable cause) {
        super(cause);
    }
}
