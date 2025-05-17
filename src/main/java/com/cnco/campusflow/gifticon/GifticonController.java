package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "기프티콘", description = "기프티콘 API")
@RestController
@RequestMapping("/gifticon")
@RequiredArgsConstructor
public class GifticonController {
    private final GifticonService gifticonService;

    @Operation(summary = "기프티콘 목록 조회", description = "로그인한 사용자의 기프티콘 목록을 조회합니다.")
    @GetMapping
    public PaginatedResponse<GifticonResponseDto> getGifticonList(
            @AuthenticationPrincipal AppUserEntity appUser,
            @RequestParam(required = false, defaultValue = "Y") String activeYn,
            @RequestParam(required = false) String type,
            @PageableDefault(size = 10) Pageable pageable) {

        // 서비스 호출하여 데이터 조회
        Page<GifticonResponseDto> gifticons = gifticonService.getGifticonList(appUser, type, activeYn, pageable);

        PaginatedResponse<GifticonResponseDto> response = new PaginatedResponse<>(
                gifticons.getContent(),
                gifticons.getNumber(),
                gifticons.getSize(),
                gifticons.getTotalElements(),
                gifticons.getTotalPages()
        );
        return response;
    }
} 