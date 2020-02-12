package epam.schepov.multithreading.shell;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NullSquareMatrixPassed;
import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;

public abstract class ConcurrentSquareMatrixShell {
    protected SquareMatrix squareMatrix;

    public ConcurrentSquareMatrixShell() {
        squareMatrix = new SquareMatrix();
    }

    public ConcurrentSquareMatrixShell(SquareMatrix squareMatrix) {
        this.squareMatrix = squareMatrix;
    }

    public void setSquareMatrix(SquareMatrix squareMatrix) throws NullSquareMatrixPassed, AccessNotGrantedException {
        if (squareMatrix == null) {
            throw new NullSquareMatrixPassed("Null square matrix passed!");
        }
    }

    public SquareMatrix getSquareMatrix() {
        return squareMatrix;
    }

    public abstract void setItem(int row, int column, int value) throws OutOfBoundsMatrixException, OutOfBoundsMatrixShellException, AccessNotGrantedException;

    public abstract int getItem(int row, int column) throws OutOfBoundsMatrixShellException, AccessNotGrantedException;

    public abstract int getSquareMatrixSize();
}