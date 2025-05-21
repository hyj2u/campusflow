package com.cnco.campusflow.eventboard;

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
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(
    name = "이벤트 게시판",
    description = """
        이벤트 게시판 API
        
        * 이벤트 게시글의 목록 조회와 상세 조회를 제공합니다.
        * 페이지네이션을 지원하여 대량의 게시글을 효율적으로 조회할 수 있습니다.
        * 게시글은 등록일시 기준으로 정렬됩니다.
        * 각 게시글은 제목, 내용, 이미지, 시작일시, 종료일시 등의 정보를 포함합니다.
        """
)
public class EventBoardController {

    private final EventBoardService eventBoardService;

    @GetMapping("/{eventId}")
    @Operation(
        summary = "이벤트 게시글 조회",
        description = """
            특정 이벤트 게시글을 조회합니다.
            
            * 게시글 ID로 특정 게시글을 조회합니다.
            * 게시글이 존재하지 않는 경우 404 에러가 발생합니다.
            * 게시글의 조회수가 증가합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "이벤트 게시글 조회 성공",
            content = @Content(schema = @Schema(implementation = EventBoardResponseDto.class))
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
    public ResponseEntity<EventBoardResponseDto> getEvent(
            @Parameter(description = "이벤트 게시글 ID", example = "22")
            @PathVariable Long eventId) {
        return ResponseEntity.ok(eventBoardService.getEvent(eventId));
    }

    @GetMapping
    @Operation(
        summary = "이벤트 게시글 목록 조회",
        description = """
            이벤트 게시글 목록을 페이지네이션과 함께 조회합니다.
            * storeId(매장ID)로 매핑된 이벤트만 조회 가능
            * checkEndDate(Y/N)로 종료/진행중 이벤트만 조회 가능
            * page, size, sort 파라미터로 페이징/정렬 가능
            * 예시: /event?storeId=1&checkEndDate=Y&page=0&size=5&sort=insertTimestamp,desc
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "이벤트 게시글 목록 조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<PaginatedResponse<EventBoardResponseDto>> getEvents(
        @Parameter(description = "매장 ID(store_id)", example = "1") @RequestParam(required = false) Long storeId,
        @Parameter(description = "이벤트 종료여부(Y: 종료, N: 진행중)", example = "Y") @RequestParam(required = false) String checkEndDate,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(eventBoardService.getEvents(pageable, storeId, checkEndDate));
    }
} 