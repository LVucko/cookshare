package com.lvucko.cookshare.models;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    //fali private na poljima
    long id;
    long userId;
    long recipeId;
    String comment;
    Date time;
}
