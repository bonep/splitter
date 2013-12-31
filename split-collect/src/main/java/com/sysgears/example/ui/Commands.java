package com.sysgears.example.ui;


/**
 * Contents command.
 */
public enum Commands {

    DEFAULT(-1, ""),
    SPLIT(1, "1. Split file"),
    COLLECT(2, "2. Collect file"),
    EXIT(0, "0. Exit");

    /**
     * Command text.
     */
    public final String text;

    /**
     * Command number.
     */
    public final int number;

    /**
     * Constructs Commands with gotten number and text.
     *
     * @param number command number
     * @param text   command text
     */
    private Commands(final int number, final String text) {
        this.number = number;
        this.text = text;
    }

    /**
     * Gets command by number.
     *
     * @param number command number
     * @return command object
     */
    public static Commands getForNumber(final int number) {
        Commands commandResult = DEFAULT;
        for (Commands command : values()) {
            if (command.number == number) {
                commandResult = command;
            }
        }

        return commandResult;
    }
}
