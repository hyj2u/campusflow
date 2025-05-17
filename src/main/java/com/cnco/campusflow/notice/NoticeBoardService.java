package com.cnco.campusflow.notice;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    
    @Value("${image.base.url}")
    private String imageBaseUrl;

    private static final String MEMBER_NOTICE_CODE = "member"; // 회원공지사항 코드 (code_id = 10)

    public NoticeBoardResponseDto getNotice(Long noticeId) {
        NoticeBoardEntity noticeEntity = noticeBoardRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 공지사항 ID 입니다."));
        
        // 회원공지사항이 아닌 경우 예외 발생
        if (!MEMBER_NOTICE_CODE.equals(noticeEntity.getBoardType().getCodeCd())) {
            throw new IllegalArgumentException("회원공지사항이 아닙니다.");
        }
        
        noticeEntity.incrementViewCount();
        return convertEntityToDto(noticeEntity);
    }

    public PaginatedResponse<NoticeBoardResponseDto> getNotices(Pageable pageable) {
        Page<NoticeBoardEntity> noticePage = noticeBoardRepository.findAllByBoardTypeCodeCdOrderByInsertTimestampDesc(MEMBER_NOTICE_CODE, pageable);

        List<NoticeBoardResponseDto> noticeDtos = noticePage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                noticeDtos,
                noticePage.getNumber(),
                noticePage.getSize(),
                noticePage.getTotalElements(),
                noticePage.getTotalPages()
        );
    }

    private NoticeBoardResponseDto convertEntityToDto(NoticeBoardEntity noticeEntity) {
        NoticeBoardResponseDto dto = new NoticeBoardResponseDto();
        dto.setBoardId(noticeEntity.getBoardId());
        dto.setTitle(noticeEntity.getTitle());
        dto.setContent(noticeEntity.getContent());
        dto.setViewCnt(noticeEntity.getViewCnt());
        dto.setPushYn(noticeEntity.getPushYn());
        dto.setTalkYn(noticeEntity.getTalkYn());
        dto.setBoardType(noticeEntity.getBoardType() != null ? noticeEntity.getBoardType().getCodeCd() : null);
        dto.setNickname(noticeEntity.getAppUser().getNickname());
        dto.setAppUserId(noticeEntity.getAppUser().getAppUserId());

        List<ImageResponseDto> imageDtos = new ArrayList<>();
        if (noticeEntity.getImages() != null) {
            for (ImageEntity image : noticeEntity.getImages()) {
                ImageResponseDto imageDto = new ImageResponseDto();
                imageDto.setImageId(image.getImageId());
                imageDto.setImageUrl(imageBaseUrl + "/" + image.getImageId());
                imageDtos.add(imageDto);
            }
            dto.setImages(imageDtos);
        }

        if (noticeEntity.getAppUser().getProfileImg() != null) {
            dto.setProfileImgUrl(imageBaseUrl + "/" + noticeEntity.getAppUser().getProfileImg().getImageId());
        }

        // 매핑된 store_id 목록 추가
        if (noticeEntity.getStoreMappings() != null) {
            List<Long> storeIds = noticeEntity.getStoreMappings().stream()
                    .map(NoticeBoardMappEntity::getStoreId)
                    .collect(Collectors.toList());
            dto.setStoreIds(storeIds);
        }

        return dto;
    }
} 