package com.sysgears.example;

import com.sysgears.example.service.Position;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Includes methods for tests position.
 */
public class UTestPosition {

    /**
     * Test position.
     */
    @Test
    public void testPosition() {
        Position position = new Position(0, 5, 9);
        Assert.assertTrue(position.getCurrentPosition() == 0);
        Assert.assertTrue(position.nextPosition()==5);
        Assert.assertTrue(position.nextPosition()==9);
    }
}
