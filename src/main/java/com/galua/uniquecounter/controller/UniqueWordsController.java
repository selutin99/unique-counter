package com.galua.uniquecounter.controller;

import com.galua.uniquecounter.dto.UniqueWordsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface UniqueWordsController {

    @ApiOperation(value = "Get unique words list from document",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The process call is Successful"),
            @ApiResponse(code = 500, message = "The process call is Failed"),
            @ApiResponse(code = 400, message = "Incorrect input data")
    })
    ResponseEntity<UniqueWordsResponse> getUniqueWordsInDocumentByMultipart(
            @ApiParam(name = "file", value = "Select the file to get unique words", required = true) MultipartFile file
    ) throws ExecutionException, InterruptedException, IOException;

    @ApiOperation(value = "Get unique words list from URL",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The process call is Successful"),
            @ApiResponse(code = 500, message = "The process call is Failed"),
            @ApiResponse(code = 400, message = "Incorrect input data")
    })
    ResponseEntity<UniqueWordsResponse> getUniqueWordsInDocumentByURL(
            @ApiParam(name = "url", value = "URL with content", required = true) String url
    ) throws InterruptedException, ExecutionException, IOException;
}
