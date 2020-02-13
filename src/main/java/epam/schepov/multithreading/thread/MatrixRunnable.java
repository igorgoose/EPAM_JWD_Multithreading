package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NotInitializedException;
import epam.schepov.multithreading.exception.WriterCreationException;
import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;
import epam.schepov.multithreading.writer.ConcurrentWriter;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatrixRunnable.class);

    private ConcurrentWriter writer = ConcurrentWriter.getInstance();
    private LockBarrierSquareMatrixShell matrixShell;
    private static int id_count = 0;
    private int id;

    public MatrixRunnable() {
        id = ++id_count;
    }

    public void run() {
        LOGGER.debug(Thread.currentThread().getName() + ": Running");
        try {
            matrixShell = LockBarrierSquareMatrixShell.getInstance();
            CyclicBarrier barrier = matrixShell.getCyclicBarrier();
            Random random = new Random();

            int diagonalIndex;
            do {
                diagonalIndex = generateIndex(matrixShell.getSquareMatrixSize());
            } while (!matrixShell.setItem(diagonalIndex, diagonalIndex, id));
            LOGGER.debug(Thread.currentThread().getName() + ": diagonal item is set");

            int nonDiagonalIndex;
            boolean isSet;
            do {
                nonDiagonalIndex = generateNonDiagonalIndex(diagonalIndex, matrixShell.getSquareMatrixSize());
                if (isRowChosen()) {
                    isSet = matrixShell.setItem(nonDiagonalIndex, diagonalIndex, id);
                } else {
                    isSet = matrixShell.setItem(diagonalIndex, nonDiagonalIndex, id);
                }
            } while (!isSet);
            LOGGER.debug(Thread.currentThread().getName() + ": non diagonal item is set");

            barrier.await();

            LOGGER.debug(Thread.currentThread().getName() + ": counting sum");
            int sum = countSums(diagonalIndex);
            String info = "Thread: " + id + "; sum: " + sum + "\n";
            LOGGER.debug(Thread.currentThread().getName() + ": writing");
            writer.write(info);

        } catch (InterruptedException | OutOfBoundsMatrixException | AccessNotGrantedException
                | NotInitializedException | BrokenBarrierException e) {
            LOGGER.warn(Thread.currentThread().getName(), e);
            e.printStackTrace();
        } catch (WriterCreationException e) {
            LOGGER.error(Thread.currentThread().getName(), e);
            e.printStackTrace();
        }
    }

    private int generateNonDiagonalIndex(int diagonalIndex, int matrixSize) {
        Random random = new Random();
        int index = Math.abs(random.nextInt()) % matrixSize;
        while (index == diagonalIndex) {
            index = Math.abs(random.nextInt()) % matrixSize;
        }
        return index;
    }

    private int generateIndex(int matrixSize) {
        Random random = new Random();
        return Math.abs(random.nextInt()) % matrixSize;
    }

    private boolean isRowChosen() {
        return new Random().nextBoolean();
    }

    private int countSums(int diagonalIndex) throws NotInitializedException, AccessNotGrantedException, OutOfBoundsMatrixException {
        if (matrixShell == null) {
            throw new NotInitializedException();
        }
        int size = matrixShell.getSquareMatrixSize();
        int count = matrixShell.getItem(diagonalIndex, diagonalIndex);
        for (int i = 0; i < size && i != diagonalIndex; i++) {
            count += matrixShell.getItem(diagonalIndex, i);
            count += matrixShell.getItem(i, diagonalIndex);
        }
        return count;
    }
}
