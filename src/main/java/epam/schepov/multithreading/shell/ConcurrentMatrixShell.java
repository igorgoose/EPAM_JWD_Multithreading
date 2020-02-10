package epam.schepov.multithreading.shell;

import epam.schepov.multithreading.matrix.Matrix;

public abstract class ConcurrentMatrixShell {
    protected Matrix matrix;

    public abstract void setItem(int row, int column, int value);
    public abstract int getItem(int row, int column);
}
