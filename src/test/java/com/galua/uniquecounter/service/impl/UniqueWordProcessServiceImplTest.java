package com.galua.uniquecounter.service.impl;

import com.galua.uniquecounter.service.UniqueWordProcessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UniqueWordProcessServiceImplTest {

    @InjectMocks
    private final UniqueWordProcessService service = new UniqueWordProcessServiceImpl();

    @Test
    void testGetAllUniqueWordsInDocumentLegalCase() throws IOException, ExecutionException, InterruptedException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("testFile.file", "Hello World".getBytes());

        // when
        CompletableFuture<Map<String, Long>> future = service.getAllUniqueWordsInDocument(multipartFile);

        // then
        assertNotNull(future);
        assertEquals(2, future.get().size());
    }

    @Test
    void testGetAllUniqueWordsInDocumentEmptyFile() throws IOException, ExecutionException, InterruptedException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("testFile.file", "".getBytes());

        // when
        CompletableFuture<Map<String, Long>> future = service.getAllUniqueWordsInDocument(multipartFile);

        // then
        assertNotNull(future);
        assertEquals(0, future.get().size());
    }
}
