package com.galua.uniquecounter.controller;

import com.galua.uniquecounter.controller.impl.UniqueWordsControllerImpl;
import com.galua.uniquecounter.model.dto.UniqueWordsResponse;
import com.galua.uniquecounter.service.UniqueWordProcessService;
import com.galua.uniquecounter.util.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.galua.uniquecounter.util.Util.readURLToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueWordsControllerImplTest {

    @Mock
    private UniqueWordProcessService processService;

    @InjectMocks
    private UniqueWordsControllerImpl controller;

    private final Map<String, Long> wordFrequencies = new HashMap<>();

    @Test
    void testGetUniqueWordsInDocumentByFile() throws InterruptedException, ExecutionException, IOException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("testFile.file", "Hello World".getBytes());
        wordFrequencies.put("hello", 1L);
        wordFrequencies.put("world", 2L);

        when(processService.getAllUniqueWordsInDocument(any(MultipartFile.class)))
                .thenReturn(CompletableFuture.completedFuture(wordFrequencies));

        // when
        ResponseEntity<UniqueWordsResponse> response = controller.getUniqueWordsInDocumentByFile(multipartFile);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetUniqueWordsInDocumentByFileEmptyData() throws InterruptedException, ExecutionException, IOException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("testFile.file", "".getBytes());
        wordFrequencies.put("", 0L);

        when(processService.getAllUniqueWordsInDocument(any(MultipartFile.class)))
                .thenReturn(CompletableFuture.completedFuture(wordFrequencies));

        // when
        ResponseEntity<UniqueWordsResponse> response = controller.getUniqueWordsInDocumentByFile(multipartFile);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testgetUniqueWordsInDocumentByURL() throws InterruptedException, ExecutionException, IOException {
        // given
        String dataString = "Hello World";
        wordFrequencies.put("hello", 1L);
        wordFrequencies.put("world", 2L);

        when(processService.getAllUniqueWordsInDocument(any(MultipartFile.class)))
                .thenReturn(CompletableFuture.completedFuture(wordFrequencies));
        try (MockedStatic<Util> utilities = Mockito.mockStatic(Util.class)) {
            utilities.when(() -> readURLToString(anyString())).thenReturn(dataString);
        }

        // when
        ResponseEntity<UniqueWordsResponse> response = controller.getUniqueWordsInDocumentByURL("https://www.google.com");

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testgetUniqueWordsInDocumentByURLEmptyData() {
        // given
        try (MockedStatic<Util> utilities = Mockito.mockStatic(Util.class)) {
            utilities.when(() -> readURLToString("")).thenThrow(IOException.class);

            // when - then
            assertThrows(IOException.class, () -> controller.getUniqueWordsInDocumentByURL(""));
        }
    }
}
