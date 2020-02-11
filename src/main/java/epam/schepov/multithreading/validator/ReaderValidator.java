package epam.schepov.multithreading.validator;

import epam.schepov.multithreading.exception.reader.InvalidDataItemReaderException;
import epam.schepov.multithreading.exception.reader.InvalidDataItemsNumberException;
import epam.schepov.multithreading.exception.reader.NullDataPassedReaderValidatorException;

public class ReaderValidator {

  public static final int META_DATA_ITEMS_LENGTH = 2;

  private ReaderValidator(){

  }

  public static void validateThreadMatrixMetaData(String[] data)
      throws NullDataPassedReaderValidatorException, InvalidDataItemsNumberException, InvalidDataItemReaderException {
      if(data == null){
        throw new NullDataPassedReaderValidatorException("Null data passed!");
      }
      int length = data.length;
      if(length < META_DATA_ITEMS_LENGTH){
        throw new InvalidDataItemsNumberException("Less data items in line than expected(expected: " +
            META_DATA_ITEMS_LENGTH + "; actual: " + length + ")");
      }
    for (int i = 0; i < META_DATA_ITEMS_LENGTH; i++) {
      try {
        Integer.parseInt(data[i]);
      } catch (NumberFormatException e){
        throw new InvalidDataItemReaderException(e);
      }
    }
  }

}
