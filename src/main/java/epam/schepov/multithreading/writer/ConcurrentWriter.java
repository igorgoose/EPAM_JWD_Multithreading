package epam.schepov.multithreading.writer;

import epam.schepov.multithreading.exception.writer.CreationConcurrentWriterException;
import epam.schepov.multithreading.exception.writer.NullFilePassedWriterException;
import epam.schepov.multithreading.exception.writer.ConcurrentWriterException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentWriter implements AutoCloseable{

    private static final Logger LOGGER = Logger.getLogger(ConcurrentWriter.class);

    private ReentrantLock lock = new ReentrantLock();
    private File outputFile;
    private BufferedWriter writer;

    private static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";

    public ConcurrentWriter() throws CreationConcurrentWriterException {
        outputFile = new File(DEFAULT_OUTPUT_FILE_NAME);
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            LOGGER.warn(Thread.currentThread().getName() + ": error creating BufferedWriter");
            throw new CreationConcurrentWriterException(e);
        }
    }

    public ConcurrentWriter(String filename) throws CreationConcurrentWriterException {
        outputFile = new File(filename);
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            LOGGER.warn(Thread.currentThread().getName() + ": error creating BufferedWriter");
            throw new CreationConcurrentWriterException(e);
        }
    }

    public void write(String str) throws ConcurrentWriterException {
        try {
            lock.lock();
            LOGGER.debug(Thread.currentThread().getName() + ": writing(\n" + str + ")");
            try {
                writer.write(str);
            } catch (IOException e) {
                LOGGER.warn(Thread.currentThread().getName() + ": couldn't write(\n" + str + ")");
                throw new ConcurrentWriterException("Error while writing", e);
            }
        } finally {
            lock.unlock();
        }
    }

    public void setOutputFile(File file) throws NullFilePassedWriterException {
        if(file == null){
            throw new NullFilePassedWriterException();
        }
        outputFile = file;
    }

    @Override
    public void close(){
        try {
            writer.close();
            LOGGER.debug(Thread.currentThread().getName() + ": closing");
        } catch (IOException e) {
            LOGGER.warn(Thread.currentThread().getName() + ": error while closing");
            e.printStackTrace();
        }
    }
}
