package com.galua.uniquecounter.controller;

import com.galua.uniquecounter.dto.UniqueWordsResponse;
import com.galua.uniquecounter.service.UniqueWordProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
@Api(value = "Unique words")
public class UniqueWordsController {

    private final UniqueWordProcessService processService;

    @ApiOperation(value = "Get unique words list from document",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The process call is Successful"),
            @ApiResponse(code = 500, message = "The process call is Failed"),
            @ApiResponse(code = 400, message = "Incorrect input data")})
    @PostMapping("/process")
    public UniqueWordsResponse getUniqueWordsInDocument(
            @ApiParam(name = "file", value = "Select the file to get unique words", required = true)
            @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file)
            throws ExecutionException, InterruptedException, IOException {
        UniqueWordsResponse response = new UniqueWordsResponse();
        response.setRequestDate(LocalDateTime.now());

        CompletableFuture<Map<String, Long>> asyncFuture = processService.getAllUniqueWordsInDocument(file);
        CompletableFuture.allOf(asyncFuture).join();

        response.setWords(asyncFuture.get());
        response.setResponseDate(LocalDateTime.now());

        return response;
    }
}
