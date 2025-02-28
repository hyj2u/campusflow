package com.cnco.campusflow.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUtil {
    public String saveFile(String basePath, MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        // 저장 경로 생성
        File directory = new File(basePath);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs(); // 경로가 없으면 생성
            if (!isCreated) {
                throw new IOException("Failed to create directories: " + basePath);
            }
        }
        // 파일 저장
        File destinationFile = new File(directory, fileName);
        file.transferTo(destinationFile);
        return fileName;
    }
}
