package com.cnco.campusflow.user;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.college.CollegeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.image.ImageEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CollegeRepository collegeRepository;
    private final FileUtil fileUtil;
    @Value("${image.base.path}")
    private String imageBasePath; // Properties에서 이미지 경로 주입

    public AppUserEntity registerUser(AppUserDto dto, MultipartFile profileImg, MultipartFile collegeImg) throws IOException {
        if (appUserRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (appUserRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        AppUserEntity user = new AppUserEntity();
        user.setUserId(dto.getUserId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEnterYear(dto.getEnterYear());
        user.setMajor(dto.getMajor());
        user.setPhone(dto.getPhone());
        user.setUsername(dto.getUsername());
        user.setBirthday(dto.getBirthday());
        user.setUserStatus("A");
        if(dto.getCollegeId() != null) {
            user.setApproveStatus("R");
            // College 매핑
            CollegeEntity college = collegeRepository.findById(dto.getCollegeId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 대학을 찾을 수 없습니다."));
            user.setCollege(college);
        }else{
            user.setApproveStatus("N");
        }

        // Profile 이미지 매핑
        if(profileImg!=null) {
            // 파일 이름 생성
            String fileName = System.currentTimeMillis() + "_" + profileImg.getOriginalFilename();
            // 파일 저장
            fileUtil.saveFile(imageBasePath, fileName, profileImg);
            // ImageEntity 생성 및 저장
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImgNm(profileImg.getOriginalFilename());
            imageEntity.setImgPath(imageBasePath + "/" + fileName);
            user.setProfileImg(imageEntity);
        }
        // College 이미지 매핑
        if (collegeImg != null) {
            String fileName = System.currentTimeMillis() + "_" + collegeImg.getOriginalFilename();
            // 파일 저장
            fileUtil.saveFile(imageBasePath, fileName, collegeImg);
            // ImageEntity 생성 및 저장
            ImageEntity collegeImgEntity = new ImageEntity();
            collegeImgEntity.setImgNm(collegeImg.getOriginalFilename());
            collegeImgEntity.setImgPath(imageBasePath + "/" + fileName);
            user.setCollegeImg(collegeImgEntity);
        }

        return appUserRepository.save(user);
    }
}
