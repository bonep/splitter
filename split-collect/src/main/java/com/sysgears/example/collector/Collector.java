package com.sysgears.example.collector;

import com.sysgears.example.Constants;
import com.sysgears.example.FileHandlers;
import com.sysgeats.example.io.FileManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Includes methods to collect files.
 */
public class Collector implements FileHandlers {

    /**
     * Log object.
     */
    public static final Logger log = Logger.getLogger(Collector.class);

    /**
     * File manager object.
     */
    private final FileManager fileManager;

    /**
     * Constructs splitter with file manager.
     *
     * @param fileManager sile manager object
     */
    public Collector(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Collect files.
     *
     * @param fileOutputStream file output stream
     * @param fileInputStream  file input stream
     * @param workSize         maximum number processed data
     * @return number processed data
     */
    @Override
    public int run(final RandomAccessFile fileOutputStream,
                   final RandomAccessFile fileInputStream,
                   final long workSize) {
        log.debug("Runs collector with size to work: " + workSize);
        int numberOfBytesToProcess = -1;
        final byte buffer[] = new byte[(int) workSize];
        try {
            numberOfBytesToProcess = fileInputStream.read(buffer, 0, (int) workSize);
            if (numberOfBytesToProcess != -1)
                fileOutputStream.write(buffer, 0, numberOfBytesToProcess);
        } catch (IOException e) {
            log.error("IOException when an red/write file :" + e.getMessage(), e);
            e.printStackTrace();
        }
        log.info("Handled bytes - " + numberOfBytesToProcess + " and return then");

        return numberOfBytesToProcess;
    }

    /**
     * Gets files for collect.
     *
     * @param filePath      ignored
     * @param directoryPath directory path
     * @param workSize      ignored
     * @return files for collect
     */
    @Override
    public ArrayList<File> getFiles(final String filePath, final String directoryPath, final long workSize) {
        final ArrayList<File> result;
        final int numberFiles = (int) Math.ceil((double) new File(filePath).length() / workSize);
        result = fileManager.createFilesInDirectory(directoryPath, Constants.MY_TYPE, numberFiles);

        return result;
    }
}

