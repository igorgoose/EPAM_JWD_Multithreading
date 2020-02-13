package epam.schepov.multithreading.main;

import epam.schepov.multithreading.exception.AccessNotGrantedException;
import epam.schepov.multithreading.exception.NullSquareMatrixPassed;
import epam.schepov.multithreading.exception.reader.ReaderException;
import epam.schepov.multithreading.matrix.SquareMatrix;
import epam.schepov.multithreading.reader.SquareMatrixReader;
import epam.schepov.multithreading.reader.result.SquareMatrixReaderResult;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;
import epam.schepov.multithreading.thread.MatrixRunnable;

public class Main {

    private static LockBarrierSquareMatrixShell matrixShell;
    private static int iterationsNumber;
    private static int threadsNumberAtOnce;
    private static int overallThreadsNumber;

    private static void initialize() throws ReaderException, NullSquareMatrixPassed, AccessNotGrantedException {
        SquareMatrixReader reader = new SquareMatrixReader();
        SquareMatrixReaderResult result = reader.read();
        matrixShell = LockBarrierSquareMatrixShell.getInstance();
        matrixShell.setSquareMatrix(result.getMatrix());
        iterationsNumber = result.getIterationsNumber();
        threadsNumberAtOnce = result.getThreadsNumber();
        overallThreadsNumber = iterationsNumber * threadsNumberAtOnce;
    }

    public static void main(String[] args) {

        try {
            initialize();
            Thread[] threadArray = new Thread[overallThreadsNumber];
            for (int i = 0; i < overallThreadsNumber; i++) {
                threadArray[i] = new Thread(new MatrixRunnable());
            }
            //for (int i = 0; i < iterationsNumber; i++) {
            for (int j = 0; j < threadsNumberAtOnce; j++) {
                threadArray[/*i * threadsNumberAtOnce + */j].start();
            }
            for (int i = 0; i < threadsNumberAtOnce; i++) {
                threadArray[i].join();
            }
            //}
        } catch (ReaderException e) {
            e.printStackTrace();
        } catch (NullSquareMatrixPassed e) {
            e.printStackTrace();
        } catch (AccessNotGrantedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
