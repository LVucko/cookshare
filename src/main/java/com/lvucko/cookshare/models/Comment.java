package com.lvucko.cookshare.models;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private long id;
    private long userId;
    private long recipeId;
    private String comment;
    private Date time;
}
