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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

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
        * 각 게시글은 내용, 이미지, 등록일시 등의 정보를 포함합니다.
        * 댓글에 대한 도움이 되었나요(Y/N) 기능을 제공합니다.
        * codeCd 가능한 값
            - ACCOUNT_LOGIN - 로그인 관련
            - ACCOUNT_AUTH - 인증 관련
            - ACCOUNT_EXIT - 탈퇴 관련
            - ACCOUNT_OTHER - 기타 계정 관련
        """
)
public class CsCenterBoardController {

    private final CsCenterBoardService csCenterBoardService;
    private final CsCenterReplyService csCenterReplyService;

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(
        summary = "고객센터 게시글 생성",
        description = """
            새로운 고객센터 게시글을 생성합니다.
            
            * content(내용), codeCd(문의유형)는 필수입니다.
            * images(이미지)는 선택입니다.
            * 생성된 게시글의 상세 정보가 반환됩니다.
            * 예시 요청(JSON): {\"content\":\"로그인이 안됩니다.\",\"codeCd\":\"ACCOUNT_LOGIN\"}
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "고객센터 게시글 생성 성공",
            content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))
        ),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterBoardResponseDto> createBoard(
            @Parameter(description = "고객센터 게시글 생성 요청 (JSON, content/codeCd 필수)", required = true)
            @Schema(type = "string", format = "json", example = "{\"content\":\"로그인이 안됩니다.\",\"codeCd\":\"ACCOUNT_LOGIN\"}")
            @RequestPart("board") String board,
            @Parameter(description = "이미지 파일 목록 (선택)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CsCenterBoardRequestDto requestDto = objectMapper.readValue(board, CsCenterBoardRequestDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(csCenterBoardService.createBoard(requestDto, appUser, images));
    }

    @PutMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 수정",
        description = """
            기존 고객센터 게시글을 수정합니다.
            
            * content(내용), codeCd(문의유형) 필수
            * images(이미지) 선택
            * 수정된 게시글의 상세 정보가 반환됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "고객센터 게시글 수정 성공", content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterBoardResponseDto> updateBoard(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "고객센터 게시글 수정 요청 (JSON, content/codeCd 필수)", required = true)
            @RequestPart("board") CsCenterBoardRequestDto requestDto,
            @Parameter(description = "이미지 파일 목록 (선택)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return ResponseEntity.ok(csCenterBoardService.updateBoard(boardId, requestDto, images));
    }

    @DeleteMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 삭제",
        description = "게시글 ID로 특정 게시글을 삭제합니다. 실제 삭제가 아닌 deleteYn='Y'로 처리됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "고객센터 게시글 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Void> deleteBoard(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId) {
        csCenterBoardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    @Operation(
        summary = "고객센터 게시글 조회",
        description = "게시글 ID로 특정 게시글을 상세 조회합니다. 조회수(viewCnt)가 1 증가합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "고객센터 게시글 조회 성공", content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterBoardResponseDto> getBoard(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId) {
        return ResponseEntity.ok(csCenterBoardService.getBoard(boardId));
    }

    // @GetMapping
    // @Operation(
    //     summary = "고객센터 게시글 목록 조회",
    //     description = """
    //         고객센터 게시글 목록을 조회합니다.
            
    //         * 페이지네이션을 지원합니다.
    //         * 기본 페이지 크기는 10입니다.
    //         * 기본 정렬은 등록일시 기준 내림차순입니다.
    //         """
    // )
    // @ApiResponses(value = {
    //     @ApiResponse(
    //         responseCode = "200",
    //         description = "고객센터 게시글 목록 조회 성공",
    //         content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
    //     ),
    //     @ApiResponse(
    //         responseCode = "500",
    //         description = "서버 내부 오류"
    //     )
    // })
    // public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getBoards(
    //         @Parameter(hidden = true)
    //         @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
    //     return ResponseEntity.ok(csCenterBoardService.getBoards(pageable));
    // }

    @GetMapping("/my")
    @Operation(
        summary = "내 고객센터 게시글 목록 조회",
        description = "로그인한 사용자의 고객센터 게시글 목록을 페이지네이션과 함께 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 고객센터 게시글 목록 조회 성공", content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<PaginatedResponse<CsCenterBoardResponseDto>> getMyBoards(
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(description = "페이지 정보(page, size, sort)", example = "page=0&size=10&sort=insertTimestamp,desc")
            @PageableDefault(size = 10, sort = "insertTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(csCenterBoardService.getMyBoards(appUser.getAppUserId(), pageable));
    }

    @PostMapping("/{boardId}/reply")
    @Operation(
        summary = "댓글 작성",
        description = "고객센터 게시글에 댓글을 작성합니다. content(내용) 필수."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterReplyResponseDto> addReply(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "댓글 작성 요청 (content 필수)")
            @RequestBody CsCenterReplyRequestDto dto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.addReply(boardId, dto, user));
    }

    @PutMapping("/reply/{replyId}")
    @Operation(
        summary = "댓글 수정",
        description = "기존 댓글을 수정합니다. content(내용) 필수."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterReplyResponseDto> updateReply(
            @Parameter(description = "댓글 ID", example = "1", required = true)
            @PathVariable Long replyId,
            @Parameter(description = "댓글 수정 요청 (content 필수)")
            @RequestBody CsCenterReplyRequestDto dto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterReplyService.updateReply(replyId, dto, user));
    }

    @DeleteMapping("/reply/{replyId}")
    @Operation(
        summary = "댓글 삭제",
        description = "댓글 ID로 특정 댓글을 삭제합니다. 실제 삭제가 아닌 deleteYn='Y'로 처리됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<Void> deleteReply(
            @Parameter(description = "댓글 ID", example = "1", required = true)
            @PathVariable Long replyId,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        csCenterReplyService.deleteReply(replyId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}/reply")
    @Operation(
        summary = "댓글 목록 조회",
        description = "게시글 ID로 해당 게시글의 댓글 목록을 조회합니다. order(DESC/ASC) 지정 가능. 기본 DESC."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", content = @Content(schema = @Schema(implementation = CsCenterReplyResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<List<CsCenterReplyResponseDto>> getReplies(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "정렬 순서(ASC/DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") String order) {
        return ResponseEntity.ok(csCenterReplyService.getReplies(boardId, order));
    }

    @PutMapping("/{boardId}/helpful")
    @Operation(
        summary = "게시글 도움이 되었나요(Y/N) 설정",
        description = """
            게시글이 도움이 되었는지 여부를 설정합니다.
            * helpfulYn(Y/N)은 쿼리 파라미터로,
            * noHelpfulReason(도움이 안된 이유)은 body(raw JSON)로 입력합니다.
            * 예시: PUT /cscenter/48/helpful?helpfulYn=N, Body: {\"noHelpfulReason\":\"답변이 불충분함\"}
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 도움이 되었나요 설정 성공", content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterBoardResponseDto> setHelpfulYn(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "도움이 되었나요 여부(Y/N)", example = "N", required = true)
            @RequestParam String helpfulYn,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "도움이 안된 이유(noHelpfulReason) (helpfulYn이 N일 때만 입력)",
                required = false,
                content = @Content(schema = @Schema(implementation = HelpfulRequestDto.class))
            )
            @RequestBody(required = false) HelpfulRequestDto body,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        String noHelpfulReason = (body != null) ? body.getNoHelpfulReason() : null;
        return ResponseEntity.ok(csCenterBoardService.setHelpfulYn(boardId, helpfulYn, noHelpfulReason, user));
    }

    @PutMapping("/{boardId}/active")
    @Operation(
        summary = "게시글 답변처리 완료 여부(activeYn) 설정",
        description = "게시글의 답변처리 완료 여부(activeYn)를 Y/N으로 설정합니다. 예시: PUT /cscenter/48/active?activeYn=Y"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "답변처리 완료 여부 설정 성공", content = @Content(schema = @Schema(implementation = CsCenterBoardResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CsCenterBoardResponseDto> setActiveYn(
            @Parameter(description = "게시글 ID", example = "1", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "답변처리 완료 여부(Y/N)", example = "Y", required = true)
            @RequestParam String activeYn,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity user) {
        return ResponseEntity.ok(csCenterBoardService.setActiveYn(boardId, activeYn, user));
    }

    @Data
    public static class HelpfulRequestDto {
        private String noHelpfulReason;
    }
} 