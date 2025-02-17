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
}
