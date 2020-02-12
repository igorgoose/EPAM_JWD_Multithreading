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
    private boolean[][] usedCells;
    private ReentrantLock matrixLock;
    private CyclicBarrier cyclicBarrier;
    private static final int DEFAULT_SLEEP_TIME = 10;

    private LockBarrierSquareMatrixShell() {
        int size = squareMatrix.getMatrixSize();
        lockMatrix = new ReentrantLock[size][size];
        usedCells = new boolean[size][size];
        cyclicBarrier = new CyclicBarrier(size);
        matrixLock = new ReentrantLock();
    }

    private static class InstanceHolder {
        private static LockBarrierSquareMatrixShell INSTANCE = new LockBarrierSquareMatrixShell();
    }

    public static LockBarrierSquareMatrixShell getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void setItem(int row, int column, int value) throws OutOfBoundsMatrixShellException, AccessNotGrantedException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            if (currentLock.tryLock() && !usedCells[row][column]) {
                Thread.sleep(DEFAULT_SLEEP_TIME);
                squareMatrix.setItem(row, column, value);
            } else {
                throw new AccessNotGrantedException("Access to the resource not granted!");
            }
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo
        } finally {
            currentLock.unlock();
        }
    }

    @Override
    public int getItem(int row, int column) throws OutOfBoundsMatrixShellException, AccessNotGrantedException {
        ReentrantLock currentLock = lockMatrix[row][column];
        try {
            if (currentLock.tryLock()) {
                return squareMatrix.getItem(row, column);
            }
        } catch (OutOfBoundsMatrixException e) {
            throw new OutOfBoundsMatrixShellException(e);
        } finally {
            currentLock.unlock();
        }
        throw new AccessNotGrantedException("Access to the resource not granted!");
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
        if(matrixLock.tryLock()) {
            super.setSquareMatrix(squareMatrix);
            int size = squareMatrix.getMatrixSize();
            lockMatrix = new ReentrantLock[size][size];
            cyclicBarrier = new CyclicBarrier(size);
        } else{
            throw new AccessNotGrantedException("Access to square matrix not granted!");
        }
    }

}
