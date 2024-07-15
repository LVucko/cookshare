package com.lvucko.cookshare.mappers;

import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.dto.UserPersonalDetailsDto;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDetailsDto mapToDetails(User user, Picture picture) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .creationDate(user.getCreationDate())
                .about(user.getAbout())
                .phone(user.getShowPhone() ? user.getPhone() : "")
                .realName(user.getShowRealName() ? user.getRealName() : "")
                .email(user.getShowEmail() ? user.getEmail() : "")
                .pathToPicture(picture.getPathToPicture())
                .build();
    }
    public UserPersonalDetailsDto mapToPersonalDetails(User user, Picture picture) {
        return UserPersonalDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .creationDate(user.getCreationDate())
                .about(user.getAbout())
                .phone(user.getPhone())
                .realName(user.getRealName())
                .email(user.getEmail())
                .pathToPicture(picture.getPathToPicture())
                .about(user.getAbout())
                .showRealName(user.getShowRealName())
                .showPhone(user.getShowPhone())
                .showEmail(user.getShowEmail())
                .build();
    }

}
