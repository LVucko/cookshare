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
public class UserPersonalDetailsDto {
    private Long id;
    private String username;
    private String email;
    private String realName;
    private Date creationDate;
    private String phone;
    private String pathToPicture;
    private String about;
    private Boolean showRealName;
    private Boolean showPhone;
    private Boolean showEmail;
}
