package com.sysgears.example;


import com.sysgears.example.splitter.Splitter;
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
     * Tests splitter work.
     *
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    @Test
    public void testSplitter() throws IOException {

        final File testDirectory = new File("./test");
        final File inputFile = new File("./test.txt");
        final File outputFile;
        try {
            testDirectory.mkdir();
            outputFile = new File("./test/test.txt");
            PrintWriter testFileStream = new PrintWriter("./test.txt", "UTF-8");
            try {
                for (int i = 0; i < 3000; i++) {
                    testFileStream.write(i);
                }
                testFileStream.flush();
            } finally {
                testFileStream.close();
            }
            final RandomAccessFile inputStream = new RandomAccessFile(inputFile, "rw");
            final RandomAccessFile outputStream = new RandomAccessFile(outputFile, "rw");
            splitter.run(inputStream, outputStream, inputFile.length());
            final FileInputStream firstFileInputStream = new FileInputStream(outputFile);
            final FileInputStream secondFileInputStream = new FileInputStream(inputFile);
            Assert.assertTrue(inputFile.length() == outputFile.length());
            final byte firstFileBuffer[] = new byte[Constants.MEGA_BYTE];
            final byte secondFileBuffer[] = new byte[Constants.MEGA_BYTE];
            long firstFileProgress = 0;
            long secondFileProgress;
            while (firstFileProgress != -1) {
                firstFileProgress = firstFileInputStream.read(firstFileBuffer, 0, Constants.MEGA_BYTE);
                secondFileProgress = secondFileInputStream.read(secondFileBuffer, 0, Constants.MEGA_BYTE);
                Assert.assertTrue(firstFileProgress == secondFileProgress);
                for (int i = 0; i < firstFileBuffer.length; i++)
                    Assert.assertTrue(firstFileBuffer[i] == secondFileBuffer[i]);
            }
        } finally {
            inputFile.delete();
            testDirectory.delete();
        }
    }
}
