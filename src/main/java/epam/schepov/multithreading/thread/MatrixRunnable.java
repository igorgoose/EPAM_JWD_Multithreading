package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixRunnable implements Runnable {

    private LockBarrierSquareMatrixShell matrixShell;
    private static boolean[][] usedCells;
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
            int diagonalIndex = random.nextInt() % matrixShell.getSquareMatrixSize();
            matrixShell.setItem(diagonalIndex, diagonalIndex, id);
            int nonDiagonalIndex = generateNonDiagonalIndex(diagonalIndex, matrixShell.getSquareMatrixSize());
            if(isRowChosen()) {
                matrixShell.setItem(nonDiagonalIndex, diagonalIndex, id);
            }//todo check on used
            barrier.await();
        } catch (OutOfBoundsMatrixShellException e) {
            e.printStackTrace();//todo
        } catch (AccessNotGrantedException e) {
            e.printStackTrace();//todo
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private int generateNonDiagonalIndex(int diagonalIndex, int matrixSize){
        Random random = new Random();
        int index = random.nextInt() % matrixSize;
        while(index == diagonalIndex){
            index = random.nextInt() % matrixSize;
        }
        return index;
    }

    private boolean isRowChosen(){
        return new Random().nextBoolean();
    }
}
