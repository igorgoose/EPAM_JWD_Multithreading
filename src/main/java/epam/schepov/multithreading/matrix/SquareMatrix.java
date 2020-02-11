package epam.schepov.multithreading.matrix;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.validator.SquareMatrixValidator;


public enum SquareMatrix {
    INSTANCE;

    private static final int MATRIX_SIZE = 10;

    int [][] matrix;

    private SquareMatrix(){
        matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    }

    public static int getMatrixSize() {
        return MATRIX_SIZE;
    }

    public int getItem(int row, int column) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row);
        SquareMatrixValidator.validateIndex(column);
        return matrix[row][column];
    }

    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row);
        SquareMatrixValidator.validateIndex(column);
        matrix[row][column] = value;
    }

    public int getRowsNumber(){
        return matrix.length;
    }

    public int getColumnsNumber(){
        return matrix.length > 0 ? matrix[0].length : 0;
    }
}
