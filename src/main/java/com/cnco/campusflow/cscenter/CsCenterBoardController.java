package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cscenter")
@RequiredArgsConstructor
@Tag(
    name = "고객센터",
    description = """
        고객센터 API
        
        * 고객센터 게시글의 생성, 수정, 삭제, 조회를 제공합니다.
        * 게시글에 대한 댓글 기능을 제공합니다.
        * 페이지네이션을 지원하여 대량의 게시글을 효율적으로 조회할 수 있습니다.
        * 게시글은 등록일시 기준으로 정렬됩니다.
        * 각 게시글은 제목, 내용, 이미지, 등록일시 등의 정보를 포함합니다.
        * 댓글에 대한 도움이 되었나요(Y/N) 기능을 제공합니다.
        """
)
public class CsCenterBoardController {

    private final CsCenterBoardService csCenterBoardService;
    private final CsCenterReplyService csCenterReplyService;

    @PostMapping
    @Operation(
        summary = "고객센터 게시글 생성",
        description = """
            새로운 고객센터 게시글을 생성합니다.
            
            * 제목, 내용, 이미지 정보가 필요합니다.
            * 이미지는 선택사항입니다.
            * 생성된 게시글의 ID가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "고객센터 게시글 생성 성공",
            content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))
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
    public ResponseEntity<CsCenterBoardResponseDto> createBoard(
            @Parameter(description = "고객센터 게시글 생성 요청")
            @RequestPart("board") CsCenterBoardRequestDto requestDto,
            @Parameter(description = "이미지 파일 목록 (선택사항)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser) throws IOException {
        return ResponseEntity.ok(csCenterBoardService.createBoard(requestDto, appUser, images));
    }

    @PutMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 수정",
        description = """
            기존 고객센터 게시글을 수정합니다.
            
            * 제목, 내용, 이미지 정보를 수정할 수 있습니다.
            * 이미지는 선택사항입니다.
            * 수정된 게시글의 정보가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "고객센터 게시글 수정 성공",
            content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
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
    public ResponseEntity<CsCenterBoardResponseDto> updateBoard(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long boardId,
            @Parameter(description = "고객센터 게시글 수정 요청")
            @RequestPart("board") CsCenterBoardRequestDto requestDto,
            @Parameter(description = "이미지 파일 목록 (선택사항)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        requestDto.setBoardId(boardId);
        return ResponseEntity.ok(csCenterBoardService.updateBoard(requestDto, images));
    }

    @DeleteMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 삭제",
        description = """
            고객센터 게시글을 삭제합니다.
            
            * 게시글 ID로 특정 게시글을 삭제합니다.
            * 게시글과 관련된 이미지도 함께 삭제됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "고객센터 게시글 삭제 성공"
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
    public ResponseEntity<Void> deleteBoard(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long boardId) {
        csCenterBoardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 조회",
        description = """
            특정 고객센터 게시글을 조회합니다.
            
            * 게시글 ID로 특정 게시글을 조회합니다.
            * 게시글이 존재하지 않는 경우 404 에러가 발생합니다.
            * 게시글의 조회수가 증가합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "고객센터 게시글 조회 성공",
            content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))
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
    public ResponseEntity<CsCenterBoardResponseDto> getBoard(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long boardId) {
        return ResponseEntity.ok(csCenterBoardService.getBoard(boardId));
    }

    @GetMapping
    @Operation(
        summary = "고객센터 게시글 목록 조회",
        description = """
            고객센터 게시글 목록을 조회합니다.
            
            * 페이지네이션을 지원합니다.
            * 기본 페이지 크기는 10입니다.
            * 기본 정렬은 등록일시 기준 내림차순입니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "고객센터 게시글 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getBoards(
            @Parameter(
                description = """
                    페이지 정보
                    * page: 페이지 번호 (0부터 시작)
                    * size: 페이지 크기
                    * sort: 정렬 기준 (예: insertTimestamp,desc)
                    """,
                example = "page=0&size=10&sort=insertTimestamp,desc"
            )
            @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(csCenterBoardService.getBoards(pageable));
    }

    @GetMapping("/my")
    @Operation(
        summary = "내 고객센터 게시글 목록 조회",
        description = """
            로그인한 사용자의 고객센터 게시글 목록을 조회합니다.
            
            * 페이지네이션을 지원합니다.
            * 기본 페이지 크기는 10입니다.
            * 기본 정렬은 등록일시 기준 내림차순입니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "내 고객센터 게시글 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getMyBoards(
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(
                description = """
                    페이지 정보
                    * page: 페이지 번호 (0부터 시작)
                    * size: 페이지 크기
                    * sort: 정렬 기준 (예: insertTimestamp,desc)
                    """,
                example = "page=0&size=10&sort=insertTimestamp,desc"
            )
            @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(csCenterBoardService.getMyBoards(appUser.getAppUserId(), pageable));
    }

    @PostMapping("/{boardId}/reply")
    @Operation(
        summary = "댓글 작성",
        description = """
            고객센터 게시글에 댓글을 작성합니다.
            
            * 댓글 내용이 필요합니다.
            * 작성된 댓글의 정보가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "댓글 작성 성공",
            content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
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
    public ResponseEntity<CsCenterReplyResponseDto> addReply(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long boardId,
            @Parameter(description = "댓글 작성 요청")
            @RequestBody CsCenterReplyRequestDto dto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.addReply(boardId, dto, user));
    }

    @PutMapping("/reply/{replyId}")
    @Operation(
        summary = "댓글 수정",
        description = """
            기존 댓글을 수정합니다.
            
            * 댓글 내용을 수정할 수 있습니다.
            * 수정된 댓글의 정보가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "댓글 수정 성공",
            content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "댓글을 찾을 수 없음"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<CsCenterReplyResponseDto> updateReply(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long replyId,
            @Parameter(description = "댓글 수정 요청")
            @RequestBody CsCenterReplyRequestDto dto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.updateReply(replyId, dto, user));
    }

    @DeleteMapping("/reply/{replyId}")
    @Operation(
        summary = "댓글 삭제",
        description = """
            댓글을 삭제합니다.
            
            * 댓글 ID로 특정 댓글을 삭제합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "댓글 삭제 성공"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "댓글을 찾을 수 없음"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<Void> deleteReply(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long replyId,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        csCenterReplyService.deleteReply(replyId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}/reply")
    @Operation(
        summary = "댓글 목록 조회",
        description = """
            게시글의 댓글 목록을 조회합니다.
            
            * 게시글 ID로 해당 게시글의 댓글 목록을 조회합니다.
            * 정렬 순서를 지정할 수 있습니다 (기본값: DESC).
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "댓글 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))
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
    public ResponseEntity<List<CsCenterReplyResponseDto>> getReplies(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long boardId,
            @Parameter(
                description = """
                    정렬 순서
                    * ASC: 오름차순
                    * DESC: 내림차순
                    """,
                example = "DESC"
            )
            @RequestParam(defaultValue = "DESC") String order) {
        return ResponseEntity.ok(csCenterReplyService.getReplies(boardId, order));
    }

    @PutMapping("/reply/{replyId}/helpful")
    @Operation(
        summary = "댓글 도움이 되었나요(Y/N)",
        description = """
            댓글이 도움이 되었는지 여부를 설정합니다.
            
            * Y: 도움이 됨
            * N: 도움이 안 됨
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "댓글 도움이 되었나요 설정 성공",
            content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "댓글을 찾을 수 없음"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    public ResponseEntity<CsCenterReplyResponseDto> setHelpfulYn(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long replyId,
            @Schema(
                description = """
                    도움이 되었나요 여부
                    * Y: 도움이 됨
                    * N: 도움이 안 됨
                    """,
                example = "Y",
                allowableValues = {"Y", "N"}
            )
            @RequestParam String helpfulYn,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.setHelpfulYn(replyId, helpfulYn, user));
    }
} 