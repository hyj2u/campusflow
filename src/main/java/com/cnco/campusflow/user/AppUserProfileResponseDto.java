package com.cnco.campusflow.user;

import lombok.Data;

import java.util.Date;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserProfileResponseDto {
    private Long appUserId;
    private String userId;
    private String nickname;
    private String collegeAdmissionYear;
    private String major;
    private String phone;
    private String username;
    private Date birthday;
    private String userStatus;
    private String approveStatus;
    private Integer collegeId;
    private String collegeName;
    private String profileImgUrl;
    private String barcode;
}
