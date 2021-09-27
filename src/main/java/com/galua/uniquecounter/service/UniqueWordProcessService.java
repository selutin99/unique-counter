package com.galua.uniquecounter.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface UniqueWordProcessService {

    CompletableFuture<Map<String, Long>> getAllUniqueWordsInDocument(MultipartFile file) throws IOException;
}
