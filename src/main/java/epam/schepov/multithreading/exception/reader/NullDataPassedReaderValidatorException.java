package epam.schepov.multithreading.exception.reader;

public class NullDataPassedReaderValidatorException extends Exception {

  public NullDataPassedReaderValidatorException() {
  }

  public NullDataPassedReaderValidatorException(String message) {
    super(message);
  }

  public NullDataPassedReaderValidatorException(String message, Throwable cause) {
    super(message, cause);
  }

  public NullDataPassedReaderValidatorException(Throwable cause) {
    super(cause);
  }
}
