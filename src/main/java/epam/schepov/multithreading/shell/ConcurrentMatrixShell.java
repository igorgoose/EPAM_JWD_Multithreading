package epam.schepov.multithreading.shell;

import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;

public abstract class ConcurrentMatrixShell {
    protected SquareMatrix squareMatrix;

    public ConcurrentMatrixShell(SquareMatrix squareMatrix){
            this.squareMatrix = squareMatrix;
    }

    public abstract void setItem(int row, int column, int value) throws OutOfBoundsMatrixException, OutOfBoundsMatrixShellException;
    public abstract int getItem(int row, int column) throws OutOfBoundsMatrixShellException;
    public abstract int getRowsNumber();
    public abstract int getColumnsNumber();
}
