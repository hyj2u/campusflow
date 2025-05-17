package com.cnco.campusflow.notice;

import com.cnco.campusflow.common.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice/member")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping
    public PaginatedResponse<NoticeBoardResponseDto> getNotices(Pageable pageable) {
        return noticeBoardService.getNotices(pageable);
    }

    @GetMapping("/{noticeId}")
    public NoticeBoardResponseDto getNotice(@PathVariable Long noticeId) {
        return noticeBoardService.getNotice(noticeId);
    }
} 