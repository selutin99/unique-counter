package com.galua.uniquecounter.controller.impl;

import com.galua.uniquecounter.controller.UniqueWordsController;
import com.galua.uniquecounter.model.dto.UniqueWordsResponse;
import com.galua.uniquecounter.service.UniqueWordProcessService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.galua.uniquecounter.util.Util.readURLToString;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
@Api(value = "Unique words controller")
public class UniqueWordsControllerImpl implements UniqueWordsController {

    private final UniqueWordProcessService processService;

    @Override
    @PostMapping("/process/file")
    public ResponseEntity<UniqueWordsResponse> getUniqueWordsInDocumentByFile(
            @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file)
            throws IOException, ExecutionException, InterruptedException {
        UniqueWordsResponse response = buildUniqueWordsResponse(file);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/process/url")
    public ResponseEntity<UniqueWordsResponse> getUniqueWordsInDocumentByURL(@RequestBody String url)
            throws InterruptedException, ExecutionException, IOException {
        UniqueWordsResponse response;
        try {
            String fileData = readURLToString(url);
            MultipartFile multipartFile = new MockMultipartFile(
                    "file", "file", MediaType.TEXT_PLAIN_VALUE, fileData.getBytes(StandardCharsets.UTF_8));
            response = buildUniqueWordsResponse(multipartFile);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(response);
    }

    private UniqueWordsResponse buildUniqueWordsResponse(MultipartFile file)
            throws IOException, ExecutionException, InterruptedException {
        UniqueWordsResponse response = new UniqueWordsResponse();
        response.setRequestDate(LocalDateTime.now());

        CompletableFuture<Map<String, Long>> asyncFuture = processService.getAllUniqueWordsInDocument(file);
        CompletableFuture.allOf(asyncFuture).join();

        response.setWords(asyncFuture.get());
        response.setResponseDate(LocalDateTime.now());

        return response;
    }
}
