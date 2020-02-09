package epam.schepov.multithreading.thread;

import epam.schepov.multithreading.matrix.Matrix;

public class MatrixRunnable implements Runnable {

    private Matrix matrix = Matrix.INSTANCE;
    private static int id_count = 1;
    private int id;

    public MatrixRunnable(){
        id = id_count++;
    }

    public void run() {

    }
}
