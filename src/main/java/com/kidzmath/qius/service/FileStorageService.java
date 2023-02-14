package com.kidzmath.qius.service;


import com.kidzmath.qius.domain.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private FileStorageProperties props;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.props = fileStorageProperties;
    }


    public List<Path> getFilesByCategory(Path directory, LocalDateTime lastmodifyFrom, LocalDateTime lastmodifyTo) {
            List<Path> pathList = new ArrayList<>();

            final long from = lastmodifyFrom.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
            final long to = lastmodifyTo.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, path -> {
                try {
                    return Files.getLastModifiedTime(path).toMillis() > from && Files.getLastModifiedTime(path).toMillis() < to;
                } catch (IOException e) {
                    return false;
                }
            })) {
                for (Path file : stream) {
                    pathList.add(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return pathList;
        }

    public List<Path> getFilesByCategory(String mvpVersion, String category, LocalDateTime lastmodifyFrom, LocalDateTime lastmodifyTo) {
        Path directory;
        if("sit20".equalsIgnoreCase(mvpVersion)){
            directory = Paths.get(props.getSit20().get(category));
        }else{
            directory = Paths.get(props.getUat20().get(category));
        }

        return getFilesByCategory(directory, lastmodifyFrom, lastmodifyTo);
    }

}
