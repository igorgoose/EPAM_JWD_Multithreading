package epam.schepov.multithreading.exception.shell;

public class OutOfBoundsMatrixShellException extends Exception {
    public OutOfBoundsMatrixShellException() {
    }

    public OutOfBoundsMatrixShellException(String message) {
        super(message);
    }

    public OutOfBoundsMatrixShellException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfBoundsMatrixShellException(Throwable cause) {
        super(cause);
    }
}
