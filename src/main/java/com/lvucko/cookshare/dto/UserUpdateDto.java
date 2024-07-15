package com.lvucko.cookshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String about;
    private String phone;
    private String realName;
    private String email;
    private Long pictureId;
    private Boolean showRealName;
    private Boolean showPhone;
    private Boolean showEmail;
}
