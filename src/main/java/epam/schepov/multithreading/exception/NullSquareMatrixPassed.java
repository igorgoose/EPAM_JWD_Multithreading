package epam.schepov.multithreading.exception;

public class NullSquareMatrixPassed extends Exception {

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
