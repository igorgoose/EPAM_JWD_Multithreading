package epam.schepov.multithreading.reader.result;

import epam.schepov.multithreading.matrix.SquareMatrix;

public class SquareMatrixReaderResult {

  private int threadsNumber;
  private int iterationsNumber;
  private SquareMatrix matrix;

  public SquareMatrixReaderResult(int threadsNumber, int iterationsNumber, SquareMatrix matrix){
    this.threadsNumber = threadsNumber;
    this.matrix = matrix;
    this.iterationsNumber = iterationsNumber;
  }

  public int getThreadsNumber() {
    return threadsNumber;
  }

  public SquareMatrix getMatrix() {
    return matrix;
  }

  public void setMatrix(SquareMatrix matrix) {
    this.matrix = matrix;
  }

  public void setThreadsNumber(int threadsNumber) {
    this.threadsNumber = threadsNumber;
  }

  public int getIterationsNumber() {
    return iterationsNumber;
  }

  public void setIterationsNumber(int iterationsNumber) {
    this.iterationsNumber = iterationsNumber;
  }
}
