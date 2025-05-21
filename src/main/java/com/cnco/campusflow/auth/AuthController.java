package com.cnco.campusflow.auth;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.college.CollegeService;
import com.cnco.campusflow.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
    name = "Auth",
    description = """
        본인인증 API
        
        대부분의 API는 JWT 인증이 필요합니다.
        """
)
public class AuthController {
    private final AuthKcpService authKcpService;

   /* @Operation(
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
    @GetMapping("/kcp/init")
    public ResponseEntity<KcpAuthInitResponseDto> getKcpAuthParams() {
        return ResponseEntity.ok(kcpService.generateKcpParams());
    }*/
}
