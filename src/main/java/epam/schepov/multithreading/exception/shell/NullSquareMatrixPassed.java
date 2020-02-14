package epam.schepov.multithreading.exception.shell;

public class NullSquareMatrixPassed extends RuntimeException {

  public NullSquareMatrixPassed() {
  }

  public NullSquareMatrixPassed(String message) {
    super(message);
  }

  public NullSquareMatrixPassed(String message, Throwable cause) {
    super(message, cause);
  }

  public NullSquareMatrixPassed(Throwable cause) {
    super(cause);
  }
}
