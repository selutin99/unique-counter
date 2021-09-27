package com.galua.uniquecounter.service.impl;

import com.galua.uniquecounter.service.UniqueWordProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
@RequiredArgsConstructor
public class UniqueWordProcessServiceImpl implements UniqueWordProcessService {

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Map<String, Integer>> getAllUniqueWordsInDocument(MultipartFile file) {
        log.info("Async start analyze file to unique words");

        return null;
    }
}
