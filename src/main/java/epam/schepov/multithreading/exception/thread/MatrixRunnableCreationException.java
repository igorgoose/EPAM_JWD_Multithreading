package epam.schepov.multithreading.exception.thread;

public class MatrixRunnableCreationException extends Exception {
    public MatrixRunnableCreationException() {
    }

    public MatrixRunnableCreationException(String message) {
        super(message);
    }

    public MatrixRunnableCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixRunnableCreationException(Throwable cause) {
        super(cause);
    }
}
