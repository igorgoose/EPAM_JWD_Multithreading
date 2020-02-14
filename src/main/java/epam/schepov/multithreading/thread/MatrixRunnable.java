package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.exception.thread.AccessNotGrantedException;
import epam.schepov.multithreading.exception.thread.MatrixRunnableCreationException;
import epam.schepov.multithreading.exception.thread.MatrixRunnableException;
import epam.schepov.multithreading.exception.writer.ConcurrentWriterException;
import epam.schepov.multithreading.exception.writer.CreationConcurrentWriterException;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;
import epam.schepov.multithreading.writer.ConcurrentWriter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatrixRunnable.class);

    private ConcurrentWriter writer;
    private LockBarrierSquareMatrixShell matrixShell = LockBarrierSquareMatrixShell.getInstance();
    private static int id_count = 0;
    private int id;

    public MatrixRunnable() throws MatrixRunnableCreationException {
        id = ++id_count;
        try {
            writer = new ConcurrentWriter();
        } catch (CreationConcurrentWriterException e) {
            throw new MatrixRunnableCreationException(e);
        }
    }

    public MatrixRunnable(ConcurrentWriter writer) throws MatrixRunnableCreationException {
        if(writer == null){
            throw new MatrixRunnableCreationException("Null writer passed!");
        }
        id = ++id_count;
        this.writer = writer;
    }

    public void run() {
        LOGGER.debug(Thread.currentThread().getName() + ": Running");
        try {
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
            LOGGER.debug(Thread.currentThread().getName() + ": closing thread");
        } catch (InterruptedException | OutOfBoundsMatrixException | AccessNotGrantedException
                | BrokenBarrierException e) {
            LOGGER.warn(Thread.currentThread().getName(), e);
            e.printStackTrace();
        } catch (ConcurrentWriterException e) {
            LOGGER.warn(Thread.currentThread().getName() + "couldn't write", e);
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

    private int countSums(int diagonalIndex) throws AccessNotGrantedException, OutOfBoundsMatrixException {
        int size = matrixShell.getSquareMatrixSize();
        int count = matrixShell.getItem(diagonalIndex, diagonalIndex);
        for (int i = 0; i < size; i++) {
            if(i != diagonalIndex) {
                count += matrixShell.getItem(diagonalIndex, i);
                count += matrixShell.getItem(i, diagonalIndex);
            }
        }
        return count;
    }
}
