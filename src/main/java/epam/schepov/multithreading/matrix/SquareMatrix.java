package epam.schepov.multithreading.matrix;

import epam.schepov.multithreading.exception.matrix.InvalidSquareMatrixSize;
import epam.schepov.multithreading.exception.matrix.NullMatrixException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.validator.SquareMatrixValidator;


public class SquareMatrix {

    private int [][] matrix;

    private static final int DEFAULT_SIZE = 1;

    public SquareMatrix(){
      matrix = new int[DEFAULT_SIZE][DEFAULT_SIZE];
    }

    public SquareMatrix(int[][] matrix) throws NullMatrixException, InvalidSquareMatrixSize {
        SquareMatrixValidator.validateSquareMatrix(matrix);
        this.matrix = matrix;
    }

    public int getMatrixSize() {
        return matrix.length;
    }

    public int getItem(int row, int column) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row, matrix.length);
        SquareMatrixValidator.validateIndex(column, matrix.length);
        return matrix[row][column];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) throws NullMatrixException, InvalidSquareMatrixSize {
        SquareMatrixValidator.validateSquareMatrix(matrix);
        this.matrix = matrix;
    }

    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row, matrix.length);
        SquareMatrixValidator.validateIndex(column, matrix.length);
        matrix[row][column] = value;
    }

    public int getRowsNumber(){
        return matrix.length;
    }

    public int getColumnsNumber(){
        return matrix.length > 0 ? matrix[0].length : 0;
    }
}
