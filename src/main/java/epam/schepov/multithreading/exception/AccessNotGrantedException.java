package epam.schepov.multithreading.exception;

public class AccessNotGrantedException extends Exception {
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
