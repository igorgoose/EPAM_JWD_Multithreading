package epam.schepov.multithreading.validator;

import epam.schepov.multithreading.exception.matrix.InvalidSquareMatrixSize;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.exception.matrix.NullMatrixException;

public class SquareMatrixValidator {

    private SquareMatrixValidator(){

    }

    public static void validateIndex(int index, int size) throws OutOfBoundsMatrixException {
        if(index < 0 || index >= size){
            throw new OutOfBoundsMatrixException("Index is out of bounds(idx: " +
                    index + ", size: " + size + ")!");
        }
    }

    public static void validateSquareMatrix(int[][] matrix)
        throws NullMatrixException, InvalidSquareMatrixSize {
        if(matrix == null){
            throw new NullMatrixException("Null matrix passed!");
        }
        int rowsNumber = matrix.length;
        if(rowsNumber < 1){
            throw new InvalidSquareMatrixSize("Matrix has less than 1 row!");
        }
        for (int[] row: matrix) {
            if(row.length != rowsNumber){
                throw new InvalidSquareMatrixSize("The matrix is not square!");
            }
        }
    }
}
