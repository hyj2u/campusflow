package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageRepository;
import com.cnco.campusflow.image.ImageResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.code.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.cnco.campusflow.common.FileUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class CsCenterBoardService {
    private final CsCenterBoardRepository csCenterBoardRepository;
    private final ImageRepository imageRepository;
    private final CodeRepository codeRepository;
    private final CsCenterReplyService csCenterReplyService;
    private final FileUtil fileUtil;
    @Value("${image.base.path}")
    private String imageBasePath;
    @Value("${image.base.url}")
    private String imageBaseUrl;

    @Transactional
    public CsCenterBoardResponseDto createBoard(CsCenterBoardRequestDto requestDto, AppUserEntity appUser, List<MultipartFile> images) throws IOException {
        // code_id 설정
        CodeEntity codeEntity = codeRepository.findByCodeCd(requestDto.getCodeCd())
                .orElseThrow(() -> new RuntimeException("Code not found for type: " + requestDto.getCodeCd()));

        // 게시판 엔티티 생성
        CsCenterBoardEntity board = CsCenterBoardEntity.builder()
                .content(requestDto.getContent())
                .appUser(appUser)
                .boardType(codeEntity)
                .viewCnt(0)
                .deleteYn("N")
                .build();

        // 이미지 처리
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String fileName = fileUtil.saveFile(imageBasePath, image);
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setImgNm(image.getOriginalFilename());
                    imageEntity.setImgPath(imageBasePath + "/" + fileName);
                    imageEntity.setBoard(board);
                    imageRepository.save(imageEntity);
                }
            }
        }

        // 게시판과 이미지 저장
        CsCenterBoardEntity savedBoard = csCenterBoardRepository.save(board);
        return convertToResponseDto(savedBoard);
    }

    @Transactional
    public CsCenterBoardResponseDto updateBoard(Long boardId, CsCenterBoardRequestDto requestDto, List<MultipartFile> images) throws IOException {
        CsCenterBoardEntity board = findById(boardId);
        
        // code_id 설정
        CodeEntity codeEntity = codeRepository.findByCodeCd(requestDto.getCodeCd())
                .orElseThrow(() -> new RuntimeException("Code not found for type: " + requestDto.getCodeCd()));
        
        // 게시판 정보 업데이트
        board.setContent(requestDto.getContent());
        board.setBoardType(codeEntity);

        // 이미지 처리
        if (images != null && !images.isEmpty()) {
            board.getImages().clear();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String fileName = fileUtil.saveFile(imageBasePath, image);
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setImgNm(image.getOriginalFilename());
                    imageEntity.setImgPath(imageBasePath + "/" + fileName);
                    imageEntity.setBoard(board);
                    imageRepository.save(imageEntity);
                }
            }
        }

        // 게시판과 이미지 저장
        CsCenterBoardEntity updatedBoard = csCenterBoardRepository.save(board);
        return convertToResponseDto(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        CsCenterBoardEntity board = findById(boardId);
        board.setDeleteYn("Y");
        csCenterBoardRepository.save(board);
    }

    @Transactional
    public CsCenterBoardResponseDto getBoard(Long boardId) {
        CsCenterBoardEntity board = findById(boardId);
        board.incrementViewCount(); // 조회수 증가
        return convertToResponseDto(board);
    }

    public PaginatedResponse<CsCenterBoardResponseDto> getBoards(Pageable pageable) {
        Page<CsCenterBoardEntity> page = csCenterBoardRepository.findAll(pageable);
        List<CsCenterBoardResponseDto> content = page.getContent().stream()
                .map(this::convertToResponseDto)
                .toList();
        return new PaginatedResponse<CsCenterBoardResponseDto>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PaginatedResponse<CsCenterBoardResponseDto> getMyBoards(Long appUserId, Pageable pageable) {
        Page<CsCenterBoardEntity> page = csCenterBoardRepository.findByAppUserIdOrderByInsertTimestampDesc(appUserId, pageable);
        List<CsCenterBoardResponseDto> content = page.getContent().stream()
                .map(this::convertToResponseDto)
                .toList();
        return new PaginatedResponse<CsCenterBoardResponseDto>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private CsCenterBoardResponseDto convertToResponseDto(CsCenterBoardEntity entity) {
        List<CsCenterReplyResponseDto> replies = csCenterReplyService != null ? csCenterReplyService.getReplies(entity.getBoardId(), "DESC") : new ArrayList<>();
        // level 오름차순 정렬
        replies.sort(Comparator.comparingInt(CsCenterReplyResponseDto::getLevel));
        return CsCenterBoardResponseDto.builder()
                .boardId(entity.getBoardId())
                .content(entity.getContent())
                .viewCnt(entity.getViewCnt())
                .boardType(entity.getBoardType() != null ? entity.getBoardType().getCodeNm() : null)
                .boardTypeCodeId(entity.getBoardType() != null ? entity.getBoardType().getCodeId() : null)
                .boardTypeCodeCd(entity.getBoardType() != null ? entity.getBoardType().getCodeCd() : null)
                .helpfulYn(entity.getHelpfulYn())
                .deleteYn(entity.getDeleteYn())
                .nickname(entity.getAppUser() != null ? entity.getAppUser().getNickname() : null)
                .appUserId(entity.getAppUser() != null ? entity.getAppUser().getAppUserId() : null)
                .insertTimestamp(entity.getInsertTimestamp())
                .images(entity.getImages() != null
                        ? entity.getImages().stream()
                                .map(image -> ImageResponseDto.builder()
                                        .imageId(image.getImageId())
                                        .imageUrl(imageBaseUrl + "/" + image.getImageId())
                                        .imgNm(image.getImgNm())
                                        .imgPath(image.getImgPath())
                                        .build())
                                .toList()
                        : new ArrayList<>())
                .replies(replies)
                .noHelpfulReason(entity.getNoHelpfulReason())
                .activeYn(entity.getActiveYn())
                .build();
    }

    public CsCenterBoardEntity findById(Long id) {
        return csCenterBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public List<CsCenterBoardEntity> findAll() {
        return csCenterBoardRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        csCenterBoardRepository.deleteById(id);
    }

    @Transactional
    public CsCenterBoardResponseDto setHelpfulYn(Long boardId, String helpfulYn, String noHelpfulReason, AppUserEntity user) {
        CsCenterBoardEntity board = findById(boardId);
        board.setHelpfulYn(helpfulYn);
        if ("N".equalsIgnoreCase(helpfulYn)) {
            board.setNoHelpfulReason(noHelpfulReason);
        } else {
            board.setNoHelpfulReason(null);
        }
        return convertToResponseDto(board);
    }

    @Transactional
    public CsCenterBoardResponseDto setActiveYn(Long boardId, String activeYn, AppUserEntity user) {
        CsCenterBoardEntity board = findById(boardId);
        board.setActiveYn(activeYn);
        return convertToResponseDto(board);
    }
} 