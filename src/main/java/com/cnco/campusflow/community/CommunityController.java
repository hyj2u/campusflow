package com.cnco.campusflow.community;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/comm")
@RequiredArgsConstructor
@Tag(name = "Community", description = "Community management APIs")
public class CommunityController {
    private final CommunityService communityService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Add board", description = "Creates a new board with optional images.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Board added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addBoard(
        @Parameter(description = "Board details") @RequestPart String board,
        @Parameter(description = "Images for the board") @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity user
    ) throws IOException {
        CommunityBoardRequestDto requestDto = objectMapper.readValue(board, CommunityBoardRequestDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addBoard(requestDto, user, images)));
    }

    @Operation(summary = "Get board", description = "Retrieves the details of a specific board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Board details retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(
        @Parameter(description = "Board ID") @PathVariable Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoard(boardId)));
    }

    @Operation(summary = "Delete board", description = "Deletes a specific board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Board deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(
        @Parameter(description = "Board ID") @PathVariable Long boardId
    ) {
        communityService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add reply", description = "Adds a reply to a specific board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reply added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/{boardId}/reply")
    public ResponseEntity<?> addReply(
        @Parameter(description = "Board ID") @PathVariable Long boardId,
        @Parameter(description = "Reply details") @RequestBody ReplyRequestDto replyDto,
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addReply(boardId, replyDto, user)));
    }

    @Operation(summary = "Get replies", description = "Retrieves the replies for a specific board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Replies retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{boardId}/reply")
    public ResponseEntity<?> addReply(
        @Parameter(description = "Board ID") @PathVariable Long boardId,
        @Parameter(description = "Order of replies") @RequestParam String order
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getReplies(boardId, order)));
    }

    @Operation(summary = "Delete reply", description = "Deletes a specific reply.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reply deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<?> deletReply(
        @Parameter(description = "Reply ID") @PathVariable Long replyId
    ) {
        communityService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add report", description = "Reports a board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Report added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/report")
    public ResponseEntity<?> addReport(
        @Parameter(description = "Report details") @RequestBody ReportDto reportDto,
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.reportBoard(reportDto, user)));
    }

    @Operation(summary = "Get free boards", description = "Retrieves the free boards based on the provided order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Free boards retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/free")
    public ResponseEntity<?> getFreeBoards(
        @Parameter(description = "Order of boards") @RequestParam String order,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getFreeBoards(order, pageable)));
    }

    @Operation(summary = "Get QnA boards", description = "Retrieves the QnA boards based on the provided criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "QnA boards retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/qna")
    public ResponseEntity<?> getQnABoards(
        @Parameter(description = "College ID") @RequestParam(required = false) Integer collegeId,
        @Parameter(description = "Order of boards") @RequestParam String order,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getQnABoards(collegeId, order, pageable)));
    }
}
