package epam.schepov.multithreading.main;

import epam.schepov.multithreading.exception.reader.ReaderException;
import epam.schepov.multithreading.exception.shell.NullSquareMatrixPassed;
import epam.schepov.multithreading.exception.thread.AccessNotGrantedException;
import epam.schepov.multithreading.exception.thread.MatrixRunnableCreationException;
import epam.schepov.multithreading.exception.writer.ConcurrentWriterException;
import epam.schepov.multithreading.exception.writer.CreationConcurrentWriterException;
import epam.schepov.multithreading.reader.SquareMatrixReader;
import epam.schepov.multithreading.reader.result.SquareMatrixReaderResult;
import epam.schepov.multithreading.shell.lock.LockBarrierSquareMatrixShell;
import epam.schepov.multithreading.thread.MatrixRunnable;
import epam.schepov.multithreading.writer.ConcurrentWriter;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private static LockBarrierSquareMatrixShell matrixShell;
    private static int iterationsNumber;
    private static int threadsNumberAtOnce;
    private static final String LINE_SEPARATOR = "__________________________________________\n";

    private static void initialize() throws ReaderException, NullSquareMatrixPassed, AccessNotGrantedException {
        SquareMatrixReader reader = new SquareMatrixReader();
        SquareMatrixReaderResult result = reader.read();
        matrixShell = LockBarrierSquareMatrixShell.getInstance();
        matrixShell.setSquareMatrix(result.getMatrix());
        iterationsNumber = result.getIterationsNumber();
        threadsNumberAtOnce = result.getThreadsNumber();
    }

    public static void main(String[] args) {

        try(ConcurrentWriter writer = new ConcurrentWriter()) {
            initialize();
            for (int i = 0; i < iterationsNumber; i++) {
                Thread[] threadArray = new Thread[threadsNumberAtOnce];
                for (int j = 0; j < threadsNumberAtOnce; j++) {
                    threadArray[j] = new Thread(new MatrixRunnable(writer));
                    threadArray[j].start();
                }
                for (int k = 0; k < threadsNumberAtOnce; k++) {
                    threadArray[k].join();
                }

                try {
                    writer.write(matrixShell.getSquareMatrix().toString());
                    writer.write(LINE_SEPARATOR);
                } catch (ConcurrentWriterException e) {
                    LOGGER.warn("Couldn't write matrix", e);
                }

                matrixShell.reset();
            }
        } catch (ReaderException e) {
            LOGGER.error("Error while reading: ", e);
        } catch (InterruptedException e) {
            LOGGER.error(Thread.currentThread().getName() + ": unplanned interruption", e);
        } catch (CreationConcurrentWriterException e) {
            LOGGER.error("Error while creating writer: ", e);
        } catch (MatrixRunnableCreationException e) {
            LOGGER.error("Error while creating thread: ", e);
        } catch (ConcurrentWriterException e) {
            LOGGER.warn("Error while closing writer: ", e);
        }
    }
}
