package epam.schepov.multithreading.thread;

import com.sun.deploy.util.StringUtils;
import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NotInitializedException;
import epam.schepov.multithreading.exception.WriterCreationException;
import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;
import epam.schepov.multithreading.writer.ConcurrentWriter;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixRunnable implements Runnable {

    private ConcurrentWriter writer = ConcurrentWriter.getInstance();
    private LockBarrierSquareMatrixShell matrixShell;
    private static int id_count = 0;
    private int id;

    public MatrixRunnable() {
        id = id_count++;
    }

    public void run() {
        try {
            matrixShell = LockBarrierSquareMatrixShell.getInstance();
            CyclicBarrier barrier = matrixShell.getCyclicBarrier();
            Random random = new Random();

            int diagonalIndex;
            do {
                diagonalIndex = random.nextInt() % matrixShell.getSquareMatrixSize();
            } while (matrixShell.setItem(diagonalIndex, diagonalIndex, id));

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

            barrier.await();

            int sum = countSums(diagonalIndex);
            String info = "Thread: " + id + "; sum: " + sum + "\n";
            writer.write(info);

        } catch (OutOfBoundsMatrixShellException e) {
            e.printStackTrace();//todo
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (NotInitializedException e) {
            e.printStackTrace();
        } catch (AccessNotGrantedException e) {
            e.printStackTrace();
        } catch (WriterCreationException e) {
            e.printStackTrace();
        }
    }

    private int generateNonDiagonalIndex(int diagonalIndex, int matrixSize) {
        Random random = new Random();
        int index = random.nextInt() % matrixSize;
        while (index == diagonalIndex) {
            index = random.nextInt() % matrixSize;
        }
        return index;
    }

    private boolean isRowChosen() {
        return new Random().nextBoolean();
    }

    private int countSums(int diagonalIndex) throws NotInitializedException, OutOfBoundsMatrixShellException, AccessNotGrantedException {
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
