package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsCenterReplyService {
    private final CsCenterReplyRepository replyRepository;
    private final CsCenterBoardRepository boardRepository;

    @Transactional
    public CsCenterReplyResponseDto addReply(Long boardId, CsCenterReplyRequestDto dto, AppUserEntity user) {
        CsCenterBoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        CsCenterReplyEntity reply = new CsCenterReplyEntity();
        reply.setBoard(board);
        reply.setContent(dto.getContent());
        reply.setAppUser(user);
        reply.setLevel(dto.getUpTreeId() == null ? 0 : 1);
        reply.setUpTreeId(dto.getUpTreeId());
        reply.setLikeCnt(0);
        reply.setDeleteYn("N");
        reply.setBlindYn("N");
        reply.setHelpfulYn("N");
        CsCenterReplyEntity saved = replyRepository.save(reply);
        return toResponseDto(saved);
    }

    @Transactional
    public CsCenterReplyResponseDto updateReply(Long replyId, CsCenterReplyRequestDto dto, AppUserEntity user) {
        CsCenterReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        if (!reply.getAppUser().getAppUserId().equals(user.getAppUserId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        reply.setContent(dto.getContent());
        // upTreeId는 댓글 생성 시에만 설정되고, 수정 시에는 절대 변경하지 않음
        return toResponseDto(reply);
    }

    @Transactional
    public void deleteReply(Long replyId, AppUserEntity user) {
        CsCenterReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        if (!reply.getAppUser().getAppUserId().equals(user.getAppUserId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        reply.setDeleteYn("Y");
    }

    @Transactional(readOnly = true)
    public List<CsCenterReplyResponseDto> getReplies(Long boardId, String order) {
        List<CsCenterReplyEntity> replies = "ASC".equalsIgnoreCase(order)
                ? replyRepository.findAllByBoardBoardIdOrderByInsertTimestampAsc(boardId)
                : replyRepository.findAllByBoardBoardIdOrderByInsertTimestampDesc(boardId);
        return replies.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public CsCenterReplyResponseDto setHelpfulYn(Long replyId, String helpfulYn, AppUserEntity user) {
        CsCenterReplyEntity reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        // 본인 또는 관리자만 변경 가능하게 하려면 조건 추가 가능
        reply.setHelpfulYn(helpfulYn);
        return toResponseDto(reply);
    }

    private CsCenterReplyResponseDto toResponseDto(CsCenterReplyEntity entity) {
        return new CsCenterReplyResponseDto(
                entity.getContent(),
                entity.getUpTreeId(),
                entity.getAppUser().getAppUserId(),
                entity.getLevel(),
                entity.getAppUser().getNickname(),
                entity.getInsertTimestamp(),
                entity.getReplyId(),
                entity.getDeleteYn(),
                entity.getBlindYn(),
                entity.getHelpfulYn()
        );
    }
} 