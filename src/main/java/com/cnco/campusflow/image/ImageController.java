package com.cnco.campusflow.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    /**
     * 이미지 제공 API
     *
     * @param imageId 이미지 ID
     * @return 이미지 파일
     */
    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        Resource resource = new FileSystemResource(imageService.getImageFile(imageId));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // MIME 타입 설정 (필요 시 이미지 타입에 맞게 변경)
                .body(resource);
    }
}
