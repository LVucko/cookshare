package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDetailsDto {
    //fali private na poljima
    long id;
    long userId;
    long recipeId;
    String comment;
    Date time;
    String username;
}
