package com.kidzmath.qius.controller;

import com.kidzmath.qius.service.FileStorageService;
import com.kidzmath.qius.util.CommUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@RestController
public class FileDownloadController {
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    private FileStorageService fileStorageService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");


    @RequestMapping(path = "/log/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> smartConvert(
            @RequestParam String mvpVersion,
            @RequestParam String category,
            @RequestParam String fromYyyyMMddHH,
            @RequestParam String toYyyyMMddHH) throws IOException {

        LocalDateTime dtFromYyyyMMddHH = LocalDateTime.parse(fromYyyyMMddHH, formatter);
        LocalDateTime dtToYyyyMMddHH = LocalDateTime.parse(toYyyyMMddHH, formatter);

        List<Path> pathList =  fileStorageService.getFilesByCategory(mvpVersion, category, dtFromYyyyMMddHH, dtToYyyyMMddHH);
        Path zipFile = CommUtils.generateZipFile(pathList,  category + "." + dtFromYyyyMMddHH + "-" + dtToYyyyMMddHH + ".zip" );

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(zipFile));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+zipFile.toFile().getName())
                .contentLength(zipFile.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
