package com.galua.uniquecounter.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface UniqueWordProcessService {

    CompletableFuture<Map<String, Integer>> getAllUniqueWordsInDocument(MultipartFile file);
}
