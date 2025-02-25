package com.cnco.campusflow.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;

    /**
     * 이미지 파일 조회
     *
     * @param imageId 이미지 ID
     * @return 파일 객체
     */
    public File getImageFile(Long imageId) {
        ImageEntity imageEntity = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID: " + imageId));
        File file = new File(imageEntity.getImgPath());
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found for image ID: " + imageId);
        }

        return file;
    }

}
