package com.cnco.campusflow.user;

import com.cnco.campusflow.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
@Tag(name = "User", description = "User management APIs")
public class AppUserController {
    private final AppUserService appUserService;

    @Operation(summary = "Register a new user", description = "Creates a new user with the provided details and optional profile images.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> registerUser(
        @Parameter(description = "User registration details") @RequestBody AppUserDto request,
        @Parameter(description = "Profile image file") @RequestParam(required = false) MultipartFile profileImg,
        @Parameter(description = "College image file") @RequestParam(required = false) MultipartFile collegeImg
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.registerUser(request, profileImg, collegeImg)));
    }

    @Operation(summary = "Check if user ID is available", description = "Verifies if the provided user ID is already in use.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User ID is available"),
        @ApiResponse(responseCode = "400", description = "User ID is already taken")
    })
    @GetMapping("/chk/id")
    public ResponseEntity<CommonResponse<?>> chkId(
        @Parameter(description = "User ID to check") @RequestParam String userId
    ) throws IOException {
        appUserService.chkId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(userId));
    }

    @Operation(summary = "Check if nickname is available", description = "Verifies if the provided nickname is already in use.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nickname is available"),
        @ApiResponse(responseCode = "400", description = "Nickname is already taken")
    })
    @GetMapping("/chk/nicknm")
    public ResponseEntity<CommonResponse<?>> chkNickname(
        @Parameter(description = "Nickname to check") @RequestParam String nickname
    ) throws IOException {
        appUserService.chkNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(nickname));
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Parameter(description = "Login credentials") @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(appUserService.login(request)));
    }

    @Operation(summary = "Refresh token", description = "Refreshes the authentication token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Token refreshed successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
        @Parameter(description = "Refresh token details") @RequestBody RefreshRequestDto refreshToken
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.refreshToken(refreshToken));
    }

    @Operation(summary = "Get user profile", description = "Retrieves the profile of the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAppUserProfile(userDetails));
    }

    @Operation(summary = "Request phone change", description = "Sends a request to change the user's phone number.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request sent successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/phone/req")
    public ResponseEntity<?> chgPhoneReq(
        @Parameter(description = "User details for phone change") @RequestBody AppUserDto appUserDto
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", appUserService.sendChgPhoneReq(appUserDto));
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @Operation(summary = "Verify and change phone", description = "Verifies the phone number and updates it for the user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Phone number updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid verification code")
    })
    @PutMapping("/phone/verify")
    public ResponseEntity<?> chgPhoneReq(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Phone verification details") @RequestBody PhoneVerifyDto phoneVerifyDto
    ) {
        appUserService.verifyAndChangePhone(appUser, phoneVerifyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
