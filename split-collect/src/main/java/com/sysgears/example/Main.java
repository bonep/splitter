package com.sysgears.example;

import com.sysgears.example.collector.Collector;
import com.sysgears.example.factory.FileHandlersFactory;
import com.sysgears.example.service.FileHandlersService;
import com.sysgears.example.splitter.Splitter;
import com.sysgears.example.ui.UserInterface;
import com.sysgeats.example.io.FileManager;
import com.sysgeats.example.io.IOManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


/**
 * Class for starting program.
 */
public class Main {

    /**
     * Log object
     */
    private static final Logger log = Logger.getLogger(Main.class);

    /**
     * Main app pointer.
     *
     * @param args should be empty
     */
    public static void main(final String args[]) throws IOException, InterruptedException {
        try {
            final IOManager ioManager = new IOManager(new PrintWriter(System.out),
                    new Scanner(System.in));
            final Semaphore semaphore = new Semaphore(Constants.MAX_NUMBER_THREADS);
            final FileManager fileManager = new FileManager();
            final UserInterface ui = new UserInterface(ioManager, new FileHandlersService(new FileHandlersFactory(),
                    semaphore), new Splitter(fileManager), new Collector(fileManager));
            ui.run();
        } catch (Throwable throwable) {
            log.fatal("Unexpected throw" + throwable.getMessage(), throwable);
        }
    }
}
