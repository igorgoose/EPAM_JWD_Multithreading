package epam.schepov.multithreading.shell.lock;

import epam.schepov.multithreading.exception.thread.AccessNotGrantedException;
import epam.schepov.multithreading.exception.shell.NullSquareMatrixPassed;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.shell.ConcurrentSquareMatrixShell;
import epam.schepov.multithreading.validator.SquareMatrixValidator;
import org.apache.log4j.Logger;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class LockBarrierSquareMatrixShell extends ConcurrentSquareMatrixShell {

    private static final Logger LOGGER = Logger.getLogger(LockBarrierSquareMatrixShell.class);

    private ReentrantLock[][] lockMatrix;
    private ReentrantLock matrixLock;

    private boolean[][] usedCells;
    private boolean isReplacingMatrix = false;
    private CyclicBarrier cyclicBarrier;

    private LockBarrierSquareMatrixShell() {
        int size = squareMatrix.getMatrixSize();
        lockMatrix = new ReentrantLock[size][size];
        usedCells = new boolean[size][size];
        cyclicBarrier = new CyclicBarrier(size);
        matrixLock = new ReentrantLock();
        initializeArrays();
    }

    private static class InstanceHolder {
        private static final LockBarrierSquareMatrixShell INSTANCE = new LockBarrierSquareMatrixShell();
    }

    private void initializeArrays() {
        LOGGER.debug(Thread.currentThread().getName() + ": Initializing arrays");
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
    public boolean setItem(int row, int column, int value) throws AccessNotGrantedException, OutOfBoundsMatrixException {
        validateRowAndColumn(row, column, getSquareMatrixSize());
        ReentrantLock currentLock = lockMatrix[row][column];
        boolean threadIsLockingMatrix = true;
        try {
            currentLock.lock();
            if (!(threadIsLockingMatrix = matrixLock.tryLock())) {
                if (isReplacingMatrix) {
                    throw new AccessNotGrantedException("Unable to get item while replacing matrix!");
                }
            }
            if (!usedCells[row][column]) {
                squareMatrix.setItem(row, column, value);
                usedCells[row][column] = true;
                LOGGER.debug(Thread.currentThread().getName() + ": Item is set: (" + row + ", " + column + ") " + value);
                return true;
            }
        } finally {
            currentLock.unlock();
            if (threadIsLockingMatrix) {
                matrixLock.unlock();
            }
        }
        LOGGER.debug(Thread.currentThread().getName() + ": Item is NOT set: (" + row + ", " + column + ") " + value);
        return false;
    }

    @Override
    public int getItem(int row, int column) throws AccessNotGrantedException, OutOfBoundsMatrixException {
        validateRowAndColumn(row, column, getSquareMatrixSize());
        ReentrantLock currentLock = lockMatrix[row][column];
        boolean threadIsLockingMatrix = true;
        try {
            currentLock.lock();
            if (!(threadIsLockingMatrix = matrixLock.tryLock())) {
                if (isReplacingMatrix) {
                    throw new AccessNotGrantedException("Unable to get item while replacing matrix!");
                }
            }
            return squareMatrix.getItem(row, column);
        } finally {
            currentLock.unlock();
            if (threadIsLockingMatrix) {
                matrixLock.unlock();
            }
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
            isReplacingMatrix = true;
            super.setSquareMatrix(squareMatrix);
            int size = squareMatrix.getMatrixSize();
            lockMatrix = new ReentrantLock[size][size];
            usedCells = new boolean[size][size];
            cyclicBarrier = new CyclicBarrier(size);
            initializeArrays();
            LOGGER.debug(Thread.currentThread().getName() + ": setting new matrix");
        } finally {
            isReplacingMatrix = false;
            matrixLock.unlock();
        }
    }

    public void reset(){
        try{
            matrixLock.lock();
            initializeArrays();
            LOGGER.debug(Thread.currentThread().getName() + ": resetting arrays");
        } finally {
            matrixLock.unlock();
        }
    }

    private void validateRowAndColumn(int row, int column, int size) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row, size);
        SquareMatrixValidator.validateIndex(column, size);
    }
}
