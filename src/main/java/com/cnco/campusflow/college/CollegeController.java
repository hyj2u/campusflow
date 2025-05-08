package com.cnco.campusflow.college;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.community.CommunityBoardRequestDto;
import com.cnco.campusflow.community.CommunityService;
import com.cnco.campusflow.community.ReplyRequestDto;
import com.cnco.campusflow.community.ReportDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
@Tag(
    name = "College",
    description = """
        대학 관리 API
        
        * 대학 목록 조회
        * 대학 정보 관리
        
        대부분의 API는 JWT 인증이 필요합니다.
        """
)
@SecurityRequirement(name = "bearerAuth")
public class CollegeController {
    private final CollegeService collegeService;

    @Operation(
        summary = "대학 목록 조회",
        description = """
            등록된 모든 대학의 목록을 조회합니다.
            
            * 대학 ID, 코드, 이름 정보가 포함됩니다.
            * 생성/수정 시간이 포함됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "대학 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = CollegeEntity.class))
        ),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근")
    })
    @GetMapping
    public ResponseEntity<?> getColleges() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(collegeService.getColleges()));
    }
}
