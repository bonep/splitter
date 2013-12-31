package com.sysgears.example.service;

import com.sysgears.example.FileHandlers;
import com.sysgears.example.factory.FileHandlersFactory;
import com.sysgears.example.factory.Worker;
import com.sysgears.example.statistic.Statistics;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Includes methods for management workers.
 */
public class FileHandlersService {

    /**
     * Log object
     */
    private static final Logger log = Logger.getLogger(FileHandlersService.class);

    /**
     * Object for creates workers.
     */
    private final FileHandlersFactory fileHandlersFactory;

    /**
     * semaphore object
     */
    private final Semaphore semaphore;

    /**
     * Constructs service with file handlers factory object and statistic.
     *
     * @param fileHandlersFactory file handlers factory object
     */
    public FileHandlersService(final FileHandlersFactory fileHandlersFactory, final Semaphore semaphore) {
        this.fileHandlersFactory = fileHandlersFactory;
        this.semaphore = semaphore;
    }

    /**
     * Runs service.
     *
     * @param filePath      file path
     * @param directoryPath directory path
     * @param fileHandlers  file handlers object
     * @param workSize      size for worker
     * @param statistics    statistics on workers
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void run(final String filePath,
                    final String directoryPath,
                    final FileHandlers fileHandlers,
                    final long workSize,
                    final Statistics statistics) throws IOException {
        log.debug("Run service for creating and manage workers for " + fileHandlers.getClass()
                + ". Directory path: " + directoryPath + ", file path: " + filePath);
        final File mainFile = new File(filePath);
        final Position position = new Position(0, workSize, mainFile.length());
        final ArrayList<File> files = fileHandlers.getFiles(filePath, directoryPath, workSize);
        final ArrayList<Worker> workers = new ArrayList<Worker>();
        for (File file : files) {
            long currentPosition =  position.getCurrentPosition();
            long sizeToWork = position.nextPosition()-currentPosition;
            workers.add(fileHandlersFactory.createWorker(fileHandlers, mainFile, file, semaphore,
                    currentPosition,sizeToWork, statistics.addProgress(sizeToWork)));
        }
        for (Worker worker : workers) {
            worker.start();
        }
        statistics.start();
        try {
            statistics.join();
        } catch (InterruptedException e) {
            log.error("Abnormal termination service: " + e.getMessage(), e);
        }
    }
}
