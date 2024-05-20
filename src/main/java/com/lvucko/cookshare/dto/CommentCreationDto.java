package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreationDto {
    long userId;
    long recipeId;
    String comment;
}
