package com.cnco.campusflow.notice;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice/member")
@RequiredArgsConstructor
@Tag(
    name = "공지사항",
    description = """
        공지사항 API
        
        * 공지사항의 목록 조회와 상세 조회를 제공합니다.
        * 페이지네이션을 지원하여 대량의 게시글을 효율적으로 조회할 수 있습니다.
        * 게시글은 등록일시 기준으로 정렬됩니다.
        * 각 게시글은 제목, 내용, 이미지, 등록일시 등의 정보를 포함합니다.
        """
)
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping
    @Operation(
        summary = "공지사항 목록 조회",
        description = """
            공지사항 목록을 페이지네이션과 함께 조회합니다.
            
            * 페이지 번호는 0부터 시작합니다.
            * 기본 페이지 크기는 10입니다.
            * 기본 정렬은 등록일시 기준 내림차순입니다.
            * 정렬 기준을 변경할 수 있습니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "공지사항 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public PaginatedResponse<NoticeBoardResponseDto> getNotices(
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticeBoardService.getNotices(pageable);
    }

    @GetMapping("/{noticeId}")
    @Operation(
        summary = "공지사항 조회",
        description = """
            특정 공지사항을 조회합니다.
            
            * 게시글 ID로 특정 게시글을 조회합니다.
            * 게시글이 존재하지 않는 경우 404 에러가 발생합니다.
            * 게시글의 조회수가 증가합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "공지사항 조회 성공",
            content = @Content(schema = @Schema(implementation = NoticeBoardResponseDto.class))
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
    public NoticeBoardResponseDto getNotice(
            @Parameter(description = "공지사항 ID", example = "1")
            @PathVariable Long noticeId) {
        return noticeBoardService.getNotice(noticeId);
    }
} 