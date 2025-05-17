package com.cnco.campusflow.eventboard;

import com.cnco.campusflow.common.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "이벤트 게시판", description = "이벤트 게시판 관련 API")
public class EventBoardController {

    private final EventBoardService eventBoardService;

    @GetMapping("/{eventId}")
    @Operation(summary = "이벤트 게시글 조회", description = "특정 이벤트 게시글을 조회합니다.")
    public ResponseEntity<EventBoardResponseDto> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventBoardService.getEvent(eventId));
    }

    @GetMapping
    @Operation(summary = "이벤트 게시글 목록 조회", description = "이벤트 게시글 목록을 페이지네이션과 함께 조회합니다.")
    public ResponseEntity<PaginatedResponse<EventBoardResponseDto>> getEvents(Pageable pageable) {
        return ResponseEntity.ok(eventBoardService.getEvents(pageable));
    }
} 