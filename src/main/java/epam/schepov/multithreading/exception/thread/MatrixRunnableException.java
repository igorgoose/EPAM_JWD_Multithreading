package epam.schepov.multithreading.exception.thread;

public class MatrixRunnableException extends Exception {
    public MatrixRunnableException() {
    }

    public MatrixRunnableException(String message) {
        super(message);
    }

    public MatrixRunnableException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixRunnableException(Throwable cause) {
        super(cause);
    }
}
