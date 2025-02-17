package com.cnco.campusflow.user;

import com.cnco.campusflow.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
