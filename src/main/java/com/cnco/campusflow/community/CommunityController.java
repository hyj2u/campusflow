package com.cnco.campusflow.community;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addBoard(@RequestPart CommunityBoardRequestDto board, @RequestPart(value = "images", required = false)
    List<MultipartFile> images,  @AuthenticationPrincipal AppUserEntity user) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addBoard(board, user, images)));
    }
    @GetMapping ("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoard(boardId)));
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
}
