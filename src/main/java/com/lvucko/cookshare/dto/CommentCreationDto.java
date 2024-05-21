package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreationDto {
    //fali private na poljima
    long userId;
    long recipeId;
    String comment;
}
