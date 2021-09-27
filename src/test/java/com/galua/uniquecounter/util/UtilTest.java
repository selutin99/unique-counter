package com.galua.uniquecounter.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    @Test
    void testReadURLToString() throws IOException {
        // given - when
        String data = Util.readURLToString("https://www.google.com");

        // then
        assertNotNull(data);
    }

    @Test
    void testReadURLToStringEmptyURL() {
        // given - when - then
        assertThrows(IOException.class, () -> Util.readURLToString(""));
    }

    @Test
    void testIsNumericTrueCondition() {
        // given - when
        boolean trueCondition = Util.isNumeric("23.3");

        // then
        assertTrue(trueCondition);
    }

    @Test
    void testIsNumericFalseCondition() {
        // given - when
        boolean falseCondition = Util.isNumeric("qwef.3");

        // then
        assertFalse(falseCondition);
    }
}
