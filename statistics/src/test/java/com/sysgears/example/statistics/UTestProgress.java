package com.sysgears.example.statistics;

import com.sysgears.example.statistic.Progress;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Includes methods for tests progress
 */
public class UTestProgress {

    /**
     * Progress object for tests.
     */
    private Progress progress;

    /**
     * Position data provider.
     *
     * @return position, end point and result
     */
    @DataProvider
    public Object[][] positionDate() {
        return new Object[][]{
                {0, 0, 100},
                {0, 50, 0},
                {64, 100, 64}
        };
    }

    /**
     * Tests getter for process in percentages.
     */
    @Test(dataProvider = "positionDate")
    public void testGetterProcessInPercentages(final long currentPosition, final long endPosition, final int result) {
        progress = new Progress(endPosition);
        progress.setCurrentPosition(currentPosition);
        Assert.assertTrue(progress.getProgressInPercentages().equals(result));
    }

    /**
     * Tests setter for current position
     */
    @Test(dataProvider = "positionDate")
    public void testSetterCurrentPosition(final long currentPosition, final long endPosition) {
        progress = new Progress(endPosition);
        Assert.assertTrue(progress.setCurrentPosition(currentPosition));
    }
}
