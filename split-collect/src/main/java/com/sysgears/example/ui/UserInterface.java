package com.sysgears.example.ui;

import com.sysgears.example.Constants;
import com.sysgears.example.collector.Collector;
import com.sysgears.example.service.FileHandlersService;
import com.sysgears.example.splitter.Splitter;
import com.sysgears.example.statistic.Statistics;
import com.sysgeats.example.io.IOManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Includes methods to interact with a user.
 */
public class UserInterface {

    /**
     * Log object
     */
    private static final Logger log = Logger.getLogger(UserInterface.class);

    /**
     * Manager i/o streams.
     */
    private final IOManager ioManager;

    /**
     * File handlers service for runs splitter and collector.
     */
    private final FileHandlersService fileHandlersService;

    /**
     * Splitter object.
     */
    private final Splitter splitter;

    /**
     * Collector object.
     */
    private final Collector collector;

    /**
     * Constructs UserInterface with ioManager, file handler service, splitter and collector.
     *
     * @param ioManager           object to interact with i/o streams
     * @param fileHandlersService file handlers service for runs splitter and collector
     * @param splitter            splitter object
     * @param collector           collector object
     */
    public UserInterface(final IOManager ioManager,
                         final FileHandlersService fileHandlersService,
                         final Splitter splitter,
                         final Collector collector) {
        this.splitter = splitter;
        this.collector = collector;
        this.ioManager = ioManager;
        this.fileHandlersService = fileHandlersService;

    }

    /**
     * Runs main menu of the program.
     *
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void run() throws IOException {
        Commands menuCommand;
        String filePath;
        String directoryPath;
        do {
            for (Commands command : Commands.values()) {
                ioManager.printLine(command.text);
            }
            Long numberCommand = ioManager.readLong();
            if (numberCommand != null) {
                menuCommand = Commands.getForNumber(numberCommand.intValue());
            } else {
                menuCommand = Commands.DEFAULT;
            }
            switch (menuCommand) {
                case SPLIT:
                    ioManager.printLine("Enter file path");
                    filePath = ioManager.readLine();
                    ioManager.printLine("Enter directory path");
                    directoryPath = ioManager.readLine();
                    ioManager.printLine("Enter split size in mega byte");
                    Long splitSize = ioManager.readLong();
                    if (splitSize != null) {
                        log.debug("User entered command \"split\" for " + filePath + " to "
                                + directoryPath + ", split size:" + splitSize*Constants.MEGA_BYTE);
                        fileHandlersService.run(filePath, directoryPath, splitter, splitSize*Constants.MEGA_BYTE
                                , new Statistics(Constants.SECOND, ioManager));
                    } else {
                        ioManager.printLine("Invalid format for \"split size\"");
                    }
                    break;
                case COLLECT:
                    ioManager.printLine("Enter file path");
                    filePath = ioManager.readLine();
                    ioManager.printLine("Enter directory path");
                    directoryPath = ioManager.readLine();
                    log.debug("User entered command \"collect\" for " + filePath + " from "
                            + directoryPath);
                    fileHandlersService.run(filePath, directoryPath, collector, -1,
                            new Statistics(Constants.SECOND, ioManager));
                    break;
            }
        } while (menuCommand != Commands.EXIT);
    }
}
