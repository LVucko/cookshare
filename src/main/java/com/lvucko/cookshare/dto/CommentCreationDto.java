package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreationDto {
    private long userId;
    private long recipeId;
    private String comment;
}
