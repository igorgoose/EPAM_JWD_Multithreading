package epam.schepov.multithreading.exception.matrix;

public class InvalidSquareMatrixSize extends Exception {

  public InvalidSquareMatrixSize() {
  }

  public InvalidSquareMatrixSize(String message) {
    super(message);
  }

  public InvalidSquareMatrixSize(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidSquareMatrixSize(Throwable cause) {
    super(cause);
  }
}
