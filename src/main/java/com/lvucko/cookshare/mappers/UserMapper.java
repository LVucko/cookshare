package com.lvucko.cookshare.mappers;

import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDetailsDto mapToDetails(User user) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .creationDate(user.getCreationDate())
                .pathToPicture(user.getPathToPicture())
                .build();
    }
}
