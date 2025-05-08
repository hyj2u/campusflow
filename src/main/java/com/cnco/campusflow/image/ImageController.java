package com.cnco.campusflow.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
    name = "Image",
    description = """
        이미지 관리 API
        
        * 이미지 파일을 조회하는 기능을 제공합니다.
        * 이미지 ID로 이미지 파일을 조회할 수 있습니다.
        * JPEG 형식의 이미지를 지원합니다.
        """
)
public class ImageController {
    private final ImageService imageService;

    @Operation(
        summary = "이미지 조회",
        description = """
            이미지 ID로 이미지 파일을 조회합니다.
            
            * 이미지 ID로 이미지 파일을 조회합니다.
            * JPEG 형식의 이미지를 반환합니다.
            * 이미지 파일이 없는 경우 404 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이미지 파일이 성공적으로 조회되었습니다"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
        @ApiResponse(responseCode = "404", description = "이미지 파일을 찾을 수 없습니다")
    })
    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(
        @Parameter(description = "이미지 번호", example = "1") @PathVariable Long imageId
    ) {
        Resource resource = new FileSystemResource(imageService.getImageFile(imageId));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
