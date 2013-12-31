package com.sysgears.example;

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

    @DataProvider
    public Object[][] positionDate() {
        return new Object[][]{
                {0,0,100 },
                {0,50,0 },
                {64,100,64 }
        };
    }

    /**
     * Tests getter for process in percentages.
     */
    @Test(dataProvider="positionDate")
    public void testGetterProcessInPercentages(final long currentPosition,final long endPosition,final int result) {
        progress = new Progress(endPosition);
        progress.setCurrentPosition(currentPosition);
        Assert.assertTrue(progress.getProgressInPercentages().equals(result));
    }

    /**
     * Tests setter for current position
     */
    @Test
    public void testSetterCurrentPosition() {
        progress = new Progress(0);
        Assert.assertTrue(progress.setCurrentPosition(0));
        progress = new Progress(50);
        Assert.assertTrue(progress.setCurrentPosition(40));
        progress = new Progress(100);
        Assert.assertTrue(progress.setCurrentPosition(100));
        progress = new Progress(20);
        Assert.assertFalse(progress.setCurrentPosition(30));
        progress = new Progress(-5);
        Assert.assertFalse(progress.setCurrentPosition(0));
        progress = new Progress(100);
        Assert.assertFalse(progress.setCurrentPosition(101));
    }
}
