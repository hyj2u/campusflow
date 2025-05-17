package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageRepository;
import com.cnco.campusflow.image.ImageResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsCenterBoardService {
    private final CsCenterBoardRepository csCenterBoardRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public CsCenterBoardResponseDto createBoard(CsCenterBoardRequestDto requestDto, AppUserEntity appUser, List<MultipartFile> images) {
        // 게시판 엔티티 생성
        CsCenterBoardEntity board = CsCenterBoardEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .appUser(appUser)
                .viewCnt(0)
                .build();

        // 이미지 처리
        List<ImageEntity> imageEntities = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setImgNm(image.getOriginalFilename());
                    // TODO: 실제 이미지 저장 경로 설정 필요
                    imageEntity.setImgPath("/images/" + image.getOriginalFilename());
                    imageEntities.add(imageEntity);
                }
            }
        }

        // 게시판과 이미지 저장
        CsCenterBoardEntity savedBoard = saveWithImages(board, imageEntities);
        return convertToResponseDto(savedBoard);
    }

    @Transactional
    public CsCenterBoardResponseDto updateBoard(CsCenterBoardRequestDto requestDto, List<MultipartFile> images) {
        CsCenterBoardEntity board = findById(requestDto.getBoardId());
        
        // 게시판 정보 업데이트
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());

        // 이미지 처리
        List<ImageEntity> imageEntities = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            // 기존 이미지 삭제
            board.getImages().clear();
            
            // 새 이미지 추가
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setImgNm(image.getOriginalFilename());
                    // TODO: 실제 이미지 저장 경로 설정 필요
                    imageEntity.setImgPath("/images/" + image.getOriginalFilename());
                    imageEntities.add(imageEntity);
                }
            }
        }

        // 게시판과 이미지 저장
        CsCenterBoardEntity updatedBoard = saveWithImages(board, imageEntities);
        return convertToResponseDto(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        CsCenterBoardEntity board = findById(boardId);
        csCenterBoardRepository.delete(board);
    }

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
        return CsCenterBoardResponseDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCnt(entity.getViewCnt())
                .boardType(entity.getBoardType() != null ? entity.getBoardType().getCodeNm() : null)
                .nickname(entity.getAppUser() != null ? entity.getAppUser().getNickname() : null)
                .appUserId(entity.getAppUser() != null ? entity.getAppUser().getAppUserId() : null)
                .insertTimestamp(entity.getInsertTimestamp())
                .images(entity.getImages() != null
                        ? entity.getImages().stream()
                                .map(image -> ImageResponseDto.builder()
                                        .imageId(image.getImageId())
                                        .imgNm(image.getImgNm())
                                        .imgPath(image.getImgPath())
                                        .build())
                                .toList()
                        : new ArrayList<>())
                .build();
    }

    @Transactional
    public CsCenterBoardEntity saveWithImages(CsCenterBoardEntity board, List<ImageEntity> images) {
        CsCenterBoardEntity savedEntity = csCenterBoardRepository.save(board);
        
        if (images != null && !images.isEmpty()) {
            for (ImageEntity imageEntity : images) {
                imageEntity.setBoard(savedEntity);
                imageRepository.save(imageEntity);
            }
        }
        
        return savedEntity;
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
} 