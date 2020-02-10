package epam.schepov.multithreading.shell;

import epam.schepov.multithreading.exception.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.Matrix;

public abstract class ConcurrentMatrixShell {
    protected Matrix matrix;

    public ConcurrentMatrixShell(Matrix matrix){
            this.matrix = matrix;
    }

    public abstract void setItem(int row, int column, int value) throws OutOfBoundsMatrixException, OutOfBoundsMatrixShellException;
    public abstract int getItem(int row, int column) throws OutOfBoundsMatrixShellException;
}
