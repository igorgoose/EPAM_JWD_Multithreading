package epam.schepov.multithreading.matrix;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.validator.MatrixBoundsValidator;

public enum Matrix {
    INSTANCE;

    private static final int MATRIX_SIZE = 10;

    int [][] matrix;

    private Matrix(){
        matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    }

    public static int getMatrixSize() {
        return MATRIX_SIZE;
    }

    public int getItem(int row, int column) throws OutOfBoundsMatrixException {
        MatrixBoundsValidator.validateIndex(row);
        MatrixBoundsValidator.validateIndex(column);
        return matrix[row][column];
    }

    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixException {
        MatrixBoundsValidator.validateIndex(row);
        MatrixBoundsValidator.validateIndex(column);
        matrix[row][column] = value;
    }


}
