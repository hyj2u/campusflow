package com.cnco.campusflow.user;

import com.cnco.campusflow.common.CommonResponse;
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
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> registerUser(@RequestBody AppUserDto request, @RequestParam(required = false)MultipartFile profileImg,
                                                          @RequestParam(required = false)MultipartFile collegeImg) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.registerUser(request, profileImg, collegeImg)));
    }
    @GetMapping("/chk/id")
    public ResponseEntity<CommonResponse<?>> chkId(@RequestParam String userId) throws IOException {
        appUserService.chkId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(userId));
    }
    @GetMapping("/chk/nicknm")
    public ResponseEntity<CommonResponse<?>> chkNickname(@RequestParam String nickname) throws IOException {
        appUserService.chkNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(nickname));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.login(request)));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequestDto refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.refreshToken(refreshToken));
    }
    @GetMapping("/profile")
    public ResponseEntity<?>  getUserProfile(@AuthenticationPrincipal AppUserEntity userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAppUserProfile(userDetails));
    }

    @PostMapping("/phone/req")
    public ResponseEntity<?>  chgPhoneReq( @RequestBody AppUserDto appUserDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", appUserService.sendChgPhoneReq(appUserDto));
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
    @PutMapping("/phone/verify")
    public ResponseEntity<?>  chgPhoneReq(@AuthenticationPrincipal AppUserEntity appUser, @RequestBody PhoneVerifyDto phoneVerifyDto) {
        appUserService.verifyAndChangePhone(appUser, phoneVerifyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
