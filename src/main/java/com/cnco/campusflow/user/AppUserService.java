package com.cnco.campusflow.user;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.college.CollegeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.jwt.JwtUtil;
import com.cnco.campusflow.jwt.RefreshTokenEntity;
import com.cnco.campusflow.jwt.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CollegeRepository collegeRepository;
    private final FileUtil fileUtil;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${image.base.path}")
    private String imageBasePath; // Properties에서 이미지 경로 주입
    @Value("${image.base.url}")
    private String imageBaseUrl;


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
    public void chkId(String userId){
        if (appUserRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }
    public void chkNickname(String nickname){
        if (appUserRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
    }
    public TokenReponseDto login(LoginRequestDto request) {
        AppUserEntity user = appUserRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 ID입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        // 동일 기기에서 로그인하면 기존 Refresh Token 삭제
        refreshTokenRepository.findByAppUserAndDeviceInfo(user, request.getDeviceInfo())
                .ifPresent(refreshTokenRepository::delete);

        // 새로운 Refresh Token 저장 (기기 정보 + FCM Token 포함)
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setAppUser(user);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)); // 7일 만료
        refreshTokenEntity.setDeviceInfo(request.getDeviceInfo());
        refreshTokenEntity.setFcmToken(request.getFcmToken()); // 🔹 FCM Token 저장
        refreshTokenRepository.save(refreshTokenEntity);
        return new TokenReponseDto(accessToken, refreshToken);
    }
    public TokenReponseDto refreshToken(RefreshRequestDto request) {
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Refresh Token입니다."));
        if (tokenEntity.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(tokenEntity);
            throw new IllegalArgumentException("Refresh Token이 만료되었습니다. 다시 로그인하세요.");
        }
        String newAccessToken = jwtUtil.generateAccessToken(tokenEntity.getAppUser().getUserId());
        return new TokenReponseDto(newAccessToken, request.getRefreshToken());
    }
    public AppUserProfileResponseDto getAppUserProfile(AppUserEntity user) {
        AppUserProfileResponseDto dto = new AppUserProfileResponseDto();
        dto.setUserId(user.getUserId());
        dto.setNickname(user.getNickname());
        dto.setPhone(user.getPhone());
        dto.setUsername(user.getUsername());
        dto.setBirthday(user.getBirthday());
        dto.setUserStatus(user.getUserStatus());
        dto.setAppUserId(user.getAppUserId());
        dto.setApproveStatus(user.getApproveStatus());
        if(user.getCollege()!=null){
            dto.setEnterYear(user.getEnterYear());
            dto.setMajor(user.getMajor());
            dto.setCollegeId(user.getCollege().getCollegeId());
            dto.setCollegeName(user.getCollege().getCollegeName());
        }
        if(user.getProfileImg()!=null){
            dto.setProfileImgUrl(imageBaseUrl + "/" + user.getProfileImg().getImageId());
        }
        return dto;
    }


}
