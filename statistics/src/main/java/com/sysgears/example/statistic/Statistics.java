package com.sysgears.example.statistic;

import com.sysgeats.example.io.IOManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Includes methods to collects statistics.
 */
public class Statistics extends Thread {

    /**
     * Log object.
     */
    private static final Logger log = Logger.getLogger(Statistics.class);

    /**
     * Interval for print statistics in seconds.
     */
    private final long intervalForPrint;

    /**
     * Manager i/o streams.
     */
    private final IOManager ioManager;

    /**
     * Progress by threads.
     */
    private final ProgressForThreads progressForThreads = new ProgressForThreads();

    /**
     * Constructs statistic with interval for print and ioManager.
     *
     * @param intervalForPrint interval for print statistics in seconds
     * @param ioManager        Manager i/o streams.
     */
    public Statistics(final long intervalForPrint, final IOManager ioManager) {
        this.intervalForPrint = intervalForPrint;
        this.ioManager = ioManager;
    }

    /**
     * Adds new process in statistic.
     *
     * @param endPosition process and position
     * @return new process
     */
    public Progress addProgress(final long endPosition) {
        return progressForThreads.add(endPosition);
    }

    /**
     * Runs print statistic until it reaches 100%.
     */
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(intervalForPrint);
            } catch (InterruptedException e) {
                log.error("InterruptedException when an slipped threads: " + e.getMessage(), e);
                e.printStackTrace();
            }
            try {
                ioManager.printLine("All progress: " + progressForThreads.getAllProgress());
                for (int i = 0; i < progressForThreads.getProgressThreads().size(); i++) {
                    ioManager.printLine("Thread " + i + ": " + progressForThreads.getProgressThreads().get(i));
                }
            } catch (IOException e) {
                log.error("IOException when an write :" + e.getMessage(), e);
                e.printStackTrace();
            }
        } while (progressForThreads.getAllProgress() < Progress.PROGRESS_FINISH);
    }
}
