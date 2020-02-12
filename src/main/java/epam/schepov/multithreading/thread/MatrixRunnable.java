package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;

import java.util.Random;

public class MatrixRunnable implements Runnable {

    private LockBarrierSquareMatrixShell matrixShell;
    private static int id_count = 0;
    private int id;

    public MatrixRunnable() {
        id = id_count++;
    }

    public void run() {
        matrixShell = LockBarrierSquareMatrixShell.getInstance();
        Random random = new Random();
        int diagonalIndex = random.nextInt() % matrixShell.getSquareMatrixSize();

    }

    private int chooseRowOrColumn() {
        return 0;
    }


}
