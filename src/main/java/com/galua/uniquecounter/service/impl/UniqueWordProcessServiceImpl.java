package com.galua.uniquecounter.service.impl;

import com.galua.uniquecounter.service.UniqueWordProcessService;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.galua.uniquecounter.util.Util.isNumeric;

@Service
@Log4j2
@RequiredArgsConstructor
public class UniqueWordProcessServiceImpl implements UniqueWordProcessService {

    private final static String SPLIT_REGEX = "-|\\#|\\(|\\)|\\{|\\}|\\<|\\>|\\s+|\\(\\\"|\\;";

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Map<String, Long>> getAllUniqueWordsInDocument(MultipartFile file) throws IOException {
        log.info("Async start analyze file to unique words");
        String fileWords = removeAllTagsFromFile(new String(file.getBytes(), StandardCharsets.UTF_8));
        return CompletableFuture.completedFuture(
                sortWordsByFrequency(countWordsFrequency(fileWords.toLowerCase().split(SPLIT_REGEX)))
        );
    }

    private String removeAllTagsFromFile(String fileContent) {
        log.info("Remove HTML tags from file");
        return Parser.unescapeEntities(Jsoup.parse(fileContent).text(),false);
    }

    private Map<String, Long> countWordsFrequency(String[] words) {
        Map<String, Long> counterMap = new HashMap<>();
        log.info("Get frequency dictionary");
        return Stream.of(words)
                .collect(Collectors.groupingBy(k -> k, () -> counterMap, Collectors.counting()));
    }

    private Map<String, Long> sortWordsByFrequency(Map<String, Long> wordsFrequencies) {
        log.info("Sort words by frequency");
        return wordsFrequencies.entrySet()
                .stream()
                .filter(entry ->
                        !Strings.isNullOrEmpty(entry.getKey())
                                && !entry.getKey().contains(" ")
                                && !isNumeric(entry.getKey()))
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
    }
}
