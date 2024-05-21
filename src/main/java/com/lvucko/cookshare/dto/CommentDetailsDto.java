package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDetailsDto {
    private long id;
    private long userId;
    private long recipeId;
    private String comment;
    private Date time;
    private String username;
}
