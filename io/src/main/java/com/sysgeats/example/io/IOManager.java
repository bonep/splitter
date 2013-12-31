package com.sysgeats.example.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Provides basic input operations and formatted output operations using output and input streams.
 */
public class IOManager {

    /**
     * Input stream.
     */
    private final Scanner scanner;

    /**
     * Output stream.
     */
    private final Writer writer;

    /**
     * Constructs IOManager with input stream and output stream.
     *
     * @param writer  input stream object
     * @param scanner output stream object
     */
    public IOManager(final Writer writer,
                     final Scanner scanner) {
        this.writer = writer;
        this.scanner = scanner;
    }

    /**
     * Prints a line to output stream.
     *
     * @param line line to print
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void printLine(final String line) throws IOException {
        writer.write(line + "\n");
        writer.flush();
    }

    /**
     * Reads a line from input stream.
     *
     * @return line value
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Reads an long value from input stream.
     *
     * @return long value, null if value is not long
     */
    public Long readLong() {
        final Long result;
        final String inputString = scanner.nextLine();
        final Pattern pattern = Pattern.compile("\\d+");
        if (pattern.matcher(inputString).matches()) {
            result = Long.parseLong(inputString);
        } else {
            result = null;
        }

        return result;
    }
}
