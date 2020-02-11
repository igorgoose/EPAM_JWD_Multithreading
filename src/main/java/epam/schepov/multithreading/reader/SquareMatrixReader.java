package epam.schepov.multithreading.reader;

import epam.schepov.multithreading.exception.reader.InvalidDataItemReaderException;
import epam.schepov.multithreading.exception.reader.InvalidDataItemsNumberException;
import epam.schepov.multithreading.exception.reader.NullDataPassedReaderValidatorException;
import epam.schepov.multithreading.exception.reader.NullFileNameReaderException;
import epam.schepov.multithreading.reader.result.SquareMatrixReaderResult;
import epam.schepov.multithreading.validator.ReaderValidator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SquareMatrixReader {

  private static final String DEFAULT_FILE_NAME = "matrix_data.txt";
  private static final String DELIMITER = " ";

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

  public SquareMatrixReaderResult read(){
    try(BufferedReader reader = new BufferedReader(new FileReader(file))){
      String[] data = reader.readLine().split(DELIMITER);
      ReaderValidator.validateThreadMatrixMetaData(data);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullDataPassedReaderValidatorException e) {
      e.printStackTrace();
    } catch (InvalidDataItemReaderException e) {
      e.printStackTrace();
    } catch (InvalidDataItemsNumberException e) {
      e.printStackTrace();
    }
    return null;
  }

}
