package epam.schepov.multithreading.shell.lock;

import epam.schepov.multithreading.exception.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.Matrix;
import epam.schepov.multithreading.shell.ConcurrentMatrixShell;

import java.util.concurrent.locks.ReentrantLock;

public class LockMatrixShell extends ConcurrentMatrixShell {

    ReentrantLock[][] lockMatrix;

    public LockMatrixShell(Matrix matrix) {
        super(matrix);
        lockMatrix = new ReentrantLock[matrix.getRowsNumber()][matrix.getColumnsNumber()];
    }

    @Override
    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixShellException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            currentLock.lock();
            matrix.setItem(row, column, value);
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } finally {
            currentLock.unlock();
        }
    }

    @Override
    public int getItem(int row, int column) throws OutOfBoundsMatrixShellException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            currentLock.lock();
            return matrix.getItem(row, column);
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } finally {
            currentLock.unlock();
        }
    }
}
