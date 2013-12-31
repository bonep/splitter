package com.sysgears.example.splitter;


import com.sysgears.example.Constants;
import com.sysgears.example.FileHandlers;
import com.sysgeats.example.io.FileManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Includes methods to split files.
 */
public class Splitter implements FileHandlers {

    /**
     * Log object.
     */
    private static final Logger log = Logger.getLogger(Splitter.class);

    /**
     * File manager object.
     */
    private final FileManager fileManager;

    /**
     * Constructs splitter with file manager.
     *
     * @param fileManager sile manager object
     */
    public Splitter(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Allocates a part of the file in other file.
     *
     * @param fileInputStream  file input stream
     * @param fileOutputStream file output stream
     * @param workSize         maximum number processed data
     * @return number processed data
     */
    @Override
    public int run(final RandomAccessFile fileInputStream,
                   final RandomAccessFile fileOutputStream,
                   final long workSize) {
        log.debug("Run splitter for split file on " + workSize + "bytes");
        int numberOfBytesToProcess;
        final byte buffer[] = new byte[(int) workSize];
        try {
            numberOfBytesToProcess = fileInputStream.read(buffer, 0, (int) workSize);
            if (numberOfBytesToProcess != -1) {
                fileOutputStream.write(buffer, 0, numberOfBytesToProcess);
            }
        } catch (IOException e) {
            log.error("IOException when an red/write file: " + e.getMessage(), e);
            numberOfBytesToProcess = -1;
        }
        log.info("Handled " + numberOfBytesToProcess + "bytes");

        return numberOfBytesToProcess;
    }

    /**
     * Gets files for splits.
     *
     * @param filePath      file path
     * @param directoryPath directory path
     * @param workSize      split size
     * @return files for split
     */
    @Override
    public ArrayList<File> getFiles(final String filePath, final String directoryPath, final long workSize) {
        final ArrayList<File> result;
        final int numberFiles = (int) Math.ceil((double) new File(filePath).length() / workSize);
        result = fileManager.createFilesInDirectory(directoryPath, Constants.MY_TYPE, numberFiles);

        return result;
    }
}
