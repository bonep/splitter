package com.sysgears.example.statistics;

import com.sysgears.example.statistic.Progress;
import com.sysgears.example.statistic.ProgressForThreads;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Includes methods for tests statistic
 */
public class ITestStatistics {

    /**
     * Integration tests statistic.
     */
    @Test
    public void testStatistic() {
        ProgressForThreads progressForThreads = new ProgressForThreads();
        Progress progress = progressForThreads.add(10);
        progress.setCurrentPosition(5);
        Assert.assertTrue(progressForThreads.getAllProgress() == 50);
        progress.setCurrentPosition(10);
        Assert.assertTrue(progressForThreads.getAllProgress() == 100);
    }
}
