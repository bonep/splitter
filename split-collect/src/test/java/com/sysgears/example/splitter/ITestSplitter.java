package com.sysgears.example.splitter;


import com.sysgears.example.Constants;
import com.sysgeats.example.io.FileManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;

/**
 * Includes methods for tests splitter.
 */
public class ITestSplitter {

    /**
     * Splitter object for tests.
     */
    private final Splitter splitter = new Splitter(new FileManager());

    /**
     * Output file path.
     */
    private final String OUTPUT_FILE_PATH = "./test/test.txt";

    /**
     * Input file path.
     */
    private final String INPUT_FILE_PATH = "./test.txt";

    /**
     * Test directory path.
     */
    private final String TEST_DIR_PATH = "./test";

    /**
     * Tested file size.
     */
    private final long FILE_SIZE = 3000;

    /**
     * Tests splitter work.
     */
    @Test
    public void testSplitter() {

        final File testDirectory = new File(TEST_DIR_PATH);
        final File inputFile = new File(INPUT_FILE_PATH);
        final File outputFile = new File(OUTPUT_FILE_PATH);
        try {
            testDirectory.mkdir();
            PrintWriter testFileStream = new PrintWriter("./test.txt", "UTF-8");
            try {
                for (int i = 0; i < FILE_SIZE; i++) {
                    testFileStream.write(i);
                }
                testFileStream.flush();
            } finally {
                testFileStream.close();
            }
            final RandomAccessFile inputStream = new RandomAccessFile(inputFile, "rw");
            final RandomAccessFile outputStream = new RandomAccessFile(outputFile, "rw");
            final FileInputStream firstTestStream = new FileInputStream(outputFile);
            final FileInputStream secondTestStream = new FileInputStream(inputFile);
            try {
                splitter.run(inputStream, outputStream, inputFile.length());
                Assert.assertTrue(inputFile.length() == outputFile.length());
                final byte firstFileBuffer[] = new byte[Constants.MEGA_BYTE];
                final byte secondFileBuffer[] = new byte[Constants.MEGA_BYTE];
                long firstFileProgress = 0;
                long secondFileProgress;
                while (firstFileProgress != -1) {
                    firstFileProgress = firstTestStream.read(firstFileBuffer, 0, Constants.MEGA_BYTE);
                    secondFileProgress = secondTestStream.read(secondFileBuffer, 0, Constants.MEGA_BYTE);
                    Assert.assertTrue(firstFileProgress == secondFileProgress);
                    for (int i = 0; i < firstFileBuffer.length; i++)
                        Assert.assertTrue(firstFileBuffer[i] == secondFileBuffer[i]);
                }
            } finally {
                inputStream.close();
                outputStream.close();
                firstTestStream.close();
                secondTestStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputFile.delete();
            outputFile.delete();
            testDirectory.delete();
        }
    }
}
