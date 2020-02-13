package epam.schepov.multithreading.shell.lock;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NullSquareMatrixPassed;
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
        private static final LockBarrierSquareMatrixShell INSTANCE = new LockBarrierSquareMatrixShell();
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
    public boolean setItem(int row, int column, int value) throws AccessNotGrantedException, OutOfBoundsMatrixException {
        validateRowAndColumn(row, column, getSquareMatrixSize());
        ReentrantLock currentLock = lockMatrix[row][column];
        boolean matrixWasLocked = true;
        try {
            currentLock.lock();
            lockMatrixIfNotLocked();
            matrixWasLocked = false;
            if (!usedCells[row][column]) {
                squareMatrix.setItem(row, column, value);
                return true;
            }
        } finally {
            currentLock.unlock();
            if(!matrixWasLocked){
                matrixLock.unlock();
            }
        }
        return false;
    }

    @Override
    public int getItem(int row, int column) throws AccessNotGrantedException, OutOfBoundsMatrixException {
        validateRowAndColumn(row, column, getSquareMatrixSize());
        ReentrantLock currentLock = lockMatrix[row][column];
        boolean matrixWasLocked = true;
        try {
            currentLock.lock();
            lockMatrixIfNotLocked();
            matrixWasLocked = false;
            return squareMatrix.getItem(row, column);
        } finally {
            currentLock.unlock();
            if(!matrixWasLocked){
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
        } finally {
            isReplacingMatrix = false;
            matrixLock.unlock();
        }
    }

    private void lockMatrixIfNotLocked() throws AccessNotGrantedException {
        if (!matrixLock.tryLock() && isReplacingMatrix) {
            throw new AccessNotGrantedException("Unable to get item while replacing matrix!");
        }
    }

    private void validateRowAndColumn(int row, int column, int size) throws OutOfBoundsMatrixException {
        SquareMatrixValidator.validateIndex(row, size);
        SquareMatrixValidator.validateIndex(column, size);
    }
}
