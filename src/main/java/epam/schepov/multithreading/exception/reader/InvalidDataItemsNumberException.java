package epam.schepov.multithreading.exception.reader;

public class InvalidDataItemsNumberException extends Exception {

  public InvalidDataItemsNumberException() {
  }

  public InvalidDataItemsNumberException(String message) {
    super(message);
  }

  public InvalidDataItemsNumberException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataItemsNumberException(Throwable cause) {
    super(cause);
  }
}
