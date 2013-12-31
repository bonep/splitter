package com.sysgears.example.factory;

import com.sysgears.example.Constants;
import com.sysgears.example.FileHandlers;
import com.sysgears.example.statistic.Progress;
import org.apache.log4j.Logger;

import java.io.RandomAccessFile;
import java.util.concurrent.Semaphore;

/**
 * Thread for work.
 */
public class Worker extends Thread {

    /**
     * Log object
     */
    private static final Logger log = Logger.getLogger(Worker.class);

    /**
     * File input stream.
     */
    private final RandomAccessFile fileInputStream;

    /**
     * File output stream.
     */
    private final RandomAccessFile fileOutputStream;

    /**
     * File handlers object.
     */
    private final FileHandlers fileHandlers;

    /**
     * Semaphore object.
     */
    private final Semaphore semaphore;

    /**
     * This thread process.
     */
    private final Progress statistic;

    /**
     * Work size.
     */
    private final long workSize;

    /**
     * Constructs worker with file streams,maximum number processed data, instance and synchronizes object.
     *
     * @param fileInputStream  file input stream
     * @param fileOutputStream file output stream
     * @param workSize         work size
     * @param fileHandlers     file handlers object
     * @param semaphore        semaphore object
     * @param statistic        progress for thread
     */
    public Worker(final RandomAccessFile fileInputStream,
                  final RandomAccessFile fileOutputStream,
                  final long workSize,
                  final FileHandlers fileHandlers,
                  final Semaphore semaphore,
                  final Progress statistic) {
        this.fileInputStream = fileInputStream;
        this.fileOutputStream = fileOutputStream;
        this.fileHandlers = fileHandlers;
        this.semaphore = semaphore;
        this.statistic = statistic;
        this.workSize=workSize;
        log.debug("Create worker for " + fileHandlers.getClass());
    }

    /**
     * Runs worker.
     */
    @Override
    public void run() {
        log.debug("Run worker for " + fileHandlers.getClass());
        long currentProgressBytes;
        long processBytes = 0;
        try {
            semaphore.acquire();
            do {
                currentProgressBytes = fileHandlers.run(fileInputStream, fileOutputStream, Constants.MEGA_BYTE);
                log.info("For current iteration handled bytes - " + currentProgressBytes);
                processBytes += currentProgressBytes;
                statistic.setCurrentPosition(processBytes);
            } while ((currentProgressBytes != -1)
                    &&(processBytes!=workSize));
        } catch (InterruptedException e) {
            log.error("Exception synchronised threads: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        log.debug("All handled bytes: " + processBytes);
    }
}
