package com.sysgears.example.service;

/**
 * Includes position and methods for position.
 */
public class Position {

    /**
     * Position step.
     */
    private final long step;

    /**
     * End position.
     */
    private final long endPosition;

    /**
     * Current position.
     */
    private long currentPosition;

    /**
     * Constructs position with start position, end position and step.
     *
     * @param startPosition start position
     * @param step          position step
     * @param endPosition   end position
     */
    public Position(final long startPosition, final long step, final long endPosition) {
        this.currentPosition = startPosition;
        this.step = step;
        this.endPosition = endPosition;
    }

    /**
     * Gets current position.
     *
     * @return current position
     */
    public long getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets current position in next position and gets it.
     *
     * @return nex position
     */
    public long nextPosition() {
        if (step < endPosition - currentPosition) {
            currentPosition += step;
        } else {
            currentPosition = endPosition;
        }

        return currentPosition;
    }
}
