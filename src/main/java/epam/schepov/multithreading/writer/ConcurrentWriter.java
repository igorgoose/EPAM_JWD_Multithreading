package epam.schepov.multithreading.writer;

import epam.schepov.multithreading.exception.NullFilePassedWriterException;
import epam.schepov.multithreading.exception.WriterCreationException;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentWriter {

    private ReentrantLock lock;
    private File outputFile;
    private BufferedWriter writer;

    private static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";

    private ConcurrentWriter() {
        outputFile = new File(getClass().getClassLoader().getResource(DEFAULT_OUTPUT_FILE_NAME).getFile());
        lock = new ReentrantLock();
    }

    private static class InstanceHolder{
        private static final ConcurrentWriter INSTANCE = new ConcurrentWriter();
    }

    public static ConcurrentWriter getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void write(String str) throws WriterCreationException {
        if(writer == null){
            try {
                writer = new BufferedWriter(new FileWriter(outputFile));
            } catch (IOException e) {
                throw new WriterCreationException();
            }
        }
        try {
            lock.lock();
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();//todo
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

}
