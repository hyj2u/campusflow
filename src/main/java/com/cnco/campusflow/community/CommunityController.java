package com.cnco.campusflow.community;

import com.cnco.campusflow.common.CommonResponse;
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
@Tag(
        name = "Community",
        description = """
                커뮤니티 관리 API
                
                * 게시글 작성, 조회, 삭제 기능을 제공합니다.
                * 댓글 작성, 조회, 삭제 기능을 제공합니다.
                * 게시글 신고 기능을 제공합니다.
                * 자유게시판과 QnA 게시판을 지원합니다.
                * JWT 인증이 필요합니다.
                """
)
public class CommunityController {
    private final CommunityService communityService;
    private final ObjectMapper objectMapper;

    @Operation(
            summary = "게시글 작성",
            description = """
                    새로운 게시글을 작성합니다.
                    
                    * 제목과 내용은 필수 입력 항목입니다.
                    * 이미지 첨부가 가능합니다.
                    * JWT 인증이 필요합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글이 성공적으로 작성되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addBoard(
            @Parameter(description = "게시글 정보 (JSON 형식)") @RequestPart String board,
            @Parameter(description = "첨부할 이미지 파일들") @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity user
    ) throws IOException {
        CommunityBoardRequestDto requestDto = objectMapper.readValue(board, CommunityBoardRequestDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addBoard(requestDto, user, images)));
    }

    @Operation(
            summary = "게시글 조회",
            description = """
                    특정 게시글의 상세 정보를 조회합니다.
                    
                    * 게시글 번호로 조회합니다.
                    * 조회수가 증가합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(
            @Parameter(description = "게시글 번호", example = "1") @PathVariable Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoard(boardId)));
    }

    @Operation(
            summary = "게시글 좋아요",
            description = """
                    특정 게시글 좋아요.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 정보가 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다")
    })
    @PutMapping("/{boardId}/like")
    public ResponseEntity<?> likeBoard(
            @Parameter(description = "게시글 번호", example = "2") @PathVariable Long boardId, @AuthenticationPrincipal AppUserEntity appUser
    ) {
        communityService.likeBoard(boardId, appUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "게시글 삭제",
            description = """
                    특정 게시글을 삭제합니다.
                    
                    * 게시글 번호로 삭제합니다.
                    * 작성자만 삭제할 수 있습니다.
                    * JWT 인증이 필요합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "게시글이 성공적으로 삭제되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다"),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(
            @Parameter(description = "게시글 번호", example = "1") @PathVariable Long boardId
    ) {
        communityService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "댓글 작성",
            description = """
                    특정 게시글에 댓글을 작성합니다.
                    
                    * 댓글 내용은 필수 입력 항목입니다.
                    * 답글 작성 시 상위 댓글 ID가 필요합니다.
                    * JWT 인증이 필요합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글이 성공적으로 작성되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다")
    })
    @PostMapping("/{boardId}/reply")
    public ResponseEntity<?> addReply(
            @Parameter(description = "게시글 번호", example = "1") @PathVariable Long boardId,
            @Parameter(description = "댓글 정보") @RequestBody ReplyRequestDto replyDto,
            @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.addReply(boardId, replyDto, user)));
    }

    @Operation(
            summary = "댓글 목록 조회",
            description = """
                    특정 게시글의 댓글 목록을 조회합니다.
                    
                    * 게시글 번호로 조회합니다.
                    * 정렬 순서를 지정할 수 있습니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다")
    })
    @GetMapping("/{boardId}/reply")
    public ResponseEntity<?> addReply(
            @Parameter(description = "게시글 번호", example = "1") @PathVariable Long boardId,
            @Parameter(description = "정렬 순서 (DESC/ASC)", example = "DESC") @RequestParam String order
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getReplies(boardId, order)));
    }

    @Operation(
            summary = "댓글 좋아요",
            description = """
                    특정 댓글 좋아요
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글이 좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다"),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다")
    })
    @PutMapping("/reply/{replyId}/like")
    public ResponseEntity<?> likeReply(
            @Parameter(description = "댓글 번호", example = "1") @PathVariable Long replyId,
            @AuthenticationPrincipal AppUserEntity appuser
    ) {
        communityService.likeReply(replyId, appuser);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "댓글 삭제",
            description = """
                    특정 댓글을 삭제합니다.
                    
                    * 댓글 번호로 삭제합니다.
                    * 작성자만 삭제할 수 있습니다.
                    * JWT 인증이 필요합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글이 성공적으로 삭제되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다"),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다")
    })
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<?> deletReply(
            @Parameter(description = "댓글 번호", example = "1") @PathVariable Long replyId
    ) {
        communityService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "게시글 신고",
            description = """
                    특정 게시글을 신고합니다.
                    
                    * 게시글 번호와 신고 사유는 필수 입력 항목입니다.
                    * JWT 인증이 필요합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글이 성공적으로 신고되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 접근입니다"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다")
    })
    @PostMapping("/report")
    public ResponseEntity<?> addReport(
            @Parameter(description = "신고 정보") @RequestBody ReportDto reportDto,
            @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(communityService.reportBoard(reportDto, user)));
    }

    @Operation(
            summary = "자유게시판 목록 조회",
            description = """
                    자유게시판의 게시글 목록을 조회합니다.
                    
                    * 정렬 순서를 지정할 수 있습니다.
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/free")
    public ResponseEntity<?> getFreeBoards(
            @Parameter(description = "단과대학 ID", example = "1") @RequestParam(required = false) Integer collegeId,
            @Parameter(description = "정렬 순서 (latest, popular)", example = "latest") @RequestParam(required = false) String order,
            @Parameter(description = "검색어", example = "수강신청") @RequestParam(required = false) String search,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getFreeBoards(collegeId, order, search, pageable)));
    }

    @Operation(
            summary = "QnA 게시판 목록 조회",
            description = """
                    QnA 게시판의 게시글 목록을 조회합니다.
                    
                    * 단과대학별 필터링이 가능합니다.
                    * 정렬 순서를 지정할 수 있습니다.
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/qna")
    public ResponseEntity<?> getQnABoards(
            @Parameter(description = "단과대학 ID", example = "1") @RequestParam(required = false) Integer collegeId,
            @Parameter(description = "정렬 순서 (latest, popular)", example = "latest") @RequestParam(required = false) String order,
            @Parameter(description = "검색어", example = "수강신청") @RequestParam(required = false) String search,
            @Parameter (hidden = true) @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getQnABoards(collegeId, order, search, pageable)));
    }

    @Operation(
            summary = "내가 쓴글 목록 조회",
            description = """
                    내가쓴 게시판의 게시글 목록을 조회합니다.
                    
                    * 내가 쓴 게시판 글 목록 조회
                    * 정렬 순서는 최신순
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/my")
    public ResponseEntity<?> getMyBoards(
            @AuthenticationPrincipal AppUserEntity user,
            @Parameter(hidden = true)
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getMyBoard(user, pageable)));
    }

    @Operation(
            summary = "내가 댓글단 게시글 목록 조회",
            description = """
                    내가 댓글 단 게시판의 게시글 목록을 조회합니다.
                    
                    * codeCd의 따라 적용 free/qna
                    * free 인경우 내가 댓글 단 게시글
                    * qna 인 경우 내가 답변한 게시글
                    * 정렬 순서는 최신순
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/my/reply")
    public ResponseEntity<?> getBoardsWithMyReply(
            @AuthenticationPrincipal AppUserEntity user,
            @Parameter(description = "게시판 유형", example = "free") @RequestParam String codeCd,
            @Parameter(hidden = true)  @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoardWithMyReplies(user, codeCd, pageable)));
    }

    @Operation(
            summary = "내가 좋아요한 게시글 목록 조회",
            description = """
                    내가 좋아요한 게시판의 게시글 목록을 조회합니다.
                    
                    
                    * 정렬 순서는 최신순
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/my/like")
    public ResponseEntity<?> getBoardsWithMyLike(
            @AuthenticationPrincipal AppUserEntity user,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoardWithMyLikes(user, pageable)));
    }

    @Operation(
            summary = "내가 좋아요한 댓글 목록 조회",
            description = """
                    내가 좋아요한 댓글 목록을 조회합니다.
                    
                    
                    * 정렬 순서는 최신순
                    * 페이지네이션을 지원합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 조회되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값입니다")
    })
    @GetMapping("/reply/my/like")
    public ResponseEntity<?> getRepliesWithMyLike(
            @AuthenticationPrincipal AppUserEntity user,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(communityService.getBoardWithMyLikes(user, pageable)));
    }
}