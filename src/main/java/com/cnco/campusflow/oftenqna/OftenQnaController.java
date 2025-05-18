package com.cnco.campusflow.oftenqna;

import com.cnco.campusflow.common.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oftenqna")
@RequiredArgsConstructor
@Tag(
    name = "자주 묻는 질문",
    description = """
        자주 묻는 질문 API
        
        * 자주 묻는 질문의 생성, 목록 조회, 상세 조회를 제공합니다.
        * 카테고리별 필터링과 페이지네이션을 지원합니다.
        * 게시글은 등록일시 기준으로 정렬됩니다.
        * 각 게시글은 제목, 내용, 카테고리 등의 정보를 포함합니다.
        """
)
public class OftenQnaController {

    private final OftenQnaService oftenQnaService;

    @PostMapping
    @Operation(
        summary = "자주 묻는 질문 생성",
        description = """
            새로운 자주 묻는 질문을 생성합니다.
            
            * 제목, 내용, 카테고리 정보가 필요합니다.
            * 생성된 게시글의 ID가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "자주 묻는 질문 생성 성공",
            content = @Content(schema = @Schema(implementation = OftenQnaResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<OftenQnaResponseDto> createQna(
            @Parameter(description = "자주 묻는 질문 생성 요청")
            @RequestBody OftenQnaRequestDto requestDto) {
        return ResponseEntity.ok(oftenQnaService.createQna(requestDto));
    }

    @GetMapping
    @Operation(
        summary = "자주 묻는 질문 목록 조회",
        description = """
            자주 묻는 질문 목록을 조회합니다.
            
            * 카테고리별 필터링이 가능합니다.
            * 페이지네이션을 지원합니다.
            * 기본 페이지 크기는 10입니다.
            * 기본 정렬은 등록일시 기준 내림차순입니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "자주 묻는 질문 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<PaginatedResponse<OftenQnaResponseDto>> getQnas(
            @Parameter(description = "카테고리 (선택사항)", example = "PAYMENT")
            @RequestParam(required = false) String category,
            @Parameter(
                description = """
                    페이지 정보
                    * page: 페이지 번호 (0부터 시작)
                    * size: 페이지 크기
                    * sort: 정렬 기준 (예: registerDate,desc)
                    """,
                example = "page=0&size=10&sort=registerDate,desc"
            )
            @PageableDefault(size = 10, sort = "registerDate", direction = Sort.Direction.DESC) Pageable pageable) {
        if (category != null) {
            return ResponseEntity.ok(oftenQnaService.getQnasByCategory(category, pageable));
        }
        return ResponseEntity.ok(oftenQnaService.getQnas(pageable));
    }

    @GetMapping("/{qnaId}")
    @Operation(
        summary = "자주 묻는 질문 조회",
        description = """
            특정 자주 묻는 질문을 조회합니다.
            
            * 게시글 ID로 특정 게시글을 조회합니다.
            * 게시글이 존재하지 않는 경우 404 에러가 발생합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "자주 묻는 질문 조회 성공",
            content = @Content(schema = @Schema(implementation = OftenQnaResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<OftenQnaResponseDto> getQna(
            @Parameter(description = "자주 묻는 질문 ID", example = "1")
            @PathVariable Long qnaId) {
        return ResponseEntity.ok(oftenQnaService.getQna(qnaId));
    }
} 