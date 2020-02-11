package epam.schepov.multithreading.main;

import epam.schepov.multithreading.exception.reader.ReaderException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.reader.SquareMatrixReader;
import epam.schepov.multithreading.reader.result.SquareMatrixReaderResult;

public class Main {

  public static void main(String[] args) {
    SquareMatrixReader reader = new SquareMatrixReader();
    try {
      SquareMatrixReaderResult result = reader.read();
      int threadsNumber = result.getThreadsNumber();

    } catch (ReaderException e) {
      e.printStackTrace();
    }
  }
}
