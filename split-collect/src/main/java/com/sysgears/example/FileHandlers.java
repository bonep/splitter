package com.sysgears.example;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Interface for file handlers.
 */
public interface FileHandlers {

    /**
     * Runs fileHandlers.
     *
     * @param firstFile  first file
     * @param secondFile second file
     * @param workSize   maximum number processed data
     * @return number processed data
     */
    public int run(final RandomAccessFile firstFile, final RandomAccessFile secondFile, final long workSize);

    /**
     * Gets files for file handlers.
     *
     * @param filePath      file path
     * @param directoryPath directory path
     * @param workSize      work size for file handlers
     * @return files for file handlers
     */
    ArrayList<File> getFiles(String filePath, String directoryPath, long workSize);
}
