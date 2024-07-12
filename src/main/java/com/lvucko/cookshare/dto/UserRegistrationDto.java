package com.lvucko.cookshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private String realName;
    private String phone;
    private Long pictureId;
}
