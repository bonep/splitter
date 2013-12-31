package com.sysgeats.example.io;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Includes methods for searches and creates files.
 */
public class FileManager {

    /**
     * Gets all file paths by mask in directory.
     *
     * @param directoryPath directory path
     * @param fileMask      file mask
     * @return file paths
     */
    public ArrayList<File> getFilesInDirectory(final String directoryPath, final String fileMask) {
        ArrayList<String> paths = new ArrayList<String>();
        final Pattern pattern = Pattern.compile("\\w+" + fileMask);
        File directory = new File(directoryPath);
        ArrayList<File> result = new ArrayList<File>();
        for (int i = 0; i < directory.list().length; i++) {
            String currentPath = directory.list()[i];
            if (pattern.matcher(currentPath).matches()) {
                paths.add(directoryPath + "/" + currentPath);
            }
        }
        Collections.sort(paths);
        for (String path : paths) {
            result.add(new File(path));
        }

        return result;
    }

    /**
     * Creates files by mask.
     *
     * @param directoryPath directory path
     * @param fileMask      file mask
     * @param countFiles    count files
     * @return created files
     */
    public ArrayList<File> createFilesInDirectory(final String directoryPath,
                                                  final String fileMask,
                                                  final int countFiles) {
        final ArrayList<File> result = new ArrayList<File>();
        for (int i = 0; i < countFiles; i++) {
            File file = new File(directoryPath + "/" + String.format("%05d", i) + fileMask);
            result.add(file);
        }

        return result;
    }
}
