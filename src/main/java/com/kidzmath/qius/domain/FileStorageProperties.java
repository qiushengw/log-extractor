package com.kidzmath.qius.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private Map<String, String> sit20 = new HashMap<>();

    private Map<String, String> uat20 = new HashMap<>();

    public Map<String, String> getSit20() {
        return sit20;
    }

    public void setSit20(Map<String, String> sit20) {
        this.sit20 = sit20;
    }

    public Map<String, String> getUat20() {
        return uat20;
    }

    public void setUat20(Map<String, String> uat20) {
        this.uat20 = uat20;
    }

    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}