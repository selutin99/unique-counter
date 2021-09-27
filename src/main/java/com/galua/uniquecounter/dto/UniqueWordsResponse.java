package com.galua.uniquecounter.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UniqueWordsResponse {

    private Map<String, Integer> words;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
}
