package epam.schepov.multithreading.exception.reader;

public class NullFileNameReaderException extends Exception {

  public NullFileNameReaderException() {
  }

  public NullFileNameReaderException(String message) {
    super(message);
  }

  public NullFileNameReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public NullFileNameReaderException(Throwable cause) {
    super(cause);
  }
}
