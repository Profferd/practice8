package com.intern.controller;

import com.intern.dto.*;
import com.intern.exception.EmptyFileException;
import com.intern.exception.InvalidFileTypeException;
import com.intern.service.PepServiceImpl;
import com.intern.util.FileUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import com.intern.dto.*;

import java.io.File;
import java.util.List;
import static com.intern.controller.GlobalExceptionHandler.returnErrorsToClient;


@RestController
@RequestMapping("/api/pep")
@RequiredArgsConstructor
public class PepController {
    private final PepServiceImpl service;

    @PostMapping("/upload")
    public RestResponse upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) throw new EmptyFileException();
        final File TEMP_FILE_DIRECTORY = new File("src/main/resources/temp");
        switch (file.getContentType()) {
            case "application/json" -> service.upload(FileUtils.getFile(file, TEMP_FILE_DIRECTORY).toFile());
            case "application/zip" -> service.upload(FileUtils.getUnzippedFile(file, TEMP_FILE_DIRECTORY));
            default -> throw new InvalidFileTypeException();
        }
        return new RestResponse("%s, %dMB: Uploaded!"
                .formatted(file.getOriginalFilename(), Math.round(file.getSize()/1e+6)));
    }

    @GetMapping("/top10")
    public List<PepTopNamesDto> getTopTen() {
        return service.getTopTenNames();
    }

    @PostMapping("/_search")
    public PageDto<PepInfoDto> findPeps(@Valid @RequestBody PepQueryDto query, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        return service.search(query);
    }
}
