package com.cnco.campusflow.community;

import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {

    private final CommunityBoardRepository communityBoardRepository;
    private final CodeRepository codeRepository;
    private final ReplyRepository replyRepository;
    private final ReportRepository reportRepository;
    private final FileUtil fileUtil;
    @Value("${image.base.path}")
    private String imageBasePath; // Properties에서 이미지 경로 주입
    @Value("${image.base.url}")
    private String imageBaseUrl;

    public CommunityBoardResponseDto addBoard(CommunityBoardRequestDto board, AppUserEntity appUser, List<MultipartFile> images) throws IOException {
        CommunityBoardEntity boardEntity;
        List<ImageEntity> imageEntities;
        if (board.getBoardId() == null) {
            boardEntity = new CommunityBoardEntity();
            imageEntities = new ArrayList<>();
        } else {
            boardEntity = communityBoardRepository.findById(board.getBoardId()).orElseThrow(()
                    -> new IllegalArgumentException("유효하지 않은 게시글 Id 입니다."));
            imageEntities = boardEntity.getImages();
            imageEntities.clear();
        }
        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());
        boardEntity.setAppUser(appUser);
        boardEntity.setSecretYn(board.getSecretYn());
        boardEntity.setViewCnt(0);
        boardEntity.setLikeCnt(0);
        boardEntity.setBoardType(codeRepository.findByCodeCd(board.getType())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 게시판 타입입니다.")));

        if (images != null) {
            for (MultipartFile image : images) {
                String fileName = fileUtil.saveFile(imageBasePath, image);
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setImgNm(image.getOriginalFilename());
                imageEntity.setImgPath(imageBasePath + "/" + fileName);
                imageEntities.add(imageEntity);
            }
            boardEntity.setImages(imageEntities);
        }
        communityBoardRepository.save(boardEntity);
        CommunityBoardResponseDto dto = convertEntityToDto(boardEntity);
        dto.setNickname(appUser.getNickname());
        dto.setAppUserId(appUser.getAppUserId());
        return dto;
    }

    public CommunityBoardResponseDto getBoard(Long boardId) {
        CommunityBoardEntity boardEntity = communityBoardRepository.findById(boardId).get();
        boardEntity.setViewCnt(boardEntity.getViewCnt() + 1);
        CommunityBoardResponseDto dto = convertEntityToDto(boardEntity);
        dto.setNickname(boardEntity.getAppUser().getNickname());
        dto.setAppUserId(boardEntity.getAppUser().getAppUserId());
        dto.setCollegeAdmissionYear(boardEntity.getAppUser().getCollegeAdmissionYear());
        return dto;
    }
    public void likeBoard(Long boardId) {
        CommunityBoardEntity boardEntity = communityBoardRepository.findById(boardId).get();
        boardEntity.setLikeCnt(boardEntity.getLikeCnt() + 1);
        communityBoardRepository.save(boardEntity);
    }

    public void deleteBoard(Long boardId) {
        replyRepository.deleteAllByBoardBoardId(boardId);
        reportRepository.deleteAllByBoardBoardId(boardId);
        communityBoardRepository.deleteById(boardId);
    }

    public Long reportBoard(ReportDto reportDto, AppUserEntity appUser) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReason(reportDto.getReason());
        if(reportDto.getBoardId() != null) {
            reportEntity.setBoard(communityBoardRepository.findById(reportDto.getBoardId()).get());
        }
        if(reportDto.getReplyId() != null) {
            reportEntity.setReply(replyRepository.findById(reportDto.getReplyId()).get());
        }
        reportEntity.setAppUser(appUser);
        return reportRepository.save(reportEntity).getReportId();
    }

    public ReplyResponseDto addReply(Long boardId, ReplyRequestDto reply, AppUserEntity appUser) {
        ReplyEntity replyEntity;
        if (reply.getReplyId() != null) {
            replyEntity = replyRepository.findById(reply.getReplyId()).get();
        } else {
            replyEntity = new ReplyEntity();
        }
        replyEntity.setContent(reply.getContent());
        replyEntity.setLikeCnt(0);
        replyEntity.setDeleteYn("N");
        replyEntity.setBlindYn("N");
        replyEntity.setAppUser(appUser);
        replyEntity.setBoard(communityBoardRepository.findById(boardId).get());
        if (reply.getUpTreeId() == null) {
            replyEntity.setLevel(0);
        } else {
            replyEntity.setUpTreeId(reply.getUpTreeId());
            replyEntity.setLevel(replyRepository.findById(reply.getUpTreeId()).get().getLevel() + 1);
        }
        ReplyEntity newReply = replyRepository.save(replyEntity);
        ReplyResponseDto replyResponseDto = new ReplyResponseDto();
        replyResponseDto.setReplyId(newReply.getReplyId());
        replyResponseDto.setContent(replyEntity.getContent());
        replyResponseDto.setUpTreeId(replyEntity.getUpTreeId());
        replyResponseDto.setLevel(replyEntity.getLevel());
        replyResponseDto.setDeleteYn(replyEntity.getDeleteYn());
        replyResponseDto.setInsertTimestamp(replyEntity.getInsertTimestamp());
        replyResponseDto.setNickname(replyEntity.getAppUser().getNickname());
        replyResponseDto.setAppUserId(replyEntity.getAppUser().getAppUserId());
        return replyResponseDto;
    }

    public PaginatedResponse<CommunityBoardResponseDto> getFreeBoards(String order, Pageable pageable) {
        Page<CommunityBoardEntity> boardPage = communityBoardRepository.findFreeBoardWithSorting(order, pageable);

        List<CommunityBoardResponseDto> boardDtos = boardPage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<CommunityBoardResponseDto>(
                boardDtos,
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalElements(),
                boardPage.getTotalPages()
        );
    }

    public PaginatedResponse<CommunityBoardResponseDto> getQnABoards(Integer collegeId, String order, Pageable pageable) {
        Page<CommunityBoardEntity> boardPage = communityBoardRepository.findQnABoardWithSorting(collegeId, order, pageable);

        List<CommunityBoardResponseDto> boardDtos = boardPage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<CommunityBoardResponseDto>(
                boardDtos,
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalElements(),
                boardPage.getTotalPages()
        );
    }
    public List<ReplyResponseDto> getReplies(Long boardId, String order) {
        List<ReplyEntity> replies;

        // 정렬 기준에 따라 댓글 조회
        if ("asc".equalsIgnoreCase(order)) {
            replies = replyRepository.findAllByBoardBoardIdOrderByInsertTimestampAsc(boardId);
        } else {
            replies = replyRepository.findAllByBoardBoardIdOrderByInsertTimestampDesc(boardId);
        }

        // ReplyEntity -> ReplyResponseDto 변환
        return replies.stream()
                .map(reply -> new ReplyResponseDto(
                        reply.getContent(),
                        reply.getUpTreeId(), // 부모 댓글 ID (대댓글인 경우),
                        reply.getAppUser().getAppUserId(),
                        reply.getLevel(),
                        reply.getAppUser().getNickname(), // 작성자 닉네임 반환
                        reply.getInsertTimestamp(),
                        reply.getReplyId(),
                        reply.getDeleteYn(),
                        reply.getBlindYn(),
                        reply.getAppUser().getCollege().getCollegeName(),
                        reply.getAppUser().getCollegeAdmissionYear()
                ))
                .collect(Collectors.toList());
    }

    public void deleteReply(Long replyId) {
        ReplyEntity replyEntity = replyRepository.findById(replyId).get();
        replyEntity.setDeleteYn("Y");
        replyRepository.save(replyEntity);
    }
    public void likeReply(Long replyId) {
        ReplyEntity replyEntity = replyRepository.findById(replyId).get();
        replyEntity.setLikeCnt(replyEntity.getLikeCnt() + 1);
        replyRepository.save(replyEntity);
    }




    private CommunityBoardResponseDto convertEntityToDto(CommunityBoardEntity boardEntity) {
        CommunityBoardResponseDto dto = new CommunityBoardResponseDto();
        dto.setBoardId(boardEntity.getBoardId());
        dto.setTitle(boardEntity.getTitle());
        dto.setContent(boardEntity.getContent());
        dto.setSecretYn(boardEntity.getSecretYn());
        dto.setBoardType(boardEntity.getBoardType().getCodeCd()); // CodeEntity의 코드명 반환
        dto.setCollegeName(boardEntity.getAppUser().getCollege().getCollegeName()); // 대학 이름 반환
        dto.setCollegeId(boardEntity.getAppUser().getCollege().getCollegeId());
        dto.setViewCnt(boardEntity.getViewCnt());
        dto.setInsertTimestamp(boardEntity.getInsertTimestamp());
        List<ImageResponseDto> imageEntities = new ArrayList<>();
        if (boardEntity.getImages() != null) {
            for (ImageEntity image : boardEntity.getImages()) {
                ImageResponseDto imageDto = new ImageResponseDto();
                imageDto.setImageId(image.getImageId());
                imageDto.setImageUrl(imageBaseUrl + "/" + image.getImageId());
                imageEntities.add(imageDto);
            }
            dto.setImages(imageEntities);
        }
        dto.setLikeCnt(boardEntity.getLikeCnt());
        dto.setReplyCnt(replyRepository.findAllByBoardBoardIdOrderByInsertTimestampAsc(boardEntity.getBoardId()).size());
        if(boardEntity.getAppUser().getProfileImg() != null) {
            dto.setProfileImgUrl(imageBaseUrl+"/"+boardEntity.getAppUser().getProfileImg().getImageId());
        }

        return dto;
    }
}
