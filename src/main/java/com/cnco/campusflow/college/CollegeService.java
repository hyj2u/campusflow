package com.cnco.campusflow.college;

import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.community.*;
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
public class CollegeService {

    private final CollegeRepository collegeRepository;
    public List<CollegeEntity> getColleges()  {
       return collegeRepository.findAll();
    }


}
