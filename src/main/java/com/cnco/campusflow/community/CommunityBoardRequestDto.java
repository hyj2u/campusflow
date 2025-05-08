package com.cnco.campusflow.community;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "CommunityBoardRequestDto",
    description = """
        커뮤니티 게시글 요청 DTO
        
        * 게시글 작성/수정 시 필요한 정보를 담습니다.
        * 제목과 내용은 필수 입력 항목입니다.
        * 비밀글 설정이 가능합니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용",
            "secretYn": "N",
            "type": "NOTICE"
        }
        """
)
public class CommunityBoardRequestDto {
    @Schema(description = "게시글 번호 (수정 시에만 사용)", example = "1")
    private Long boardId;

    @NotBlank(message = "제목은 필수 입력 항목입니다")
    @Schema(description = "게시글 제목", example = "게시글 제목", required = true)
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다")
    @Schema(description = "게시글 내용", example = "게시글 내용", required = true)
    private String content;

    @Schema(description = "비밀글 여부 (Y/N)", example = "N")
    private String secretYn;

    @NotNull(message = "게시글 유형은 필수 입력 항목입니다")
    @Schema(description = "게시글 유형 코드", example = "NOTICE", required = true)
    private String type;
}
