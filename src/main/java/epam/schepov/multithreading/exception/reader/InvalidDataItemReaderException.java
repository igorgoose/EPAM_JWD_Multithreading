package epam.schepov.multithreading.exception.reader;

public class InvalidDataItemReaderException extends Exception {

  public InvalidDataItemReaderException() {
  }

  public InvalidDataItemReaderException(String message) {
    super(message);
  }

  public InvalidDataItemReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataItemReaderException(Throwable cause) {
    super(cause);
  }
}
