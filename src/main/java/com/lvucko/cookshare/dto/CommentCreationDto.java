package com.lvucko.cookshare.dto;

import lombok.*;

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
