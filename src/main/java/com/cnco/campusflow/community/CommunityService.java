package com.cnco.campusflow.community;

import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
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

    public CommunityBoardResponeDto addBoard(CommunityBoardRequestDto board, AppUserEntity appUser, List<MultipartFile> images) throws IOException {
        CommunityBoardEntity boardEntity;
        List<ImageEntity> imageEntities;
        if(board.getBoardId() == null){
            boardEntity = new CommunityBoardEntity();
            imageEntities = new ArrayList<>();
        }else{
            boardEntity = communityBoardRepository.findById(board.getBoardId()).orElseThrow(()
                    -> new IllegalArgumentException("유효하지 않은 게시글 Id입니다."));
            imageEntities = boardEntity.getImages();
            imageEntities.clear();
        }
        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());
        boardEntity.setAppUser(appUser);
        boardEntity.setSecretYn(board.getSecretYn());
        boardEntity.setViewCnt(0);
        boardEntity.setBoardType(codeRepository.findByCodeCd(board.getType()));

        if(images != null) {
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
        CommunityBoardResponeDto dto=convertEntityToDto(boardEntity);
        dto.setNickname(appUser.getNickname());
        dto.setAppUserId(appUser.getAppUserId());
        return dto;
    }
    public CommunityBoardResponeDto getBoard(Long boardId) {
        CommunityBoardEntity boardEntity = communityBoardRepository.findById(boardId).get();
        boardEntity.setViewCnt(boardEntity.getViewCnt()+1);
        CommunityBoardResponeDto dto=convertEntityToDto(boardEntity);
        dto.setNickname(boardEntity.getAppUser().getNickname());
        dto.setAppUserId(boardEntity.getAppUser().getAppUserId());
        return dto;
    }
    public void deleteBoard(Long boardId) {
      communityBoardRepository.deleteById(boardId);
    }
    public Long reportBoard(ReportDto reportDto, AppUserEntity appUser) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReason(reportDto.getReason());
        reportEntity.setBoard(communityBoardRepository.findById(reportDto.getBoardId()).get());
        reportEntity.setAppUser(appUser);
        return reportRepository.save(reportEntity).getReportId();
    }
    public ReplyResponseDto addReply(Long boardId, ReplyRequestDto reply, AppUserEntity appUser) {
        ReplyEntity replyEntity;
        if(reply.getReplyId() != null) {
            replyEntity = replyRepository.findById(reply.getReplyId()).get();
        }else{
            replyEntity = new ReplyEntity();
        }
        replyEntity.setContent(reply.getContent());
        replyEntity.setAppUser(appUser);
        replyEntity.setBoard(communityBoardRepository.findById(boardId).get());
        if(reply.getUpTreeId()==null){
            replyEntity.setLevel(0);
        }else{
            replyEntity.setUpTreeId(reply.getUpTreeId());
            replyEntity.setLevel(replyRepository.findById(reply.getUpTreeId()).get().getLevel()+1);
        }
        ReplyEntity newReply =replyRepository.save(replyEntity);
        ReplyResponseDto replyResponseDto =new ReplyResponseDto();
        replyResponseDto.setReplyId(newReply.getReplyId());
        replyResponseDto.setContent(replyEntity.getContent());
        replyResponseDto.setUpTreeId(replyEntity.getUpTreeId());
        replyResponseDto.setLevel(replyEntity.getLevel());
        replyResponseDto.setInsertTimestamp(replyEntity.getInsertTimestamp());
        replyResponseDto.setNickname(replyEntity.getAppUser().getNickname());
        replyResponseDto.setAppUserId(replyEntity.getAppUser().getAppUserId());
        return replyResponseDto;
    }
    public List<CommunityBoardResponeDto> getFreeBoards(String order){

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
                        reply.getReplyId()
                ))
                .collect(Collectors.toList());
    }
    public void deleteReply(Long replyId) {
       replyRepository.deleteById(replyId);
    }


    private CommunityBoardResponeDto convertEntityToDto(CommunityBoardEntity boardEntity) {
        CommunityBoardResponeDto dto = new CommunityBoardResponeDto();
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
        if(boardEntity.getImages() != null) {
            for (ImageEntity image : boardEntity.getImages()) {
                ImageResponseDto imageDto = new ImageResponseDto();
                imageDto.setImageId(image.getImageId());
                imageDto.setImageUrl(imageBaseUrl+"/"+image.getImageId());
                imageEntities.add(imageDto);
            }
            dto.setImages(imageEntities);
        }
        return dto;
    }
}
