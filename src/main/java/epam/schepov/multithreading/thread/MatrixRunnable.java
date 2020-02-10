package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.exception.shell.OutOfBoundsMatrixShellException;
import epam.schepov.multithreading.shell.ConcurrentMatrixShell;

public class MatrixRunnable implements Runnable {

    private ConcurrentMatrixShell matrixShell;
    private static int id_count = 0;
    private int id;

    public MatrixRunnable(ConcurrentMatrixShell matrixShell){
        id = id_count++;
        this.matrixShell = matrixShell;
    }

    public void run() {
        int index = id % matrixShell.getRowsNumber();
        try {
            matrixShell.setItem(index, index, id);
        } catch (OutOfBoundsMatrixException e) {
            e.printStackTrace();
        } catch (OutOfBoundsMatrixShellException e) {
            e.printStackTrace();
        }
    }

    private int chooseRowOrColumn(){
        return 0;
    }
}
