package epam.schepov.multithreading.shell.lock;

import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.shell.ConcurrentMatrixShell;

import java.util.concurrent.locks.ReentrantLock;

public class LockOnlyMatrixShell extends ConcurrentMatrixShell {

    ReentrantLock[][] lockMatrix;


    public LockOnlyMatrixShell(SquareMatrix squareMatrix) {
        super(squareMatrix);
        lockMatrix = new ReentrantLock[squareMatrix.getRowsNumber()][squareMatrix.getColumnsNumber()];
    }

    @Override
    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixShellException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            currentLock.lock();
            squareMatrix.setItem(row, column, value);
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
            return squareMatrix.getItem(row, column);
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } finally {
            currentLock.unlock();
        }
    }

    @Override
    public int getRowsNumber() {
        return squareMatrix.getRowsNumber();
    }

    @Override
    public int getColumnsNumber() {
        return squareMatrix.getColumnsNumber();
    }
}
