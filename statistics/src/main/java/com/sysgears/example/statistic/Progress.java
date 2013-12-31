package com.sysgears.example.statistic;

import org.apache.log4j.Logger;

/**
 * Includes methods for collects progress.
 */
public class Progress {

    /**
     * Log object.
     */
    private static final Logger log = Logger.getLogger(Progress.class);

    /**
     * Progress finish.
     */
    public static final int PROGRESS_FINISH = 100;

    /**
     * End position.
     */
    private final long endPosition;

    /**
     * Current position.
     */
    private long currentPosition;

    /**
     * Constructs Progress with end position.
     *
     * @param endPosition end position
     */
    public Progress(final long endPosition) {
        log.info("Create process with end position: " + endPosition);
        this.endPosition = endPosition;
        this.currentPosition = 0;
    }

    /**
     * Sets current position.
     *
     * @param currentPosition current position
     * @return true if sets, otherwise false
     */
    public boolean setCurrentPosition(final long currentPosition) {
        boolean result = false;
        if (currentPosition <= endPosition) {
            this.currentPosition = currentPosition;
            result = true;
            log.debug("Set current position  - " + currentPosition);
        }

        return result;
    }

    /**
     * Gets current position.
     *
     * @return current position.
     */
    public long getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Gets progress in percents.
     *
     * @return progress in percents
     */
    public Integer getProgressInPercentages() {
        if (endPosition == 0) {
            return PROGRESS_FINISH;
        } else {
            return (int) (currentPosition * PROGRESS_FINISH / endPosition);
        }
    }
}
