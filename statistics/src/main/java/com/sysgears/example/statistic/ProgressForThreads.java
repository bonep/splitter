package com.sysgears.example.statistic;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Include progress for threads
 */
public class ProgressForThreads {

    /**
     * Log object.
     */
    private static final Logger log = Logger.getLogger(Statistics.class);

    /**
     * End position.
     */
    private long endPosition;

    /**
     * Progress objects.
     */
    private final ArrayList<Progress> progresses = new ArrayList<Progress>();

    /**
     * Adds new process in progress by threads.
     *
     * @param endPosition end position.
     * @return new progress
     */
    public Progress add(long endPosition) {
        log.info("Added progress by end position: " + endPosition);
        this.endPosition += endPosition;
        Progress progress = new Progress(endPosition);
        progresses.add(progress);

        return progress;
    }

    /**
     * Gets all progress.
     *
     * @return all progress in percentages
     */
    public long getAllProgress() {
        long currentPosition = 0;
        long result;
        for (Progress progress : progresses) {
            currentPosition += progress.getCurrentPosition();
        }
        if (endPosition == 0) {
            result = 100;
        } else {
            result = currentPosition * 100 / endPosition;
        }
        log.debug("Gets all progress by threads: " + result);

        return result;
    }

    /**
     * Gets progress for each thread.
     *
     * @return progress for each thread
     */
    public ArrayList<Integer> getProgressThreads() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Progress progress : progresses) {
            result.add(progress.getProgressInPercentages());
        }

        return result;
    }
}
