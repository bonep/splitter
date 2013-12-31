package com.sysgears.example.factory;

import com.sysgears.example.FileHandlers;
import com.sysgears.example.statistic.Progress;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Semaphore;

/**
 * Includes methods to create workers.
 */
public class FileHandlersFactory {

    /**
     * Log object.
     */
    private static final Logger log = Logger.getLogger(FileHandlersFactory.class);

    /**
     * Creates and gets worker.
     *
     * @param fileHandlers file handlers object
     * @param mainFile     main file for reads, writes
     * @param workingFile  working file for reads, writes
     * @param semaphore    semaphore object
     * @param position     worker position in file
     * @param workSize     work size.
     * @param progress     progress for current worker
     * @return created worker
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public Worker createWorker(final FileHandlers fileHandlers,
                               final File mainFile,
                               final File workingFile,
                               final Semaphore semaphore,
                               final long position,
                               final long workSize,
                               final Progress progress) throws IOException {
        RandomAccessFile firstFileStream = new RandomAccessFile(mainFile, "rw");
        firstFileStream.seek(position);
        RandomAccessFile secondFileStream = new RandomAccessFile(workingFile, "rw");
        log.info("Creates worker for " + FileHandlers.class + " for " + mainFile.getPath()
                + ", position: " + position + ", work size: " + workSize);

        return new Worker(firstFileStream, secondFileStream,workSize, fileHandlers, semaphore, progress);
    }
}
