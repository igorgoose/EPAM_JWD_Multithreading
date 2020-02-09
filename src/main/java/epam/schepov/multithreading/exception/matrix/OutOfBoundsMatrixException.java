package epam.schepov.multithreading.exception.matrix;

public class OutOfBoundsMatrixException extends Exception {
    public OutOfBoundsMatrixException() {
    }

    public OutOfBoundsMatrixException(String message) {
        super(message);
    }

    public OutOfBoundsMatrixException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfBoundsMatrixException(Throwable cause) {
        super(cause);
    }
}
