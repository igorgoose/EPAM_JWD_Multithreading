package epam.schepov.multithreading.validator;

import epam.schepov.multithreading.exception.reader.InvalidDataItemReaderException;
import epam.schepov.multithreading.exception.reader.InvalidDataItemsNumberException;
import epam.schepov.multithreading.exception.reader.NullDataPassedReaderValidatorException;

public class ReaderValidator {

    public static final int META_DATA_ITEMS_LENGTH = 2;
    private static final int MIN_MATRIX_SIZE = 1;
    private static final int MIN_THREADS_NUMBER = 1;

    private ReaderValidator() {

    }

    public static void validateThreadMatrixMetaData(String[] data)
            throws NullDataPassedReaderValidatorException, InvalidDataItemsNumberException, InvalidDataItemReaderException {
        validateData(data, META_DATA_ITEMS_LENGTH);
    }

    public static void validateMatrixRowData(String[] data, int matrixSize)
            throws NullDataPassedReaderValidatorException, InvalidDataItemsNumberException, InvalidDataItemReaderException {
        validateData(data, matrixSize);
    }

    private static void validateData(String[] data, int numberOfItems)
            throws NullDataPassedReaderValidatorException, InvalidDataItemsNumberException, InvalidDataItemReaderException {
        if (data == null) {
            throw new NullDataPassedReaderValidatorException("Null data passed!");
        }
        int length = data.length;
        if (length < numberOfItems) {
            throw new InvalidDataItemsNumberException("Less data items in line than expected(expected: " +
                    META_DATA_ITEMS_LENGTH + "; actual: " + length + ")");
        }
        for (int i = 0; i < numberOfItems; i++) {
            try {
                Integer.parseInt(data[i]);
            } catch (NumberFormatException e) {
                throw new InvalidDataItemReaderException(e);
            }
        }
    }

    public static void validateMatrixSizeAndThreadsNumber(int matrixSize, int threadsNumber) throws InvalidDataItemReaderException {
        if (matrixSize < MIN_MATRIX_SIZE || threadsNumber < MIN_THREADS_NUMBER) {
            throw new InvalidDataItemReaderException("Matrix size or threads number less than 1(MS: "
                    + matrixSize + ", TN: " + threadsNumber + ")");
        }
    }
}
