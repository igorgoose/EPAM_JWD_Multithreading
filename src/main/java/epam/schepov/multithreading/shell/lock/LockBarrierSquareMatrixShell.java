package epam.schepov.multithreading.shell.lock;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NullSquareMatrixPassed;
import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.shell.ConcurrentSquareMatrixShell;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class LockBarrierSquareMatrixShell extends ConcurrentSquareMatrixShell {

    private ReentrantLock[][] lockMatrix;
    private ReentrantLock matrixLock;
    private static boolean[][] usedCells;
    private CyclicBarrier cyclicBarrier;
    private static final int DEFAULT_SLEEP_TIME = 10;

    private LockBarrierSquareMatrixShell() {
        int size = squareMatrix.getMatrixSize();
        lockMatrix = new ReentrantLock[size][size];
        usedCells = new boolean[size][size];
        cyclicBarrier = new CyclicBarrier(size);
        matrixLock = new ReentrantLock();
        initializeArrays();
    }

    private static class InstanceHolder {
        private static LockBarrierSquareMatrixShell INSTANCE = new LockBarrierSquareMatrixShell();
    }

    private void initializeArrays() {
        int size = lockMatrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                lockMatrix[i][j] = new ReentrantLock();
                usedCells[i][j] = false;
            }
        }
    }

    public static LockBarrierSquareMatrixShell getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public boolean setItem(int row, int column, int value) throws OutOfBoundsMatrixShellException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            currentLock.lock();
            matrixLock.lock();
            if (usedCells[row][column]) {
                Thread.sleep(DEFAULT_SLEEP_TIME);
                squareMatrix.setItem(row, column, value);
                return true;
            }
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo
        } finally {
            currentLock.unlock();
            matrixLock.unlock();
        }
        return false;
    }

    @Override
    public int getItem(int row, int column) throws OutOfBoundsMatrixShellException{
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            currentLock.lock();
            matrixLock.lock();
            return squareMatrix.getItem(row, column);
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } finally {
            currentLock.unlock();
            matrixLock.unlock();
        }
    }

    @Override
    public int getSquareMatrixSize() {
        return squareMatrix.getMatrixSize();
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    @Override
    public void setSquareMatrix(SquareMatrix squareMatrix) throws NullSquareMatrixPassed, AccessNotGrantedException {
        try {
            matrixLock.lock();
            super.setSquareMatrix(squareMatrix);
            int size = squareMatrix.getMatrixSize();
            lockMatrix = new ReentrantLock[size][size];
            cyclicBarrier = new CyclicBarrier(size);
            initializeArrays();
        } finally {
            matrixLock.unlock();
        }
    }

}
