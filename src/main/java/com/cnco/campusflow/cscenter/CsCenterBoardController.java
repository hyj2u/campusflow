package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cscenter")
@RequiredArgsConstructor
public class CsCenterBoardController {

    private final CsCenterBoardService csCenterBoardService;
    private final CsCenterReplyService csCenterReplyService;

    @PostMapping
    public ResponseEntity<CsCenterBoardResponseDto> createBoard(
            @RequestPart("board") CsCenterBoardRequestDto requestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal AppUserEntity appUser) throws IOException {
        return ResponseEntity.ok(csCenterBoardService.createBoard(requestDto, appUser, images));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<CsCenterBoardResponseDto> updateBoard(
            @PathVariable Long boardId,
            @RequestPart("board") CsCenterBoardRequestDto requestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        requestDto.setBoardId(boardId);
        return ResponseEntity.ok(csCenterBoardService.updateBoard(requestDto, images));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        csCenterBoardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CsCenterBoardResponseDto> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(csCenterBoardService.getBoard(boardId));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getBoards(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(csCenterBoardService.getBoards(pageable));
    }

    @GetMapping("/my")
    public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getMyBoards(
            @AuthenticationPrincipal AppUserEntity appUser,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(csCenterBoardService.getMyBoards(appUser.getAppUserId(), pageable));
    }

    // 댓글 작성
    @PostMapping("/{boardId}/reply")
    public ResponseEntity<CsCenterReplyResponseDto> addReply(
            @PathVariable Long boardId,
            @RequestBody CsCenterReplyRequestDto dto,
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.addReply(boardId, dto, user));
    }

    // 댓글 수정
    @PutMapping("/reply/{replyId}")
    public ResponseEntity<CsCenterReplyResponseDto> updateReply(
            @PathVariable Long replyId,
            @RequestBody CsCenterReplyRequestDto dto,
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.updateReply(replyId, dto, user));
    }

    // 댓글 삭제
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal AppUserEntity user) {
        csCenterReplyService.deleteReply(replyId, user);
        return ResponseEntity.ok().build();
    }

    // 댓글 목록 조회
    @GetMapping("/{boardId}/reply")
    public ResponseEntity<List<CsCenterReplyResponseDto>> getReplies(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "DESC") String order) {
        return ResponseEntity.ok(csCenterReplyService.getReplies(boardId, order));
    }

    // 댓글 도움이 되었나요(Y/N)
    @PutMapping("/reply/{replyId}/helpful")
    public ResponseEntity<CsCenterReplyResponseDto> setHelpfulYn(
            @PathVariable Long replyId,
            @RequestParam String helpfulYn,
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.setHelpfulYn(replyId, helpfulYn, user));
    }
} 