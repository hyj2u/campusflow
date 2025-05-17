package com.cnco.campusflow.user;

import com.cnco.campusflow.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(
    name = "User",
    description = """
        사용자 관리 API
        
        * 회원가입
        * 로그인/토큰 갱신
        * 사용자 정보 조회/수정
        * 전화번호 변경
        
        대부분의 API는 JWT 인증이 필요합니다.
        """
)

public class AppUserController {
    private final AppUserService appUserService;

    @Operation(
        summary = "회원가입",
        description = """
            새로운 사용자를 등록합니다.
            
            * 프로필 이미지와 대학 이미지는 선택사항입니다.
            * 아이디와 닉네임은 중복될 수 없습니다.
            * 비밀번호는 암호화되어 저장됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (중복된 아이디/닉네임, 필수값 누락 등)"),
        @ApiResponse(responseCode = "415", description = "지원하지 않는 이미지 형식")
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> registerUser(
        @Parameter(
            description = "회원가입 정보",
            required = true,
            schema = @Schema(implementation = AppUserDto.class)
        )
        @RequestBody AppUserDto request,
        @Parameter(description = "프로필 이미지 파일 (선택사항)")
        @RequestParam(required = false) MultipartFile profileImg,
        @Parameter(description = "대학 이미지 파일 (선택사항)")
        @RequestParam(required = false) MultipartFile collegeImg
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.registerUser(request, profileImg, collegeImg)));
    }

    @Operation(
        summary = "아이디 중복 확인",
        description = "입력한 아이디의 사용 가능 여부를 확인합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용 가능한 아이디"),
        @ApiResponse(responseCode = "400", description = "이미 사용 중인 아이디")
    })
    @GetMapping("/chk/id")
    public ResponseEntity<CommonResponse<?>> chkId(
        @Parameter(description = "확인할 아이디", required = true, example = "user123")
        @RequestParam String userId
    ) throws IOException {
        appUserService.chkId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(userId));
    }

    @Operation(
        summary = "닉네임 중복 확인",
        description = "입력한 닉네임의 사용 가능 여부를 확인합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임"),
        @ApiResponse(responseCode = "400", description = "이미 사용 중인 닉네임")
    })
    @GetMapping("/chk/nicknm")
    public ResponseEntity<CommonResponse<?>> chkNickname(
        @Parameter(description = "확인할 닉네임", required = true, example = "닉네임123")
        @RequestParam String nickname
    ) throws IOException {
        appUserService.chkNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(nickname));
    }

    @Operation(
        summary = "로그인",
        description = """
            사용자 인증 후 JWT 토큰을 발급합니다.
            
            * 발급된 토큰은 Authorization 헤더에 Bearer 형식으로 포함해야 합니다.
            * 토큰은 1시간 후 만료됩니다.
            * 리프레시 토큰도 함께 발급됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "로그인 성공"),
        @ApiResponse(responseCode = "401", description = "잘못된 아이디 또는 비밀번호")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Parameter(
            description = "로그인 정보",
            required = true,
            schema = @Schema(implementation = LoginRequestDto.class)
        )
        @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.login(request)));
    }

    @Operation(
        summary = "토큰 갱신",
        description = """
            리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
            
            * 리프레시 토큰은 7일간 유효합니다.
            * 새로운 액세스 토큰은 1시간간 유효합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "토큰 갱신 성공"),
        @ApiResponse(responseCode = "401", description = "유효하지 않은 리프레시 토큰")
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
        @Parameter(
            description = "리프레시 토큰",
            required = true,
            schema = @Schema(implementation = RefreshRequestDto.class)
        )
        @RequestBody RefreshRequestDto refreshToken
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.refreshToken(refreshToken));
    }

    @Operation(
        summary = "사용자 프로필 조회",
        description = """
            현재 로그인한 사용자의 프로필 정보를 조회합니다.
            
            * JWT 토큰이 필요합니다.
            * 프로필 이미지와 대학 이미지 URL이 포함됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근")
    })
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(
        @Parameter(description = "인증된 사용자 정보", hidden = true)
        @AuthenticationPrincipal AppUserEntity userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAppUserProfile(userDetails));
    }

    @Operation(
        summary = "전화번호 변경 요청",
        description = """
            새로운 전화번호로 변경하기 위한 인증 코드를 발송합니다.
            응답값에 인증코드 포함.
            * 인증 코드는 SMS로 발송됩니다.
            * 인증 코드는 3분간 유효합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "인증 코드 발송 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 전화번호 형식"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근")
    })
    @PostMapping("/phone/req")
    public ResponseEntity<?> chgPhoneReq(
        @Parameter(
            description = "전화번호 변경 요청 정보",
            required = true,
            schema = @Schema(implementation = AppUserDto.class)
        )
        @RequestBody AppUserDto appUserDto
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", appUserService.sendChgPhoneReq(appUserDto));
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @Operation(
        summary = "전화번호 변경 확인",
        description = """
            발송된 인증 코드를 확인하고 전화번호를 변경합니다.
            
            * 인증 코드는 3분간 유효합니다.
            * 인증 코드는 한 번만 사용 가능합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "전화번호 변경 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 인증 코드"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근")
    })
    @PutMapping("/phone/verify")
    public ResponseEntity<?> chgPhoneReq(
        @Parameter(description = "인증된 사용자 정보", hidden = true)
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "전화번호 인증 정보",
            required = true,
            schema = @Schema(implementation = PhoneVerifyDto.class)
        )
        @RequestBody PhoneVerifyDto phoneVerifyDto
    ) {
        appUserService.verifyAndChangePhone(appUser, phoneVerifyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
