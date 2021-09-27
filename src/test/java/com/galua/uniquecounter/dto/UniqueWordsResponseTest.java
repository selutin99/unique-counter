package com.galua.uniquecounter.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UniqueWordsResponseTest {

    @Test
    void testCreateUniqueWordsResponseObjectShouldSetSettingsCorrectly() {
        // given - when
        UniqueWordsResponse response = new UniqueWordsResponse();
        response.setRequestDate(LocalDateTime.now());

        // then
        assertNotNull(response);
        assertNotNull(response.getRequestDate());
        assertNull(response.getResponseDate());
    }
}
