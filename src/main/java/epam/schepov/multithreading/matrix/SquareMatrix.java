package epam.schepov.multithreading.matrix;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.validator.SquareMatrixValidator;


public class SquareMatrix {

    private int [][] matrix;

    private static final int DEFAULT_SIZE = 1;

    public SquareMatrix(){
      matrix = new int[DEFAULT_SIZE][DEFAULT_SIZE];
    }

    public SquareMatrix(int[][] matrix){
        this.matrix = matrix;
    }

    public int getMatrixSize() {
        return matrix.length;
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
