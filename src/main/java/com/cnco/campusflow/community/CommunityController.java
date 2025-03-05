package com.cnco.campusflow.community;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/comm")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addBoard(@RequestPart String board, @RequestPart(value = "images", required = false)
    List<MultipartFile> images,  @AuthenticationPrincipal AppUserEntity user) throws IOException {
        CommunityBoardRequestDto requestDto =objectMapper.readValue(board, CommunityBoardRequestDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addBoard(requestDto, user, images)));
    }
    @GetMapping ("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoard(boardId)));
    }
    @DeleteMapping ("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        communityService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping ("/{boardId}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long boardId, @RequestBody ReplyRequestDto replyDto, @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addReply(boardId, replyDto, user)));
    }
    @GetMapping ("/{boardId}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long boardId, @RequestParam String order) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getReplies(boardId, order)));
    }
    @DeleteMapping ("/reply/{replyId}")
    public ResponseEntity<?> deletReply(@PathVariable Long replyId) {
        communityService.deleteReply(replyId);
        return ResponseEntity.noContent().build();

    }
    @PostMapping ("/report")
    public ResponseEntity<?> addReport(@RequestBody ReportDto reportDto, @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.reportBoard(reportDto, user)));
    }
    @GetMapping ("/free")
    public ResponseEntity<?> getFreeBoards(@RequestParam String order, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getFreeBoards(order, pageable)));
    }

}
