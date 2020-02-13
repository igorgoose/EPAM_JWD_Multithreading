package epam.schepov.multithreading.reader;

import epam.schepov.multithreading.exception.reader.InvalidDataItemReaderException;
import epam.schepov.multithreading.exception.reader.InvalidDataItemsNumberException;
import epam.schepov.multithreading.exception.reader.NullDataPassedReaderValidatorException;
import epam.schepov.multithreading.exception.reader.NullFileNameReaderException;
import epam.schepov.multithreading.exception.reader.ReaderException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.reader.result.SquareMatrixReaderResult;
import epam.schepov.multithreading.validator.ReaderValidator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SquareMatrixReader {

  private static final String DEFAULT_FILE_NAME = "matrix_data.txt";
  private static final String DELIMITER = " ";
  private static final int MATRIX_SIZE_INDEX = 0;
  private static final int ITERATIONS_NUMBER_INDEX = 1;


  private File file;

  public SquareMatrixReader(){
    file = new File(getClass().getClassLoader().getResource(DEFAULT_FILE_NAME).getFile());
  }

  public SquareMatrixReader(String fileName) throws NullFileNameReaderException {
    if (fileName == null) {
      throw new NullFileNameReaderException("Null file name passed!");
    }
      file = new File(getClass().getClassLoader().getResource(fileName).getFile());
  }

  public SquareMatrixReaderResult read() throws ReaderException {
    try(BufferedReader reader = new BufferedReader(new FileReader(file))){
      String data = reader.readLine();
      if(data == null){
        throw new ReaderException("File is missing data!");
      }
      String[] parameters = data.split(DELIMITER);
      ReaderValidator.validateThreadMatrixMetaData(parameters);
      int matrixSize = Integer.parseInt(parameters[MATRIX_SIZE_INDEX]);
      int iterationsNumber = Integer.parseInt(parameters[ITERATIONS_NUMBER_INDEX]);
      ReaderValidator.validateMatrixSizeAndThreadsNumber(matrixSize, iterationsNumber);
      int[][] matrix = new int[matrixSize][matrixSize];
      for (int i = 0; i < matrixSize; i++) {
        data = reader.readLine();
        if(data != null){
          parameters = data.split(DELIMITER);
          ReaderValidator.validateMatrixRowData(parameters, matrixSize);
          for (int j = 0; j < matrixSize; j++) {
            matrix[i][j] = Integer.parseInt(parameters[j]);
          }
        }
      }
      return new SquareMatrixReaderResult(matrixSize, iterationsNumber, new SquareMatrix(matrix));
    } catch (IOException | NullDataPassedReaderValidatorException | InvalidDataItemReaderException
        | InvalidDataItemsNumberException e) {
      throw new ReaderException(e);
    }
  }

}
